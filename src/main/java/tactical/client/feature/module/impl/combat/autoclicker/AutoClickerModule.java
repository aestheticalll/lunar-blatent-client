package tactical.client.feature.module.impl.combat.autoclicker;

import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.feature.module.setting.custom.Range;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.input.EventClickMouse;
import tactical.client.listener.event.player.EventUpdate;
import tactical.client.utility.math.MathUtils;
import tactical.client.utility.math.Timer;

/**
 * @author Gavin
 * @since 06/20/23
 */
@Register(value = "AutoClicker", category = Category.COMBAT)
public class AutoClickerModule extends Module {
    private final Setting<AutoClickerMode> mode = new Setting<>(
            "Mode", AutoClickerMode.BASIC);
    private final Setting<AttackMode> attack = new Setting<>(
            "Attack", AttackMode.CLICK);
    private final Setting<Range<Double>> cps = new Setting<>("CPS",
            new Range<>(8.0, 14.0, 0.1, 1.0, 20.0));
    private final Setting<Double> chance = new Setting<>(
            "Chance", 80.0, 0.1, 1.0, 100.0);
    private final Setting<Boolean> lookingAtEntity = new Setting<>(
            "LookingAtEntity", true);
    private final Setting<Boolean> limiter = new Setting<>("Limiter", true);

    private final Timer limitTimer = new Timer();
    private final Timer cpsTimer = new Timer();
    private int clicks;

    @Override
    public void disable() {
        super.disable();
        clicks = 0;
    }

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        MovingObjectPosition result = mc.objectMouseOver;
        if (result == null) return;

        if (result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                || mc.thePlayer.isUsingItem()) return;

        switch (attack.value()) {
            case HOVER -> {
                if (result.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) return;
            }

            case HOLD -> {
                if (!Mouse.isButtonDown(0)) return;
            }

            case CLICK -> {
                if (!mc.gameSettings.keyBindAttack.isPressed()) return;
            }
        }

        if (!MathUtils.chance(chance.value())) return;

        if (lookingAtEntity.value()
                && (result.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY
                || result.entityHit == null)) return;

        if (mode.value().click(cpsTimer, cps.value())) {
            cpsTimer.resetTimer();
            mc.clickMouse();
        }
    };

    @Subscribe
    private final Listener<EventClickMouse> clickMouse = (event) -> {
        if (!limiter.value()) return;

        ++clicks;
        if (limitTimer.passed(1000L, true)) {
            clicks = 0;
            return;
        }

        if (clicks >= 20) event.setCanceled(true);
    };
}

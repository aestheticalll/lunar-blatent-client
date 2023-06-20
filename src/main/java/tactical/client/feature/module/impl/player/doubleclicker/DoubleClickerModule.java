package tactical.client.feature.module.impl.player.doubleclicker;

import net.minecraft.util.MovingObjectPosition;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.player.EventUpdate;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "DoubleClicker", category = Category.PLAYER)
public class DoubleClickerModule extends Module {
    private final Setting<Boolean> lookingAtBlock = new Setting<>(
            "LookingAtBlock", true);

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {

        if (lookingAtBlock.value()) {
            MovingObjectPosition result = mc.objectMouseOver;
            if (result == null || result.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        }

        if (mc.gameSettings.keyBindUseItem.pressed && !mc.thePlayer.isUsingItem()) {
            mc.rightClickMouse();
        }
    };
}

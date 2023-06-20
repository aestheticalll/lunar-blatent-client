package tactical.client.feature.module.impl.combat.wtap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.network.EventPacket;
import tactical.client.listener.event.player.EventUpdate;
import tactical.client.utility.chat.Printer;
import tactical.client.utility.math.Timer;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "WTap", category = Category.COMBAT)
public class WTapModule extends Module {

    private final Setting<Double> delay = new Setting<>(
            "Delay", 0.2, 0.01, 0.0, 5.0);
    private final Setting<Double> holdTime = new Setting<>(
            "HoldTime", 0.4, 0.01, 0.1, 2.0);

    private final Timer timer = new Timer();
    private final Timer noSprintTimer = new Timer();

    private boolean cancelSprint;

    @Override
    public void disable() {
        super.disable();

        if (cancelSprint && mc.thePlayer != null) sprintState(true);
        cancelSprint = false;
    }

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        if (cancelSprint) {
            if (noSprintTimer.passed((long) (holdTime.value() * 1000.0), false)) {
                cancelSprint = false;
                sprintState(true);
                return;
            }

            sprintState(false);
        }
    };

    @Subscribe
    private final Listener<EventPacket.Outbound> packetOutbound = (event) -> {
        if (event.get() instanceof C02PacketUseEntity packet) {
            if (packet.getAction() != C02PacketUseEntity.Action.ATTACK
                    || !(packet.getEntityFromWorld(mc.theWorld) instanceof EntityLivingBase)
                    || !mc.thePlayer.isSprinting()
                    || cancelSprint) return;

            if (!timer.passed((long) (delay.value() * 1000.0), true)) return;

            sprintState(false);
            cancelSprint = true;
            noSprintTimer.resetTimer();
        }
    };

    /**
     * Sets the client sprint state
     * @param state the state
     */
    private void sprintState(boolean state) {
        mc.gameSettings.keyBindSprint.pressed = state;
        mc.thePlayer.setSprinting(state);
    }
}

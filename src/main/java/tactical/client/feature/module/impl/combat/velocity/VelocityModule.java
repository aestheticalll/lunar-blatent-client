package tactical.client.feature.module.impl.combat.velocity;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.network.EventPacket;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "Velocity", category = Category.COMBAT)
public class VelocityModule extends Module {
    private final Setting<VelocityMode> mode = new Setting<>(
            "Mode", VelocityMode.BASIC);
    private final Setting<Double> horizontal = new Setting<>(
            "Chance", 80.0, 0.1, 1.0, 200.0);
    private final Setting<Double> vertical = new Setting<>(
            "Chance", 100.0, 0.1, 1.0, 200.0);
    private final Setting<Double> chance = new Setting<>(
            "Chance", 80.0, 0.1, 1.0, 100.0);

    @Subscribe
    private final Listener<EventPacket.Inbound> packetInbound = (event) -> {
        if (event.get() instanceof S12PacketEntityVelocity packet) {
            if (packet.entityID != mc.thePlayer.getEntityId()) return;

            switch (mode.value()) {
                case BASIC -> {
                    packet.motionX = (int) (packet.motionX * (horizontal.value() / 100.0));
                    packet.motionY = (int) (packet.motionY * (vertical.value() / 100.0));
                    packet.motionZ = (int) (packet.motionZ * (horizontal.value() / 100.0));
                }

                case CANCEL -> event.setCanceled(true);

                case WATCHDOG -> {
                    // TODO
                }
            }
        }
    };
}

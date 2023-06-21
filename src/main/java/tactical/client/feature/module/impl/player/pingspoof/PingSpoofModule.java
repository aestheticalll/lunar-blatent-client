package tactical.client.feature.module.impl.player.pingspoof;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.network.EventPacket;
import tactical.client.listener.event.player.EventUpdate;
import tactical.client.utility.math.Timer;
import tactical.client.utility.network.PacketUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "PingSpoof", category = Category.PLAYER)
public class PingSpoofModule extends Module {

    private final Setting<Double> delay = new Setting<>(
            "Delay", 650.0, 0.01, 25.0, 5000.0);

    private final Queue<Packet<?>> packetQueue = new ConcurrentLinkedQueue<>();
    private final Timer timer = new Timer();

    @Override
    public void disable() {
        super.disable();

        if (mc.thePlayer != null) {
            while (!packetQueue.isEmpty()) {
                PacketUtils.silent(packetQueue.poll());
            }
        }

        packetQueue.clear();
    }

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        if (mc.thePlayer.ticksExisted <= 1) {
            packetQueue.clear();
            return;
        }

        if (timer.passed(delay.value().longValue(), false)) {
            while (!packetQueue.isEmpty()) {
                PacketUtils.silent(packetQueue.poll());
            }
            timer.resetTimer();
        }
    };

    @Subscribe
    private final Listener<EventPacket.Outbound> packetOutbound = (event) -> {
        if (event.get() instanceof C0FPacketConfirmTransaction
                || event.get() instanceof C00PacketKeepAlive) {

            event.setCanceled(true);
            packetQueue.add(event.get());
        }
    };
}

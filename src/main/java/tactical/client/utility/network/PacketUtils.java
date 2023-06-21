package tactical.client.utility.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import tactical.client.mixin.network.INetworkManager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class PacketUtils {

    /**
     * The ID for the next packet thread
     */
    private static final AtomicInteger ID = new AtomicInteger();

    /**
     * The {@link Minecraft} game instance
     */
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * A list of packet threads
     */
    private static final List<Thread> packetThreads = new LinkedList<>();

    /**
     * Sends a delayed packet
     * @param packet the packet
     * @param delay the delay
     */
    public static void delayed(Packet<?> packet, long delay) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
                return;
            }

            ((INetworkManager) mc.thePlayer.sendQueue.netManager)
                    .hookDispatchPacket(packet, null);

            if (packetThreads.contains(Thread.currentThread())) {
                Thread.currentThread().interrupt();
                packetThreads.remove(Thread.currentThread());
            }
        }, "Packet-Sender #" + ID.getAndIncrement());
        packetThreads.add(thread);
        thread.start();
    }

    /**
     * Sends a packet silently
     * @param packet the {@link Packet}
     */
    public static void silent(Packet<?> packet) {
        ((INetworkManager) mc.thePlayer.sendQueue.netManager)
                .hookDispatchPacket(packet, null);
    }

}

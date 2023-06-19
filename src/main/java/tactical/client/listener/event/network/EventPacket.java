package tactical.client.listener.event.network;

import net.minecraft.network.Packet;
import tactical.client.listener.bus.Cancelable;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class EventPacket extends Cancelable {
    private final Packet<?> packet;

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public <T extends Packet<?>> T get() {
        return (T) packet;
    }

    public static class Inbound extends EventPacket {

        public Inbound(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Outbound extends EventPacket {

        public Outbound(Packet<?> packet) {
            super(packet);
        }
    }
}

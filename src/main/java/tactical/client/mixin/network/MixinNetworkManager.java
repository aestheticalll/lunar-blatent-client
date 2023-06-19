package tactical.client.mixin.network;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tactical.client.Tactical;
import tactical.client.listener.event.network.EventPacket;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V",
            shift = At.Shift.BEFORE), cancellable = true)
    public void hook$channelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo info) {
        EventPacket.Inbound event = new EventPacket.Inbound(packet);
        if (Tactical.bus().dispatch(event)) info.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/NetworkManager;dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V",
            shift = At.Shift.BEFORE), cancellable = true)
    public void hook$sendPacket(Packet<?> packet, CallbackInfo info) {
        EventPacket.Outbound event = new EventPacket.Outbound(packet);
        if (Tactical.bus().dispatch(event)) info.cancel();
    }
}

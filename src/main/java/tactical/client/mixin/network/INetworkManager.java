package tactical.client.mixin.network;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NetworkManager.class)
public interface INetworkManager {

    @Invoker("dispatchPacket")
    void hookDispatchPacket(
            Packet<?> packet,
            GenericFutureListener<? extends Future<? super Void>>[] futureListeners);
}

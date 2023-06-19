package tactical.client.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tactical.client.Tactical;
import tactical.client.listener.event.start.EventStartMinecraft;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "startGame", at = @At("RETURN"))
    public void hook$startGame(CallbackInfo info) {
        Tactical.bus().dispatch(new EventStartMinecraft());
    }

    /**
     * @author Gavin
     * Canceled cause this seems stupid
     * If someone has a good reason as to why this is a thing, please tell me
     */
    @Inject(method = "startTimerHackThread", at = @At("HEAD"), cancellable = true)
    public void hook$startTimerHackThread(CallbackInfo info) {
        info.cancel();
    }
}

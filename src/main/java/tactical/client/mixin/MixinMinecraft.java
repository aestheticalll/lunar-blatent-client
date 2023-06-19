package tactical.client.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

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

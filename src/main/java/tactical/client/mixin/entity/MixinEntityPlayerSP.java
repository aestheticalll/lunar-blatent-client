package tactical.client.mixin.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tactical.client.Tactical;
import tactical.client.listener.event.player.EventUpdate;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void hook$onUpdate(CallbackInfo info) {
        Tactical.bus().dispatch(new EventUpdate());
    }
}

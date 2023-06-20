package tactical.client.mixin.render;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tactical.client.Tactical;
import tactical.client.listener.event.input.EventAttackReach;
import tactical.client.listener.event.render.EventRender3D;
import tactical.client.utility.render.Renders;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @ModifyConstant(method = "getMouseOver",
            constant = { @Constant(doubleValue = 3.0, ordinal = 1) })
    public double modifyReach(double value) {
        EventAttackReach event = new EventAttackReach(value);
        Tactical.bus().dispatch(event);
        return event.getReach();
    }

    // lunar overrides something that doesnt make my normal Render3D hook work
    @Inject(method = "renderWorldPass", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V",
            shift = At.Shift.BEFORE))
    public void hook$RenderWorldPass(int pass, float partialTicks, long time, CallbackInfo info) {
        Renders.updateProjection();
        Tactical.bus().dispatch(new EventRender3D(partialTicks));
    }
}

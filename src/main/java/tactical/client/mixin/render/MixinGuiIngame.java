package tactical.client.mixin.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tactical.client.Tactical;
import tactical.client.listener.event.render.EventRender2D;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Shadow @Final public Minecraft mc;

    @Inject(method = "renderGameOverlay", at = @At("TAIL"))
    public void hook$renderGameOverlay(float partialTicks, CallbackInfo info) {
        Tactical.bus().dispatch(new EventRender2D(
                partialTicks, new ScaledResolution(mc)));
    }
}

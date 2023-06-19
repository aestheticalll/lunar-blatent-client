package tactical.client.mixin.render;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tactical.client.Tactical;
import tactical.client.listener.event.input.EventAttackReach;

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
}

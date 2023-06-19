package tactical.client.listener.event.render;

import net.minecraft.client.gui.ScaledResolution;

/**
 * @author Gavin
 * @since 1.0.0
 */
public record EventRender2D(float partialTicks, ScaledResolution resolution) {
}

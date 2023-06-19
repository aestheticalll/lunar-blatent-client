package tactical.client.feature.module.impl.render.hud.element.impl;

import net.minecraft.client.gui.ScaledResolution;
import tactical.client.feature.module.impl.render.hud.element.Element;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Watermark extends Element {
    @Override
    public void render(ScaledResolution resolution) {
        mc.fontRendererObj.drawStringWithShadow(
                "Lunar Beta", 2.0f, 2.0f, -1);
    }
}

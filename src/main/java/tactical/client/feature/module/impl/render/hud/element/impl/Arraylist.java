package tactical.client.feature.module.impl.render.hud.element.impl;

import net.minecraft.client.gui.ScaledResolution;
import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.render.hud.element.Element;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.ModuleRegistry;
import tactical.client.utility.render.Colors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Arraylist extends Element {
    private static final double PADDING = 2.0;

    @Override
    public void render(ScaledResolution resolution) {
        ModuleRegistry modules = Registry.get(ModuleRegistry.class);
        List<Module> entries = new ArrayList<>(modules.entries())
                .stream()
                .filter((module) -> module.toggled() || module.animation().factor() > 0.0)
                // 10/10 code
                .sorted(Comparator.comparingInt(
                        (module) -> -mc.fontRendererObj.getStringWidth(module.key())))
                .toList();

        if (entries.isEmpty()) return;

        double posY = PADDING;
        for (int i = 0; i < entries.size(); ++i) {
            Module module = entries.get(i);
            String key = module.key();

            double factor = module.animation().factor();

            mc.fontRendererObj.drawStringWithShadow(key,
                    (float) (resolution.getScaledWidth_double()
                            - ((mc.fontRendererObj.getStringWidth(key) + PADDING) * factor)),
                    (float) posY, Colors.rainbow(i * 250, 3.5));

            posY += (mc.fontRendererObj.FONT_HEIGHT + PADDING) * factor;
        }
    }
}

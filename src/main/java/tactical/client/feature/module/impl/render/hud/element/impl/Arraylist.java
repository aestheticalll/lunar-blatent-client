package tactical.client.feature.module.impl.render.hud.element.impl;

import net.minecraft.client.gui.ScaledResolution;
import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.render.hud.element.Element;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.ModuleRegistry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Arraylist extends Element {
    @Override
    public void render(ScaledResolution resolution) {
        ModuleRegistry modules = Registry.get(ModuleRegistry.class);
        List<Module> entries = new ArrayList<>(modules.entries())
                .stream()
                .filter(Module::toggled)
                // 10/10 code
                .sorted(Comparator.comparingInt(
                        (module) -> -mc.fontRendererObj.getStringWidth(module.key())))
                .toList();

        if (entries.isEmpty()) return;

        double posY = 2.0;
        for (Module module : entries) {
            String key = module.key();

            mc.fontRendererObj.drawStringWithShadow(key,
                    (float) (resolution.getScaledWidth_double()
                            - mc.fontRendererObj.getStringWidth(key) - 2.0),
                    (float) posY, -1);

            posY += mc.fontRendererObj.FONT_HEIGHT + 2.0;
        }
    }
}

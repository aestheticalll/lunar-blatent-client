package tactical.client.feature.module.registry;

import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.combat.reach.ReachModule;
import tactical.client.feature.module.impl.combat.velocity.VelocityModule;
import tactical.client.feature.module.impl.render.clickgui.ClickGUIModule;
import tactical.client.feature.module.impl.render.hud.HUDModule;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ModuleRegistry extends Registry<Module> {
    @Override
    protected void init() {
        registerEntries(
                // Combat modules
                new ReachModule(),
                new VelocityModule(),

                // Render modules
                new ClickGUIModule(),
                new HUDModule());

        for (Module module : entries) module.reflectSettings();

        logger().info("Loaded {} modules",  size());
    }
}

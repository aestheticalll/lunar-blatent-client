package tactical.client.feature.module.registry;

import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.render.HUDModule;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ModuleRegistry extends Registry<Module> {
    @Override
    protected void init() {
        registerEntries(new HUDModule());

        logger().info("Loaded {} modules",  size());
    }
}

package tactical.client.feature.module.registry;

import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.combat.reach.ReachModule;
import tactical.client.feature.module.impl.combat.velocity.VelocityModule;
import tactical.client.feature.module.impl.combat.wtap.WTapModule;
import tactical.client.feature.module.impl.player.bridgeassist.BridgeAssistModule;
import tactical.client.feature.module.impl.player.doubleclicker.DoubleClickerModule;
import tactical.client.feature.module.impl.player.stealer.StealerModule;
import tactical.client.feature.module.impl.render.clickgui.ClickGUIModule;
import tactical.client.feature.module.impl.render.esp.ESPModule;
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
                new WTapModule(),

                // Player modules
                new BridgeAssistModule(),
                new DoubleClickerModule(),
                new StealerModule(),

                // Render modules
                new ClickGUIModule(),
                new ESPModule(),
                new HUDModule());

        for (Module module : entries) module.reflectSettings();

        logger().info("Loaded {} modules",  size());
    }
}

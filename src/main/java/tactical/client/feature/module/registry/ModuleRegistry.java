package tactical.client.feature.module.registry;

import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.combat.aimassist.AimAssistModule;
import tactical.client.feature.module.impl.combat.autoclicker.AutoClickerModule;
import tactical.client.feature.module.impl.combat.nohitdelay.NoHitDelayModule;
import tactical.client.feature.module.impl.combat.reach.ReachModule;
import tactical.client.feature.module.impl.combat.velocity.VelocityModule;
import tactical.client.feature.module.impl.combat.wtap.WTapModule;
import tactical.client.feature.module.impl.player.antivoid.AntiVoidModule;
import tactical.client.feature.module.impl.player.bridgeassist.BridgeAssistModule;
import tactical.client.feature.module.impl.player.doubleclicker.DoubleClickerModule;
import tactical.client.feature.module.impl.player.fastplace.FastPlaceModule;
import tactical.client.feature.module.impl.player.pingspoof.PingSpoofModule;
import tactical.client.feature.module.impl.player.stealer.StealerModule;
import tactical.client.feature.module.impl.render.clickgui.ClickGUIModule;
import tactical.client.feature.module.impl.render.esp.ESPModule;
import tactical.client.feature.module.impl.render.hud.HUDModule;
import tactical.client.feature.module.impl.render.nametags.NameTagsModule;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ModuleRegistry extends Registry<Module> {
    @Override
    protected void init() {
        registerEntries(
                // Combat modules
                new AimAssistModule(),
                new AutoClickerModule(),
                new NoHitDelayModule(),
                new ReachModule(),
                new VelocityModule(),
                new WTapModule(),

                // Player modules
                new AntiVoidModule(),
                new BridgeAssistModule(),
                new DoubleClickerModule(),
                new FastPlaceModule(),
                new PingSpoofModule(),
                new StealerModule(),

                // Render modules
                new ClickGUIModule(),
                new ESPModule(),
                new HUDModule(),
                new NameTagsModule());

        for (Module module : entries) module.reflectSettings();

        logger().info("Loaded {} modules", size());
    }
}

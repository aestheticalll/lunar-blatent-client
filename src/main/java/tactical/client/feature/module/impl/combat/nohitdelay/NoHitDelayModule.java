package tactical.client.feature.module.impl.combat.nohitdelay;

import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.player.EventUpdate;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "NoHitDelay", category = Category.COMBAT)
public class NoHitDelayModule extends Module {

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        mc.leftClickCounter = 0;
    };
}

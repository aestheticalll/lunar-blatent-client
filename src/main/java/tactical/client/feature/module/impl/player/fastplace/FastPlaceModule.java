package tactical.client.feature.module.impl.player.fastplace;

import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.player.EventUpdate;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "FastPlace", category = Category.PLAYER)
public class FastPlaceModule extends Module {
    private final Setting<Integer> speed = new Setting<>(
            "Speed", 3, 1, 0, 4);

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        mc.rightClickDelayTimer = 4 - speed.value();
    };
}

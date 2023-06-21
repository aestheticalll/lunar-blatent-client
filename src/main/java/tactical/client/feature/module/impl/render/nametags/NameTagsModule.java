package tactical.client.feature.module.impl.render.nametags;

import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.render.EventRender3D;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "NameTags", category = Category.RENDER)
public class NameTagsModule extends Module {

    @Subscribe
    private final Listener<EventRender3D> render3D = (event) -> {

    };
}

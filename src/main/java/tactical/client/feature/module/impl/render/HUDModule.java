package tactical.client.feature.module.impl.render;

import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.render.EventRender2D;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "HUD", category = Category.RENDER)
public class HUDModule extends Module {

    public HUDModule() {
        setState(true);
    }

    @Subscribe
    private final Listener<EventRender2D> render2D = (event) -> {
        mc.fontRendererObj.drawStringWithShadow("Lunar Beta", 2.0f, 2.0f, -1);
    };
}

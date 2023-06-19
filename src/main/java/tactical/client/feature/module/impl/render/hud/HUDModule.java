package tactical.client.feature.module.impl.render.hud;

import tactical.client.feature.module.impl.render.hud.element.Element;
import tactical.client.feature.module.impl.render.hud.element.impl.Arraylist;
import tactical.client.feature.module.impl.render.hud.element.impl.Watermark;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.render.EventRender2D;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "HUD", category = Category.RENDER)
public class HUDModule extends Module {

    private final List<Element> elementList = new LinkedList<>();

    public HUDModule() {
        setState(true);

        elementList.add(new Arraylist());
        elementList.add(new Watermark());
    }

    @Subscribe
    private final Listener<EventRender2D> render2D = (event) -> {
        for (Element element : elementList) {
            element.render(event.resolution());
        }
    };
}

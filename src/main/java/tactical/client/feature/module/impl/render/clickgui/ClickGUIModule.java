package tactical.client.feature.module.impl.render.clickgui;

import tactical.client.feature.module.gui.ClickGUIScreen;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "ClickGUI", category = Category.RENDER)
public class ClickGUIModule extends Module {

    private ClickGUIScreen screen;

    @Override
    public void enable() {
        super.enable();

        if (screen == null)
            screen = new ClickGUIScreen(this);

        mc.displayGuiScreen(screen);
    }
}

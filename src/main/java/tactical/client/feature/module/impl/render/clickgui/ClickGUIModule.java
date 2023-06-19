package tactical.client.feature.module.impl.render.clickgui;

import tactical.client.feature.module.gui.ClickGUIScreen;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;

import static org.lwjgl.input.Keyboard.KEY_RCONTROL;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "ClickGUI", category = Category.RENDER)
public class ClickGUIModule extends Module {

    /**
     * The {@link ClickGUIScreen} instance
     */
    private ClickGUIScreen screen;

    public ClickGUIModule() {
        // set default keybind
        keyBind().setKeyCode(KEY_RCONTROL);
    }

    @Override
    public void enable() {
        if (screen == null)
            screen = new ClickGUIScreen(this);

        mc.displayGuiScreen(screen);
    }

    @Override
    public void disable() {

    }
}

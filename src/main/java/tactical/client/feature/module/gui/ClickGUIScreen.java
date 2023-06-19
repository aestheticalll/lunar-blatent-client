package tactical.client.feature.module.gui;

import net.minecraft.client.gui.GuiScreen;
import tactical.client.feature.module.impl.render.clickgui.ClickGUIModule;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ClickGUIScreen extends GuiScreen {

    private final ClickGUIModule clickGui;

    public ClickGUIScreen(ClickGUIModule clickGuiModule) {
        clickGui = clickGuiModule;
    }

    @Override
    public void drawScreen(int i, int i1, float v) {
        drawDefaultBackground();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        clickGui.setState(false);
    }
}

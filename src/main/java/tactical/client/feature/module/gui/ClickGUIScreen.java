package tactical.client.feature.module.gui;

import net.minecraft.client.gui.GuiScreen;
import tactical.client.feature.Registry;
import tactical.client.feature.module.impl.render.clickgui.ClickGUIModule;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.ModuleRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ClickGUIScreen extends GuiScreen {

    private final ClickGUIModule clickGui;

    private final List<CategoryPanel> categoryPanels = new LinkedList<>();

    public ClickGUIScreen(ClickGUIModule clickGuiModule) {
        clickGui = clickGuiModule;

        double POS_X = 4.0,
                POS_Y = 20.0;

        for (Category category : Category.values()) {
            List<Module> moduleList = Registry.get(ModuleRegistry.class).entries()
                    .stream()
                    .filter((module) -> module.category() == category)
                    .toList();

            if (moduleList.isEmpty()) continue;

            CategoryPanel categoryPanel = new CategoryPanel(category, moduleList);
            categoryPanel.setX(POS_X);
            categoryPanel.setY(POS_Y);
            categoryPanel.setWidth(120.0);
            categoryPanel.setHeight(15.0);

            categoryPanels.add(categoryPanel);

            POS_X += categoryPanel.width() + 8.0;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (CategoryPanel categoryPanel : categoryPanels) categoryPanel.draw(
                mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (CategoryPanel categoryPanel : categoryPanels)
            categoryPanel.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        clickGui.setState(false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

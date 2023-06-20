package tactical.client.feature.module.gui;

import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.utility.render.Renders;

import java.awt.*;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class CategoryPanel extends Component {
    private static final Color BACKGROUND_COLOR = new Color(35, 35, 35);

    private static final double PADDING = 1.5;

    private final Category category;
    private boolean opened = true;

    public CategoryPanel(Category category, List<Module> moduleList) {
        this.category = category;

        for (Module module : moduleList) components().add(
                new ModuleButton(module));
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Renders.rect(x(), y(), width(), height(), BACKGROUND_COLOR.getRGB());

        String name = category.name();
        mc.fontRendererObj.drawStringWithShadow(name,
                (float) (x() + (width() / 2.0) - (mc.fontRendererObj.getStringWidth(name) / 2.0)),
                (float) (y() + PADDING + (super.height() / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)),
                -1);

        if (opened) {
            double POS_Y = y() + super.height() + PADDING;
            for (Component component : components()) {
                component.setX(x() + PADDING);
                component.setY(POS_Y);
                component.setWidth(width() - (PADDING * 2));
                component.setHeight(13.5);

                component.draw(mouseX, mouseY, partialTicks);

                POS_Y += component.height();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!opened) return;

        for (Component component : components())
            component.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyTyped(char charTyped, int keyCode) {

    }

    @Override
    public double height() {
        double height = super.height();
        if (opened) {
            height += PADDING;

            for (Component component : components())
                height += component.height();

            height += PADDING;
        }
        return height;
    }
}

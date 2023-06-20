package tactical.client.feature.module.gui;

import tactical.client.feature.module.gui.setting.BooleanComponent;
import tactical.client.feature.module.gui.setting.EnumComponent;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.setting.Setting;
import tactical.client.utility.render.Renders;

import java.awt.*;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ModuleButton extends Component {
    private static final Color TOGGLED_COLOR = new Color(118, 93, 194);
    private static final Color BG_COLOR = new Color(28, 28, 28);

    private static final double PADDING = 1.5;

    private final Module module;
    private boolean opened;

    public ModuleButton(Module module) {
        this.module = module;

        for (Setting<?> setting : module.settings()) {
            if (setting.value() instanceof Enum<?>) {
                components().add(new EnumComponent(
                        (Setting<Enum<?>>) setting));
            } else if (setting.value() instanceof Boolean) {
                components().add(new BooleanComponent(
                        (Setting<Boolean>) setting));
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

        if (module.toggled()) Renders.rect(x(), y(), width(), super.height(), TOGGLED_COLOR.getRGB());

        String key = module.key();
        mc.fontRendererObj.drawStringWithShadow(key,
                (float) (x() + (PADDING * 2)),
                (float) (y() + (super.height() / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)),
                -1);

        if (opened) {
            Renders.rect(x(), y() + super.height(), PADDING, height() - super.height(), TOGGLED_COLOR.getRGB());
            Renders.rect(x() + PADDING,
                    y() + super.height(),
                    width() - (PADDING * 2),
                    height() - super.height(),
                    BG_COLOR.getRGB());

            double POS_Y = y() + super.height();
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
        if (isInBounds(mouseX, mouseY, x(), y(), width(), super.height())) {
            if (button == 0) {
                module.setState(!module.toggled());
            } else if (button == 1) {
                opened = !opened;
            }
        }

        if (opened) {
            for (Component component : components()) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
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
        if (!opened) return height;

        for (Component component : components())
            height += component.height();
        return height;
    }
}

package tactical.client.feature.module.gui.setting;

import tactical.client.feature.module.gui.Component;
import tactical.client.feature.module.setting.Setting;
import tactical.client.utility.render.Renders;

import java.awt.*;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class BooleanComponent extends Component {
    private static final Color ENABLED_COLOR = new Color(93, 196, 93);
    private static final Color DISABLED_COLOR = new Color(196, 77, 77);
    private static final Color BUTTON_BG_COLOR = new Color(143, 135, 135);

    private static final double PADDING = 1.5;

    private final Setting<Boolean> setting;

    public BooleanComponent(Setting<Boolean> setting) {
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow(setting.key(),
                (float) (x() + (PADDING * 2)),
                (float) (y() + (height() / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)),
                -1);

        // TODO: rounded rects
        double sliderWidth = 15.0;
        double sliderHeight = height() - (PADDING * 3);

        double sliderX = x() + width() - sliderWidth - (PADDING * 2);

        Renders.rect(sliderX,
                y() + (PADDING * 1.5),
                sliderWidth,
                sliderHeight,
                (setting.value() ? ENABLED_COLOR : DISABLED_COLOR).getRGB());

        double buttonWidth = 5.0;
        double buttonHeight = sliderHeight - PADDING;
        // TODO: animations
        double x = sliderX + (setting.value() ? sliderWidth - (PADDING * 4) : PADDING);
        Renders.rect(x, y() + (PADDING * 2), buttonWidth, buttonHeight, BUTTON_BG_COLOR.getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isInBounds(mouseX, mouseY) && button == 0) {
            setting.setValue(!setting.value());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyTyped(char charTyped, int keyCode) {

    }
}

package tactical.client.feature.module.gui.setting;

import tactical.client.feature.module.gui.Component;
import tactical.client.feature.module.setting.Setting;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class EnumComponent extends Component {
    private static final double PADDING = 2.0;

    private final Setting<Enum<?>> setting;

    public EnumComponent(Setting<Enum<?>> setting) {
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow(setting.key(),
                (float) (x() + (PADDING * 2)),
                (float) (y() + (height() / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)),
                -1);

        String formatted = setting.value().toString();
        mc.fontRendererObj.drawStringWithShadow(formatted,
                (float) (x() + width() - (mc.fontRendererObj.getStringWidth(formatted) - (PADDING * 2))),
                (float) (y() + (height() / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)),
                -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyTyped(char charTyped, int keyCode) {

    }
}

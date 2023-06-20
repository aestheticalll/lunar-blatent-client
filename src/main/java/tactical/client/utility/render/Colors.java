package tactical.client.utility.render;

import java.awt.*;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Colors {

    /**
     * Creates a rainbow cycle
     * @param delay the delay from the initial current time
     * @param speed the speed to cycle through the rainbow
     * @return the hex color code for the rainbow cycle
     */
    public static int rainbow(int delay, double speed) {
        double hue = (((System.currentTimeMillis() + 10) + delay) * (speed / 100.0)) % 360.0;
        return Color.HSBtoRGB((float) hue / 360.0f, 1.0f, 1.0f);
    }
}

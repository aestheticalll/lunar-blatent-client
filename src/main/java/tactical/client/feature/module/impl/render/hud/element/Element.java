package tactical.client.feature.module.impl.render.hud.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author Gavin
 * @since 1.0.0
 */
public abstract class Element {

    /**
     * The {@link Minecraft} game instance
     */
    protected final Minecraft mc = Minecraft.getMinecraft();

    private double x, y, width, height;

    /**
     * Renders this {@link Element}
     * @param resolution the {@link ScaledResolution} object from a {@link tactical.client.listener.event.render.EventRender2D}
     */
    public abstract void render(ScaledResolution resolution);

    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double width() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double height() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

package tactical.client.feature.module.gui;

import net.minecraft.client.Minecraft;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public abstract class Component {

    /**
     * The {@link Minecraft} game instace
     */
    protected final Minecraft mc = Minecraft.getMinecraft();

    private double x, y, width, height;
    private final List<Component> components = new LinkedList<>();

    public abstract void draw(int mouseX, int mouseY, float partialTicks);
    public abstract void mouseClicked(int mouseX, int mouseY, int button);
    public abstract void mouseReleased(int mouseX, int mouseY, int button);
    public abstract void keyTyped(char charTyped, int keyCode);

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

    public List<Component> components() {
        return components;
    }

    public boolean isInBounds(int mouseX, int mouseY) {
        return isInBounds(mouseX, mouseY, x, y, width, height);
    }

    public static boolean isInBounds(int mouseX, int mouseY, double x, double y, double w, double h) {
        return mouseX >= x && x + w >= mouseX && mouseY >= y && y + h >= mouseY;
    }
}

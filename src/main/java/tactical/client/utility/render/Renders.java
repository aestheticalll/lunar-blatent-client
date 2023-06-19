package tactical.client.utility.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.GL_QUADS;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Renders {

    /**
     * The {@link Minecraft} game instance
     */
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Gets the ARGB values of a hex color
     * @param color the color hex
     * @return a float[] of 4 elements in the format of ARGB
     */
    public static float[] color(int color) {
        float alpha = (color >> 24 & 0xff) / 255.0f;
        float red = (color >> 16 & 0xff) / 255.0f;
        float green = (color >> 8 & 0xff) / 255.0f;
        float blue = (color & 0xff) / 255.0f;

        return new float[] { alpha, red, green, blue };
    }

    public static void rect(double x, double y, double w, double h, int color) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getWorldRenderer();

        float alpha = (color >> 24 & 0xff) / 255.0f;
        float red = (color >> 16 & 0xff) / 255.0f;
        float green = (color >> 8 & 0xff) / 255.0f;
        float blue = (color & 0xff) / 255.0f;

        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x, y + h, 0.0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + w, y + h, 0.0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + w, y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

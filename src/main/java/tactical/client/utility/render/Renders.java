package tactical.client.utility.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Renders {

    /**
     * The {@link Minecraft} game instance
     */
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static FloatBuffer modelMatrix;
    private static FloatBuffer projMatrix;
    private static IntBuffer viewport;

    /**
     * Updates the modelMatrix, projectionMatrix, and the viewport
     */
    public static void updateProjection() {
        if (modelMatrix == null || projMatrix == null || viewport == null) {
            modelMatrix = BufferUtils.createFloatBuffer(16);
            projMatrix = BufferUtils.createFloatBuffer(16);
            viewport = BufferUtils.createIntBuffer(16);
        }

        glGetFloat(GL_MODELVIEW_MATRIX, modelMatrix);
        glGetFloat(GL_PROJECTION_MATRIX, projMatrix);
        glGetInteger(GL_VIEWPORT, viewport);
    }

    /**
     * Converts the x, y, z coordinates from the 3D space into a 2D plane
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return the resulting xyz coordinates
     */
    public static float[] project(double x, double y, double z) {
        FloatBuffer winPos = BufferUtils.createFloatBuffer(3);
        GLU.gluProject((float) x, (float) y, (float) z, modelMatrix, projMatrix, viewport, winPos);
        return new float[] { winPos.get(0), Display.getHeight() - winPos.get(1), winPos.get(2) };
    }

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


    public static void line(double x, double y, double x1, double y1, float lineWidth, int color) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getWorldRenderer();

        float alpha = (color >> 24 & 0xff) / 255.0f;
        float red = (color >> 16 & 0xff) / 255.0f;
        float green = (color >> 8 & 0xff) / 255.0f;
        float blue = (color & 0xff) / 255.0f;

        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glLineWidth(lineWidth);

        buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x1, y1, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        glLineWidth(1.0f);
        glDisable(GL_LINE_SMOOTH);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

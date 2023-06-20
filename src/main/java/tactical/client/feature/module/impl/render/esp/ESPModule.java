package tactical.client.feature.module.impl.render.esp;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.render.EventRender2D;
import tactical.client.listener.event.render.EventRender3D;
import tactical.client.utility.chat.Printer;
import tactical.client.utility.render.Renders;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "ESP", category = Category.RENDER)
public class ESPModule extends Module {

    private final Map<Integer, float[][]> projectionCoordinates = new ConcurrentHashMap<>();

    @Subscribe
    private final Listener<EventRender2D> render2D = (event) -> {
        if (mc.thePlayer.ticksExisted < 5) {
            projectionCoordinates.clear();
            return;
        }

        for (int id : projectionCoordinates.keySet()) {
            Entity entity = mc.theWorld.getEntityByID(id);
            if (!(entity instanceof EntityPlayer player)
                    || player.equals(mc.thePlayer)
                    || player.isDead) continue;

            int color = 0xFFFFFFFF;
            if (player.getTeam() != null) color = 0xFF000000 | teamColor(
                    player.getDisplayName().getFormattedText());

            float[][] projection = projectionCoordinates.get(id);
            if (projection == null) continue;

            float[] top = projection[0];
            float[] bottom = projection[1];

            if (top[2] >= 0.0f && top[2] < 1.0f && bottom[2] >= 0.0f && bottom[2] < 1.0f) {

                glPushMatrix();
                glScaled(0.5, 0.5, 0.5);

                glDisable(GL_LIGHTING);
                glDisable(GL_TEXTURE_2D);

                Renders.line(top[0], top[1], top[0] + (bottom[0] - top[0]), top[1], 1.5f, color);
                Renders.line(bottom[0], top[1], bottom[0], bottom[1], 1.5f, color);
                Renders.line(top[0], top[1], top[0], bottom[1], 1.5f, color);
                Renders.line(bottom[0], bottom[1], bottom[0] + (top[0] - bottom[0]), bottom[1], 1.5f, color);

                glEnable(GL_TEXTURE_2D);

                glPopMatrix();
            }
        }
    };

    @Subscribe
    private final Listener<EventRender3D> render3D = (event) -> {
        double viewerX = mc.renderManager.renderPosX;
        double viewerY = mc.renderManager.renderPosY;
        double viewerZ = mc.renderManager.renderPosZ;

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == null || player.equals(mc.thePlayer) || player.isDead) {
                if (player != null) projectionCoordinates.remove(player.getEntityId());
                continue;
            }

            double x = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks();
            double y = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks();
            double z = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks();

            AxisAlignedBB box = player.getEntityBoundingBox();

            float[] top = Renders.project(
                    (box.maxX - player.posX) + x - viewerX,
                    ((box.maxY - player.posY) + y + 0.33) - viewerY,
                    (box.minZ - player.posZ) + z - viewerZ);
            float[] bottom = Renders.project(
                    (box.minX - player.posX) + x - viewerX,
                    ((box.minY - player.posY) + y - 0.33) - viewerY,
                    (box.maxZ - player.posZ) + z - viewerZ);

            projectionCoordinates.put(player.getEntityId(), new float[][]{top, bottom});
        }
    };

    private int teamColor(String display) {
        char[] chars = display.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char at = chars[i];
            if (at == '\u00A7') {
                int index = "0123456789abcdef".indexOf(chars[i + 1]);
                if (index != -1) return mc.fontRendererObj.colorCode[index];
            }
        }

        return 0xFFFFFFFF;
    }
}

package tactical.client.feature.module.impl.combat.aimassist;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.feature.module.setting.custom.Range;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.render.EventRender3D;
import tactical.client.utility.math.MathUtils;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "AimAssist", category = Category.COMBAT)
public class AimAssistModule extends Module {
    private final Setting<Range<Float>> speed = new Setting<>("Speed",
            new Range<>(75.0f, 85.0f, 0.01f, 1.0f, 100.0f));
    private final Setting<Boolean> onlyOnClick = new Setting<>("OnlyOnClick", true);
    private final Setting<Float> fov = new Setting<>(
            "FoV", 180.0f, 0.01f, 1.0f, 360.0f);
    private final Setting<Double> range = new Setting<>(
            "Range", 4.0, 0.01, 1.0, 10.0);

    @Subscribe
    private final Listener<EventRender3D> render3D = (event) -> {
        if (onlyOnClick.value() && !mc.gameSettings.keyBindAttack.pressed) return;

        EntityPlayer target = null;
        double compared = -1.0;

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == null
                    || player.equals(mc.thePlayer)
                    || player.isDead
                    || player.getHealth() <= 0.0f) continue;

            double crosshairDistance = distanceFromCrosshair(
                    event.partialTicks(), player);
            if (crosshairDistance > range.value() * range.value()) continue;

            if (compared == -1.0 || compared > crosshairDistance) {
                target = player;
                compared = crosshairDistance;
            }
        }

        if (target == null) return;

        double difference = angleDifference(calcYawTo(target));

        // uh oh stinky
        if (Math.abs(difference) > fov.value()) return;

        Range<Float> range = speed.value();
        float delta = (float) (-difference / MathUtils.random(range.minValue(), range.maxValue()));

        mc.thePlayer.rotationYaw += delta;
        mc.thePlayer.rotationYawHead += delta;
    };

    private double angleDifference(float to) {
        return ((mc.thePlayer.rotationYaw - to) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    private float calcYawTo(EntityPlayer player) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(1.0f);
        Vec3 vec = player.getPositionVector().addVector(0.0, player.getEyeHeight(), 0.0);

        double diffX = vec.xCoord - eyes.xCoord;
        double diffZ = vec.zCoord - eyes.zCoord;

        return (float) -(Math.toDegrees(Math.atan2(diffX, diffZ)));
    }

    private double distanceFromCrosshair(float partialTicks, EntityPlayer player) {
        // wizard shit!!!!

        double reach = mc.playerController.extendedReach() ? 6.0 : 3.0;

        Vec3 eyes = mc.thePlayer.getPositionEyes(partialTicks);
        Vec3 look = mc.thePlayer.getLook(partialTicks);
        Vec3 vec = eyes.addVector(
                look.xCoord * reach,
                look.yCoord * reach,
                look.zCoord * reach);

        float borderSize = player.getCollisionBorderSize();
        AxisAlignedBB box = player.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
        MovingObjectPosition objectPosition = box.calculateIntercept(eyes, vec);
        Vec3 v = objectPosition != null && objectPosition.hitVec != null ? objectPosition.hitVec : vec;

        return v.squareDistanceTo(player.getPositionEyes(partialTicks));
    }
}

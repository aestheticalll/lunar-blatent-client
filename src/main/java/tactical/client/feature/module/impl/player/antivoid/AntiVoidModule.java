package tactical.client.feature.module.impl.player.antivoid;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.player.EventUpdate;

/**
 * @author Gavin
 * @since 06/20/23
 */
@Register(value = "AntiVoid", category = Category.PLAYER)
public class AntiVoidModule extends Module {
    private final Setting<AntiVoidMode> mode = new Setting<>(
            "Mode", AntiVoidMode.NCP);
    private final Setting<Float> fallDistance = new Setting<>(
            "FallDistance", 4.0f, 0.01f, 3.0f, 20.0f);
    private final Setting<Boolean> voidCheck = new Setting<>(
            "VoidCheck", true);

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        if (mc.thePlayer.fallDistance < fallDistance.value()
                || (voidCheck.value() && !overVoid())) return;

        mc.thePlayer.fallDistance = 0.0f;
        switch (mode.value()) {
            case NCP -> mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ, false));

            case JUMP -> mc.thePlayer.jump();
        }
    };

    private boolean overVoid() {
        double x = mc.thePlayer.posX + (mc.thePlayer.motionX * 1.5);
        double z = mc.thePlayer.posZ + (mc.thePlayer.motionZ * 1.5);

        for (double y = mc.thePlayer.posY; y >= 0.0; y--) {
           if (!mc.theWorld.isAirBlock(new BlockPos(x, y, z))) return false;
        }

        return true;
    }
}

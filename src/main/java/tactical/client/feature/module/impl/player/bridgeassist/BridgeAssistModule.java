package tactical.client.feature.module.impl.player.bridgeassist;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.player.EventUpdate;

import static org.lwjgl.input.Keyboard.KEY_R;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "BridgeAssist", category = Category.PLAYER)
public class BridgeAssistModule extends Module {

    private final Setting<Boolean> holdingBlock = new Setting<>(
            "HoldingBlock", true);
    private final Setting<Boolean> lookingAtBlock = new Setting<>(
            "LookingAtBlock", true);

    private boolean sneakOverride;

    public BridgeAssistModule() {
        keyBind().setKeyCode(KEY_R);
    }

    @Override
    public void disable() {
        super.disable();
        if (sneakOverride) sneak(false);
    }

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        if (!airBelow()) {
            if (sneakOverride) sneak(false);
            return;
        }

        if (holdingBlock.value()) {
            ItemStack itemStack = mc.thePlayer.getHeldItem();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock)) return;
        }

        if (lookingAtBlock.value()) {
            MovingObjectPosition result = mc.objectMouseOver;
            if (result == null || result.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        }

        sneak(true);
    };

    /**
     * Checks if there is air below you
     * @return if the block below you is air
     */
    private boolean airBelow() {
        BlockPos pos = new BlockPos(mc.thePlayer.getPositionVector()).down();
        return mc.theWorld.isAirBlock(pos);
    }

    /**
     * Sets sneaking controls
     * @param value the sneak value
     */
    private void sneak(boolean value) {
        mc.gameSettings.keyBindSneak.pressed = value;
        sneakOverride = value;
    }
}

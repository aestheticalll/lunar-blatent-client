package tactical.client.feature.module.impl.player.stealer;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.feature.module.setting.custom.Range;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.player.EventUpdate;
import tactical.client.utility.math.MathUtils;
import tactical.client.utility.math.Timer;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static org.lwjgl.input.Keyboard.KEY_GRAVE;
import static tactical.client.utility.item.ItemUtils.scoreItemStack;

/**
 * @author Gavin
 * @since 1.0.
 */
@Register(value = "Stealer", category = Category.PLAYER)
public class StealerModule extends Module {

    private final Setting<Range<Double>> delay = new Setting<>(
            "StealDelay",
            new Range<>(150.0, 250.0, 0.1, 0.0, 1000.0));
    private final Setting<Boolean> noJunk = new Setting<>("NoJunk", true);
    private final Setting<Boolean> nameCheck = new Setting<>("NameCheck", true);
    private final Setting<Boolean> autoClose = new Setting<>("AutoClose", false);

    private final Timer timer = new Timer();
    private double calculatedDelay = NaN;

    public StealerModule() {
        keyBind().setKeyCode(KEY_GRAVE);
    }

    @Override
    public void disable() {
        super.disable();
        calculatedDelay = NaN;
    }

    @Subscribe
    private final Listener<EventUpdate> update = (event) -> {
        if (!(mc.thePlayer.openContainer instanceof ContainerChest container)) return;

        IInventory inventory = container.getLowerChestInventory();
        if (!validChestInventory(inventory) && nameCheck.value()) return;

        if (isNaN(calculatedDelay) || timer.passed((long) calculatedDelay, false)) {

            for (int slotId = 0; slotId < inventory.getSizeInventory(); ++slotId) {
                Slot slot = container.getSlot(slotId);
                if (!slot.getHasStack() || (noJunk.value() && junk(slot.getStack()))) continue;

                mc.playerController.windowClick(container.windowId,
                        slotId, 0, 1, mc.thePlayer);

                calculatedDelay = MathUtils.random(
                        delay.value().minValue(), delay.value().maxValue());
                return;
            }
        }

    };

    private boolean junk(ItemStack itemStack) {
        if (itemStack == null) return true;

        double score = scoreItemStack(itemStack);

        if (itemStack.getItem() instanceof ItemArmor itemArmor) {
            int armorIndex = 3 - itemArmor.armorType;

            ItemStack equipped = mc.thePlayer.inventory.armorInventory[armorIndex];
            if (equipped != null && equipped.getItem() instanceof ItemArmor) {
                double equippedScore = scoreItemStack(equipped);
                return score < equippedScore;
            }
        }

        return false; // for now
    }

    private boolean validChestInventory(IInventory inventory) {
        // TODO: check via minecraft game translations...
        String unformatted = inventory.getDisplayName().getUnformattedText();
        return unformatted.equals("Chest") || unformatted.equals("Large Chest");
    }
}

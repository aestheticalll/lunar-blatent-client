package tactical.client.utility.item;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;

import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ItemUtils {

    private static final List<Enchantment> PROTECTION_ENCHANTS = Lists.newArrayList(
            Enchantment.protection, Enchantment.blastProtection,
            Enchantment.projectileProtection, Enchantment.fireProtection);

    private static final List<Enchantment> TOOL_ENCHANTS = Lists.newArrayList(
            Enchantment.unbreaking, Enchantment.fortune, Enchantment.unbreaking);

    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * "Scores" an {@link ItemStack}
     * @param itemStack the {@link ItemStack}
     * @return the "score" for this stack
     */
    public static double scoreItemStack(ItemStack itemStack) {

        // i know this isnt the best code but there is not really a better way to do this
        // other than creating a new method for each item type... which seems worse

        if (itemStack.getItem() instanceof ItemArmor itemArmor) {
            int score = itemArmor.damageReduceAmount;

            for (Enchantment enchantment : PROTECTION_ENCHANTS) {
                score += EnchantmentHelper.getEnchantmentLevel(
                        enchantment.effectId, itemStack);
            }

            return score;
        } else if (itemStack.getItem() instanceof ItemSword itemSword) {
            double score = itemSword.attackDamage;

            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.sharpness.effectId, itemStack) * 1.2;
            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.fireAspect.effectId, itemStack) * 1.1;
            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.unbreaking.effectId, itemStack) * 1.5;

            return score;
        } else if (itemStack.getItem() instanceof ItemTool itemTool) {
            double score = 0.0;

            if (itemStack.getItem() instanceof ItemPickaxe) {
                score = itemTool.getStrVsBlock(itemStack, Blocks.stone);
            } else if (itemStack.getItem() instanceof ItemSpade) {
                score = itemTool.getStrVsBlock(itemStack, Blocks.grass);
            } else if (itemStack.getItem() instanceof ItemAxe) {
                score = itemTool.getStrVsBlock(itemStack, Blocks.log);
            }

            for (Enchantment enchantment : TOOL_ENCHANTS) {
                score += EnchantmentHelper.getEnchantmentLevel(
                        enchantment.effectId, itemStack);
            }

            return score;
        } else if (itemStack.getItem() instanceof ItemHoe itemHoe) { // i love how mc doesnt consider a hoe a tool
            double score = itemHoe.theToolMaterial.efficiencyOnProperMaterial;
            score *= itemHoe.getStrVsBlock(itemStack, Blocks.hay_block);

            for (Enchantment enchantment : TOOL_ENCHANTS) {
                score += EnchantmentHelper.getEnchantmentLevel(
                        enchantment.effectId, itemStack);
            }

            return score;
        } else if (itemStack.getItem() instanceof ItemBow) {
            double score = 0.0;

            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.knockback.effectId, itemStack) * 1.2;
            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.punch.effectId, itemStack) * 1.5;
            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.flame.effectId, itemStack) * 1.3;
            score += EnchantmentHelper.getEnchantmentLevel(
                    Enchantment.unbreaking.effectId, itemStack) * 1.5;

            return score;
        }

        return 0.0;
    }
}

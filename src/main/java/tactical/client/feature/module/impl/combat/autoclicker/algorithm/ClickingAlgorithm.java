package tactical.client.feature.module.impl.combat.autoclicker.algorithm;

import net.minecraft.client.Minecraft;
import tactical.client.feature.module.setting.custom.Range;
import tactical.client.utility.math.Timer;

/**
 * @author Gavin
 * @since 1.0.0
 */
public interface ClickingAlgorithm {

    /**
     * Called on when the players key attack is down
     * @param timer the timer used for calculating via time
     * @param cpsRange the range of CPS
     * @return if to call {@link Minecraft#clickMouse()}
     */
    boolean click(Timer timer, Range<Double> cpsRange);
}

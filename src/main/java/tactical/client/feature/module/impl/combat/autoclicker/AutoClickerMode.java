package tactical.client.feature.module.impl.combat.autoclicker;

import tactical.client.feature.module.impl.combat.autoclicker.algorithm.BasicClickingAlgorithm;
import tactical.client.feature.module.impl.combat.autoclicker.algorithm.ClickingAlgorithm;
import tactical.client.feature.module.setting.custom.Range;
import tactical.client.utility.math.Timer;

/**
 * @author Gavin
 * @since 06/20/23
 */
public enum AutoClickerMode {
    BASIC(new BasicClickingAlgorithm());

    private final ClickingAlgorithm clickingAlgorithm;

    AutoClickerMode(ClickingAlgorithm clickingAlgorithm) {
        this.clickingAlgorithm = clickingAlgorithm;
    }

    /**
     * Clicks with the {@link ClickingAlgorithm} clicking algorithm
     * @param timer the timer for measuring time
     * @param cpsRange the range setting for clicking
     */
    public boolean click(Timer timer, Range<Double> cpsRange) {
        return clickingAlgorithm.click(timer, cpsRange);
    }
}

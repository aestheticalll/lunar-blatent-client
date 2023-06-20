package tactical.client.feature.module.impl.combat.autoclicker.algorithm;

import tactical.client.feature.module.setting.custom.Range;
import tactical.client.utility.math.MathUtils;
import tactical.client.utility.math.Timer;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class BasicClickingAlgorithm implements ClickingAlgorithm {

    @Override
    public boolean click(Timer timer, Range<Double> cpsRange) {
        return timer.passed(
                (long) (1000.0 / MathUtils.random(
                        cpsRange.minValue(), cpsRange.maxValue())),
                true);
    }
}

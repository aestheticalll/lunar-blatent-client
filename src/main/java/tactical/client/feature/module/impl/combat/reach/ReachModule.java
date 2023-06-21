package tactical.client.feature.module.impl.combat.reach;

import tactical.client.feature.module.registry.Category;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.module.setting.Setting;
import tactical.client.feature.module.setting.custom.Range;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.input.EventAttackReach;
import tactical.client.utility.math.MathUtils;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(value = "Reach", category = Category.COMBAT)
public class ReachModule extends Module {
    private final Setting<Range<Double>> attack = new Setting<>("Attack",
            new Range<>(3.03, 3.4, 0.01, 3.0, 6.0));
    private final Setting<Double> chance = new Setting<>(
            "Chance", 80.0, 0.1, 1.0, 100.0);

    @Subscribe
    private final Listener<EventAttackReach> attackReach = (event) -> {
        if (!MathUtils.chance(chance.value())) return;
        Range<Double> range = attack.value();
        event.setReach(MathUtils.random(range.minValue(), range.maxValue()));
    };
}

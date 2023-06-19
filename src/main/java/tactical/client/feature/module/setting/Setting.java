package tactical.client.feature.module.setting;

import tactical.client.feature.trait.Feature;

/**
 * @author Gavin
 * @since 1.0.0
 * @param <T> the type of value this {@link Setting} holds
 */
public class Setting<T> implements Feature {

    private final String key;
    private T value;

    private final T scale, min, max;

    public Setting(String key, T value) {
        this(key, value, null, null, null);
    }

    public Setting(String key, T value, T scale, T min, T max) {
        this.key = key;
        this.value = value;
        this.scale = scale;
        this.min = min;
        this.max = max;
    }

    @Override
    public String key() {
        return null;
    }

    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T scale() {
        return scale;
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }
}

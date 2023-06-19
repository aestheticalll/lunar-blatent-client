package tactical.client.feature.module.setting.custom;

/**
 * @author Gavin
 * @since 1.0.0
 * @param <T> the {@link Number} type
 */
public class Range<T extends Number> {

    private T valueMin, valueMax;
    private final T scale, min, max;

    public Range(T valueMin, T valueMax, T scale, T min, T max) {
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.scale = scale;
        this.min = min;
        this.max = max;
    }

    public T minValue() {
        return valueMin;
    }

    public void setValueMin(T valueMin) {
        this.valueMin = valueMin;
    }

    public T maxValue() {
        return valueMax;
    }

    public void setValueMax(T valueMax) {
        this.valueMax = valueMax;
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

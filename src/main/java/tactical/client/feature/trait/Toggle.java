package tactical.client.feature.trait;

/**
 * @author Gavin
 * @since 1.0.0
 */
public interface Toggle {

    void enable();
    void disable();

    void setState(boolean state);
    boolean toggled();
}

package tactical.client.bind;

/**
 * @author Gavin
 * @since 1.0.0
 */
@FunctionalInterface
public interface Inhibitor {

    /**
     * Called when a {@link KeyBind} has its state changed
     * @param bindState the current {@link KeyBind} state
     */
    void invoke(boolean bindState);
}

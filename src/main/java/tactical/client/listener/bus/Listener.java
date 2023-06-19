package tactical.client.listener.bus;

/**
 * @author Gavin
 * @since 06/11/23
 */
@FunctionalInterface
public interface Listener<T> {
    void invoke(T event);
}

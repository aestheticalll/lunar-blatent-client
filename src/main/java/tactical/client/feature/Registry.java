package tactical.client.feature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @author Gavin
 * @since 1.0.0
 * @param <T> the type this registry holds
 */
public abstract class Registry<T> {

    /**
     * A map containing each registry and its instance
     */
    private static final Map<Class<? extends Registry>, Registry<?>> registryMap = new HashMap<>();

    /**
     * A map containing the class value and its instance
     */
    protected final Map<Class<? extends T>, T> classInstanceMap = new LinkedHashMap<>();

    /**
     * A list containing each object instance of T
     */
    protected final List<T> entries = new LinkedList<>();

    /**
     * This {@link Registry} logger
     */
    private Logger logger;

    /**
     * Initializes this {@link Registry}
     */
    protected abstract void init();

    /**
     * Gets the logger for this {@link Registry}
     * @return the {@link Logger} for this {@link Registry}
     */
    protected Logger logger() {
        if (logger == null) logger = LogManager.getLogger(
                    getClass().getSimpleName());
        return logger;
    }

    /**
     * Registries multiple entries into this registry
     * @param entries varargs of entries to put into this registry
     */
    protected void registerEntries(T... entries) {
        for (T entry : entries) {
            classInstanceMap.put((Class<? extends T>) entry.getClass(), entry);
            this.entries.add(entry);
        }
    }

    /**
     * Gets an entry
     * @param clazz the class
     * @return the entry
     * @param <T> the value of the entry
     */
    public <T> T getEntry(Class<T> clazz) {
        return (T) registryMap.getOrDefault(clazz, null);
    }

    /**
     * Gets the list of entries
     * @return a list of T
     */
    public List<T> entries() {
        return entries;
    }

    /**
     * Gets the size of this {@link Registry}
     * @return the amount of entries the {@link Map} for this {@link Registry} has
     */
    public int size() {
        return registryMap.size();
    }

    /**
     * Gets a registry
     * @param clazz the registry class
     * @return the registry singleton
     * @param <T> the type of registry class
     */
    public static <T extends Registry> T get(Class<T> clazz) {
        return (T) registryMap.getOrDefault(clazz, null);
    }

    /**
     * Registers a registry
     * @param registry the {@link Registry} instance
     */
    public static void register(Registry<?> registry) {
        if (!registryMap.containsKey(registry.getClass())) {
            registryMap.put(registry.getClass(), registry);
            registry.init();
        }
    }
}

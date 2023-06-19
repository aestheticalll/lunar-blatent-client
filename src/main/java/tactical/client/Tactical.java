package tactical.client;

import net.weavemc.loader.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tactical.client.feature.Registry;
import tactical.client.feature.module.registry.ModuleRegistry;
import tactical.client.listener.bus.EventBus;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.start.EventStartMinecraft;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Tactical implements ModInitializer {

    /**
     * Our client version
     */
    private static final String version = "1.0.0";

    /**
     * Our {@link EventBus}
     */
    private static final EventBus bus = new EventBus();

    /**
     * Our client {@link Logger}
     */
    private static final Logger logger
            = LogManager.getLogger("Tactical");

    @Override
    public void preInit() {
        logger.info("Starting Tactical {}", version);
        bus.subscribe(this);
    }

    @Subscribe
    private final Listener<EventStartMinecraft> startMinecraft = (event) -> {
        Registry.register(new ModuleRegistry());
        logger.info("Fully initialized Tactical {}", version);
    };

    public static EventBus bus() {
        return bus;
    }
}

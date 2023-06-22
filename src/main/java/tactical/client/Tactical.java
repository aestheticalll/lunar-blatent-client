package tactical.client;

import net.weavemc.loader.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tactical.client.bind.BindRegistry;
import tactical.client.config.Configs;
import tactical.client.feature.Registry;
import tactical.client.feature.command.registry.CommandRegistry;
import tactical.client.feature.module.registry.ModuleRegistry;
import tactical.client.listener.bus.EventBus;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.start.EventStartMinecraft;

import java.io.File;

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

    /**
     * The directory for the save data of Tactical
     */
    private static File directory;
    public static boolean firstLaunch;

    @Override
    public void preInit() {
        logger.info("Starting Tactical {}", version);
        bus.subscribe(this);

        directory = new File(System.getProperty("user.home"), "tactical");
        if (!directory.exists()) {
            firstLaunch = directory.mkdir();
            logger.info("Created {} {}",
                    directory,
                    firstLaunch ? "successfully" : "unsuccessfully");
        }
    }

    @Subscribe
    private final Listener<EventStartMinecraft> startMinecraft = (event) -> {
        Configs.init();
        Registry.register(new BindRegistry());
        Registry.register(new ModuleRegistry());
        Registry.register(new CommandRegistry());
        logger.info("Fully initialized Tactical {}", version);
    };

    public static EventBus bus() {
        return bus;
    }

    /**
     * The directory for the save data of Tactical
     * @return the {@link File} object
     */
    public static File directory() {
        return directory;
    }
}

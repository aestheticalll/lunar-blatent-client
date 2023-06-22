package tactical.client.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Configs {

    private static final Logger logger = LogManager.getLogger(
            "Configs");

    private static final List<Config> configList = new LinkedList<>();

    /**
     * Initializes the {@link Config} object
     * (not really cause its all static but u know what i mean)
     */
    public static void init() {
        Runtime.getRuntime().addShutdownHook(
                new ConfigShutdownHookThread());
    }

    public static void add(Config config) {
        configList.add(config);
    }

    public static Logger logger() {
        return logger;
    }

    public static List<Config> get() {
        return configList;
    }
}

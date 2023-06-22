package tactical.client.config;

import tactical.client.utility.math.Timer;

import java.util.List;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ConfigShutdownHookThread extends Thread {

    public ConfigShutdownHookThread() {
        setName("Config Shutdown Thread");
    }

    @Override
    public void run() {
        List<Config> configs = Configs.get();
        if (configs.isEmpty()) {
            Configs.logger().info("No configs to save.");
            return;
        }

        for (Config config : configs) {
            try {
                Timer timer = new Timer();
                config.save();
                long elapsed = timer.elapsedMs();
                Configs.logger().info(
                        "Saved {} in {}ms", config.file(), elapsed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

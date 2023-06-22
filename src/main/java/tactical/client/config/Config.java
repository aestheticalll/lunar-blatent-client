package tactical.client.config;

import java.io.File;

/**
 * @author Gavin
 * @since 1.0.0
 */
public abstract class Config {
    private final File file;

    public Config(File file) {
        this.file = file;
    }

    /**
     * Saves this config
     */
    public abstract void save();

    /**
     * Loads this config
     */
    public abstract void load();

    public File file() {
        return file;
    }
}

package tactical.client.feature.module.registry.config;

import com.google.gson.JsonObject;
import tactical.client.Tactical;
import tactical.client.feature.module.registry.ModuleRegistry;
import tactical.client.utility.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class ModuleConfigurations {

    /**
     * The configs folder
     */
    private final File moduleConfigs = new File(
            Tactical.directory(), "configs");

    /**
     * The file extension
     */
    private final String extension = ".tactial";

    private final ModuleRegistry registry;

    public ModuleConfigurations(ModuleRegistry registry) {
        this.registry = registry;

        if (!moduleConfigs.exists()) moduleConfigs.mkdir();
    }

    /**
     * Saves a module configuration
     * @param name the config name
     * @return the plaintext result
     */
    public String save(String name) {
        if (!name.endsWith(extension)) name += extension;
        File file = new File(moduleConfigs, name);
        if (!file.exists()) {
            try {
                if (!file.createNewFile())
                    return "Could not create file \"" + file + "\"";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to create config file \"" + file + "\"";
            }
        }

        JsonObject object = new JsonObject();

        // TODO

        try {
            FileUtils.write(file, FileUtils.gson.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to write to config \"" + file + "\"";
        }

        return "Saved config to \"" + file + "\"";
    }

    /**
     * Loads a module configuration
     * @param name the config name
     * @return the plaintext result
     */
    public String load(String name) {
        if (!name.endsWith(extension)) name += extension;
        File file = new File(moduleConfigs, name);
        if (!file.exists())
            return "Config with \"" + file + "\" does not exist";

        String content;
        try {
            content = FileUtils.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read \"" + file + "\"";
        }

        if (content.isEmpty()) return "Failed to read \"" + file + "\"";

        JsonObject object = FileUtils.jsonParser
                .parse(content).getAsJsonObject();

        // TODO

        return "Loaded config \"" + file + "\"";
    }

    public File moduleConfigs() {
        return moduleConfigs;
    }

    public String extension() {
        return extension;
    }
}

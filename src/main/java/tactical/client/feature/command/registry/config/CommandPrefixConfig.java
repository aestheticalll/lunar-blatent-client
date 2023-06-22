package tactical.client.feature.command.registry.config;

import tactical.client.Tactical;
import tactical.client.config.Config;
import tactical.client.feature.command.registry.CommandRegistry;
import tactical.client.utility.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class CommandPrefixConfig extends Config {
    private final CommandRegistry registry;

    public CommandPrefixConfig(CommandRegistry registry) {
        super(new File(Tactical.directory(), "prefix.txt"));
        this.registry = registry;
    }

    @Override
    public void save() {
        try {
            FileUtils.write(file(), registry.getPrefix());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        String content;
        try {
            content = FileUtils.read(file());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (content.isEmpty()) return;

        // this may not be good to just do .charAt but...
        registry.setPrefix(String.valueOf(content.charAt(0)));
    }
}

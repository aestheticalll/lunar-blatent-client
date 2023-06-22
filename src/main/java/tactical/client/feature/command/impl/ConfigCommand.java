package tactical.client.feature.command.impl;

import tactical.client.feature.Registry;
import tactical.client.feature.command.exception.CommandInvalidArgumentException;
import tactical.client.feature.command.exception.CommandSyntaxException;
import tactical.client.feature.command.registry.Command;
import tactical.client.feature.command.registry.annotation.Register;
import tactical.client.feature.module.registry.ModuleRegistry;
import tactical.client.feature.module.registry.config.ModuleConfigurations;
import tactical.client.utility.chat.Printer;

import java.io.File;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(aliases = {"config", "cfg", "moduleconfig", "mconfig"}, syntax = "[save|load|delete] [config name]")
public class ConfigCommand extends Command {
    @Override
    public void dispatch(String[] args)
            throws CommandSyntaxException,
            CommandInvalidArgumentException {

        if (args.length < 2) throw new CommandSyntaxException();

        ModuleConfigurations moduleConfigurations = Registry.get(ModuleRegistry.class)
                .moduleConfigurations();

        switch (args[0].toLowerCase()) {
            case "save" -> Printer.chat(moduleConfigurations.save(args[1]));

            case "load" -> Printer.chat(moduleConfigurations.load(args[1]));

            case "delete" -> {
                String fileName = args[1];
                if (!fileName.endsWith(moduleConfigurations.extension()))
                    fileName += moduleConfigurations.extension();
                File file = new File(moduleConfigurations.moduleConfigs(), fileName);
                if (file.exists()) {
                    boolean result = file.delete();
                    if (result) {
                        Printer.chat("Deleted config \"" + file + "\"");

                    } else {
                        Printer.chat("Failed to delete config \"" + args[1] + "\"");
                    }
                } else {
                    Printer.chat("No config was found with name \"" + args[1] + "\"");
                }
            }

            default -> throw new CommandInvalidArgumentException(
                    "action", "\"" + args[0] + "\" did not match save, load, or delete");
        }
    }
}

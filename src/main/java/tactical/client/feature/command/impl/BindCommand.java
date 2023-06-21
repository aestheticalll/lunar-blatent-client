package tactical.client.feature.command.impl;

import org.lwjgl.input.Keyboard;
import tactical.client.feature.Registry;
import tactical.client.feature.command.exception.CommandInvalidArgumentException;
import tactical.client.feature.command.exception.CommandSyntaxException;
import tactical.client.feature.command.registry.Command;
import tactical.client.feature.command.registry.annotation.Register;
import tactical.client.feature.module.registry.Module;
import tactical.client.feature.module.registry.ModuleRegistry;
import tactical.client.utility.chat.Printer;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Register(aliases = {"bind", "setbind", "keybind", "binding"},
        syntax = "[module] [key]")
public class BindCommand extends Command {
    @Override
    public void dispatch(String[] args)
            throws CommandSyntaxException,
            CommandInvalidArgumentException {

        if (args.length < 2) throw new CommandSyntaxException();

        Module parsedModule = null;
        for (Module module : Registry.get(ModuleRegistry.class).entries()) {
            if (args[0].equalsIgnoreCase(module.key())) {
                parsedModule = module;
                break;
            }
        }

        if (parsedModule == null) throw new CommandInvalidArgumentException(
                "module", "Module with name \"" + args[0] + "\" does not exist.");

        int keyCode = Keyboard.getKeyIndex(args[1].toUpperCase());

        parsedModule.keyBind().setKeyCode(keyCode);
        Printer.chat("Set &5"
                + parsedModule.key()
                + "&7's key bind to &5"
                + Keyboard.getKeyName(keyCode));
    }

    @Override
    public String suggest(String argument, int index) {
        if (index == 0) {
            for (Module module : Registry.get(ModuleRegistry.class).entries()) {
                if (module.key().toLowerCase().startsWith(argument.toLowerCase()))
                    return module.key();
            }
        }

        return super.suggest(argument, index);
    }
}

package tactical.client.feature.command.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.C01PacketChatMessage;
import tactical.client.Tactical;
import tactical.client.feature.Registry;
import tactical.client.feature.command.exception.CommandInvalidArgumentException;
import tactical.client.feature.command.exception.CommandSyntaxException;
import tactical.client.feature.command.impl.BindCommand;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.input.EventKeyInput;
import tactical.client.listener.event.network.EventPacket;
import tactical.client.utility.chat.Printer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.input.Keyboard.KEY_TAB;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class CommandRegistry extends Registry<Command> {
    /**
     * The {@link Minecraft} game instance
     */
    private final Minecraft mc = Minecraft.getMinecraft();

    private String prefix = "-";

    private final Map<String, Command> commandAliasMap = new HashMap<>();

    @Override
    protected void init() {
        registerEntries(new BindCommand());

        Tactical.bus().subscribe(this);
        logger().info("Loaded {} commands", size());
    }

    @Override
    protected void registerEntries(Command... entries) {
        for (Command command : entries) {
            classInstanceMap.put(command.getClass(), command);
            for (String alias : command.aliases()) {
                commandAliasMap.put(alias, command);
            }
        }
    }

    @Subscribe
    private final Listener<EventPacket.Outbound> packetOutbound = (event) -> {
        if (event.get() instanceof C01PacketChatMessage packet) {
            String message = packet.getMessage();
            if (!message.startsWith(prefix)) return;

            event.setCanceled(true);

            String[] args = message.substring(prefix.length())
                    .trim().split("\\s+");

            if (args.length == 0) {
                Printer.chat("Invalid command. Run &5" + prefix + "help&7 for commands");
                return;
            }

            Command command = commandAliasMap.getOrDefault(
                    args[0].toLowerCase(), null);
            if (command == null) {
                Printer.chat("Unrecognized command \"&5"
                        + args[0]
                        + "&7\" Run &5"
                        + prefix
                        + "help&7 for commands");
                return;
            }

            try {
                command.dispatch(Arrays.copyOfRange(
                        args, 1, args.length));
            } catch (Exception e) {
                if (e instanceof CommandInvalidArgumentException exception) {
                    Printer.chat("Invalid value for argument \"&5"
                            + exception.argumentName()
                            + "&7\": "
                            + exception.message());
                } else if (e instanceof CommandSyntaxException) {
                    Printer.chat("Syntax: &5" + command.syntax());
                } else {
                    Printer.chat("Failed to execute command. Check logs and report to the developer.");
                    e.printStackTrace();
                }
            }
        }
    };

    @Subscribe
    private final Listener<EventKeyInput> keyInput = (event) -> {
        if (event.keyCode() != KEY_TAB
                || !(mc.currentScreen instanceof GuiChat guiChat)) return;

        String text = guiChat.inputField.getText();
        if (!text.startsWith(prefix)) return;

        String[] args = text.substring(prefix.length())
                .trim().split("\\s+");

        int lastIndex = args.length - 1;
        if (lastIndex == -1) return;

        if (lastIndex == 0) {
            String prediction = null;
            for (String alias : commandAliasMap.keySet()) {
                if (alias.startsWith(args[0].toLowerCase())) {
                    prediction = alias;
                    break;
                }
            }

            if (prediction != null) guiChat.inputField.setText(
                    prefix + prediction + " ");
        } else {
            Command command = commandAliasMap.getOrDefault(
                    args[0].toLowerCase(), null);
            if (command == null) return;

            String arg = args[lastIndex];
            String suggested = command.suggest(arg, lastIndex - 1);
            if (suggested != null) guiChat.inputField.setText(
                    text.replace(arg, suggested));
        }
    };

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

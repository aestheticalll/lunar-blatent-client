package tactical.client.utility.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Gavin
 * @since 06/19/23
 */
public class Printer {

    /**
     * The {@link Minecraft} game instance
     */
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * The section character (§) used for minecraft color codes
     * @see net.minecraft.util.EnumChatFormatting
     */
    private static final String SECTION_CHARACTER = "\u00A7";

    /**
     * Prints text to chat
     * @param text the text
     */
    public static void chat(String text) {
        mc.ingameGUI.getChatGUI().printChatMessage(
                new ChatComponentText(replaceColorCodes("&dtactical"))
                        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY))
                        .appendText(replaceColorCodes(" &7> "))
                        .appendText(replaceColorCodes(text)));
    }

    /**
     * Replaces all & with a §
     * @param text the text
     * @return the parsed text
     */
    public static String replaceColorCodes(String text) {
        return text.replaceAll("&", SECTION_CHARACTER);
    }

    /**
     * Prints something to sysout
     * @param s the content
     */
    public static void sysout(String s) {
        System.out.println("[T] " + s);
    }
}

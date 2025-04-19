package ltd.rymc.wss.chat.utils;

import org.bukkit.ChatColor;

public class ChatColorUtils {

    public static String translatedFormat(char altColorChar, String format, Object... args) {
        return String.format(ChatColor.translateAlternateColorCodes(altColorChar, format), args);
    }

}

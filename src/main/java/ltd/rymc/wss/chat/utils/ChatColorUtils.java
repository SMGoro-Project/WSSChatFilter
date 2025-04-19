package ltd.rymc.wss.chat.utils;

import org.bukkit.ChatColor;

import java.util.Formatter;

public class ChatColorUtils {

    public static String translatedFormat(char altColorChar, String format, Object... args) {
        return String.format(ChatColor.translateAlternateColorCodes(altColorChar, format), args);
    }
}

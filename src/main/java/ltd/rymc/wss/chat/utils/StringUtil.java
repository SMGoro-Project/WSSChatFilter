package ltd.rymc.wss.chat.utils;

public class StringUtil {
    public static boolean isSingleCharString(String s) {
        if (s == null || s.isEmpty()) return false;
        char firstChar = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static String extractCodeBlock(String content) {
        content = content.trim();

        if (content.startsWith("```")) {
            int firstLineEnd = content.indexOf("\n");
            if (firstLineEnd != -1 && content.length() > firstLineEnd + 1) {
                String stripped = content.substring(firstLineEnd + 1).trim();

                if (stripped.endsWith("```")) {
                    stripped = stripped.substring(0, stripped.length() - 3).trim();
                }

                return stripped;
            }
        }
        return content;
    }
}

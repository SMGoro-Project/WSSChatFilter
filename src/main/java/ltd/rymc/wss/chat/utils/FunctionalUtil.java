package ltd.rymc.wss.chat.utils;

import java.util.function.Supplier;

public class FunctionalUtil {

    @SuppressWarnings("StatementWithEmptyBody")
    public static <T> T retry(Supplier<T> supplier, int maxAttempts) {
        T result = null;
        for (int i = 0; i < maxAttempts && (result = supplier.get()) == null; i++);
        return result;
    }

}

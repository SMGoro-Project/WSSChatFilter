package ltd.rymc.wss.chat.utils;

import ltd.rymc.wss.chat.core.FileLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class ResourceUtil {

    public static <T> T readResource(FileLoader loader, String path, ReaderFunction<T> function) throws IOException {
        try (InputStream in = loader.getResource(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
        ) {
            return function.apply(reader);
        }
    }

    @FunctionalInterface
    public interface ReaderFunction<T> {
        T apply(BufferedReader reader) throws IOException;
    }

}

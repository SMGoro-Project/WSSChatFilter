package ltd.rymc.io;

import ltd.rymc.function.throwable.ThrowableConsumer;
import ltd.rymc.function.throwable.ThrowableFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class IOProcessor {

    public static <P, T> T bufferedRead(
            P path,
            ThrowableFunction<P, InputStream, IOException> loader,
            ThrowableFunction<BufferedReader, T, IOException> function
    ) throws IOException {
        return read(path, loader,
                in -> new InputStreamReader(in, StandardCharsets.UTF_8),
                reader -> {
                    return function.apply(new BufferedReader(reader));
                }
        );
    }

    public static <P> void bufferedRead(
            P path,
            ThrowableFunction<P, InputStream, IOException> loader,
            ThrowableConsumer<BufferedReader, IOException> function
    ) throws IOException {
        read(path, loader,
                in -> new InputStreamReader(in, StandardCharsets.UTF_8),
                reader -> {
                    function.accept(new BufferedReader(reader));
                }
        );
    }

    public static <P, T, Q extends InputStream, R extends Reader> T read(
            P path,
            ThrowableFunction<P, Q, IOException> loader,
            ThrowableFunction<Q, R, IOException> readerWrapper,
            ThrowableFunction<R, T, IOException> function
    ) throws IOException {
        return streamRead(path, loader, in -> {
            try (R reader = readerWrapper.apply(in)) {
                return function.apply(reader);
            }
        });
    }

    public static <P, Q extends InputStream, R extends Reader> void read(
            P path,
            ThrowableFunction<P, Q, IOException> loader,
            ThrowableFunction<Q, R, IOException> readerWrapper,
            ThrowableConsumer<R, IOException> function
    ) throws IOException {
        streamRead(path, loader, in -> {
            try (R reader = readerWrapper.apply(in)) {
                function.accept(reader);
            }
        });
    }

    public static <P, T, Q extends InputStream> T streamRead(
            P path,
            ThrowableFunction<P, Q, IOException> loader,
            ThrowableFunction<Q, T, IOException> function
    ) throws IOException {
        try (Q in = loader.apply(path)) {
            return function.apply(in);
        }
    }

    public static <P, Q extends InputStream> void streamRead(
            P path,
            ThrowableFunction<P, Q, IOException> loader,
            ThrowableConsumer<Q, IOException> function
    ) throws IOException {
        try (Q in = loader.apply(path)) {
            function.accept(in);
        }
    }

}

package ltd.rymc.wss.chat.core;

import java.io.InputStream;

public interface FileLoader {
    InputStream getResource(String path);
}

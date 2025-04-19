package ltd.rymc.wss.chat.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ModelResponseFailedException extends RuntimeException {

    private final Type type;

    public enum Type {
        JSON_PARSE,
        API_FAILED
    }

}

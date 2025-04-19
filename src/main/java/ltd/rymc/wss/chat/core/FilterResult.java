package ltd.rymc.wss.chat.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FilterResult {

    public static final FilterResult UNSAFE = new FilterResult(Result.UNSAFE, 100, null);
    public static final FilterResult SAFE = new FilterResult(Result.SAFE, 100, null);

    @Getter
    private final Result result;
    @Getter
    private final double confidence;

    private final String reason;

    public Optional<String> getReason() {
        return reason == null || reason.trim().isEmpty() ? Optional.empty() : Optional.of(reason);
    }

    public boolean isUnsafeResult(double lowestConfidence) {
        if (result == Result.SAFE) {
            return false;
        }

        if (result == Result.UNSAFE) {
            return true;
        }

        return confidence < lowestConfidence;
    }

    public enum Result {
        UNSAFE,
        SAFE,
        UNKNOWN
    }

}

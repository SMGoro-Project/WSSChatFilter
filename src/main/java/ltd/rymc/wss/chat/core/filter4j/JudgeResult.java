package ltd.rymc.wss.chat.core.filter4j;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JudgeResult extends Exception {

    private final int result;

}

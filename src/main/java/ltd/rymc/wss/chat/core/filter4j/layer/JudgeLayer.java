package ltd.rymc.wss.chat.core.filter4j.layer;

import lombok.RequiredArgsConstructor;
import ltd.rymc.wss.chat.core.filter4j.JudgeResult;

@RequiredArgsConstructor
public class JudgeLayer extends Layer {

    private final int inputCount;

    @Override
    public double[] forward(double[] input) throws JudgeResult {
        int index = 0;
        for (int i = 1; i < inputCount; i++) {
            if (input[i] > input[index]) {
                index = i;
            }
        }
        throw new JudgeResult(index);
    }
}

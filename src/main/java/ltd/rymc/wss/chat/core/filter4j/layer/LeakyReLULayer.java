package ltd.rymc.wss.chat.core.filter4j.layer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LeakyReLULayer extends Layer {

    private final int inputCount;

    @Override
    public double[] forward(double[] input) {
        for (int i = 0; i < inputCount; i++) {
            if (input[i] <= 0) {
                input[i] *= 0.01;
            }
        }
        return input;
    }
}

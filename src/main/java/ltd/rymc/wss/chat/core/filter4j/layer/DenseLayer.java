package ltd.rymc.wss.chat.core.filter4j.layer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DenseLayer extends Layer {

    private final int inputCount;
    private final int outputCount;
    private final double[][] weights;

    @Override
    public double[] forward(double[] input) {
        double[] output = new double[outputCount];
        for (int i = 0; i < outputCount; i++) {
            double sum = 0;
            for (int j = 0; j < inputCount; j++) {
                sum += input[j] * weights[j][i];
            }
            output[i] = sum;
        }
        return output;
    }
}

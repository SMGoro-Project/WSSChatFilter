package ltd.rymc.wss.chat.core.filter4j.layer;

import ltd.rymc.wss.chat.core.filter4j.JudgeResult;

public abstract class Layer {

    public abstract double[] forward(double[] input) throws JudgeResult;
}

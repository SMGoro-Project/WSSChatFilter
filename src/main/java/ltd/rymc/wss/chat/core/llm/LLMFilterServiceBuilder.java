package ltd.rymc.wss.chat.core.llm;

import com.zhipu.oapi.ClientV4;

import java.util.List;

public class LLMFilterServiceBuilder {

    private final String apiKey;
    private String prompt = LLMConstant.PROMPT;
    private String model = LLMConstant.MODEL;
    private float temperature = LLMConstant.TEMPERATURE;
    private int maxAttempts = LLMConstant.MAX_ATTEMPTS;

    public LLMFilterServiceBuilder(String apiKey) {
        this.apiKey = apiKey;
    }

    public LLMFilterServiceBuilder setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public LLMFilterServiceBuilder setPrompt(List<String> prompt) {
        StringBuilder builder = new StringBuilder();
        for (String string : prompt) {
            builder.append(string).append("\n");
        }
        this.prompt = builder.toString();
        return this;
    }

    public LLMFilterServiceBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public LLMFilterServiceBuilder setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public LLMFilterServiceBuilder setTemperature(double temperature) {
        this.temperature = (float) temperature;
        return this;
    }

    public LLMFilterServiceBuilder setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    public LLMFilterService build() {
        return new LLMFilterService(new ClientV4.Builder(apiKey).build(), prompt, model, temperature, maxAttempts);
    }

}

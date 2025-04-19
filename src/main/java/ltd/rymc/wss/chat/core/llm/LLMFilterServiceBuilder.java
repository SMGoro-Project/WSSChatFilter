package ltd.rymc.wss.chat.core.llm;

import com.zhipu.oapi.ClientV4;

public class LLMFilterServiceBuilder {

    private String prompt = "Please act as a content security auditor and determine if the messages sent by the following players contain any of the following:\n" +
                            "1. abusive (e.g. insulting words, personal attacks, etc.)\n" +
                            "2. Pornographic (e.g. sexually suggestive, explicit terms, vulgar content, etc.)\n" +
                            "3. illegal (e.g. threats of violence, politically sensitive, drugs, scams, etc.)\n" +
                            "4. Malicious attempts to evade detection using techniques such as character substitution, homophones, obfuscation, puns, or indirect references, especially in Chinese\n" +
                            "and **strictly** returns a JSON object in the following format:\n" +
                            "{\n" +
                            "  \"label\": \"SAFE|UNSAFE|UNKNOWN\",\n" +
                            "  \"confidence\": Number[0-100]\n" +
                            "  \"reason\": string[No required if SAFE]\n" +
                            "}\n" +
                            "**NOTE**: Server players mainly use Chinese and English\n" +
                            "Player message: \"%s\"";

    private String model = "GLM-4-FlashX";
    private float temperature = 0.0f;
    private int maxAttempts = 3;

    private final String apiKey;

    public LLMFilterServiceBuilder(String apiKey){
        this.apiKey = apiKey;
    }

    public LLMFilterServiceBuilder setPrompt(String prompt) {
        this.prompt = prompt;
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

    public LLMFilterServiceBuilder setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    public LLMFilterService build() {
        return new LLMFilterService(new ClientV4.Builder(apiKey).build(), prompt, model, temperature, maxAttempts);
    }
}

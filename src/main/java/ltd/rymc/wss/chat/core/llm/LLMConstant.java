package ltd.rymc.wss.chat.core.llm;

public class LLMConstant {

    private LLMConstant() {
    }

    public static final String PROMPT = "Please act as a content security auditor and determine if the messages sent by the following players contain any of the following:\n" +
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
    public static final String MODEL = "GLM-4-FlashX";
    public static final float TEMPERATURE = 0.0f;
    public static final int MAX_ATTEMPTS = 3;

}

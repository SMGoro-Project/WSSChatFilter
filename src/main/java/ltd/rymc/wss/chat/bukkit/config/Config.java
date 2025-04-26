package ltd.rymc.wss.chat.bukkit.config;

import ltd.rymc.wss.chat.core.llm.LLMConstant;
import net.elytrium.java.commons.config.YamlConfig;

public class Config extends YamlConfig {

    @Ignore
    public static final Config IMP = new Config();

    public double UNKNOWN_THRESHOLD = 30.0d;
    public boolean LOG_JSON_PARSE_ERROR = true;
    public boolean FILTER4J = true;

    @Create
    public LLM LLM;
    @Create
    public RATE_LIMITER RATE_LIMITER;

    public static class LLM {
        public String API_KEY = "";
        public int API_MAX_ATTEMPTS = LLMConstant.MAX_ATTEMPTS;
        public String MODEL = LLMConstant.MODEL;
        public double MODEL_TEMPERATURE = LLMConstant.TEMPERATURE;
    }

    public static class RATE_LIMITER {
        public long COOMDOWN_MILIS = 3000L;
    }

}

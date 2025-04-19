package ltd.rymc.wss.chat.bukkit.config;

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
        public int API_MAX_ATTEMPTS = 3;
        public String MODEL = "GLM-4-FlashX";
        public double MODEL_TEMPERATURE = 0.0d;
    }

    public static class RATE_LIMITER {
        public long COOMDOWN_MILIS = 3000L;
    }

}

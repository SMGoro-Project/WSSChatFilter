package ltd.rymc.wss.chat.bukkit.config;

import net.elytrium.java.commons.config.YamlConfig;

public class Messages extends YamlConfig {

    @Ignore
    public static final Messages IMP = new Messages();

    public String FILTED_CHAT_FORMAT = "&7<%s> %s [Filtered]";
    public String FILTER_REASON = "&7Filter Reason: %s";
    public String NO_API_KEY = "You have not entered the API KEY, the plugin will not be available, go to https://www.bigmodel.cn/ registration to get the API KEY";
    public String JSON_PARSE_ERROR = "The LLM returned an invalid JSON, which might have been caused by a faulty prompt. Of course, this can also happen occasionally under normal circumstances.";
    public String API_FAILED_ERROR = "LLM Request failed. Please check whether your API key is valid.";
    public String DISABLE_ERROR_MESSAGES = "You can disable this message by setting log-json-parse-error to false in the configuration file.";
    public String PLUGIN_RELOAD = "Plugin reloaded.";

}

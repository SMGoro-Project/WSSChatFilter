package ltd.rymc.wss.chat.bukkit;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import ltd.rymc.wss.chat.bukkit.commands.ChatFilterCommand;
import ltd.rymc.wss.chat.bukkit.config.Config;
import ltd.rymc.wss.chat.bukkit.config.Messages;
import ltd.rymc.wss.chat.bukkit.listener.ChatFilter;
import ltd.rymc.wss.chat.bukkit.listener.ChatRateLimiter;
import ltd.rymc.wss.chat.core.FileLoader;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.core.filter4j.Filter4JService;
import ltd.rymc.wss.chat.core.llm.LLMFilterService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class WSSChatFilter extends JavaPlugin implements FileLoader {

    @Getter
    private static WSSChatFilter instance;
    private FilterService filter4jService;
    private FilterService llmFilterService;

    @Override
    public void onEnable() {
        instance = this;
        reload();

        new PaperCommandManager(this).registerCommand(new ChatFilterCommand());
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new ChatRateLimiter(), this);
        manager.registerEvents(new ChatFilter(), this);
    }

    public void reload() {
        Config.IMP.reload(new File(getDataFolder(), "config.yml"));
        Messages.IMP.reload(new File(getDataFolder(), "messages.yml"));
        filter4jService = new Filter4JService(this);

        Config.LLM llm = Config.IMP.LLM;
        if (llm.API_KEY.isEmpty()) {
            llmFilterService = null;
            getLogger().severe(Messages.IMP.NO_API_KEY);
            return;
        }

        llmFilterService = LLMFilterService.builder(llm.API_KEY)
                .setPrompt(llm.PROMPT)
                .setModel(llm.MODEL)
                .setTemperature(llm.MODEL_TEMPERATURE)
                .setMaxAttempts(llm.API_MAX_ATTEMPTS).build();

    }

}

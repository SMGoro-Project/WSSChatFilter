package ltd.rymc.wss.chat.bukkit;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import ltd.rymc.wss.chat.bukkit.commands.ChatFilterCommand;
import ltd.rymc.wss.chat.bukkit.config.Config;
import ltd.rymc.wss.chat.bukkit.config.Messages;
import ltd.rymc.wss.chat.bukkit.listener.ChatFilter;
import ltd.rymc.wss.chat.bukkit.listener.ChatRateLimiter;
import ltd.rymc.wss.chat.core.llm.LLMConstant;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.core.filter4j.Filter4JService;
import ltd.rymc.wss.chat.core.llm.LLMFilterService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;

@Getter
public final class WSSChatFilter extends JavaPlugin {

    @Getter
    private static WSSChatFilter instance;
    private FilterService filter4jService;
    private FilterService llmFilterService;
    private String prompt;

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
        prompt = readPrompt();
        filter4jService = new Filter4JService(this::getResource);

        Config.LLM llm = Config.IMP.LLM;
        if (llm.API_KEY == null || llm.API_KEY.isEmpty()) {
            llmFilterService = null;
            getLogger().severe(Messages.IMP.NO_API_KEY);
            return;
        }

        llmFilterService = LLMFilterService.builder(llm.API_KEY)
                .setPrompt(prompt)
                .setModel(llm.MODEL)
                .setTemperature(llm.MODEL_TEMPERATURE)
                .setMaxAttempts(llm.API_MAX_ATTEMPTS).build();

    }

    private String readPrompt() {
        File file = new File(getDataFolder(), "prompt.txt");
        String defaultValue = LLMConstant.PROMPT;

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(defaultValue);
                }
            }

            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Prompt read failed", e);
            return defaultValue;
        }
    }

}

package ltd.rymc.wss.chat.bukkit.listener;

import lombok.RequiredArgsConstructor;
import ltd.rymc.wss.chat.bukkit.WSSChatFilter;
import ltd.rymc.wss.chat.bukkit.config.Config;
import ltd.rymc.wss.chat.bukkit.config.Messages;
import ltd.rymc.wss.chat.core.FilterResult;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.core.exception.ModelResponseFailedException;
import ltd.rymc.wss.chat.utils.ChatColorUtils;
import ltd.rymc.wss.chat.utils.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class ChatFilter implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();

        if (message.length() <= 1) return;
        if (StringUtil.isSingleCharString(message)) return;

        FilterService filter4jService = WSSChatFilter.getInstance().getFilter4jService();
        FilterService llmFilterService = WSSChatFilter.getInstance().getLlmFilterService();
        if (llmFilterService == null) return;

        if (Config.IMP.FILTER4J) {
            boolean unsafeResult = filter4jService.filter(message).isUnsafeResult(100.0);
            if (!unsafeResult) {
                return;
            }
        }

        try {
            FilterResult result = llmFilterService.filter(message);

            if (result.isUnsafeResult(Config.IMP.UNKNOWN_THRESHOLD)) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.sendMessage(ChatColorUtils.translatedFormat('&', Messages.IMP.FILTED_CHAT_FORMAT, player.getName(), message));
                result.getReason().ifPresent(
                        (reason) -> player.sendMessage(ChatColorUtils.translatedFormat('&', Messages.IMP.FILTER_REASON, reason))
                );
            }

        } catch (ModelResponseFailedException exception) {
            if (Config.IMP.LOG_JSON_PARSE_ERROR) {
                Logger logger = WSSChatFilter.getInstance().getLogger();
                Messages messages = Messages.IMP;
                switch (exception.getType()) {
                    case JSON_PARSE:
                        logger.warning(messages.JSON_PARSE_ERROR);
                        break;
                    case API_FAILED:
                        logger.warning(messages.API_FAILED_ERROR);
                }
                logger.warning(messages.DISABLE_ERROR_MESSAGES);
            }
        }

    }

}

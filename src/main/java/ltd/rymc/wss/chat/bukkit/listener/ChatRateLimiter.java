package ltd.rymc.wss.chat.bukkit.listener;

import ltd.rymc.wss.chat.bukkit.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRateLimiter implements Listener {

    private final Map<UUID, Long> lastChatTimestamps = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        long cooldownMillis = Config.IMP.RATE_LIMITER.COOMDOWN_MILIS;
        if (cooldownMillis <= 0) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();

        long lastTime = lastChatTimestamps.getOrDefault(uuid, 0L);

        if (now - lastTime < cooldownMillis) {
            event.setCancelled(true);
            long wait = (cooldownMillis - (now - lastTime)) / 1000;
            player.sendMessage(ChatColor.RED + String.format("You are speaking too often, please wait for %s seconds and try again.", wait));
        } else {
            lastChatTimestamps.put(uuid, now);
        }
    }

}

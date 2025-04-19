package ltd.rymc.wss.chat.bukkit.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import ltd.rymc.wss.chat.bukkit.WSSChatFilter;
import ltd.rymc.wss.chat.bukkit.config.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias("filter")
@Description("Chat filter command")
@SuppressWarnings("unused")
public class ChatFilterCommand extends BaseCommand {

    @Default
    public void base(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "WSS Chat Filter ver 1.0.0 by RENaa_FD");
        sender.sendMessage(ChatColor.GRAY + "- Filtering Chat with Filter4J + ChatGLM");
        sender.sendMessage(ChatColor.GRAY + "- See source code on: https://github.com/SMGoro-Project/WSSChatFilter");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "Source code under the MIT LICENSE - Copyright (c) 2025 lRENyaaa");
    }

    @Subcommand("reload")
    @CommandPermission("chatfilter.reload")
    public void reload(CommandSender sender) {
        WSSChatFilter.getInstance().reload();
        sender.sendMessage(Messages.IMP.PLUGIN_RELOAD);
    }

}

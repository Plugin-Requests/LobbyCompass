package net.savagedev.lc.commands.subcommands;

import net.savagedev.lc.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class HelpCmd implements SubCommand {
    @Override
    public void execute(CommandSender user, String[] args) {
        MessageUtils.message(user, "&8/&7lc open [name] &8|&7 Open the lobby compass for a player.");
        MessageUtils.message(user, "&8/&7lc help &8|&7 Display this help page.");
        MessageUtils.message(user, "&8/&7lc reload &8|&7 Reload the plugin.");
    }

    @Override
    public String getPermission() {
        return "lobbycompass.help";
    }
}

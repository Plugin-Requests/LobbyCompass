package net.savagedev.lc.commands.subcommands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    void execute(CommandSender sender, String[] args);

    String getPermission();
}

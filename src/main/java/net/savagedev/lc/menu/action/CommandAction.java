package net.savagedev.lc.menu.action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandAction implements ClickAction {
    private final String command;

    private final SenderType senderType;

    public CommandAction(final String command, SenderType senderType) {
        this.command = command;
        this.senderType = senderType;
    }

    @Override
    public void onClick(Player player) {
        if (senderType == SenderType.CONSOLE) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command.replace("%player%", player.getName()));
        } else {
            Bukkit.dispatchCommand(player, this.command);
        }
    }

    public enum SenderType {
        CONSOLE,
        PLAYER
    }
}

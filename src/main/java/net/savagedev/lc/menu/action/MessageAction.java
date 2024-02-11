package net.savagedev.lc.menu.action;

import net.savagedev.lc.utils.MessageUtils;
import org.bukkit.entity.Player;

public class MessageAction implements ClickAction {
    private final String message;

    public MessageAction(final String message) {
        this.message = message;
    }

    @Override
    public void onClick(Player player) {
        MessageUtils.message(player, this.message);
    }
}

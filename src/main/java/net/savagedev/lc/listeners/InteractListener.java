package net.savagedev.lc.listeners;

import net.savagedev.lc.LobbyCompass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {
    private final LobbyCompass plugin;

    public InteractListener(final LobbyCompass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractE(final PlayerInteractEvent event) {
        final Action action = event.getAction();

        if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final Player user = event.getPlayer();

        if (user.getInventory().getItemInMainHand().isSimilar(this.plugin.getCompassItem())) {
            this.plugin.getLobbyCompass().open(user);
        }
    }
}

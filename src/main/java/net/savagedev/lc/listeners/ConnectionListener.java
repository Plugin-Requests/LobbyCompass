package net.savagedev.lc.listeners;

import net.savagedev.lc.LobbyCompass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {
    private final LobbyCompass plugin;

    public ConnectionListener(final LobbyCompass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinE(final PlayerJoinEvent event) {
        this.plugin.giveCompassItem(event.getPlayer());
    }
}

package net.savagedev.lc.listeners;

import net.savagedev.lc.LobbyCompass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldListener implements Listener {
    private final LobbyCompass plugin;

    public WorldListener(final LobbyCompass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldChangeE(final PlayerChangedWorldEvent event) {
        this.plugin.giveCompassItem(event.getPlayer());
    }
}

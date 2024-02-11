package net.savagedev.lc.menu.action;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class TeleportAction implements ClickAction {
    private final World world;

    public TeleportAction(final World world) {
        this.world = world;
    }

    @Override
    public void onClick(Player player) {
        player.teleport(this.world.getSpawnLocation());
    }
}

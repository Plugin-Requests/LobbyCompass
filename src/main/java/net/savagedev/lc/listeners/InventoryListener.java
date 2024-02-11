package net.savagedev.lc.listeners;

import net.savagedev.lc.menu.MenuHolder;
import net.savagedev.lc.menu.action.ClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;
import java.util.Set;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickE(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        final Inventory inventory = event.getClickedInventory();

        if (inventory == null) {
            return;
        }

        final InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof MenuHolder)) {
            return;
        }

        event.setCancelled(true);

        final Set<ClickAction> actions = ((MenuHolder) holder).menu().getSlot(event.getSlot()).getActions();
        for (ClickAction action : actions) {
            action.onClick((Player) event.getWhoClicked());
        }
    }
}

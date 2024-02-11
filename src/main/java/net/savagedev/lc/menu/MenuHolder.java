package net.savagedev.lc.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;

public record MenuHolder(Menu menu) implements InventoryHolder {
    @Override @Nonnull
    public Inventory getInventory() {
        return this.menu.getInventory();
    }
}

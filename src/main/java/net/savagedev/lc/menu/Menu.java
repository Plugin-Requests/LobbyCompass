package net.savagedev.lc.menu;

import net.savagedev.lc.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu {
    private final MenuSlot[] slots;
    private final Inventory inventory;

    public Menu(String title, int rows) {
        this.inventory = Bukkit.createInventory(new MenuHolder(this), rows * 9, MessageUtils.color(title));
        this.slots = new MenuSlot[rows * 9];
    }

    public void open(Player user) {
        user.openInventory(this.inventory);
    }

    public MenuSlot setItem(int slot, ItemStack item) {
        final MenuSlot menuSlot = new MenuSlot();
        this.slots[slot] = menuSlot;
        this.inventory.setItem(slot, item);
        return menuSlot;
    }

    Inventory getInventory() {
        return this.inventory;
    }

    public MenuSlot getSlot(int slot) {
        return this.slots[slot];
    }
}

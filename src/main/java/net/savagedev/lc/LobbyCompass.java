package net.savagedev.lc;

import net.savagedev.lc.commands.LobbyCompassCmd;
import net.savagedev.lc.listeners.ConnectionListener;
import net.savagedev.lc.listeners.InteractListener;
import net.savagedev.lc.listeners.InventoryListener;
import net.savagedev.lc.listeners.WorldListener;
import net.savagedev.lc.utils.MessageUtils;
import net.savagedev.lc.menu.Menu;
import net.savagedev.lc.menu.MenuSlot;
import net.savagedev.lc.menu.action.CommandAction;
import net.savagedev.lc.menu.action.CommandAction.SenderType;
import net.savagedev.lc.menu.action.MessageAction;
import net.savagedev.lc.menu.action.TeleportAction;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LobbyCompass extends JavaPlugin {
    private List<String> enabledWorlds;
    private ItemStack compassItem;
    private Menu lobbyCompass;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadListeners();
        this.loadCompassItem();
        this.buildCompass();
        this.loadCommands();
    }

    public void reload() {
        this.reloadConfig();
        this.loadCompassItem();
        this.buildCompass();
    }

    private void loadCompassItem() {
        this.enabledWorlds = this.getConfig().getStringList("compass-item.worlds");

        this.compassItem = new ItemStack(Material.valueOf(Objects.requireNonNull(this.getConfig().getString("compass-item.material")).toUpperCase()));
        final ItemMeta meta = this.compassItem.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(MessageUtils.color(this.getConfig().getString("compass-item.name")));
            meta.setLore(MessageUtils.color(this.getConfig().getStringList("compass-item.lore")));
        }

        this.compassItem.setItemMeta(meta);
    }

    private void buildCompass() {
        this.lobbyCompass = new Menu(this.getConfig().getString("lobby-compass.title"), this.getConfig().getInt("lobby-compass.rows", 3));

        final ConfigurationSection section = this.getConfig().getConfigurationSection("lobby-compass.items");
        if (section == null) {
            return;
        }

        for (String slotStr : section.getKeys(false)) {
            final int slot = Integer.parseInt(slotStr);

            final String materialName = this.getConfig().getString(String.format("lobby-compass.items.%s.material", slotStr));
            if (materialName == null) {
                continue;
            }

            final ItemStack item = new ItemStack(Material.valueOf(materialName.toUpperCase()), this.getConfig().getInt(String.format("lobby-compass.items.%s.amount", slotStr), 1));
            final ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(MessageUtils.color(this.getConfig().getString(String.format("lobby-compass.items.%s.name", slotStr))));
            meta.setLore(MessageUtils.color(this.getConfig().getStringList(String.format("lobby-compass.items.%s.lore", slotStr))));
            item.setItemMeta(meta);

            final MenuSlot menuSlot = this.lobbyCompass.setItem(slot, item);

            final List<String> actions = this.getConfig().getStringList(String.format("lobby-compass.items.%s.actions", slotStr));
            for (String action : actions) {
                String[] actionParts = action.split("\\|", 2);

                String actionType = actionParts[0].toLowerCase();

                SenderType senderType = null;
                if (actionType.startsWith("command")) {
                    final String[] commandActionParts = actionType.split(":", 2);

                    if (commandActionParts.length < 2) {
                        this.getLogger().warning("Inventory slot " + slotStr + ". Command sender cannot be null/empty! Skipping action.");
                        continue;
                    }

                    actionType = commandActionParts[0];
                    try {
                        senderType = SenderType.valueOf(commandActionParts[1].toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException ignored) {
                        this.getLogger().warning("Inventory slot " + slotStr + ". Invalid command sender! (" + commandActionParts[1] + ") Skipping action.");
                    }
                }

                final String value = actionParts[1];

                if (value == null || value.isEmpty()) {
                    this.getLogger().warning("Inventory slot " + slotStr + ". Action value cannot be null/empty! Skipping action.");
                    continue;
                }

                switch (actionType) {
                    case "message": {
                        menuSlot.addAction(new MessageAction(value));
                        break;
                    }
                    case "command": {
                        menuSlot.addAction(new CommandAction(value, senderType));
                        break;
                    }
                    case "teleport": {
                        final World world = this.getServer().getWorld(value);
                        if (world == null) {
                            this.getLogger().warning("Inventory slot " + slotStr + ". Teleport action could not find the world '" + value + "' Skipping action.");
                            continue;
                        }
                        menuSlot.addAction(new TeleportAction(world));
                    }
                }
            }
        }
    }

    private void loadCommands() {
        this.getCommand("lobbycompass").setExecutor(new LobbyCompassCmd(this));
    }

    private void loadListeners() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new WorldListener(this), this);
        pluginManager.registerEvents(new InteractListener(this), this);
        pluginManager.registerEvents(new ConnectionListener(this), this);
        pluginManager.registerEvents(new InventoryListener(), this);
    }

    public void giveCompassItem(Player user) {
        final World world = user.getWorld();

        if (this.enabledWorlds.contains(world.getName()) && !user.getInventory().contains(this.compassItem)) {
            user.getInventory().setItem(this.getConfig().getInt("compass-item.slot"), this.compassItem);
        }
    }

    public ItemStack getCompassItem() {
        return this.compassItem;
    }

    public Menu getLobbyCompass() {
        return this.lobbyCompass;
    }
}

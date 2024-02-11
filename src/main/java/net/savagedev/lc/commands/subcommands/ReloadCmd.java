package net.savagedev.lc.commands.subcommands;

import net.savagedev.lc.LobbyCompass;
import net.savagedev.lc.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class ReloadCmd implements SubCommand {
    private final LobbyCompass plugin;

    public ReloadCmd(final LobbyCompass plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender user, String[] args) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            this.plugin.reload();

            PluginDescriptionFile description = this.plugin.getDescription();
            MessageUtils.message(user, "&6%s &7v&6%s &7by &6%s &7reloaded.", description.getName(), description.getVersion(), description.getAuthors().get(0));
        });
    }

    @Override
    public String getPermission() {
        return "lobbycompass.reload";
    }
}

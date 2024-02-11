package net.savagedev.lc.commands.subcommands;

import net.savagedev.lc.LobbyCompass;
import net.savagedev.lc.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenCmd implements SubCommand {
    private final LobbyCompass plugin;

    public OpenCmd(final LobbyCompass plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            MessageUtils.message(sender, "&cThis command can only by executed by players!");
            return;
        }

        Player user = (Player) sender;

        if (args.length == 1) {
            this.plugin.getLobbyCompass().open(user);
            return;
        }

        String targetName = args[1];

        if (!user.hasPermission(this.getPermission() + ".other")) {
            MessageUtils.message(user, "&cYou do not have permission to execute this command.");
            return;
        }

        Player target = this.plugin.getServer().getPlayer(targetName);

        if (target == null) {
            MessageUtils.message(user, "&cCould not find a player by the name of %s.", targetName);
            return;
        }

        this.plugin.getLobbyCompass().open(target);
        MessageUtils.message(user, "&cOpened lobby compass for %s.", target.getName());
    }

    @Override
    public String getPermission() {
        return "lobbycompass.open";
    }
}

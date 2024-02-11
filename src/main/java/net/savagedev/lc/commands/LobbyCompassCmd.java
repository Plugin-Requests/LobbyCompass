package net.savagedev.lc.commands;

import net.savagedev.lc.LobbyCompass;
import net.savagedev.lc.commands.subcommands.HelpCmd;
import net.savagedev.lc.commands.subcommands.OpenCmd;
import net.savagedev.lc.commands.subcommands.ReloadCmd;
import net.savagedev.lc.commands.subcommands.SubCommand;
import net.savagedev.lc.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LobbyCompassCmd implements CommandExecutor {
    private final Map<String, SubCommand> subCommands;
    private final LobbyCompass plugin;

    public LobbyCompassCmd(final LobbyCompass plugin) {
        this.subCommands = new HashMap<>();
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        this.subCommands.put("help", new HelpCmd());
        this.subCommands.put("open", new OpenCmd(this.plugin));
        this.subCommands.put("reload", new ReloadCmd(this.plugin));
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String d, @Nonnull String[] args) {
        if (args.length == 0) {
            MessageUtils.message(sender, "&cInvalid arguments! Try: /lc help");
            return true;
        }

        String commandName = args[0].toLowerCase();

        if (!this.subCommands.containsKey(commandName)) {
            MessageUtils.message(sender, "&cInvalid arguments! Try: /lc %s", this.getSuggestion(sender, commandName));
            return true;
        }

        SubCommand command = this.subCommands.get(commandName);

        if (!sender.hasPermission(command.getPermission())) {
            MessageUtils.message(sender, "&cYou do not have permission to execute this command.");
            return true;
        }

        command.execute(sender, args);
        return true;
    }

    private String getSuggestion(CommandSender sender, String arg) {
        for (Entry<String, SubCommand> entry : this.subCommands.entrySet()) {
            if (entry.getKey().startsWith(arg) && sender.hasPermission(entry.getValue().getPermission())) {
                return entry.getKey();
            }
        }

        return "help";
    }
}

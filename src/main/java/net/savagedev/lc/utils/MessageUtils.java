package net.savagedev.lc.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
    public static void message(CommandSender user, String message, Object... args) {
        message(user, String.format(message, args));
    }

    public static void message(CommandSender user, String message) {
        user.sendMessage(color(message));
    }

    public static List<String> color(List<String> messages) {
        List<String> colored = new ArrayList<>();

        for (String message : messages) {
            colored.add(color(message));
        }

        return colored;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

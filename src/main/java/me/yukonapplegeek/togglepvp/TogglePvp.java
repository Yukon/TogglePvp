package me.yukonapplegeek.togglepvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglePvp extends JavaPlugin implements Listener {
    public void onDisable() {
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("togglepvp.self")) {
                togglePvp(sender, (Player) sender);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission!");
                return true;
            }
        } else if (args.length >= 1) {
            if (args.length == 1) {
                if (sender.hasPermission("togglepvp.other")) {
                    Player player = this.getServer().getPlayer(args[0]);
                    togglePvp(sender, player);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission!");
                    return true;
                }
            } else if (args.length == 2) {
                if (sender.hasPermission("togglepvp.other")) {
                    Player player = this.getServer().getPlayer(args[0]);
                    togglePvp(sender, player);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission!");
                    return true;
                }
            }

        }
        return false;
    }

    private void togglePvp(CommandSender sender, Player player) {
        if (player != null) {

        } else {
            sender.sendMessage(ChatColor.RED + "Player not found!");
        }
    }
}

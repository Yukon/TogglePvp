package me.yukonapplegeek.togglepvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
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
            if (player.hasMetadata("pvp")) {
                boolean pvp = player.getMetadata("pvp").get(0).asBoolean();
                player.setMetadata("pvp", new FixedMetadataValue(this, !pvp));
            } else {
                player.setMetadata("pvp", new FixedMetadataValue(this, true));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Player not found!");
        }
    }

    private boolean canPvp(Player damaged, Player damager) {
        if (!damaged.hasMetadata("pvp") || !damager.hasMetadata("pvp")) {
            if (!damager.hasMetadata("pvp")) {
                damager.sendMessage(ChatColor.RED + "You have pvp disabled! Do /pvp to enable!");
            } else if (!damaged.hasMetadata("pvp")) {
                damager.sendMessage(ChatColor.RED + damaged.getName() + " has pvp disabled!");
            }
            return false;
        } else {
            boolean damagedPvp = damaged.getMetadata("pvp").get(0).asBoolean();
            boolean damagerPvp = damager.getMetadata("pvp").get(0).asBoolean();

            if (!damagedPvp || !damagerPvp) {
                if (!damagerPvp) {
                    damager.sendMessage(ChatColor.RED + "You have pvp disabled! Do /pvp to enable!");
                } else if (!damagedPvp) {
                    damager.sendMessage(ChatColor.RED + damaged.getName() + " has pvp disabled!");
                }
                return false;
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            boolean canPvp = canPvp((Player) event.getEntity(), (Player) event.getDamager());
            if (!canPvp) event.setCancelled(true);
        } else if (event.getEntity() instanceof Player && event.getDamager() instanceof Projectile) {
            if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
                boolean canPvp = canPvp((Player) event.getEntity(), (Player) ((Projectile) event.getDamager()).getShooter());
                if (!canPvp) event.setCancelled(true);
            }
        }
    }
}

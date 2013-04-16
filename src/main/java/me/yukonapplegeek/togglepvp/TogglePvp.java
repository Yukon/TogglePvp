package me.yukonapplegeek.togglepvp;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglePvp extends JavaPlugin implements Listener {
    public void onDisable() {
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
}
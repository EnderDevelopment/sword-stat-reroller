package com.enderdevelopment.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("reroll").setExecutor(new RerollCommand(this));
        getCommand("setstat").setExecutor(new SetStatCommand(this));
        getLogger().info("SwordReroll has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SwordReroll has been disabled!");
    }
}

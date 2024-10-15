package com.arenaclear.cpvp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class ArenaClearPlugin extends JavaPlugin {

    private FileConfiguration config;
    private int clearInterval;
    private int yLevel;
    private String announcement;

    @Override
    public void onEnable() {
        config = getConfig();
        clearInterval = config.getInt("clearInterval");
        yLevel = config.getInt("yLevel");
        announcement = config.getString("announcement");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new ClearTask(), 0, clearInterval * 20);
    }

    private class ClearTask implements Runnable {
        @Override
        public void run() {
            // Send announcement 10 seconds before clearing
            Bukkit.broadcastMessage(announcement);

            // Clear obsidian under Y level
            for (Block block : getServer().getWorlds().get(0).getBlocks()) {
                if (block.getY() < yLevel && block.getType() == Material.OBSIDIAN) {
                    block.setType(Material.AIR);
                }
            }
        }
    }
}
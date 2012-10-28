package com.mistphizzle.stopwitherspawns;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class StopWitherSpawns extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	public void onDisable() {
	}
	@EventHandler
	public void WitherSpawnEvent(CreatureSpawnEvent event) {
		EntityType type = event.getEntity().getType();
		if (type == EntityType.WITHER) {
			event.setCancelled(true);
		}
	}
}

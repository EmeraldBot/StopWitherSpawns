package com.mistphizzle.withermanagement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WitherManagement extends JavaPlugin implements Listener {
	
	// Configuration Stuff
	File configFile;
	FileConfiguration config;
	
	WitherManagementCommand wmc;
	
	public void onEnable() {
		wmc = new WitherManagementCommand(this);
		getServer().getPluginManager().registerEvents(this, this);
		configFile = new File(getDataFolder(), "config.yml");
		
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = new YamlConfiguration();
		loadYamls();
	}
	public void onDisable() {
	}
	@EventHandler
	public void WitherSpawnEvent(CreatureSpawnEvent event) {
		Entity e = event.getEntity();
		EntityType type = event.getEntity().getType();
		String world = e.getWorld().getName();
		if (type == EntityType.WITHER && !getConfig().getStringList("Worlds").contains(world)) {
			event.setCancelled(true);
		}
		
	}

	@EventHandler
	public void WitherProjectile(EntityExplodeEvent event) {
		if (event.isCancelled()) return;
		Entity entity = event.getEntity();
		
		if (entity instanceof WitherSkull && config.getBoolean("Properties.DisableExplosions", true)) {
			World world = event.getLocation().getWorld();
			Location location = event.getLocation();
			
			event.setCancelled(true);
			world.createExplosion(location, 0.0F, false);
			world.playEffect(location, Effect.SMOKE, 1);
			
		}
	}
	
	@EventHandler
	public void WitherSpawnExplosion(EntityExplodeEvent event) {
		if (event.isCancelled()) return;
		Entity entity = event.getEntity();
		
		if (entity instanceof Wither && config.getBoolean("Properties.DisableExplosions", true)) {
			World world = event.getLocation().getWorld();
			Location location = event.getLocation();
			
			event.setCancelled(true);
			world.createExplosion(location, 0.0F, false);
			world.playEffect(location, Effect.SMOKE, 1);
			
		}
	}
	
	// Methods
	private void firstRun() throws Exception {
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
	}
	
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte [1024];
			int len;
			while ((len = in.read(buf))>0) {
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadYamls() {
		try {
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package com.mistphizzle.withermanagement;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;

public class WitherManagementCommand {
	
	WitherManagement plugin;
	
	public WitherManagementCommand(WitherManagement instance) {
		this.plugin = instance;
		init();
	}
	
	private void init() {
		PluginCommand withermanagement = plugin.getCommand("withermanagement");
		CommandExecutor exe;
		
		exe = new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
				if (args.length < 1) {
					s.sendMessage("§eWitherManagement Commands");
					s.sendMessage("§3/withermanagement reload:§f - Reloads Config.");
					s.sendMessage("§3/withermanagement version:§f - Checks the version number.");
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload") && s.hasPermission("withermanagement.reload")) {
					ConfigReload();
					s.sendMessage("§aConfig Reloaded");
					return true;
				}
				else if (args[0].equalsIgnoreCase("version")) {
					if (s.hasPermission("withermanagement.version")) {
						s.sendMessage("§eThis server is running WitherManagement §3v" + plugin.getDescription().getVersion());
						return true;
					}
					else {
						s.sendMessage("§cYou don't have permission to do that!");
					}
				} return false;
			}
		}; withermanagement.setExecutor(exe);
	}
	
	public void ConfigReload() {
		plugin.reloadConfig();
	}

}

package me.wickersty.travellogs.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.wickersty.travellogs.TravelLogs;

public class HelpManager {

	@SuppressWarnings("unused")
	private final TravelLogs instance;
	
	public HelpManager(TravelLogs instance) {
		
		this.instance = instance;
		
	}
	
	public void showHelp(Player player) {
		
		player.sendMessage(ChatColor.DARK_AQUA + "Travel Logs " + ChatColor.WHITE + "HELP");
		player.sendMessage(ChatColor.WHITE + "All commands can be either " + ChatColor.AQUA + "/travellog " + ChatColor.WHITE + "or " + ChatColor.AQUA + "/tl");
		
		player.sendMessage(ChatColor.AQUA + "/travellog list" + ChatColor.GRAY + "Entries for the current world");
		player.sendMessage(ChatColor.AQUA + "/travellog list all" + ChatColor.GRAY + "Entries for all worlds");
		player.sendMessage(ChatColor.AQUA + "/travellog add <name>" + ChatColor.GRAY + "Add an entry");
		player.sendMessage(ChatColor.AQUA + "/travellog remove <name>" + ChatColor.GRAY + "Remove an entry");
		player.sendMessage(ChatColor.AQUA + "/travellog use <name>" + ChatColor.GRAY + "Use an entry");
		player.sendMessage(ChatColor.AQUA + "/travellog share <name> <player>" + ChatColor.GRAY + "Share an entry");
		
	}

}

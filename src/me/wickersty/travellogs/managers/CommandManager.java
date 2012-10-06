package me.wickersty.travellogs.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.wickersty.travellogs.TravelLogs;
import me.wickersty.travellogs.objects.TravelLog;
import me.wickersty.travellogs.objects.TravelLogEntry;

public class CommandManager {

	private final TravelLogs instance;
	
	public CommandManager(TravelLogs instance) {
		
		this.instance = instance;
		
	}

	public void processCommand(Player player, Command cmd, String[] args) {
		
		if (instance.getConfigManager().isWorldEnabled(player.getWorld().getName()) == false) {
			
			player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You cannot use your Travel Log in this world.");

			return;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("travellogs") || cmd.getName().equalsIgnoreCase("tl")) {
			
			if (instance.getPermissionsManager().playerHasPermissions(player, "travellogs.use") == false && !player.isOp()) {
				
				player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You do not have permissions to use Travel Logs.");
				
			}

			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				
				instance.getHelpManager().showHelp(player);
				
				return;
				
			}
			
			if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
				
				if (args[1].equalsIgnoreCase("")) {
					
					return;
					
				}
				
				TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(player);
				
				if (instance.getConfigManager().maxEntries != 0 && travelLog.entries.size() == instance.getConfigManager().maxEntries) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You cannot fit any more entries into your Travel Log.");
					
					return;
					
				}
				
				if (travelLog.getTravelLogEntryByName(args[1]) == null) {
					
					TravelLogEntry entry = new TravelLogEntry(args[1].toUpperCase(), player.getLocation());
					travelLog.addEntry(entry);

					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "Entry created.");

				} else {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You already have an entry by this name.");
					
					return;
					
				}
				
			}
			
			if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
				
				if (args[1].equalsIgnoreCase("")) {
					
					return;
					
				}
				
				TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(player); 
				
				if (travelLog.getTravelLogEntryByName(args[1].toUpperCase()) == null) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You do not have an entry by this name.");
					
					return;
					
				} else {
					
					TravelLogEntry entry = new TravelLogEntry(args[1].toUpperCase(), player.getLocation());
					
					instance.getTravelLogManager().removeEntryFromTravelLog(travelLog, entry);

					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "Entry removed.");

				}
				
			}
			
			if (args.length == 2 && args[0].equalsIgnoreCase("use")) {
				
				if (args[1].equalsIgnoreCase("")) {
					
					return;
					
				}
				
				TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(player); 
				
				TravelLogEntry entry = travelLog.getTravelLogEntryByName(args[1].toUpperCase());
				
				if (entry == null) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You do not have an entry by this name.");
					
					return;
					
				} else {
					
					if (!player.getWorld().equals(entry.entryLocation.getWorld())) {
						
						player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "That entry is for another world.");
						
						return;
						
					}
					
					instance.getTravelLogManager().readTravelLogEntry(player, entry);
										
				}				
			}
			
			if (args.length == 1 && args[0].equalsIgnoreCase("list")) {

				TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(player);
				
				instance.getTravelLogManager().listTravelLogEntriesForWorld(player, travelLog);				
				
			}
			
			if (args.length == 2 && args[0].equalsIgnoreCase("list") && args[1].equalsIgnoreCase("all")) {

				TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(player);
				
				instance.getTravelLogManager().listAllTravelLogEntries(player, travelLog);				
				
			}
			
			if (args.length == 3 && args[0].equalsIgnoreCase("share")) {
				
				if (args[1].equalsIgnoreCase("") || args[2].equalsIgnoreCase("")) {
					
					return;
					
				}

				TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(player);
				TravelLogEntry entry = travelLog.getTravelLogEntryByName(args[1].toUpperCase());
				
				if (entry == null) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You do not have an entry by this name.");
					
					return; 
					
				}
				
				Player recipient = instance.getServer().getPlayer(args[2]);
				
				if (recipient == null) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "That player is not online.");
				
					return;
					
				}
				
				TravelLog recipientsTravelLog = instance.getTravelLogManager().getTravelLogByOwnerName(recipient);
				
				
				if (instance.getConfigManager().maxEntries != 0 && recipientsTravelLog.entries.size() == instance.getConfigManager().maxEntries) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + recipient.getName() + " cannot fit any more entries into their Travel Log.");
					
					return;
					
				}				
				
				if (recipientsTravelLog.getTravelLogEntryByName(args[1]) != null) {
					
					player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + recipient.getName() + " already has an entry by this name.");
					
					return;
					
				}
								
				
				instance.getTravelLogManager().addEntryToShareQueue(recipient, entry);
				
				player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "Sharing Travel Log entry with " + args[2]);
				
				recipient.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + player.getName() + " would like to share a Travel Log entry with you. To accept, type: " + ChatColor.WHITE + "/tl accept");
				
			}
			
		}
		
		if (args.length == 1 && args[0].equalsIgnoreCase("accept")) {

			if (instance.getTravelLogManager().sharedEntryInQueue(player)) {
				
				instance.getTravelLogManager().removeEntryFromShareQueue(player);
				
				instance.getTravelLogManager().acceptEntryFromShareQueue(player);
				
			} else {
				
				player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.GRAY + "You don't have any shared entries to accept.");
				
			}
			
		}
		
	}

}

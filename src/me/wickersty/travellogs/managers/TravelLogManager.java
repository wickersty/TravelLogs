package me.wickersty.travellogs.managers;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.wickersty.travellogs.TravelLogs;
import me.wickersty.travellogs.objects.TravelLog;
import me.wickersty.travellogs.objects.TravelLogEntry;

public class TravelLogManager {
	
	private final TravelLogs instance;
	private HashMap<Player,TravelLogEntry> sharedEntryQueue;
	
	public TravelLogManager(TravelLogs instance) {
		
		this.instance = instance;
		
		sharedEntryQueue = new HashMap<Player,TravelLogEntry>();
		
	}
	
	public boolean sharedEntryInQueue(Player player) {

		return sharedEntryQueue.containsKey(player);
		
	}
	
	public TravelLog getTravelLogByOwnerName(Player player) {
		
		TravelLog foundTravelLog = null;
		
		instance.getLogger().info("Checking " + instance.getDataManager().travelLogs.size() + " Travel Logs for " + player.getName());
		
		for (TravelLog travelLog : instance.getDataManager().travelLogs) {
			
			instance.getLogger().info("Found " + travelLog.ownerName);
			
			if (travelLog.ownerName.equalsIgnoreCase(player.getName())) {
			
				foundTravelLog = travelLog;
				
			}
			
		}
		
		return foundTravelLog;
		
	}

	public void readTravelLogEntry(Player player, TravelLogEntry entry) {
		
		String distanceAsString = String.valueOf(player.getLocation().distance(entry.entryLocation)).split(".")[0];
		
		player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.WHITE + entry.entryName);
		
		if (instance.getConfigManager().showDistances) {

			player.sendMessage(ChatColor.GRAY + "Distance: " + distanceAsString + " Blocks");
			
		}
		
		player.sendMessage(ChatColor.GRAY + "Your compass is now pointing at " + entry.entryName);
		
		player.setCompassTarget(entry.entryLocation);
		
	}
	
	public void listTravelLogEntriesForWorld(Player player, TravelLog travelLog) {
		
		player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.WHITE + "Available Entries");
		
		for (TravelLogEntry entry : travelLog.entries) {
			
			if (entry.entryLocation.getWorld().equals(player.getWorld())) {

				player.sendMessage(ChatColor.GRAY + entry.entryName);
				
			}
			
		}
		
	}

	public void listAllTravelLogEntries(Player player, TravelLog travelLog) {
		
		player.sendMessage(ChatColor.AQUA + "[Travel Logs] " + ChatColor.WHITE + "All Entries");
		
		for (TravelLogEntry entry : travelLog.entries) {

			player.sendMessage(ChatColor.GRAY + entry.entryName + "(in " + entry.entryLocation.getWorld() + ")");
			
		}
		
	}

	public void addEntryToShareQueue(Player recipient, TravelLogEntry entry) {

		sharedEntryQueue.remove(recipient);
		sharedEntryQueue.put(recipient, entry);
		
	}

	public void removeEntryFromShareQueue(Player recipient) {

		sharedEntryQueue.remove(recipient);
		
	}
	
	public void removeEntryFromTravelLog(TravelLog travelLog, TravelLogEntry entry) {
		
		TravelLogEntry entryToDelete = null;
		
		for (TravelLogEntry thisEntry : travelLog.entries) {
			
			if (thisEntry.entryName.equalsIgnoreCase(entry.entryName)) {
				
				entryToDelete = thisEntry;
				
			}
			
		}
		
		travelLog.entries.remove(entryToDelete);		
		
	}
	
	public void acceptEntryFromShareQueue(Player recipient) {
		
		TravelLogEntry entry = sharedEntryQueue.get(recipient);
		
		TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(recipient);
		
		travelLog.addEntry(entry);
		
		removeEntryFromShareQueue(recipient);
		
	}

}

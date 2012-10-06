package me.wickersty.travellogs.listeners;

import me.wickersty.travellogs.TravelLogs;
import me.wickersty.travellogs.objects.TravelLog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {
	
	private final TravelLogs instance;
	
	public PlayerListener(TravelLogs instance) {
		
		this.instance = instance;
		
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void playerLogsIn(PlayerLoginEvent event) {
		
		TravelLog travelLog = instance.getTravelLogManager().getTravelLogByOwnerName(event.getPlayer());
		
		if (travelLog == null) {
			
			instance.getLogger().info("Creating Travel Log for " + event.getPlayer().getName());
			
			travelLog = new TravelLog(event.getPlayer().getName());
			
			instance.getDataManager().travelLogs.add(travelLog);
			
		}
		
	}
	
}

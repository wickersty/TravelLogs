package me.wickersty.travellogs.objects;

import org.bukkit.Location;

public class TravelLogEntry {

	public String entryName;
	public Location entryLocation;
	
	public TravelLogEntry(String entryName, Location entryLocation) {
		
		this.entryName = entryName.toUpperCase();
		this.entryLocation = entryLocation;
		
	}
	
	public String serializeEntry() {
		
		return entryName.toUpperCase() + "~" + entryLocation.getWorld().getName() + "~" + entryLocation.getX() + "~" + entryLocation.getY() + "~" + entryLocation.getZ();
		
	}
	
}

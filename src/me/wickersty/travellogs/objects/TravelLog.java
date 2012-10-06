package me.wickersty.travellogs.objects;

import java.util.ArrayList;
import java.util.List;

public class TravelLog {
	
	public String ownerName;
	public List<TravelLogEntry> entries;
	
	public TravelLog(String ownerName) {
		
		this.ownerName = ownerName;
		this.entries = new ArrayList<TravelLogEntry>();
		
	}
	
	public TravelLogEntry getTravelLogEntryByName(String entryName) {
		
		TravelLogEntry foundEntry = null;
		
		for (TravelLogEntry entry : entries) {
			
			if (entry.entryName.equalsIgnoreCase(entryName)) {
				
				foundEntry = entry;
				
			}
			
		}
		
		return foundEntry;
		
	}
	
	public void addEntry(TravelLogEntry entry) {
		
		entries.add(entry);
		
	}

	public void removeEntry(TravelLogEntry entry) {

		entries.remove(entry);
		
	}

}

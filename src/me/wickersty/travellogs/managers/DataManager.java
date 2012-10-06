package me.wickersty.travellogs.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bukkit.Location;
import me.wickersty.travellogs.TravelLogs;
import me.wickersty.travellogs.objects.TravelLog;
import me.wickersty.travellogs.objects.TravelLogEntry;

public class DataManager {

	private final TravelLogs instance;
	public List<TravelLog> travelLogs;	
	
	public DataManager(TravelLogs instance) {
		
		this.instance = instance;
		
		initializeData();
		
		loadData();
		
	}
	
	public void initializeData() {

		instance.getLogger().info("Initializing Folders");

		travelLogs = new ArrayList<TravelLog>();

		File folder = new File(instance.getDataFolder(), "travel-logs" + File.separator);
		
		if (folder.exists() == false) {

			folder.mkdir();

		}		

	}

	public void loadData() {
		
		instance.getLogger().info("Loading Data");

		try {

			String path = instance.getDataFolder() + File.separator + "travel-logs";
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			
			instance.getLogger().info("Travel Logs: " + listOfFiles.toString());
			
			for (int i = 0; i < listOfFiles.length; i++) {
				
				if (listOfFiles[i].isFile()) {
					
					// each file is a player's travel log
					instance.getLogger().info("Getting " + listOfFiles[i].getName());
					
					File travelLogFile = new File(instance.getDataFolder() + File.separator + "travel-logs" + File.separator + listOfFiles[i].getName());
					Scanner inputFile = new Scanner(travelLogFile);

					String ownerName = listOfFiles[i].getName(); 

					TravelLog travelLog = new TravelLog(ownerName.replace(".txt", ""));
					
					while (inputFile.hasNextLine()) {
						
						String travelLogEntriesString = inputFile.nextLine();
		
						String[] travelLogEntriesArray = travelLogEntriesString.split("~");

						String entryName = travelLogEntriesArray[0].toUpperCase();
						Location entryLocation = new Location(instance.getServer().getWorld(travelLogEntriesArray[1]), Double.valueOf(travelLogEntriesArray[2]), Double.valueOf(travelLogEntriesArray[3]), Double.valueOf(travelLogEntriesArray[4]));
						
						TravelLogEntry entry = new TravelLogEntry(entryName, entryLocation);
						travelLog.entries.add(entry);
						
					}					
					
					travelLogs.add(travelLog);

				}
				
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
	
		}

	}
	
	public void saveData() {
		
		instance.getLogger().info("Saving Data");
		
		try {
			
			for (TravelLog travelLog : travelLogs) {
				
				instance.getLogger().info("Saving Travel Log for " + travelLog.ownerName + ", with " + travelLog.entries.size() + " entries");
				
				String ownerName = travelLog.ownerName;
				
				instance.getLogger().info("Deleting & Creating " + ownerName + ".txt");
	
				File logFile = new File(instance.getDataFolder() + File.separator + "travel-logs" + File.separator + ownerName + ".txt");
				logFile.delete();
	
				logFile = new File(instance.getDataFolder() + File.separator + "travel-logs" + File.separator + ownerName + ".txt");
				logFile.createNewFile();
				
				PrintWriter logWriter = new PrintWriter(instance.getDataFolder() + File.separator + "travel-logs" + File.separator + ownerName + ".txt");
				
				for (TravelLogEntry entry : travelLog.entries) {
					
					String entryString = entry.serializeEntry();
					
					logWriter.println(entryString);
					
				}
				
				logWriter.close();
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}

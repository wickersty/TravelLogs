package me.wickersty.travellogs.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import me.wickersty.travellogs.TravelLogs;

public class ConfigManager {

	private final TravelLogs instance;

	// config files
	private File configFile;
	private FileConfiguration config;

	public String[] worldsEnabled;
	public Integer maxEntries;
	public Boolean showDistances;

	public ConfigManager(TravelLogs instance) {
		
		this.instance = instance;

		config = instance.getConfig();
		
		configFile = new File(instance.getDataFolder() + File.separator + "config.yml");

		initializeConfig();
		
		loadConfig();
		
		saveConfig();		

	}

	public void initializeConfig() {
		
		boolean configFileExists = configFile.exists();
		
		if (configFileExists == true) {

			instance.getLogger().info("Config File Exists");

			try {

				config.options().copyDefaults(true);
				config.load(this.configFile);
				
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			
		} else {
			
			instance.getLogger().info("Creating Default Config File");
			
			config.options().copyDefaults(true);
			
		}
				
	}

	public void loadConfig() {
		
		// worlds
		worldsEnabled = config.getString("settings.worlds-enabled").split(",");
		maxEntries = config.getInt("settings.max-entries");
		showDistances = config.getBoolean("settings.show-distances");
		
	}

	public void saveConfig() {

		try {

			config.save(configFile);
			
		} catch (IOException e) {

			e.printStackTrace();
			
		}

	}

	public boolean isWorldEnabled(String worldName) {
		
		boolean isEnabled = false;
		
		for (String world : worldsEnabled) {
			
			if (world.equalsIgnoreCase(worldName)) {
				
				isEnabled = true;
				
			}
			
		}
		
		return isEnabled;
		
	}
	
	
}

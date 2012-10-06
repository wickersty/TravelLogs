package me.wickersty.travellogs;

import java.util.logging.Logger;

import me.wickersty.travellogs.listeners.PlayerListener;
import me.wickersty.travellogs.managers.CommandManager;
import me.wickersty.travellogs.managers.ConfigManager;
import me.wickersty.travellogs.managers.DataManager;
import me.wickersty.travellogs.managers.HelpManager;
import me.wickersty.travellogs.managers.PermissionsManager;
import me.wickersty.travellogs.managers.TravelLogManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class TravelLogs extends JavaPlugin {

	public static TravelLogs instance;
	private static Logger logger = Logger.getLogger("Minecraft");

	private CommandManager commandManager;
	private PermissionsManager permissionsManager;
	private ConfigManager configManager;
	private HelpManager helpManager;
	private DataManager dataManager;
	private TravelLogManager travelLogManager;
	
	private PlayerListener playerListener;
	
	public void onEnable() {

		logger.info("|----------|");

		instance = this;

		getDataFolder().mkdir();
		
		commandManager = new CommandManager(instance);
		permissionsManager = new PermissionsManager(instance);
		configManager = new ConfigManager(instance);
		helpManager = new HelpManager(instance);
		dataManager = new DataManager(instance);
		travelLogManager = new TravelLogManager(instance);
		
		playerListener = new PlayerListener(instance);
		
		getServer().getPluginManager().registerEvents(this.playerListener, this);				

		logger.info("|----------|");

	}
	
	public void onDisable() {
		
		logger.info("|----------|");

		getDataManager().saveData();
		
		getServer().getScheduler().cancelTasks(this);

		logger.info("|----------|");
		
	}
	
	public CommandManager getCommandManager() {
		
		return commandManager;
		
	}

	public PermissionsManager getPermissionsManager() {
		
		return permissionsManager;
		
	}

	public ConfigManager getConfigManager() {
		
		return configManager;
		
	}

	public HelpManager getHelpManager() {
		
		return helpManager;
		
	}

	public DataManager getDataManager() {
		
		return dataManager;
		
	}

	public TravelLogManager getTravelLogManager() {
		
		return travelLogManager;
		
	}

	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (sender instanceof Player) {
			
			// player commands
			getCommandManager().processCommand((Player) sender, cmd, args);
			
		}
		
		return true;
		
	}	
	
}

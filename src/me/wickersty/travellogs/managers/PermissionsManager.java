package me.wickersty.travellogs.managers;

import me.wickersty.travellogs.TravelLogs;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermissionsManager {

	private final TravelLogs instance;
	
	public static Permission permissions;

	public PermissionsManager(TravelLogs instance) {
		
		this.instance = instance;
	
		initializePermissions();

	}

	private void initializePermissions() {
		
		RegisteredServiceProvider<Permission> permissionProvider = instance.getServer().getServicesManager().getRegistration(Permission.class);
		
		if (permissionProvider != null) {

			permissions = (Permission)permissionProvider.getProvider();
			
		}
		
		if (permissions != null) { 
			
			instance.getLogger().info("Permissions Initialized");
			
		} else {
			
			instance.getLogger().warning("Could Not Initialize Permissions");
			
		}
		
	}
	
	public boolean playerHasPermissions(Player player, String permission) {
		
		boolean hasPermissions = false;
		
		if (permissions.has(player, permission)) {
			
			hasPermissions = true;
			
		}
		
		return hasPermissions;		
		
	}
	
}


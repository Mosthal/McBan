package fr.Fozl;

import org.bukkit.plugin.java.JavaPlugin;

import fr.Fozl.Commands.BanCommand;
import fr.Fozl.Commands.BanTabCompleter;
import fr.Fozl.Listeners.OnPlayerLogin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class McBan extends JavaPlugin {
	
	final TextComponent PERMISSION_MSG = 
		Component.text("You can't use this command", NamedTextColor.RED);
	
	@Override
	public void onEnable() {		
		getCommand("ban").setExecutor(new BanCommand());
		getCommand("ban").setTabCompleter(new BanTabCompleter());
		getCommand("ban").setPermission("mcban.ban");
		getCommand("ban").permissionMessage(PERMISSION_MSG);
		getServer().getPluginManager().registerEvents(new OnPlayerLogin(), this);
		
		getLogger().info("McBan has been initialized");
	}
}

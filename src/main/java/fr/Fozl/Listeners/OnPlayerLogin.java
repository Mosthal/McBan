package fr.Fozl.Listeners;

import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import fr.Fozl.Utils.Utils;
import net.kyori.adventure.text.TextComponent;

public class OnPlayerLogin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		
		if (p.isBanned()) {
			
			String reason = Bukkit.getBanList(Type.NAME).getBanEntry(p.getName()).getReason();
			String source = Bukkit.getBanList(Type.NAME).getBanEntry(p.getName()).getSource();
			Date expireDate = Bukkit.getBanList(Type.NAME).getBanEntry(p.getName()).getExpiration();
			
			TextComponent kickMsg;
			if (expireDate == null) {
				kickMsg = Utils.getBanMessage(reason, source);
			}
			else {
				kickMsg = Utils.getBanMessage(reason, source, expireDate);
			}
			e.disallow(PlayerLoginEvent.Result.KICK_BANNED, kickMsg);
		}
	}
}

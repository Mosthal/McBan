package fr.Fozl.Commands;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent.Cause;

import fr.Fozl.Utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class BanCommand extends CommandAbstract implements CommandExecutor {

	final static TextComponent HELP_MSG = 
		Component.text("Usage: /ban <player> {perm / <amount> {h/d/m/y}} <reason>\n", NamedTextColor.YELLOW)
		.append(Component.text("Examples:\n", NamedTextColor.YELLOW))
		.append(Component.text("/ban Notch perm Kills all my cows\n", NamedTextColor.YELLOW))
		.append(Component.text("/ban Jeb_ 7 d See you in a week !", NamedTextColor.YELLOW));
	
	final static TextComponent USAGE_MSG = 
		Component.text("Incorrect usage, try: /ban", NamedTextColor.RED);
	
	public BanCommand() {
		super(HELP_MSG, USAGE_MSG);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {			
			super.sendHelpMessage(sender);
		}
		else if (args.length < 3) {
			super.sendUsageMessage(sender);		
		}
		else if (args[1].equalsIgnoreCase("perm")) {
			banPlayer(sender, args, true);
		}
		else if (args.length > 3 && StringUtils.isNumeric(args[1]) && isUnitValid(args[2])) {
			banPlayer(sender, args, false);
		}
		else {
			super.sendUsageMessage(sender);
		}
		return true;
	}	
	
	private boolean isUnitValid(String s) {
		boolean valid = false;
		String[] units = new String[] {"h", "d", "m", "y"};
		
		for (String unit : units) {
			if (s.equalsIgnoreCase(unit)) {
				valid = true;
			}
		}
		return valid;
	}
	
	private String getReason(int index, String[] args) {
		String reason = "";
		for (int i = index; i < args.length; i++) {
			reason += args[i];
			if (i < (args.length - 1)) {
				reason += " ";
			}
		}
		return reason;
	}
	
	private Date getExpireDate(int amount, String unit) {
		long current = new Date().getTime();
		unit = unit.toUpperCase();
		
		switch (unit) {
		    case "H":
		    	current += amount * 1000L * 60L * 60L;
		        break;
	
		    case "D":
		    	current += amount * 1000L * 60L * 60L * 24L;
		        break;
	
		    case "M":
		    	current += amount * 2629800000L;
		        break;
	
		    case "Y":
		    	current += amount * 2629800000L * 12L;
		        break;
		}
		return new Date(current);
	}
	
	private boolean isValidAmount(String s) {
		boolean valid = false;

		try {
			int amount = Integer.parseInt(s);
			
			if (amount >= 1 && amount <= 100) {
				valid = true;
			}
		}
		catch (NumberFormatException e) {}

		return valid;
	}
	
	private void banPlayer(CommandSender sender, String[] args, boolean perm) {
		String reason = "";
		String source = "Server";
		String unit = args[2];
		int amount = 0;
		Date expireDate = new Date();
		
		if (perm) {
			reason = getReason(2, args);
		}
		else {
			reason = getReason(3, args);
			
			if (!isValidAmount(args[1])) {
				TextComponent amoErrMsg = 
				Component.text("Invalid amount, must be between 1 and 100", NamedTextColor.RED);
				sender.sendMessage(amoErrMsg);
				return;
			}
			amount = Integer.parseInt(args[1]);
			expireDate = getExpireDate(amount, unit);
			
		}
		
		if (sender instanceof Player) {
			source = sender.getName();
		}
		
		if (Bukkit.getOfflinePlayerIfCached(args[0]) != null) {
			OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
			TextComponent banTchatMsg;
			
			if (perm) {
				Bukkit.getBanList(Type.NAME).addBan(target.getName(), reason, null, source);
				banTchatMsg = Utils.getBanTchatMessage(target.getName(), reason, source);
			}
			else {
				Bukkit.getBanList(Type.NAME).addBan(target.getName(), reason, expireDate, source);
				banTchatMsg = Utils.getBanTchatMessage(target.getName(), reason, source, amount, unit);
			}
			
			if (target.isOnline()) {
				Player onlineTarget = Bukkit.getPlayer(args[0]);
				
				TextComponent banMsg;
				
				if (perm) {
					banMsg = Utils.getBanMessage(reason, source);
				}
				else {
					banMsg = Utils.getBanMessage(reason, source, expireDate);
				}
				
				onlineTarget.kick(banMsg, Cause.BANNED);
			}		
			
			Bukkit.getOnlinePlayers().forEach(p -> {
				if (p.hasPermission("mcban.view")) {
					p.sendMessage(banTchatMsg);
				}
			});
			
		}
		else {
			TextComponent errorMsg = 
			Component.text("This player has never been connected to the server", NamedTextColor.RED);
			sender.sendMessage(errorMsg);
		}				
	}
}

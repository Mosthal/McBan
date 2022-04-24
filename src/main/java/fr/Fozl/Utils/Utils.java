package fr.Fozl.Utils;

import java.util.Date;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Utils {
	
	public static TextComponent getBanMessage(String reason, String source, Date expireDate) {
		TextComponent banMsg;
		boolean perm = (expireDate == null);
		
		if (perm) {
			banMsg = Component.text("You have been permanently banned from the server\n", NamedTextColor.RED);
		}
		else {
			banMsg = Component.text("You have been banned from the server\n", NamedTextColor.RED);
		}
		
		banMsg = banMsg.append(Component.text("Reason: ", NamedTextColor.RED))
		.append(Component.text(reason + "\n", NamedTextColor.YELLOW))
		.append(Component.text("By: ", NamedTextColor.RED))
		.append(Component.text(source, NamedTextColor.YELLOW));
		
		if (!perm) {					
			banMsg = banMsg.append(Component.text("\nUntil: ", NamedTextColor.RED))
			.append(Component.text(expireDate.toString(), NamedTextColor.YELLOW));
		}
		return banMsg;
	}
	
	public static TextComponent getBanMessage(String reason, String source) {
		return getBanMessage(reason, source, null);
	}
	
	public static TextComponent getBanTchatMessage(String playerName, String reason, String source, int amount,
		String unit) {
		
		TextComponent banTchatMsg;
		boolean perm = (amount == 0);
		
		banTchatMsg = Component.text(playerName, NamedTextColor.RED)
		.append(Component.text(" has been banned by ", NamedTextColor.YELLOW))
		.append(Component.text(source, NamedTextColor.RED));
		
		if (!perm) {
			banTchatMsg = banTchatMsg.append(Component.text(" for ", NamedTextColor.YELLOW))
			.append(Component.text(amountUnitToString(amount, unit), NamedTextColor.RED));
		}
		
		banTchatMsg = banTchatMsg.append(Component.text(". Reason: ", NamedTextColor.YELLOW))
		.append(Component.text(reason, NamedTextColor.RED));
		
		return banTchatMsg;
	}
	
	public static TextComponent getBanTchatMessage(String playerName, String reason, String source) {
		return getBanTchatMessage(playerName, reason, source, 0, null);
	}
	
	public static String amountUnitToString(int amount, String unit) {
		String s = Integer.toString(amount);
		
		unit = unit.toUpperCase();
		switch (unit) {
		    case "H":
		    	s += " hour";
		        break;
	
		    case "D":
		    	s += " day";
		        break;
	
		    case "M":
		    	s += " month";
		        break;
	
		    case "Y":
		    	s += " year";
		        break;
		}
		
		if (amount > 1) {
			s += "s";
		}
		return s;
	}
}

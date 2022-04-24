package fr.Fozl.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class BanTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) { 
		ArrayList<String> ret = new ArrayList<String>();
		
		if (command.getName().equalsIgnoreCase("ban")) {
			if (args.length == 1) {
				Bukkit.getOnlinePlayers().forEach(p -> ret.add(p.getName()));
			}
			else if (args.length == 3 && StringUtils.isNumeric(args[1])) {
				Collections.addAll(ret, "h", "d", "m", "y");
			}
		}
		else {
			return null;
		}
		return ret;
	}

}

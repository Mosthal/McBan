package fr.Fozl.Commands;

import org.bukkit.command.CommandSender;

import net.kyori.adventure.text.TextComponent;

public abstract class CommandAbstract {
	
	private final TextComponent HELP_MSG;
	private final TextComponent USAGE_MSG;
	
	public CommandAbstract(TextComponent HELP_MSG, TextComponent USAGE_MSG) {
		this.HELP_MSG = HELP_MSG;
		this.USAGE_MSG = USAGE_MSG;
	}
	
	public void sendHelpMessage(CommandSender sender) {
		sender.sendMessage(HELP_MSG);
	}
	
	public void sendUsageMessage(CommandSender sender) {
		sender.sendMessage(USAGE_MSG);
	}
}

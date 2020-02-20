package me.AlanZ.CommandMineRewards.commands.special;

import java.util.StringJoiner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageCommand extends SpecialCommand {
	// special commands are commands that aren't designed to be used in-game except for testing purposes
	@Override
	public String getName() {
		return "msg";
	}

	@Override
	public String getBasicDescription() {
		return "Special command, used in reward commands.";
	}

	@Override
	public String getExtensiveDescription() {
		return "Simply sends the specified message. Color codes are supported. When used in-game, sends message to the command sender. When used in rewards, sends to the reward recipient.";
	}
	
	@Override
	public String getUsage() {
		return "<message>";
	}

	@Override
	public boolean isModifier() {
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			this.getPlugin().getLogger().warning(ChatColor.RED + "Command error: Not enough args in /cmr " + getName() + " " + args);
			return true;
		}
		StringJoiner message = new StringJoiner(" ");
		for (String arg : args) {
			message.add(arg);
		}
		String colorized = ChatColor.translateAlternateColorCodes('&', message.toString());
		sender.sendMessage(colorized);
		return true;
	}
}
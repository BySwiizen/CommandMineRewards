package me.AlanZ.CommandMineRewards.commands.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.AlanZ.CommandMineRewards.Reward;
import me.AlanZ.CommandMineRewards.Exceptions.CommandAlreadyInListException;
import me.AlanZ.CommandMineRewards.Exceptions.InvalidRewardException;
import me.AlanZ.CommandMineRewards.Exceptions.InvalidRewardSectionException;
import me.AlanZ.CommandMineRewards.commands.ArgType;

public class CmdInsertCommand extends CmdCommand {
	@Override
	public String getName() {
		return "insert";
	}
	@Override
	public String getBasicDescription() {
		return "Inserts a reward command";
	}

	@Override
	public String getExtensiveDescription() {
		return "Inserts a reward command at a specific index in the list. If you need to run the commands in a certain order, you can use this.";
	}

	@Override
	public String getUsage() {
		return "<rewardSection> <reward> <index> <command>";
	}

	@Override
	public int getMinArgs() {
		return 4;
	}

	@Override
	public int getMaxArgs() {
		return -1;
	}

	@Override
	public ArgType[] getArgs() {
		return new ArgType[] {ArgType.REWARD_SECTION, ArgType.REWARD};
	}
	@Override
	public boolean isModifier() {
		return true;
	}
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		String rewardSection = args[0];
		String reward = args[1];
		int index;
		try {
			index = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + args[2] + " is not a valid number!");
			return true;
		}
		String command = parseCommand(3, args);
		try {
			new Reward(rewardSection, reward).insertCommand(command, index);
		} catch (InvalidRewardSectionException | InvalidRewardException | ArrayIndexOutOfBoundsException | CommandAlreadyInListException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			return true;
		}
		sender.sendMessage(SUCCESS);
		return true;
	}
}
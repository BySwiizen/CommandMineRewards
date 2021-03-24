package me.Datatags.CommandMineRewards.commands.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Datatags.CommandMineRewards.CMRPermission;
import me.Datatags.CommandMineRewards.Reward;
import me.Datatags.CommandMineRewards.Exceptions.InvalidRewardException;
import me.Datatags.CommandMineRewards.Exceptions.InvalidRewardGroupException;
import me.Datatags.CommandMineRewards.commands.ArgType;
import me.Datatags.CommandMineRewards.commands.RewardCommandEntry;

public class CmdRunAsMeCommand extends CmdCommand {
	@Override
	public String getName() {
		return "runasme";
	}
	@Override
	public String getBasicDescription() {
		return "Runs all commands in the reward as you.";
	}

	@Override
	public String getExtensiveDescription() {
		return "All commands in the reward are executed as if you'd typed them yourself. Specify an index to only run the command with that index.";
	}

	@Override
	public String getUsage() {
		return "<rewardSection> <reward> [index]";
	}
	
	@Override
	public String[] getExamples() {
		return new String[] {"genericRewards bigReward 0", "genericRewards smallReward"};
	}

	@Override
	public int getMinArgs() {
		return 2;
	}

	@Override
	public int getMaxArgs() {
		return 3;
	}
	@Override
	public CMRPermission getPermission() {
		return CMRPermission.COMMAND_EXECUTE;
	}
	@Override
	public ArgType[] getArgs() {
		return new ArgType[] {ArgType.REWARD_SECTION, ArgType.REWARD};
	}
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		String rewardSection = args[0];
		String rewardName = args[1];
		int index = -1;
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command can only be used as a player!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length > 2) {
			try {
				index = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Invalid index: " + args[2]);
			}
		}
		Reward reward;
		try {
			reward = new Reward(rewardSection, rewardName);
		} catch (InvalidRewardGroupException | InvalidRewardException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			return true;
		}
		if (index >= reward.getCommands().size()) {
			sender.sendMessage(ChatColor.RED + "No command with index " + index);
			return true;
		}
		if (index == -1) {
			for (RewardCommandEntry rce : reward.getCommands()) {
				rce.execute(player, player);
			}
		} else {
			reward.getCommands().get(index).execute(player, player);
		}
		sender.sendMessage(SUCCESS);
		return true;
	}
}

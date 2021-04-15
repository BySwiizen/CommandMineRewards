package me.datatags.commandminerewards.gui;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;

import me.datatags.commandminerewards.gui.guis.CMRGUI;

public class GUIUserHolder {
	private UUID owner;
	private Set<UUID> helpers = new HashSet<>();
	private CMRGUI currentGUI;
	public GUIUserHolder(Player owner, CMRGUI gui) {
		this.owner = owner.getUniqueId();
		this.currentGUI = gui;
	}
	public void addHelper(Player helper) {
		helpers.add(helper.getUniqueId());
		if (currentGUI == null) { // in a conversation
			helper.sendMessage(ChatColor.YELLOW + Bukkit.getPlayer(owner).getName() + " is currently answering a text prompt. Please wait.");
		} else {
			updateGUI();
		}
	}
	public void removeHelper(Player helper) {
		helpers.remove(helper.getUniqueId());
	}
	public CMRGUI getGUI() {
		return currentGUI;
	}
	public void changeGUI(CMRGUI gui) {
		currentGUI = gui;
	}
	public void updateGUI() {
		if (currentGUI == null) return;
		currentGUI = currentGUI.refreshSelf(Bukkit.getPlayer(owner));
	}
	public boolean isConversing() {
		return currentGUI == null;
	}
	public void setConversation(Conversation convo) {
		currentGUI = null;
		convo.getContext().setSessionData("userholder", this);
	}
	public UUID getOwner() {
		return owner;
	}
	public Set<UUID> getHelpers() {
		return helpers;
	}
	public boolean containsUser(Player player) {
		if (owner.equals(player.getUniqueId())) return true;
		for (UUID helper : helpers) {
			if (helper.equals(player.getUniqueId())) return true;
		}
		return false;
	}
	public void clear() {
		Bukkit.getPlayer(owner).closeInventory();
		for (UUID helper : helpers) {
			Bukkit.getPlayer(helper).closeInventory();
		}
	}
}

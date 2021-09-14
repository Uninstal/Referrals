package org.uninstal.referral.commands;

import org.bukkit.command.CommandSender;

public abstract class AbstractCommand {

	private boolean console;
	private int minArgs;
	
	public AbstractCommand(boolean console, int minArgs) {
		this.console = console;
		this.minArgs = minArgs;
	}
	
	public int getMinArgs() {
		return minArgs;
	}
	
	public boolean canSendFromConsole() {
		return console;
	}
	
	public abstract boolean run(CommandSender sender, String[] args);
}

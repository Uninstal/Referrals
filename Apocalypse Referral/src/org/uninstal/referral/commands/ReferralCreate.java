package org.uninstal.referral.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.uninstal.referral.Main;
import org.uninstal.referral.data.ReferralData;
import org.uninstal.referral.util.Values;

public class ReferralCreate extends AbstractCommand {

	public ReferralCreate(boolean console, int minArgs) {
		super(console, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("§cOnly players.");
			return false;
		}
		
		Player player = (Player) sender;
		String code = args[1];
		
		if(Main.getInstance().data.containsKey(player.getName())) {
			
			sender.sendMessage(Values.ERRORS_ALREADY_CREATE);
			return false;
		}
		
		if(code.length() != 6) {
			
			sender.sendMessage(Values.ERRORS_LENGHT);
			return false;
		}
		
		ReferralData dat = new ReferralData(
				player.getUniqueId(), 
				player.getName(), 
				player.getAddress().getHostName(), 
				code);
		
		Main.getInstance().data.put(player.getName(), dat);
		Main.getInstance().db.save(dat);
		
		sender.sendMessage(Values.DEFAULTS_CREATE.replace("<code>", code));
		return false;
	}
}

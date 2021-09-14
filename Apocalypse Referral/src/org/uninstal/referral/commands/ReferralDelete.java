package org.uninstal.referral.commands;

import org.bukkit.command.CommandSender;
import org.uninstal.referral.Main;
import org.uninstal.referral.data.ReferralData;
import org.uninstal.referral.util.Values;

public class ReferralDelete extends AbstractCommand {

	public ReferralDelete(boolean console, int minArgs) {
		super(console, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		ReferralData dat = Main.getInstance().data.get(sender.getName());
		String code = args[1];
		
		if(code.equals(dat.getCode())) {
			
			Main.getInstance().data.remove(sender.getName());
			Main.getInstance().db.delete(dat);
			
			sender.sendMessage(Values.DEFAULTS_DELETE);
			return false;
		}
		
		else {
			
			sender.sendMessage(Values.ERRORS_NULL_REFERRAL);
			return false;
		}
	}
}

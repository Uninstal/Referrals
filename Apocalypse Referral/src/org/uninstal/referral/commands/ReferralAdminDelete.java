package org.uninstal.referral.commands;

import org.bukkit.command.CommandSender;
import org.uninstal.referral.Main;
import org.uninstal.referral.data.ReferralData;

public class ReferralAdminDelete extends AbstractCommand {

	public ReferralAdminDelete(boolean console, int minArgs) {
		super(console, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		String ownerRef = args[2];
		ReferralData dat = Main.getInstance().data.get(ownerRef);
		
		if(dat != null) {
			
			Main.getInstance().data.remove(dat.getOwnerReferralName());
			Main.getInstance().db.delete(dat);
			
			sender.sendMessage("§7Реферальный код игрока §a" + dat.getOwnerReferralName() + "§7 был удален.");
			return false;
		}
		
		else {
			
			sender.sendMessage("§cИгрок §a" + ownerRef + "§c не имеет реферального кода.");
			return false;
		}
	}
}

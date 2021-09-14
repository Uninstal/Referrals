package org.uninstal.referral.commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.uninstal.referral.Main;
import org.uninstal.referral.data.QueueReport;
import org.uninstal.referral.data.ReferralData;
import org.uninstal.referral.util.LogUtil;
import org.uninstal.referral.util.Values;

public class ReferralSend extends AbstractCommand {

	public ReferralSend(boolean console, int minArgs) {
		super(console, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		Player player = (Player) sender;
		String code = args[0];
		
		if(code.length() > 6 || code.length() < 6) {
			
			sender.sendMessage(Values.ERRORS_NULL_TARGET);
			return false;
		}
		
		else {
			
			Map<String, ReferralData> map = Main.getInstance().data;
			ReferralData target = null;
			
			//Поиск человека с этим кодом
			for(ReferralData d : map.values()){
				if(d.getCode().equals(code)) {
					target = d;
					break;
				}
			}
			
			if(target == null) {
				
				sender.sendMessage(Values.ERRORS_NULL_TARGET);
				return false;
			}
			
			else {
				
				String IP = target.getLastIP();
				String tIP = player.getAddress().getHostName();
				
//				//Защита от новичков
//				int playTime = PlayerUtil.tH(player);
//				int walk = PlayerUtil.w(player);
//				
//				if(playTime < Values.PROTECTION_TIME
//						|| walk < Values.PROTECTION_WALK) {
//					
//					sender.sendMessage(Values.ERRORS_DISALLOW);
//					return false;
//				}
				
				//Проверка на использованность
				for(ReferralData t : map.values())
					if(t.getUsers().contains(player.getUniqueId())) {
						sender.sendMessage(Values.ERRORS_ALREADY_SEND);
						return false;
					}
				
				//Проверка схожести айпи
				if(IP.equalsIgnoreCase(tIP)) {
					
					//Игрок пытался использовать рефералку
					//повторно на твинке
					sender.sendMessage(Values.ERRORS_IP);
					
					//Логирование попытки обхода
					Main.getInstance().reports
					    .put(player.getUniqueId(), 
					    new QueueReport(player.getUniqueId(), 
					    "&7Игрок &e" + player.getName() + "&7 пытался "
					    + "использовать рефельный код на &cдругом аккаунте&7!"));
					
					//Логирование для онлайн админов
					LogUtil.o(
							"&7Игрок &e" + player.getName() + "&7 пытался "
						    + "использовать рефельный код на &cдругом аккаунте&7!");
					
					return false;
				}
				
				target.send(player.getUniqueId());
				player.sendMessage(Values.SEND_SENDER);
				
				Player owner = Bukkit.getPlayer(target.getOwnerReferral());
				if(owner != null) owner.sendMessage(Values.SEND_TARGET.replace("<user>", player.getName()));
					
				return false;
			}
		}
	}
}

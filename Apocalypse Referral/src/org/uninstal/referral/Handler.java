package org.uninstal.referral;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.uninstal.referral.data.QueueReport;
import org.uninstal.referral.data.ReferralData;

public class Handler implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		String name = player.getName();
		UUID uuid = player.getUniqueId();
		
		if(!player.isOp()) {
			
			ReferralData dat = Main.getInstance().data.get(name);
			if(dat == null) return;
			if(!dat.getOwnerReferral().equals(uuid)) return;
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					String IP = player.getAddress().getHostName();
					if(!dat.getLastIP().equalsIgnoreCase(IP)) 
						dat.updateIP(IP);
				}
				
			}.runTaskAsynchronously(Main.getInstance());
			
			return;
		}
		
		else if(Main.getInstance().reports.size() != 0) {
			
			List<QueueReport> rep = 
			Main.getInstance().reports.values()
			.stream().collect(Collectors.toList());
					
			for(int k = 0; k < rep.size(); k++)
				rep.get(k).send(player, k+1);
		}
	}
}

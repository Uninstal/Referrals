package org.uninstal.referral.data;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.uninstal.referral.Main;

public class QueueReport {

	private UUID uuid;
	private String text;
	private int k = 0;

	public QueueReport(UUID reported, String text) {
		this.uuid = reported;
		this.text = text.replace("&", "§");
	}
	
	public void send(Player op, int position_send) {
		op.sendMessage("§c#" + position_send + "§r " + text);
		k++;
		
		if(k == 3)
			Main.getInstance().reports
			.remove(uuid);
	}
}

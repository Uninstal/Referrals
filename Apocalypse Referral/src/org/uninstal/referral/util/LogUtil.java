package org.uninstal.referral.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LogUtil {

	public static void a(String text) {
		
		c(text);
		o(text);
	}
	
	public static void c(String text) {
		
		Bukkit.getConsoleSender()
		.sendMessage(text
				.replace("&", "§"));
	}
	
	public static void o(String text) {
		
		for(Player op : Bukkit.getOnlinePlayers()) {
			
			if(op.isOp()) {
				
				text = text
						.replace("&", "§")
						.replace("\n", System.lineSeparator());
				
				op.sendMessage(text);
				op.playSound(op.getLocation(), Sound.BLOCK_NOTE_PLING, 10f, 1f);
			}
		}
	}
}

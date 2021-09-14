package org.uninstal.referral.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class PlayerUtil {

	public static int tH(Player player) {
		
		int time = player.getStatistic(Statistic.PLAY_ONE_TICK);
		int h = time / 20 / 60 / 60;
		
		return h;
	}
	
	public static int w(Player player) {
		return player.getStatistic(Statistic.WALK_ONE_CM);
	}
	
    public static String n(UUID uuid) {
		
		OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(uuid);
		return offPlayer.getName();
	}
}

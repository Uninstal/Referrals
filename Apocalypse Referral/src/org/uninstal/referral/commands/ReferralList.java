package org.uninstal.referral.commands;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.uninstal.referral.Main;
import org.uninstal.referral.data.ReferralData;
import org.uninstal.referral.util.ComponentHolders;
import org.uninstal.referral.util.PlayerUtil;
import org.uninstal.referral.util.SuperComponentBuilder;
import org.uninstal.referral.util.TextUtil;
import org.uninstal.referral.util.Values;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class ReferralList extends AbstractCommand {
	
	public ReferralList(boolean console, int minArgs) {
		super(console, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		Map<String, ReferralData> map = Main.getInstance().data;
		ReferralData dat = map.get(sender.getName());
		
		try {
			
			if(dat != null) {
				
				int page = args.length == 1 ? 1 : Integer.valueOf(args[1]);
				List<Object> fp = TextUtil.fFN(dat.getUsers().stream().collect(Collectors.toList()), page * 10 - 9, page * 10);
				
				if(sender instanceof Player) {
					sender.sendMessage(Values.FORMATS_LIST.get(0)); //head
					
					for(Object j : fp) {
						
						UUID uuid = (UUID) j;
						String name = PlayerUtil.n(uuid);
						String date = dat.getUsersWithDate().get(uuid);
						
						String text = Values.FORMATS_LIST.get(1);
						text = text.replace("<user>", name);
						text = text.replace("<date>", date);
						text = text.replace("<status>", dat.isConfirmed(uuid)
						? "[Реферал]" : "[Новичок]");
						
						if(sender instanceof Player) {
							
							ComponentHolders ch = new ComponentHolders();
							ch.component("%" + text + "%", text, null, new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(date)));
							SuperComponentBuilder builder = new SuperComponentBuilder("%" + text + "%", ch);
							
							BaseComponent[] b = builder.build().getBuildedComponent();
							((Player) sender).spigot().sendMessage(b);
						}
						
						else sender.sendMessage(text);
					}
					
					return false;
				}
			}
			
			else {
				
				sender.sendMessage(Values.ERRORS_NULL_REFERRAL); //code...
				return false;
			}
			
		} catch (NumberFormatException e) {
			
			int page = 1;
			List<Object> fp = TextUtil.fFN(dat.getUsers().stream().collect(Collectors.toList()), page * 10 - 9, page * 10);
			
			if(sender instanceof Player) {
				sender.sendMessage(Values.FORMATS_LIST.get(0)); //head
				
				for(Object j : fp) {
					
					UUID uuid = (UUID) j;
					String name = PlayerUtil.n(uuid);
					String date = dat.getUsersWithDate().get(uuid);
					
					String text = Values.FORMATS_LIST.get(1);
					text = text.replace("<user>", name);
					text = text.replace("<date>", date);
					text = text.replace("<status>", dat.isConfirmed(uuid)
					? "[Реферал]" : "[Новичок]");
					
					if(sender instanceof Player) {
						
						ComponentHolders ch = new ComponentHolders();
						ch.component("%" + text + "%", text, null, new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(date)));
						SuperComponentBuilder builder = new SuperComponentBuilder("%" + text + "%", ch);
						
						BaseComponent[] b = builder.build().getBuildedComponent();
						((Player) sender).spigot().sendMessage(b);
					}
					
					else sender.sendMessage(text);
				}
				
				return false;
			}
		}
		
		return false;
	}
}

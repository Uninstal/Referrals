package org.uninstal.referral.commands;

import java.util.HashMap;
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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class ReferralAdminList extends AbstractCommand {

	public ReferralAdminList(boolean console, int minArgs) {
		super(console, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		//all datas
		Map<String, ReferralData> map = new HashMap<>(Main.getInstance().data);
		
		try {
			
			if(args.length == 2) {
				
				String head = Values.FORMATS_ADMIN_LIST.get(0);
				sender.sendMessage(head);
				
				for(ReferralData dat : map.values()) {
					
					String msg = Values.FORMATS_ADMIN_LIST.get(1);
					msg = msg.replace("<user>", dat.getOwnerReferralName());
					msg = msg.replace("<refs>", String.valueOf(dat.getUsers().size()));
					msg = msg.replace("<code>", dat.getCode());
					
					if(sender instanceof Player) {
						
						ComponentHolders ch = new ComponentHolders();
						SuperComponentBuilder scb = null;
						
						ch.component("%" + msg + "%", msg, new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/ref admin list " + dat.getOwnerReferralName()), null);
						scb = new SuperComponentBuilder("%" + msg + "%", ch);
						
						Player player = (Player) sender;
						player.spigot().sendMessage(scb.build().getBuildedComponent());
					}
					
					else sender.sendMessage(msg);
				}
				
				return false;
			}
			
			else {
				
				String targetName = args[2];
				ReferralData dat = map.get(targetName);
				
				if(dat != null) {
					sender.sendMessage(Values.FORMATS_LIST.get(0) + " §8(" + dat.getOwnerReferralName() + "§8)");
					
					int page = args.length == 3 ? 1 : Integer.valueOf(args[3]);
					List<Object> fp = TextUtil.fFN(dat.getUsers().stream().collect(Collectors.toList()), page * 10 - 9, page * 10);
					
					for(Object j : fp) {
						
						UUID uuid = (UUID) j;
						String name = PlayerUtil.n(uuid);
						String date = dat.getUsersWithDate().get(uuid);
						
						String msg = Values.FORMATS_LIST.get(1);
						msg = msg.replace("<user>", name);
						msg = msg.replace("<date>", date);
						msg = msg.replace("<status>", dat.isConfirmed(uuid)
						? "[Реферал]" : "[Новичок]");
						
						if(sender instanceof Player) {
							
							ComponentHolders ch = new ComponentHolders();
							ch.component("%" + msg + "%", msg, null, new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(date)));
							SuperComponentBuilder builder = new SuperComponentBuilder("%" + msg + "%", ch);
							
							BaseComponent[] b = builder.build().getBuildedComponent();
							((Player) sender).spigot().sendMessage(b);
						}
						
						else sender.sendMessage(msg);
					}
					
					return false;
				}
			}
			
		} catch (NumberFormatException e) {
			
			String head = Values.FORMATS_ADMIN_LIST.get(0);
			sender.sendMessage(head);
			
			for(ReferralData dat : map.values()) {
				
				String msg = Values.FORMATS_ADMIN_LIST.get(1);
				msg = msg.replace("<user>", dat.getOwnerReferralName());
				msg = msg.replace("<refs>", String.valueOf(dat.getUsers().size()));
				
				if(sender instanceof Player) {
					
					ComponentHolders ch = new ComponentHolders();
					SuperComponentBuilder scb = null;
					
					ch.component("%" + msg + "%", msg, new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/ref admin list " + dat.getOwnerReferralName()), null);
					scb = new SuperComponentBuilder("%" + msg + "%", ch);
					
					Player player = (Player) sender;
					player.spigot().sendMessage(scb.build().getBuildedComponent());
				}
				
				else sender.sendMessage(msg);
			}
			
			return false;
		}
		
		return false;
	}
}

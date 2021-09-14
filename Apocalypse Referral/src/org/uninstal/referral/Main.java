package org.uninstal.referral;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.uninstal.referral.commands.AbstractCommand;
import org.uninstal.referral.commands.ReferralAdminDelete;
import org.uninstal.referral.commands.ReferralAdminList;
import org.uninstal.referral.commands.ReferralCreate;
import org.uninstal.referral.commands.ReferralList;
import org.uninstal.referral.commands.ReferralSend;
import org.uninstal.referral.data.QueueReport;
import org.uninstal.referral.data.ReferralData;
import org.uninstal.referral.db.CombineDatabaseTables;
import org.uninstal.referral.db.Database;
import org.uninstal.referral.util.DatabaseUtil;
import org.uninstal.referral.util.PlayerUtil;
import org.uninstal.referral.util.Values;

public class Main extends JavaPlugin {

	private static Main instance = null;
	
	public Map<String, AbstractCommand> cmds = new HashMap<>();;
	public CombineDatabaseTables db = null;
	public Map<String, ReferralData> data = new ConcurrentHashMap<>();
	public Map<UUID, QueueReport> reports = new ConcurrentHashMap<>();
	public Files files = null;
	
	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		
		try {
			
			//Данные главного класса
			instance = this;
			
			//Загрузка файлов
			this.files = new Files(this);
			YamlConfiguration config = files.registerNewFile("config");
			
			//Загрузка значений из конфига
			Values v = new Values();
			v.setUsedConfig(config).read();
			
			//Подключение базы данных
			String basename = Values.MYSQL_BASENAME;
			String host = Values.MYSQL_HOST;
			String user = Values.MYSQL_USER;
			String password = Values.MYSQL_PASSWORD;
			
			String table_data = Values.MYSQL_TABLE_DATA;
			String table_users = Values.MYSQL_TABLE_USERS;
			
			Database db = new Database(host, basename, user, password);
			db.createTable(table_data, DatabaseUtil.t("uuid VARCHAR(36) PRIMARY KEY", "name VARCHAR(15)", "lastIP VARCHAR(90)", "code VARCHAR(6)"));
			db.createTable(table_users, DatabaseUtil.t("uuid VARCHAR(36)", "user VARCHAR(36)", "date VARCHAR(50)", "confirm INT"));
			
			this.db = new CombineDatabaseTables(db, table_data, table_users);
			this.db.loadAll();
			
			//Регистрация команд
			this.cmds = new HashMap<>();
			this.cmds.put("send", new ReferralSend(false, 1));
			//this.cmds.put("delete", new ReferralDelete(false, 2));
			this.cmds.put("create", new ReferralCreate(false, 2));
			this.cmds.put("list", new ReferralList(false, 1));
			this.cmds.put("admin list", new ReferralAdminList(true, 2));
			this.cmds.put("admin delete", new ReferralAdminDelete(true, 3));
			
			//Регистрация событий
			Bukkit.getPluginManager().registerEvents(new Handler(), this);
			
			//Чекер
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					for(ReferralData dat : data.values()) {
						
						for(UUID uuid : dat.getUsers()) {
							if(dat.isConfirmed(uuid)) continue;
							
							Player target = Bukkit.getPlayer(uuid);
							if(target == null) continue;
							
							int hours = PlayerUtil.tH(target);
							int walk = PlayerUtil.w(target);
							
							if(hours >= Values.PROTECTION_TIME
									&& walk >= Values.PROTECTION_WALK) {
								
								for(String cmd : Values.COMMANDS_SENDER)
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
											cmd.replace("<user>", target.getName()));
								
								for(String cmd : Values.COMMANDS_TARGET)
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
											cmd.replace("<user>", dat.getOwnerReferralName()));
								
								dat.confirm(uuid);
								continue;
							}
						}
					}
				}
				
			}.runTaskTimer(this, 20L, 20L);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void onDisable() {
		db.saveAll();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("ref")) {
			
			if(args.length == 0) {
				
				ReferralData dat = data.get(sender.getName());
				String code = dat == null ? "отсутствует" : dat.getCode();
				
				sender.sendMessage(Values.DEFAULTS_HELP.replace("<mycode>", code));
				if(sender.isOp()) sender.sendMessage(Values.DEFAULTS_ADMIN_HELP);
				
				return false;
			}
			
			String arg = args[0].equalsIgnoreCase("admin")
					&& args.length > 1
					? args[0].toLowerCase() + " " 
					+ args[1].toLowerCase()
					: args[0].toLowerCase();
					
			AbstractCommand abstractCommand = cmds.containsKey(arg) ? cmds.get(arg) : cmds.get("send");
			
			if(abstractCommand.getMinArgs() > args.length) {
				
				ReferralData dat = data.get(sender.getName());
				String code = dat == null ? "отсутствует" : dat.getCode();
				
				sender.sendMessage(Values.DEFAULTS_HELP.replace("<mycode>", code));
				if(sender.isOp()) sender.sendMessage(Values.DEFAULTS_ADMIN_HELP);
				
				return false;
			}
			
			else if(sender instanceof Player && abstractCommand.canSendFromConsole()) abstractCommand.run(sender, args);
			else abstractCommand.run(sender, args);
		}
		
		return false;
	}
}

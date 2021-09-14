package org.uninstal.referral.util;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Values {

	private YamlConfiguration config;

	public Values setUsedConfig(YamlConfiguration configuration) {
		this.config = configuration;
		return this;
	}
	
	public void read() {
		
		MYSQL_BASENAME         = config.getString("settings.mysql.basename").replace("&", "§");
		MYSQL_PASSWORD         = config.getString("settings.mysql.password").replace("&", "§");
		MYSQL_HOST             = config.getString("settings.mysql.host").replace("&", "§");
		MYSQL_USER             = config.getString("settings.mysql.user").replace("&", "§");
		MYSQL_TABLE_DATA       = config.getString("settings.mysql.table-data").replace("&", "§");
		MYSQL_TABLE_USERS      = config.getString("settings.mysql.table-users").replace("&", "§");
		COMMANDS_SENDER        = config.getStringList("settings.commands.sender");
		COMMANDS_TARGET        = config.getStringList("settings.commands.target");
		PROTECTION_TIME        = config.getInt("settings.protection.time");
		PROTECTION_WALK        = config.getInt("settings.protection.walk");
		DEFAULTS_HELP          = config.getString("messages.defaults.help").replace("&", "§");
		DEFAULTS_ADMIN_HELP    = config.getString("messages.defaults.admin-help").replace("&", "§");
		DEFAULTS_CREATE        = config.getString("messages.defaults.create").replace("&", "§");
		DEFAULTS_DELETE        = config.getString("messages.defaults.delete").replace("&", "§");
		FORMATS_LIST           = config.getStringList("messages.formats.list");
		FORMATS_ADMIN_LIST     = config.getStringList("messages.formats.admin-list");
		ERRORS_ALREADY_CREATE  = config.getString("messages.errors.already-create").replace("&", "§");
		ERRORS_ALREADY_SEND    = config.getString("messages.errors.already-send").replace("&", "§");
		ERRORS_IP              = config.getString("messages.errors.ip").replace("&", "§");
		ERRORS_DISALLOW        = config.getString("messages.errors.disallow").replace("&", "§");
		ERRORS_NULL_TARGET     = config.getString("messages.errors.null-target").replace("&", "§");
		ERRORS_NULL_REFERRAL   = config.getString("messages.errors.null-referral").replace("&", "§");
		ERRORS_LENGHT          = config.getString("messages.errors.lenght").replace("&", "§");
		SEND_SENDER            = config.getString("messages.defaults.send.sender").replace("&", "§");
		SEND_TARGET            = config.getString("messages.defaults.send.target").replace("&", "§");
		
		FORMATS_LIST.replaceAll(t -> t.replace("&", "§"));
		FORMATS_ADMIN_LIST.replaceAll(t -> t.replace("&", "§"));
	}
	
	public static String MYSQL_BASENAME;
	public static String MYSQL_PASSWORD;
	public static String MYSQL_HOST;
	public static String MYSQL_USER;
	public static String MYSQL_TABLE_DATA;
	public static String MYSQL_TABLE_USERS;
	public static List<String> COMMANDS_SENDER;
	public static List<String> COMMANDS_TARGET;
	public static int PROTECTION_TIME;
	public static int PROTECTION_WALK;
	public static String DEFAULTS_HELP;
	public static String DEFAULTS_ADMIN_HELP;
	public static String DEFAULTS_CREATE;
	public static String DEFAULTS_DELETE;
	public static List<String> FORMATS_LIST;
	public static List<String> FORMATS_ADMIN_LIST;
	public static String ERRORS_ALREADY_CREATE;
	public static String ERRORS_ALREADY_SEND;
	public static String ERRORS_IP;
	public static String ERRORS_DISALLOW;
	public static String ERRORS_NULL_TARGET;
	public static String ERRORS_NULL_REFERRAL;
	public static String ERRORS_LENGHT;
	public static String SEND_SENDER;
	public static String SEND_TARGET;
}

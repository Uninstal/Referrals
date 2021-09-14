package org.uninstal.referral.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.uninstal.referral.Main;
import org.uninstal.referral.data.ReferralData;

public class CombineDatabaseTables {

	private Database db;
	private String table_data;  //Таблица для данных рефералки
	private String table_users; //Таблица, хранящая использовавших
	                            //рефералку кого-либо
	
	private String types_data = "uuid, name, lastIP, code";
	private String types_users = "uuid, user, date, confirm";
	
	public CombineDatabaseTables(Database db, String table_data, String table_users) {
		
		this.db = db;
		this.table_data = table_data;
		this.table_users = table_users;
	}
	
	//Загрука всех данных
	public void loadAll() throws SQLException {
		long startMS = System.currentTimeMillis();
		
		ResultSet set_data = db.get(table_data);
		ReferralData dat = null;
		
		while(set_data.next()) {
			
			UUID uuid = UUID.fromString(set_data.getString(1));
			String name = set_data.getString(2);
			if(Main.getInstance().data.containsKey(name)) continue;
			
			String IP = set_data.getString(3);
			String code = set_data.getString(4);
			
			dat = new ReferralData(uuid, name, IP, code);
			
			ResultSet set_users = db.get(table_users, "uuid", uuid.toString());
			Map<UUID, String> users = new HashMap<>();
			
			while(set_users.next()) {
				
				UUID user = UUID.fromString(set_users.getString(2));
				String date = set_users.getString(3);
				int confirm = set_users.getInt(4);
				
				users.put(user, date);
				if(confirm == 1) dat.confirm(user);
			}
			
			set_users.close();
			
			dat.setData(users);
			Main.getInstance().data.put(name, dat);
			
			continue;
		}
		set_data.close();
		
		Bukkit.getConsoleSender().sendMessage("§aDatas load with " + (System.currentTimeMillis() - startMS) + "ms");
	}
	
	//Для сохранения при выключении\автосохранении
	public void saveAll() {
		long startMS = System.currentTimeMillis();
		
		db.deleteFull(table_users);
		db.deleteFull(table_data);
		
		for(ReferralData dat : Main.getInstance().
				data.values())
			save(dat);
		
		Bukkit.getConsoleSender().sendMessage("§aDatas save with " + (System.currentTimeMillis() - startMS) + "ms");
	}
	
	//Для одиночных сохранений
	public void save(ReferralData dat) {
		
		String values_data = "'" + dat.getOwnerReferral().toString() + "', '" + dat.getOwnerReferralName() + "', '" +
				             dat.getLastIP() + "', '" + dat.getCode() + "'";
		
		for(Entry<UUID, String> e : dat.getUsersWithDate().entrySet()) {
			
			UUID user = e.getKey();
			String date = e.getValue();
			
			String values_users = "'" + dat.getOwnerReferral().toString() + "', '" + user.toString() + "', '" + date + "', "
					+ (dat.isConfirmed(user) ? 1 : 0);
			db.send("INSERT INTO " + table_users + " (" + types_users + ") VALUES (" + values_users + ")");
		}
		
		db.send("INSERT INTO " + table_data + " (" + types_data + ") VALUES (" + values_data + ")");
		
		/*LinkedHashMap<String, Object> data = new LinkedHashMap<>();
		
		data.put("uuid", dat.getOwnerReferral().toString());
		data.put("name", dat.getOwnerReferralName());
		data.put("lastIP", dat.getLastIP());
		data.put("code", dat.getCode());
		
		for(Entry<UUID, String> e : dat.getUsersWithDate().entrySet()) {
			
			LinkedHashMap<String, Object> users = new LinkedHashMap<>();
			users.put("uuid", dat.getOwnerReferral().toString());
			users.put("user", e.getKey().toString());
			users.put("date", e.getValue());
			
			db.send(table_users, users);
			continue;
		}
		
		db.send(table_data, data);*/
	}
	
	public void delete(ReferralData dat) {
		
		db.delete(table_users, "uuid", dat.getOwnerReferral().toString());
		db.delete(table_data, "uuid", dat.getOwnerReferral().toString());
	}
}

package org.uninstal.referral.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ReferralData {

	private String lastDynamicIP;
	private String ownerReferralName;
	private UUID ownerReferral;
	private Map<UUID, String> users;
	private Set<UUID> confirmed;
	private String code;
	
	public ReferralData(UUID ownerReferral, String ownerReferralName, String IP, String code) {
		
		this.lastDynamicIP = IP;
		this.ownerReferral = ownerReferral;
		this.ownerReferralName = ownerReferralName;
		this.users = new HashMap<>();
		this.confirmed = new HashSet<>();
		this.code = code;
	}
	
	public ReferralData(UUID ownerReferral, String ownerReferralName, String IP, String code, Set<UUID> confirmed, Map<UUID, String> users) {
		
		this.lastDynamicIP = IP;
		this.ownerReferral = ownerReferral;
		this.ownerReferralName = ownerReferralName;
		this.users = users;
		this.confirmed = confirmed;
		this.code = code;
	}
	
	public void setData(Map<UUID, String> users) {
		this.users = users;
	}
	
	public String getLastIP() {
		return lastDynamicIP;
	}
	
	public String getOwnerReferralName() {
		return ownerReferralName;
	}
	
	public UUID getOwnerReferral() {
		return ownerReferral;
	}
	
	public Set<UUID> getUsers() {
		
		Set<UUID> users = new HashSet<>();
		users.addAll(this.users.keySet());
		users.addAll(this.confirmed);
		
		return users;
	}
	
	public Map<UUID, String> getUsersWithDate() {
		return users;
	}
	
	public String getCode() {
		return code;
	}
	
	public boolean isConfirmed(UUID user) {
		return confirmed.contains(user);
	}
	
	public void confirm(UUID user) {
		confirmed.add(user);
	}
	
	public void updateIP(String newIP) {
		this.lastDynamicIP = newIP;
	}
	
	public void send(UUID sender) {
		
		Date date = new Date(System.currentTimeMillis());
		String format = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
		
		this.users.put(sender, format);
	}
}

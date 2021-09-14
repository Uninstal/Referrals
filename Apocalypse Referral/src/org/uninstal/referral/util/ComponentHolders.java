package org.uninstal.referral.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComponentHolders {
	
	private Map<String, BaseComponent> components = new HashMap<>();

    public ComponentHolders component(String textId, String newName, ClickEvent clickEvent, HoverEvent hoverEvent) {
		
		BaseComponent baseComponent = new TextComponent(newName.replace("&", "§"));
		if(clickEvent != null) baseComponent.setClickEvent(clickEvent);
		if(hoverEvent != null) baseComponent.setHoverEvent(hoverEvent);
		components.put(textId.replace("%", ""), baseComponent);
		
		return this;
	}
    
    public Map<String, BaseComponent> getComponents() {
		return components;
	}
    
    public Set<String> getTextIds(){
    	return components.keySet();
    }
}

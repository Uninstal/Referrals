package org.uninstal.referral.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class SuperComponentBuilder {

	private BaseComponent[] baseComponent;
	
	private String text;
	private ComponentHolders holders;

	public SuperComponentBuilder(String text, ComponentHolders holders) {
		this.text = text;
		this.holders = holders;
	}
	
	public SuperComponentBuilder setHolders(ComponentHolders holders) {
		this.holders = holders;
		return this;
	}
	
	public SuperComponentBuilder build() {
		
		ComponentBuilder builder = new ComponentBuilder(new String());
		String[] split = text.split("%");
		
		for(String cuttedText : split) {
			
			if(holders.getTextIds().contains(cuttedText))
				builder.append(holders.getComponents().get(cuttedText));
			
			else builder.append(cuttedText.replace("&", "§"));
		}
		
		baseComponent = builder.create();
		return this;
	}
	
	public BaseComponent[] getBuildedComponent() {
		return baseComponent;
	}
}

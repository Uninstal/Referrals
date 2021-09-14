package org.uninstal.referral.util;

import java.util.LinkedList;

public class DatabaseUtil {

	public static LinkedList<String> t(String... string){
		
		LinkedList<String> l = new LinkedList<>();
		for(String s : string) l.add(s);
		
		return l;
	}
}

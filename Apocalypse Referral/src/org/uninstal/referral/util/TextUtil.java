package org.uninstal.referral.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

public class TextUtil {
	
	private static Map<UUID, Map<UUID, String>> cache = new HashMap<>();

	public static <T> List<Object> fFN(List<T> list, int s, int e) {
		e = Math.min(e, list.size());
		
		List<Object> newList = new ArrayList<>();
		for(int k = (s - 1); k < e; k++) newList.add(list.get(k));
		
		return newList;
	}
	
	public static LinkedList<String> fT(UUID uuid, Map<UUID, String> map){
		
		//return list
		LinkedList<String> top = new LinkedList<>();
		
		if(cache.containsKey(uuid)) {
			
			
		}
		
		else {
			
			//sorted list
			List<String> list = map.values()
					.stream().sorted(new fT()).collect(Collectors.toList());
			
			//cache
			Map<UUID, String> c = new HashMap<>();
			
			for(String j : list) {
				
				for(Entry<UUID, String> e : map.entrySet())
					if(j.equalsIgnoreCase(e.getValue()))
						c.put(e.getKey(), j);
			}
			
			//save cache data
			cache.put(uuid, c);
		}
		
		return top;
	}
	
	private static class fT implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			
			String[] s1 = o1.split(":");
			String[] s2 = o2.split(":");
			
			int[] f = new int[7];
			
			for(int k = 0; k < 6; k++) {
				
				if(k < 3) f[k] = Integer.valueOf(s1[k]);
				else f[k] = Integer.valueOf(s2[k]);
			}
			
			int j1 = f[0] * f[1] + f[2];
			int j2 = f[3] * f[4] + f[5];
			
			return j2 - j1;
		}
	}
}

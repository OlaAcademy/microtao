package com.xpown.utils;
import java.util.HashMap;
import java.util.Map;

public class MapResult {

	public static Map<String, Object> initMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("apicode", 10000);
		map.put("message", "成功");
		return map;
	}
	
	
	public static Map<String, Object> initMap(Integer apicode, String message) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("apicode", apicode);
		map.put("message", message);
		return map;
	}


	public static Map<String, Object> failMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("apicode", 9999);
		map.put("message", "系统错误，请重试");
		return map;
	}
	
	
	
}

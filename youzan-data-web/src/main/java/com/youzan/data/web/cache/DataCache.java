package com.youzan.data.web.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class DataCache {
	
	//缓存小区数据
	public static List<String> HOUSE_CACHE = new ArrayList<String>(); 
	
	/**
	 * 刷新小区配置
	 * @param house
	 */
	public static void refreshHouse(String house) {
		if(StringUtils.isNotEmpty(house)) {
			String [] houses =  house.split(",");
			HOUSE_CACHE = Arrays.asList(houses);
		}
	}
	
}

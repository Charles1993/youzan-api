package com.youzan.data.service.util;

import java.util.HashMap;
import java.util.Map;

import com.youzan.common.bean.constant.Constants;

public class SUtils {
	
	private static Map<Integer,String> EXPRESS_TYPE = null;

	/**
	 * 转换物流类型名称
	 * @param expressType
	 * @return
	 */
	public static String getExpressType(Integer expressType) {
		return EXPRESS_TYPE.getOrDefault(expressType, Constants.EMPTY);
	}
	
	static {
		EXPRESS_TYPE = new HashMap<Integer,String>();
		EXPRESS_TYPE.put(0, "快递发货");
		EXPRESS_TYPE.put(1, "到店自提");
		EXPRESS_TYPE.put(2, "同城配送");
		EXPRESS_TYPE.put(9, "无需发货（虚拟商品订单）");
	}
}

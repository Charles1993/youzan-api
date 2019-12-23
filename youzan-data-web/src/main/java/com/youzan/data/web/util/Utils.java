package com.youzan.data.web.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetParams;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultAddressinfo;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultFullorderinfo;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultFullorderinfolist;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultRemarkinfo;
import com.youzan.common.util.DateUtil;
import com.youzan.data.api.constant.Constants;
import com.youzan.data.web.cache.DataCache;
import com.youzan.data.web.constant.WebCostants;
import com.youzan.data.web.param.OrderQueryParam;
import com.youzan.data.web.vo.ExportVo;

public class Utils {
	
	/**
	 * 初始化订单查询条件
	 * @param queryParam
	 * @throws ParseException
	 */
	public static YouzanTradesSoldGetParams getTradesSoldGetParam(OrderQueryParam queryParam) throws ParseException {
		YouzanTradesSoldGetParams apiParams = new YouzanTradesSoldGetParams();
		
		//开始时间
		String startCreated = queryParam.getStartTime();
		if(StringUtils.isEmpty(startCreated)) {
			//2019-12-20 11:00:00
			startCreated = DateUtil.date2Str(DateUtil.day(-1)) + " 11:00:00";
		}
		apiParams.setStartCreated(DateUtil.str2Date(startCreated, DateUtil.YYYYMMDDHHMMSS));
		
		//结束时间
		String endCreated = queryParam.getEndTime();
		if(StringUtils.isEmpty(endCreated)) {
			//2019-12-21 10:59:59
			endCreated = DateUtil.date2Str() + " 10:59:59";
		}
		apiParams.setEndCreated(DateUtil.str2Date(endCreated, DateUtil.YYYYMMDDHHMMSS));

		//待发货
		apiParams.setStatus(Constants.WAIT_SELLER_SEND_GOODS);
		return apiParams;
	}
	
	/**
	 *  判断小区是否存在
	 * @param obj
	 * @return
	 */
	public static String isExistHouse(String ...arrays) {
		if(CollectionUtils.isEmpty(DataCache.HOUSE_CACHE)) {
			return null;
		}
		String ret = null;
		for(String str: arrays) {
			ret = isExistHouse(str);
			if(null != ret) {
				return ret;
			}
		}
		return null;
	}
	
	/**
	 *  判断小区是否存在
	 * @param obj
	 * @return
	 */
	public static String isExistHouse(String str) {
		if(StringUtils.isNotEmpty(str)) {
			for(String house: DataCache.HOUSE_CACHE) {
				if(str.contains(house)) {
					return house;
				}
			}
		}
		return null;
	}
	
	public static boolean equals(String a,String b) {
		if(null == a || null == b) {
			return true;
		}
		
		if(StringUtils.isNotEmpty(a) && 
				StringUtils.isNotEmpty(b) && 
				a.trim().equals(b.trim())) {
			return true;
		}
		return false;
	}
}

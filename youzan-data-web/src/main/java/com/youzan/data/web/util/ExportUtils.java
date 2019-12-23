package com.youzan.data.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultAddressinfo;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultBuyerinfo;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultFullorderinfo;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultFullorderinfolist;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultOrderinfo;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultOrders;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultRemarkinfo;
import com.youzan.common.bean.constant.Constants;
import com.youzan.data.api.bean.SelfFetchInfo;
import com.youzan.data.service.util.SUtils;
import com.youzan.data.web.cache.DataCache;
import com.youzan.data.web.constant.WebCostants;
import com.youzan.data.web.vo.ExportVo;

public final class ExportUtils {
	/**
	 * 配送表 -- 导出格式处理
	 * @param list
	 * @return
	 */
	public static Map<String,List<ExportVo>> convertData(List<YouzanTradesSoldGetResultFullorderinfolist> list){
		//临时记录
		Map<String,List<ExportVo>> exportData = new HashMap<String,List<ExportVo>>();
		
		YouzanTradesSoldGetResultFullorderinfo fullOrderInfo = null;
		
		YouzanTradesSoldGetResultAddressinfo addressInfo = null;
		YouzanTradesSoldGetResultRemarkinfo  remarkInfo  = null;
		
		//单个sheet 数据
		List<ExportVo> sheetList2 = null;
		String sheetName = null;
		for(YouzanTradesSoldGetResultFullorderinfolist fullData: list) {
			fullOrderInfo = fullData.getFullOrderInfo();
			//地址信息
			addressInfo = fullOrderInfo.getAddressInfo();
			//备注信息
			remarkInfo = fullOrderInfo.getRemarkInfo();
			
			//Excel sheet Name -- 对应小区关键词
			sheetName = getSheetName(addressInfo,remarkInfo);
			
			//先转换为对象存储，需要排序
			sheetList2 = exportData.getOrDefault(sheetName, new ArrayList<ExportVo>());
			convert(fullOrderInfo,sheetList2);
			exportData.put(sheetName, sheetList2);
		}
		//相同组、地址的放在一起
		sort(exportData);
		return exportData.size() > 1 ? asLinkedHashMap(exportData) : exportData;
	}
	
	/**
	 * 默认sheet 放在最后
	 * @param exportData
	 * @return
	 */
	private static Map<String,List<ExportVo>> asLinkedHashMap(Map<String,List<ExportVo>> exportData){
		Map<String,List<ExportVo>> linkMap = new LinkedHashMap<String,List<ExportVo>>();
		
		Iterator<Entry<String,List<ExportVo>>> sheetList = exportData.entrySet().iterator();
		
		Entry<String,List<ExportVo>> entry = null;
		String key = null;
		
		String lastKey = null;
		List<ExportVo> lastVal = null;
		
		while(sheetList.hasNext()) {
			entry = sheetList.next();
			
			key = entry.getKey();
			//默认sheet ，放在最后
			if(WebCostants.DEFAULT_SHEET_NAME.equals(key)) {
				lastKey = entry.getKey();
				lastVal = entry.getValue();
			}else {
				linkMap.put(key, entry.getValue());
			}
		}
		
		if(null != lastKey) {
			linkMap.put(lastKey, lastVal);
		}
		
		return linkMap;
	}
	
	
	
	/**
	 * //相同组、地址的放在一起
	 * @param exportData
	 */
	private static void sort(Map<String,List<ExportVo>> exportData) {
		Iterator<Entry<String,List<ExportVo>>> sheetList = exportData.entrySet().iterator();
		
		Entry<String,List<ExportVo>> sheetObj = null;
		List<ExportVo> sheetVal = null;
		while(sheetList.hasNext()) {
			sheetObj = sheetList.next();
			sheetVal = sheetObj.getValue();
			
			sheetVal.sort((a,b) -> {
				if(Utils.equals(a.getGroup(), b.getGroup()) && 
						Utils.equals(a.getDeliveryAddress(), b.getDeliveryAddress())) {
					return 0;
				}
				String aVal = StringUtils.isEmpty(a.getDeliveryAddress()) ? Constants.EMPTY : a.getDeliveryAddress();
				String bVal = StringUtils.isEmpty(b.getDeliveryAddress()) ? Constants.EMPTY : b.getDeliveryAddress();
				
				return aVal.compareTo(bVal);
			});
		}
	}
	
	/**
	 * 解析每一行数据
	 * @param fullOrderInfo
	 * @param sheetList2
	 */
	private static void convert(YouzanTradesSoldGetResultFullorderinfo fullOrderInfo,List<ExportVo> sheetList2) {
		
		YouzanTradesSoldGetResultAddressinfo  addressInfo = fullOrderInfo.getAddressInfo();
		YouzanTradesSoldGetResultRemarkinfo   remarkInfo  = fullOrderInfo.getRemarkInfo();
		YouzanTradesSoldGetResultBuyerinfo    buyerInfo   = fullOrderInfo.getBuyerInfo();
		YouzanTradesSoldGetResultOrderinfo    orderInfo   = fullOrderInfo.getOrderInfo();
		List<YouzanTradesSoldGetResultOrders> orderList   = fullOrderInfo.getOrders();
		
		ExportVo exportVo = null;
		SelfFetchInfo sinfo = null;
		
		String selfFetchInfo = addressInfo.getSelfFetchInfo();
		if(StringUtils.isNotEmpty(selfFetchInfo)) {
			sinfo = JSON.parseObject(selfFetchInfo, SelfFetchInfo.class);
		}
		
		//总数量
		Long subTotalNum = orderList.stream().mapToLong(item -> item.getNum()).sum();
		String group = UUID.randomUUID().toString();
		//快递发货
		boolean isExpress = orderInfo.getExpressType().intValue() == 0;
		for(YouzanTradesSoldGetResultOrders order: orderList) {
			exportVo = new ExportVo();
			
			exportVo.setSubTotalNum(subTotalNum.intValue());//设置总数量
			exportVo.setGroup(group);//组
			exportVo.setReceiverName(addressInfo.getReceiverName());//收货人
			exportVo.setDeliveryAddress(addressInfo.getDeliveryAddress());//地址
			exportVo.setFansNickname(buyerInfo.getFansNickname());//买家昵称
			exportVo.setBuyerMessage(remarkInfo.getBuyerMessage());//买家备注
			exportVo.setExpressType(SUtils.getExpressType(orderInfo.getExpressType().intValue()));//物流方式
			exportVo.setSpanRow(orderList.size());//跨行
			exportVo.setReceiverTel(addressInfo.getReceiverTel());//联系电话
			
			if(null != sinfo) {
				if(StringUtils.isEmpty(exportVo.getReceiverName())){
					exportVo.setReceiverName(sinfo.getUserName());//收货人
				}
				
				if(StringUtils.isEmpty(exportVo.getDeliveryAddress())){
					exportVo.setDeliveryAddress(sinfo.getAddressDetail());//地址
				}
				
				if(StringUtils.isEmpty(exportVo.getReceiverTel())){
					exportVo.setReceiverTel(sinfo.getUserTel());//联系电话
				}
				exportVo.setSelfAddrName(sinfo.getName());//自提点
			}
			
			//快递发货
			if(isExpress) {
				String prefix = getExpressAddress(addressInfo);
				exportVo.setDeliveryAddress(prefix + exportVo.getDeliveryAddress());//地址
			}
			exportVo.setNum(order.getNum().intValue());//单件数量
			exportVo.setItemName(getItemName(order));
			
			sheetList2.add(exportVo);
		}
	}
	
	/**
	 * 处理快递发货地址
	 * @param addressInfo
	 * @param sinfo
	 * @return
	 */
	private static String getExpressAddress(YouzanTradesSoldGetResultAddressinfo addressInfo) {
		String province = addressInfo.getDeliveryProvince();
		String city = addressInfo.getDeliveryCity();
		String district = addressInfo.getDeliveryDistrict();
		
		StringBuilder sb = new StringBuilder();
		sb.append(province).append(city).append(district);
		return sb.toString();
	}
	
	/**
	 * 解析产品名称
	 * @param order
	 * @return
	 */
	private static String getItemName(YouzanTradesSoldGetResultOrders order) {
		String skuName = order.getSkuPropertiesName();
		if(StringUtils.isNotEmpty(skuName)) {
			//[{\"k\":\"砂糖橘\",\"k_id\":541723,\"v\":\"买2斤送2斤 共4斤\",\"v_id\":38881221}]
			JSONArray json = JSON.parseArray(skuName);
			if(null != json && !json.isEmpty()) {
				JSONObject jsonObj = json.getJSONObject(0);
				String k = jsonObj.getString("k");
				String v = jsonObj.getString("v");
				
				if(StringUtils.isNotEmpty(k) || StringUtils.isNotEmpty(v)) {
					StringBuilder sb = new StringBuilder();
					sb.append(StringUtils.isNotEmpty(k) ? k : Constants.EMPTY);
					sb.append(StringUtils.isNotEmpty(v) ? v : Constants.EMPTY);
					return sb.toString();
				}
			}
		}
		
		return order.getTitle();
	}
	
	
	/**
	 * 获取sheet Name
	 * @param addressInfo
	 * @param remarkInfo
	 * @return
	 */
	private static String getSheetName(YouzanTradesSoldGetResultAddressinfo addressInfo,YouzanTradesSoldGetResultRemarkinfo remarkInfo) {
		
		if(CollectionUtils.isEmpty(DataCache.HOUSE_CACHE)) {
			return WebCostants.DEFAULT_SHEET_NAME;
		}
		
		List<String> pList = new ArrayList<String>();
		
		String address1 = addressInfo.getDeliveryAddress();//收货详细地址
		String selfFetchInfo = addressInfo.getSelfFetchInfo();
		
		String address2 = null;
		String address3 = null;
		if(StringUtils.isNotEmpty(selfFetchInfo)) {
			SelfFetchInfo sinfo = JSON.parseObject(selfFetchInfo, SelfFetchInfo.class);
			address2 = sinfo.getAddressDetail();//自提详细地址
			address3 = sinfo.getName();//自提点
		}
		String address4 = remarkInfo.getTradeMemo();//商家备注
		add(pList,address1);
		add(pList,address2);
		add(pList,address3);
		add(pList,address4);
		
		String sheetName = Utils.isExistHouse(pList.toArray(new String[] {}));
		return null == sheetName ? WebCostants.DEFAULT_SHEET_NAME : sheetName;
	}
	
	private static void add(List<String> pList,String element) {
		if(StringUtils.isNotEmpty(element)) {
			pList.add(element);
		}
	}
}

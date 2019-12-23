package com.youzan.data.web.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultData;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultFullorderinfolist;
import com.youzan.common.util.ExcelExportUtil;
import com.youzan.common.util.FileUtils;
import com.youzan.data.api.OrderService;
import com.youzan.data.web.constant.WebCostants;
import com.youzan.data.web.param.OrderQueryParam;
import com.youzan.data.web.service.ExportService;
import com.youzan.data.web.util.ExportUtils;
import com.youzan.data.web.util.Utils;
import com.youzan.data.web.vo.ExportVo;

@Service
public class ExportServiceImpl implements ExportService{
	
	@Autowired
	private OrderService orderService;
	
	private Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);
	
	
	/**
	 * 导出配送表
	 */
	public byte[] download001(OrderQueryParam queryParam) throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(WebCostants.EXPORT_0001);
		InputStream inputStream = classPathResource.getInputStream();
		
		YouzanTradesSoldGetResultData ret = orderService.getTradesSold(Utils.getTradesSoldGetParam(queryParam));
		
		if(ret.getTotalResults().longValue() > 0) {
			List<YouzanTradesSoldGetResultFullorderinfolist> list = ret.getFullOrderInfoList();
			//数据转换
			Map<String,List<ExportVo>> dataMap = ExportUtils.convertData(list);
			
			//初始化导出对象
			ExcelExportUtil excelExport = new ExcelExportUtil(inputStream);
			//初始化sheet
			excelExport.initSheet(dataMap.size());
			
			Iterator<Entry<String,List<ExportVo>>> sheetList = dataMap.entrySet().iterator();
			Entry<String,List<ExportVo>> entry = null;
			
			List<ExportVo> sheetVal = null;
			String sheetName = null;
			
			//处理list 显示
			Map<Integer, Object> rowMap = null;
			List<Map<Integer, Object>> rowList = null;
			String empty = com.youzan.common.bean.constant.Constants.EMPTY;
			
			//每一个sheet
			//记录跨行的数据
			Map<Integer,Integer> rowSpanMap = null;
			int startRowIndex = 2;//起始行索引 --索引从1开始
			int rowIndex = 0;//行索引
			int sheetIndex = 0;
			
			int seqInex = 1;//序号
			Map<String,Integer> seqMap = new HashMap<String,Integer>();
			while(sheetList.hasNext()) {
				rowList = new ArrayList<Map<Integer, Object>>();
				rowSpanMap = new HashMap<Integer,Integer>();
				rowIndex = 0;
				seqInex  = 0;
				
				entry = sheetList.next();
				sheetName = entry.getKey();//sheet name
				sheetVal  = entry.getValue();
				
				//每一行数据 序号、姓名、买家昵称、商品名称、份数、总数量、手机号码、订单配送方式、地址、备注
				for(ExportVo exportVo: sheetVal) {
					rowMap = new HashMap<Integer, Object>();
					
					if(!seqMap.containsKey(exportVo.getGroup())) {//处理序号
						seqMap.put(exportVo.getGroup(), ++seqInex);
					}
					
					rowMap.put(1, seqMap.get(exportVo.getGroup()));//序号
					rowMap.put(2, exportVo.getReceiverName());
					rowMap.put(3, StringUtils.isNotEmpty(exportVo.getFansNickname()) ? exportVo.getFansNickname() : empty);
					rowMap.put(4, exportVo.getItemName());
					rowMap.put(5, exportVo.getNum());
					rowMap.put(6, exportVo.getSubTotalNum());
					rowMap.put(7, StringUtils.isNotEmpty(exportVo.getReceiverTel()) ? exportVo.getReceiverTel() : empty);
					rowMap.put(8, exportVo.getExpressType());
					rowMap.put(9, StringUtils.isNotEmpty(exportVo.getDeliveryAddress()) ? exportVo.getDeliveryAddress() : empty);
					rowMap.put(10, StringUtils.isNotEmpty(exportVo.getBuyerMessage()) ? exportVo.getBuyerMessage() : empty);
					
					//存在多行，需要合并数据
					if(exportVo.getSpanRow() > 1) {
						rowSpanMap.put(startRowIndex + rowIndex, exportVo.getSpanRow());
					}
					
					rowList.add(rowMap);
					rowIndex++;
				}
				
				//写入一个sheet
				excelExport.writeDataList(rowList, sheetIndex, startRowIndex,true);
				excelExport.setSheetName(sheetIndex, sheetName);//设置sheet 名称
				
				//设置跨行
				if(!CollectionUtils.isEmpty(rowSpanMap)) {
					int startIndex = 0;
					int endIndex   = 0;
					for(Entry<Integer,Integer> spanEntry: rowSpanMap.entrySet()) {
						startIndex = spanEntry.getKey() - 1;
						endIndex   = startIndex + spanEntry.getValue() - 1;
						rowMap = rowList.get(spanEntry.getKey() - startRowIndex);
						//序号 姓名	买家昵称	商品名称	份数	总数量	手机号码	订单配送方式	地址	备注
						merge(excelExport,sheetIndex, startIndex, endIndex, 0, 0,rowMap.get(1));//序号
						merge(excelExport,sheetIndex, startIndex, endIndex, 1, 1,rowMap.get(2));//姓名
						merge(excelExport,sheetIndex, startIndex, endIndex, 2, 2,rowMap.get(3));//买家昵称
						merge(excelExport,sheetIndex, startIndex, endIndex, 5, 5,rowMap.get(6));//总数量
						merge(excelExport,sheetIndex, startIndex, endIndex, 6, 6,rowMap.get(7));//手机号码
						merge(excelExport,sheetIndex, startIndex, endIndex, 7, 7,rowMap.get(8));//订单配送方式
						merge(excelExport,sheetIndex, startIndex, endIndex, 8, 8,rowMap.get(9));//地址
						merge(excelExport,sheetIndex, startIndex, endIndex, 9, 9,rowMap.get(10));//备注
					}
				}
				
				sheetIndex++;
			}
			
			return excelExport.writeBytes();
		}
		
		return FileUtils.toByteArray(inputStream);
	}
	
	private void merge(ExcelExportUtil excelExport,int sheetIndex, int firstRow, int lastRow, int firstCol,int lastCol, Object data) throws InvalidFormatException, IOException {
		excelExport.merge(sheetIndex, firstRow, lastRow, firstCol, lastCol, data);
	}
}

package com.youzan.data.service.impl;

import org.springframework.stereotype.Service;

import com.youzan.cloud.open.sdk.api.ApiParams;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.gen.v4_0_1.api.YouzanTradesSoldGet;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultData;
import com.youzan.common.exception.ServiceException;
import com.youzan.data.api.OrderService;

@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService{
	/**
	 * 查询订单信息
	 * @param apiParams
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public YouzanTradesSoldGetResultData getTradesSold(ApiParams apiParams) throws ServiceException, SDKException {
		YouzanTradesSoldGetResult ret = this.httpService.get(new YouzanTradesSoldGet(), apiParams, YouzanTradesSoldGetResult.class);
		
		if(ret.getSuccess()) {
			return ret.getData();
		}
		return null;
	}
}

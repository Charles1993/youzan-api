package com.youzan.data.api;

import com.youzan.cloud.open.sdk.api.ApiParams;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetResult.YouzanTradesSoldGetResultData;
import com.youzan.common.exception.ServiceException;

public interface OrderService extends BaseService{

	/**
	 * 查询订单信息
	 * @param apiParams
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public YouzanTradesSoldGetResultData getTradesSold(ApiParams apiParams) throws ServiceException, SDKException;
}

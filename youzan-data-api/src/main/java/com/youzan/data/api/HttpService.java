package com.youzan.data.api;

import com.youzan.cloud.open.sdk.api.AbstractAPI;
import com.youzan.cloud.open.sdk.api.ApiParams;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.common.exception.ServiceException;

public interface HttpService {

	public String getAccessToken() throws ServiceException;
	
	/**
	 * 通用请求方法
	 * @param api
	 * @param apiParams
	 * @param clazz
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public <T> T get(AbstractAPI api,ApiParams apiParams,Class<T> clazz) throws ServiceException, SDKException;
}

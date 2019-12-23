package com.youzan.data.api;

import com.youzan.cloud.open.sdk.api.ApiParams;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanUserBasicGetResult.YouzanUserBasicGetResultData;
import com.youzan.common.exception.ServiceException;

public interface UserService extends BaseService{
	
	/**
	 * 查询获取用户信息
	 * @param apiParams
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public YouzanUserBasicGetResultData getUserInfoV3(ApiParams apiParams) throws ServiceException, SDKException;
	
	/**
	 * 根据用户id 查询yzopenid
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public String getUserOpenId(Long userId) throws ServiceException, SDKException;
}

package com.youzan.data.service.impl;

import org.springframework.stereotype.Service;

import com.youzan.cloud.open.sdk.api.ApiParams;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.gen.v1_0_0.api.YouzanUserOpenidGet;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUserOpenidGetParams;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUserOpenidGetResult;
import com.youzan.cloud.open.sdk.gen.v3_0_0.api.YouzanUserBasicGet;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanUserBasicGetResult;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanUserBasicGetResult.YouzanUserBasicGetResultData;
import com.youzan.common.exception.ServiceException;
import com.youzan.data.api.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
	
	/**
	 * 查询获取用户信息
	 * @param apiParams
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public YouzanUserBasicGetResultData getUserInfoV3(ApiParams apiParams) throws ServiceException, SDKException {
		YouzanUserBasicGetResult ret = this.httpService.get(new YouzanUserBasicGet(), apiParams, YouzanUserBasicGetResult.class);
		
		if(ret.getSuccess()) {
			return ret.getData();
		}
		return null;
	}
	
	/**
	 * 根据用户id 查询yzopenid
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public String getUserOpenId(Long userId) throws ServiceException, SDKException {
		YouzanUserOpenidGetParams apiParams = new YouzanUserOpenidGetParams();
		apiParams.setUserId(userId);
		
		YouzanUserOpenidGetResult ret = this.httpService.get(new YouzanUserOpenidGet(), apiParams, YouzanUserOpenidGetResult.class);
		
		if(ret.getSuccess()) {
			return ret.getData().getYzOpenId();
		}
		return null;
	}
}

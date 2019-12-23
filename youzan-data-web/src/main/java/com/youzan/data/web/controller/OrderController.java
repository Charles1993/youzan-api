package com.youzan.data.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.gen.v3_0_0.model.YouzanUserBasicGetParams;
import com.youzan.cloud.open.sdk.gen.v4_0_1.api.YouzanTradesSoldGet;
import com.youzan.cloud.open.sdk.gen.v4_0_1.model.YouzanTradesSoldGetParams;
import com.youzan.common.exception.ServiceException;
import com.youzan.data.api.HttpService;
import com.youzan.data.api.OrderService;
import com.youzan.data.api.UserService;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController{
	
	@Autowired
	private HttpService httpService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public Object getToken() throws Exception{
		return httpService.getAccessToken();
	}
	
	@RequestMapping(value = "/getTradesSold", method = RequestMethod.GET)
	public Object getTradesSold(YouzanTradesSoldGetParams apiParams) throws ServiceException, SDKException {
		return orderService.getTradesSold(apiParams);
	}
	
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public Object getUser(YouzanUserBasicGetParams apiParams) throws ServiceException, SDKException {
		return userService.getUserInfoV3(apiParams);
	}
}

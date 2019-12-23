package com.youzan.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.youzan.data.api.BaseService;
import com.youzan.data.api.HttpService;

public class BaseServiceImpl implements BaseService{
	
	@Autowired
	protected HttpService httpService;
}

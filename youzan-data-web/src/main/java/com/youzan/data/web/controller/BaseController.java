package com.youzan.data.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class BaseController {
	
	@Autowired
	private Environment env;
	
	protected boolean isProd(){
		String [] profiles = env.getActiveProfiles();
		for(String str:profiles){
			if(str.contains("prod")){
				return true;
			}
		}
		return false;
	}
	
}

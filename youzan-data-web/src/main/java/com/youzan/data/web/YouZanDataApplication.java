package com.youzan.data.web;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;


@SpringBootApplication

//mybatis mapper 包
//@MapperScan({"com.longsys.bi.service.oracle","com.longsys.bi.service.mysql"})
@ComponentScan("com.youzan.data.*")
@EnableScheduling
public class YouZanDataApplication {
	
	//用于处理编码问题
	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	public static void main(String[] args) {
		SpringApplication.run(YouZanDataApplication.class, args);
	}
}

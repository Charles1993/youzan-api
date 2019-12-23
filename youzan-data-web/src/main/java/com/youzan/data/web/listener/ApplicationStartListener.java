package com.youzan.data.web.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.youzan.data.web.cache.DataCache;
import com.youzan.data.web.constant.WebCostants;

/**
 * 启动监听类
 * @author  Evan.zhu
 * @version  [Version NO, 2019年11月7日]
 * @see  [Related classes/methods]
 * @since  [product/module version]
 */
@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationReadyEvent> {

	private Logger logger = LoggerFactory.getLogger(ApplicationStartListener.class);
	
	/** 
	 * @author  Evan.zhu
	 * @param event [Parameters description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		initHouse(event);
	}
	/**
	 * 初始化小区
	 * @param event
	 */
	private void initHouse(ApplicationReadyEvent event) {
		try {
			String fileName = event.getApplicationContext().getEnvironment().getProperty(WebCostants.HOUSE_PATH);
			if(StringUtils.isNotEmpty(fileName)) {
				File f = new File(fileName);
				if(f.exists()) {
					f = new File(fileName + File.separator + WebCostants.HOUSE_FILE);
					if(f.exists()) {
						InputStream input = new FileInputStream(f);
						String house = IOUtils.toString(input, StandardCharsets.UTF_8);
						DataCache.refreshHouse(house);
					}
				}
			}
		}catch(Exception ex) {
			logger.error("启动初始化小区异常！");
		}
	}
}

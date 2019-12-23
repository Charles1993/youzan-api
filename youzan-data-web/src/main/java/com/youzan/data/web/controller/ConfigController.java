package com.youzan.data.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youzan.common.bean.Result;
import com.youzan.common.util.FileUtils;
import com.youzan.data.web.cache.DataCache;
import com.youzan.data.web.constant.WebCostants;

@RestController
@RequestMapping("conf")
public class ConfigController {
	
	@Value("${conf.path}")
	private String filePath;
	
	/**
	 * 设置小区
	 * @param queryParam
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public Result setHouses(String param) throws IOException {
		if(StringUtils.isNotEmpty(param)) {
			DataCache.refreshHouse(param);
			this.writeFile(param);
		}
		return new Result("设置小区成功！");
	}
	
	private void writeFile(String param) throws IOException {
		File f = new File(filePath + File.separator + WebCostants.HOUSE_FILE);
		if(!f.exists()) {
			f.createNewFile();
		}
		FileUtils.write(param, f);
	}
}

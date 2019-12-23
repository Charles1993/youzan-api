package com.youzan.data.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.youzan.common.util.DateUtil;
import com.youzan.data.api.constant.Constants;
import com.youzan.data.web.param.OrderQueryParam;
import com.youzan.data.web.service.ExportService;

@RestController
@RequestMapping("export")
public class ExportController {
	
	@Autowired
	private ExportService exportService;
	//private Logger logger = LoggerFactory.getLogger(ExportController.class);
	
	/**
	 * 导出配送表数据
	 * @param queryParam
	 * @throws Exception
	 */
	@RequestMapping(value = "/d001", method = RequestMethod.GET)
	public void download_0001(OrderQueryParam queryParam) throws Exception {
		String name = DateUtil.date2Str(new Date(),DateUtil.YYYYMMDD);
		
		this.downloadExcel(String.format("%s配送表.xlsx", name), exportService.download001(queryParam));
	}
	
	/**
	 * 通用下载附件
	 * @param fileName
	 * @param file
	 * @throws Exception
	 */
	private void downloadExcel(String fileName,byte [] file) throws Exception{
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        
		response.reset();
		
		// 下面几行是为了解决文件名乱码的问题
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		ServletOutputStream out = response.getOutputStream();
		
		out.write(file);
		out.flush();
		out.close();
	}
}

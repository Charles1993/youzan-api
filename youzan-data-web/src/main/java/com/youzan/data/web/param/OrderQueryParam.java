package com.youzan.data.web.param;

import com.youzan.common.bean.QueryParam;

public class OrderQueryParam extends QueryParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//模板文件
	private String templateFile;
	
	//开始时间
	private String startTime;
	
	//结束时间
	private String endTime;
	
	//订单状态
	private String status;

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

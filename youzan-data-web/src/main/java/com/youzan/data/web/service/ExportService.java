package com.youzan.data.web.service;

import com.youzan.data.web.param.OrderQueryParam;

public interface ExportService {

	/**
	 * 导出物流配送表
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	public byte[] download001(OrderQueryParam queryParam) throws Exception;
}

package com.youzan.data.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youzan.common.bean.Result;
import com.youzan.common.exception.ErrCode;
import com.youzan.common.exception.ServiceException;

@ControllerAdvice
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	/**
	 * 业务调用异常
	 * 
	 * @author  Evan.zhu
	 * @param request
	 * @param ex
	 * @return [Parameters description]
	 * @return Result [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	@ExceptionHandler(value = ServiceException.class)
	@ResponseBody
	public Result execService(HttpServletRequest request, Exception ex){
		logger.error("业务返回异常！",ex);
		ServiceException se = (ServiceException) ex;
		return new Result(se.getCode(), se.getMessage());
	}
	
	/**
	 * 全局异常
	 * @author  Evan.zhu
	 * @param request
	 * @param ex
	 * @return [Parameters description]
	 * @return Result [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	@ExceptionHandler
	@ResponseBody
	public Result globalExec(HttpServletRequest request, Exception ex){
		logger.error("全局异常！",ex);
		return new Result(ErrCode.FAILURE,ex.getMessage());
	}
}

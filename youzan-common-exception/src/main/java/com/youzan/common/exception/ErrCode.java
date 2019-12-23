package com.youzan.common.exception;

public final class ErrCode {
	
	
	/**
	 * 编码规则   
	 *   1、系统级别错误码 ：10 系统(2位) |  10 模块(2位)  |  1000 错误码(4位)
	 *   2、通用错误码暂定为1000以下
	 */
	
	
	/////////////////////////////////////////////通用错误码///////////////////////////////////////////
	/**
	 * 成功
	 */
	public static final int SUCCESS = 200;
	
	/**
	 * 失败
	 */
	public static final int FAILURE = 201;
	
	/**
	 * 登录失效
	 */
	public static final int SESSION_FAILURE = 202;
	
	/**
	 * 参数为空
	 */
	public static final int EMPTY = 300;
	
	/**
	 * 数据不存在
	 */
	public static final int DATA_NO_FOUND = 301;
	
	/**
	 * 数据已经存在
	 */
	public static final int DATA_EXISTS = 302;
	
	/**
	 * 禁止访问，非法操作
	 */
	public static final int FORBIDDEN = 500;
	
	/**
	 * 上传文件超出最大限制
	 */
	public static final int UPLOAD_MAX_SIZE_EXCEEDED = 600;
	
	/**
	 * 上传文件类型错误
	 */
	public static final int UPLOAD_FILE_TYPE_ERR     = 601;
	
	
    /////////////////////////////////////////////通用错误码///////////////////////////////////////////
}

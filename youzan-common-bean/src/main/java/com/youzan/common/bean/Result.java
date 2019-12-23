package com.youzan.common.bean;

import java.io.Serializable;

import com.youzan.common.exception.ErrCode;

/**
 * 通用返回实体对象
 * @author  evan.zhu
 * @version  [Version NO, 2018年9月30日]
 * @see  [Related classes/methods]
 * @since  [product/module version]
 */
public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码
	 */
	private int code = ErrCode.SUCCESS;
	
	/**
	 * 返回数据对象
	 */
	private Object data;
	private Object data2;
	private Object data3;
	private Object data4;
	
	/**
	 * 错误信息
	 */
	private String msg;
	
	public Result(){
		
	}
	
	public boolean isOk(){
		return this.code == ErrCode.SUCCESS;
	}
	
	public Result(int code){
		this.code = code;
	}
	
	public Result(String msg){
		this.msg = msg;
	}
	
	public Result(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public Result(int code,Object data){
		this.code = code;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData2() {
		return data2;
	}

	public void setData2(Object data2) {
		this.data2 = data2;
	}

	public Object getData3() {
		return data3;
	}

	public void setData3(Object data3) {
		this.data3 = data3;
	}

	public Object getData4() {
		return data4;
	}

	public void setData4(Object data4) {
		this.data4 = data4;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

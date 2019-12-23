package com.youzan.common.exception;

public abstract class BaseException extends RuntimeException {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private int code;

	public BaseException(){
		
	}
	
	public BaseException(int code){
		this.code = code;
	}
	
	public BaseException(int code,String message){
		super(message);
		this.code = code;
	}
	
	public BaseException(int code,String message,Throwable tw){
		super(message,tw);
		this.code = code;
	}
	
	public BaseException(Throwable tw){
		super(tw);
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

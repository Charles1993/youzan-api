package com.youzan.common.exception;

public class ServiceException extends BaseException {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(int code, String message, Throwable tw) {
		super(code, message, tw);
	}

	public ServiceException(int code, String message) {
		super(code, message);
	}

	public ServiceException(int code) {
		super(code);
	}

	public ServiceException(Throwable tw) {
		super(tw);
	}
}

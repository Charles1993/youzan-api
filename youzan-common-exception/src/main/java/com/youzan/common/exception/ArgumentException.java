package com.youzan.common.exception;

public class ArgumentException extends BaseException {
	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ArgumentException() {
		super();
	}

	public ArgumentException(int code, String message, Throwable tw) {
		super(code, message, tw);
	}

	public ArgumentException(int code, String message) {
		super(code, message);
	}

	public ArgumentException(int code) {
		super(code);
	}

	public ArgumentException(Throwable tw) {
		super(tw);
	}
}

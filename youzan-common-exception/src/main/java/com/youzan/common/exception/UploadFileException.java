package com.youzan.common.exception;

public class UploadFileException extends BaseException {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public UploadFileException() {
		super();
	}

	public UploadFileException(int code, String message, Throwable tw) {
		super(code, message, tw);
	}

	public UploadFileException(int code, String message) {
		super(code, message);
	}

	public UploadFileException(int code) {
		super(code);
	}

	public UploadFileException(Throwable tw) {
		super(tw);
	}
}

package com.fincity.nocode.core;

import com.fincity.nocode.core.util.UniqueID;

public class NocodeException extends RuntimeException {

	private int errorCode;
	private Object[] arguments;
	private String exceptionId = UniqueID.shortId();

	private static final long serialVersionUID = -5014082890200361721L;

	public NocodeException(int errorCode) {

		super();
		this.errorCode = errorCode;
	}

	public NocodeException(int errorCode, String message, Object... arguments) {
		super(message);
		this.errorCode = errorCode;
		this.arguments = arguments;
	}

	public NocodeException(int errorCode, String message, Throwable ex, Object... arguments) {
		super(message, ex);
		this.errorCode = errorCode;
		this.arguments = arguments;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public String getExceptionId() {
		return exceptionId;
	}

	public Object[] getArguments() {
		return arguments;
	}
}

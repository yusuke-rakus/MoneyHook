package com.example.common.exception;

public class AlreadyExistsException extends SystemException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String msg) {
		super(msg);
	}
}

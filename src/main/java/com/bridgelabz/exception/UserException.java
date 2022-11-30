package com.bridgelabz.exception;

public class UserException extends RuntimeException
{
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserException(String message) {
		//super();
		this.message = message;
	}

	public UserException() {
		//super();
	}
	
	
}

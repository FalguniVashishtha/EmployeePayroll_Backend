package com.bridgelabz.exception;

public class ErrorResponse {
	private int statusCode;
	private String message;
	public int getStatusCode() {
		return statusCode;
	}
	public ErrorResponse() {
		//super();
	}
	public ErrorResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

package com.bridgelabz.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(value = UserException.class)
	@ResponseStatus
	@ResponseBody
	public ErrorResponse handleUserExistsException(UserException e) {
		return new ErrorResponse(1, e.getMessage());
	}
}

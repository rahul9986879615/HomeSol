package com.example.homesol.exception;

import java.lang.reflect.Constructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String detailError;
	public BadRequestException(String reason)
	{
		detailError=reason;
	}
	public String getDetailError() {
		return detailError;
	}
	
}

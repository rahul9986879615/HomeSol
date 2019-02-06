package com.example.homesol.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7750095919383415751L;
	private String detailError;
	public ServerErrorException(String reason)
	{
		detailError=reason;
	}
	public String getDetailError() {
		return detailError;
	}
	
}

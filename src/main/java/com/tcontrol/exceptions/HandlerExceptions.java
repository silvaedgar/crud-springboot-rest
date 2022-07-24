package com.tcontrol.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HandlerExceptions extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus httpStatus;

	public HandlerExceptions(String message, HttpStatus httpStatus) {
		super(message);
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

//	public HandlerExceptions (String message, String field, HttpStatus httpStatus) {
//		super(message);
//		this.field = field;
//		this.message = message;
//		this.setHttpStatus(httpStatus);
//	}
//
//	public HttpStatus getHttpStatus() {
//		return httpStatus;
//	}
//
//	public void setHttpStatus(HttpStatus httpStatus) {
//		this.httpStatus = httpStatus;
//	}

}

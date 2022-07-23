package com.webapp.employee.exception;

public class GeneralException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String errorMessage;

	public GeneralException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}

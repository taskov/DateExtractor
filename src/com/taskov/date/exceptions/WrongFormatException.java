package com.taskov.date.exceptions;

public class WrongFormatException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongFormatException(){
		
	}
	
	public WrongFormatException(String message){
		super(message);
	}

}

package com.balaji.movie_diary.exception;

public class DuplicateResourceFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Object fieldValue;
	
	public DuplicateResourceFoundException(String resourceName, String fieldName, Object fieldValue) {
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public DuplicateResourceFoundException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return resourceName+" is already found with"+fieldName+"-"+fieldValue;
	}
	
	
}

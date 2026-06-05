package com.balaji.movie_diary.exception;

public class ResourceNotFoundException extends RuntimeException {
	String resourceName;
	String fieldName;
	Object fieldId;
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldId) {
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
	}
	
	@Override
	public String getMessage() {
		return (resourceName+" is not found with "+fieldName+"-"+fieldId);
	}
}

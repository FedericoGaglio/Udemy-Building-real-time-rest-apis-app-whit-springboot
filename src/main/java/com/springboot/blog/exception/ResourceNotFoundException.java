package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private String resourceName;
	private String fieldName;
	private String fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		//per vedere dinamicamente
		super(String.format("%s not found whit %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
	
	
	/*
	 * 
	 * Annotazione @ResponseStatus
	 * Serve per marcare un metodo o un eccezione andando a mostrare il TIPO DI ERRORE TRAMITE CODICE e anche
	 * IL MOTIVO di tale eccezione.
	 * 
	 * */


	

}

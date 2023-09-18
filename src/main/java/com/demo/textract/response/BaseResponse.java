package com.demo.textract.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long code;
	private String message;
	private String description;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object object;
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

package com.panasonic.b2bacns.bizportal.common;

/**
 * This class is used for Status
 * 
 * @author akansha
 *
 */
public class Response {

	private boolean status;

	/**
	 * Return success
	 * 
	 * @return
	 */
	
	public Response(boolean success){
		this.status = success;
	}
	
	public Response(){
		
	}
	
	public boolean isSuccess() {
		return status;
	}

	/**
	 * Set success
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.status = success;
	}
}

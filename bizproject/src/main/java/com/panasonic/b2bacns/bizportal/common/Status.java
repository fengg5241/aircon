package com.panasonic.b2bacns.bizportal.common;

/**
 * This class is used for Status
 * 
 * @author akansha
 *
 */
public class Status {

	private boolean status;

	
	public Status(boolean status){
		this.status = status;
	}
	
	public Status(){
		
	}
	
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}

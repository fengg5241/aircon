/**
 * 
 */
package com.panasonic.b2bacns.bizportal.common;

/**
 * The class AjaxResponse can be used to send the response back to a Ajax call.
 * 
 * @author abhishek.vishnoi	
 * 
 */
public class AjaxResponse {

	private Object model;

	private Boolean success;

	private String message;

	public AjaxResponse() {
	}

	public AjaxResponse(Object model, Boolean success, String message) {
		this.model = model;
		this.setSuccess(success);
		this.message = message;
	}

	/**
	 * @return the model
	 */
	public Object getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Object model) {
		this.model = model;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param errorSuccessMessage
	 *            the message to set
	 */
	public void setMessage(String errorSuccessMessage) {
		this.message = errorSuccessMessage;
	}

}

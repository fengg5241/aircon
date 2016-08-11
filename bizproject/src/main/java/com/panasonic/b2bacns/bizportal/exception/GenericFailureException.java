package com.panasonic.b2bacns.bizportal.exception;

public class GenericFailureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorID;

	private String customErrorMsg;

	/**
	 * Constructor with message only
	 * 
	 * @param message
	 */
	public GenericFailureException(final String message) {
		super(message);
		this.customErrorMsg = message;
	}

	/**
	 * Constructor with message & Throwable parameter.
	 * 
	 * @param message
	 * @param object
	 */
	public GenericFailureException(final String message, final Throwable object) {
		super(message, object);
		this.customErrorMsg = message;
	}

	/**
	 * Constructor for MerchantAdminException.
	 * 
	 * @param messages
	 *            List<String>
	 */
	public GenericFailureException(final int errorID, String message) {
		super(message);
		this.errorID = errorID;
		this.customErrorMsg = message;
	}

	public GenericFailureException(int errorID, Throwable cause) {
		super(cause);
		this.errorID = errorID;
	}

	/**
	 * 
	 * @return the errorID
	 */
	public int getErrorID() {
		return this.errorID;
	}

	/**
	 * 
	 * Set the errorID
	 */
	public void setErrorID(int errorID) {
		this.errorID = errorID;
	}

	/**
	 * @param customErrorMsg
	 *            the customErrorMsg to set
	 */
	public void setCustomErrorMsg(String customErrorMsg) {
		this.customErrorMsg = customErrorMsg;
	}

	/**
	 * @return the customErrorMsg
	 */
	public String getCustomErrorMsg() {
		return customErrorMsg;
	}

}

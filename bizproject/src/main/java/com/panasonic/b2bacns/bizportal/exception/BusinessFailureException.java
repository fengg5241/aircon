/**
 * 
 */
package com.panasonic.b2bacns.bizportal.exception;

/**
 * @author diksha.rattan
 * 
 */
public class BusinessFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1104768899748962597L;

	private int errorID;

	/**
	 * Constructor with message only
	 * 
	 * @param message
	 */
	public BusinessFailureException(final String message) {
		super(message);
	}

	/**
	 * Constructor with message & Throwable parameter.
	 * 
	 * @param message
	 * @param object
	 */
	public BusinessFailureException(final String message, final Throwable object) {
		super(message, object);
	}

	/**
	 * Constructor for MerchantAdminException.
	 * 
	 * @param messages
	 *            List<String>
	 */
	public BusinessFailureException(final int errorID, String message) {
		super(message);
		this.errorID = errorID;
	}

	/**
	 * 
	 * @return the messages
	 */
	public int getErrorID() {
		return this.errorID;
	}

}

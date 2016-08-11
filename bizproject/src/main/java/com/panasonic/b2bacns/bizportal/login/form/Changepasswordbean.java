/**
 * 
 */
package com.panasonic.b2bacns.bizportal.login.form;

/**
 * @author seshu
 *
 */
public class Changepasswordbean {

	private String UserId;	
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
	

	/**
	 * @return the currentLoginId
	 */
	public String getUserId() {
		return UserId;
	}

	/**
	 * @param currentLoginId
	 *            the currentLoginId to set
	 */
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @param currentPassword
	 *            the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	

}

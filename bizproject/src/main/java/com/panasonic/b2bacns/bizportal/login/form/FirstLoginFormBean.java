/**
 * 
 */
package com.panasonic.b2bacns.bizportal.login.form;

import java.util.Locale;

/**
 * @author akansha
 *
 */
public class FirstLoginFormBean {

	private String currentLoginId;
	private String newLoginId;
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
	private String email;
	private String telephone;
	private Locale locale;

	/**
	 * @return the currentLoginId
	 */
	public String getCurrentLoginId() {
		return currentLoginId;
	}

	/**
	 * @param currentLoginId
	 *            the currentLoginId to set
	 */
	public void setCurrentLoginId(String currentLoginId) {
		this.currentLoginId = currentLoginId;
	}

	/**
	 * @return the newLoginId
	 */
	public String getNewLoginId() {
		return newLoginId;
	}

	/**
	 * @param newLoginId
	 *            the newLoginId to set
	 */
	public void setNewLoginId(String newLoginId) {
		this.newLoginId = newLoginId;
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}

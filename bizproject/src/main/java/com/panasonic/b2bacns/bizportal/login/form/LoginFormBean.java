package com.panasonic.b2bacns.bizportal.login.form;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
/**
 * This class is used as login form.
 * 
 * @author kumar.madhukar
 * 
 */
public class LoginFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2951554614319158216L;

	private String password;

	private String userTimeZone;

	private List<String> errorList;

	private String loginId;

	private String language;
	
	//Added by Jia Wei
	private Locale locale;
	/**
	 * 
	 * @return
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the errorList
	 */
	public List<String> getErrorList() {
		return errorList;
	}

	/**
	 * @param errorList
	 *            the errorList to set
	 */
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public String getUserTimeZone() {
		return userTimeZone;
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	// Added By Jia Wei
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
	// End of adding by Jia Wei
	
}

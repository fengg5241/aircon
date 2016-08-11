package com.panasonic.b2bacns.bizportal.login.form;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
/**
 * This class is used as login form.
 * 
 * @author sesha
 * 
 */
public class ChangeFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2951554614319158216L;
	

	private String userTimeZone;

	private List<String> errorList;

	private String loginId;

	private String language;
	
	
	private Locale locale;
	

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

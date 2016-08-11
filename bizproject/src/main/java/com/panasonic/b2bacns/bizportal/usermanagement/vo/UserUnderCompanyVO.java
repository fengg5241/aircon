package com.panasonic.b2bacns.bizportal.usermanagement.vo;

public class UserUnderCompanyVO {

	private Long id;
	private String company_name;
	private String registered_date;
	private String registered_by;
	private String account_state;
	private String loginId;
	private String rolename;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the company_name
	 */
	public String getCompany_name() {
		return company_name;
	}

	/**
	 * @param company_name
	 *            the company_name to set
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	/**
	 * @return the registered_date
	 */
	public String getRegistered_date() {
		return registered_date;
	}

	/**
	 * @param registered_date
	 *            the registered_date to set
	 */
	public void setRegistered_date(String registered_date) {
		this.registered_date = registered_date;
	}

	/**
	 * @return the registered_by
	 */
	public String getRegistered_by() {
		return registered_by;
	}

	/**
	 * @param registered_by
	 *            the registered_by to set
	 */
	public void setRegistered_by(String registered_by) {
		this.registered_by = registered_by;
	}

	/**
	 * @return the account_state
	 */
	public String getAccount_state() {
		return account_state;
	}

	/**
	 * @param account_state
	 *            the account_state to set
	 */
	public void setAccount_state(String account_state) {
		this.account_state = account_state;
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

	/**
	 * @return the rolename
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * @param rolename
	 *            the rolename to set
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}

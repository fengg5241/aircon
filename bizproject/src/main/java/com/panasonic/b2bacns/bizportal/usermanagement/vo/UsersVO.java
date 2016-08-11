package com.panasonic.b2bacns.bizportal.usermanagement.vo;

public class UsersVO {

	private long id;

	private String company_name;

	private String registered_date;

	private String registered_by;

	private String account_state;

	private String loginId;

	private String rolename;

	private Long roleid;
	
	private Long roletypeid;
	
	//Added by seshu.
	private Long companyId;
	//Added by seshu.
	public long getCompanyid() {
		return companyId;
	}
	//Added by seshu.
	public void setCompanyid(Long companyid) {
		this.companyId = companyid;
	}
	
	
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getRegistered_date() {
		return registered_date;
	}

	public void setRegistered_date(String registered_date) {
		this.registered_date = registered_date;
	}

	public String getRegistered_by() {
		return registered_by;
	}

	public void setRegistered_by(String registered_by) {
		this.registered_by = registered_by;
	}

	public String getAccount_state() {
		return account_state;
	}

	public void setAccount_state(String account_state) {
		this.account_state = account_state;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getRoletypeid() {
		return roletypeid;
	}

	public void setRoletypeid(Long roletypeid) {
		this.roletypeid = roletypeid;
	}
	
}

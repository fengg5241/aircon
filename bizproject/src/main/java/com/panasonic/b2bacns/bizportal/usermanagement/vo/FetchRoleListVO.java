package com.panasonic.b2bacns.bizportal.usermanagement.vo;

public class FetchRoleListVO {

	private Long role_id = 0l;
	private String role_name;
	private String roletype_name;
	private Long roletype_id;
	private String functionalgroupids;
	private String functionalgroupnames;

	/**
	 * @return the role_id
	 */
	public Long getRole_id() {
		return role_id;
	}

	/**
	 * @param role_id
	 *            the role_id to set
	 */
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	/**
	 * @return the role_name
	 */
	public String getRole_name() {
		return role_name;
	}

	/**
	 * @param role_name
	 *            the role_name to set
	 */
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	/**
	 * @return the roletype_name
	 */
	public String getRoletype_name() {
		return roletype_name;
	}

	/**
	 * @param roletype_name
	 *            the roletype_name to set
	 */
	public void setRoletype_name(String roletype_name) {
		this.roletype_name = roletype_name;
	}

	/**
	 * @return the roletype_id
	 */
	public Long getRoletype_id() {
		return roletype_id;
	}

	/**
	 * @param roletype_id
	 *            the roletype_id to set
	 */
	public void setRoletype_id(Long roletype_id) {
		this.roletype_id = roletype_id;
	}

	/**
	 * @return the functionalgroupids
	 */
	public String getFunctionalgroupids() {
		return functionalgroupids;
	}

	/**
	 * @param functionalgroupids
	 *            the functionalgroupids to set
	 */
	public void setFunctionalgroupids(String functionalgroupids) {
		this.functionalgroupids = functionalgroupids;
	}

	/**
	 * @return the functionalgroupnames
	 */
	public String getFunctionalgroupnames() {
		return functionalgroupnames;
	}

	/**
	 * @param functionalgroupnames
	 *            the functionalgroupnames to set
	 */
	public void setFunctionalgroupnames(String functionalgroupnames) {
		this.functionalgroupnames = functionalgroupnames;
	}

}

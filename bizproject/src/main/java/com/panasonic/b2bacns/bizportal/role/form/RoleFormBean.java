package com.panasonic.b2bacns.bizportal.role.form;

import java.util.List;
import java.util.Locale;

public class RoleFormBean {

	private Locale locale ;
	private String role_name ;
    private	String roletype_id ;
	private List<Long> functional_id;
	private String roleId;
	
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRoletype_id() {
		return roletype_id;
	}
	public void setRoletype_id(String roletype_id) {
		this.roletype_id = roletype_id;
	}
	public List<Long> getFunctional_id() {
		return functional_id;
	}
	public void setFunctional_id(List<Long> functional_id) {
		this.functional_id = functional_id;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
	
	
	
}

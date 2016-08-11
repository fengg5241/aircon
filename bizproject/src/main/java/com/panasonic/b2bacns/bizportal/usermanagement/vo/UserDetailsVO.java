package com.panasonic.b2bacns.bizportal.usermanagement.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UserDetailsVO {

	private Long roleId;
	private String roleName;
	private Long compId;
	private String compName;
	private String accountState;
	@JsonInclude(Include.NON_NULL)
	private List<GroupCompanyVO> group_strucutre;
	//Added by seshu.
	private List<GroupCompanyVO> groupVoCompanyWise;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public List<GroupCompanyVO> getGroup_strucutre() {
		return group_strucutre;
	}

	public void setGroup_strucutre(List<GroupCompanyVO> group_strucutre) {
		this.group_strucutre = group_strucutre;
	}
	//Added by seshu.
	public List<GroupCompanyVO> getGroupVoCompanyWise() {
		return groupVoCompanyWise;
	}
	public void setGroupVoCompanyWise(List<GroupCompanyVO> groupVoCompanyWise) {
		this.groupVoCompanyWise = groupVoCompanyWise;
	}
}

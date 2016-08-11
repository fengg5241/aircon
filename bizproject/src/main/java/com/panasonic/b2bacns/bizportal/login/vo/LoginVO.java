package com.panasonic.b2bacns.bizportal.login.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.panasonic.b2bacns.bizportal.common.PermissionVO;
import com.panasonic.b2bacns.bizportal.persistence.Role;

/**
 * This class is used as login DTO
 * 
 * @author kumar.madhukar
 *
 */
public class LoginVO {

	// private String email;

	private String password;

	private String userRole;

	private List<String> menuList;

	private List<PermissionVO> permissionsList;

	private String errorMessage;

	private String successMessage;

	private Long userId;

	private String userRoleId;

	private String loginId;

	private Date lastLoginDate;

	private Role role;
	
	private boolean isFirstLogin = false;
	
	private long companyId;
	
	//add by shanf
	private List<String> permissionNameList = new ArrayList<String>();

	/**
	 * 
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage
	 *            the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the menuList
	 */
	public List<String> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList
	 *            the menuList to set
	 */
	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}

	/**
	 * 
	 * @return
	 */
	// public String getEmail() {
	// return email;
	// }

	/**
	 * 
	 * @param email
	 */
	// public void setEmail(String email) {
	// this.email = email;
	// }

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
	 * @return the userRoleId
	 */
	public String getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId
	 *            the userRoleId to set
	 */
	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public List<PermissionVO> getPermissionsList() {
		return permissionsList;
	}

	public void setPermissionsList(List<PermissionVO> permissionsList) {
		this.permissionsList = permissionsList;
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
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	//add by shanf
	public List<String> getPermissionNameList() {
		return permissionNameList;
	}
	//add by shanf
	public void setPermissionNameList(List<String> permissionNameList) {
		this.permissionNameList = permissionNameList;
	}

	
	//add by jwchan toString method
	@Override
	public String toString() {
		return "LoginVO [userRole=" + userRole
				+ ", menuList=" + menuList + ", permissionsList="
				+ permissionsList + ", errorMessage=" + errorMessage
				+ ", successMessage=" + successMessage + ", userId=" + userId
				+ ", userRoleId=" + userRoleId + ", loginId=" + loginId
				+ ", lastLoginDate=" + lastLoginDate + ", role=" + role
				+ ", isFirstLogin=" + isFirstLogin + ", companyId=" + companyId
				+ ", permissionNameList=" + permissionNameList + "]";
	}
	
	

}

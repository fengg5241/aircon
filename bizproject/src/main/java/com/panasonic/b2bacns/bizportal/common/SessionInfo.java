package com.panasonic.b2bacns.bizportal.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.panasonic.b2bacns.bizportal.home.vo.UserSelectionVO;

/**
 * This class is being used to store global attributes which is to be added to
 * the HttpSession later.
 * 
 * @author kumar.madhukar
 * 
 */

public class SessionInfo implements Serializable, Cloneable {

	private static final long serialVersionUID = -5582299006334253685L;

	private String userRole;

	private List<String> menuList;

	private List<PermissionVO> permissionsList;
	
	//add by shanf
	private List<String> permissionNameList = new ArrayList<String>();

	private String emailId;

	private Long userId;

	private Long companyId;

	private String userRoleId;

	private String imagePath;

	private Long lastSelectedGroupID;

	private List<UserSelectionVO> userSelectionsList;

	private String userTimeZone;

	private String loginId;

	private Map<Integer, String> roleTypeMap;

	private Map<Integer, String> functionalGroupMap;

	private boolean isFirstLogin;

	private String csrfToken;

	public SessionInfo(Long userId, String userRole, List<String> menuList,
			String emailId) {
		super();
		this.setUserId(userId);
		this.userRole = userRole;
		this.menuList = menuList;
		this.emailId = emailId;
		// Just for testing
		this.userTimeZone = "Asia/Kolkata";
	}

	public SessionInfo() {

	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public List<String> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 
	 * @return
	 */
	public Long getLastSelectedGroupID() {
		return lastSelectedGroupID;
	}

	/**
	 * 
	 * @param lastSelectedGroupID
	 */
	public void setLastSelectedGroupID(Long lastSelectedGroupID) {
		this.lastSelectedGroupID = lastSelectedGroupID;
	}

	/**
	 * 
	 * @return the userSelectionsList
	 */
	public List<UserSelectionVO> getUserSelectionsList() {
		return userSelectionsList;
	}

	/**
	 * 
	 * @param userSelectionsList
	 *            the userSelectionsList to set
	 */
	public void setUserSelectionsList(List<UserSelectionVO> userSelectionsList) {
		this.userSelectionsList = userSelectionsList;
	}

	public final String getUserTimeZone() {
		return userTimeZone;
	}

	public final void setUserTimeZone(final String userTimeZone) {
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

	/**
	 * @return the roleTypeMap
	 */
	public Map<Integer, String> getRoleTypeMap() {
		return roleTypeMap;
	}

	/**
	 * @param roleTypeMap
	 *            the roleTypeMap to set
	 */
	public void setRoleTypeMap(Map<Integer, String> roleTypeMap) {
		this.roleTypeMap = roleTypeMap;
	}

	/**
	 * @return the functionalGroupMap
	 */
	public Map<Integer, String> getFunctionalGroupMap() {
		return functionalGroupMap;
	}

	/**
	 * @param functionalGroupMap
	 *            the functionalGroupMap to set
	 */
	public void setFunctionalGroupMap(Map<Integer, String> functionalGroupMap) {
		this.functionalGroupMap = functionalGroupMap;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	//add by shanf
	public List<String> getPermissionNameList() {
		return permissionNameList;
	}

	public void setPermissionNameList(List<String> permissionNameList) {
		this.permissionNameList = permissionNameList;
	}
	/**
	 * @return the csrfToken
	 */
	public String getCsrfToken() {
		return csrfToken;
	}

	/**
	 * @param csrfToken
	 *            the csrfToken to set
	 */
	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	@Override
	protected SessionInfo clone() {
		SessionInfo clone = null;
		try {
			clone = (SessionInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return clone;
	}

}

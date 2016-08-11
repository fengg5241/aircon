package com.panasonic.b2bacns.bizportal.usermanagement.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.Role;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.FetchRoleListVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.FunctionalGroupVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.RoleTypeVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserDetailsVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserUnderCompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UsersVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UsersViewLogVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.viewRoleLogVO;

public interface UserManagementDAO {

	public String generateUserId(String companyId) throws HibernateException;
	/**
	 * Returns list of site groups associated with the company.
	 * 
	 * @param companyId
	 * @return
	 * @throws HibernateException
	 */
	/*
	 * public List<SiteGroupVO> getSiteGroupListByCompanyId(String companyId)
	 * throws HibernateException;
	 */

	/**
	 * Returns list of site groups with which the group is associated
	 * 
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	/*
	 * public List<SiteGroupVO> getSiteGroupNames(Long[] id) throws
	 * HibernateException;
	 */

	/**
	 * Returns list of logical groups associated with the site
	 * 
	 * @param siteId
	 * @return
	 * @throws HibernateException
	 */
	/*
	 * public List<LogicalGroupVO> getLogicalGroup(Long[] siteId) throws
	 * HibernateException;
	 */

	/**
	 * Returns list of control groups associated with the logical groups
	 * 
	 * @param logicalId
	 * @return
	 * @throws HibernateException
	 */
	/*
	 * public List<ControlGroupVO> getControlGroupName(Long[] logicalId) throws
	 * HibernateException;
	 */

	/**
	 * This method handles request for getRoleList and takes userId returns
	 * Roles name and Roles Id from "Roles" table.
	 * 
	 * @param userId
	 * @return
	 * @throws HibernateException
	 */
	public List<FetchRoleListVO> getRoleList(Long userId)
			throws HibernateException;

	/**
	 * This method handles request for getFuncGrpList and takes role_type_id
	 * returns FunctionalGroup id and FunctionalGroup name from
	 * "FunctionalGroup" table.
	 * 
	 * @param role_type_id
	 * @return
	 * @throws HibernateException
	 */
	public List<FunctionalGroupVO> getFunctionalGroupName(Integer role_type_id)
			throws HibernateException;

	/**
	 * This method handles request for getRoleType and takes roleTypeid returns
	 * RolesType id and RolesType name from "RoleType" table.
	 * 
	 * @param roleTypeId
	 * @return
	 * @throws HibernateException
	 */
	public List<RoleTypeVO> getRoleTypeName(Integer roleTypeId)
			throws HibernateException;

	/**
	 * Get permissions assigned to the company from "Permissions" table
	 * 
	 * @param companyId
	 * @return
	 * @throws HibernateException
	 */
	/*
	 * public List<PermissionsVO> getPermissionDetails(Long companyId) throws
	 * HibernateException;
	 */

	/**
	 * This method adds a record in "RoleHistory" table that a new role is
	 * created
	 * 
	 * @param saveStatus
	 * @param role
	 */
	public void addRoleHistory(Long saveStatus, Role role);

	/**
	 * This method adds a record in "RoleHistory" table that the role is edited
	 * 
	 * @param role
	 */
	public void addRoleHistory(Role role);

	/**
	 * This method adds a record in "RoleHistory" table that the role is deleted
	 * 
	 * @param loggedinUserId
	 * @param role
	 */
	public void addRoleHistoryDelete(Long loggedinUserId, Role role);

	/**
	 * Returns complete role history of the given user associated with the given
	 * company
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public List<viewRoleLogVO> viewRoleLog(String companyId, Long userId);

	/**
	 * This method handles simple user list request. It takes Company_Id from
	 * request and if it is not available then from session and returns loginId
	 * list
	 * 
	 * @param companyId
	 * @return
	 */
	/* public List<UsersVO> getUserIdList(String companyId); */

	/**
	 * This method handles Full user list request. It takes Company_Id from
	 * request and if it is not available then from session and returns Full
	 * User list
	 * 
	 * @param userId
	 * @return
	 */
	public List<UsersVO> getUserIdListFull(Long userId);

	/**
	 * This method fetch the complete group hierarchy of all the companies
	 * 
	 * @return
	 */
	public List<Group> getGroupHierarchy();

	/**
	 * This method is used to activate users email id.
	 * 
	 * @param emailadd
	 * @param token
	 * @return
	 */
	public String updateUserDetails(String emailadd, String token);

	/**
	 * This method is used to view all logs of the user
	 * 
	 * @param user_id
	 * @return
	 */
	public List<UsersViewLogVO> getviewLog(Long user_id);

	/**
	 * This method is used to reset the password of the user and returns the
	 * complete user details
	 * 
	 * @param loginId
	 * @param encryptPassword
	 * @param userIdSession
	 * @return
	 * @throws HibernateException
	 */
	public User resetPassword(String loginId, String encryptPassword,
			Long userIdSession) throws HibernateException;

	/**
	 * This method is used to add a record in "UserMangementHistory" table to
	 * keep track of user activities at the time of reset user
	 * 
	 * @param userIdSession
	 * @param userId
	 * @return
	 * @throws HibernateException
	 */
	public boolean addUserMangementHistory(Long userIdSession, Long userId)
			throws HibernateException;

	/**
	 * This method is used to fetch company id of the user with which it is
	 * associated on the basis of his login id
	 * 
	 * @param loginId
	 * @return
	 * @throws HibernateException
	 */
	public String getCompanyId(String loginId) throws HibernateException;

	/**
	 * This method is used to delete all functional group ids associated with
	 * the role at the time of editing a role
	 * 
	 * @param roleId
	 */
	public void deleteFunctionalIds(Long roleId);

	/**
	 * This method is used to get complete hierarchy of the group based on group
	 * id. If company id is passed, it returns all the groups in hierarchy
	 * format associated with that company
	 * 
	 * @param groupIds
	 * @param compID
	 * @return
	 */
	public List<Group> getGroupHierarchy(String groupIds, boolean compID);

	/**
	 * This method is used to fetch details of the user
	 * 
	 * @param userId
	 * @return
	 * @throws HibernateException
	 */
	public UserDetailsVO retrieveUserDetails(Long userId)
			throws HibernateException;

	/**
	 * This method is used to get details of groups to store in
	 * userManagementHistory
	 * 
	 * @param groupIDs
	 * @return
	 */
	public Map<Long, String> getGroupDetailsForHistory(Set<Long> groupIDs);

	/**
	 * This method is used to check if role name exists in "Roles" table
	 * 
	 * @param roleName
	 * @return
	 * @throws HibernateException
	 */
	public boolean checkRoleName(String roleName) throws HibernateException;

	/**
	 * This method returns the list of users associated with the given company
	 * id and created by the logged in user
	 * 
	 * @param companyId
	 * @param userId
	 * @param loginId
	 * @return
	 */
	//modified by seshu.
	public List<UserUnderCompanyVO> getUserListUnderCompany(Long companyId,
			Long userId,String loginId);

	//Added by seshu.
	public boolean getRoleAssignedStatus(String roleId) throws HibernateException;
    
	//Added by seshu.
	public List<UsersVO> getAllUserIdListFull(String companyId);
}

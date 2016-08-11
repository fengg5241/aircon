package com.panasonic.b2bacns.bizportal.usermanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Role;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupCompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupUserVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserDetailsVO;

public interface ManageUserManagementService {

	/**
	 * Returns the generated user id required at the time of registering a new
	 * user.
	 * 
	 * @param companyId
	 * @return
	 * @throws HibernateException
	 * @throws JsonProcessingException
	 */
	public String generateUserId(String companyId) throws HibernateException,
			JsonProcessingException;

	/**
	 * Returns list of site groups associated with the company.
	 * 
	 * @param companyId
	 * @return
	 * @throws JsonProcessingException
	 */
	/*
	 * public String getSiteGroupListByCompanyId(String companyId) throws
	 * JsonProcessingException;
	 */

	/**
	 * Returns names of site groups with which the group is associated
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	/*
	 * public String getSiteGroupNames(Long[] id) throws
	 * JsonProcessingException, HibernateException;
	 */

	/**
	 * Returns names of logical groups associated with the site
	 * 
	 * @param siteId
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	/*
	 * public String getLogicalGroupName(Long[] siteId) throws
	 * JsonProcessingException, HibernateException;
	 */

	/**
	 * Returns names of control groups associated with the logical groups
	 * 
	 * @param logicalId
	 * @return
	 * @throws JsonProcessingException
	 */
	/*
	 * public String getControlGroupName(Long[] logicalId) throws
	 * JsonProcessingException;
	 */

	/**
	 * This method handles request for getRoleList and takes userId returns
	 * Roles name and Roles Id from "Roles" table.
	 * 
	 * @param userId
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	public String getRoleList(Long userId) throws JsonProcessingException,
			HibernateException;

	/**
	 * This method handles request for getRoleType and takes roleTypeid returns
	 * RolesType id and RolesType name from "RoleType" table.
	 * 
	 * @param roleTypeid
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	public String getRoleTypeName(Integer roleTypeid)
			throws JsonProcessingException, HibernateException;

	/**
	 * This method handles request for getFuncGrpList and takes role_type_id
	 * returns FunctionalGroup id and FunctionalGroup name from
	 * "FunctionalGroup" table.
	 * 
	 * @param role_type_id
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	public String getFuncGrpList(Integer role_type_id)
			throws JsonProcessingException, HibernateException;

	/**
	 * Get permissions assigned to the company from "Permissions" table
	 * 
	 * @param companyId
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	/*
	 * public String getPermissionDetails(Long companyId) throws
	 * JsonProcessingException, HibernateException;
	 */

	/**
	 * This method handles add new role request. It takes Company_Id from
	 * session, Role_name, Role_Type_id, Functional_Id parameter from request
	 * and return status true/false
	 * 
	 * @param role
	 * @param functional_id
	 * @return
	 * @throws BusinessFailureException
	 */
	public boolean addNewRole(Role role, List<Long> functional_id)
			throws BusinessFailureException;

	/**
	 * This method handles Edit role request. It takes Company_Id, Role_name,
	 * Role_Type_id, Functional_Id parameter from request and return status
	 * true/false
	 * 
	 * @param role
	 * @param functional_ids
	 * @param roleId
	 * @return
	 * @throws BusinessFailureException
	 */
	public boolean editRole(Role role, List<Long> functional_ids, Long roleId)
			throws BusinessFailureException;

	/**
	 * This method handles Delete role request. It takes Company_Id from
	 * session, Role_ID parameter from request and return status true/false
	 * 
	 * @param loggedinUserId
	 * @param role_Id
	 * @return
	 * @throws BusinessFailureException
	 */
	public boolean deleteRole(Long loggedinUserId, Long role_Id)
			throws BusinessFailureException;

	/**
	 * This method returns role on the basis of role id from "Roles" table
	 * 
	 * @param role_Id
	 * @return
	 */
	public Role getRole(Long role_Id);

	/**
	 * Returns complete role history of the given user associated with the given
	 * company
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws JsonProcessingException
	 */
	public String viewRoleLog(String companyId, Long userId)
			throws JsonProcessingException;

	/**
	 * This method handles simple user list request. It takes Company_Id from
	 * request and if it is not available then from session and returns loginId
	 * list
	 * 
	 * @param companyId
	 * @return
	 * @throws JsonProcessingException
	 */
	/*
	 * public String getUserIdList(String companyId) throws
	 * JsonProcessingException;
	 */

	/**
	 * This method handles Full user list request. It takes Company_Id from
	 * request and if it is not available then from session and returns Full
	 * User list
	 * 
	 * @param userId
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getUserIdListFull(Long userId) throws JsonProcessingException;

	/**
	 * This method is used to register a new user.
	 * 
	 * @param sessionInfo
	 * @param user_Id
	 * @param companyId
	 * @param group_ids
	 * @param role_Id
	 * @return
	 * @throws JsonProcessingException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessFailureException
	 */
	public String userRegistration(SessionInfo sessionInfo, String user_Id,
			String companyId, Set<Long> group_ids, String role_Id)
			throws JsonProcessingException, NoSuchAlgorithmException,
			BusinessFailureException;

	/**
	 * This method is used to generate a new password
	 * 
	 * @return
	 */
	public String generatePassword();

	/**
	 * This method is used to download user details at the time of user
	 * registration
	 * 
	 * @param user_id
	 * @param password
	 * @param userId_Generated
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	public String downloadNewUserDetails(String user_id, String password,
			Long userId_Generated) throws JsonProcessingException,
			HibernateException;

	/**
	 * This method is used to activate users email id.
	 * 
	 * @param emailAddr
	 * @param token
	 * @return
	 */
	/*
	 * public String activateEmail(String emailAddr, String token);
	 */

	/**
	 * This method is used to view all logs of the user
	 * 
	 * @param user_id
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getviewLog(Long user_id) throws JsonProcessingException;

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
	public String getCompanyIdByLoginId(String loginId)
			throws HibernateException;

	/**
	 * This method is used to download details of the user at the time of
	 * resetting it
	 * 
	 * @param userFullName
	 * @param newPassword
	 * @return
	 */
	public String downloadResetUserDetails(String userFullName,
			String newPassword);

	/**
	 * getGroupsHierarchyByCompanyId will provide detailed information group
	 * structure, as per company
	 * 
	 * @param sessionInfo
	 * @param update
	 * @return
	 * @throws HibernateException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BusinessFailureException
	 */
	public List<GroupCompanyVO> getGroupsHierarchyByCompanyId(
			SessionInfo sessionInfo, boolean update, Long userIdReq)
			throws HibernateException, IllegalAccessException,
			InvocationTargetException, BusinessFailureException;

	/**
	 * This method is used to get complete hierarchy of the groups
	 * 
	 * @param sessionInfo
	 * @param update
	 * @param userIdReq
	 * @return
	 */
	public List<GroupUserVO> getGroups(SessionInfo sessionInfo, boolean update,
			Long userIdReq);

	/**
	 * fetch details of the user
	 * 
	 * @param userId
	 * @return
	 * @throws HibernateException
	 */
	public UserDetailsVO retrieveUserDetails(Long userId)
			throws HibernateException;

	/**
	 * This method is used to get all user details by his login id
	 * 
	 * @param loginId
	 * @return
	 */
	public User getUser(String loginId);

	/**
	 * This method is used to update user details of an existing user
	 * 
	 * @param user_Id
	 * @param role_Id
	 * @param accountState
	 * @param group_ids
	 * @param sessionInfo
	 * @param companyId
	 * @param prev_group_ids
	 * @param userInfo
	 * @return
	 * @throws JsonProcessingException
	 * @throws BusinessFailureException
	 */
	public String updateUser(String user_Id, String role_Id,
			String accountState, Set<Long> group_ids, SessionInfo sessionInfo,
			String companyId, Set<Long> prev_group_ids, boolean userInfo)
			throws JsonProcessingException, BusinessFailureException;

	/**
	 * This method handles request for getCompanyList and returns Company name
	 * and Company Id from "companies" table.
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getCompanyList() throws JsonProcessingException;

	/**
	 * This method returns the list of users associated with the given company
	 * id and created by the logged in user
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws JsonProcessingException
	 * @author seshu.
	 */
	public String getUserListUnderCompany(Long companyId, Long userId,String loginId)
			throws JsonProcessingException;

	/**
	 * This method returns all the groups associated with all the given company
	 * in a hierarchical manner.
	 * 
	 * @param companyIds
	 * @param updateUser
	 * @return
	 */
	public List<GroupCompanyVO> getMultipleCompanyUserGroup(
			List<Long> companyIds, boolean updateUser);
	//Added by seshu.
	public String getTotalRoleList() throws JsonProcessingException;
	//Added by seshu.
	public String getAllUserIdListFull(String companyId) throws JsonProcessingException;
	public boolean edditRole(Role role, List<Long> functional_ids, Long roleId);
    //Added by seshu
	public List<GroupUserVO> getAssignedGroupinfo(SessionInfo sessionInfo, boolean update, Long userIdReq);
	//Added by seshu
	public List<GroupUserVO> getCustomerGroups(SessionInfo sessionInfo, boolean update, Long userIdReq, Long companyId);
	//Added by seshu
	public List<GroupCompanyVO> getCustomerGroupsHierarchyByCompanyId(SessionInfo sessionInfo, boolean update, Long userIdReq,Long companyID)
			throws HibernateException, IllegalAccessException, InvocationTargetException, BusinessFailureException;
}

package com.panasonic.b2bacns.bizportal.usermanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.Role;
import com.panasonic.b2bacns.bizportal.persistence.Rolefunctionalgrp;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.persistence.UserMangementHistory;
import com.panasonic.b2bacns.bizportal.service.CompanyUserService;
import com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.CompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.DownloadResetPasswordVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.DownloadUserDetailsVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.FetchRoleListVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.FunctionalGroupVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupCompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupUserVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.RoleTypeVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.TotalRoleVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserDetailsVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserUnderCompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UsersVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UsersViewLogVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.viewRoleLogVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.GroupUserComparator;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;
import com.panasonic.b2bacns.common.reports.DataTableHeader;
import com.panasonic.b2bacns.common.reports.HeadingTextProperties;
import com.panasonic.b2bacns.common.reports.ReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportMetadata;

@Service
public class ManageUserManagementServiceImpl implements
		ManageUserManagementService {

	private static final Logger logger = Logger
			.getLogger(ManageUserManagementServiceImpl.class);

	@Autowired
	private CompanyUserService companyUserService;

	@Autowired
	private UserManagementDAO userManagementDAO;

	@Resource(name = "properties")
	private Properties bizProperties;

	// Added by seshu.
	private GenericDAO<Role> roleDao;

	@Autowired
	public void setDao(GenericDAO<Role> daoToSet) {
		roleDao = daoToSet;
		roleDao.setClazz(Role.class);
	}

	private GenericDAO<Rolefunctionalgrp> rolefunctionalgrpDao;

	@Autowired
	public void setDao1(GenericDAO<Rolefunctionalgrp> daoToSet) {
		rolefunctionalgrpDao = daoToSet;
		rolefunctionalgrpDao.setClazz(Rolefunctionalgrp.class);
	}

	private GenericDAO<User> userDao;

	@Autowired
	public void setUserDao(GenericDAO<User> daoToSet) {
		userDao = daoToSet;
		userDao.setClazz(User.class);
	}

	private GenericDAO<Companiesuser> companiesUserDao;

	@Autowired
	public void setCompaniesUserDao(GenericDAO<Companiesuser> daoToSet) {
		companiesUserDao = daoToSet;
		companiesUserDao.setClazz(Companiesuser.class);
	}

	private GenericDAO<UserMangementHistory> managementUserHistoryDao;

	@Autowired
	public void setUserMangementHistoryDao(
			GenericDAO<UserMangementHistory> daoToSet) {
		managementUserHistoryDao = daoToSet;
		managementUserHistoryDao.setClazz(UserMangementHistory.class);
	}

	private GenericDAO<Company> companyDao;

	// Added by seshu.
	private GenericDAO<Role> rolesDao;

	@Autowired
	public void setCompanyDao(GenericDAO<Company> daoToSet) {
		companyDao = daoToSet;
		companyDao.setClazz(Company.class);
	}

	@Value("${userManagementHistory.auditAction.created}")
	String auditAction;

	@Value("${userManagementHistory.auditAction.updated}")
	String auditActionUpdate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#generateUserId(java.lang.String)
	 */
	@Override
	@Transactional
	public String generateUserId(String companyId) throws HibernateException,
			JsonProcessingException {
		String userId = null;
		try {
			synchronized (userDao) {
				User newUser = new User();
				newUser.setCompanyId(Long.valueOf(companyId));
				newUser.setFailedattempt(0);
				newUser.setLoginid("tempid");
				Long newUserID = userDao.create(newUser);

				userId = createUserId(String.valueOf(newUserID));
				newUser.setLoginid(userId);
				userDao.update(newUser);

				Map<String, String> userIdMap = new HashMap<String, String>();
				userIdMap.put("userid", userId);
				userId = CommonUtil.convertFromEntityToJsonStr(userIdMap);
			}
		} catch (Exception e) {
			logger.error("Error occured while Generating new user ID", e);

			userId = CommonUtil
					.convertFromEntityToJsonStr(BizConstants.NO_RECORDS_FOUND);
		}

		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#generatePassword()
	 */
	@Override
	public String generatePassword() {

		String passString = BizConstants.passwordString;

		int n = passString.length();

		StringBuilder defaultPassword = new StringBuilder();

		Random r = new Random();

		for (int i = 0; i < BizConstants.passwordLength; i++)
			defaultPassword.append(passString.charAt(r.nextInt(n)));

		return defaultPassword.toString();
	}

	private String createUserId(String userId) {

		if (!userId.equalsIgnoreCase("0")) {
			StringBuilder user_id = new StringBuilder(BizConstants.userId);

			user_id.replace(user_id.length() - userId.length(),
					user_id.length(), userId);

			return user_id.toString();

		} else
			return BizConstants.EMPTY_STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getSiteGroupListByCompanyId(java.lang.String)
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String getSiteGroupListByCompanyId(String
	 * companyId) throws JsonProcessingException, HibernateException {
	 * 
	 * String siteGroupsJson = BizConstants.EMPTY_STRING;
	 * 
	 * List<SiteGroupVO> listSiteGroups = userManagementDAO
	 * .getSiteGroupListByCompanyId(companyId);
	 * 
	 * if (listSiteGroups != null && !listSiteGroups.isEmpty() &&
	 * listSiteGroups.size() > 0) {
	 * 
	 * siteGroupsJson = CommonUtil .convertFromEntityToJsonStr(listSiteGroups);
	 * } return siteGroupsJson; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getSiteGroupNames(java.lang.Long[])
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String getSiteGroupNames(Long[] id) throws
	 * JsonProcessingException, HibernateException {
	 * 
	 * String siteGroupNamesJson = BizConstants.EMPTY_STRING;
	 * 
	 * List<SiteGroupVO> listSiteGroups = userManagementDAO
	 * .getSiteGroupNames(id);
	 * 
	 * if (listSiteGroups != null && !listSiteGroups.isEmpty() &&
	 * listSiteGroups.size() > 0) {
	 * 
	 * siteGroupNamesJson = CommonUtil
	 * .convertFromEntityToJsonStr(listSiteGroups); } return siteGroupNamesJson;
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getLogicalGroupName(java.lang.Long[])
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String getLogicalGroupName(Long[] siteId) throws
	 * JsonProcessingException, HibernateException {
	 * 
	 * String logicalGroupJson = BizConstants.EMPTY_STRING;
	 * 
	 * List<LogicalGroupVO> listGroups = userManagementDAO
	 * .getLogicalGroup(siteId);
	 * 
	 * if (!listGroups.isEmpty() && listGroups.size() > 0) {
	 * 
	 * logicalGroupJson = CommonUtil .convertFromEntityToJsonStr(listGroups); }
	 * 
	 * return logicalGroupJson; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getControlGroupName(java.lang.Long[])
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String getControlGroupName(Long[] logicalId) throws
	 * JsonProcessingException {
	 * 
	 * String controlGroupJson = BizConstants.EMPTY_STRING;
	 * 
	 * List<ControlGroupVO> listControlGroup = userManagementDAO
	 * .getControlGroupName(logicalId);
	 * 
	 * if (listControlGroup != null && !listControlGroup.isEmpty() &&
	 * listControlGroup.size() > 0) {
	 * 
	 * controlGroupJson = CommonUtil
	 * .convertFromEntityToJsonStr(listControlGroup); } else { throw new
	 * GenericFailureException("error in getControlGroupName"); } return
	 * controlGroupJson; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getRoleList(java.lang.Long)
	 */
	@Override
	@Transactional
	public String getRoleList(Long userId) throws JsonProcessingException,
			HibernateException {

		List<FetchRoleListVO> listRolelist = null;

		String roleListJson = BizConstants.EMPTY_STRING;

		listRolelist = userManagementDAO.getRoleList(userId);

		if (listRolelist != null && !listRolelist.isEmpty()
				&& listRolelist.size() > 0) {

			roleListJson = CommonUtil.convertFromEntityToJsonStr(listRolelist);
		} else {
			// Added by seshu.(To check the log file)
			logger.error("start of log" + BizConstants.NO_RECORDS_FOUND);
			roleListJson = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			logger.error("end of log" + BizConstants.NO_RECORDS_FOUND);
		}
		return roleListJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getRoleTypeName(java.lang.Integer)
	 */
	@Override
	@Transactional
	public String getRoleTypeName(Integer roleTypeid)
			throws JsonProcessingException, HibernateException {

		String roleTypeListJson = BizConstants.EMPTY_STRING;

		List<RoleTypeVO> roleTypelist = userManagementDAO
				.getRoleTypeName(roleTypeid);

		if (roleTypelist != null && !roleTypelist.isEmpty()
				&& roleTypelist.size() > 0) {

			roleTypeListJson = CommonUtil
					.convertFromEntityToJsonStr(roleTypelist);
		}
		return roleTypeListJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getFuncGrpList(java.lang.Integer)
	 */
	@Override
	@Transactional
	public String getFuncGrpList(Integer role_type_id)
			throws JsonProcessingException, HibernateException {

		String funcGrplistJson = BizConstants.EMPTY_STRING;

		List<FunctionalGroupVO> funcGrplist = userManagementDAO
				.getFunctionalGroupName(role_type_id);

		if (funcGrplist != null && !funcGrplist.isEmpty()
				&& funcGrplist.size() > 0) {

			funcGrplistJson = CommonUtil
					.convertFromEntityToJsonStr(funcGrplist);
		}
		return funcGrplistJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getPermissionDetails(java.lang.Long)
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String getPermissionDetails(Long companyId) throws
	 * JsonProcessingException, HibernateException {
	 * 
	 * String permissionlistJson = BizConstants.EMPTY_STRING;
	 * 
	 * List<PermissionsVO> permissionlist = userManagementDAO
	 * .getPermissionDetails(companyId);
	 * 
	 * if (permissionlist != null && !permissionlist.isEmpty() &&
	 * permissionlist.size() > 0) {
	 * 
	 * permissionlistJson = CommonUtil
	 * .convertFromEntityToJsonStr(permissionlist); } return permissionlistJson;
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService
	 * #addNewRole(com.panasonic.b2bacns.bizportal.persistence.Role,
	 * java.util.List)
	 * 
	 * @param : role, functional_Id
	 * 
	 * @return : addStatus of Boolean type
	 */
	@Override
	@Transactional
	public boolean addNewRole(Role role, List<Long> functional_id)
			throws BusinessFailureException {

		Long saveStatus = 0L;

		boolean addStatus = false;

		Rolefunctionalgrp rolefunctionalgrp = null;
		List<Rolefunctionalgrp> rfg = null;

		try {
			// checkRoleName
			String roleName = role.getName();
			boolean roleNameExists = userManagementDAO.checkRoleName(roleName);

			if (!roleNameExists) {

				saveStatus = roleDao.create(role);
				if (saveStatus > 0) {
					userManagementDAO.addRoleHistory(saveStatus, role);

				}

			} else {
				throw new BusinessFailureException("role_name.alreadyexist");
			}

		}

		catch (HibernateException hibernateException) {
			logger.error("" + hibernateException);
		}

		if (saveStatus > 0L) {

			rfg = new ArrayList<Rolefunctionalgrp>();

			Date date = new Date();
			for (Long functionalId : functional_id) {

				rolefunctionalgrp = new Rolefunctionalgrp();

				rolefunctionalgrp.setFuncgroupids(functionalId.intValue());
				rolefunctionalgrp.setRoleid(saveStatus.intValue());
				rolefunctionalgrp.setCompanyid(role.getCompany_id());
				rolefunctionalgrp
						.setCreationdate(new Timestamp(date.getTime()));

				rfg.add(rolefunctionalgrp);
			}
		}
		if (rfg != null) {
			rolefunctionalgrpDao.batchSaveOrUpdate(rfg);
			addStatus = true;
		}
		return addStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService
	 * #editRole(com.panasonic.b2bacns.bizportal.persistence.Role,
	 * java.util.List, java.lang.Long)
	 * 
	 * @param : role, functional_Id, roleId
	 * 
	 * @return : EditRoleStatus of Boolean type
	 */
	@Override
	@Transactional
	public boolean editRole(Role role, List<Long> functional_id, Long roleId)
			throws BusinessFailureException {

		boolean editRoleStatus = false;

		Rolefunctionalgrp rolefunctionalgrp = null;
		List<Rolefunctionalgrp> rfg = new ArrayList<Rolefunctionalgrp>();
		try {
			// code added for checking provided name exists in DB
			// checkRoleName
			String roleName = role.getName();
			boolean roleNameExists = userManagementDAO.checkRoleName(roleName);
			if (!roleNameExists) {

				roleDao.saveOrUpdate(role);
				editRoleStatus = true;

				if (editRoleStatus) {
					userManagementDAO.addRoleHistory(role);

				}

			} else {
				throw new BusinessFailureException("role_name.exist");
			}

		}

		catch (HibernateException hibernateException) {
			logger.error("" + hibernateException);
		}

		if (editRoleStatus) {

			userManagementDAO.deleteFunctionalIds(roleId);

			if (functional_id != null) {

				for (Long functionalId : functional_id) {

					rolefunctionalgrp = new Rolefunctionalgrp();

					rolefunctionalgrp.setFuncgroupids(functionalId.intValue());

					rolefunctionalgrp.setRoleid(roleId.intValue());
					rolefunctionalgrp.setCompanyid(role.getCompany_id());

					Date date = new Date();

					rolefunctionalgrp.setCreationdate(new Timestamp(date
							.getTime()));

					rfg.add(rolefunctionalgrp);

				}
			}

		}
		if (rfg != null) {
			rolefunctionalgrpDao.batchSaveOrUpdate(rfg);
			editRoleStatus = true;
		}
		return editRoleStatus;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#deleteRole(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public boolean deleteRole(Long loggedinUserId, Long role_Id)
			throws BusinessFailureException {

		boolean isDelRole = false;

		Role role = getRole(role_Id);

		if (role != null) {

			if (role.getUsers() == null
					|| (role.getUsers() != null && role.getUsers().isEmpty())) {

				// No users assigned

				role.setIsdel(true);

				roleDao.update(role);

				userManagementDAO.addRoleHistoryDelete(loggedinUserId, role);

				isDelRole = true;
			} else {
				// Users assigned to this Role
				throw new BusinessFailureException("role.assignedto.other.user");
			}
		}
		return isDelRole;
	}

	/**
	 * This method returns role on the basis of role id from "Roles" table
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	@Transactional
	public Role getRole(Long roleId) {

		return roleDao.findByID(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#viewRoleLog(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional
	public String viewRoleLog(String companyId, Long userId)
			throws JsonProcessingException {

		String roleListJson = BizConstants.NO_RECORDS_FOUND;

		List<viewRoleLogVO> roleList = userManagementDAO.viewRoleLog(companyId,
				userId);

		if (roleList != null && !roleList.isEmpty() && roleList.size() > 0) {

			roleListJson = CommonUtil.convertFromEntityToJsonStr(roleList);
		}
		return roleListJson;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getUserIdList(java.lang.String)
	 * 
	 * @param : CompanyId
	 * 
	 * @return : userListJson
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String getUserIdList(String companyId) throws
	 * JsonProcessingException {
	 * 
	 * String userListJson = BizConstants.EMPTY_STRING;
	 * 
	 * List<UsersVO> userList = userManagementDAO.getUserIdList(companyId);
	 * 
	 * if (!userList.isEmpty() && userList.size() > 0) {
	 * 
	 * userListJson = CommonUtil.convertFromEntityToJsonStr(userList); } return
	 * userListJson;
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getUserIdListFull(java.lang.Long)
	 * 
	 * @param : userId
	 * 
	 * @return : Full userListJson
	 */
	@Override
	@Transactional
	public String getUserIdListFull(Long userId) throws JsonProcessingException {

		String userListJson = BizConstants.EMPTY_STRING;

		List<UsersVO> userList = userManagementDAO.getUserIdListFull(userId);

		if (userList != null && userList.size() > 0) {

			userListJson = CommonUtil.convertFromEntityToJsonStr(userList);
		} else {
			userListJson = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
		}
		return userListJson;
	}

	/**
	 * This method is used to get complete hierarchy of the groups
	 * 
	 * @param sessionInfo
	 * @param update
	 * @param userIdReq
	 * @return
	 */
	@Override
	@Transactional
	public List<GroupUserVO> getGroups(SessionInfo sessionInfo, boolean update,
			Long userIdReq) {

		Map<String, Object> properties = new HashMap<>();

		Long userId = sessionInfo.getUserId();

		Long companyId = sessionInfo.getCompanyId();

		List<GroupUserVO> completeGroupHierarchy = null;

		List<Long> groupIds = new ArrayList<Long>();

		List<Long> groupIdsReqUser = new ArrayList<Long>();

		List<Group> groupHierarchy = new ArrayList<Group>();

		boolean panasonicUser = false;

		Integer roleTypeid = sessionInfo.getRoleTypeMap().keySet() != null ? (Integer) sessionInfo
				.getRoleTypeMap().keySet().iterator().next()
				: null;

		if (roleTypeid != null) {

			if (update) {

				if (!roleTypeid.equals(1)) {
					// Customer
					if (userId != null) {
						List<User> userObjects = new ArrayList<User>();

						User user = new User();
						user.setId(userId);
						userObjects.add(user);

						User userReq = new User();
						userReq.setId(userIdReq);
						userObjects.add(userReq);
						properties.put("user", userObjects);
					}

					if (companyId != null) {
						Company company = new Company();
						company.setId(companyId);
						properties.put("company", company);
					}

					List<Companiesuser> companiesUsers = companyUserService
							.findAllByProperties(properties);

					for (Companiesuser companiesUser : companiesUsers) {
						if (companiesUser.getGroup() != null
								&& companiesUser.getUserId().equals(userId)) {
							groupIds.add(companiesUser.getGroup().getId());
						}
						if (companiesUser.getGroup() != null
								&& companiesUser.getUserId().equals(userIdReq))
							groupIdsReqUser.add(companiesUser.getGroup()
									.getId());
					}

					if (companyId != null) {

						groupHierarchy = userManagementDAO.getGroupHierarchy(
								companyId.toString(), true);
					}

				} else {
					groupHierarchy = userManagementDAO.getGroupHierarchy();
					if (userIdReq != null) {
						User userReq = new User();
						userReq.setId(userIdReq);
						properties.put("user", userReq);

						List<Companiesuser> companiesUsers = companyUserService
								.findAllByProperties(properties);

						for (Companiesuser companiesUser : companiesUsers) {
							groupIdsReqUser.add(companiesUser.getGroup()
									.getId());
						}

					}
					panasonicUser = true;
				}
			} else {
				if (roleTypeid.equals(1)) {
					groupHierarchy = userManagementDAO.getGroupHierarchy();
					panasonicUser = true;
				} else {
					if (userId != null) {
						User user = new User();
						user.setId(userId);
						properties.put("user", user);
					}

					if (companyId != null) {
						Company company = new Company();
						company.setId(companyId);
						properties.put("company", company);
					}

					List<Companiesuser> companiesUsers = companyUserService
							.findAllByProperties(properties);

					for (Companiesuser companiesUser : companiesUsers) {
						if (companiesUser.getGroup() != null) {
							groupIds.add(companiesUser.getGroup().getId());
						}
					}
				}
			}
		}
		if (groupIds.size() > 0 && !update) {

			String groupIdsSQL = CommonUtil.convertCollectionToString(groupIds);

			// fetches all groups with their children as eager
			// collection in the case of User Role Customer

			groupHierarchy = userManagementDAO.getGroupHierarchy(groupIdsSQL,
					false);

		}

		completeGroupHierarchy = new ArrayList<GroupUserVO>();
		Set<Long> listOfGroups = new TreeSet<Long>();

		if (!groupHierarchy.isEmpty() && groupHierarchy.size() > 0) {

			for (Group grp : groupHierarchy) {

				if (!listOfGroups.contains(grp.getId())) {
					GroupUserVO groupUserVO = populateGroupUserVO(grp,
							groupIds, groupIdsReqUser, panasonicUser,
							listOfGroups);

					listOfGroups.add(grp.getId());

					if (panasonicUser) {
						if (grp.getGroup() == null) {
							completeGroupHierarchy.add(getAllSubGroups(grp,
									groupUserVO, groupIds, groupIdsReqUser,
									panasonicUser, listOfGroups));
						}
					} else {
						completeGroupHierarchy.add(getAllSubGroups(grp,
								groupUserVO, groupIds, groupIdsReqUser,
								panasonicUser, listOfGroups));
					}
				}
				// completeGroupHierarchy.add(groupUserVO);

			}

		}

		return completeGroupHierarchy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService
	 * #getGroupsHierarchyByCompanyId(com.panasonic.b2bacns
	 * .bizportal.common.SessionInfo, boolean, java.lang.Long)
	 */
	// Added by seshu.
	@Override
	@Transactional
	public List<GroupCompanyVO> getGroupsHierarchyByCompanyId(
			SessionInfo sessionInfo, boolean update, Long userIdReq)
			throws HibernateException, IllegalAccessException,
			InvocationTargetException, BusinessFailureException {

		List<GroupUserVO> groupVoList = getAssignedGroupinfo(sessionInfo,
				update, userIdReq);

		Map<Long, List<GroupUserVO>> groupVoMapCompanyWise = new HashMap<Long, List<GroupUserVO>>();

		List<GroupCompanyVO> groupTreeByCompany = new ArrayList<>();

		Map<Long, String> companyInfo = new HashMap<>();

		for (GroupUserVO vo1 : groupVoList) {

			List<GroupUserVO> list = new ArrayList<GroupUserVO>();

			int innerCount = 0;

			if (!groupVoMapCompanyWise.containsKey(vo1.getCompanyId())) {

				for (GroupUserVO vo2 : groupVoList) {

					if (vo1.getCompanyId() == vo2.getCompanyId()) {

						if (innerCount == 0) {
							list.add(vo2);

							if (!companyInfo.containsKey(vo1.getCompanyId())) {
								companyInfo.put(vo1.getCompanyId(),
										vo1.getCompanyName());
							}
							groupVoMapCompanyWise.put(vo1.getCompanyId(), list);
						} else {
							if (!companyInfo.containsKey(vo1.getCompanyId())) {
								companyInfo.put(vo1.getCompanyId(),
										vo1.getCompanyName());
							}
							groupVoMapCompanyWise.get(vo1.getCompanyId()).add(
									vo2);
						}

						innerCount++;

					}

				}

			}

		}

		GroupCompanyVO groupsByCompany = null;

		for (long companyID : companyInfo.keySet()) {
			groupsByCompany = new GroupCompanyVO();
			groupsByCompany.setCompanyID(companyID);
			// added by seshu.(to display group tree from company level)
			groupsByCompany.setText(companyInfo.get(companyID));
			groupsByCompany.setChildren(groupVoMapCompanyWise.get(companyID));
			groupTreeByCompany.add(groupsByCompany);
		}

		return groupTreeByCompany;
	}

	private GroupUserVO populateGroupUserVO(Group group, List<Long> groupIds,
			List<Long> groupReqUserIds, boolean panasonicUser,
			Set<Long> listOfGroups) {

		GroupUserVO groupUserVO = new GroupUserVO();

		Map<String, Object> attrMap = new HashMap<String, Object>();

		Map<String, Object> stateMap = new HashMap<String, Object>();

		groupUserVO.setGroupId(group.getId());
		groupUserVO.setGroupName(group.getName());
		groupUserVO.setCompanyId(group.getCompany().getId());
		groupUserVO.setCompanyName(group.getCompany().getName());
		groupUserVO.setGroupCategory(group.getGroupcategory()
				.getGroupcategoryname());
		groupUserVO.setGroupTypeLevelID(group.getGroupLevel().getId());
		groupUserVO.setGroupTypeLevelName(group.getGroupLevel()
				.getTypeLevelName());

		groupUserVO.setSvgpath(group.getSvgPath());
		groupUserVO.setGroupCriteria(group.getGroupscriteria().getId());
		groupUserVO.setLevel(group.getLevel());
		groupUserVO.setText(group.getName());

		String[] groups = group.getPath().split("\\|");

		List<String> grpIds = Arrays.asList(groups);

		stateMap.put("opened", true);

		boolean enable = false;

		if (panasonicUser)
			enable = true;
		else {
			for (String groupId : grpIds) {

				if (StringUtils.isNotBlank(groupId))
					if (groupIds != null) {
						if (groupIds.contains(Long.parseLong(groupId))
								&& groupId.equalsIgnoreCase(group.getId()
										.toString())) {
							enable = true;

						}
						if (getParentStatus(grpIds, groupId, groupIds))
							enable = true;
					}
			}
		}

		if (!enable) {
			groupUserVO.setEnable(false);
		}

		attrMap.put("dataId", group.getId());
		attrMap.put("svgPath", group.getSvgPath());
		groupUserVO.setLi_attr(attrMap);

		if (groupReqUserIds != null) {
			for (String groupId : grpIds) {
				if (StringUtils.isNotBlank(groupId)) {
					if (groupReqUserIds.size() > 0
							&& groupReqUserIds
									.contains(Long.parseLong(groupId))
							&& groupId.equalsIgnoreCase(group.getId()
									.toString())) {
						stateMap.put("selected", true);
					} else if (getParentStatus(grpIds, groupId, groupReqUserIds)) {
						stateMap.put("selected", true);
					} else
						stateMap.put("selected", false);
				}
			}
		}

		groupUserVO.setState(stateMap);

		/*
		 * groupUserVO = getAllSubGroups(group, groupUserVO, groupIds,
		 * groupReqUserIds, panasonicUser);
		 */

		return groupUserVO;

	}

	private boolean getParentStatus(List<String> grpIds, String groupId,
			List<Long> parentList) {

		boolean parentStatus = false;

		for (Long parent : parentList) {
			for (String id : grpIds) {
				if (id.equalsIgnoreCase(parent.toString()))
					parentStatus = true;
				if (id.equalsIgnoreCase(groupId) && parentStatus)
					return true;
			}
		}

		return false;
	}

	/**
	 * Recursive method to get all subgroups of a group and populate GroupUserVO
	 * 
	 * @param vo
	 * @param parent
	 * @return
	 */
	private GroupUserVO getAllSubGroups(Group vo, GroupUserVO parent,
			List<Long> groupIds, List<Long> groupReqUserIds,
			boolean panasonicUser, Set<Long> listOfGroups) {

		List<GroupUserVO> sub = new ArrayList<GroupUserVO>();

		for (Group subGroup : vo.getGroups()) {

			GroupUserVO subgrp = populateGroupUserVO(subGroup, groupIds,
					groupReqUserIds, panasonicUser, listOfGroups);

			listOfGroups.add(subgrp.getGroupId());
			sub.add(subgrp);

			getAllSubGroups(subGroup, subgrp, groupIds, groupReqUserIds,
					panasonicUser, listOfGroups);

		}
		if (!sub.isEmpty()) {

			Collections.sort(sub, new GroupUserComparator());

			parent.setChildren(sub);
		}

		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#activateEmail(java.lang.String,
	 * java.lang.String)
	 */
	/*
	 * @Override
	 * 
	 * @Transactional public String activateEmail(String emailAddr, String
	 * token) { String error = BizConstants.EMPTY_STRING;
	 * 
	 * error = userManagementDAO.updateUserDetails(emailAddr, token);
	 * 
	 * return error; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService
	 * #userRegistration(com.panasonic.b2bacns.bizportal.common.SessionInfo,
	 * java.lang.String, java.lang.String, java.util.Set, java.lang.String)
	 */
	@Override
	@Transactional
	public String userRegistration(SessionInfo sessionInfo,
			String newUserLoginID, String companyId, Set<Long> group_ids,
			String newUserRoleID) throws JsonProcessingException,
			NoSuchAlgorithmException, BusinessFailureException {

		Long newUserID = null;
		String filePath = null;

		Long roleId = Long.parseLong(newUserRoleID);

		Role newUserRole = getRole(roleId);

		if (newUserRole.getRoletype().getRoletypename()
				.equals(BizConstants.ROLE_TYPE_PANASONIC)) {
			if (!companyId.equals(String
					.valueOf(BizConstants.PANASONIC_COMPANY_ID))) {

				throw new BusinessFailureException("panasonic.user.company.id");
			}
		}

		// User user = new User();
		User user = findUserByLoginID(newUserLoginID);
		if (user == null) {
			throw new BusinessFailureException(BizConstants.NO_RECORDS_FOUND);
		}

		String newUserPassword = generatePassword();
		user.setCreatedby(sessionInfo.getUserId().toString());
		user.setCreationdate(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		user.setEncryptedpassword(PasswordEncryptionDecryption
				.getEncryptedPassword(newUserPassword));
		user.setIsverified((byte) 0);
		// user.setLoginid(newUserLoginID);
		user.setValid(true);
		user.setFailedattempt(0);
		user.setRolesId(roleId);
		user.setCompanyId(Long.parseLong(companyId));

		try {

			newUserID = userDao.create(user);
			user.setId(newUserID);

			// Modified by seshu.
			if (newUserRole.getRoletype().getRoletypename()
					.equals(BizConstants.ROLE_TYPE_CUSTOMER)
					|| newUserRole.getRoletype().getRoletypename()
							.equals(BizConstants.ROLE_TYPE_INSTALLER)
					|| newUserRole.getRoletype().getRoletypename()
							.equals(BizConstants.ROLE_TYPE_PANASONIC)) {

				// Assign groups in Company_Users table only if the role type is
				// Customer or Installer
				Companiesuser companiesuser = null;
				List<Companiesuser> newUserAssignedGroupsList = new ArrayList<Companiesuser>();

				if (group_ids != null && group_ids.size() > 0) {

					for (Long group_id : group_ids) {
						companiesuser = new Companiesuser();
						companiesuser.setCompanyId(Long.parseLong(companyId));
						companiesuser.setGroupId(group_id);
						companiesuser.setUserId(user.getId());
						companiesuser.setCreationdate((Timestamp) user
								.getCreationdate());

						newUserAssignedGroupsList.add(companiesuser);
					}

					companiesUserDao
							.batchSaveOrUpdate(newUserAssignedGroupsList);
				}
			}

			UserMangementHistory userMangementHistory = new UserMangementHistory();
			userMangementHistory.setAuditaction(auditAction);
			userMangementHistory.setCreatedby(Long.parseLong(user
					.getCreatedby()));
			userMangementHistory.setCreationdate((Timestamp) user
					.getCreationdate());
			userMangementHistory.setUpdatedate((Timestamp) user
					.getCreationdate());
			userMangementHistory.setUpdatedby(Long.parseLong(user
					.getCreatedby()));
			userMangementHistory.setUser_id(user.getId());

			managementUserHistoryDao.saveOrUpdate(userMangementHistory);

			filePath = downloadNewUserDetails(user.getLoginid(),
					newUserPassword, newUserID);

		} catch (RuntimeException exp) {
			logger.error("Error occurred while creating user, loginID - "
					+ newUserLoginID, exp);
		} catch (Exception exp) {
			logger.error("Error occurred while creating user, loginID - "
					+ newUserLoginID, exp);
		}

		return filePath;
	}

	/**
	 * This method is used to get user by its login id
	 * 
	 * @param loginID
	 * @return
	 * @throws HibernateException
	 */
	private User findUserByLoginID(String loginID) throws HibernateException {

		User user = null;

		List<User> result = userDao.findAllByProperty("loginid", loginID);
		if (result != null && result.size() > 0) {
			user = result.get(0);
		}
		return user;

	}

	/**
	 * This method is used to download user details at the time of user
	 * registration
	 * 
	 * @param userId
	 * @param password
	 * @param userId_Generated
	 * @return
	 * @throws JsonProcessingException
	 * @throws HibernateException
	 */
	@Override
	public String downloadNewUserDetails(String userId, String password,
			Long userId_Generated) throws JsonProcessingException,
			HibernateException {

		String fileName = BizConstants.EMPTY_STRING;

		List<DownloadUserDetailsVO> userdatalist = new ArrayList<DownloadUserDetailsVO>();

		DownloadUserDetailsVO userdetails = new DownloadUserDetailsVO();
		if (userId_Generated != null && userId_Generated > 0) {
			// change by shanf
			userdetails.setUserId("=\"" + userId + "\"");
			userdetails.setPassword(password);
		}
		userdatalist.add(userdetails);

		ReportGenerator<DownloadUserDetailsVO> report = new CSVReportGenerator<DownloadUserDetailsVO>();

		CSVReportMetadata metadata = new CSVReportMetadata(
				DownloadUserDetailsVO.class, "", "New-User-Details-" + userId);

		List<DataTableHeader> tableheader = new ArrayList<DataTableHeader>();

		DataTableHeader header = new DataTableHeader();
		header.setColumnName("userId");
		header.setDisplayName("User ID");
		tableheader.add(header);

		header = new DataTableHeader();
		header.setColumnName("password");
		header.setDisplayName("Password");
		tableheader.add(header);

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		metadata.setHeadingTextProperties(headerTextList);

		metadata.setDataTableHeading(tableheader);

		// Writes the excel metadata and dataList received in the csv
		try {
			fileName = report.writeTabularReport(metadata, userdatalist);
		} catch (Exception e) {
			logger.error("Error occurred while writing data to Excel", e);
		}

		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#updateUser(java.lang.String,
	 * java.lang.String, java.lang.String, java.util.Set,
	 * com.panasonic.b2bacns.bizportal.common.SessionInfo, java.lang.String,
	 * java.util.Set, boolean)
	 */
	@Override
	@Transactional
	public String updateUser(String user_Id, String role_Id,
			String accountState, Set<Long> group_ids, SessionInfo sessionInfo,
			String companyId, Set<Long> prev_group_ids, boolean userInfo)
			throws JsonProcessingException, BusinessFailureException {

		Long roleId = null;
		boolean userEntityUpdateRequired = false;
		boolean success = false;

		// code refactor as coverity issue Dereference before null check

		Long userId = Long.parseLong(user_Id);

		User user = userDao.findByID(userId);

		if (user == null || (user != null && !user.isValid())) {
			throw new BusinessFailureException("user.state.invalid");
		}

		Role userRole = null;

		if (StringUtils.isNotBlank(role_Id)) {
			roleId = Long.parseLong(role_Id);
			userRole = getRole(roleId);
		}

		// Modified by seshu.
		if (user.getRole().getRoletype().getRoletypename()
				.equals(BizConstants.ROLE_TYPE_CUSTOMER)
				|| user.getRole().getRoletype().getRoletypename()
						.equals(BizConstants.ROLE_TYPE_INSTALLER)) {
			if (userRole == null
					&& (group_ids == null || (group_ids != null && group_ids
							.size() == 0)) && StringUtils.isBlank(accountState)) {
				// At least one information required to be updated
				throw new BusinessFailureException("no.update.info.provided");
			}
		} else {
			if (userRole == null && StringUtils.isBlank(accountState)) {
				// At least one information required to be updated
				throw new BusinessFailureException("no.update.info.provided");
			}
		}

		if (userRole != null
				&& (!user.getRole().getRoletype()
						.equals(userRole.getRoletype()))) {
			throw new BusinessFailureException("user.role.type.cannot.changed");
		}

		if (accountState != null) {
			if (accountState.equals("Invalid")) {

				user.setValid(false);
				
				user.setCompanyId(Long.parseLong(companyId));
			} else if (accountState.equals("Locked")) {

				user.setLocked(true);
				
				user.setCompanyId(Long.parseLong(companyId));
				user.setLockedDate(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
			} else {
				// Added by seshu.
				user.setLocked(false);
				user.setLockedDate(null);
				// Added by seshu.
				user.setCompanyId(Long.parseLong(companyId));
				user.setUpdatedate(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
			}

			userEntityUpdateRequired = true;
		}

		if (userRole != null) {
			user.setRolesId(Long.parseLong(role_Id));
			userEntityUpdateRequired = true;
		}
		try {
			if (userEntityUpdateRequired) {

				user.setUpdatedby(sessionInfo.getUserId().toString());

				user.setUpdatedate(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));

				userDao.update(user);

				UserMangementHistory userMangementHistory = new UserMangementHistory();

				userMangementHistory.setAuditaction(auditActionUpdate);

				userMangementHistory.setUpdatedby(Long.parseLong(user
						.getUpdatedby()));
				userMangementHistory.setUpdatedate((Timestamp) user
						.getUpdatedate());
				userMangementHistory.setUser_id(user.getId());

				managementUserHistoryDao.saveOrUpdate(userMangementHistory);

			}

			Companiesuser companiesuser = null;
			List<Companiesuser> companiesusersList = new ArrayList<Companiesuser>();

			if ((user.getRole().getRoletype().getRoletypename()
					.equals(BizConstants.ROLE_TYPE_CUSTOMER) || user.getRole()
					.getRoletype().getRoletypename()
					.equals(BizConstants.ROLE_TYPE_INSTALLER))
					&& (group_ids != null && group_ids.size() > 0)
					&& (prev_group_ids != null && prev_group_ids.size() > 0)) {

				String hqlQuery = "delete from Companiesuser where userId=:user_id";
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("user_id", user.getId());

				Query query = companiesUserDao.executeHQLQuery(hqlQuery,
						parameter);
				query.executeUpdate();

				for (Long group_id : group_ids) {
					companiesuser = new Companiesuser();
					companiesuser.setCompanyId(Long.parseLong(companyId));
					companiesuser.setGroupId(group_id);
					companiesuser.setUserId(user.getId());
					companiesuser.setUpdatedate((Timestamp) user
							.getUpdatedate());
					companiesuser.setUpdatedby(user.getUpdatedby());
					companiesuser.setCreationdate((Timestamp) user
							.getCreationdate());

					companiesusersList.add(companiesuser);
				}

				companiesUserDao.batchSaveOrUpdate(companiesusersList);

				Set<Long> uniqueListForGroupDetails = new TreeSet<Long>();

				uniqueListForGroupDetails.addAll(group_ids);

				uniqueListForGroupDetails.addAll(prev_group_ids);

				Map<Long, String> groupDetailsMap = userManagementDAO
						.getGroupDetailsForHistory(uniqueListForGroupDetails);

				String updatedLog = BizConstants.EMPTY_STRING;

				if (groupDetailsMap != null && !groupDetailsMap.isEmpty()) {

					updatedLog = getUpdatedLogString(group_ids, prev_group_ids,
							groupDetailsMap);

				}

				if (StringUtils.isNotBlank(updatedLog)) {
					UserMangementHistory userMangementHistory = new UserMangementHistory();

					userMangementHistory.setAuditaction(updatedLog);

					userMangementHistory.setUpdatedby(Long.parseLong(user
							.getUpdatedby()));
					userMangementHistory.setUpdatedate((Timestamp) user
							.getUpdatedate());
					userMangementHistory.setUser_id(user.getId());

					managementUserHistoryDao.saveOrUpdate(userMangementHistory);
				}
			}
			success = true;
		} catch (Exception exp) {
			logger.error("Error occured while updating user, ID  " + userId,
					exp);
		}

		return CommonUtil.convertFromEntityToJsonStr(success);
	}

	private String getUpdatedLogString(Set<Long> group_ids,
			Set<Long> prev_group_ids, Map<Long, String> groupDetailsMap) {

		StringBuilder updatedStringForRemoval = new StringBuilder("");

		StringBuilder completeString = new StringBuilder("");

		boolean removed = false;
		for (Long grpId : prev_group_ids) {
			if (!group_ids.contains(grpId)) {

				String[] groupAndLevelName = groupDetailsMap.get(grpId).split(
						"\\^");

				if (removed)
					updatedStringForRemoval.append(",")
							.append(groupAndLevelName[0]).append(" ")
							.append((groupAndLevelName[1]));
				else {
					updatedStringForRemoval.append(groupAndLevelName[0])
							.append(" ").append((groupAndLevelName[1]));
					removed = true;
				}
			}
		}
		if (removed)
			updatedStringForRemoval.append(" has been removed");

		StringBuilder updatedStringForAddition = new StringBuilder("");

		boolean added = false;

		boolean addString = false;

		for (Long grpId : group_ids) {
			if (!prev_group_ids.contains(grpId)) {

				String[] groupAndLevelName = groupDetailsMap.get(grpId).split(
						"\\^");

				if (added)
					updatedStringForAddition.append(",")
							.append(groupAndLevelName[0]).append(" ")
							.append((groupAndLevelName[1]));
				else {
					updatedStringForAddition.append(groupAndLevelName[0])
							.append(" ").append((groupAndLevelName[1]));
					added = true;
				}
			}
		}
		if (added)
			updatedStringForAddition.append(" has been added");

		if (StringUtils.isNotBlank(updatedStringForAddition.toString())) {
			completeString.append(updatedStringForAddition);
			addString = true;
		}

		if (StringUtils.isNotBlank(updatedStringForRemoval.toString())) {
			if (addString) {
				completeString.append(" AND ").append(updatedStringForRemoval);
			} else {
				completeString.append(updatedStringForRemoval);
			}

		}

		return completeString.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getviewLog(java.lang.Long)
	 */
	@Override
	@Transactional
	public String getviewLog(Long user_id) throws JsonProcessingException {

		String userListJson = BizConstants.EMPTY_STRING;

		List<UsersViewLogVO> userList = userManagementDAO.getviewLog(user_id);

		if (userList != null && !userList.isEmpty() && userList.size() > 0) {

			userListJson = CommonUtil.convertFromEntityToJsonStr(userList);
		} else {

			userListJson = BizConstants.NO_RECORDS_FOUND;
		}
		return userListJson;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#resetPassword(java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional
	public User resetPassword(String loginId, String encryptPassword,
			Long userIdSession) throws HibernateException {

		User user = userManagementDAO.resetPassword(loginId, encryptPassword,
				userIdSession);

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#addUserMangementHistory(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	@Transactional
	public boolean addUserMangementHistory(Long userIdSession, Long userId)
			throws HibernateException {

		boolean insertUserMangementHistory = false;

		insertUserMangementHistory = userManagementDAO.addUserMangementHistory(
				userIdSession, userId);

		return insertUserMangementHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getCompanyIdByLoginId(java.lang.String)
	 */
	@Override
	@Transactional
	public String getCompanyIdByLoginId(String loginId)
			throws HibernateException {

		String companyID = null;

		companyID = userManagementDAO.getCompanyId(loginId);

		return companyID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#downloadResetUserDetails(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String downloadResetUserDetails(String userFullName,
			String newPassword) {

		String fileName = BizConstants.EMPTY_STRING;

		List<DownloadResetPasswordVO> userdatalist = new ArrayList<DownloadResetPasswordVO>();

		DownloadResetPasswordVO userdetails = new DownloadResetPasswordVO();
		// added by seshu.
		if (userFullName != null) {
			userdetails.setUserName("=\"" + userFullName + "\"");
			userdetails.setPassword(newPassword);
		}

		userdatalist.add(userdetails);

		ReportGenerator<DownloadResetPasswordVO> report = new CSVReportGenerator<DownloadResetPasswordVO>();

		CSVReportMetadata metadata = new CSVReportMetadata(
				DownloadResetPasswordVO.class, "", "User-New-Password");

		List<DataTableHeader> tableheader = new ArrayList<DataTableHeader>();

		DataTableHeader header = new DataTableHeader();
		header.setColumnName("userName");
		header.setDisplayName("User Name");
		tableheader.add(header);

		header = new DataTableHeader();
		header.setColumnName("password");
		header.setDisplayName("Password");
		tableheader.add(header);

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		metadata.setHeadingTextProperties(headerTextList);

		metadata.setDataTableHeading(tableheader);

		// Writes the excel metadata and dataList received in the csv
		try {
			fileName = report.writeTabularReport(metadata, userdatalist);
		} catch (Exception e) {
			logger.error("Error occurred while writing data to Excel", e);
		}

		return fileName;
	}

	@Override
	public User getUser(String loginId) {

		List<User> users = userDao.findAllByProperty("loginid", loginId);

		if (users.size() > 0) {

			return users.get(0);

		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#retrieveUserDetails(java.lang.Long)
	 */
	@Override
	public UserDetailsVO retrieveUserDetails(Long userId)
			throws HibernateException {
		return userManagementDAO.retrieveUserDetails(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getCompanyList()
	 */

	@Override
	public String getCompanyList() throws JsonProcessingException {

		List<Company> companyList = companyDao.findAll();

		String companyListJson = BizConstants.EMPTY_STRING;

		List<CompanyVO> companyVOs = new ArrayList<CompanyVO>();

		CompanyVO companyVO = null;

		if (companyList != null && !companyList.isEmpty()
				&& companyList.size() > 0) {

			for (Company company : companyList) {

				companyVO = new CompanyVO();

				companyVO.setCompanyid(company.getId());
				companyVO.setCompanyname(company.getName());

				companyVOs.add(companyVO);
			}

			companyListJson = CommonUtil.convertFromEntityToJsonStr(companyVOs);
		}
		return companyListJson;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getUserListUnderCompany(java.lang.Long,
	 * java.lang.Long)
	 */
	// Modified by seshu.
	@Override
	public String getUserListUnderCompany(Long companyId, Long userId,
			String loginId) throws JsonProcessingException {

		List<UserUnderCompanyVO> userList = null;

		String userListJson = BizConstants.EMPTY_STRING;

		userList = userManagementDAO.getUserListUnderCompany(companyId, userId,
				loginId);

		if (userList != null && !userList.isEmpty() && userList.size() > 0) {

			userListJson = CommonUtil.convertFromEntityToJsonStr(userList);
		} else {
			// Added by seshu.
			userListJson = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
		}
		return userListJson;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.usermanagement.service.
	 * ManageUserManagementService#getMultipleCompanyUserGroup(java.util.List,
	 * boolean)
	 */
	@Override
	public List<GroupCompanyVO> getMultipleCompanyUserGroup(
			List<Long> companyIds, boolean updateUser) {

		List<GroupUserVO> completeGroupHierarchy = null;

		List<GroupCompanyVO> groupVoCompanyWise = new ArrayList<GroupCompanyVO>();

		for (Long companyId : companyIds) {

			GroupCompanyVO groupCompanyVO = new GroupCompanyVO();

			List<Group> groupHierarchy = userManagementDAO.getGroupHierarchy(
					companyId.toString(), true);

			if (!groupHierarchy.isEmpty() && groupHierarchy.size() > 0) {

				completeGroupHierarchy = new ArrayList<GroupUserVO>();
				Set<Long> listOfGroups = new TreeSet<Long>();

				String companyName = BizConstants.EMPTY_STRING;
				Long companyID = null;

				for (Group grp : groupHierarchy) {

					if (!listOfGroups.contains(grp.getId())) {
						GroupUserVO groupUserVO = populateGroupUserVOForMultipleCompanies(
								grp, listOfGroups);

						companyName = groupUserVO.getCompanyName();
						companyID = groupUserVO.getCompanyId();

						listOfGroups.add(grp.getId());

						completeGroupHierarchy
								.add(getAllSubGroupsForMultipleCompanies(grp,
										groupUserVO, listOfGroups));

					}
				}

				groupCompanyVO.setCompanyID(companyID);
				// Modified by seshu.
				groupCompanyVO.setText(companyName);
				groupCompanyVO.setChildren(completeGroupHierarchy);

				groupVoCompanyWise.add(groupCompanyVO);

			}

		}

		return groupVoCompanyWise;

	}

	private GroupUserVO populateGroupUserVOForMultipleCompanies(Group group,
			Set<Long> listOfGroups) {

		GroupUserVO groupUserVO = new GroupUserVO();

		Map<String, Object> attrMap = new HashMap<String, Object>();

		Map<String, Object> stateMap = new HashMap<String, Object>();

		groupUserVO.setGroupId(group.getId());
		groupUserVO.setGroupName(group.getName());
		groupUserVO.setCompanyId(group.getCompany().getId());
		groupUserVO.setCompanyName(group.getCompany().getName());
		groupUserVO.setGroupCategory(group.getGroupcategory()
				.getGroupcategoryname());
		groupUserVO.setGroupTypeLevelID(group.getGroupLevel().getId());
		groupUserVO.setGroupTypeLevelName(group.getGroupLevel()
				.getTypeLevelName());

		groupUserVO.setSvgpath(group.getSvgPath());
		groupUserVO.setGroupCriteria(group.getGroupscriteria().getId());
		groupUserVO.setLevel(group.getLevel());
		groupUserVO.setText(group.getName());

		stateMap.put("opened", true);

		boolean enable = false;

		if (!enable) {
			groupUserVO.setEnable(false);
		}

		attrMap.put("dataId", group.getId());
		attrMap.put("svgPath", group.getSvgPath());
		groupUserVO.setLi_attr(attrMap);

		groupUserVO.setState(stateMap);

		return groupUserVO;

	}

	private GroupUserVO getAllSubGroupsForMultipleCompanies(Group vo,
			GroupUserVO parent, Set<Long> listOfGroups) {

		List<GroupUserVO> sub = new ArrayList<GroupUserVO>();

		for (Group subGroup : vo.getGroups()) {

			GroupUserVO subgrp = populateGroupUserVOForMultipleCompanies(
					subGroup, listOfGroups);

			listOfGroups.add(subgrp.getGroupId());
			sub.add(subgrp);

			getAllSubGroupsForMultipleCompanies(subGroup, subgrp, listOfGroups);

		}
		if (!sub.isEmpty()) {

			Collections.sort(sub, new GroupUserComparator());

			parent.setChildren(sub);
		}

		return parent;
	}

	// Added by seshu.
	@Override
	public String getTotalRoleList() throws JsonProcessingException {
		List<Role> roleList = roleDao.findAll();

		String roleListJson = BizConstants.EMPTY_STRING;

		List<TotalRoleVO> tottalroleVOs = new ArrayList<TotalRoleVO>();

		TotalRoleVO roleVO = null;

		if (roleList != null && !roleList.isEmpty() && roleList.size() > 0) {

			for (Role role : roleList) {

				roleVO = new TotalRoleVO();

				roleVO.setRoleid(role.getId());
				roleVO.setRolename(role.getName());

				tottalroleVOs.add(roleVO);
			}

			roleListJson = CommonUtil.convertFromEntityToJsonStr(tottalroleVOs);

		} else {
			roleListJson = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
		}
		return roleListJson;
	}

	// Added by seshu.
	@Override
	@Transactional
	public String getAllUserIdListFull(String companyId)
			throws JsonProcessingException {
		List<UsersVO> userList = new ArrayList<UsersVO>();

		String userListJson = BizConstants.EMPTY_STRING;

		userList = userManagementDAO.getAllUserIdListFull(companyId);

		if (userList != null && userList.size() > 0) {

			userListJson = CommonUtil.convertFromEntityToJsonStr(userList);
		} else {
			userListJson = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
		}
		return userListJson;
	}

	// Added by seshu(to keep backup).
	@Override
	@Transactional
	public boolean edditRole(Role role, List<Long> functional_id, Long roleId) {

		boolean editRoleStatus = false;

		Rolefunctionalgrp rolefunctionalgrp = null;
		List<Rolefunctionalgrp> rfg = new ArrayList<Rolefunctionalgrp>();
		try {

			if (role != null) {

				roleDao.saveOrUpdate(role);
				editRoleStatus = true;

				if (editRoleStatus) {
					userManagementDAO.addRoleHistory(role);

				}

			}
		}

		catch (HibernateException hibernateException) {
			logger.error("" + hibernateException);
		}

		if (editRoleStatus) {

			userManagementDAO.deleteFunctionalIds(roleId);

			if (functional_id != null) {

				for (Long functionalId : functional_id) {

					rolefunctionalgrp = new Rolefunctionalgrp();

					rolefunctionalgrp.setFuncgroupids(functionalId.intValue());

					rolefunctionalgrp.setRoleid(roleId.intValue());
					rolefunctionalgrp.setCompanyid(role.getCompany_id());

					Date date = new Date();

					rolefunctionalgrp.setCreationdate(new Timestamp(date
							.getTime()));

					rfg.add(rolefunctionalgrp);

				}
			}

		}
		if (rfg != null) {
			rolefunctionalgrpDao.batchSaveOrUpdate(rfg);
			editRoleStatus = true;
		}
		return editRoleStatus;

	}

	// added by seshu.
	@Override
	@Transactional
	public List<GroupUserVO> getAssignedGroupinfo(SessionInfo sessionInfo,
			boolean update, Long userIdReq) {

		Map<String, Object> properties = new HashMap<>();

		Long userId = sessionInfo.getUserId();

		Long companyId = sessionInfo.getCompanyId();

		List<GroupUserVO> completeGroupHierarchy = null;

		List<Long> groupIds = new ArrayList<Long>();

		List<Long> groupIdsReqUser = new ArrayList<Long>();

		List<Group> groupHierarchy = new ArrayList<Group>();

		boolean panasonicUser = false;

		if (update) {

			if (!(1 == (int) sessionInfo.getRoleTypeMap().keySet().toArray()[0])) {
				// Customer
				if (userId != null) {
					List<User> userObjects = new ArrayList<User>();

					User user = new User();
					user.setId(userId);
					userObjects.add(user);

					User userReq = new User();
					userReq.setId(userIdReq);
					userObjects.add(userReq);
					properties.put("user", userObjects);
				}

				if (companyId != null) {
					Company company = new Company();
					company.setId(companyId);
					properties.put("company", company);
				}

				List<Companiesuser> companiesUsers = companyUserService
						.findAllByProperties(properties);

				for (Companiesuser companiesUser : companiesUsers) {
					if (companiesUser.getGroup() != null
							&& companiesUser.getUserId() == userId) {
						groupIds.add(companiesUser.getGroup().getId());
					}
					if (companiesUser.getGroup() != null
							&& companiesUser.getUserId() == userIdReq)
						groupIdsReqUser.add(companiesUser.getGroup().getId());
				}
				groupHierarchy = userManagementDAO.getGroupHierarchy(
						companyId.toString(), true);
			} else {
				groupHierarchy = userManagementDAO.getGroupHierarchy();
				if (userIdReq != null) {
					User userReq = new User();
					userReq.setId(userIdReq);
					properties.put("user", userReq);

					List<Companiesuser> companiesUsers = companyUserService
							.findAllByProperties(properties);

					for (Companiesuser companiesUser : companiesUsers) {
						groupIdsReqUser.add(companiesUser.getGroup().getId());
					}

				}
				panasonicUser = true;
			}
		} else {
			if (1 == (int) sessionInfo.getRoleTypeMap().keySet().toArray()[0]) {
				groupHierarchy = userManagementDAO.getGroupHierarchy();
				panasonicUser = true;
			} else {
				if (userId != null) {
					User user = new User();
					user.setId(userId);
					properties.put("user", user);
				}

				if (companyId != null) {
					Company company = new Company();
					company.setId(companyId);
					properties.put("company", company);
				}

				List<Companiesuser> companiesUsers = companyUserService
						.findAllByProperties(properties);

				for (Companiesuser companiesUser : companiesUsers) {
					if (companiesUser.getGroup() != null) {
						groupIds.add(companiesUser.getGroup().getId());
					}
				}
			}
		}
		if (groupIds.size() > 0 && !update) {

			String groupIdsSQL = CommonUtil.convertCollectionToString(groupIds);

			// fetches all groups with their children as eager
			// collection in the case of User Role Customer

			groupHierarchy = userManagementDAO.getGroupHierarchy(groupIdsSQL,
					false);

		}

		completeGroupHierarchy = new ArrayList<GroupUserVO>();
		Set<Long> listOfGroups = new TreeSet<Long>();

		for (Group grp : groupHierarchy) {

			if (!listOfGroups.contains(grp.getId())) {
				GroupUserVO groupUserVO = populateGroupUserVO(grp, groupIds,
						groupIdsReqUser, panasonicUser, listOfGroups);

				listOfGroups.add(grp.getId());

				if (panasonicUser) {
					if (grp.getGroup() == null) {
						completeGroupHierarchy.add(getAllSubGroups(grp,
								groupUserVO, groupIds, groupIdsReqUser,
								panasonicUser, listOfGroups));
					}
				} else {
					completeGroupHierarchy.add(getAllSubGroups(grp,
							groupUserVO, groupIds, groupIdsReqUser,
							panasonicUser, listOfGroups));
				}
			}
			// completeGroupHierarchy.add(groupUserVO);
		}

		return completeGroupHierarchy;
	}

	// Added by seshu.
	@Override
	@Transactional
	public List<GroupUserVO> getCustomerGroups(SessionInfo sessionInfo,
			boolean update, Long userIdReq, Long companyID) {

		Map<String, Object> properties = new HashMap<>();

		Long userId = sessionInfo.getUserId();

		User user1 = userDao.findByID(userId);

		Long companyId = null;

		if (user1.getRole().getRoletype().getRoletypename()
				.equals(BizConstants.ROLE_TYPE_CUSTOMER)) {
			companyId = companyID;
		} else {
			companyId = sessionInfo.getCompanyId();
		}

		List<GroupUserVO> completeGroupHierarchy = null;

		List<Long> groupIds = new ArrayList<Long>();

		List<Long> groupIdsReqUser = new ArrayList<Long>();

		List<Group> groupHierarchy = new ArrayList<Group>();

		boolean panasonicUser = false;

		if (update) {

				// Added by seshu. (Modified by seshu removed loop to fix assigned issue)
				groupHierarchy = userManagementDAO.getGroupHierarchy(
						companyId.toString(), true);
				if (userIdReq != null) {
					User userReq = new User();
					userReq.setId(userIdReq);
					properties.put("user", userReq);

					List<Companiesuser> companiesUsers = companyUserService
							.findAllByProperties(properties);

					for (Companiesuser companiesUser : companiesUsers) {
						groupIdsReqUser.add(companiesUser.getGroup().getId());
					}

				}
				panasonicUser = true;
			
		} else {
			if ((Long.parseLong(bizProperties
					.getProperty("user.management.companyid")) == companyId)) {
				groupHierarchy = userManagementDAO.getGroupHierarchy();
				panasonicUser = true;
			} else {
				if (userId != null) {
					User user = new User();
					user.setId(userId);
					properties.put("user", user);
				}

				if (companyId != null) {
					Company company = new Company();
					company.setId(companyId);
					properties.put("company", company);
				}

				List<Companiesuser> companiesUsers = companyUserService
						.findAllByProperties(properties);

				for (Companiesuser companiesUser : companiesUsers) {
					if (companiesUser.getGroup() != null) {
						groupIds.add(companiesUser.getGroup().getId());
					}
				}
			}
		}
		if (groupIds.size() > 0 && !update) {

			String groupIdsSQL = CommonUtil.convertCollectionToString(groupIds);

			// fetches all groups with their children as eager
			// collection in the case of User Role Customer

			groupHierarchy = userManagementDAO.getGroupHierarchy(groupIdsSQL,
					false);

		}

		completeGroupHierarchy = new ArrayList<GroupUserVO>();
		Set<Long> listOfGroups = new TreeSet<Long>();

		for (Group grp : groupHierarchy) {

			if (!listOfGroups.contains(grp.getId())) {
				GroupUserVO groupUserVO = populateGroupUserVO(grp, groupIds,
						groupIdsReqUser, panasonicUser, listOfGroups);

				listOfGroups.add(grp.getId());

				if (panasonicUser) {
					if (grp.getGroup() == null) {
						completeGroupHierarchy.add(getAllSubGroups(grp,
								groupUserVO, groupIds, groupIdsReqUser,
								panasonicUser, listOfGroups));
					}
				} else {
					completeGroupHierarchy.add(getAllSubGroups(grp,
							groupUserVO, groupIds, groupIdsReqUser,
							panasonicUser, listOfGroups));
				}
			}
			// completeGroupHierarchy.add(groupUserVO);
		}

		return completeGroupHierarchy;
	}

	// Added by seshu.(All changes)
	@Override
	@Transactional
	public List<GroupCompanyVO> getCustomerGroupsHierarchyByCompanyId(
			SessionInfo sessionInfo, boolean update, Long userIdReq,
			Long companyID) throws HibernateException, IllegalAccessException,
			InvocationTargetException, BusinessFailureException {

		List<GroupUserVO> groupVoList = getCustomerGroups(sessionInfo, update,
				userIdReq, companyID);

		Map<Long, List<GroupUserVO>> groupVoMapCompanyWise = new HashMap<Long, List<GroupUserVO>>();

		List<GroupCompanyVO> groupTreeByCompany = new ArrayList<>();

		Map<Long, String> companyInfo = new HashMap<>();

		for (GroupUserVO vo1 : groupVoList) {

			List<GroupUserVO> list = new ArrayList<GroupUserVO>();

			int innerCount = 0;

			if (!groupVoMapCompanyWise.containsKey(vo1.getCompanyId())) {

				for (GroupUserVO vo2 : groupVoList) {

					if (vo1.getCompanyId() == vo2.getCompanyId()) {

						if (innerCount == 0) {
							list.add(vo2);

							if (!companyInfo.containsKey(vo1.getCompanyId())) {
								companyInfo.put(vo1.getCompanyId(),
										vo1.getCompanyName());
							}
							groupVoMapCompanyWise.put(vo1.getCompanyId(), list);
						} else {
							if (!companyInfo.containsKey(vo1.getCompanyId())) {
								companyInfo.put(vo1.getCompanyId(),
										vo1.getCompanyName());
							}
							groupVoMapCompanyWise.get(vo1.getCompanyId()).add(
									vo2);
						}

						innerCount++;

					}

				}

			}

		}

		GroupCompanyVO groupsByCompany = null;
		// Modified by seshu.
		for (long companyID1 : companyInfo.keySet()) {
			groupsByCompany = new GroupCompanyVO();
			groupsByCompany.setCompanyID(companyID1);
			// added by seshu.(to display group tree from company level)
			groupsByCompany.setText(companyInfo.get(companyID1));
			groupsByCompany.setChildren(groupVoMapCompanyWise.get(companyID1));
			groupTreeByCompany.add(groupsByCompany);
		}

		return groupTreeByCompany;
	}

}

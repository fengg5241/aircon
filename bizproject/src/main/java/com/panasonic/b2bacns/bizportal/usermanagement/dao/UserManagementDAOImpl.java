package com.panasonic.b2bacns.bizportal.usermanagement.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.Role;
import com.panasonic.b2bacns.bizportal.persistence.RoleHistory;
import com.panasonic.b2bacns.bizportal.persistence.Rolefunctionalgrp;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.persistence.UserMangementHistory;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.FetchRoleListVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.FunctionalGroupVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.RoleTypeVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserDetailsVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserUnderCompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UsersVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UsersViewLogVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.viewRoleLogVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

@Service
public class UserManagementDAOImpl implements UserManagementDAO {

	private static final Logger logger = Logger
			.getLogger(UserManagementDAOImpl.class);

	private static final String HQL_ALL_GROUP_BY_USER = "select distinct grp from Group as grp where grp.id in (%s) and cast(grp.isUnitExists as integer) = 1 ";

	private static final String HQL_ALL_GROUP_ALL_COMPANY = "select distinct grp from Group as grp left join fetch grp.groups where cast(grp.isUnitExists as integer) = 1 and grp.level=1 order by  grp.company";

	private static final String HQL_ALL_GROUP_COMPANY = "select distinct grp from Group as grp where cast(grp.isUnitExists as integer) = 1 and grp.company.id = %s and grp.level=1";

	private static final String HQL_Group_Details_For_History = "select grp.id, grp.name, grp.groupcategory.groupcategoryname from Group as grp where grp.id in (%s)";

	@Autowired
	private SQLDAO sqlDao;

	private GenericDAO<RoleHistory> roleHistoryDao;

	@Autowired
	public void setDao(GenericDAO<RoleHistory> daoToSet) {
		roleHistoryDao = daoToSet;
		roleHistoryDao.setClazz(RoleHistory.class);
	}

	private GenericDAO<User> userDao;

	@Autowired
	public void setUserDao(GenericDAO<User> daoToSet) {
		userDao = daoToSet;
		userDao.setClazz(User.class);
	}

	private GenericDAO<Rolefunctionalgrp> rolefunctionalgrpDao;

	@Autowired
	public void setDao1(GenericDAO<Rolefunctionalgrp> daoToSet) {
		rolefunctionalgrpDao = daoToSet;
		rolefunctionalgrpDao.setClazz(Rolefunctionalgrp.class);
	}

	private GenericDAO<UserMangementHistory> userMangementHistoryDao;

	@Autowired
	public void setUserMangementHistoryDao(
			GenericDAO<UserMangementHistory> daoToSet) {
		userMangementHistoryDao = daoToSet;
		userMangementHistoryDao.setClazz(UserMangementHistory.class);
	}

	private static final String USERID = "id";
	private static final String COMPANY_ID = "companyId";
	/*
	 * private static final String GROUP_ID = "groupId"; private static final
	 * String SITEGROUP_ID = "siteGroupId"; private static final String
	 * SITEGROUP_NAME = "siteGroupName"; private static final String
	 * LOGICALGROUP_ID = "logicalGroupId"; private static final String
	 * LOGICALGROUP_NAME = "logicalGroupName"; private static final String
	 * SITE_ID = "siteId";
	 */
	private static final String ROLELIST_ID = "rolelistId";
	private static final String ROLELIST_NAME = "rolelistName";
	private static final String ROLETYPE_ID = "roletypeId";
	private static final String ROLETYPE_NAME = "roletypename";
	private static final String ROLE_TYPE_ID = "roletypeid";
	private static final String FUNCGROUP_ID = "functionalgroupid";
	private static final String FUNCGROUP_NAME = "functionalgroupname";
	/*private static final String PERMISSION_ID = "permissionid";
	private static final String PERMISSION_NAME = "permissionname";*/
	private static final String CREATED_BY = "createdby";

	// For view Role Log :

	private static final String VIEW_ROLE_Log_ACTION = "action";
	private static final String ROLE_NAME = "rolename";
	private static final String STRING_AGG = "string_agg";
	private static final String LOGIN_ID = "loginid";
	private static final String UPDATE_DATE = "updateddate";
	private static final String ROLEID = "role_id";

	// For User List

	private static final String COMAPNY_NAME = "companyname";
	private static final String REGISTERED_DATE = "creationdate";
	private static final String REGISTERED_BY = "createdbyloginid";
	private static final String IS_VALID = "is_valid";
	private static final String IS_LOCKED = "is_locked";
	
	private static final String ISEMAILVERIFIED = "isemailverified"; private
	static final String CONFIRMATIONTOKEN = "confirmationtoken"; private
	static final String EMAIL = "email";
	 

	// For user Audit

	private static final String DATE_TIME = "updatedate";
	//private static final String UPDATE_BY = "updatedby";
	private static final String UPDATE_DATA = "auditaction";
	private static final String USER_ID = "user_id";
	//private static final String IsDel = "isdel";
	private static final String CREATEDBY = "createdby";

	public static final StringBuilder SQL_GET_USER_ID_FROM_USERS = new StringBuilder(
			"select max(id) as id from users");

	public static final StringBuilder SQL_GET_SITEGROUP_LIST_BY_COMPANYID = new StringBuilder(
			"select uniqueid as siteGroupId ,g.name as sitegroupname from companiesusers cu join groups g on cu.group_id = g.id where cast(cu.company_id as varchar)  = :companyId Order by uniqueid");

	public static final StringBuilder SQL_GET_SITEGROUP_LIST_BY_GROUPID = new StringBuilder(
			"select uniqueid as sitegroupid , name as sitegroupname from groups where id in ('%s') Order by uniqueid");

	public static final StringBuilder SQL_GET_LOGICALGROUP_BY_SITEID = new StringBuilder(
			"Select id as logicalGroupId, name as logicalGroupName from groups where group_level_id = 3 and cast(id as varchar(500)) in ('%s') order by id");

	public static final StringBuilder SQL_GET_CONTROLGROUP_BY_LOGICALID = new StringBuilder(
			"select id as controlGroupId , name as controlGroupName from groups where group_level_id = 4 and cast(id as varchar(500)) in ('%s') order by id");

	public static final StringBuilder SQL_GET_FETCHROLE_LIST_BY_COMPANYID = new StringBuilder(
			"Select id as rolelistId , name as rolelistName  from roles where company_id in (:companyId) and roletype_id >=:roletypeId and  (isdel = :isdel or isdel is null) order by id ");

	public static final StringBuilder SQL_GET_ROLETYPE_LIST_BY_USERID = new StringBuilder(
			"Select id as roletypeId , roletypename from roletype where id >= :roletypeid order by id");

	public static final StringBuilder SQL_GET_FUNCTIONALGROUPLIST_BY_ROLETYPEID = new StringBuilder(
			"Select functional_groupid as functionalgroupid, functional_groupname as functionalgroupname from functionalgroup where roletypeid = :roletypeid order by functionalgroupid");

	public static final StringBuilder SQL_GET_PERMISSIONDETAILS_BY_COMPANYID = new StringBuilder(
			"Select id as permissionid, name as permissionname from permissions where company_id =cast(:companyId as integer) order by id");

	public static final StringBuilder SQL_GET_VIEWROLE_LOG = new StringBuilder(
			"select action, roletypename, fn.rolelistname as rolename, string_agg(fn.functionalgroupname,','), u.loginid,rh.updateddate ")
			.append("from usp_getrolessbyuserid_roletype(:id) fn ")
			.append("inner join rolehistory rh on rh.role_id = fn.rolelistid ")
			.append("inner join users u on rh.updateby = u.id   ")
			.append("group by action,roletypename,rolename ,u.loginid,rh.updateddate");

	public static final StringBuilder SQL_GET_USERLIST = new StringBuilder(
			"select loginid from users WHERE companyid =:companyId and is_valid=true and is_locked= false");
	// Modified by seshu.
	public static final StringBuilder SQL_GET_USERLIST_FULL = new StringBuilder(
			"Select distinct u.id ,c.name as companyname,u.creationdate,ul.loginid as createdbyloginid,u.is_valid,u.is_locked,u.loginid ,r.name as rolename, u.roles_id as role_id")
			.append(" from companies c inner join users u on c.id = u.companyid inner join roles r on r.id = u.roles_id left outer join users ul on ul.id  = cast(u.createdby as bigint) where c.id = :companyId ");

	// Modified by seshu.
	public static final StringBuilder SQL_GET_USERLIST_FULL_BY_USERID = new StringBuilder(
			"Select distinct u.id ,c.name as companyname,u.creationdate,ul.loginid as createdbyloginid,u.is_valid,u.is_locked,u.loginid ,r.name as rolename, u.roles_id as role_id, r.roletype_id as roletypeid,c.id as companyId ")
			.append("from usp_getusersbyuserid_roletype(:id) fn inner join users u on u.id =  fn.id inner join companies c on c.id = u.companyid inner join roles r on r.id = u.roles_id ")
			.append("left outer join users ul on ul.id  = cast(u.createdby as bigint) where u.is_valid = true");

	public static final StringBuilder SQL_UPDATE_USER_DETAIL_FOR_EMAIL = new StringBuilder(
			"update users set isemailverified = :isemailverified where confirmationtoken = :confirmationtoken and email = :email");

	public static final StringBuilder SQL_GET_VIEWLOG = new StringBuilder(
			"select umh_cr.auditaction,umh_cr.updatedate,u.loginid from users u Left outer join usermangementhistory umh_cr "
					+ " on (u.id = umh_cr.createdby or u.id = umh_cr.updatedby) Where umh_cr.user_id  =:user_id");

	public static final StringBuilder SQL_GET_COMPANY_ID = new StringBuilder(
			"select cu.company_id as companyId from users us join companiesusers cu on  us.id = cu.user_id and us.loginid = :loginid ");

	private static final String SQL_RETRIEVE_USER_DETAILS = "select u.roles_id as role_id,u.is_valid as is_valid, u.is_locked as is_locked, r.name as rolename,u.companyid as companyId,c.name as companyname from users u left join roles r on u.roles_id = r.id left join companies c on u.companyid = c.id where u.id = :user_id";

	public static final StringBuilder SQL_GET_FETCHROLE_LIST_BY_USERID = new StringBuilder(
			"select * from usp_getrolessbyuserid_roletype(:id)");

	private static final String SQL_CHECK_ROLE = "select id as role_id from roles where name = :rolename";

	private static final String SQL_CHECK_ROLE_ASSIGNED_STATUS = "select id from users where roles_id = :role_id";

	@Override
	public String generateUserId(String companyId) throws HibernateException {

		String sqlQuery = null;
		String userId = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(USERID, StandardBasicTypes.LONG);

		/*
		 * Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		 * 
		 * parameter.put(COMPANY_ID, companyId);
		 */
		sqlQuery = String.format(SQL_GET_USER_ID_FROM_USERS.toString());
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping);

		if (resultList != null && resultList.size() > 0) {

			userId = resultList.get(0).toString();

			Integer incrementedUserId = Integer.parseInt(userId == null ? "0"
					: userId) + 1;

			userId = incrementedUserId.toString();
		}
		return userId;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getSiteGroupListByCompanyId(java.lang.String)
	 */
	/*
	 * @Override public List<SiteGroupVO> getSiteGroupListByCompanyId(String
	 * companyId) throws HibernateException {
	 * 
	 * String sqlQuery = null;
	 * 
	 * LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String,
	 * Type>();
	 * 
	 * scalarMapping.put(SITEGROUP_ID, StandardBasicTypes.STRING);
	 * scalarMapping.put(SITEGROUP_NAME, StandardBasicTypes.STRING);
	 * 
	 * Map<String, Object> parameter = new HashMap<String, Object>();
	 * 
	 * parameter.put(COMPANY_ID, companyId);
	 * 
	 * sqlQuery = String
	 * .format(SQL_GET_SITEGROUP_LIST_BY_COMPANYID.toString());
	 * 
	 * List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
	 * parameter);
	 * 
	 * List<SiteGroupVO> siteGrouplist = null; Object[] rowData = null;
	 * 
	 * SiteGroupVO siteGroupVO = null;
	 * 
	 * if (!resultList.isEmpty()) {
	 * 
	 * Iterator<?> itr = resultList.iterator(); siteGrouplist = new
	 * ArrayList<SiteGroupVO>();
	 * 
	 * while (itr.hasNext()) { siteGroupVO = new SiteGroupVO(); rowData =
	 * (Object[]) itr.next();
	 * 
	 * siteGroupVO.setSiteId((String) rowData[0]);
	 * siteGroupVO.setSiteGroupName((String) rowData[1]);
	 * 
	 * siteGrouplist.add(siteGroupVO); }
	 * 
	 * return siteGrouplist; }
	 * 
	 * return null;
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getSiteGroupNames(java.lang.Long[])
	 */
	/*
	 * @Override public List<SiteGroupVO> getSiteGroupNames(Long[] id) throws
	 * HibernateException {
	 * 
	 * String sqlQuery = null;
	 * 
	 * StringBuilder groupId = new StringBuilder(); for (Long groupID : id) {
	 * groupId.append("'").append(groupID).append("'").append(","); }
	 * groupId.deleteCharAt(groupId.lastIndexOf(","));
	 * groupId.deleteCharAt(groupId.lastIndexOf("'")); groupId.deleteCharAt(0);
	 * 
	 * LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String,
	 * Type>();
	 * 
	 * scalarMapping.put(SITEGROUP_ID, StandardBasicTypes.STRING);
	 * scalarMapping.put(SITEGROUP_NAME, StandardBasicTypes.STRING);
	 * 
	 * Map<String, Object> parameter = new HashMap<String, Object>();
	 * 
	 * parameter.put(GROUP_ID, groupId);
	 * 
	 * sqlQuery = String.format(SQL_GET_SITEGROUP_LIST_BY_GROUPID.toString(),
	 * groupId);
	 * 
	 * List<?> resultList = sqlDao.executeSQLSelect(sqlQuery);
	 * 
	 * List<SiteGroupVO> siteGrouplist = new ArrayList<SiteGroupVO>();
	 * 
	 * Object[] rowData = null; SiteGroupVO siteGroupVO = null;
	 * 
	 * if (!resultList.isEmpty()) {
	 * 
	 * Iterator<?> itr = resultList.iterator();
	 * 
	 * while (itr.hasNext()) {
	 * 
	 * rowData = (Object[]) itr.next(); siteGroupVO = new SiteGroupVO();
	 * siteGroupVO.setSiteId((String) rowData[0]);
	 * siteGroupVO.setSiteGroupName((String) rowData[1]);
	 * 
	 * siteGrouplist.add(siteGroupVO); } return siteGrouplist; }
	 * 
	 * return null; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getLogicalGroup(java.lang.Long[])
	 */
	/*
	 * @Override public List<LogicalGroupVO> getLogicalGroup(Long[] siteId)
	 * throws HibernateException {
	 * 
	 * String sqlQuery = null;
	 * 
	 * List<Long> siteid = Arrays.asList(siteId); StringBuilder siteIdlist = new
	 * StringBuilder();
	 * 
	 * for (Long siteID : siteid) {
	 * siteIdlist.append("'").append(siteID).append("'").append(","); }
	 * 
	 * siteIdlist.deleteCharAt(siteIdlist.lastIndexOf(","));
	 * siteIdlist.delete(0, 1);
	 * siteIdlist.deleteCharAt(siteIdlist.lastIndexOf("'"));
	 * 
	 * List<LogicalGroupVO> logicalGroupList = new ArrayList<LogicalGroupVO>();
	 * LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String,
	 * Type>();
	 * 
	 * scalarMapping.put(LOGICALGROUP_ID, StandardBasicTypes.STRING);
	 * scalarMapping.put(LOGICALGROUP_NAME, StandardBasicTypes.STRING);
	 * 
	 * Map<String, Object> parameter = new HashMap<String, Object>();
	 * 
	 * parameter.put(SITE_ID, siteIdlist);
	 * 
	 * sqlQuery = String.format(SQL_GET_LOGICALGROUP_BY_SITEID.toString(),
	 * siteIdlist);
	 * 
	 * List<?> resultList = sqlDao.executeSQLSelect(sqlQuery); Object[] rowData
	 * = null; LogicalGroupVO logicalGroupVO = null;
	 * 
	 * if (!resultList.isEmpty()) {
	 * 
	 * Iterator<?> itr = resultList.iterator(); while (itr.hasNext()) {
	 * 
	 * rowData = (Object[]) itr.next(); logicalGroupVO = new LogicalGroupVO();
	 * 
	 * logicalGroupVO.setGroupId((long) Integer.parseInt(rowData[0]
	 * .toString())); logicalGroupVO.setGroupName((String) rowData[1]);
	 * 
	 * logicalGroupList.add(logicalGroupVO); }
	 * 
	 * } return logicalGroupList; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getControlGroupName(java.lang.Long[])
	 */
	/*
	 * @Override public List<ControlGroupVO> getControlGroupName(Long[]
	 * logicalId) {
	 * 
	 * String sqlQuery;
	 * 
	 * List<Long> logicalGroupIdList = Arrays.asList(logicalId);
	 * List<ControlGroupVO> listControlGroup = null; StringBuilder logicalIdList
	 * = new StringBuilder();
	 * 
	 * for (Long logId : logicalGroupIdList) {
	 * 
	 * logicalIdList.append("'").append(logId).append("'").append(","); }
	 * 
	 * logicalIdList.deleteCharAt(logicalIdList.lastIndexOf(","));
	 * logicalIdList.delete(0, 1);
	 * logicalIdList.deleteCharAt(logicalIdList.lastIndexOf("'"));
	 * 
	 * sqlQuery = String.format(SQL_GET_CONTROLGROUP_BY_LOGICALID.toString(),
	 * logicalIdList);
	 * 
	 * List<?> resultList = sqlDao.executeSQLSelect(sqlQuery);
	 * 
	 * Object[] rowData = null; ControlGroupVO controlGroupVO = null;
	 * 
	 * if (!resultList.isEmpty()) {
	 * 
	 * Iterator<?> itr = resultList.iterator(); listControlGroup = new
	 * ArrayList<ControlGroupVO>();
	 * 
	 * while (itr.hasNext()) {
	 * 
	 * rowData = (Object[]) itr.next(); controlGroupVO = new ControlGroupVO();
	 * 
	 * controlGroupVO.setControlGroupid((long) Integer
	 * .parseInt(rowData[0].toString()));
	 * controlGroupVO.setControlGroupName(rowData[1].toString());
	 * 
	 * listControlGroup.add(controlGroupVO); } } return listControlGroup; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getRoleList(java.lang.Long)
	 */
	@Override
	public List<FetchRoleListVO> getRoleList(Long userId)
			throws HibernateException {

		String sqlQuery = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(ROLELIST_ID, StandardBasicTypes.LONG);
		scalarMapping.put(ROLELIST_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(FUNCGROUP_ID, StandardBasicTypes.STRING);
		scalarMapping.put(FUNCGROUP_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(ROLE_TYPE_ID, StandardBasicTypes.LONG);
		scalarMapping.put(ROLETYPE_NAME, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new HashMap<String, Object>();

		// parameter.put(COMPANY_ID, companyIdList);
		// parameter.put(ROLETYPE_ID, roleTypeId);
		parameter.put(USERID, userId);

		sqlQuery = String.format(SQL_GET_FETCHROLE_LIST_BY_USERID.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		logger.debug("Executing sql query for getRoleList");

		List<FetchRoleListVO> fetchRolelist = null;
		Object[] rowData = null;

		FetchRoleListVO fetchRolelistVo = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			fetchRolelist = new ArrayList<FetchRoleListVO>();

			while (itr.hasNext()) {
				fetchRolelistVo = new FetchRoleListVO();
				rowData = (Object[]) itr.next();

				fetchRolelistVo.setRole_id((Long) rowData[0]);
				fetchRolelistVo.setRole_name((String) rowData[1]);
				fetchRolelistVo.setFunctionalgroupids((String) rowData[2]);
				fetchRolelistVo.setFunctionalgroupnames((String) rowData[3]);
				fetchRolelistVo.setRoletype_id((Long) rowData[4]);
				fetchRolelistVo.setRoletype_name((String) rowData[5]);

				fetchRolelist.add(fetchRolelistVo);
			}

		}

		return fetchRolelist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getRoleTypeName(java.lang.Integer)
	 */
	@Override
	public List<RoleTypeVO> getRoleTypeName(Integer roleTypeid)
			throws HibernateException {

		String sqlQuery = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(ROLETYPE_ID, StandardBasicTypes.STRING);
		scalarMapping.put(ROLETYPE_NAME, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put(ROLE_TYPE_ID, roleTypeid);

		sqlQuery = String.format(SQL_GET_ROLETYPE_LIST_BY_USERID.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		logger.debug("Executing sql query for getRoleType");

		List<RoleTypeVO> roleTypelist = null;
		Object[] rowData = null;

		RoleTypeVO roleTypeVo = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			roleTypelist = new ArrayList<RoleTypeVO>();

			while (itr.hasNext()) {
				roleTypeVo = new RoleTypeVO();
				rowData = (Object[]) itr.next();

				roleTypeVo.setRole_type_id(Integer
						.parseInt((String) rowData[0]));// setRole_id((int)
														// rowData[0]);
				roleTypeVo.setRole_type_name((String) rowData[1]);

				roleTypelist.add(roleTypeVo);
			}

			return roleTypelist;
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getFunctionalGroupName(java.lang.Integer)
	 */
	@Override
	public List<FunctionalGroupVO> getFunctionalGroupName(Integer role_type_id)
			throws HibernateException {

		String sqlQuery = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(FUNCGROUP_ID, StandardBasicTypes.INTEGER);
		scalarMapping.put(FUNCGROUP_NAME, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put(ROLE_TYPE_ID, role_type_id);

		sqlQuery = String.format(SQL_GET_FUNCTIONALGROUPLIST_BY_ROLETYPEID
				.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		logger.debug("Executing sql query for getFuncGroup");

		List<FunctionalGroupVO> funcGrplist = null;

		Object[] rowData = null;

		FunctionalGroupVO funcGrpVo = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			funcGrplist = new ArrayList<FunctionalGroupVO>();

			while (itr.hasNext()) {
				funcGrpVo = new FunctionalGroupVO();
				rowData = (Object[]) itr.next();

				funcGrpVo.setFunctional_id((Integer) rowData[0]);
				funcGrpVo.setFunctional_name((String) rowData[1]);

				funcGrplist.add(funcGrpVo);
			}

			return funcGrplist;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getPermissionDetails(java.lang.Long)
	 */
	/*
	 * @Override public List<PermissionsVO> getPermissionDetails(Long companyId)
	 * throws HibernateException {
	 * 
	 * String sqlQuery = null;
	 * 
	 * LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String,
	 * Type>();
	 * 
	 * scalarMapping.put(PERMISSION_ID, StandardBasicTypes.STRING);
	 * scalarMapping.put(PERMISSION_NAME, StandardBasicTypes.STRING);
	 * 
	 * Map<String, Object> parameter = new HashMap<String, Object>();
	 * 
	 * parameter.put(COMPANY_ID, companyId);
	 * 
	 * sqlQuery = String.format(SQL_GET_PERMISSIONDETAILS_BY_COMPANYID
	 * .toString());
	 * 
	 * List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
	 * parameter);
	 * 
	 * List<PermissionsVO> permissionslist = null; Object[] rowData = null;
	 * 
	 * PermissionsVO permissionslistVo = null;
	 * 
	 * if (!resultList.isEmpty()) {
	 * 
	 * Iterator<?> itr = resultList.iterator(); permissionslist = new
	 * ArrayList<PermissionsVO>();
	 * 
	 * while (itr.hasNext()) { permissionslistVo = new PermissionsVO(); rowData
	 * = (Object[]) itr.next();
	 * 
	 * permissionslistVo.setPermissions_id((int) Integer
	 * .parseInt(rowData[0].toString()));
	 * permissionslistVo.setPermissions_name((String) rowData[1]);
	 * 
	 * permissionslist.add(permissionslistVo); }
	 * 
	 * return permissionslist; }
	 * 
	 * return null; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * addRoleHistory(java.lang.Long,
	 * com.panasonic.b2bacns.bizportal.persistence.Role)
	 */

	@Override
	public void addRoleHistory(Long role_id, Role role) {

		String created = BizConstants.ROLEHISTORY_ACTION_CREATE;
		RoleHistory roleHistory = new RoleHistory();

		roleHistory.setRole_id(role_id.intValue());
		roleHistory.setAction(created);
		roleHistory.setCompanyId(role.getCompany_id());
		roleHistory.setUpdateBy(Integer.parseInt(role.getCreatedby()));

		Date date = new Date();

		roleHistory.setUpdatedDate(new Timestamp(date.getTime()));

		roleHistoryDao.create(roleHistory);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * addRoleHistory(com.panasonic.b2bacns.bizportal.persistence.Role)
	 */

	@Override
	public void addRoleHistory(Role role) {

		String edited = BizConstants.ROLEHISTORY_ACTION_EDIT;
		RoleHistory roleHistory = new RoleHistory();
		roleHistory.setRole_id(role.getId().intValue());
		roleHistory.setAction(edited);
		roleHistory.setCompanyId(role.getCompany_id());
		roleHistory.setUpdateBy(Integer.parseInt(role.getCreatedby()));
		Date date = new Date();

		roleHistory.setUpdatedDate(new Timestamp(date.getTime()));

		roleHistoryDao.create(roleHistory);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * addRoleHistoryDelete(java.lang.Long,
	 * com.panasonic.b2bacns.bizportal.persistence.Role)
	 */
	@Override
	public void addRoleHistoryDelete(Long loggedinUserId, Role role) {

		String edited = BizConstants.ROLEHISTORY_ACTION_DELETE;
		RoleHistory roleHistory = new RoleHistory();
		roleHistory.setRole_id(role.getId().intValue());
		roleHistory.setAction(edited);
		roleHistory.setCompanyId(role.getCompany_id());
		roleHistory.setUpdateBy(loggedinUserId);
		Date date = new Date();

		roleHistory.setUpdatedDate(new Timestamp(date.getTime()));

		roleHistoryDao.create(roleHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * viewRoleLog(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<viewRoleLogVO> viewRoleLog(String companyId, Long userId) {

		String sqlQuery = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(VIEW_ROLE_Log_ACTION, StandardBasicTypes.STRING);
		scalarMapping.put(ROLETYPE_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(ROLE_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(STRING_AGG, StandardBasicTypes.STRING);
		scalarMapping.put(LOGIN_ID, StandardBasicTypes.STRING);
		scalarMapping.put(UPDATE_DATE, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(USERID, userId);

		sqlQuery = String.format(SQL_GET_VIEWROLE_LOG.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		List<viewRoleLogVO> roleLoglist = null;
		Object[] rowData = null;

		viewRoleLogVO viewRoleVO = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			roleLoglist = new ArrayList<viewRoleLogVO>();

			while (itr.hasNext()) {
				viewRoleVO = new viewRoleLogVO();
				rowData = (Object[]) itr.next();

				viewRoleVO.setAction((String) rowData[0]);
				viewRoleVO.setRoleTypename((String) rowData[1]);
				viewRoleVO.setRoleName((String) rowData[2]);
				viewRoleVO.setString_agg((String) rowData[3]);
				viewRoleVO.setLogin_id((String) rowData[4]);
				viewRoleVO.setUpdatedate((String) rowData[5]);

				roleLoglist.add(viewRoleVO);
			}
			logger.debug("The full list of viewLog is: " + roleLoglist);

		}

		return roleLoglist;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getUserIdList(java.lang.String)
	 */
	/*
	 * @Override public List<UsersVO> getUserIdList(String companyId) {
	 * 
	 * String sqlQuery = null; LinkedHashMap<String, Type> scalarMapping = new
	 * LinkedHashMap<String, Type>();
	 * 
	 * scalarMapping.put(LOGIN_ID, StandardBasicTypes.STRING);
	 * 
	 * Map<String, Object> parameter = new LinkedHashMap<String, Object>();
	 * parameter.put(COMPANY_ID, Integer.parseInt(companyId));
	 * 
	 * sqlQuery = String.format(SQL_GET_USERLIST.toString());
	 * 
	 * @SuppressWarnings("unchecked") List<UsersVO> resultSet = (List<UsersVO>)
	 * sqlDao.executeSQLSelect( sqlQuery, scalarMapping, parameter);
	 * 
	 * return resultSet; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getUserIdListFull(java.lang.Long)
	 */
	@Override
	public List<UsersVO> getUserIdListFull(Long userId) {
		String sqlQuery = null;
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(USERID, StandardBasicTypes.STRING);
		scalarMapping.put(COMAPNY_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(REGISTERED_DATE, StandardBasicTypes.STRING);
		scalarMapping.put(REGISTERED_BY, StandardBasicTypes.STRING);
		scalarMapping.put(IS_VALID, StandardBasicTypes.BOOLEAN);
		scalarMapping.put(IS_LOCKED, StandardBasicTypes.BOOLEAN);
		scalarMapping.put(LOGIN_ID, StandardBasicTypes.STRING);
		scalarMapping.put(ROLE_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(ROLEID, StandardBasicTypes.LONG);
		// Added by sesha
		scalarMapping.put(ROLE_TYPE_ID, StandardBasicTypes.LONG);
		// Added by seshu.
		scalarMapping.put(COMPANY_ID, StandardBasicTypes.LONG);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(USERID, userId);

		sqlQuery = String.format(SQL_GET_USERLIST_FULL_BY_USERID.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		List<UsersVO> userslist = null;
		Object[] rowData = null;

		UsersVO usersVO = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			userslist = new ArrayList<UsersVO>();
			while (itr.hasNext()) {
				usersVO = new UsersVO();
				rowData = (Object[]) itr.next();

				if ((boolean) rowData[4]) {
					if (!(boolean) rowData[5]) {

						usersVO.setAccount_state("Valid");
					} else {
						usersVO.setAccount_state("Locked");
					}

				} else {
					usersVO.setAccount_state("Invalid");
				}

				usersVO.setId(Long.parseLong((String) rowData[0]));
				usersVO.setCompany_name((String) rowData[1]);
				usersVO.setRegistered_date((String) rowData[2]);
				usersVO.setRegistered_by((String) rowData[3]);
				usersVO.setLoginId((String) rowData[6]);
				usersVO.setRolename((String) rowData[7]);
				usersVO.setRoleid((Long) rowData[8]);
				usersVO.setRoletypeid((Long) rowData[9]);
				// Added by seshu.
				usersVO.setCompanyid((Long) rowData[10]);

				userslist.add(usersVO);
			}

			logger.debug("The full list of userList Simple is: " + userslist);
			return userslist;
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getGroupHierarchy(java.lang.String, boolean)
	 */
	@Override
	public List<Group> getGroupHierarchy(String groupOrCompId, boolean compID) {

		List<Group> groupHierarchy = new ArrayList<Group>();
		String hqlQueryCustomer = null;

		if (compID) {
			hqlQueryCustomer = String.format(HQL_ALL_GROUP_COMPANY,
					groupOrCompId);
		} else {
			hqlQueryCustomer = String.format(HQL_ALL_GROUP_BY_USER,
					groupOrCompId);
		}

		List<?> groupUserVOList = sqlDao.executeHQLQuery(hqlQueryCustomer);

		if (groupUserVOList != null && !groupUserVOList.isEmpty()
				&& groupUserVOList.size() > 0) {

			Iterator<?> group_itr = groupUserVOList.iterator();

			while (group_itr.hasNext()) {

				Group grp = (Group) group_itr.next();

				/* GroupUserVO groupVo = populateGroupUserVO(grp); */

				groupHierarchy.add(grp);

			}
		}

		return groupHierarchy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getGroupDetailsForHistory(java.util.Set)
	 */
	@Override
	public Map<Long, String> getGroupDetailsForHistory(Set<Long> groupIDs) {

		List<Long> groupIds = new ArrayList<Long>();
		groupIds.addAll(groupIDs);

		String groupIdsInString = CommonUtil
				.convertCollectionToString(groupIds);

		String hqlQuery = null;

		hqlQuery = String.format(HQL_Group_Details_For_History,
				groupIdsInString);

		List<?> groupDetails = sqlDao.executeHQLQuery(hqlQuery);

		Map<Long, String> groupDetailMap = null;

		if (groupDetails != null && !groupDetails.isEmpty()
				&& groupDetails.size() > 0) {

			Iterator<?> group_itr = groupDetails.iterator();

			groupDetailMap = new HashMap<Long, String>();
			while (group_itr.hasNext()) {

				Object[] grpDetails = (Object[]) group_itr.next();
				StringBuilder grpLevel = new StringBuilder();
				if ((grpDetails[1] != null && grpDetails[2] != null)) {
					grpLevel.append((String) grpDetails[2]).append("^")
							.append((String) grpDetails[1]);
				}
				if (StringUtils.isNotBlank(grpLevel.toString()))
					groupDetailMap.put((Long) grpDetails[0],
							grpLevel.toString());

			}

		}

		return groupDetailMap;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getGroupHierarchy()
	 */
	@Override
	public List<Group> getGroupHierarchy() {

		List<Group> groupHierarchy = new ArrayList<Group>();

		List<?> groupUserVOList = sqlDao
				.executeHQLQuery(HQL_ALL_GROUP_ALL_COMPANY);

		if (groupUserVOList != null && !groupUserVOList.isEmpty()
				&& groupUserVOList.size() > 0) {

			Iterator<?> group_itr = groupUserVOList.iterator();

			while (group_itr.hasNext()) {

				Group grp = (Group) group_itr.next();

				/* GroupUserVO groupVo = populateGroupUserVO(grp); */

				groupHierarchy.add(grp);

			}
		}

		return groupHierarchy;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * updateUserDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public String updateUserDetails(String emailadd, String token) {

		String errorMessage = BizConstants.EMPTY_STRING;
		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(ISEMAILVERIFIED, (byte) 1);
		parameter.put(CONFIRMATIONTOKEN, token);
		parameter.put(EMAIL, emailadd);

		String sqlQuery = String.format(SQL_UPDATE_USER_DETAIL_FOR_EMAIL
				.toString());
		try {
			sqlDao.executeSQLUpdateQuery(sqlQuery, parameter);
		} catch (HibernateException hbexp) {
			errorMessage = hbexp.getMessage();
		}

		return errorMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getviewLog(java.lang.Long)
	 */
	@Override
	public List<UsersViewLogVO> getviewLog(Long user_id) {

		String sqlQuery = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(DATE_TIME, StandardBasicTypes.STRING);
		scalarMapping.put(UPDATE_DATA, StandardBasicTypes.STRING);
		scalarMapping.put(LOGIN_ID, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(USER_ID, user_id);

		sqlQuery = String.format(SQL_GET_VIEWLOG.toString());
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		List<UsersViewLogVO> userslist = null;
		Object[] rowData = null;

		UsersViewLogVO usersViewLogVO = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			userslist = new ArrayList<UsersViewLogVO>();
			while (itr.hasNext()) {
				usersViewLogVO = new UsersViewLogVO();
				rowData = (Object[]) itr.next();

				usersViewLogVO.setDate_time((String) rowData[0]);
				usersViewLogVO.setUpdate_data((String) rowData[1]);
				usersViewLogVO.setUpdate_by((String) rowData[2]);
				usersViewLogVO.setUserid(user_id);

				userslist.add(usersViewLogVO);
			}

		}
		logger.debug("The full list of viewLog is: " + userslist);

		return userslist;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * resetPassword(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public User resetPassword(String loginId, String encryptPassword,
			Long userIdSession) throws HibernateException {

		User user = null;

		List<User> listUserDetail = userDao.findAllByProperty("loginid",
				loginId);

		if (listUserDetail.size() > 0) {

			user = listUserDetail.get(0);

			user.setEncryptedpassword(encryptPassword);
			user.setUpdatedby(userIdSession.toString());
			user.setLoginid(loginId);
			Calendar cal = Calendar.getInstance();
			user.setUpdatedate(cal.getTime());

			userDao.update(user);
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * addUserMangementHistory(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean addUserMangementHistory(Long userIdSession, Long userId)
			throws HibernateException {

		boolean insertUserMangementHistory = false;

		String auditAction = BizConstants.USER_MANAGEMENT_HISTORY_PASSWORD_RESET;

		UserMangementHistory userMangementHistory = new UserMangementHistory();

		userMangementHistory.setAuditaction(auditAction);
		userMangementHistory.setCreatedby(userIdSession);
		userMangementHistory.setUpdatedby(userIdSession);
		userMangementHistory.setUser_id(userId);

		Date date = new Date();
		userMangementHistory.setUpdatedate(new Timestamp(date.getTime()));

		userMangementHistoryDao.create(userMangementHistory);
		insertUserMangementHistory = true;

		return insertUserMangementHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getCompanyId(java.lang.String)
	 */
	@Override
	public String getCompanyId(String loginId) throws HibernateException {

		String sqlQuery = null;

		String companyId = null;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(COMPANY_ID, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(LOGIN_ID, loginId);

		sqlQuery = String.format(SQL_GET_COMPANY_ID.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		if (!resultList.isEmpty() && resultList.size() > 0) {

			companyId = (String) resultList.get(0);
		}
		return companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * deleteFunctionalIds(java.lang.Long)
	 */
	@Override
	public void deleteFunctionalIds(Long roleId) {
		Map<String, Object> parameter = new HashMap<>();
		Object obj = roleId.intValue();
		parameter.put("roleId", obj);
		Query query = rolefunctionalgrpDao.executeHQLQuery(
				"delete from Rolefunctionalgrp where roleid= :roleId ",
				parameter);

		query.executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * retrieveUserDetails(java.lang.Long)
	 */
	@Override
	public UserDetailsVO retrieveUserDetails(Long userId)
			throws HibernateException {

		String sqlQuery = null;

		UserDetailsVO userDetailsVO = new UserDetailsVO();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(ROLEID, StandardBasicTypes.LONG);
		scalarMapping.put(IS_VALID, StandardBasicTypes.BOOLEAN);
		scalarMapping.put(IS_LOCKED, StandardBasicTypes.BOOLEAN);
		scalarMapping.put(ROLE_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(COMPANY_ID, StandardBasicTypes.LONG);
		scalarMapping.put(COMAPNY_NAME, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(USER_ID, userId);

		sqlQuery = String.format(SQL_RETRIEVE_USER_DETAILS);

		Iterator<?> resultList = sqlDao.executeSQLSelect(sqlQuery,
				scalarMapping, parameter).iterator();

		if (resultList.hasNext()) {

			Object[] rowData = (Object[]) resultList.next();

			userDetailsVO.setRoleId((Long) rowData[0]);
			userDetailsVO.setRoleName((String) rowData[3]);
			userDetailsVO.setCompId((Long) rowData[4]);
			userDetailsVO.setCompName((String) rowData[5]);
			userDetailsVO
					.setAccountState((Boolean) rowData[1] == false ? "Invalid"
							: ((Boolean) rowData[2] == true ? "Locked"
									: "Valid"));
		}

		return userDetailsVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * checkRoleName(java.lang.String)
	 */
	@Override
	public boolean checkRoleName(String roleName) throws HibernateException {

		String sqlQuery = null;
		boolean roleNameExists = false;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(ROLEID, StandardBasicTypes.LONG);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(ROLE_NAME, roleName);

		sqlQuery = String.format(SQL_CHECK_ROLE);

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		if (!resultList.isEmpty() && resultList.size() > 0) {
			roleNameExists = true;
		}
		return roleNameExists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.usermanagement.dao.UserManagementDAO#
	 * getUserListUnderCompany(java.lang.Long, java.lang.Long)
	 */
	// Modified by seshu.
	@Override
	public List<UserUnderCompanyVO> getUserListUnderCompany(Long companyId,
			Long userId, String loginId) {

		List<UserUnderCompanyVO> userUnderCompanyList = null;

		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		propertiesMap.put(COMPANY_ID, companyId);
		propertiesMap.put(CREATEDBY, String.valueOf(userId));

		List<User> userList = userDao.findAllByProperties(propertiesMap);

		if (userList != null && !userList.isEmpty() && userList.size() > 0) {

			userUnderCompanyList = new ArrayList<UserUnderCompanyVO>();

			for (User user : userList) {

				UserUnderCompanyVO userUnderCompany = new UserUnderCompanyVO();
				userUnderCompany.setId(user.getId());
				userUnderCompany.setCompany_name(user.getCompany().getName());
				userUnderCompany.setRegistered_date(CommonUtil
						.dateToString(user.getCreationdate()));
				userUnderCompany.setRegistered_by(loginId);
				if (user.isValid()) {
					userUnderCompany.setAccount_state(BizConstants.VALID);
				} else {
					userUnderCompany.setAccount_state(BizConstants.INVALID);
				}
				userUnderCompany.setLoginId(user.getLoginid());
				userUnderCompany.setRolename(user.getRole().getName());

				userUnderCompanyList.add(userUnderCompany);
			}
		}

		return userUnderCompanyList;
	}

	// Added by seshu.
	@Override
	public boolean getRoleAssignedStatus(String roleId)
			throws HibernateException {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		boolean roleAssignedStatus = false;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(USERID, StandardBasicTypes.LONG);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(ROLEID, Integer.parseInt(roleId));

		sqlQuery = String.format(SQL_CHECK_ROLE_ASSIGNED_STATUS);

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		if (!resultList.isEmpty() && resultList.size() > 0) {
			roleAssignedStatus = true;
		}
		return roleAssignedStatus;

	}

	// Added by seshu.
	@Override
	public List<UsersVO> getAllUserIdListFull(String companyId) {
		String sqlQuery = null;
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(USERID, StandardBasicTypes.STRING);
		scalarMapping.put(COMAPNY_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(REGISTERED_DATE, StandardBasicTypes.STRING);
		scalarMapping.put(REGISTERED_BY, StandardBasicTypes.STRING);
		scalarMapping.put(IS_VALID, StandardBasicTypes.BOOLEAN);
		scalarMapping.put(IS_LOCKED, StandardBasicTypes.BOOLEAN);
		scalarMapping.put(LOGIN_ID, StandardBasicTypes.STRING);
		scalarMapping.put(ROLE_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(ROLEID, StandardBasicTypes.LONG);

		Map<String, Object> parameter = new LinkedHashMap<String, Object>();
		parameter.put(COMPANY_ID, Integer.parseInt(companyId));

		sqlQuery = String.format(SQL_GET_USERLIST_FULL.toString());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		List<UsersVO> userslist = null;
		Object[] rowData = null;

		UsersVO usersVO = null;

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			userslist = new ArrayList<UsersVO>();
			while (itr.hasNext()) {
				usersVO = new UsersVO();
				rowData = (Object[]) itr.next();

				if ((boolean) rowData[4]) {
					if (!(boolean) rowData[5]) {

						usersVO.setAccount_state("Valid");
					} else {
						usersVO.setAccount_state("Locked");
					}

				} else {
					usersVO.setAccount_state("Invalid");
				}

				usersVO.setId(Long.parseLong((String) rowData[0]));
				usersVO.setCompany_name((String) rowData[1]);
				usersVO.setRegistered_date((String) rowData[2]);
				usersVO.setRegistered_by((String) rowData[3]);
				usersVO.setLoginId((String) rowData[6]);
				usersVO.setRolename((String) rowData[7]);
				usersVO.setRoleid((Long) rowData[8]);

				userslist.add(usersVO);
			}

			logger.debug("The full list of userList Simple is: " + userslist);
			return userslist;
		}

		return null;

	}

}

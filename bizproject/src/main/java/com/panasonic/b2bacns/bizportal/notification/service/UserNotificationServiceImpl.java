/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingOutJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UserListVO;
import com.panasonic.b2bacns.bizportal.persistence.Notificationsetting;
import com.panasonic.b2bacns.bizportal.persistence.UserNotificationSetting;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author Narendra.Kumar
 * 
 */
@Service
public class UserNotificationServiceImpl implements UserNotificationService {
	private static final Logger logger = LogManager
			.getLogger(UserNotificationServiceImpl.class);
	private static final StringBuilder GET_USER_NOTIFICATION_SETTINGS_BY_USER_AND_NOTIFICATIONSETTING_ID = new StringBuilder(
			"from UserNotificationSetting as uns where uns.user.id=:userId and uns.notificationsetting.id=:notificationSettingId");
	private static final StringBuilder GET_USER_NOTIFICATION_SETTING_BY_GROUP_AND_NOTIFICATION_CATEGORY_ID = new StringBuilder(
			" from UserNotificationSetting as uns where uns.notificationsetting.group.id=:groupId and uns.notificationsetting.notificationtypeMaster.id IN (:notificationTypeIds)");
	private static final StringBuilder GET_USER_NOTIFICATION_SETTING_BATCH_BY_USERS_AND_NOTIFICATION_TYPE_ID = new StringBuilder(
			"from UserNotificationSetting uns where uns.user.id IN (:userIds) and uns.notificationsetting.notificationtypeMaster.id=:notificationTypeId");

	private static final StringBuilder GET_USER_NOTIFICATION_SETTING_BY_COMPANY_AND_SETTING_TYPE = new StringBuilder(
			"from UserNotificationSetting as uns1 where uns1.notificationsetting.company.id=:companyId and uns1.notificationsetting.notificationtypeMaster.typename=:typename");

	final StringBuilder SQL_GET_USERS_LIST_BY_COMPANY_ID = new StringBuilder(
			"select distinct users.id as userId, users.firstname || ' ' || users.lastname as userName, users.department as department, users.email as emailAddress, users.telephone telephone from  users users, companiesusers cusers,user_notification_settings uns")
			.append(" where users.id=cusers.user_id and uns.user_id=users.id and cusers.company_id=:companyId");

	private static final StringBuilder GET_USER_NOTIFICATION_SETTINGS_BY_USER_AND_NOTIFICATIONSETTING_IDS = new StringBuilder(
			"from UserNotificationSetting as uns where uns.user.id=:userId and uns.notificationsetting.id in (:notificationSettingId)");
	
	private static final StringBuilder DELETE_USERS = new StringBuilder(
			"delete from UserNotificationSetting as uns where uns.user.id in (:userId) and uns.notificationsetting.id = :notificationSettingId");

	@Autowired
	private SQLDAO sqldao;
	private GenericDAO<UserNotificationSetting> userNotificationSettingDAO;

	@Autowired
	public void setDao(GenericDAO<UserNotificationSetting> daoToSet) {
		userNotificationSettingDAO = daoToSet;
		userNotificationSettingDAO.setClazz(UserNotificationSetting.class);
	}

	@Override
	public List<AdvanceNotificationSettingOutJsonVO> getUserNotificationSetting(
			Long groupId, List<Integer> notificationCategoryIdList) {
		List<UserNotificationSetting> userNotificationSettingList = getUserNotificationSettingByGroupAndNotificationCategoryId(
				groupId, notificationCategoryIdList);
		List<AdvanceNotificationSettingOutJsonVO> userNotificationSettingVosList = new ArrayList<AdvanceNotificationSettingOutJsonVO>();
		for (UserNotificationSetting userNotificationSetting : userNotificationSettingList) {
			AdvanceNotificationSettingOutJsonVO advanceNotificationSettingOutJsonVO = new AdvanceNotificationSettingOutJsonVO();
			advanceNotificationSettingOutJsonVO
					.setUserId(userNotificationSetting.getUser().getId());
			advanceNotificationSettingOutJsonVO
					.setUserName(userNotificationSetting.getUser()
							.getFirstname()
							+ " "
							+ userNotificationSetting.getUser().getLastname());
			advanceNotificationSettingOutJsonVO
					.setDepartment(userNotificationSetting.getUser()
							.getDepartment());
			advanceNotificationSettingOutJsonVO
					.setEmailAddress(userNotificationSetting.getUser()
							.getEmail());
			advanceNotificationSettingOutJsonVO
					.setTelephone(userNotificationSetting.getUser()
							.getTelephone());
			/*
			 * advanceNotificationSettingOutJsonVO.setNotificationBy(
			 * userNotificationSetting .getNotificationby());
			 */

			userNotificationSettingVosList
					.add(advanceNotificationSettingOutJsonVO);
		}

		return userNotificationSettingVosList;
	}

	@Override
	public List<UserListVO> getUserListByCompanyId(long companyId) {
		List<?> userNotificationSettingList = getUsersListByCompanyId(companyId);
		List<UserListVO> userListVOList = new ArrayList<UserListVO>();

		UserListVO userListVO = null;

		if (userNotificationSettingList != null
				&& userNotificationSettingList.size() > 0) {
			Iterator<?> itr = userNotificationSettingList.iterator();
			Object[] rowData = null;
			while (itr.hasNext()) {
				rowData = (Object[]) itr.next();
				userListVO = new UserListVO();
				userListVO.setUserId(Long.parseLong(rowData[0].toString()));
				userListVO.setUserName((String) rowData[1]);
				userListVO.setDepartment((String) rowData[2]);
				userListVO.setEmailAddress((String) rowData[3]);
				userListVO.setTelephone((String) rowData[4]);
				userListVOList.add(userListVO);

			}
		}

		return userListVOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNotificationSetting> duplicateCheckForUserNotificationSettings(
			long userId, int notificationSettingId) throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.USER_ID, userId);
		queryMap.put(BizConstants.NOTIFICATION_SETTING_ID,
				notificationSettingId);
		String hqlQuery = String
				.format(GET_USER_NOTIFICATION_SETTINGS_BY_USER_AND_NOTIFICATIONSETTING_ID
						.toString());
		return (List<UserNotificationSetting>) userNotificationSettingDAO
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNotificationSetting> duplicateCheckForUserNotificationSettings(
			long userId, List<Integer> notificationSettingId)
			throws HibernateException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.USER_ID, userId);
		queryMap.put(BizConstants.NOTIFICATION_SETTING_ID,
				notificationSettingId);

		String hqlQuery = String
				.format(GET_USER_NOTIFICATION_SETTINGS_BY_USER_AND_NOTIFICATIONSETTING_IDS
						.toString());
		return (List<UserNotificationSetting>) userNotificationSettingDAO
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNotificationSetting> getUserNotificationSettingByGroupAndNotificationCategoryId(
			Long groupId, List<Integer> notificationCategoryIdList)
			throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_ID, groupId);
		queryMap.put(BizConstants.NOTIFICATION_TYPE_IDS,
				notificationCategoryIdList);
		String hqlQuery = String
				.format(GET_USER_NOTIFICATION_SETTING_BY_GROUP_AND_NOTIFICATION_CATEGORY_ID
						.toString());

		return (List<UserNotificationSetting>) userNotificationSettingDAO
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNotificationSetting> getUserNotificationSettingBatchByIds(
			List<Long> userIds, int notificationCategoryId)
			throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.USER_IDS, userIds);
		queryMap.put(BizConstants.NOTIFICATION_TYPE_ID, notificationCategoryId);
		String hqlQuery = String
				.format(GET_USER_NOTIFICATION_SETTING_BATCH_BY_USERS_AND_NOTIFICATION_TYPE_ID
						.toString());
		return (List<UserNotificationSetting>) userNotificationSettingDAO
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	public List<UserNotificationSetting> getUserNotificationSettingBySettingType(
			long companyId, String settingType) throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.COMPANY_ID, companyId);
		queryMap.put(BizConstants.TYPE_NAME, settingType);
		String hqlQuery = String
				.format(GET_USER_NOTIFICATION_SETTING_BY_COMPANY_AND_SETTING_TYPE
						.toString());

		return (List<UserNotificationSetting>) userNotificationSettingDAO
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	public List<?> getUsersListByCompanyId(long companyId)
			throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.COMPANY_ID, companyId);
		String SQL_QUERY = String.format(SQL_GET_USERS_LIST_BY_COMPANY_ID
				.toString());
		List<?> resultList = sqldao.executeSQLSelect(SQL_QUERY, queryMap);

		return resultList;
	}

	@Override
	@Transactional
	public boolean saveNotificationSetting(
			List<UserNotificationSetting> notificationsetting) {
		// TODO Auto-generated method stub
		boolean saveStatus = false;
		try {

			if (notificationsetting != null) {
				userNotificationSettingDAO.batchSaveOrUpdate(notificationsetting);
				saveStatus = true;
			}
		}

		catch (HibernateException hibernateException) {
			logger.error("" + hibernateException);
		}
		return saveStatus;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void deleteUsers(
			List<Long> userId, Integer notificationSettingId)
			throws HibernateException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.USER_ID, userId);
		queryMap.put(BizConstants.NOTIFICATION_SETTING_ID,
				notificationSettingId);

		String hqlQuery = String
				.format(DELETE_USERS
						.toString());
		
		Query query=  userNotificationSettingDAO
				.executeHQLQuery(hqlQuery, queryMap);
		
		query.executeUpdate();
	}

}

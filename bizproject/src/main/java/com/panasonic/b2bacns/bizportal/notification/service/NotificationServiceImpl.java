/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVO;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.Notificationsetting;
import com.panasonic.b2bacns.bizportal.persistence.NotificationtypeMaster;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author Narendra.Kumar
 * 
 */
@Service
public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = Logger
			.getLogger(NotificationServiceImpl.class);

	private static final StringBuilder GET_NON_MASSIVE_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE = new StringBuilder(
			"select  nm.typename, ns.on_off  from notificationtype_master nm left outer join notificationsettings ns  ")
			.append("on nm.id=ns.notificationtype_id and ns.group_id=:groupId where nm.typename<>:typename");
	private static final StringBuilder GET_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE = new StringBuilder(
			"from Notificationsetting as ns where ns.group.id=:groupId and ns.notificationtypeMaster.typename= :typename");
	private static final StringBuilder GET_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE_ID = new StringBuilder(
			" from Notificationsetting as ns where ns.group.id=:groupId and ns.notificationtypeMaster.id=:notificationTypeId");

	private static final StringBuilder SQL_GET_PARENT_CHILD_NOTIFICATION_SETTING = new StringBuilder(
			"WITH RECURSIVE ctegroupchildtoparent AS ")
			.append("(SELECT id, parentid, groupcategoryid,uniqueid,name as groupname,groups.path FROM groups WHERE id = :groupId UNION ALL ")
			.append("SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,name as groupname,sp.path FROM groups As si ")
			.append("INNER JOIN ctegroupchildtoparent AS sp ON (si.id = sp.parentid)), ctegroups AS ")
			.append("(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path FROM groups g ")
			.append("WHERE (g.parentid = ANY(cast(string_to_array(:group_id, ',') as int[])) OR (g.id = ANY(cast(string_to_array(:group_id, ',') as int[])))) UNION ALL ")
			.append("SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,si.path FROM groups As si INNER JOIN ctegroups AS sp ON (si.parentid = sp.id)) ")
			.append("Select distinct temp.*,ns.id as notificationsettingid, uns.user_id as userid, uns.id as usernotificationid, uns.email_status as emailstatus from (Select distinct id,0 as parent from ctegroups where id <> :groupId union ")
			.append("Select distinct id, 1 as parent from ctegroupchildtoparent g where id <> :groupId union Select distinct id, -1 as parent from ctegroupchildtoparent g where id = :groupId)temp  join notificationsettings ns on ns.group_id = temp.id and ns.notificationtype_id in (Select id from notificationtype_master where id = :notificationTypeIdValue) ")
			.append("join user_notification_settings uns on (uns.notification_settings_id  = ns.id ) where uns.user_id in (:userid)");

	private static final StringBuilder SQL_GET_PARENT_CHILD_NOTIFICATION_SETTING_DEL = new StringBuilder(
			"WITH RECURSIVE ctegroupchildtoparent AS ")
			.append("(SELECT id, parentid, groupcategoryid,uniqueid,name as groupname,groups.path FROM groups WHERE id = :groupId UNION ALL ")
			.append("SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,name as groupname,sp.path FROM groups As si ")
			.append("INNER JOIN ctegroupchildtoparent AS sp ON (si.id = sp.parentid)), ctegroups AS ")
			.append("(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path, 0 as level FROM groups g ")
			.append("WHERE (g.parentid = ANY(cast(string_to_array(:group_id, ',') as int[]))) UNION ALL ")
			.append("SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,si.path, sp.level+1 as level FROM groups As si INNER JOIN ctegroups AS sp ON (si.parentid = sp.id)) ")
			.append("Select distinct temp.*,ns.id as notificationsettingid, uns.user_id as userid, uns.id as usernotificationid, uns.email_status as emailstatus from (Select distinct id,0 as parent, ctegroups.level as level from ctegroups where id <> :groupId union ")
			.append("Select distinct id, 1 as parent, 0 as level from ctegroupchildtoparent g where id <> :groupId union Select distinct id, -1 as parent, 0 as level from ctegroupchildtoparent g where id = :groupId)temp  join notificationsettings ns on ns.group_id = temp.id and ns.notificationtype_id in (Select id from notificationtype_master where id = :notificationTypeIdValue) ")
			.append("join user_notification_settings uns on (uns.notification_settings_id  = ns.id ) where uns.user_id in (:userid) order by parent desc");

	private static final StringBuilder SQL_GET_PARENT_CHILD_NOTIFICATION_SETTING_FORALL = new StringBuilder(
			"WITH RECURSIVE ctegroupchildtoparent AS ")
			.append("(SELECT id, parentid, groupcategoryid,uniqueid,name as groupname,groups.path FROM groups WHERE id = :groupId UNION ALL ")
			.append("SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,name as groupname,sp.path FROM groups As si ")
			.append("INNER JOIN ctegroupchildtoparent AS sp ON (si.id = sp.parentid)), ctegroups AS ")
			.append("(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path FROM groups g ")
			.append("WHERE (g.parentid = ANY(cast(string_to_array(:group_id, ',') as int[])) OR (g.id = ANY(cast(string_to_array(:group_id, ',') as int[])))) UNION ALL ")
			.append("SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,si.path FROM groups As si INNER JOIN ctegroups AS sp ON (si.parentid = sp.id)) ")
			.append("Select distinct temp.*,ns.id as notificationsettingid, uns.user_id as userid, uns.id as usernotificationid, uns.email_status as emailstatus from (Select distinct id,0 as parent from ctegroups where id <> :groupId union ")
			.append("Select distinct id, 1 as parent from ctegroupchildtoparent g where id <> :groupId union Select distinct id, -1 as parent from ctegroupchildtoparent g where id = :groupId)temp  join notificationsettings ns on ns.group_id = temp.id and ns.notificationtype_id in (Select id from notificationtype_master) ")
			.append("join user_notification_settings uns on (uns.notification_settings_id  = ns.id ) where uns.user_id in (:userid)");

	private static final String GROUP_ID = "groupId";
	private static final String GROUP_ID_STRING = "group_id";
	private static final String NOTIFICATION_TYPE_ID = "notificationTypeId";
	private static final String NOTIFICATION_TYPE_ID_VALUE = "notificationTypeIdValue";
	private static final String USER_ID = "userid";

	@Value("${master.notificationid}")
	String masterNotificationId;

	@Autowired
	private SQLDAO sqlDao;

	private GenericDAO<Notificationsetting> notificationsettingDao;

	@Autowired
	public void setDao(GenericDAO<Notificationsetting> daoToSet) {
		notificationsettingDao = daoToSet;
		notificationsettingDao.setClazz(Notificationsetting.class);
	}

	@Override
	public Map<String, String> getNotificationSetting(long groupId) {
		List<?> notificationsettingList = getNonMassiveNotificationSettingByGroupId(groupId);
		Map<String, String> notificationCategorySettingVOs = new HashMap<String, String>();

		if (notificationsettingList != null
				&& notificationsettingList.size() > 0) {
			Iterator<?> itr = notificationsettingList.iterator();
			Object[] rowData = null;
			while (itr.hasNext()) {
				rowData = (Object[]) itr.next();
				if (rowData[1] != null) {
					Boolean onff = (Boolean) rowData[1];
					if (onff) {
						notificationCategorySettingVOs.put((String) rowData[0],
								BizConstants.on);

					} else {
						notificationCategorySettingVOs.put((String) rowData[0],
								BizConstants.off);

					}
				} else {
					notificationCategorySettingVOs.put((String) rowData[0],
							(String) rowData[1]);
				}
			}
		}

		return notificationCategorySettingVOs;
	}

	@Override
	public boolean setNotificationSetting(
			List<NotificationCategorySettingVO> notificationCategorySettingVOs) {
		boolean saveStatus = false;
		List<Notificationsetting> notificationsettings = new LinkedList<Notificationsetting>();
		try {
			for (NotificationCategorySettingVO notificationCategorySettingVO : notificationCategorySettingVOs) {
				Notificationsetting notificationsetting = new Notificationsetting();
				List<Notificationsetting> notificationSettingExistingList = duplicateCheckForNotificationSettings(
						notificationCategorySettingVO.getGroupID(),
						notificationCategorySettingVO
								.getNotificationCategoryId());
				if (notificationSettingExistingList != null
						&& !notificationSettingExistingList.isEmpty()) {
					notificationsetting = notificationSettingExistingList
							.get(0);
				} else {
					Group group = new Group();
					group.setId(notificationCategorySettingVO.getGroupID());
					NotificationtypeMaster notificationtypeMaster = new NotificationtypeMaster();
					notificationtypeMaster.setId(notificationCategorySettingVO
							.getNotificationCategoryId());
					notificationsetting.setGroup(group);
					notificationsetting
							.setNotificationtypeMaster(notificationtypeMaster);
				}

				if (StringUtils.equalsIgnoreCase(
						notificationCategorySettingVO.getNotification(),
						BizConstants.on))
					notificationsetting.setOnOff(true);
				else
					notificationsetting.setOnOff(false);

				notificationsettings.add(notificationsetting);
			}
			if (notificationsettings != null && !notificationsettings.isEmpty()) {
				notificationsettingDao.batchSaveOrUpdate(notificationsettings);
				saveStatus = true;
			}
		}

		catch (HibernateException hibernateException) {
			logger.error("" + hibernateException);
		}
		return saveStatus;
	}

	@Override
	public boolean saveNotificationSetting(
			Notificationsetting notificationsetting) {
		boolean saveStatus = false;
		try {

			if (notificationsetting != null) {
				notificationsettingDao.saveOrUpdate(notificationsetting);
				saveStatus = true;
			}
		}

		catch (HibernateException hibernateException) {
			logger.error("" + hibernateException);
		}
		return saveStatus;
	}

	@Override
	public List<?> getNonMassiveNotificationSettingByGroupId(long groupId)
			throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_ID, groupId);
		queryMap.put(BizConstants.TYPE_NAME, BizConstants.IS_MASTER);
		String sqlQuery = String
				.format(GET_NON_MASSIVE_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE
						.toString());
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, queryMap);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notificationsetting> duplicateCheckForNotificationSettings(
			long groupId, int notificationTypeId) throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_ID, groupId);
		queryMap.put(BizConstants.NOTIFICATION_TYPE_ID, notificationTypeId);
		String hqlQuery = String
				.format(GET_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE_ID
						.toString());
		return (List<Notificationsetting>) notificationsettingDao
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notificationsetting> getNotificationSettingByGroupIdAndSettingTypeId(
			long groupId, int notificationTypeId) throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_ID, groupId);
		queryMap.put(BizConstants.NOTIFICATION_TYPE_ID, notificationTypeId);
		String hqlQuery = String
				.format(GET_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE_ID
						.toString());
		return (List<Notificationsetting>) notificationsettingDao
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notificationsetting> getNotificationSettingByGroupIdAndSettingType(
			long groupId, String notificationType) throws HibernateException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_ID, groupId);
		queryMap.put(BizConstants.TYPE_NAME, BizConstants.IS_MASTER);
		String hqlQuery = String
				.format(GET_NOTIFICATION_SETTING_BY_GROUP_ID_AND_SETTING_TYPE
						.toString());
		return (List<Notificationsetting>) notificationsettingDao
				.executeHQLSelect(hqlQuery, queryMap);
	}

	@Override
	public Notificationsetting getNotificationSettingById(
			int notificationSettingId) throws HibernateException {
		return notificationsettingDao.findByID(notificationSettingId);
	}

	@Override
	public List<?> getParentandChildNotificationSettings(Long groupId,
			Integer notificationCategoryId, List<Long> userList,
			boolean delStatus) {

		/*
		 * String paramName;
		 * 
		 * if(notificationCategoryId==4) paramName = "1"; else paramName = "id";
		 */

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(GROUP_ID, groupId);
		parameter.put(GROUP_ID_STRING, String.valueOf(groupId));
		if (!notificationCategoryId.toString().equalsIgnoreCase(
				masterNotificationId) && !delStatus)
			parameter.put(NOTIFICATION_TYPE_ID_VALUE, notificationCategoryId);
		else if(delStatus)
			parameter.put(NOTIFICATION_TYPE_ID_VALUE, notificationCategoryId);
		parameter.put(USER_ID, userList);

		String query;

		if (delStatus) {
			query = SQL_GET_PARENT_CHILD_NOTIFICATION_SETTING_DEL.toString();
		} else {
			if (notificationCategoryId.toString().equalsIgnoreCase(
					masterNotificationId))
				query = SQL_GET_PARENT_CHILD_NOTIFICATION_SETTING_FORALL
						.toString();
			else
				query = SQL_GET_PARENT_CHILD_NOTIFICATION_SETTING.toString();
		}

		return sqlDao.executeSQLSelect(query, parameter);

	}
}

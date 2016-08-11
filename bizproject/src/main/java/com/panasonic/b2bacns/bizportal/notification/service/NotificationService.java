/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVO;
import com.panasonic.b2bacns.bizportal.persistence.Notificationsetting;

/**
 * @author Narendra.Kumar
 * 
 */
public interface NotificationService {

	/**
	 * @param groupId
	 * @return
	 */
	public Map<String, String> getNotificationSetting(long groupId);

	/**
	 * @param notificationCategorySettingVOs
	 * @return
	 */
	public boolean setNotificationSetting(
			List<NotificationCategorySettingVO> notificationCategorySettingVOs);

	/**
	 * @param groupId
	 * @return
	 */
	public List<?> getNonMassiveNotificationSettingByGroupId(long groupId);

	/**
	 * @param groupId
	 * @param notificationTypeId
	 * @return
	 */
	public List<Notificationsetting> duplicateCheckForNotificationSettings(
			long groupId, int notificationTypeId);

	/**
	 * @param groupId
	 * @param notificationType
	 * @return
	 */
	public List<Notificationsetting> getNotificationSettingByGroupIdAndSettingType(
			long groupId, String notificationType);

	/**
	 * @param group
	 * @param notificationTypeId
	 * @return
	 * @throws HibernateException
	 */
	public List<Notificationsetting> getNotificationSettingByGroupIdAndSettingTypeId(
			long groupId, int notificationTypeId) throws HibernateException;

	/**
	 * @param notificationSettingId
	 * @return
	 */
	public Notificationsetting getNotificationSettingById(
			int notificationSettingId);

	/**
	 * @param notificationsetting
	 * @return
	 */
	public boolean saveNotificationSetting(
			Notificationsetting notificationsetting);

	public List<?> getParentandChildNotificationSettings(Long groupId,
			Integer notificationCategoryId, List<Long> userList, boolean delStatus);

	

	

	

	
}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingOutJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UserListVO;
import com.panasonic.b2bacns.bizportal.persistence.Notificationsetting;
import com.panasonic.b2bacns.bizportal.persistence.UserNotificationSetting;

/**
 * @author Narendra.Kumar
 * 
 */
public interface UserNotificationService {

	/**
	 * This method check user id and notification setting id unique combination
	 * in user notification setting
	 * 
	 * @param userId
	 * @param notificationSettingId
	 * @return
	 */
	public List<UserNotificationSetting> duplicateCheckForUserNotificationSettings(
			long userId, int notificationSettingId);

	/**
	 * @param userIds
	 * @param notificationCategoryId
	 * @return
	 * @throws HibernateException
	 */
	public List<UserNotificationSetting> getUserNotificationSettingBatchByIds(
			List<Long> userIds, int notificationCategoryId)
			throws HibernateException;

	/**
	 * this method return the user notification detail
	 * 
	 * @param companyId
	 * @param notificationCategoryId
	 * @param settingType
	 * @return
	 */

	public List<AdvanceNotificationSettingOutJsonVO> getUserNotificationSetting(
			Long groupId, List<Integer> notificationCategoryIdList);

	/**
	 * this method return the user notification detail
	 * 
	 * @param companyId
	 * @param notificationCategoryId
	 * @return
	 */
	public List<UserNotificationSetting> getUserNotificationSettingByGroupAndNotificationCategoryId(
			Long groupId, List<Integer> notificationCategoryIdList);

	/**
	 * this method return the user detail of a company
	 * 
	 * @param companyId
	 * @return
	 */
	public List<UserListVO> getUserListByCompanyId(long companyId);

	public List<UserNotificationSetting> duplicateCheckForUserNotificationSettings(
			long userId, List<Integer> notificationSettingId)
			throws HibernateException;

	public boolean saveNotificationSetting(
			List<UserNotificationSetting> notificationsetting);

	public void deleteUsers(List<Long> userId, Integer notificationSettingId)
			throws HibernateException;

	

	

}

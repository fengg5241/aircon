/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.service;

import java.util.List;
import java.util.Map;

import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingInJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingOutJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UpdateAdvanceNotificationSettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UserListVO;

/**
 * @author Narendra.Kumar
 * 
 */
public interface NotificationSettingCommonService {
	/**
	 * @param groupId
	 * @return
	 */
	public Map<String, String> getNotificationSetting(long groupId);

	public boolean setNotificationSetting(
			List<NotificationCategorySettingVO> notificationCategorySettingVOs);

	/**
	 * @param updateAdvanceNotificationSettingVO
	 * @return
	 */
	public boolean updateAdvanceNotificationSetting(
			UpdateAdvanceNotificationSettingVO updateAdvanceNotificationSettingVO);

	/**
	 * @param advanceNotificationSettingInJsonVO
	 * @return
	 */
	public List<AdvanceNotificationSettingOutJsonVO> getAdvanceNotificationSetting(
			AdvanceNotificationSettingInJsonVO advanceNotificationSettingInJsonVO);

	/**
	 * @param companyId
	 * @return
	 */
	public List<UserListVO> getUserListByCompanyId(long companyId);

	/**
	 * @param updateAdvanceNotificationSettingVO
	 * @return
	 */
	public boolean updateMasterNotificationSetting(
			UpdateAdvanceNotificationSettingVO updateAdvanceNotificationSettingVO);

	/**
	 * @param advanceNotificationSettingInJsonVO
	 * @return
	 */
	public List<AdvanceNotificationSettingOutJsonVO> getMasterNotificationSetting(
			AdvanceNotificationSettingInJsonVO advanceNotificationSettingInJsonVO);
}

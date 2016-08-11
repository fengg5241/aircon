/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingInJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingOutJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UpdateAdvanceNotificationSettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UserListVO;
import com.panasonic.b2bacns.bizportal.persistence.Notificationsetting;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.persistence.UserNotificationSetting;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author Narendra.Kumar
 * 
 */
@Service
public class NotificationSettingCommonServiceImpl implements
		NotificationSettingCommonService {

	@Autowired
	NotificationService notificationService;
	@Autowired
	UserNotificationService userNotificationService;

	@Override
	public Map<String, String> getNotificationSetting(long groupId) {
		Map<String, String> notificationCategorySettingVOs = notificationService
				.getNotificationSetting(groupId);
		return notificationCategorySettingVOs;
	}

	@Override
	public boolean setNotificationSetting(
			List<NotificationCategorySettingVO> notificationCategorySettingVOs) {
		boolean saveStatus = notificationService
				.setNotificationSetting(notificationCategorySettingVOs);
		return saveStatus;

	}

	@Override
	@Transactional
	public boolean updateAdvanceNotificationSetting(
			UpdateAdvanceNotificationSettingVO updateAdvanceNotificationSettingVO) {
		boolean saveStatus = false;

		boolean delStatus = false;

		if (updateAdvanceNotificationSettingVO != null) {
			Long groupId = updateAdvanceNotificationSettingVO.getGroupID();
			int notificationCategoryId = updateAdvanceNotificationSettingVO
					.getNotificationCategoryId();
			List<Notificationsetting> notificationSettingList = notificationService
					.getNotificationSettingByGroupIdAndSettingTypeId(groupId,
							notificationCategoryId);
			if (notificationSettingList != null
					&& !notificationSettingList.isEmpty()) {
				Notificationsetting notificationsetting = notificationSettingList
						.get(0);

				List<Long> userNotificationSettingRemoveList = updateAdvanceNotificationSettingVO
						.getDeleteusers();

				List<Long> userNotificationSettingAddList = updateAdvanceNotificationSettingVO
						.getAddusers();

				List<UserNotificationSetting> userNotificationSettingSaveUpdateList = new ArrayList<UserNotificationSetting>();

				List<UserNotificationSetting> userNotificationSettingSaveUpdateListForDeletedUsers = new ArrayList<UserNotificationSetting>();
				if (userNotificationSettingAddList != null
						&& !userNotificationSettingAddList.isEmpty())
					userNotificationSettingSaveUpdateList = getUserStatusUpdateList(
							groupId, notificationCategoryId,
							updateAdvanceNotificationSettingVO.getAddusers(),
							notificationsetting, false);

				if (userNotificationSettingSaveUpdateList != null
						&& !userNotificationSettingSaveUpdateList.isEmpty())
					saveStatus = userNotificationService
							.saveNotificationSetting(userNotificationSettingSaveUpdateList);

				if (userNotificationSettingRemoveList != null
						&& !userNotificationSettingRemoveList.isEmpty())
					userNotificationService.deleteUsers(
							userNotificationSettingRemoveList,
							notificationsetting.getId());

				if (userNotificationSettingRemoveList != null
						&& !userNotificationSettingRemoveList.isEmpty())
					userNotificationSettingSaveUpdateListForDeletedUsers = getUserStatusUpdateList(
							groupId, notificationCategoryId,
							userNotificationSettingRemoveList,
							notificationsetting, true);

				if (userNotificationSettingSaveUpdateListForDeletedUsers != null
						&& !userNotificationSettingSaveUpdateListForDeletedUsers
								.isEmpty())
					delStatus = userNotificationService
							.saveNotificationSetting(userNotificationSettingSaveUpdateListForDeletedUsers);

			}
		}
		return delStatus == false ? saveStatus : delStatus;

	}

	private List<UserNotificationSetting> getUserStatusUpdateList(Long groupId,
			int notificationCategoryId, List<Long> users,
			Notificationsetting notificationsetting, boolean delStatus) {

		List<UserNotificationSetting> userNotificationSettingSaveUpdateList = new ArrayList<UserNotificationSetting>();

		List<?> resultList = notificationService
				.getParentandChildNotificationSettings(groupId,
						notificationCategoryId, users, delStatus);

		Map<Long, Boolean> userStatusMap = new HashMap<Long, Boolean>();

		if (users != null & !users.isEmpty()) {

			for (Long userId : users) {
				userStatusMap.put(userId, true);
			}
		}	
			UserNotificationSetting userNotificationSetting = null;

			Object[] rowData = null;
			if (!delStatus) {
				if (resultList != null && !resultList.isEmpty()) {

					Iterator<?> itr = resultList.iterator();

					while (itr.hasNext()) {

						rowData = (Object[]) itr.next();

						userNotificationSetting = new UserNotificationSetting();
						Notificationsetting setting = new Notificationsetting();
						setting.setId((Integer) rowData[2]);
						userNotificationSetting.setId((Integer) rowData[4]);
						userNotificationSetting.setNotificationsetting(setting);
						User user = new User();
						user.setId(Long.valueOf((Integer) rowData[3]));
						userNotificationSetting.setUser(user);

						if ((Integer) rowData[1] == 1) {

							userNotificationSetting.setEmailStatus(true);

							userStatusMap.put(
									Long.valueOf((Integer) rowData[3]), false);

						} else if ((Integer) rowData[1] == 0) {

							userNotificationSetting.setEmailStatus(false);

							userStatusMap.put(
									Long.valueOf((Integer) rowData[3]), true);

						} else if (((Integer) rowData[2])
								.equals(notificationsetting.getId())) {
							users.remove(Long.valueOf((Integer) rowData[3]));
						}

						if ((Integer) rowData[2] != notificationsetting.getId()
								&& (rowData[5] != null && (Boolean) rowData[5])) {
							if ((Integer) rowData[1] == -1)
								userNotificationSetting
										.setEmailStatus((Boolean) rowData[5]);
							userNotificationSettingSaveUpdateList
									.add(userNotificationSetting);
						}

					}

				}
			if (users != null & !users.isEmpty()) {
				for (Long userId : users) {

					userNotificationSetting = new UserNotificationSetting();
					userNotificationSetting
							.setNotificationsetting(notificationsetting);
					User user = new User();
					user.setId(userId);
					userNotificationSetting.setUser(user);
					userNotificationSetting.setEmailStatus(userStatusMap
							.get(userId) != null ? userStatusMap.get(userId)
							: true);
					userNotificationSettingSaveUpdateList
							.add(userNotificationSetting);

				}
			}	
			} else {
				if (resultList != null && !resultList.isEmpty()) {

					Iterator<?> itr = resultList.iterator();

					int level = -1;

					while (itr.hasNext()) {

						rowData = (Object[]) itr.next();
						int tmpLevel = -1;
						if (level == -1) {
							tmpLevel = (Integer) rowData[2];
							level = tmpLevel;
						}

						else if (level != tmpLevel)
							return userNotificationSettingSaveUpdateList;

						userNotificationSetting = new UserNotificationSetting();
						Notificationsetting setting = new Notificationsetting();
						setting.setId((Integer) rowData[3]);
						userNotificationSetting.setId((Integer) rowData[5]);
						userNotificationSetting.setNotificationsetting(setting);
						User user = new User();
						user.setId(Long.valueOf((Integer) rowData[4]));
						userNotificationSetting.setUser(user);

						if ((Integer) rowData[1] == 1) {

							return new ArrayList<UserNotificationSetting>();

						} else if ((Integer) rowData[1] == 0) {

							userNotificationSetting.setEmailStatus(true);

						}
						userNotificationSettingSaveUpdateList
								.add(userNotificationSetting);

						tmpLevel = (Integer) rowData[2];

					}

				}

			}

		return userNotificationSettingSaveUpdateList;
	}

	@Override
	public List<AdvanceNotificationSettingOutJsonVO> getAdvanceNotificationSetting(
			AdvanceNotificationSettingInJsonVO advanceNotificationSettingInJsonVO) {
		List<AdvanceNotificationSettingOutJsonVO> notificationCategorySettingVOs = new ArrayList<AdvanceNotificationSettingOutJsonVO>();
		if (advanceNotificationSettingInJsonVO != null) {
			List<Integer> notificationCategoryIdList = new ArrayList<Integer>();
			notificationCategoryIdList.add(advanceNotificationSettingInJsonVO
					.getNotificationCategoryId());
			notificationCategorySettingVOs = userNotificationService
					.getUserNotificationSetting(
							advanceNotificationSettingInJsonVO.getGroupID(),
							notificationCategoryIdList);
		}
		return notificationCategorySettingVOs;
	}

	@Override
	public List<UserListVO> getUserListByCompanyId(long companyId) {
		List<UserListVO> notificationCategorySettingVOs = userNotificationService
				.getUserListByCompanyId(companyId);
		return notificationCategorySettingVOs;
	}

	@Override
	public boolean updateMasterNotificationSetting(
			UpdateAdvanceNotificationSettingVO updateAdvanceNotificationSettingVO) {
		boolean saveStatus = false;
		int notificationCategoryId = 0;
		if (updateAdvanceNotificationSettingVO != null) {
			Long groupId = updateAdvanceNotificationSettingVO.getGroupID();

			if (updateAdvanceNotificationSettingVO.getNotificationCategoryId() > 0) {
				notificationCategoryId = updateAdvanceNotificationSettingVO
						.getNotificationCategoryId();
			} else {
				List<Notificationsetting> notificationsettingList = notificationService
						.getNotificationSettingByGroupIdAndSettingType(groupId,
								BizConstants.IS_MASTER);
				if (notificationsettingList != null
						&& !notificationsettingList.isEmpty()) {
					notificationCategoryId = notificationsettingList.get(0)
							.getNotificationtypeMaster().getId();
				}
			}

			List<Notificationsetting> notificationSettingList = notificationService
					.getNotificationSettingByGroupIdAndSettingTypeId(groupId,
							notificationCategoryId);
			if (notificationSettingList != null
					&& !notificationSettingList.isEmpty()) {
				Notificationsetting notificationsetting = notificationSettingList
						.get(0);
				List<UserNotificationSetting> userNotificationSettingSaveUpdateList = new ArrayList<UserNotificationSetting>();
				List<UserNotificationSetting> userNotificationSettingRemoveList = new ArrayList<UserNotificationSetting>();

				UserNotificationSetting userNotificationSetting = null;
				for (Long userId : updateAdvanceNotificationSettingVO
						.getAddusers()) {
					List<UserNotificationSetting> userNotificationSettingList = userNotificationService
							.duplicateCheckForUserNotificationSettings(userId,
									notificationSettingList.get(0).getId());
					if (userNotificationSettingList != null
							&& !userNotificationSettingList.isEmpty()) {
						userNotificationSetting = userNotificationSettingList
								.get(0);
					} else {
						userNotificationSetting = new UserNotificationSetting();
						userNotificationSetting
								.setNotificationsetting(notificationsetting);
						User user = new User();
						user.setId(userId);
						userNotificationSetting.setUser(user);
					}
					userNotificationSettingSaveUpdateList
							.add(userNotificationSetting);
				}
				if (updateAdvanceNotificationSettingVO.getDeleteusers() != null
						&& !updateAdvanceNotificationSettingVO.getDeleteusers()
								.isEmpty()) {
					userNotificationSettingRemoveList = userNotificationService
							.getUserNotificationSettingBatchByIds(
									updateAdvanceNotificationSettingVO
											.getDeleteusers(),
									notificationCategoryId);
				}
				if (userNotificationSettingSaveUpdateList != null
						&& !userNotificationSettingSaveUpdateList.isEmpty()) {
					notificationsetting
							.setUserNotificationSettings(userNotificationSettingSaveUpdateList);

				}

				if (userNotificationSettingRemoveList != null
						&& !userNotificationSettingRemoveList.isEmpty()) {
					notificationsetting.getUserNotificationSettings()
							.removeAll(userNotificationSettingRemoveList);
				}

				if (notificationsetting != null
						&& notificationsetting.getUserNotificationSettings() != null
						&& !notificationsetting.getUserNotificationSettings()
								.isEmpty()) {
					saveStatus = notificationService
							.saveNotificationSetting(notificationsetting);
				}
			}
		}
		return saveStatus;

	}

	@Override
	public List<AdvanceNotificationSettingOutJsonVO> getMasterNotificationSetting(
			AdvanceNotificationSettingInJsonVO advanceNotificationSettingInJsonVO) {
		List<AdvanceNotificationSettingOutJsonVO> notificationCategorySettingVOs = new ArrayList<AdvanceNotificationSettingOutJsonVO>();
		List<Integer> notificationCategoryIdList = new ArrayList<Integer>();

		if (advanceNotificationSettingInJsonVO != null) {
			if (advanceNotificationSettingInJsonVO.getNotificationCategoryId() > 0) {
				notificationCategoryIdList
						.add(advanceNotificationSettingInJsonVO
								.getNotificationCategoryId());
			} else {
				List<Notificationsetting> notificationsettingList = notificationService
						.getNotificationSettingByGroupIdAndSettingType(
								advanceNotificationSettingInJsonVO.getGroupID(),
								BizConstants.IS_MASTER);
				if (notificationsettingList != null
						&& !notificationsettingList.isEmpty()) {
					notificationCategoryIdList.add(notificationsettingList
							.get(0).getNotificationtypeMaster().getId());
				}
			}
			notificationCategorySettingVOs = userNotificationService
					.getUserNotificationSetting(
							advanceNotificationSettingInJsonVO.getGroupID(),
							notificationCategoryIdList);
		}
		return notificationCategorySettingVOs;
	}
}

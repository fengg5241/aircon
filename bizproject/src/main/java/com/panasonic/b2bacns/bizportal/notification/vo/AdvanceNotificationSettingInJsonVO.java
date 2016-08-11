package com.panasonic.b2bacns.bizportal.notification.vo;

/**
 * @author Narendra.Kumar
 * 
 */
public class AdvanceNotificationSettingInJsonVO {
	private Long groupID;
	private int notificationCategoryId;

	/**
	 * @return the groupID
	 */
	public Long getGroupID() {
		return groupID;
	}

	/**
	 * @param groupID
	 *            the groupID to set
	 */
	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @return the notificationCategoryId
	 */
	public int getNotificationCategoryId() {
		return notificationCategoryId;
	}

	/**
	 * @param notificationCategoryId
	 *            the notificationCategoryId to set
	 */
	public void setNotificationCategoryId(int notificationCategoryId) {
		this.notificationCategoryId = notificationCategoryId;
	}

}

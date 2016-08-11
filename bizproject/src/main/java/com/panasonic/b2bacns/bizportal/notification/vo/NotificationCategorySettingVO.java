/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author Narendra.Kumar
 * 
 */
@JsonAutoDetect
public class NotificationCategorySettingVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer notificationCategoryId;
	private Long groupID;
	private String notification;

	/**
	 * @return the notificationCategoryId
	 */
	public Integer getNotificationCategoryId() {
		return notificationCategoryId;
	}

	/**
	 * @param notificationCategoryId
	 *            the notificationCategoryId to set
	 */
	public void setNotificationCategoryId(Integer notificationCategoryId) {
		this.notificationCategoryId = notificationCategoryId;
	}

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
	 * @return the notification
	 */
	public String getNotification() {
		return notification;
	}

	/**
	 * @param notification
	 *            the notification to set
	 */
	public void setNotification(String notification) {
		this.notification = notification;
	}

}

package com.panasonic.b2bacns.bizportal.notification.vo;

import java.util.List;

public class NotificationRequestDownloadVO {

	private List<Long> notificationIds;
	private String fileType;
	private String addCustName;

	/**
	 * @return the notificationIds
	 */
	public List<Long> getNotificationIds() {
		return notificationIds;
	}

	/**
	 * @param notificationIds
	 *            the notificationIds to set
	 */
	public void setNotificationIds(List<Long> notificationIds) {
		this.notificationIds = notificationIds;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the addCustName
	 */
	public String getAddCustName() {
		return addCustName;
	}

	/**
	 * @param addCustName
	 *            the addCustName to set
	 */
	public void setAddCustName(String addCustName) {
		this.addCustName = addCustName;
	}

}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.vo;

import java.util.List;

/**
 * @author Narendra.Kumar
 * 
 */
public class UpdateAdvanceNotificationSettingVO {
	private Integer notificationCategoryId;
	private Long groupID;
	private List<Long> addusers;
	private List<Long> deleteusers;

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
	 * @return the addusers
	 */
	public List<Long> getAddusers() {
		return addusers;
	}

	/**
	 * @param addusers
	 *            the addusers to set
	 */
	public void setAddusers(List<Long> addusers) {
		this.addusers = addusers;
	}

	/**
	 * @return the deleteusers
	 */
	public List<Long> getDeleteusers() {
		return deleteusers;
	}

	/**
	 * @param deleteusers
	 *            the deleteusers to set
	 */
	public void setDeleteusers(List<Long> deleteusers) {
		this.deleteusers = deleteusers;
	}

}

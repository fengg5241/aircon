/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.vo;

/**
 * @author Narendra.Kumar
 * 
 */
public class EfficiencyRatingRequestVO {
	private String groupId;
	private Long userId;
	private Integer grouplevel;
	private String userTimeZone;
	private String fileType;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the grouplevel
	 */
	public Integer getGrouplevel() {
		return grouplevel;
	}

	/**
	 * @param grouplevel
	 *            the grouplevel to set
	 */
	public void setGrouplevel(Integer grouplevel) {
		this.grouplevel = grouplevel;
	}

	/**
	 * @return the userTimeZone
	 */
	public String getUserTimeZone() {
		return userTimeZone;
	}

	/**
	 * @param userTimeZone
	 *            the userTimeZone to set
	 */
	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
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

}

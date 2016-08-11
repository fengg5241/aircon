package com.panasonic.b2bacns.bizportal.dashboard.vo;

/**
 * This class is used for ODUInfo data
 * 
 * @author akansha
 *
 */
public class ODUInfo {

	private Integer totalODU;

	private Integer totalInactiveODU;

	private Integer notificationCountOfODU;

	/**
	 * @return the totalODU
	 */
	public Integer getTotalODU() {
		return totalODU;
	}

	/**
	 * @param totalODU
	 *            the totalODU to set
	 */
	public void setTotalODU(Integer totalODU) {
		this.totalODU = totalODU;
	}

	/**
	 * @return the totalInactiveODU
	 */
	public Integer getTotalInactiveODU() {
		return totalInactiveODU;
	}

	/**
	 * @param totalInactiveODU
	 *            the totalInactiveODU to set
	 */
	public void setTotalInactiveODU(Integer totalInactiveODU) {
		this.totalInactiveODU = totalInactiveODU;
	}

	/**
	 * @return the notificationCountOfODU
	 */
	public Integer getNotificationCountOfODU() {
		return notificationCountOfODU;
	}

	/**
	 * @param notificationCountOfODU
	 *            the notificationCountOfODU to set
	 */
	public void setNotificationCountOfODU(Integer notificationCountOfODU) {
		this.notificationCountOfODU = notificationCountOfODU;
	}

}

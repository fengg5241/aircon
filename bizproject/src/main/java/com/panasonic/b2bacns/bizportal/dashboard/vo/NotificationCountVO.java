package com.panasonic.b2bacns.bizportal.dashboard.vo;

public class NotificationCountVO {

	Long criticalCount;
	Long noncriticalCount;

	/**
	 * @return the criticalCount
	 */
	public Long getCriticalCount() {
		return criticalCount;
	}

	/**
	 * @param criticalCount
	 *            the criticalCount to set
	 */
	public void setCriticalCount(Long criticalCount) {
		this.criticalCount = criticalCount;
	}

	/**
	 * @return the noncriticalCount
	 */
	public Long getNoncriticalCount() {
		return noncriticalCount;
	}

	/**
	 * @param noncriticalCount
	 *            the noncriticalCount to set
	 */
	public void setNoncriticalCount(Long noncriticalCount) {
		this.noncriticalCount = noncriticalCount;
	}

}

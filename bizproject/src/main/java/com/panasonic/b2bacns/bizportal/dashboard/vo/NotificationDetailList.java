package com.panasonic.b2bacns.bizportal.dashboard.vo;

import java.util.Set;

import com.panasonic.b2bacns.bizportal.acconfig.vo.RefrigerantSVG;

public class NotificationDetailList {

	Set<NotificationDetailsVO> notificationList;
	Set<RefrigerantSVG> refrigerantList;

	/**
	 * @return the notificationList
	 */
	public Set<NotificationDetailsVO> getNotificationList() {
		return notificationList;
	}

	/**
	 * @param notificationList
	 *            the notificationList to set
	 */
	public void setNotificationList(Set<NotificationDetailsVO> notificationList) {
		this.notificationList = notificationList;
	}

	/**
	 * @return the refrigerantList
	 */
	public Set<RefrigerantSVG> getRefrigerantList() {
		return refrigerantList;
	}

	/**
	 * @param refrigerantList
	 *            the refrigerantList to set
	 */
	public void setRefrigerantList(Set<RefrigerantSVG> refrigerantList) {
		this.refrigerantList = refrigerantList;
	}

}

package com.panasonic.b2bacns.bizportal.notification.vo;

public class NotificationOverViewVO {

	private Long alarmcount;
	private String supplyGroupName;
	private String pathName;
	private String companyName;

	public Long getAlarmcount() {
		return alarmcount;
	}

	public void setAlarmcount(Long alarmcount) {
		this.alarmcount = alarmcount;
	}

	public String getSupplyGroupName() {
		return supplyGroupName;
	}

	public void setSupplyGroupName(String supplyGroupName) {
		this.supplyGroupName = supplyGroupName;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

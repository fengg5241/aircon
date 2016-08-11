package com.panasonic.b2bacns.bizportal.notification.vo;

public class NotificationOverviewAlarm {
	
	
	private String groupName;
	private String alarmStatus;
	private int alarmCount =0;
	
	
	
	public NotificationOverviewAlarm(String groupName, String alarmStatus,
			int alarmCount) {
		super();
		this.groupName = groupName;
		this.alarmStatus = alarmStatus;
		this.alarmCount = alarmCount;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public int getAlarmCount() {
		return alarmCount;
	}
	public void setAlarmCount(int alarmCount) {
		this.alarmCount = alarmCount;
	}
	
	
	

}

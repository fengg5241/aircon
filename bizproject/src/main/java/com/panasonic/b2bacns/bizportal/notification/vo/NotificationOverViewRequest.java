package com.panasonic.b2bacns.bizportal.notification.vo;

import java.util.List;

public class NotificationOverViewRequest {

	private List<Long> groupIds;
	private String startDate;
	private String endDate;
	private String alarmType;
	private String period;
	private int grouplevel = 0;
	private String fileType;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	private String timeZone;
	
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	

	public int getGrouplevel() {
		return grouplevel;
	}

	public void setGrouplevel(int grouplevel) {
		this.grouplevel = grouplevel;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public List<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
}

package com.panasonic.b2bacns.bizportal.device.vo;

public class IndoorUnitLogVO {

	private String power;
	private String temperature;
	private String mode;
	private String unitAddress;
	private String flapMode;
	private String fanSpeed;
	private String ecoNavi;
	private String lastFilterCleaning;
	private String criticalAlarmCount;
	private String nonCriticalAlarmCount;

	public String getLastFilterCleaning() {
		return lastFilterCleaning;
	}

	public void setLastFilterCleaning(String lastFilterCleaning) {
		this.lastFilterCleaning = lastFilterCleaning;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFlapMode() {
		return flapMode;
	}

	public void setFlapMode(String flapMode) {
		this.flapMode = flapMode;
	}

	public String getFanSpeed() {
		return fanSpeed;
	}

	public void setFanSpeed(String fanSpeed) {
		this.fanSpeed = fanSpeed;
	}

	public String getEcoNavi() {
		return ecoNavi;
	}

	public void setEcoNavi(String ecoNavi) {
		this.ecoNavi = ecoNavi;
	}

	public String getCriticalAlarmCount() {
		return criticalAlarmCount;
	}

	public void setCriticalAlarmCount(String criticalAlarmCount) {
		this.criticalAlarmCount = criticalAlarmCount;
	}

	public String getNonCriticalAlarmCount() {
		return nonCriticalAlarmCount;
	}

	public void setNonCriticalAlarmCount(String nonCriticalAlarmCount) {
		this.nonCriticalAlarmCount = nonCriticalAlarmCount;
	}

}

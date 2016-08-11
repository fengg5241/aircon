package com.panasonic.b2bacns.bizportal.device.vo;

/**
 * 
 * @author shobhit.singh
 * 
 */
public class ACIndoorUnitVO {

	private String power;
	private float temperature;
	private String mode;
	private String fanSpeed;
	private String windDirection;
	private boolean powerProhibition;
	private boolean tempProhibition;
	private boolean modeProhibition;
	private boolean fanSpeedProhibition;
	private boolean windDirectionProhibition;
	private String energySaving;

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFanSpeed() {
		return fanSpeed;
	}

	public void setFanSpeed(String fanSpeed) {
		this.fanSpeed = fanSpeed;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public boolean isPowerProhibition() {
		return powerProhibition;
	}

	public void setPowerProhibition(boolean powerProhibition) {
		this.powerProhibition = powerProhibition;
	}

	public boolean isTempProhibition() {
		return tempProhibition;
	}

	public void setTempProhibition(boolean tempProhibition) {
		this.tempProhibition = tempProhibition;
	}

	public boolean isModeProhibition() {
		return modeProhibition;
	}

	public void setModeProhibition(boolean modeProhibition) {
		this.modeProhibition = modeProhibition;
	}

	public boolean isFanSpeedProhibition() {
		return fanSpeedProhibition;
	}

	public void setFanSpeedProhibition(boolean fanSpeedProhibition) {
		this.fanSpeedProhibition = fanSpeedProhibition;
	}

	public boolean isWindDirectionProhibition() {
		return windDirectionProhibition;
	}

	public void setWindDirectionProhibition(boolean windDirectionProhibition) {
		this.windDirectionProhibition = windDirectionProhibition;
	}

	public String getEnergySaving() {
		return energySaving;
	}

	public void setEnergySaving(String energySaving) {
		this.energySaving = energySaving;
	}

}

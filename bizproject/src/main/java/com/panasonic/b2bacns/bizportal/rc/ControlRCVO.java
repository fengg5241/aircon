/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;


/**
 * 
 * @author shobhit.singh
 * 
 */
public class ControlRCVO {

	private String powerStatus;
	private Double temperature;
	private String mode;
	private String fanSpeed;
	private String windDirection;
	private String powerProhibition;
	private String tempProhibition;
	private String modeProhibition;
	private String fanSpeedProhibition;
	private String windDirectionProhibition;
	private String energySaving;

	public String getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature
	 *            the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the fanSpeed
	 */
	public String getFanSpeed() {
		return fanSpeed;
	}

	/**
	 * @param fanSpeed
	 *            the fanSpeed to set
	 */
	public void setFanSpeed(String fanSpeed) {
		this.fanSpeed = fanSpeed;
	}

	/**
	 * @return the windDirection
	 */
	public String getWindDirection() {
		return windDirection;
	}

	/**
	 * @param windDirection
	 *            the windDirection to set
	 */
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String isPowerProhibition() {
		return powerProhibition;
	}

	public void setPowerProhibition(String powerProhibition) {
		this.powerProhibition = powerProhibition;
	}

	public String isTempProhibition() {
		return tempProhibition;
	}

	public void setTempProhibition(String tempProhibition) {
		this.tempProhibition = tempProhibition;
	}

	public String isModeProhibition() {
		return modeProhibition;
	}

	public void setModeProhibition(String modeProhibition) {
		this.modeProhibition = modeProhibition;
	}

	public String isFanSpeedProhibition() {
		return fanSpeedProhibition;
	}

	public void setFanSpeedProhibition(String fanSpeedProhibition) {
		this.fanSpeedProhibition = fanSpeedProhibition;
	}

	public String isWindDirectionProhibition() {
		return windDirectionProhibition;
	}

	public void setWindDirectionProhibition(String windDirectionProhibition) {
		this.windDirectionProhibition = windDirectionProhibition;
	}

	public String getEnergySaving() {
		return energySaving;
	}

	public void setEnergySaving(String energySaving) {
		this.energySaving = energySaving;
	}

}

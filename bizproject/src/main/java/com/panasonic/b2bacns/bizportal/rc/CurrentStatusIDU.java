/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

/**
 * @author simanchal.patra
 *
 */
public class CurrentStatusIDU {

	private Long ID;

	private String power;

	private String acMode;

	private boolean allowPower;

	private boolean allowMode;

	private boolean allowTemperature;

	private boolean allowFanSpeed;

	private boolean allowFlap;

	private boolean allowEnergySaving;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getAcMode() {
		return acMode;
	}

	public void setAcMode(String acMode) {
		this.acMode = acMode;
	}

	public boolean isAllowPower() {
		return allowPower;
	}

	public void setAllowPower(boolean allowPower) {
		this.allowPower = allowPower;
	}

	public boolean isAllowMode() {
		return allowMode;
	}

	public void setAllowMode(boolean allowMode) {
		this.allowMode = allowMode;
	}

	public boolean isAllowTemperature() {
		return allowTemperature;
	}

	public void setAllowTemperature(boolean allowTemperature) {
		this.allowTemperature = allowTemperature;
	}

	public boolean isAllowFanSpeed() {
		return allowFanSpeed;
	}

	public void setAllowFanSpeed(boolean allowFanSpeed) {
		this.allowFanSpeed = allowFanSpeed;
	}

	public boolean isAllowFlap() {
		return allowFlap;
	}

	public void setAllowFlap(boolean allowFlap) {
		this.allowFlap = allowFlap;
	}

	public boolean isAllowEnergySaving() {
		return allowEnergySaving;
	}

	public void setAllowEnergySaving(boolean allowEnergySaving) {
		this.allowEnergySaving = allowEnergySaving;
	}

}

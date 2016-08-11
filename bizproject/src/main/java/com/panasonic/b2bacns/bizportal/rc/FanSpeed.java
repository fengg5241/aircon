/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

/**
 * @author simanchal.patra
 * 
 */
public enum FanSpeed {
	//change by jiawei
	HIGH("B17h"), MEDIUM("B17m"), LOW("B17l"), AUTO("B17at");

	String propertyID;

	FanSpeed(String propertyID) {
		this.propertyID = propertyID;
	}

	public static FanSpeed getPropertyID(String fanSpeed) {
		FanSpeed result = null;
		//add by shanf ,always compare with capital
		String fanSpeedStr = null;
		if (fanSpeed != null) {
			fanSpeedStr = fanSpeed.toUpperCase();
		}
		for (FanSpeed fs : values()) {
			if (fs.name().equals(fanSpeedStr)) {
				result = fs;
			}
		}
		return result;
	}

	public String getPropertyID() {
		return this.propertyID;
	}
}

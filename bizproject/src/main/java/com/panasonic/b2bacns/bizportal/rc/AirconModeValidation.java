/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

/**
 * @author simanchal.patra
 * 
 */
public enum AirconModeValidation {
	//change by shanf
	COOL("B16c"), DRY("B16d"), HEAT("B16h"), AUTO("B16a"), FAN("B16f");

	String propertyID;

	AirconModeValidation(String propertyID) {
		this.propertyID = propertyID;
	}

	public static AirconModeValidation getPropertyID(String acModeName) {
		AirconModeValidation result = null;
		//add by shanf ,always compare with capital
		String acModeNameStr = null;
		if (acModeName != null) {
			acModeNameStr = acModeName.toUpperCase();
		}
		for (AirconModeValidation acMode : values()) {
			if (acMode.name().equals(acModeNameStr)) {
				result = acMode;
			}
		}
		return result;
	}

	public String getPropertyID() {
		return this.propertyID;
	}

}

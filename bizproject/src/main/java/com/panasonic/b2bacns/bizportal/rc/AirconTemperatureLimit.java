/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

/**
 * @author simanchal.patra
 * 
 */
public enum AirconTemperatureLimit {
	COOL_HI("B15c_u"), COOL_LOW("B15c_l"), HOT_HI("B15h_u"), HOT_LOW("B15h_l"), DRY_HI(
			"B15d_u"), DRY_LOW("B15d_l"), AUTO_HI("B15a_u"), AUTO_LOW("B15a_l");

	String propertyID;

	AirconTemperatureLimit(String propertyID) {
		this.propertyID = propertyID;
	}

	public static AirconTemperatureLimit getPropertyID(
			String airconTemperatureLimit) {
		AirconTemperatureLimit result = null;
		for (AirconTemperatureLimit airconTemperature : values()) {
			if (airconTemperature.name().equals(airconTemperatureLimit)) {
				result = airconTemperature;
			}
		}
		return result;
	}

	public String getPropertyID() {
		return this.propertyID;
	}
}

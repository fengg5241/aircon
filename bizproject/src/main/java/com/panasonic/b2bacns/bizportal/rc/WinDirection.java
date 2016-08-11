/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

/**
 * @author Narendra.Kumar
 * 
 */
public enum WinDirection {
	F1, F2, F3, F4, F5, STOP, SWING;
	String propertyId;

	public static WinDirection getPropertyID(String winDirection) {
		WinDirection result = null;
		//add by shanf ,always compare with capital
		String winDirectionStr = null;
		if (winDirection != null) {
			winDirectionStr = winDirection.toUpperCase();
		}
		for (WinDirection fs : values()) {
			if (fs.name().equals(winDirectionStr)) {
				result = fs;
			}
		}
		return result;
	}

	public String getPropertyID() {
		return this.propertyId;
	}
}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

/**
 * @author Narendra.Kumar
 * 
 */
public enum MonthEnum {
	JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(
			8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);
	private final int levelCode;

	MonthEnum(int levelCode) {
		this.levelCode = levelCode;
	}

	public int getMonth() {
		return this.levelCode;
	}
}

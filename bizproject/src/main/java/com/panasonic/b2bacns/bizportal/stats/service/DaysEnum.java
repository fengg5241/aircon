package com.panasonic.b2bacns.bizportal.stats.service;

public enum DaysEnum {
	SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(
			7);
	private final int levelCode;

	DaysEnum(int levelCode) {
		this.levelCode = levelCode;
	}

	public int getDay() {
		return this.levelCode;
	}
}

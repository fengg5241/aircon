package com.panasonic.b2bacns.bizportal.stats.service;

public enum WeekofMonthEnum {
	wk1(1), wk2(2), wk3(3), wk4(4), wk5(5);
	private final int levelCode;

	WeekofMonthEnum(int levelCode) {
		this.levelCode = levelCode;
	}

	public int getWeekOfMonth() {
		return this.levelCode;
	}
}

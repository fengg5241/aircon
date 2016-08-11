package com.panasonic.b2bacns.bizportal.rc;

public enum CurrentAirconMode {
	UNDECIDED(0), HEATING(1), COOLING(2), FAN(3), DRY(4), AUTO_HEATING(5), AUTO_COOLING(
			6), AUTO_UNDECIDED(7);

	private int value;

	CurrentAirconMode(int value) {
		this.value = value;
	}

	public static CurrentAirconMode getPropertyID(int value) {
		CurrentAirconMode result = null;
		for (CurrentAirconMode currentAirconOperation : values()) {
			if (currentAirconOperation.value == value) {
				result = currentAirconOperation;
			}

		}
		return result;
	}

	public int getValue() {
		return this.value;
	}

}

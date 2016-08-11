/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.sql.Timestamp;

/**
 * @author akansha
 *
 */
public class GasHeatDataVO {

	private String centralAddress;

	private double totalEnergyConsumed;

	private Timestamp time;

	private String week;

	private String month;

	private String year;

	/**
	 * @return the centralAddress
	 */
	public String getCentralAddress() {
		return centralAddress;
	}

	/**
	 * @param centralAddress
	 *            the centralAddress to set
	 */
	public void setCentralAddress(String centralAddress) {
		this.centralAddress = centralAddress;
	}

	/**
	 * @return the totalEnergyConsumed
	 */
	public double getTotalEnergyConsumed() {
		return totalEnergyConsumed;
	}

	/**
	 * @param totalEnergyConsumed
	 *            the totalEnergyConsumed to set
	 */
	public void setTotalEnergyConsumed(double totalEnergyConsumed) {
		this.totalEnergyConsumed = totalEnergyConsumed;
	}

	/**
	 * @return the time
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * @param week
	 *            the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

}

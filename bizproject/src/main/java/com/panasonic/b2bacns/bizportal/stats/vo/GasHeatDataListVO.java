/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.util.List;

/**
 * @author akansha
 *
 */
public class GasHeatDataListVO {

	private List<String> listofCentralAddress;

	private List<Double> listofTotalEnergyConsumed;

	private List<String> listofTime;

	private List<String> listofWeek;

	private List<String> listofMonth;

	private List<String> listofYear;

	/**
	 * @return the listofCentralAddress
	 */
	public List<String> getListofCentralAddress() {
		return listofCentralAddress;
	}

	/**
	 * @param listofCentralAddress
	 *            the listofCentralAddress to set
	 */
	public void setListofCentralAddress(List<String> listofCentralAddress) {
		this.listofCentralAddress = listofCentralAddress;
	}

	/**
	 * @return the listofTotalEnergyConsumed
	 */
	public List<Double> getListofTotalEnergyConsumed() {
		return listofTotalEnergyConsumed;
	}

	/**
	 * @param listofTotalEnergyConsumed
	 *            the listofTotalEnergyConsumed to set
	 */
	public void setListofTotalEnergyConsumed(
			List<Double> listofTotalEnergyConsumed) {
		this.listofTotalEnergyConsumed = listofTotalEnergyConsumed;
	}

	/**
	 * @return the listofTime
	 */
	public List<String> getListofTime() {
		return listofTime;
	}

	/**
	 * @param listofTime
	 *            the listofTime to set
	 */
	public void setListofTime(List<String> listofTime) {
		this.listofTime = listofTime;
	}

	/**
	 * @return the listofWeek
	 */
	public List<String> getListofWeek() {
		return listofWeek;
	}

	/**
	 * @param listofWeek
	 *            the listofWeek to set
	 */
	public void setListofWeek(List<String> listofWeek) {
		this.listofWeek = listofWeek;
	}

	/**
	 * @return the listofMonth
	 */
	public List<String> getListofMonth() {
		return listofMonth;
	}

	/**
	 * @param listofMonth
	 *            the listofMonth to set
	 */
	public void setListofMonth(List<String> listofMonth) {
		this.listofMonth = listofMonth;
	}

	/**
	 * @return the listofYear
	 */
	public List<String> getListofYear() {
		return listofYear;
	}

	/**
	 * @param listofYear
	 *            the listofYear to set
	 */
	public void setListofYear(List<String> listofYear) {
		this.listofYear = listofYear;
	}

}

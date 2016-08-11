/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.util.List;

/**
 * @author akansha
 * 
 */
public class EnergyConsumptionResponseVO {

	private List<String> dates;
	private List<Double> total_consumption;
	private List<Double> average_consumption;
	
	

	/**
	 * @return the dates
	 */
	public List<String> getDates() {
		return dates;
	}

	/**
	 * @param dates
	 *            the dates to set
	 */
	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	/**
	 * @return the total_consumption
	 */
	public List<Double> getTotal_consumption() {
		return total_consumption;
	}

	/**
	 * @param total_consumption
	 *            the total_consumption to set
	 */
	public void setTotal_consumption(List<Double> total_consumption) {
		this.total_consumption = total_consumption;
	}

	/**
	 * @return the average_consumption
	 */
	public List<Double> getAverage_consumption() {
		return average_consumption;
	}

	/**
	 * @param average_consumption
	 *            the average_consumption to set
	 */
	public void setAverage_consumption(List<Double> average_consumption) {
		this.average_consumption = average_consumption;
	}

}

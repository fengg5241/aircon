/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author diksha.rattan
 * 
 */
public class StatsResponseVO {

	List<String> categories = new ArrayList<String>();

	List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
	
	@JsonInclude(Include.NON_NULL)
	Double averageTotalConsumption ;

	@JsonInclude(Include.NON_NULL)
	Double total;

	@JsonInclude(Include.NON_NULL)
	Double totalCapacityHeat;

	@JsonInclude(Include.NON_NULL)
	Double totalCapacityCool;

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * @return the series
	 */
	public List<Map<String, Object>> getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(List<Map<String, Object>> series) {
		this.series = series;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the totalCapacityHeat
	 */
	public Double getTotalCapacityHeat() {
		return totalCapacityHeat;
	}

	/**
	 * @param totalCapacityHeat
	 *            the totalCapacityHeat to set
	 */
	public void setTotalCapacityHeat(Double totalCapacityHeat) {
		this.totalCapacityHeat = totalCapacityHeat;
	}

	/**
	 * @return the totalCapacityCool
	 */
	public Double getTotalCapacityCool() {
		return totalCapacityCool;
	}

	/**
	 * @param totalCapacityCool
	 *            the totalCapacityCool to set
	 */
	public void setTotalCapacityCool(Double totalCapacityCool) {
		this.totalCapacityCool = totalCapacityCool;
	}

	/**
	 * @return the averageTotalConsumption
	 */
	public Double getAverageTotalConsumption() {
		return averageTotalConsumption;
	}

	/**
	 * @param averageTotalConsumption the averageTotalConsumption to set
	 */
	public void setAverageTotalConsumption(Double averageTotalConsumption) {
		this.averageTotalConsumption = averageTotalConsumption;
	}
	
	

}

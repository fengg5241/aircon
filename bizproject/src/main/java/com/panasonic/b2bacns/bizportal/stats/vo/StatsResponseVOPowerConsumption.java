/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diksha.rattan
 * 
 */
public class StatsResponseVOPowerConsumption {

	List<String> categories = new ArrayList<String>();

	List<Object> series = new ArrayList<Object>();

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
	public List<Object> getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(List<Object> series) {
		this.series = series;
	}

}

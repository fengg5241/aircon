/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

/**
 * @author akansha
 *
 */
public interface ChartTypeService {

	/**
	 * This method is used to fetch the type of chart whether its daily, weekly,
	 * monthly or yearly by calculating the total number of days of which chart
	 * is to be shown
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param deviceIDArray
	 * @param chartType
	 * @return
	 */
	String getChartTypeForDevices(String fromDate, String toDate,
			Long[] deviceIDArray, String chartType);

}

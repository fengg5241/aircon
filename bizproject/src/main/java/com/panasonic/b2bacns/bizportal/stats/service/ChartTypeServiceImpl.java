/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 *
 */
@Service
public class ChartTypeServiceImpl implements ChartTypeService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.service.ChartTypeService#
	 * getChartTypeForDevices(java.lang.String, java.lang.String,
	 * java.lang.Long[], java.lang.String)
	 */
	@Override
	public String getChartTypeForDevices(String fromDate, String toDate,
			Long[] deviceIDArray, String chartType) {

		Long calendarDays = CommonUtil.getDifferenceInDays(fromDate, toDate);

		String chartByType = null;

		switch (chartType) {
		case BizConstants.COMPARE_WORKING_HOURS:

			chartByType = getChartType(calendarDays);

			break;
		case BizConstants.COMPARE_ROOM_TEMPERATURE:

			chartByType = getChartType(calendarDays);

			break;
		case BizConstants.COMPARE_ENERGY_CONSUMPTION:

			chartByType = getChartType(calendarDays);

			break;
		case BizConstants.COMPARE_CAPACITY:

			chartByType = getChartType(calendarDays);

			break;
		case BizConstants.COMPARE_EFFICIENCY:

			chartByType = getChartType(calendarDays);

			break;
		case BizConstants.GAS_HEAT_GRAPH:

			chartByType = getChartType(calendarDays);

			break;

		default:
			break;
		}

		return chartByType;
	}

	/**
	 * Get chartType based on number of days calculated
	 * 
	 * @param calendarDays
	 * @return
	 */
	private String getChartType(Long calendarDays) {

		if (calendarDays <= 18) {

			return BizConstants.SHOW_CHART_BY_DAYS;

		} else if (calendarDays > 18 && calendarDays <= 63) {

			return BizConstants.SHOW_CHART_BY_WEEKS;

		} else if (calendarDays > 63 && calendarDays <= 360) {

			return BizConstants.SHOW_CHART_BY_MONTHS;

		} else if (calendarDays > 360) {

			return BizConstants.SHOW_CHART_BY_YEARS;

		}
		return null;
	}

}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 * 
 */
@Component
public class ReportUtil {

	/**
	 * Generates title for report
	 * 
	 * @param statsName
	 * @param statsType
	 * @return
	 */
	public static String getReportTitle(String statsName, String statsType) {

		String title = BizConstants.EMPTY_STRING;

		switch (statsName) {
		case BizConstants.POWER_CONSUMPTION:

			title = "Power Consumption";
			break;

		case BizConstants.EFFICIENCY:

			title = "Efficiency";
			break;

		case BizConstants.CAPACITY:

			title = "Capacity";
			break;

		case BizConstants.DIFF_TEMPERATURE:

			title = "Indoor Temperature";
			break;

		case BizConstants.WORKING_HOURS:

			title = "Working Hours ";
			break;

		default:
			break;
		}

		if (StringUtils.equalsIgnoreCase(statsType,
				BizConstants.STATISTICS_ACCUMULATED)) {

			title = title + " Breakdown (Accumulated)";

		} else if (StringUtils.equalsIgnoreCase(statsType,
				BizConstants.STATISTICS_CHRONOLOGICAL)) {

			title = title + " Trend (Chronology)";
		}

		return title;

	}

	/**
	 * Generates start date for the period
	 * 
	 * @param requestVO
	 * @return
	 */
	public static String getDateRange(StatsRequestVO requestVO) {

		Calendar calendar = CommonUtil.setUserTimeZone(Calendar.getInstance(),
				requestVO.getUserTimeZone());

		String dateRange = BizConstants.EMPTY_STRING;

		String startDate = BizConstants.EMPTY_STRING;

		String endDate = CommonUtil.dateToString(calendar.getTime(),
				BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

		switch (requestVO.getPeriod()) {
		case BizConstants.PERIOD_THISYEAR:

			calendar.set(Calendar.DAY_OF_YEAR, 1);

			startDate = CommonUtil.dateToString(calendar.getTime(),
					BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

			break;

		case BizConstants.PERIOD_THISMONTH:

			calendar.set(Calendar.DAY_OF_MONTH, 1);

			startDate = CommonUtil.dateToString(calendar.getTime(),
					BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

			break;

		case BizConstants.PERIOD_THISWEEK:

			calendar = CommonUtil.getCalendarWeekFromMonday(
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.WEEK_OF_YEAR));

			startDate = CommonUtil.dateToString(calendar.getTime(),
					BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

			break;

		case BizConstants.PERIOD_TODAY:
		case BizConstants.RANGE_DAY:

			Calendar endCal = null;

			Calendar startCal = null;

			Calendar currentCal = null;

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.RANGE_DAY)) {

				endCal = Calendar.getInstance();
				endCal.setTime(CommonUtil.stringToDate(requestVO.getEndDate(),
						BizConstants.DATE_FORMAT_YYYYMMDD));

				startCal = Calendar.getInstance();
				startCal.setTime(CommonUtil.stringToDate(
						requestVO.getStartDate(),
						BizConstants.DATE_FORMAT_YYYYMMDD));

				currentCal = CommonUtil.setUserTimeZone(Calendar.getInstance(),
						requestVO.getUserTimeZone());
			}

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.RANGE_DAY)) {

				if ((CommonUtil
						.getCalendarWithDateFormatWithoutTime(endCal)
						.compareTo(
								CommonUtil
										.getCalendarWithDateFormatWithoutTime(currentCal)) == -1)) {

					endCal.set(Calendar.HOUR_OF_DAY, 23);
					endCal.set(Calendar.MINUTE, 0);

					endDate = CommonUtil.dateToString(endCal.getTime(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD);

					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);

					startDate = CommonUtil.dateToString(startCal.getTime(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD);
				} else {

					endDate = CommonUtil.dateToString(calendar.getTime(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD);

					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);

					startDate = CommonUtil.dateToString(calendar.getTime(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD);

				}

			} else {

				endDate = CommonUtil.dateToString(calendar.getTime(),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD);

				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);

				startDate = CommonUtil.dateToString(calendar.getTime(),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD);
			}

			break;

		case BizConstants.PERIOD_24HOURS:

			Calendar endCal24Hour = Calendar.getInstance();
			endCal24Hour.setTime(CommonUtil.stringToDate(
					requestVO.getEndDate(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

			endCal24Hour.set(Calendar.MINUTE, 0);
			endCal24Hour.set(Calendar.SECOND, 0);
			
			endCal24Hour.getTime();

			Calendar startCal24Hour = Calendar.getInstance();
			startCal24Hour.setTime(CommonUtil.stringToDate(
					requestVO.getStartDate(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

			startCal24Hour.set(Calendar.MINUTE, 0);
			startCal24Hour.set(Calendar.SECOND, 0);
			
			startCal24Hour.getTime();

			startDate = CommonUtil.dateToString(startCal24Hour.getTime(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD);

			endDate = CommonUtil.dateToString(endCal24Hour.getTime(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD);

			break;

		default:

			startDate = CommonUtil.dateToString(
					CommonUtil.stringToDate(requestVO.getStartDate(),
							BizConstants.DATE_FORMAT_YYYYMMDD),
					BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

			endDate = CommonUtil.dateToString(CommonUtil.stringToDate(
					requestVO.getEndDate(), BizConstants.DATE_FORMAT_YYYYMMDD),
					BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

			break;
		}

		dateRange = startDate + " to " + endDate;

		return dateRange;

	}
}

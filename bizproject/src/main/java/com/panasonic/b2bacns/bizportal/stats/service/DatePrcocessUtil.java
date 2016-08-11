/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Narendra.Kumar
 * 
 */
public class DatePrcocessUtil {

	/**
	 * This method is used to populate processDateMap which contains keys for x
	 * axis and y axis in the case of chronological for all the charts as per
	 * selected period
	 * 
	 * @param requestVO
	 * @return
	 */

	public static Map<Integer, TreeMap<Integer, Object>> getProcessedDateMap(
			StatsRequestVO requestVO) {

		Map<Integer, TreeMap<Integer, Object>> prcessedMap = new TreeMap<Integer, TreeMap<Integer, Object>>();

		String startRange1 = null;
		String endRange1 = null;
		String startRange2 = null;
		String endRange2 = null;
		int year1 = 0;
		int year2 = 0;
		String startResidualDate1 = null;
		String endResidualDate1 = null;
		String startResidualDate2 = null;
		String endResidualDate2 = null;
		Boolean isResidual1Exists = false;

		if (requestVO.getPeriodStrategyMap().get(BizConstants.KEY_START_RANGE1) != null)
			startRange1 = String.valueOf(requestVO.getPeriodStrategyMap()
					.get(BizConstants.KEY_START_RANGE1).toString());
		if (requestVO.getPeriodStrategyMap().get(BizConstants.KEY_END_RANGE1) != null)
			endRange1 = String.valueOf(requestVO.getPeriodStrategyMap().get(
					BizConstants.KEY_END_RANGE1));
		if (requestVO.getPeriodStrategyMap().get(BizConstants.KEY_START_RANGE2) != null)
			startRange2 = String.valueOf(requestVO.getPeriodStrategyMap().get(
					BizConstants.KEY_START_RANGE2));
		if (requestVO.getPeriodStrategyMap().get(BizConstants.KEY_END_RANGE2) != null)
			endRange2 = String.valueOf(requestVO.getPeriodStrategyMap().get(
					BizConstants.KEY_END_RANGE2));
		if (requestVO.getPeriodStrategyMap().get(
				BizConstants.KEY_START_RESIDUAL_DATE1) != null)
			startResidualDate1 = String.valueOf(requestVO
					.getPeriodStrategyMap().get(
							BizConstants.KEY_START_RESIDUAL_DATE1));
		if (requestVO.getPeriodStrategyMap().get(
				BizConstants.KEY_END_RESIDUAL_DATE1) != null)
			endResidualDate1 = String.valueOf(requestVO.getPeriodStrategyMap()
					.get(BizConstants.KEY_END_RESIDUAL_DATE1));
		if (requestVO.getPeriodStrategyMap().get(
				BizConstants.KEY_START_RESIDUAL_DATE2) != null)
			startResidualDate2 = String.valueOf(requestVO
					.getPeriodStrategyMap().get(
							BizConstants.KEY_START_RESIDUAL_DATE2));
		if (requestVO.getPeriodStrategyMap().get(
				BizConstants.KEY_END_RESIDUAL_DATE2) != null)
			endResidualDate2 = String.valueOf(requestVO.getPeriodStrategyMap()
					.get(BizConstants.KEY_END_RESIDUAL_DATE2));

		if (requestVO.getPeriodStrategyMap().get(BizConstants.KEY_YEAR1) != null)
			year1 = Integer.parseInt(String.valueOf(requestVO
					.getPeriodStrategyMap().get(BizConstants.KEY_YEAR1)));
		if (requestVO.getPeriodStrategyMap().get(BizConstants.KEY_YEAR2) != null)
			year2 = Integer.parseInt(String.valueOf(requestVO
					.getPeriodStrategyMap().get(BizConstants.KEY_YEAR2)));

		if (requestVO.getStartDate() != null && startResidualDate1 != null) {

			CommonUtil.stringToDate(requestVO.getStartDate(),
					BizConstants.DATE_FORMAT_YYYYMMDD);

			if (DateUtils.isSameDay(
					CommonUtil.stringToDate(requestVO.getStartDate(),
							BizConstants.DATE_FORMAT_YYYYMMDD), CommonUtil
							.stringToDate(startResidualDate1,
									BizConstants.DATE_FORMAT_YYYYMMDD))) {
				// Overlapping week case between years - for 52 or 53 week #2344

				Calendar calStart = CommonUtil
						.convertStringToCalendarWithDateFormat(
								startResidualDate1,
								BizConstants.DATE_FORMAT_YYYYMMDD);

				if (calStart != null
						&& (calStart.get(Calendar.DAY_OF_MONTH) >= 1
								&& calStart.get(Calendar.WEEK_OF_YEAR) == 52 || calStart
								.get(Calendar.WEEK_OF_YEAR) == 53)) {

					isResidual1Exists = false;

				} else if (calStart != null
						&& (calStart.get(Calendar.DAY_OF_MONTH) >= 1
								&& calStart.get(Calendar.WEEK_OF_YEAR) == 0 || calStart
								.get(Calendar.WEEK_OF_YEAR) == 1)) {

					isResidual1Exists = false;

				} else {

					isResidual1Exists = true;
				}

			}

		}

		switch (requestVO.getPeriod()) {
		case BizConstants.PERIOD_THISYEAR:

			prcessedMap = getCalendarMonthsForYear(
					startRange1 != null ? Integer.parseInt(startRange1) : null,
					endRange1 != null ? Integer.parseInt(endRange1) : null,
					year1, startRange2 != null ? Integer.parseInt(startRange2)
							: null,
					endRange2 != null ? Integer.parseInt(endRange2) : null,
					year2, startResidualDate1, endResidualDate1,
					startResidualDate2, endResidualDate2, requestVO);

			break;
		case BizConstants.PERIOD_THISMONTH:
			// Modified by ravi
			prcessedMap = getCalendarWeekForMonth(
					startRange1 != null ? startRange1
							: BizConstants.EMPTY_STRING,
					endRange1 != null ? endRange1 : BizConstants.EMPTY_STRING,
					year1, startRange2 != null ? startRange2
							: BizConstants.EMPTY_STRING,
					endRange2 != null ? endRange2 : BizConstants.EMPTY_STRING,
					year2, startResidualDate1 != null ? startResidualDate1
							: BizConstants.EMPTY_STRING,
					endResidualDate1 != null ? endResidualDate1
							: BizConstants.EMPTY_STRING,
					startResidualDate2 != null ? startResidualDate2
							: BizConstants.EMPTY_STRING,
					endResidualDate2 != null ? endResidualDate2
							: BizConstants.EMPTY_STRING, requestVO,
					isResidual1Exists);

			break;

		case BizConstants.PERIOD_THISWEEK:

			prcessedMap = getCalendarDayForWeek(
					startRange1 != null ? startRange1
							: BizConstants.EMPTY_STRING,
					endRange1 != null ? endRange1 : BizConstants.EMPTY_STRING,
					year1, startRange2 != null ? startRange2
							: BizConstants.EMPTY_STRING,
					endRange2 != null ? endRange2 : BizConstants.EMPTY_STRING,
					year2, startResidualDate1 != null ? startResidualDate1
							: BizConstants.EMPTY_STRING,
					endResidualDate1 != null ? endResidualDate1
							: BizConstants.EMPTY_STRING,
					startResidualDate2 != null ? startResidualDate2
							: BizConstants.EMPTY_STRING,
					endResidualDate2 != null ? endResidualDate2
							: BizConstants.EMPTY_STRING);
			break;

		case BizConstants.PERIOD_TODAY:
			if (StringUtils.isNotBlank(startResidualDate1)
					&& StringUtils.isNotBlank(endResidualDate1))
				prcessedMap = getCalendarHoursForADay(startResidualDate1,
						endResidualDate1);

			break;

		case BizConstants.RANGE_YEAR:

			prcessedMap = getCalendarMonthsForYear(
					startRange1 != null ? Integer.parseInt(startRange1) : null,
					endRange1 != null ? Integer.parseInt(endRange1) : null,
					year1, startRange2 != null ? Integer.parseInt(startRange2)
							: null,
					endRange2 != null ? Integer.parseInt(endRange2) : null,
					year2, startResidualDate1, endResidualDate1,
					startResidualDate2, endResidualDate2, requestVO);
			break;
		case BizConstants.RANGE_MONTH:
			// Modified by ravi
			prcessedMap = getCalendarWeekForMonth(
					startRange1 != null ? startRange1
							: BizConstants.EMPTY_STRING,
					endRange1 != null ? endRange1 : BizConstants.EMPTY_STRING,
					year1, startRange2 != null ? startRange2
							: BizConstants.EMPTY_STRING,
					endRange2 != null ? endRange2 : BizConstants.EMPTY_STRING,
					year2, startResidualDate1 != null ? startResidualDate1
							: BizConstants.EMPTY_STRING,
					endResidualDate1 != null ? endResidualDate1
							: BizConstants.EMPTY_STRING,
					startResidualDate2 != null ? startResidualDate2
							: BizConstants.EMPTY_STRING,
					endResidualDate2 != null ? endResidualDate2
							: BizConstants.EMPTY_STRING, requestVO,
					isResidual1Exists);

			break;

		case BizConstants.RANGE_WEEK:

			prcessedMap = getCalendarDayForWeek(
					startRange1 != null ? startRange1
							: BizConstants.EMPTY_STRING,
					endRange1 != null ? endRange1 : BizConstants.EMPTY_STRING,
					year1, startRange2 != null ? startRange2
							: BizConstants.EMPTY_STRING,
					endRange2 != null ? endRange2 : BizConstants.EMPTY_STRING,
					year2, startResidualDate1 != null ? startResidualDate1
							: BizConstants.EMPTY_STRING,
					endResidualDate1 != null ? endResidualDate1
							: BizConstants.EMPTY_STRING,
					startResidualDate2 != null ? startResidualDate2
							: BizConstants.EMPTY_STRING,
					endResidualDate2 != null ? endResidualDate2
							: BizConstants.EMPTY_STRING);

			break;

		case BizConstants.RANGE_DAY:

			prcessedMap = getCalendarHoursForADay(startResidualDate1,
					endResidualDate1);

			break;
		case BizConstants.RANGE_3YEAR:

			prcessedMap = getCalendarYearForYears(
					startRange1 != null ? Integer.parseInt(startRange1) : null,
					endRange1 != null ? Integer.parseInt(endRange1) : null,
					year1, startRange2 != null ? Integer.parseInt(startRange2)
							: null,
					endRange2 != null ? Integer.parseInt(endRange2) : null,
					year2, startResidualDate1, endResidualDate1,
					startResidualDate2, endResidualDate2);

			break;

		default:
			break;
		}
		return prcessedMap;
	}

	private static Calendar getCalendar(String dateStr, String dateFormatStr) {
		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		Date startDate = CommonUtil.stringToDate(dateStr, dateFormatStr);
		startCal.setTime(startDate);
		return startCal;
	}

	private static Map<Integer, TreeMap<Integer, Object>> getCalendarMonthsForYear(
			Integer startRange1, Integer endRange1, Integer year1,
			Integer startRange2, Integer endRange2, Integer year2,
			String startResidualDate1, String endResidualDate1,
			String startResidualDate2, String endResidualDate2,
			StatsRequestVO requestVO) {

		Map<Integer, TreeMap<Integer, Object>> categories = new TreeMap<Integer, TreeMap<Integer, Object>>();

		Locale locale = Locale.getDefault();

		int i = 1;
		for (Map.Entry<String, Object> entry : requestVO.getPeriodStrategyMap()
				.entrySet()) {

			if (StringUtils.contains(entry.getKey(),
					BizConstants.KEY_START_RANGE + i)) {

				categories.put(
						Integer.parseInt(requestVO.getPeriodStrategyMap()
								.get("year" + i).toString()),
						populateMapForRangeMonths(
								Integer.parseInt(requestVO
										.getPeriodStrategyMap()
										.get("startRange" + i).toString()),
								Integer.parseInt(requestVO
										.getPeriodStrategyMap()
										.get("endRange" + i).toString()),
								Integer.parseInt(requestVO
										.getPeriodStrategyMap().get("year" + i)
										.toString()), locale));

				i++;

			}
		}

		/*
		 * if (startRange1 != null) {
		 * 
		 * categories.put( year1, populateMapForRangeMonths(startRange1,
		 * endRange1, year1, locale)); }
		 * 
		 * if (startRange2 != null) {
		 * 
		 * categories.put( year2, populateMapForRangeMonths(startRange2,
		 * endRange2, year2, locale)); }
		 */

		if (StringUtils.isNotBlank(startResidualDate1)) {
			populateMapForResidualMonths(startResidualDate1, categories, locale);
		}
		if (StringUtils.isNotBlank(startResidualDate2)) {

			populateMapForResidualMonths(startResidualDate2, categories, locale);
		}

		return categories;
	}
	
	// Modified by ravi
	private static Map<Integer, TreeMap<Integer, Object>> getCalendarWeekForMonth(
			String startRange1, String endRange1, Integer year1,
			String startRange2, String endRange2, Integer year2,
			String startResidualDate1, String endResidualDate1,
			String startResidualDate2, String endResidualDate2, StatsRequestVO requestVO,
			Boolean isResidual1Exists) {

		//Added By Ravi
		String period = requestVO.getPeriod();
		
		Map<Integer, TreeMap<Integer, Object>> categories = new TreeMap<Integer, TreeMap<Integer, Object>>();

		Locale locale = Locale.getDefault();
		TreeMap<Integer, Object> innerMap = new TreeMap<Integer, Object>();
		GregorianCalendar cal = null;
		int residualWeekExists = 0;

		if (StringUtils.isNotBlank(startResidualDate1)) {
			cal = CommonUtil.convertStringToCalendarWithDateFormat(
					startResidualDate1, BizConstants.DATE_FORMAT_YYYYMMDD);

			Calendar calEnd = CommonUtil.convertStringToCalendarWithDateFormat(
					endResidualDate1, BizConstants.DATE_FORMAT_YYYYMMDD);

			int fromYear = cal.get(GregorianCalendar.YEAR);

			int weekNumberFrom = cal.get(Calendar.WEEK_OF_YEAR);

			if (weekNumberFrom == 52 || weekNumberFrom == 53) {
				fromYear--;
			}

			cal = CommonUtil.getCalendarWeekFromMonday(fromYear,
					cal.get(Calendar.WEEK_OF_YEAR));

			int toYear = calEnd.get(GregorianCalendar.YEAR);

			int weekNumberTo = calEnd.get(Calendar.WEEK_OF_YEAR);

			if (weekNumberTo == 52 || weekNumberTo == 53) {
				toYear--;
			}

			calEnd = CommonUtil.getCalendarWeekFromMonday(toYear,
					calEnd.get(Calendar.WEEK_OF_YEAR));

			GregorianCalendar residualDayOfMonth = CommonUtil
					.convertStringToCalendarWithDateFormat(startResidualDate1,
							BizConstants.DATE_FORMAT_YYYYMMDD);

			if (StringUtils.equalsIgnoreCase(period,
					BizConstants.PERIOD_THISMONTH)) {

				if (cal.before(residualDayOfMonth)) {

					innerMap.put(cal.get(Calendar.WEEK_OF_YEAR), 1 + "/0 wk");

					if (calEnd.after(cal)) {

						innerMap.put(calEnd.get(Calendar.WEEK_OF_YEAR),
								calEnd.get(Calendar.DAY_OF_MONTH) + "/"
										+ (calEnd.get(Calendar.WEEK_OF_MONTH))
										+ "wk");
					}

					residualWeekExists++;

				} else {

					innerMap.put(
							cal.get(Calendar.WEEK_OF_YEAR),
							residualDayOfMonth.get(Calendar.DAY_OF_MONTH)
									+ "/"
									+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
									+ "wk");

				}

				categories.put(cal.get(Calendar.YEAR), innerMap);

			}

			// Modified by Ravi
			if (StringUtils.equalsIgnoreCase(period, BizConstants.RANGE_MONTH) && requestVO.getFileType() == null) {

				int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

				residualDayOfMonth.getTime();

				innerMap.put(
						residualDayOfMonth.get(Calendar.WEEK_OF_YEAR),
						residualDayOfMonth.get(Calendar.DAY_OF_MONTH)
								+ "/"
								+ (residualDayOfMonth.get(Calendar.WEEK_OF_MONTH))
								+ "wk");

				categories.put(
						getYearIfMixedWeeksOrNot(weekOfYear,
								residualDayOfMonth, isResidual1Exists),
						innerMap);

			} else if(StringUtils.equalsIgnoreCase(period, BizConstants.RANGE_MONTH)){
				
				int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

				residualDayOfMonth.getTime();

				innerMap.put(
						residualDayOfMonth.get(Calendar.WEEK_OF_YEAR),
						residualDayOfMonth.get(Calendar.DAY_OF_MONTH)
								+ "/"
								+ (residualDayOfMonth.get(Calendar.WEEK_OF_MONTH))
								+ "wk" + "/"
								+ residualDayOfMonth.getDisplayName(Calendar.MONTH,
										Calendar.SHORT, locale) + "/"
								+ residualDayOfMonth.get(Calendar.YEAR));

				categories.put(
						getYearIfMixedWeeksOrNot(weekOfYear,
								residualDayOfMonth, isResidual1Exists),
						innerMap);
			}
			// End of Modification by ravi

		}

		if (innerMap.size() > 0
				&& StringUtils.equalsIgnoreCase(period,
						BizConstants.RANGE_MONTH)) {

			if (StringUtils.isNotBlank(startRange1)) {

				cal = CommonUtil.getCalendarWeekFromMonday(year1,
						Integer.parseInt(startRange1));

				cal.getTime();

				/*
				 * if (weekNumberForRangeResudual1 -
				 * cal.get(Calendar.WEEK_OF_MONTH) > 1) {
				 * 
				 * residualWeekExists = 1;
				 * 
				 * 
				 * }
				 */

			}

		}

		if (StringUtils.isNotBlank(startRange1)) {

			// Modified By Ravi
			populateMapForRangeWeeks(Integer.parseInt(startRange1),
					Integer.parseInt(endRange1), year1, residualWeekExists,
					categories, innerMap, requestVO, locale);

		}

		if (StringUtils.isNotBlank(startRange2)) {

			// Modified By Ravi
			populateMapForRangeWeeks(Integer.parseInt(startRange2),
					Integer.parseInt(endRange2), year2, residualWeekExists,
					categories, innerMap, requestVO, locale);

		}

		if (StringUtils.isNotBlank(startResidualDate2)) {

			if (cal != null) {
				cal.clear();
			}

			if (StringUtils.isNotBlank(startRange1)) {

				cal = CommonUtil.getCalendarWeekFromMonday(year1,
						Integer.parseInt(startRange1));

				if (cal.get(Calendar.WEEK_OF_MONTH) == 1) {
					residualWeekExists = 0;
				}
			}

			if (cal != null) {
				cal.clear();
			}

			cal = CommonUtil.convertStringToCalendarWithDateFormat(
					startResidualDate2, BizConstants.DATE_FORMAT_YYYYMMDD);

			cal = CommonUtil.getCalendarWeekFromMonday(
					cal.get(GregorianCalendar.YEAR),
					cal.get(Calendar.WEEK_OF_YEAR));

			if (cal.get(Calendar.WEEK_OF_MONTH) == 1) {

				residualWeekExists = 0;

			}

			// Modify by ravi
			if (StringUtils.equalsIgnoreCase(period,
					BizConstants.PERIOD_THISMONTH)) {

				if (categories.containsKey(cal.get(Calendar.YEAR))) {
					innerMap = categories.get(cal.get(Calendar.YEAR));
				} else {

					innerMap = new TreeMap<Integer, Object>();
				}

				innerMap.put(
						cal.get(Calendar.WEEK_OF_YEAR),
						cal.get(Calendar.DAY_OF_MONTH)
								+ "/"
								+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
								+ "wk");

				categories.put(cal.get(Calendar.YEAR), innerMap);

			} else if (requestVO.getFileType() == null){

				TreeMap<Integer, Object> innerMapResidual2 = null;

				if (categories.containsKey(cal.get(Calendar.YEAR))) {
					innerMapResidual2 = categories.get(cal.get(Calendar.YEAR));
				} else {

					innerMapResidual2 = new TreeMap<Integer, Object>();
				}

				int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

				innerMapResidual2
						.put(cal.get(Calendar.WEEK_OF_YEAR),
								cal.get(Calendar.DAY_OF_MONTH)
										+ "/"
										+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
										+ "wk");

				categories.put(
						getYearIfMixedWeeksOrNot(weekOfYear, cal,
								isResidual1Exists), innerMapResidual2);

			} else {

				TreeMap<Integer, Object> innerMapResidual2 = null;

				if (categories.containsKey(cal.get(Calendar.YEAR))) {
					innerMapResidual2 = categories.get(cal.get(Calendar.YEAR));
				} else {

					innerMapResidual2 = new TreeMap<Integer, Object>();
				}

				int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

				innerMapResidual2
						.put(cal.get(Calendar.WEEK_OF_YEAR),
								cal.get(Calendar.DAY_OF_MONTH)
										+ "/"
										+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
										+ "wk" + "/"
										+ cal.getDisplayName(Calendar.MONTH,
												Calendar.SHORT, locale) + "/"
										+ cal.get(Calendar.YEAR));

				categories.put(
						getYearIfMixedWeeksOrNot(weekOfYear, cal,
								isResidual1Exists), innerMapResidual2);
			}
			//End of Modify by ravi

		}

		return categories;
	}

	private static Map<Integer, TreeMap<Integer, Object>> getCalendarDayForWeek(
			String startRange1, String endRange1, int year1,
			String startRange2, String endRange2, int year2,
			String startResidualDate1, String endResidualDate1,
			String startResidualDate2, String endResidualDate2) {

		Map<Integer, TreeMap<Integer, Object>> categories = new TreeMap<Integer, TreeMap<Integer, Object>>();

		Date startDate1 = null;
		Date endDate1 = null;

		Calendar cal = Calendar.getInstance();

		int i = 0;

		if (StringUtils.isNotBlank(startRange1)) {

			startDate1 = CommonUtil.stringToDate(startRange1,
					BizConstants.DATE_FORMAT_YYYYMMDD);
			endDate1 = CommonUtil.stringToDate(endRange1,
					BizConstants.DATE_FORMAT_YYYYMMDD);

			cal.setTime(startDate1);

			i = 0;

			year1 = cal.get(Calendar.YEAR);

			TreeMap<Integer, Object> subCategories = new TreeMap<Integer, Object>();

			if (null != cal
					&& (cal.getTime().before(endDate1) || cal.getTime().equals(
							endDate1))) {

				while (cal.getTime().compareTo(endDate1) != 1) {

					subCategories.put(i++, cal.getTimeInMillis());
					cal.add(Calendar.DATE, 1);
				}

				categories.put(year1, subCategories);
			}

		}

		if (StringUtils.isNotBlank(startResidualDate1)
				&& StringUtils.isBlank(startResidualDate2)) {

			cal.setTime(CommonUtil.stringToDate(startResidualDate1,
					BizConstants.DATE_FORMAT_YYYYMMDD));

			if (categories.get(cal.get(GregorianCalendar.YEAR)) != null) {

				i = categories.get(cal.get(GregorianCalendar.YEAR)).size() - 1;
				categories.get(cal.get(GregorianCalendar.YEAR)).put(++i,

				cal.getTimeInMillis());

			} else {

				i = 0;

				TreeMap<Integer, Object> subCategories = new TreeMap<Integer, Object>();

				subCategories.put(0, cal.getTimeInMillis());

				categories.put(cal.get(GregorianCalendar.YEAR), subCategories);
			}

		}

		return categories;
	}

	private static Map<Integer, TreeMap<Integer, Object>> getCalendarHoursForADay(
			String startResidualDate1, String endResidualDate1) {

		Map<Integer, TreeMap<Integer, Object>> categories = new TreeMap<Integer, TreeMap<Integer, Object>>();
		TreeMap<Integer, Object> subCategories = new TreeMap<Integer, Object>();

		Calendar startDate1 = Calendar.getInstance();
		Calendar endDate1 = Calendar.getInstance();

		startDate1.setTime(CommonUtil.stringToDate(startResidualDate1,
				BizConstants.DATE_FORMAT_YYYYMMDD));
		endDate1.setTime(CommonUtil.stringToDate(endResidualDate1,
				BizConstants.DATE_FORMAT_YYYYMMDD));
		startDate1.set(Calendar.HOUR_OF_DAY,
				startDate1.getActualMinimum(Calendar.HOUR_OF_DAY));
		endDate1.set(Calendar.HOUR_OF_DAY,
				endDate1.getActualMaximum(Calendar.HOUR_OF_DAY));

		while (startDate1.get(Calendar.HOUR_OF_DAY) <= endDate1
				.get(Calendar.HOUR_OF_DAY)) {

			subCategories.put(startDate1.get(Calendar.HOUR_OF_DAY),
					startDate1.getTimeInMillis());
			if (startDate1.get(Calendar.HOUR_OF_DAY) == endDate1
					.get(Calendar.HOUR_OF_DAY))
				break;
			else
				startDate1.add(Calendar.HOUR_OF_DAY, 1);
		}

		categories.put(startDate1.get(Calendar.YEAR), subCategories);

		return categories;
	}

	public static Map<Integer, TreeMap<Integer, Object>> getCalendarHoursForPast24Hours(
			String startResidualDate1, String endResidualDate1) {

		Map<Integer, TreeMap<Integer, Object>> categories = new TreeMap<Integer, TreeMap<Integer, Object>>();
		TreeMap<Integer, Object> subCategories = new TreeMap<Integer, Object>();

		Calendar startDate1 = CommonUtil.convertStringToCalendarWithDateFormat(
				startResidualDate1, BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		Calendar endDate1 = CommonUtil.convertStringToCalendarWithDateFormat(
				endResidualDate1, BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

		startDate1.getTime();
		endDate1.getTime();

		int index = 0;

		while (startDate1.compareTo(endDate1) <= 0) {

			subCategories.put(index, startDate1.getTimeInMillis());

			startDate1.add(Calendar.HOUR, 1);

			index++;
		}

		categories.put(startDate1.get(Calendar.YEAR), subCategories);

		return categories;
	}

	public static Map<Integer, TreeMap<Integer, Object>> getCalendarYearForYears(
			Integer startRange1, Integer endRange1, Integer year1,
			Integer startRange2, Integer endRange2, Integer year2,
			String startResidualDate1, String endResidualDate1,
			String startResidualDate2, String endResidualDate2) {

		Map<Integer, TreeMap<Integer, Object>> yearMap = new TreeMap<Integer, TreeMap<Integer, Object>>();

		TreeMap<Integer, Object> subCategories = new TreeMap<Integer, Object>();

		if (startRange1 != null && endRange1 != null) {

			for (int i = startRange1; i <= endRange1; i++) {
				subCategories.put(i, i);
			}
		}

		if (startResidualDate1 != null) {

			Calendar startResidualDate1Cal = getCalendar(startResidualDate1,
					BizConstants.YYYY_MM_DD);

			subCategories.put(startResidualDate1Cal.get(Calendar.YEAR),
					startResidualDate1Cal.get(Calendar.YEAR));
		}

		if (startResidualDate2 != null) {
			Calendar startResidualDate2Cal = getCalendar(startResidualDate2,
					BizConstants.YYYY_MM_DD);

			subCategories.put(startResidualDate2Cal.get(Calendar.YEAR),
					startResidualDate2Cal.get(Calendar.YEAR));
		}

		yearMap.put(1, subCategories);

		return yearMap;
	}

	private static TreeMap<Integer, Object> populateMapForRangeMonths(
			Integer startRange, Integer endRange, Integer year, Locale locale) {

		TreeMap<Integer, Object> subCategories = null;

		subCategories = new TreeMap<Integer, Object>();
		for (int i = (startRange - 1); i <= (endRange - 1); i++) {

			GregorianCalendar cal = new GregorianCalendar();
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.YEAR, year);
			cal.getTime();
			subCategories.put(
					(i + 1),
					cal.getDisplayName(GregorianCalendar.MONTH,
							GregorianCalendar.SHORT, locale) + "/" + year);

		}

		return subCategories;
	}

	private static Map<Integer, TreeMap<Integer, Object>> populateMapForResidualMonths(
			String startResidualDate,
			Map<Integer, TreeMap<Integer, Object>> categories, Locale locale) {

		GregorianCalendar cal = null;

		cal = new GregorianCalendar();
		cal.setTime(CommonUtil.stringToDate(startResidualDate,
				BizConstants.DATE_FORMAT_YYYYMMDD));

		if (categories.containsKey(cal.get(GregorianCalendar.YEAR))) {

			categories.get(cal.get(GregorianCalendar.YEAR)).put(
					cal.get(GregorianCalendar.MONTH) + 1,
					cal.getDisplayName(GregorianCalendar.MONTH,
							GregorianCalendar.SHORT, locale)
							+ "/"
							+ cal.get(GregorianCalendar.YEAR));

		} else {

			TreeMap<Integer, Object> map = new TreeMap<Integer, Object>();
			map.put(cal.get(GregorianCalendar.MONTH) + 1,
					cal.getDisplayName(GregorianCalendar.MONTH,
							GregorianCalendar.SHORT, locale)
							+ "/"
							+ cal.get(GregorianCalendar.YEAR));

			categories.put(cal.get(GregorianCalendar.YEAR), map);

		}
		return categories;
	}

	private static Integer getYearIfMixedWeeksOrNot(Integer weekOfYear,
			GregorianCalendar cal, boolean isResidual1Exists) {

		int year = 0;

		cal.getTime();

		if ((weekOfYear == 52 || weekOfYear == 53)
				&& cal.get(Calendar.DAY_OF_MONTH) >= 1
				&& cal.get(Calendar.MONTH) >= 0) {
			year = cal.get(GregorianCalendar.YEAR) - 1;
		} else if (weekOfYear == 52 || weekOfYear == 53) {

			year = cal.get(GregorianCalendar.YEAR);

		} else if (weekOfYear == 1) {

			if (isResidual1Exists) {
				year = cal.get(GregorianCalendar.YEAR) + 1;
			} else {
				year = cal.get(GregorianCalendar.YEAR);
			}
		} else {
			year = cal.get(GregorianCalendar.YEAR);
		}

		return year;
	}

	// Modified By Ravi
	private static Map<Integer, TreeMap<Integer, Object>> populateMapForRangeWeeks(
			Integer startRange, Integer endRange, Integer year,
			Integer residualWeekExists,
			Map<Integer, TreeMap<Integer, Object>> categories,
			TreeMap<Integer, Object> innerMap, StatsRequestVO requestVO, Locale locale) {

		//Added By Ravi
		String period = requestVO.getPeriod();
		
		TreeMap<Integer, Object> innerMapstartRange = null;
		GregorianCalendar cal = null;

		if (categories.containsKey(year)) {
			innerMapstartRange = categories.get(year);
		} else {

			innerMapstartRange = new TreeMap<Integer, Object>();
		}

		if (innerMap.size() > 0
				&& StringUtils.equalsIgnoreCase(period,
						BizConstants.PERIOD_THISMONTH)) {

			cal = CommonUtil.getCalendarWeekFromMonday(year, startRange);

			if (cal.get(Calendar.WEEK_OF_MONTH) == 1) {
				residualWeekExists = 0;
			}

		}

		for (int i = startRange; i <= endRange; i++) {

			cal = CommonUtil.getCalendarWeekFromMonday(year, i);
			// Modify by Ravi
			if (StringUtils.equalsIgnoreCase(period,
					BizConstants.PERIOD_THISMONTH)) {

				innerMapstartRange
						.put(cal.get(Calendar.WEEK_OF_YEAR),
								cal.get(Calendar.DAY_OF_MONTH)
										+ "/"
										+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
										+ "wk");

			} else if (requestVO.getFileType() == null) {

				if (cal.get(Calendar.WEEK_OF_MONTH) == 1) {
					residualWeekExists = 0;
				}

				innerMapstartRange
						.put(cal.get(Calendar.WEEK_OF_YEAR),
								cal.get(Calendar.DAY_OF_MONTH)
										+ "/"
										+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
										+ "wk");

			} else {
				
				if (cal.get(Calendar.WEEK_OF_MONTH) == 1) {
					residualWeekExists = 0;
				}

				innerMapstartRange
						.put(cal.get(Calendar.WEEK_OF_YEAR),
								cal.get(Calendar.DAY_OF_MONTH)
										+ "/"
										+ (cal.get(Calendar.WEEK_OF_MONTH) - residualWeekExists)
										+ "wk" + "/"
										+ cal.getDisplayName(Calendar.MONTH,
												Calendar.SHORT, locale) + "/"
										+ cal.get(Calendar.YEAR));
			}
		}
		categories.put(year, innerMapstartRange);

		return categories;
	}

	public static boolean isValidDateFormat(String dateStr) {

		boolean valid = false;

		if (CommonUtil.stringToDate(dateStr, BizConstants.DATE_FORMAT_YYYYMMDD) != null) {
			valid = true;
		}

		return valid;
	}
}

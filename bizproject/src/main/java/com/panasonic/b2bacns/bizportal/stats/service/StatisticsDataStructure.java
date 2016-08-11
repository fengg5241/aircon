package com.panasonic.b2bacns.bizportal.stats.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

@Component
@Scope("prototype")
public class StatisticsDataStructure {

	private Map<String, Object> returnObject = null;

	private static final int MIN_DAYS_IN_MONTH = 1;
	private static final int MAX_DAYS_IN_MONTH = 31;
	private static final int MIN_WEEKS_IN_YEAR = 1;
	private static final int MAX_WEEKS_IN_YEAR = 52;

	@Resource(name = "properties")
	private Properties bizProperties;

	/**
	 * This method is used to return Map which provide values to SQL named
	 * parameters define as keys to the SQL Queries of relevant charts as per
	 * selected period . Possible keys can be startRange1 up to nth number
	 * endRange1 up to nth number year1 up to nth number startResidualDate1 up
	 * to nth number endResidualDate1 up to nth number
	 * 
	 * @param strategy
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public Map<String, Object> getStatisticsStrategy(String strategy,
			Calendar fromCal, Calendar toCal, String userTimeZone)
			throws GenericFailureException {

		returnObject = new TreeMap<String, Object>();

		Calendar todayCal = CommonUtil.dateToCalendar(new Date());

		todayCal = CommonUtil.setUserTimeZone(todayCal, userTimeZone);

		// if startDate comes after current date then throwing
		// GenericFailureException .
		if (toCal.after(todayCal)) {
			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			throw new GenericFailureException(customErrorMessage);
		} else if (fromCal.after(toCal)) {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			throw new GenericFailureException(customErrorMessage);

		} else {

			int currentYear = 0;

			int thisYear = todayCal.get(Calendar.YEAR);

			int yearNo = 0;

			int residualNumber = 0;

			switch (strategy) {

			case BizConstants.RANGE_DAY:

				// In this case startDate and endDate will be same and only keys
				// startResidualDate1 and endResidualDate1 will be created in
				// the
				// map returnObject.
				// Modified by Ravi
				returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE1,
						CommonUtil.dateToString(fromCal.getTime())+" 00:00:00.000");

				returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE1,
						CommonUtil.dateToString(toCal.getTime())+" 23:59:59.999");

				break;

			case BizConstants.RANGE_WEEK:

				// if endDate is equal to current day
				if (DateUtils.isSameDay(todayCal, toCal)) {

					// only keys startResidualDate1 and endResidualDate1 will be
					// created in the map returnObject.
					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE1,
							CommonUtil.dateToString(todayCal.getTime()));

					returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE1,
							CommonUtil.dateToString(todayCal.getTime()));

				}

				// if endDate comes after startDate and endDate is equal to
				// current date
				if (toCal.after(fromCal)
						&& DateUtils.isSameDay(todayCal, toCal)) {

					returnObject.put(BizConstants.KEY_START_RANGE1,
							CommonUtil.dateToString(fromCal.getTime()));

					Calendar previousToEndDate = (Calendar) toCal.clone();

					previousToEndDate.add(Calendar.DATE, -1);

					returnObject.put(BizConstants.KEY_END_RANGE1, CommonUtil
							.dateToString(previousToEndDate.getTime()));

				} // if endDate comes after startDate
				else if (toCal.after(fromCal)) {

					returnObject.put(BizConstants.KEY_START_RANGE1,
							CommonUtil.dateToString(fromCal.getTime()));

					returnObject.put(BizConstants.KEY_END_RANGE1,
							CommonUtil.dateToString(toCal.getTime()));
				} // if endDate is equal to startDate
				else if (DateUtils.isSameDay(toCal, fromCal)) {

					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE1,
							CommonUtil.dateToString(toCal.getTime()));

					returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE1,
							CommonUtil.dateToString(toCal.getTime()));

				}

				break;
			case BizConstants.RANGE_MONTH:

				thisYear = toCal.get(Calendar.YEAR);

				Calendar firstWeek = (Calendar) fromCal.clone();

				Calendar lastWeek = (Calendar) toCal.clone();

				firstWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				lastWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

				Date firstDayOfWeek = firstWeek.getTime();

				Calendar lastDateOfLastWeek = (Calendar) lastWeek.clone();

				lastDateOfLastWeek.add(Calendar.DATE, 6);

				Date lastDayOfWeek = lastDateOfLastWeek.getTime();

				// getting week number of first week starting from Monday
				int weekNumberFrom = firstWeek.get(Calendar.WEEK_OF_YEAR);

				// getting week number of last week starting from Monday
				int weekNumberTo = lastWeek.get(Calendar.WEEK_OF_YEAR);

				fromCal.getTime();
				toCal.getTime();

				/*
				 * int lastWeekFrom =
				 * fromCal.getActualMaximum(Calendar.WEEK_OF_YEAR);
				 * 
				 * int startWeekTo =
				 * toCal.getActualMinimum(Calendar.WEEK_OF_YEAR);
				 */

				// if endDate is equal to current date
				if (DateUtils.isSameDay(fromCal, toCal)) {

					// only keys startResidualDate1 and endResidualDate1 will be
					// created in
					// the map returnObject.

					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE
							+ ++residualNumber,
							CommonUtil.dateToString(fromCal.getTime()));

					returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE
							+ residualNumber,
							CommonUtil.dateToString(toCal.getTime()));

					break;

				} // if first day of first week starting from Monday comes
					// before startDate
				else if (firstDayOfWeek.before(fromCal.getTime())) {

					Calendar lastDayDateOfFirstWeek = (Calendar) fromCal
							.clone();

					lastDayDateOfFirstWeek.setTime(firstDayOfWeek);

					// getting last date of the first week (Residual) starting
					// from Monday.
					lastDayDateOfFirstWeek.add(Calendar.DATE, 6);

					// setting value as startDate in the key startResidualDate1
					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE
							+ ++residualNumber,
							CommonUtil.dateToString(fromCal.getTime()));

					// setting value as last date of the first week(residual) in
					// the key endResidualDate1
					returnObject
							.put(BizConstants.KEY_END_RESIDUAL_DATE
									+ residualNumber, CommonUtil
									.dateToString(lastDayDateOfFirstWeek
											.getTime()));

					Calendar fromWeekCal = (Calendar) fromCal.clone();

					fromWeekCal.add(Calendar.WEEK_OF_YEAR, 1);

					// getting next weekNumberFrom starting from Monday
					weekNumberFrom = fromWeekCal.get(Calendar.WEEK_OF_YEAR);

				}

				int weekNumberToYear = 0;
				// if last day of last week comes after endDate it means last
				// week is not completed
				if (lastDayOfWeek.after(toCal.getTime())) {

					// Setting value as Monday date of last week in the key
					// startResidualDate2 if key
					// startResidualDate1 is already exists otherwise in the key
					// startResidualDate1
					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE
							+ ++residualNumber,
							CommonUtil.dateToString(lastWeek.getTime()));

					// Setting value as endDate in the key endResidualDate2 if
					// key endResidualDate1
					// is already exists otherwise in the key endResidualDate1
					returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE
							+ residualNumber,
							CommonUtil.dateToString(toCal.getTime()));

					Calendar toWeekCal = (Calendar) toCal.clone();

					toWeekCal.getTime();

					toWeekCal.add(Calendar.WEEK_OF_YEAR, -1);

					toWeekCal.getTime();

					// getting next weekNumberTo starting from Monday
					weekNumberTo = toWeekCal.get(Calendar.WEEK_OF_YEAR);

					toWeekCal.getTime();

					if (weekNumberTo == 52 || weekNumberTo == 53) {

						toWeekCal.add(Calendar.YEAR, -1);

					}/*
					 * else if(weekNumberTo == 1){
					 * 
					 * 
					 * toWeekCal.add(Calendar.YEAR, 1);
					 * 
					 * }
					 */

					toWeekCal.getTime();
					weekNumberToYear = toWeekCal.get(Calendar.YEAR);

				} else {

					weekNumberToYear = toCal.get(Calendar.YEAR);

				}

				// if weekNumberFrom is either 52 or 53 then year belongs to
				// startDate
				// else if weekNumberFrom is equal to 1 then year belongs to
				// endDate
				// else year belongs to startDate
				if (weekNumberFrom == 52 || weekNumberFrom == 53) {
					currentYear = fromCal.get(Calendar.YEAR);
				} else if (weekNumberFrom == 1) {
					currentYear = toCal.get(Calendar.YEAR);
				} else {

					currentYear = fromCal.get(Calendar.YEAR);
				}

				if (weekNumberTo == 52 || weekNumberTo == 53) {
					thisYear = fromCal.get(Calendar.YEAR);
				} else if (weekNumberTo == 1) {
					thisYear = toCal.get(Calendar.YEAR);
				} else {

					thisYear = fromCal.get(Calendar.YEAR);
				}

				// if weekNumberFrom is greater then weekNumberTo and
				// currentYear is not
				// equal to this year it means startDate and endDate are falling
				// in two different years and
				// need to handle boundary case between two years
				if ((weekNumberFrom > weekNumberTo)
						&& (currentYear != thisYear)) {

					// if weekNumberFrom is equals or less than 52 or 53 means
					// year is changing
					// falling weekNumberFrom in the month of December
					if (weekNumberFrom <= MAX_WEEKS_IN_YEAR
							|| weekNumberFrom <= 53) {

						// putting value as currentYear in the key year1
						returnObject.put(BizConstants.KEY_YEAR + ++yearNo,
								currentYear);
						// putting value as weekNumberFrom in the key
						// startRangeDate1
						returnObject.put(BizConstants.KEY_START_RANGE + yearNo,
								weekNumberFrom);
						// putting constant MAX_WEEKS_IN_YEAR value in the key
						// endRangeDate1

						Calendar fromWeekCal = (Calendar) fromCal.clone();

						fromWeekCal.add(Calendar.WEEK_OF_YEAR, 1);

						int previousWeekNumberFrom = weekNumberFrom;

						int lastWeekNo = MAX_WEEKS_IN_YEAR;

						// getting next weekNumberFrom starting from Monday
						weekNumberFrom = fromWeekCal.get(Calendar.WEEK_OF_YEAR);

						if (previousWeekNumberFrom > weekNumberFrom) {

							if (previousWeekNumberFrom == 53) {

								lastWeekNo = 53;

							}

						}

						if (weekNumberFrom == 52) {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, 52);

						}
						if (weekNumberFrom == 53) {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, 53);

						} else {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, lastWeekNo);

						}

					}

					currentYear++;

					// if weekNumberTo have at least weeks as defined in the
					// constant MIN_WEEKS_IN_YEAR
					if (weekNumberTo >= MIN_WEEKS_IN_YEAR
							&& (currentYear == weekNumberToYear)) {

						// putting value as currentYear in the key year2
						returnObject.put(BizConstants.KEY_YEAR + ++yearNo,
								currentYear);
						// putting value as 1 in the key startRangeDate2
						returnObject.put(BizConstants.KEY_START_RANGE + yearNo,
								MIN_WEEKS_IN_YEAR);
						// putting constant weekNumberTo value in the key
						// endRangeDate2
						returnObject.put(BizConstants.KEY_END_RANGE + yearNo,
								weekNumberTo);
					}

				} else if ((weekNumberFrom == 52 || weekNumberFrom == 53)
						&& (currentYear != weekNumberToYear)) {

					if ((weekNumberFrom == 52 || weekNumberFrom == 53)) {
						returnObject.put(BizConstants.KEY_YEAR + ++yearNo,
								currentYear);
						returnObject.put(BizConstants.KEY_START_RANGE + yearNo,
								weekNumberFrom);
						if ((weekNumberTo == 52 || weekNumberTo == 53)) {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, weekNumberTo);

						} else {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, weekNumberFrom);
						}

					}

					if (weekNumberTo >= 1) {

						returnObject.put(BizConstants.KEY_YEAR + ++yearNo,
								weekNumberToYear);
						returnObject.put(BizConstants.KEY_START_RANGE + yearNo,
								1);
						returnObject.put(BizConstants.KEY_END_RANGE + yearNo,
								weekNumberTo);

					}

				} else if ((weekNumberFrom <= weekNumberTo)
						&& (currentYear >= weekNumberToYear)) {

					// weekNumberFrom less then or equal to weekNumberTo means
					// startDate and endDate are falling
					// in the same year.

					returnObject.put(BizConstants.KEY_YEAR + ++yearNo,
							currentYear);
					returnObject.put(BizConstants.KEY_START_RANGE + yearNo,
							weekNumberFrom);
					if (weekNumberFrom == 1
							&& (weekNumberTo == 52 || weekNumberTo == 53)) {

						returnObject.put(BizConstants.KEY_END_RANGE + yearNo,
								weekNumberFrom);

					} else {
						returnObject.put(BizConstants.KEY_END_RANGE + yearNo,
								weekNumberTo);
					}
				}

				break;
			case BizConstants.RANGE_YEAR:

				int monthNumberFrom = fromCal.get(Calendar.MONTH) + 1;
				int monthNumberTo = toCal.get(Calendar.MONTH) + 1;

				boolean firstResidualExists = false;

				boolean endResidualExists = false;

				Calendar calendar = (Calendar) fromCal.clone();
				calendar.set(Calendar.DATE,
						fromCal.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date firstDayOfMonth = calendar.getTime();

				calendar = (Calendar) toCal.clone();
				calendar.set(Calendar.DATE,
						toCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date lastDayOfMonth = calendar.getTime();

				// if first date of startDate'month comes before startDate means
				// startDate
				// month is not completed that's why it will make start residual
				// month.
				if (firstDayOfMonth.before(fromCal.getTime())) {

					Calendar lastDayDate = (Calendar) fromCal.clone();

					lastDayDate.set(Calendar.DATE,
							fromCal.getActualMaximum(Calendar.DAY_OF_MONTH));

					// putting value as startDate in the key startResidualDate1
					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE
							+ ++residualNumber,
							CommonUtil.dateToString(fromCal.getTime()));

					// putting value as last date of residual month in the key
					// endResidualDate1
					returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE
							+ residualNumber,
							CommonUtil.dateToString(lastDayDate.getTime()));

					firstResidualExists = true;

					monthNumberFrom++;

				}

				// if last date of endDate month is after endDate means endDate
				// month
				// is not completed that'why it will create last residual month
				if (lastDayOfMonth.after(toCal.getTime())) {

					Calendar firstDayDate = (Calendar) toCal.clone();

					firstDayDate.set(Calendar.DATE,
							toCal.getActualMinimum(Calendar.DAY_OF_MONTH));

					// putting value as first date of endDate month in the key
					// startResidualDate2 if key
					// startResidualDate1 is already exists otherwise in the key
					// startResidualDate1
					returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE
							+ ++residualNumber,
							CommonUtil.dateToString(firstDayDate.getTime()));

					// putting value as endDate in the key endResidualDate2 if
					// key
					// endResidualDate1 is already exists otherwise in the key
					// endResidualDate1
					returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE
							+ residualNumber,
							CommonUtil.dateToString(toCal.getTime()));

					endResidualExists = true;

					--monthNumberTo;

					// getting previous monthNumberTo

				}

				currentYear = fromCal.get(Calendar.YEAR);

				int lastYear = toCal.get(Calendar.YEAR);

				int yearEndResidual = 0;
				int yearstartResidual = 0;

				// if monthNumberFrom is greater then monthNumberTo and
				// currentYear is not
				// equal to this year it means startDate and endDate are falling
				// in two different years and
				// need to handle boundary case between two years
				if ((lastYear - currentYear) >= 0) {

					if (lastYear - currentYear >= 0) {

						if ((firstResidualExists || endResidualExists)
								&& (currentYear == lastYear)) {

							if (monthNumberFrom <= 12) {

								returnObject.put(BizConstants.KEY_YEAR
										+ ++yearNo, currentYear);

								returnObject.put(BizConstants.KEY_START_RANGE
										+ yearNo, monthNumberFrom);

								returnObject.put(BizConstants.KEY_END_RANGE
										+ yearNo, monthNumberTo);

							}

							currentYear++;
							lastYear--;

						} else if ((firstResidualExists || endResidualExists)
								&& ((lastYear - currentYear) >= 1)) {

							if (firstResidualExists) {

								if (monthNumberFrom <= 12) {

									returnObject.put(BizConstants.KEY_YEAR
											+ ++yearNo, currentYear);

									returnObject.put(
											BizConstants.KEY_START_RANGE
													+ yearNo, monthNumberFrom);

									returnObject.put(BizConstants.KEY_END_RANGE
											+ yearNo, 12);
								}

								yearstartResidual = currentYear;

								currentYear++;

							}

							if (endResidualExists) {

								yearEndResidual = lastYear;

								lastYear--;

							}

						}

					}

					while (currentYear <= lastYear) {
						// putting value as currentYear in the key year1
						returnObject.put(BizConstants.KEY_YEAR + ++yearNo,
								currentYear);
						// putting value as monthNumberFrom in the key
						// startRange1

						if ((!firstResidualExists) && (currentYear <= lastYear)
								&& yearNo == 1) {

							returnObject.put(BizConstants.KEY_START_RANGE
									+ yearNo, monthNumberFrom);

						} else {

							returnObject.put(BizConstants.KEY_START_RANGE
									+ yearNo, 1);

						}
						// putting constant value MAX_MONTHS_IN_YEAR in the key
						// endRange1

						if ((!endResidualExists) && (currentYear == lastYear)) {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, monthNumberTo);

						} else {

							returnObject.put(BizConstants.KEY_END_RANGE
									+ yearNo, 12);

						}

						currentYear++;

					}

					if ((endResidualExists)
							&& ((yearEndResidual - yearstartResidual) >= 1)) {

						if (endResidualExists) {

							if (monthNumberTo > 0) {

								returnObject.put(BizConstants.KEY_YEAR
										+ ++yearNo, yearEndResidual);

								returnObject.put(BizConstants.KEY_START_RANGE
										+ yearNo, 1);

								returnObject.put(BizConstants.KEY_END_RANGE
										+ yearNo, monthNumberTo);

							}

						}

					}

				}

				break;
			case BizConstants.RANGE_3YEAR:

				Integer startYear = fromCal.get(Calendar.YEAR);
				Integer endYear = toCal.get(Calendar.YEAR);

				Calendar calendarRange = Calendar.getInstance();

				// setting calendarRange as 1st January of startDate year
				calendarRange.set(Calendar.YEAR, startYear);
				calendarRange.set(Calendar.MONTH, Calendar.JANUARY);
				calendarRange.set(Calendar.DAY_OF_MONTH, MIN_DAYS_IN_MONTH);

				calendarRange.getTime();

				Integer residualYear1 = startYear;
				Integer residualYear2 = endYear;

				// if calendarRange i.e. 1st January of startDate year comes
				// before startDate or calendarRange
				// and startDate equals then populating keys startResidualDate1
				// and endResidualDate1
				if (CommonUtil
						.getCalendarWithDateFormatWithoutTime(calendarRange)
						.getTime()
						.before(CommonUtil
								.getCalendarWithDateFormatWithoutTime(fromCal)
								.getTime())
						|| DateUtils.isSameDay(calendarRange, fromCal)) {

					Calendar lastDayDate = Calendar.getInstance();

					lastDayDate.set(Calendar.YEAR, startYear);
					lastDayDate.set(Calendar.MONTH, Calendar.DECEMBER);
					lastDayDate.set(Calendar.DAY_OF_MONTH, MAX_DAYS_IN_MONTH);

					if (!DateUtils.isSameDay(calendarRange, fromCal)) {
						returnObject.put(BizConstants.KEY_START_RESIDUAL_DATE
								+ ++residualNumber,
								CommonUtil.dateToString(fromCal.getTime()));

						returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE
								+ residualNumber,
								CommonUtil.dateToString(lastDayDate.getTime()));

					}

					if ((endYear - residualYear1) > 1) {

						if (returnObject
								.containsKey(BizConstants.KEY_START_RESIDUAL_DATE1)) {

							returnObject.put(BizConstants.KEY_START_RANGE1,
									residualYear1 + 1);
						} else {
							returnObject.put(BizConstants.KEY_START_RANGE1,
									residualYear1);
						}

					} else {

						if (!returnObject
								.containsKey(BizConstants.KEY_START_RESIDUAL_DATE1)) {

							returnObject.put(BizConstants.KEY_START_RANGE1,
									residualYear1);
						}

					}

				}

				// setting calendarRange as 31st December of endDate year
				calendarRange.set(Calendar.YEAR, endYear);
				calendarRange.set(Calendar.MONTH, Calendar.DECEMBER);
				calendarRange.set(Calendar.DAY_OF_MONTH, MAX_DAYS_IN_MONTH);

				boolean isEndResidual = false;

				// if calendarRange i.e. 31st of December of endDate year comes
				// after endDate or calendarRange
				// and endDate equals then populating keys startResidualDate2
				// and endResidualDate2 if keys
				// startResidualDate1 and endResidualDate1 already exists other
				// wise populating keys
				// startResidualDate1 and endResidualDate1
				if (calendarRange.getTime().after(toCal.getTime())
						|| DateUtils.isSameDay(calendarRange, toCal)) {

					Calendar firstDayDate = Calendar.getInstance();

					firstDayDate.set(Calendar.YEAR, endYear);
					firstDayDate.set(Calendar.MONTH, Calendar.JANUARY);
					firstDayDate.set(Calendar.DAY_OF_MONTH, MIN_DAYS_IN_MONTH);

					if (!DateUtils.isSameDay(calendarRange, toCal)) {
						returnObject
								.put(BizConstants.KEY_START_RESIDUAL_DATE
										+ ++residualNumber, CommonUtil
										.dateToString(firstDayDate.getTime()));

						returnObject.put(BizConstants.KEY_END_RESIDUAL_DATE
								+ residualNumber,
								CommonUtil.dateToString(toCal.getTime()));

						isEndResidual = true;

					}
					if ((residualYear2 - endYear) == 0
							&& returnObject
									.containsKey(BizConstants.KEY_START_RANGE1)) {

						if (isEndResidual) {

							returnObject.put(BizConstants.KEY_END_RANGE1,
									residualYear2 - 1);
						} else {

							returnObject.put(BizConstants.KEY_END_RANGE1,
									residualYear2);
						}

					} else {

						if (!isEndResidual) {

							returnObject.put(BizConstants.KEY_END_RANGE1,
									residualYear2);
						}

					}

				}

				break;

			default:
				break;

			}
		}

		System.out.println(returnObject);

		return returnObject;
	}
}

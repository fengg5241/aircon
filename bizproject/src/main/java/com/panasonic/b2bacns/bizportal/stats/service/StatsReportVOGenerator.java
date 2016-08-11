/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 * 
 */
@Component
public class StatsReportVOGenerator {

	/**
	 * 
	 * @param resultList
	 * @param requestVO
	 * @return
	 * @throws ParseException
	 */
	public List<StatsReportVO> getStatsList(List<?> resultList,
			StatsRequestVO requestVO) throws ParseException {

		List<StatsReportVO> statsReportVOList = new ArrayList<StatsReportVO>();

		Map<Integer, TreeMap<Integer, Object>> processMapForChronological = null;

		if (StringUtils.equalsIgnoreCase(requestVO.getType(),
				BizConstants.STATISTICS_CHRONOLOGICAL)
				&& !StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

			processMapForChronological = DatePrcocessUtil
					.getProcessedDateMap(requestVO);

		}
		List<String> categories = new ArrayList<String>();

		List<Float> list = new ArrayList<>();

		if (StringUtils.equalsIgnoreCase(requestVO.getType(),
				BizConstants.STATISTICS_CHRONOLOGICAL)
				&& !StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {
			getProcessMapForChronological(requestVO,
					processMapForChronological, categories, list);
		}

		Map<String, Map<Integer, StatsReportVO>> reportVoMap = new TreeMap<>();

		Map<Integer, StatsReportVO> innerMap = null;

		Double totalCapacity = 0.0;

		Iterator<?> itr = resultList.iterator();

		boolean isCategoryForPast24Created = false;

		while (itr.hasNext()) {

			Object[] rowData = (Object[]) itr.next();

			StatsReportVO reportVO = new StatsReportVO();

			Calendar fromCal = null;

			Calendar toCal = null;

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				int fromDateIndex = 0;

				int toDateIndex = 0;

				if (StringUtils.equalsIgnoreCase(requestVO.getType(),
						BizConstants.STATISTICS_CHRONOLOGICAL)) {

					fromDateIndex = rowData.length - 2;

					toDateIndex = rowData.length - 1;

				} else {

					if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
							BizConstants.STATISTICS_REFRIGERANT)) {
						
						fromDateIndex = rowData.length - 5;

						toDateIndex = rowData.length - 4;

					} else {

						fromDateIndex = rowData.length - 4;

						toDateIndex = rowData.length - 3;
					}

				}

				if (rowData[fromDateIndex] != null
						&& !isCategoryForPast24Created) {

					Iterator<?> itrForPast24Hour = resultList.iterator();

					while (itrForPast24Hour.hasNext()) {

						Object[] rowData1 = (Object[]) itrForPast24Hour.next();

						if (rowData1[fromDateIndex] != null) {

							Calendar fromDateTimeTemp = CommonUtil
									.convertStringToCalendarWithDateFormat(
											rowData1[fromDateIndex].toString(),
											BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							if (fromCal == null) {

								fromCal = CommonUtil
										.convertStringToCalendarWithDateFormat(
												rowData1[fromDateIndex]
														.toString(),
												BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							}

							fromDateTimeTemp.getTime();
							fromCal.getTime();

							if (fromCal.compareTo(fromDateTimeTemp) > 0) {

								fromCal = fromDateTimeTemp;
							}

						}

						if (rowData1[toDateIndex] != null) {

							Calendar toDateTimeTemp = CommonUtil
									.convertStringToCalendarWithDateFormat(
											rowData1[toDateIndex].toString(),
											BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							if (toCal == null) {
								toCal = CommonUtil
										.convertStringToCalendarWithDateFormat(
												rowData1[toDateIndex]
														.toString(),
												BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
							}

							toCal.getTime();
							toDateTimeTemp.getTime();

							if (toCal.compareTo(toDateTimeTemp) < 0) {

								toCal = toDateTimeTemp;
							}

						}

					}

					System.out.println("fromCal :" + fromCal.getTime());
					System.out.println("toCal :" + toCal.getTime());

					requestVO.setStartDate(CommonUtil
							.getCalendarWithDateFormat(
									fromCal.getTimeInMillis(),
									BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

					requestVO.setEndDate(CommonUtil.getCalendarWithDateFormat(
							toCal.getTimeInMillis(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

					isCategoryForPast24Created = true;

					if (StringUtils.equalsIgnoreCase(requestVO.getType(),
							BizConstants.STATISTICS_CHRONOLOGICAL)) {

						processMapForChronological = DatePrcocessUtil
								.getCalendarHoursForPast24Hours(
										requestVO.getStartDate(),
										requestVO.getEndDate());

						getProcessMapForChronological(requestVO,
								processMapForChronological, categories, list);

					}

				}

			}

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				if (requestVO.getStartDate() == null) {

					requestVO.setStartDate(requestVO.getPeriodStrategyMap()
							.get(BizConstants.KEY_START_RESIDUAL_DATE1)
							.toString());

				}

				if (requestVO.getEndDate() == null) {

					requestVO.setEndDate(requestVO.getPeriodStrategyMap()
							.get(BizConstants.KEY_END_RESIDUAL_DATE1)
							.toString());

				}

			}

			switch (requestVO.getType()) {
			case BizConstants.STATISTICS_ACCUMULATED:

				/* reportVO = new StatsReportVO(); */

				/*
				 * if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
				 * BizConstants.PERIOD_24HOURS)) {
				 * 
				 * requestVO.setStartDate(requestVO.getPeriodStrategyMap()
				 * .get(BizConstants.KEY_START_RESIDUAL_DATE1) .toString());
				 * requestVO.setEndDate(requestVO.getPeriodStrategyMap()
				 * .get(BizConstants.KEY_END_RESIDUAL_DATE1) .toString()); }
				 */

				populateVOForAccumulated(rowData, requestVO, reportVO);

				if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.CAPACITY)) {

					totalCapacity = totalCapacity
							+ (reportVO.getCurrent() != null ? reportVO
									.getCurrent() : 0.0);
				}

				statsReportVOList.add(reportVO);

				break;

			case BizConstants.STATISTICS_CHRONOLOGICAL:

				innerMap = new HashMap<Integer, StatsReportVO>();

				if (rowData != null && requestVO != null) {

					reportVO = populateVOForChronological(
							rowData,
							requestVO,
							processMapForChronological != null ? processMapForChronological
									: null, categories, list);

				}

				String lvlName = null;

				Integer groupNameIndex = null;
				Integer companyNameIndex = null;

				if ((StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_THISWEEK)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_WEEK)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_DAY)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_TODAY)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_3YEAR) || StringUtils
							.equalsIgnoreCase(requestVO.getPeriod(),
									BizConstants.PERIOD_24HOURS))
						&& rowData != null) {

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {
						groupNameIndex = rowData.length - 4;
						companyNameIndex = rowData.length - 3;

					} else {

						groupNameIndex = rowData.length - 2;
						companyNameIndex = rowData.length - 1;
					}

				} else {

					if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_AIRCON)
							&& rowData != null) {

						groupNameIndex = rowData.length - 2;
						companyNameIndex = rowData.length - 1;
					} else {

						groupNameIndex = rowData.length - 3;
						companyNameIndex = rowData.length - 2;

					}
				}

				lvlName = String.valueOf(rowData[0]) + "/"
						+ String.valueOf(rowData[groupNameIndex]) + "/"
						+ String.valueOf(rowData[companyNameIndex]);

				if (reportVoMap.containsKey(lvlName)
						&& reportVO.getIndex() != null) {

					reportVoMap.get(lvlName).put(reportVO.getIndex(), reportVO);

				} else {

					if (reportVO != null && reportVO.getIndex() != null) {

						innerMap = new TreeMap<Integer, StatsReportVO>();

						innerMap.put(reportVO.getIndex(), reportVO);
						reportVoMap.put(lvlName, innerMap);

					}
				}

				break;
			}

		}

		if (StringUtils.equalsIgnoreCase(requestVO.getType(),
				BizConstants.STATISTICS_ACCUMULATED)
				&& statsReportVOList != null && !statsReportVOList.isEmpty()) {

			statsReportVOList.get(statsReportVOList.size() - 1)
					.setTotalCapacity(totalCapacity);

		}

		if (StringUtils.equalsIgnoreCase(requestVO.getType(),
				BizConstants.STATISTICS_CHRONOLOGICAL)
				&& !reportVoMap.isEmpty() && reportVoMap.size() > 0) {

			statsReportVOList = new ArrayList<StatsReportVO>();

			// change from keyset() to entrySet() following coverity issue

			for (Map.Entry<String, Map<Integer, StatsReportVO>> entry : reportVoMap
					.entrySet()) {

				String levelName = entry.getKey();

				StatsReportVO reportVO = null;

				innerMap = reportVoMap.get(levelName);
				for (int index = 1; index < (categories.size() + 1); index++) {

					if (!innerMap.containsKey(index)) {

						reportVO = new StatsReportVO();
						// Removed by ravi
						if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
								BizConstants.STATISTICS_REFRIGERANT)) {

							reportVO.setLevelName(levelName.split("/")[1] + "/"
									+ levelName.split("/")[0]);
							reportVO.setCustomerName(levelName.split("/")[2]);

						} else {
							if (StringUtils.equals(requestVO.getApiCallFor(),
									BizConstants.STATS_API_CALL_BY_AIRCON)) {
								reportVO.setLevelName(levelName.split("/")[1]
										+ (levelName.split("/")[0].split("-")[0]
												.isEmpty() ? ""
												: ","
														+ levelName.split("/")[0]
																.substring(
																		0,
																		levelName
																				.split("/")[0]
																				.lastIndexOf("-"))));
							} else {
								reportVO.setLevelName(levelName.split("/")[1]);
							}
							reportVO.setCustomerName(levelName.split("/")[2]);
						}
						reportVO.setIndex(index);
						if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_TODAY)
								|| StringUtils.equalsIgnoreCase(
										requestVO.getPeriod(),
										BizConstants.RANGE_DAY)
								|| StringUtils.equalsIgnoreCase(
										requestVO.getPeriod(),
										BizConstants.PERIOD_24HOURS)) {
							reportVO.setDataDuration(1);
						}

					} else {

						reportVO = innerMap.get(index);
					}
					
					// Added by Ravi for modifying time format (Making common logic)
					
					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {
						
						String timeLine_date[] = categories.get(index - 1)
								.split("/");

						String timeLine[] = timeLine_date[1].split(":");

						Integer start_time = Integer.parseInt(timeLine[0]);
						String start_time_string = start_time.toString();
						if (start_time < 10) {
							start_time_string = BizConstants.ZERO
									+ start_time_string;
						}

						Integer end_time = start_time + 1;
						String end_time_string = end_time.toString();
						if (end_time < 10) {
							end_time_string = BizConstants.ZERO
									+ end_time_string;
						}

						String finalTimeLine = timeLine_date[0]
								+ BizConstants.CONCATE_TYPE_SLASH
								+ start_time_string
								+ BizConstants.CONCATE_TYPE_COLON
								+ BizConstants.ZERO + BizConstants.ZERO
								+ BizConstants.CONCATE_TYPE_HYPHEN
								+ end_time_string
								+ BizConstants.CONCATE_TYPE_COLON
								+ BizConstants.ZERO + BizConstants.ZERO;

						reportVO.setTimeline(finalTimeLine);
					} else if(StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_TODAY)){
						
						String timeLine_time = categories.get(index - 1);
						
						String timeLine[] = timeLine_time.split(":");

						Integer start_time = Integer.parseInt(timeLine[0]);
						String start_time_string = start_time.toString();
						if (start_time < 10) {
							start_time_string = BizConstants.ZERO
									+ start_time_string;
						}

						Integer end_time = start_time + 1;
						String end_time_string = end_time.toString();
						if (end_time < 10) {
							end_time_string = BizConstants.ZERO
									+ end_time_string;
						}

						String finalTimeLine = start_time_string
								+ BizConstants.CONCATE_TYPE_COLON
								+ BizConstants.ZERO + BizConstants.ZERO
								+ BizConstants.CONCATE_TYPE_HYPHEN
								+ end_time_string
								+ BizConstants.CONCATE_TYPE_COLON
								+ BizConstants.ZERO + BizConstants.ZERO;
						
						reportVO.setTimeline(finalTimeLine);
						
					} else {

						reportVO.setTimeline(categories.get(index - 1));
					}
					
					// End of Adding by Ravi for modifying time format

					statsReportVOList.add(reportVO);

				}
			}

		}

		return statsReportVOList;

	}

	/**
	 * 
	 * @param rowData
	 * @param requestVO
	 * @param reportVO
	 */
	private void populateVOForAccumulated(Object[] rowData,
			StatsRequestVO requestVO, StatsReportVO reportVO) {

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			reportVO.setPower(rowData[1] != null ? CommonUtil
					.getFormattedValueUpToTwoDecimal(rowData[1]) : null);

			break;

		case BizConstants.CAPACITY:

			reportVO.setRated(rowData[1] != null ? CommonUtil
					.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
			reportVO.setCurrent(rowData[2] != null ? CommonUtil
					.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
			reportVO.setOutdoorTemp(rowData[3] != null ? CommonUtil
					.getFormattedValueUpToTwoDecimal(rowData[3]) : null);

			break;

		case BizConstants.EFFICIENCY:

			if (!StringUtils.equals(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					&& !StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				reportVO.setEfficiency(rowData[4] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[4]) : null);
				reportVO.setSettingTemp(rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
				reportVO.setRmTemp(rowData[2] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
				reportVO.setOutdoorTemp(rowData[3] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[3]) : null);
			} else {

				reportVO.setEfficiency(rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[1]) : null);

			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			reportVO.setRoomTemp(rowData[1] != null ? CommonUtil
					.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
			reportVO.setSettingTemp(rowData[2] != null ? CommonUtil
					.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
			if (rowData[1] == null && rowData[2] == null) {

				reportVO.setDifferentialTemp(null);

			} else {
				reportVO.setDifferentialTemp((rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[1]) : 0)
						- (rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[2])
								: 0));
			}

			break;

		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				//Modified by Ravi
				if (rowData[rowData.length-1] != null) {

					if (StringUtils.equalsIgnoreCase(rowData[rowData.length-1].toString(),
							BizConstants.ODU_TYPE_VRF)
							|| StringUtils.equalsIgnoreCase(
									rowData[rowData.length-1].toString(),
									BizConstants.ODU_TYPE_PAC)) {

						reportVO.setCompressor1(rowData[1] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[1])
								: null);

					} else if (StringUtils.equalsIgnoreCase(
							rowData[rowData.length-1].toString(), BizConstants.ODU_TYPE_GHP)) {

						reportVO.setEngine(rowData[1] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[1])
								: null);

					}
				}
				//End of modification by ravi

				reportVO.setCompressor2(rowData[2] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
				reportVO.setCompressor3(rowData[3] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[3]) : null);

			} else {

				reportVO.setThermostatOff(BizConstants.WORKHOUR_PARAM_OPTION_OFF);

				reportVO.setHighOff(rowData[2] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
						: null);

				reportVO.setMediumOff(rowData[4] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
						: null);

				reportVO.setLowOff(rowData[6] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
						: null);

				if (rowData[2] != null && rowData[4] != null
						&& rowData[6] != null) {

					reportVO.setTotalHoursOff((rowData[2] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
							: 0.0)
							+ (rowData[4] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
									: 0.0)
							+ (rowData[6] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
									: 0.0));
				}

				reportVO.setThermostatOn(BizConstants.WORKHOUR_PARAM_OPTION_ON);

				reportVO.setHighOn(rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimalInMinutes(rowData[1])
						: null);

				reportVO.setMediumOn(rowData[3] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
						: null);

				reportVO.setLowOn(rowData[5] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
						: null);

				if (rowData[1] != null && rowData[3] != null
						&& rowData[5] != null) {

					reportVO.setTotalHoursOn((rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimalInMinutes(rowData[1])
							: 0.0)
							+ (rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
									: 0.0)
							+ (rowData[5] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
									: 0.0));
				}
			}

			break;
		}

		Integer groupNameIndex = rowData.length - 2;
		Integer companyNameIndex = rowData.length - 1;
		if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.STATISTICS_REFRIGERANT)) {

			
				groupNameIndex = rowData.length - 3;
				companyNameIndex = rowData.length - 2;

			

			reportVO.setLevelName(String.valueOf(rowData[groupNameIndex]) + "/"
					+ rowData[0] + BizConstants.EMPTY_STRING);

		} else {

			groupNameIndex = rowData.length - 2;
			companyNameIndex = rowData.length - 1;
			if (StringUtils.equals(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_AIRCON)) {
				reportVO.setLevelName(String.valueOf(rowData[groupNameIndex])
						+ (rowData[0].toString().split("-")[0].isEmpty() ? ""
								: ","
										+ rowData[0].toString().substring(
												0,
												rowData[0].toString()
														.lastIndexOf("-"))));
			} else
				reportVO.setLevelName(String.valueOf(rowData[groupNameIndex]));

		}
		reportVO.setCustomerName(rowData[companyNameIndex] != null ? String
				.valueOf(rowData[companyNameIndex]) : BizConstants.EMPTY_STRING);

	}

	/**
	 * 
	 * @param rowData
	 * @param requestVO
	 * @param reportVO
	 * @param list
	 * @param categories
	 * @param processMapForChronological
	 */
	private StatsReportVO populateVOForChronological(Object[] rowData,
			StatsRequestVO requestVO,
			Map<Integer, TreeMap<Integer, Object>> processMapForChronological,
			List<String> categories, List<Float> list) {

		int index = getIndexForList(rowData, requestVO,
				processMapForChronological);

		StatsReportVO reportVO = new StatsReportVO();

		if (index <= categories.size()) {

			// reportVO = new StatsReportVO();

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_TODAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.RANGE_DAY)

					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

				reportVO.setDataDuration(1);
			}

		}

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			if (index < categories.size()) {

				reportVO.setIndex(0);

				if (rowData[1] != null) {

					reportVO.setIndex(index + 1);
					reportVO.setTimeline(categories.get(index));
					reportVO.setPower(rowData[3] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[3]) : null);

				}

			}

			break;

		case BizConstants.CAPACITY:

			if (index < categories.size()) {

				reportVO.setIndex(0);

				if (rowData[1] != null) {

					reportVO.setIndex(index + 1);
					reportVO.setTimeline(categories.get(index));
					reportVO.setRated(rowData[2] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
					reportVO.setCurrent(rowData[3] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[3]) : null);
					reportVO.setOutdoorTemp(rowData[4] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[4]) : null);

				}
			}

			break;

		case BizConstants.EFFICIENCY:

			if (index < categories.size()) {

				reportVO.setIndex(0);

				if (rowData[1] != null) {

					if (!StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
							&& !StringUtils.equals(requestVO.getApiCallFor(),
									BizConstants.STATS_API_CALL_BY_GROUP)) {

						reportVO.setIndex(index + 1);
						reportVO.setTimeline(categories.get(index));
						reportVO.setEfficiency(rowData[5] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[5])
								: null);
						reportVO.setSettingTemp(rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[2])
								: null);
						reportVO.setRmTemp(rowData[3] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[3])
								: null);
						reportVO.setOutdoorTemp(rowData[4] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[4])
								: null);

					} else {

						reportVO.setIndex(index + 1);
						reportVO.setTimeline(categories.get(index));
						reportVO.setEfficiency(rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[2])
								: null);

					}

				}

			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			if (index < categories.size()) {

				reportVO.setIndex(0);

				if (rowData[1] != null) {

					reportVO.setIndex(index + 1);
					reportVO.setTimeline(categories.get(index));
					reportVO.setRoomTemp(rowData[2] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
					reportVO.setSettingTemp(rowData[3] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[3]) : null);
					if (rowData[2] == null && rowData[3] == null) {

						reportVO.setDifferentialTemp(null);

					} else {
						reportVO.setDifferentialTemp((rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[2])
								: 0)
								- (rowData[3] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[3])
										: 0));
					}
				}

			}

			break;

		case BizConstants.WORKING_HOURS:

			if (index < categories.size()) {

				reportVO.setIndex(0);

				if (rowData[1] != null) {

					reportVO.setIndex(index + 1);
					reportVO.setTimeline(categories.get(index));

					if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

						if (rowData[2] != null) {

							if (StringUtils.equalsIgnoreCase(
									rowData[2].toString(),
									BizConstants.ODU_TYPE_VRF)
									|| StringUtils.equalsIgnoreCase(
											rowData[2].toString(),
											BizConstants.ODU_TYPE_PAC)) {

								reportVO.setCompressor1(rowData[3] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[3])
										: null);

							} else if (StringUtils.equalsIgnoreCase(
									rowData[2].toString(),
									BizConstants.ODU_TYPE_GHP)) {

								reportVO.setEngine(rowData[3] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[3])
										: null);

							}
						}

						reportVO.setCompressor2(rowData[4] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[4])
								: null);
						reportVO.setCompressor3(rowData[5] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[5])
								: null);

					} else {

						reportVO.setThermostatOff(BizConstants.WORKHOUR_PARAM_OPTION_OFF);

						reportVO.setHighOff(rowData[3] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
								: null);

						reportVO.setMediumOff(rowData[5] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
								: null);

						reportVO.setLowOff(rowData[7] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[7])
								: null);

						if (rowData[3] != null && rowData[5] != null
								&& rowData[7] != null) {

							reportVO.setTotalHoursOff((rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
									: 0.0)
									+ (rowData[5] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
											: 0.0)
									+ (rowData[7] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[7])
											: 0.0));
						}

						reportVO.setThermostatOn(BizConstants.WORKHOUR_PARAM_OPTION_ON);

						reportVO.setHighOn(rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
								: null);

						reportVO.setMediumOn(rowData[4] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
								: null);

						reportVO.setLowOn(rowData[6] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
								: null);

						if (rowData[2] != null && rowData[4] != null
								&& rowData[6] != null) {

							reportVO.setTotalHoursOn((rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
									: 0.0)
									+ (rowData[4] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
											: 0.0)
									+ (rowData[6] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
											: 0.0));
						}
					}
				}

			}

			break;
		}

		if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
				BizConstants.PERIOD_24HOURS)) {

			if (reportVO != null) {

				if (StringUtils.isNotBlank(reportVO.getTimeline())) {
					String timeLine[] = reportVO.getTimeline().split(":");

					reportVO.setTimeline(timeLine[0] + ":00");
				}
			}

		}

		Integer groupNameIndex = null;
		Integer companyNameIndex = null;

		if (index < categories.size()) {

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_AIRCON)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					groupNameIndex = rowData.length - 4;
					companyNameIndex = rowData.length - 3;

				} else {

					groupNameIndex = rowData.length - 2;
					companyNameIndex = rowData.length - 1;

				}
				reportVO.setLevelName(String.valueOf(rowData[groupNameIndex])
						+ (rowData[0].toString().split("-")[0].isEmpty() ? ""
								: ","
										+ rowData[0].toString().substring(
												0,
												rowData[0].toString()
														.lastIndexOf("-"))));
				reportVO.setCustomerName(rowData[companyNameIndex] != null ? String
						.valueOf(rowData[companyNameIndex])
						: BizConstants.EMPTY_STRING);

			} else {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_THISWEEK)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_WEEK)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_DAY)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_TODAY)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_3YEAR)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_24HOURS)) {

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

						groupNameIndex = rowData.length - 4;
						companyNameIndex = rowData.length - 3;

					} else {

						groupNameIndex = rowData.length - 2;
						companyNameIndex = rowData.length - 1;
					}

				} else {

					groupNameIndex = rowData.length - 3;
					companyNameIndex = rowData.length - 2;
				}

				if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.STATISTICS_REFRIGERANT)) {

					reportVO.setLevelName(String
							.valueOf(rowData[groupNameIndex])
							+ "/"
							+ rowData[0].toString());

				} else {

					reportVO.setLevelName(String
							.valueOf(rowData[groupNameIndex]));

				}

				reportVO.setCustomerName(rowData[companyNameIndex] != null ? String
						.valueOf(rowData[companyNameIndex])
						: BizConstants.EMPTY_STRING);

			}

		}

		return reportVO;

	}

	/**
	 * 
	 * @param requestVO
	 * @param processMapForChronological
	 * @param categories
	 * @param list
	 * @throws ParseException
	 */
	private void getProcessMapForChronological(StatsRequestVO requestVO,
			Map<Integer, TreeMap<Integer, Object>> map,
			List<String> categories, List<Float> list) throws ParseException {

		// change from keyset() to entrySet() following coverity issue

		for (Entry<Integer, TreeMap<Integer, Object>> entry1 : map.entrySet()) {

			Integer key = entry1.getKey();

			TreeMap<Integer, Object> innerMap = map.get(key);

			for (Entry<Integer, Object> entry2 : innerMap.entrySet()) {

				Integer innerkey = entry2.getKey();

				Calendar calendar = CommonUtil.setUserTimeZone(
						Calendar.getInstance(), requestVO.getUserTimeZone());
				String[] categoryArr = null;
				Date date = null;
				String categoryValue = BizConstants.EMPTY_STRING;
				Long timeInMilliSeconds = null;

				switch (requestVO.getPeriod()) {
				case BizConstants.PERIOD_THISYEAR:
				case BizConstants.RANGE_YEAR:

					categoryArr = String.valueOf(innerMap.get(innerkey)).split(
							"/");
					date = new SimpleDateFormat("MMM").parse(categoryArr[0]);
					calendar.setTime(date);

					categories.add((calendar.get(Calendar.MONTH) + 1) + "/"
							+ categoryArr[1]);

					calendar.clear();

					break;
				case BizConstants.PERIOD_THISMONTH:

					categoryArr = String.valueOf(innerMap.get(innerkey)).split(
							"/");
					date = new SimpleDateFormat("DD").parse(categoryArr[0]);
					calendar.set(Calendar.DATE,
							Integer.parseInt(categoryArr[0]));

					categoryValue = CommonUtil.dateToString(calendar.getTime(),
							BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

					calendar = CommonUtil.getCalendarWeekLastDay(key, innerkey);
					if (calendar.getTime().after(
							CommonUtil.setUserTimeZone(Calendar.getInstance(),
									requestVO.getUserTimeZone()).getTime())) {

						calendar = CommonUtil.setUserTimeZone(
								Calendar.getInstance(),
								requestVO.getUserTimeZone());
					}

					categoryValue = categoryValue
							+ " to "
							+ CommonUtil.dateToString(calendar.getTime(),
									BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

					categories.add(categoryValue);

					break;
				case BizConstants.RANGE_MONTH:

					categoryArr = String.valueOf(innerMap.get(innerkey)).split(
							"/|-");
					date = new SimpleDateFormat("MMM").parse(categoryArr[2]);
					calendar.setTime(date);
					date = new SimpleDateFormat("DD").parse(categoryArr[0]);
					calendar.set(Calendar.DATE,
							Integer.parseInt(categoryArr[0]));
					calendar.set(Calendar.YEAR,
							Integer.parseInt(categoryArr[3]));

					categoryValue = CommonUtil.dateToString(calendar.getTime(),
							BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

					calendar = CommonUtil.getCalendarWeekLastDay(key, innerkey);

					Date endDate = CommonUtil.stringToDate(
							requestVO.getEndDate(),
							BizConstants.DATE_FORMAT_YYYYMMDD);
					if (calendar.getTime().after(endDate)) {

						calendar.setTime(endDate);
					}

					categoryValue = categoryValue
							+ " to "
							+ CommonUtil.dateToString(calendar.getTime(),
									BizConstants.DATE_FORMAT_YYYYMMDD_DOWNLOAD);

					categories.add(categoryValue);

					break;

				case BizConstants.PERIOD_THISWEEK:
				case BizConstants.RANGE_WEEK:

					timeInMilliSeconds = (Long) innerMap.get(innerkey);

					calendar = CommonUtil
							.getCalendarFromMilliseconds(timeInMilliSeconds);
					categories.add(calendar.get(Calendar.DAY_OF_MONTH) + "/"
							+ (calendar.get(Calendar.MONTH) + 1) + "/"
							+ calendar.get(Calendar.YEAR));

					break;

				case BizConstants.PERIOD_TODAY:
				case BizConstants.RANGE_DAY:
				case BizConstants.PERIOD_24HOURS:

					timeInMilliSeconds = (Long) innerMap.get(innerkey);

					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(timeInMilliSeconds);

					Calendar currentCal = CommonUtil
							.setUserTimeZone(Calendar.getInstance(),
									requestVO.getUserTimeZone());

					if (CommonUtil
							.getCalendarWithDateFormatWithoutTime(cal)
							.compareTo(
									CommonUtil
											.getCalendarWithDateFormatWithoutTime(currentCal)) == 0
							&& !StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.PERIOD_24HOURS)) {

						if (innerkey > currentCal.get(Calendar.HOUR_OF_DAY))
							break;

					}

					calendar = CommonUtil
							.getCalendarFromMilliseconds(timeInMilliSeconds);

					// calendar.add(Calendar.HOUR_OF_DAY, 1);
					// calendar.add(Calendar.MINUTE, -1);
					//
					// if (calendar.getTime().after(
					// CommonUtil.setUserTimeZone(Calendar.getInstance(),
					// requestVO.getUserTimeZone()).getTime())) {
					//
					// calendar = CommonUtil.setUserTimeZone(
					// Calendar.getInstance(),
					// requestVO.getUserTimeZone());
					// }
					// categoryValue = categoryValue
					// + " to "
					// + CommonUtil.dateToString(calendar.getTime(),
					// "HH:mm");

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

						categories.add(CommonUtil.dateToString(
								calendar.getTime(),
								calendar.get(Calendar.DAY_OF_MONTH) + "/"
										+ "HH:mm"));

					} else {
						// Modified by Ravi
						Calendar calendar2 = CommonUtil
								.getCalendarFromMilliseconds(timeInMilliSeconds
										+ (60 * 60 * 1000));
						categories.add(CommonUtil.dateToString(
								calendar.getTime(), "HH:mm")
								+ BizConstants.EMPTY_SPACE
								+ BizConstants.DASH_STRING
								+ BizConstants.EMPTY_SPACE
								+ CommonUtil.dateToString(calendar2.getTime(),
										"HH:mm"));

					}

					break;

				case BizConstants.RANGE_3YEAR:

					categories.add(String.valueOf(innerMap.get(innerkey)));

					break;

				default:
					break;
				}

				list.add(null);

			}
		}

	}

	/**
	 * 
	 * @param rowData
	 * @param requestVO
	 * @param processMapForChronological
	 * @return
	 */
	private int getIndexForList(Object[] rowData, StatsRequestVO requestVO,
			Map<Integer, TreeMap<Integer, Object>> processMapForChronological) {

		int index = 0;

		if (rowData[1] != null) {

			int outerIndex = 0;

			outer:

			for (Entry<Integer, TreeMap<Integer, Object>> entry : processMapForChronological
					.entrySet()) {

				index = outerIndex;

				for (Entry<Integer, Object> innerEntry : entry.getValue()
						.entrySet()) {

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_TODAY)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.PERIOD_THISWEEK)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_WEEK)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_DAY)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.PERIOD_24HOURS)) {

						String dateFromMap = null;

						if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_THISWEEK)
								|| StringUtils.equalsIgnoreCase(
										requestVO.getPeriod(),
										BizConstants.RANGE_WEEK)) {

							dateFromMap = CommonUtil.getCalendarWithDateFormat(
									(long) innerEntry.getValue(),
									BizConstants.DATE_FORMAT_YYYYMMDD);

							if (String.valueOf((rowData[1])).contains(
									dateFromMap)) {

								break outer;
							}

						} else {

							dateFromMap = CommonUtil
									.getCalendarWithDateFormatHourly(
											(long) innerEntry.getValue(),
											BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							Date date = CommonUtil.stringToDate(
									rowData[1].toString(),
									BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							String dateFromDatabase = CommonUtil
									.getCalendarWithDateFormatHourly(
											date.getTime(),
											BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							if (String.valueOf(dateFromMap).contains(
									dateFromDatabase)) {

								break outer;
							}

						}

					} else {

						if (!StringUtils
								.equalsIgnoreCase(requestVO.getPeriod(),
										BizConstants.RANGE_3YEAR)) {

							Integer yearIndex = null;

							if (StringUtils.equalsIgnoreCase(
									requestVO.getApiCallFor(),
									BizConstants.STATS_API_CALL_BY_AIRCON)) {

								yearIndex = rowData.length - 3;
							} else {

								yearIndex = rowData.length - 1;

							}

							BigDecimal periodNum = new BigDecimal(
									String.valueOf((rowData[1])));

							if (innerEntry.getKey() == periodNum.byteValue()
									&& entry.getKey()
											.equals(Integer.parseInt(String
													.valueOf((rowData[yearIndex]))))) {

								break outer;
							}
						} else {

							if (innerEntry.getKey().equals(
									(Integer.parseInt(String
											.valueOf((rowData[1])))))) {

								break outer;
							}

						}

					}

					index++;
				}

				outerIndex = outerIndex + entry.getValue().size();
			}

		}

		return index;
	}

}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.service.CompanyUserService;
import com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO;
import com.panasonic.b2bacns.bizportal.stats.vo.EnergyConsumptionResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.RefrigerantCircuitResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsResponseVOPowerConsumption;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StatsManagementServiceImpl implements StatsManagementService {

	@Autowired
	private StatsSQLDAO statssqldao;

	@Autowired
	private StatisticsDataStructure statsDS;

	@Autowired
	private CompanyUserService companyUserService;

	@Autowired
	private StatsReportGenerator statsReportGenerator;

	@Resource(name = "properties")
	private Properties bizProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService#
	 * getStatsDetails(com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getStatsDetails(StatsRequestVO requestVO)
			throws JsonProcessingException, GenericFailureException,
			JSONException, NumberFormatException, ParseException {

		setPeriodStrategy(requestVO);

		StatsResponseVO responseVO = statssqldao.getStatsDetails(requestVO);

		String jsonResponse = null;

		if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
				BizConstants.POWER_CONSUMPTION)
				&& StringUtils.equalsIgnoreCase(requestVO.getType(),
						BizConstants.STATISTICS_ACCUMULATED)
				&& !StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

			StatsResponseVOPowerConsumption powerConsumptionVO = new StatsResponseVOPowerConsumption();

			powerConsumptionVO.setCategories(responseVO.getCategories());

			powerConsumptionVO.setSeries((List<Object>) responseVO.getSeries()
					.get(0).get(BizConstants.KEY_JSON_RESPONSE_DATA));

			jsonResponse = CommonUtil
					.convertFromEntityToJsonStr(powerConsumptionVO);

		} /*
		 * else if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
		 * BizConstants.CAPACITY) &&
		 * StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
		 * BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {
		 * 
		 * responseVO.setTotal(null);
		 * 
		 * jsonResponse = CommonUtil.convertFromEntityToJsonStr(responseVO);
		 * 
		 * }
		 */else if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
				BizConstants.CAPACITY)
				&& StringUtils.equalsIgnoreCase(requestVO.getType(),
						BizConstants.STATISTICS_ACCUMULATED)) {

			jsonResponse = CommonUtil.convertFromEntityToJsonStr(responseVO);

			JSONObject jsonObj = new JSONObject(jsonResponse);

			if (!jsonObj.has("total")) {
				jsonObj.put("total", JSONObject.NULL);

			}

			jsonResponse = jsonObj.toString();

		} else {

			jsonResponse = CommonUtil.convertFromEntityToJsonStr(responseVO);
		}

		return jsonResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService#
	 * getPeriodStrategy
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public Map<String, Object> getPeriodStrategy(StatsRequestVO requestVO)
			throws GenericFailureException, ParseException {

		/*
		 * This is periodStrategyMap is used to provide values to SQL named
		 * parameters define as keys to the SQL Queries of relevant charts as
		 * per its period . Possible keys can be startRange1 up to nth number
		 * endRange1 up to nth number year1 up to nth number startResidualDate1
		 * up to nth number endResidualDate1 up to nth number e. g. If current
		 * date is 14 November 2015 and selected period is thisyear then
		 * startRange1=1 endRange1=10 year1-2015 startResidualDate1=2015-11-01
		 * endResudualDate1=2015-11-14
		 */
		Map<String, Object> periodStrategyMap = new HashMap<String, Object>();

		Calendar cal = Calendar.getInstance();

		cal.setFirstDayOfWeek(Calendar.MONDAY);

		// Below setUserTimeZone method of CommonUtil class is used get Calendar
		// Object as per user Time Zone
		// and provided date from user session
		cal = CommonUtil.setUserTimeZone(cal, requestVO.getUserTimeZone());

		System.out.println("userTimeZone : " + requestVO.getUserTimeZone());

		if (requestVO.getPeriod() == null) {

			requestVO.setPeriod(BizConstants.PERIOD_RANGE);

		}

		switch (requestVO.getPeriod()) {

		case BizConstants.PERIOD_THISYEAR:

			int month = cal.get(Calendar.MONTH);

			// if month is not of Jan then startRange will be 1 and endRange1
			// will
			// last month to current month and year1 will be year .
			if (month != 0) {

				periodStrategyMap.put(BizConstants.KEY_START_RANGE1, 1);

				periodStrategyMap.put(BizConstants.KEY_END_RANGE1, month);

				periodStrategyMap.put(BizConstants.KEY_YEAR1,
						cal.get(Calendar.YEAR));

			}

			// Getting current day number of the month
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

			// setting 1 to current date to get the first day of the month
			cal.set(Calendar.DATE, 1);

			// putting vale as first date of the month to the key
			// startResidualDate1
			periodStrategyMap.put(BizConstants.KEY_START_RESIDUAL_DATE1,
					CommonUtil.dateToString(cal.getTime()));

			cal.set(Calendar.DATE, dayOfMonth);

			// putting vale as current date of the month to the key
			// endResidualDate1
			periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE1,
					CommonUtil.dateToString(cal.getTime()));

			break;

		case BizConstants.PERIOD_THISMONTH:

			// Below setUserTimeZone method of CommonUtil class is used get
			// Calendar Object
			// as per user Time Zone and provided date from user session
			Calendar firstDayOfWeekCal = CommonUtil.setUserTimeZone(
					Calendar.getInstance(), requestVO.getUserTimeZone());

			firstDayOfWeekCal.getTime();

			// getting first of day of the month
			firstDayOfWeekCal.set(Calendar.DAY_OF_MONTH, 1);

			int currentYear = firstDayOfWeekCal.get(Calendar.YEAR);
			int weekNumberFrom = firstDayOfWeekCal.get(Calendar.WEEK_OF_YEAR);

			if (weekNumberFrom == 52 || weekNumberFrom == 53) {
				currentYear--;
			}

			// getting day difference between first day and current day of this
			// month
			Long dateDiff = CommonUtil.getDifferenceInDays(
					CommonUtil.dateToString(firstDayOfWeekCal.getTime()),
					CommonUtil.dateToString(cal.getTime()));

			requestVO.setPeriod(getPeriodStrategicByRange(dateDiff));

			// if difference between month starting date and current date is
			// less then a week then
			// periodStrategyMap is populated in the below if block.
			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.RANGE_WEEK)) {

				// getting calendar week of first day of this month starting
				// from Monday
				Calendar calStartWeek = CommonUtil.getCalendarWeekFromMonday(
						currentYear,
						firstDayOfWeekCal.get(Calendar.WEEK_OF_YEAR));

				calStartWeek.getTime();

				// getting calendar week of current day of this month starting
				// from Monday
				currentYear = cal.get(Calendar.YEAR);
				int weekNumberTo = cal.get(Calendar.WEEK_OF_YEAR);

				if (weekNumberTo == 52 || weekNumberTo == 53) {
					currentYear--;
				}

				Calendar calEndWeek = CommonUtil.getCalendarWeekFromMonday(
						currentYear, cal.get(Calendar.WEEK_OF_YEAR));

				calEndWeek.getTime();

				// populating periodStrategyMap if end week comes after last
				// week
				if (calEndWeek.after(calStartWeek)) {

					// In this case startResidualDate1 and endResidualDate1 will
					// be starting and ending date of
					// start week and startResidualDate2 and endResidualDate2
					// will be starting and ending date of
					// end week

					periodStrategyMap.put(
							BizConstants.KEY_START_RESIDUAL_DATE1, CommonUtil
									.dateToString(firstDayOfWeekCal.getTime(),
											BizConstants.DATE_FORMAT_YYYYMMDD));

					Calendar calEndResidual1 = (Calendar) calEndWeek.clone();
					calEndResidual1.add(Calendar.DATE, -1);

					periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE1,
							CommonUtil.dateToString(calEndResidual1.getTime(),
									BizConstants.DATE_FORMAT_YYYYMMDD));

					calEndResidual1.add(Calendar.DATE, 1);

					periodStrategyMap.put(
							BizConstants.KEY_START_RESIDUAL_DATE2, CommonUtil
									.dateToString(calEndResidual1.getTime(),
											BizConstants.DATE_FORMAT_YYYYMMDD));

					periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE2,
							CommonUtil.dateToString(cal.getTime(),
									BizConstants.DATE_FORMAT_YYYYMMDD));

				} else {

					// if week change does not occur between first and current
					// dates of this month and week is
					// not completed then only startResidualDate1 and
					// endResidaulDate1 will have values as first
					// and current day of this month
					periodStrategyMap.put(
							BizConstants.KEY_START_RESIDUAL_DATE1, CommonUtil
									.dateToString(firstDayOfWeekCal.getTime(),
											BizConstants.DATE_FORMAT_YYYYMMDD));

					periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE1,
							CommonUtil.dateToString(cal.getTime(),
									BizConstants.DATE_FORMAT_YYYYMMDD));
				}

			} else {

				// if difference between month starting date and current date is
				// more then a week
				// or equal to one day then periodStrategyMap is populated in
				// the below else block.
				// and calling getStatisticsStrategy StatisticsDataStructure
				// class to populate
				// periodStrategyMap

				periodStrategyMap = statsDS.getStatisticsStrategy(
						requestVO.getPeriod(), firstDayOfWeekCal, cal,
						requestVO.getUserTimeZone());
			}

			requestVO.setPeriod(BizConstants.PERIOD_THISMONTH);

			break;

		case BizConstants.PERIOD_THISWEEK:

			Calendar currentDateCal = Calendar.getInstance();

			currentDateCal.setFirstDayOfWeek(Calendar.MONDAY);

			currentDateCal = CommonUtil.setUserTimeZone(currentDateCal,
					requestVO.getUserTimeZone());

			Calendar firstDayOfThisWeekCal = CommonUtil
					.getCalendarWeekFromMonday(
							currentDateCal.get(Calendar.YEAR),
							currentDateCal.get(Calendar.WEEK_OF_YEAR));

			// if first day of this week comes before current day of this week
			// then
			// startRange1 will be first date and endRange1 will have value as
			// date
			// before current date
			if (CommonUtil
					.getCalendarWithDateFormatWithoutTime(firstDayOfThisWeekCal)
					.getTime()
					.before(CommonUtil.getCalendarWithDateFormatWithoutTime(
							currentDateCal).getTime())) {

				periodStrategyMap.put(BizConstants.KEY_START_RANGE1, CommonUtil
						.dateToString(firstDayOfThisWeekCal.getTime()));

				Calendar previousToCurrent = (Calendar) currentDateCal.clone();

				previousToCurrent.add(Calendar.DATE, -1);

				periodStrategyMap.put(BizConstants.KEY_END_RANGE1,
						CommonUtil.dateToString(previousToCurrent.getTime()));

			}

			// startResidualDate1 and endResidualDate1 will have current date as
			// value
			periodStrategyMap.put(BizConstants.KEY_START_RESIDUAL_DATE1,
					CommonUtil.dateToString(currentDateCal.getTime()));

			periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE1,
					CommonUtil.dateToString(currentDateCal.getTime()));

			break;

		case BizConstants.PERIOD_TODAY:

			// startResidualDate1 and endResidualDate1 will have current date as
			// value
			// Modified by ravi
			periodStrategyMap.put(BizConstants.KEY_START_RESIDUAL_DATE1,
				CommonUtil.dateToString(cal.getTime())+" 00:00:00.000");

			periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE1,
				CommonUtil.dateToString(cal.getTime())+" 23:59:59.999");

			break;

		case BizConstants.PERIOD_24HOURS:

			Calendar past24Cal = Calendar.getInstance();

			periodStrategyMap.put(BizConstants.KEY_END_RESIDUAL_DATE1,
					CommonUtil.getCalendarWithDateFormat(
							past24Cal.getTimeInMillis(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

			past24Cal.add(Calendar.HOUR, -23);

			periodStrategyMap.put(BizConstants.KEY_START_RESIDUAL_DATE1,
					CommonUtil.getCalendarWithDateFormat(
							past24Cal.getTimeInMillis(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

			break;
		default:

			// If user selected Range then this default block will be executed
			// and calls
			// getStatisticsStrategy method of StatisticsDataStructure class to
			// populate
			// periodStrategyMap

			if (requestVO.getStartDate() != null
					&& requestVO.getEndDate() != null) {

				Calendar startCal = CommonUtil
						.convertStringToCalendarWithDateFormat(
								requestVO.getStartDate(),
								BizConstants.DATE_FORMAT_YYYYMMDD);

				Calendar endCal = CommonUtil
						.convertStringToCalendarWithDateFormat(
								requestVO.getEndDate(),
								BizConstants.DATE_FORMAT_YYYYMMDD);

				Long dateDiffForRange = CommonUtil.getDifferenceInDays(
						CommonUtil.dateToString(startCal.getTime(),
								BizConstants.DATE_FORMAT_YYYYMMDD), CommonUtil
								.dateToString(endCal.getTime(),
										BizConstants.DATE_FORMAT_YYYYMMDD));

				requestVO
						.setPeriod(getPeriodStrategicByRange(dateDiffForRange));

				periodStrategyMap = statsDS.getStatisticsStrategy(
						requestVO.getPeriod(), startCal, endCal,
						requestVO.getUserTimeZone());

			}

			break;

		}

		return periodStrategyMap;

	}

	/**
	 * This method is used determine the range period (year,month,week,day) on
	 * the basis of day difference between startDate and endDate.
	 * 
	 * @param dateDiff
	 * @return
	 */
	private String getPeriodStrategicByRange(Long dateDiff) {

		String rangePeriod = BizConstants.EMPTY_STRING;

		if (dateDiff.equals(Long.valueOf((String) bizProperties
				.get("stats.datediff.hours")))) {

			rangePeriod = BizConstants.RANGE_DAY;

		} else if (dateDiff.equals(Long.valueOf((String) bizProperties
				.get("stats.datediff.days")))
				|| dateDiff.compareTo((Long.valueOf((String) bizProperties
						.get("stats.datediff.days")))) < 0) {

			rangePeriod = BizConstants.RANGE_WEEK;

		} else if (dateDiff.equals(Long.valueOf((String) bizProperties
				.get("stats.datediff.weeks")))
				|| dateDiff.compareTo((Long.valueOf((String) bizProperties
						.get("stats.datediff.weeks")))) < 0) {

			rangePeriod = BizConstants.RANGE_MONTH;

		} else if (dateDiff.equals(Long.valueOf((String) bizProperties
				.get("stats.datediff.months")))
				|| dateDiff.compareTo((Long.valueOf((String) bizProperties
						.get("stats.datediff.months")))) < 0) {

			rangePeriod = BizConstants.RANGE_YEAR;

		} /*
		 * else if (dateDiff <= Long.valueOf((String) bizProperties
		 * .get("stats.datediff.years"))) {
		 * 
		 * rangePeriod = BizConstants.RANGE_3YEAR; }
		 */else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_RESOURCE_NOT_AVAILABLE);

			throw new GenericFailureException(customErrorMessage);
		}

		return rangePeriod;
	}

	/**
	 * 
	 * @param requestVO
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	@Override
	public void setPeriodStrategy(StatsRequestVO requestVO)
			throws GenericFailureException, ParseException,
			NumberFormatException {

		Map<String, Object> periodStrategyMap = getPeriodStrategy(requestVO);

		periodStrategyMap.put(BizConstants.KEY_IDS,
				requestVO.getId().split(BizConstants.COMMA_STRING));

		if ((StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_INDOOR)
				|| StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.ID_TYPE_OUTDOOR) || StringUtils
					.equalsIgnoreCase(requestVO.getIdType(),
							BizConstants.STATISTICS_REFRIGERANT))) {

			if (requestVO.getId() != null) {

				String[] idArr = requestVO.getId().split(
						BizConstants.COMMA_STRING);
				for (String id : idArr) {

					requestVO.getIdList().add(Long.valueOf(id));

				}

			}

			periodStrategyMap.put(BizConstants.KEY_IDS, requestVO.getIdList());
			// requestVO.getIdList().add(Long.valueOf(id));

		} else {

			periodStrategyMap.put(BizConstants.KEY_IDS, requestVO.getId());
		}

		requestVO.setPeriodStrategyMap(periodStrategyMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService#
	 * getDataForEnergyConsumptionGraph
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getDataForEnergyConsumptionGraph(StatsRequestVO requestVO)
			throws JsonProcessingException, GenericFailureException {

		String jsonResponse = BizConstants.EMPTY_STRING;

		Calendar startCal = null;

		Calendar endCal = null;

		List<Companiesuser> companiesuser = companyUserService
				.findGroupFromCompaniesuserByUser(Long.parseLong(requestVO
						.getPeriodStrategyMap().get(BizConstants.KEY_USER_ID)
						+ BizConstants.EMPTY_STRING));

		if (companiesuser.size() == 0) {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.USER_GROUP_NOT_AVAILABLE);
			throw new GenericFailureException(customErrorMessage);

		}

		if (requestVO.getStartDate() != null && requestVO.getEndDate() != null) {

			startCal = CommonUtil
					.convertStringToCalendarWithDateFormat(
							requestVO.getStartDate(),
							BizConstants.DATE_FORMAT_YYYYMMDD);

			endCal = CommonUtil.convertStringToCalendarWithDateFormat(
					requestVO.getEndDate(), BizConstants.DATE_FORMAT_YYYYMMDD);

			if (endCal.before(startCal)) {
				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
				throw new GenericFailureException(customErrorMessage);
			}

		} else {

			startCal = CommonUtil.setUserTimeZone(Calendar.getInstance(),
					requestVO.getUserTimeZone());
			startCal.add(Calendar.DATE, -7);

			endCal = CommonUtil.setUserTimeZone(Calendar.getInstance(),
					requestVO.getUserTimeZone());
			endCal.add(Calendar.DATE, -1);

		}

		Long dateDiffForRange = CommonUtil.getDifferenceInDays(CommonUtil
				.dateToString(startCal.getTime(),
						BizConstants.DATE_FORMAT_YYYYMMDD), CommonUtil
				.dateToString(endCal.getTime(),
						BizConstants.DATE_FORMAT_YYYYMMDD));

		requestVO.setPeriod(getPeriodStrategicByRange(dateDiffForRange));

		if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
				BizConstants.RANGE_DAY)) {

			requestVO.setPeriod(BizConstants.RANGE_WEEK);
		}

		requestVO.setPeriodStrategyMap(statsDS.getStatisticsStrategy(
				requestVO.getPeriod(), startCal, endCal,
				requestVO.getUserTimeZone()));

		List<Group> groupList = (List<Group>) CollectionUtils.collect(
				companiesuser, new BeanToPropertyValueTransformer("group"));

		List<Long> idList = (List<Long>) CollectionUtils.collect(groupList,
				new BeanToPropertyValueTransformer("id"));

		String StrIdList = StringUtils.join(idList, ',');

		requestVO.getPeriodStrategyMap().put(BizConstants.KEY_IDS, StrIdList);

		requestVO.setType(BizConstants.STATISTICS_CHRONOLOGICAL);

		requestVO.setParameter(BizConstants.POWER_CONSUMPTION);

		requestVO.setIdType(BizConstants.ID_TYPE_GROUP);

		requestVO
				.setApiCallFor(BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION);

		EnergyConsumptionResponseVO responseVO = statssqldao
				.getDataForEnergyConsumptionGraph(requestVO);

		jsonResponse = CommonUtil.convertFromEntityToJsonStr(responseVO);

		return jsonResponse;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService#
	 * getRefrigerantCircuitsByGroupIds
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public String getRefrigerantCircuitsByGroupIds(StatsRequestVO requestVO)
			throws JsonProcessingException, GenericFailureException {

		String jsonResponse = BizConstants.EMPTY_STRING;

		if (requestVO.getId() != null) {

			String[] idArr = requestVO.getId().split(BizConstants.COMMA_STRING);
			for (String id : idArr) {

				requestVO.getIdList().add(Long.valueOf(id));
			}

		}

		requestVO.getPeriodStrategyMap().put(BizConstants.KEY_IDS,
				requestVO.getId());

		RefrigerantCircuitResponseVO responseVO = statssqldao
				.getRefrigerantCircuitByGroupIds(requestVO);

		jsonResponse = CommonUtil.convertFromEntityToJsonStr(responseVO);

		return jsonResponse;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService#
	 * getStatsReportDetails
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public List<StatsReportVO> getStatsReportDetails(StatsRequestVO requestVO)
			throws HibernateException, GenericFailureException, ParseException {

		List<StatsReportVO> statsReportVOList = null;

		if (!StringUtils.equalsIgnoreCase(requestVO.getFileType(),
				BizConstants.REPORT_TYPE_CSV)
				&& !StringUtils.equalsIgnoreCase(requestVO.getFileType(),
						BizConstants.REPORT_TYPE_EXCEL)) {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			throw new GenericFailureException(customErrorMessage);

		} else {

			setPeriodStrategy(requestVO);

			statsReportVOList = statssqldao.getStatsReport(requestVO);

		}

		return statsReportVOList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService#
	 * getStatsFilePath(java.util.List,
	 * com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public String getStatsFilePath(List<StatsReportVO> statsList,
			StatsRequestVO requestVO) throws IOException, URISyntaxException,
			IllegalAccessException, InvocationTargetException {

		return statsReportGenerator.getFilePath(statsList, requestVO);

	}

}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyGroupRefVO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRankingDownloadVO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRankingResponseVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.GroupLevel;
import com.panasonic.b2bacns.bizportal.service.CompanyUserService;
import com.panasonic.b2bacns.bizportal.service.GroupLevelService;
import com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO;
import com.panasonic.b2bacns.bizportal.stats.service.ReportUtil;
import com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsResponseVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.common.reports.DataTableHeader;
import com.panasonic.b2bacns.common.reports.HeadingTextProperties;
import com.panasonic.b2bacns.common.reports.Logo;
import com.panasonic.b2bacns.common.reports.ReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportMetadata;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportGenerator;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportMetadata;

/**
 * @author Narendra.Kumar
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EfficiencyRankingServiceImpl implements EfficiencyRankingService {

	private static final Logger logger = Logger
			.getLogger(EfficiencyRankingServiceImpl.class);

	@Autowired
	private StatsManagementService statsManagementService;

	@Autowired
	private StatsSQLDAO statssqldao;

	@Autowired
	private GroupLevelService groupLevelService;

	@Autowired
	private CompanyUserService companyUserService;

	@Autowired
	private SQLDAO sqldao;

	private static final StringBuilder SQL_GET_EFFCIENCY_AFTER_LEVEL_6 = new StringBuilder(
			"select supplygroupname,supplygroupid,refid,refrigerantid,t.status, g.path_name,c.name from (")
			.append("Select supplygroupname,supplygroupid,refid,refrigerantid,case when count(supplygroupstatus)>1 then 'False' else 'True' end status from ( ")
			.append("Select distinct fn.supplygroupname,fn.supplygroupid,rm.refid as refid,rm.refrigerantid as refrigerantid, case when (gidu.path LIKE ('%|'||fn.supplygroupid||'|%')) ")
			.append("then 1 else 0 end supplygroupstatus ,gidu.path from (Select distinct supplygroupid,siteid,supplygroupname from ")
			.append("usp_getindoorunits_supplylevelgroupname(:groupIds,:groupLevel) where indoorunitid is not null) fn left outer join refrigerantmaster rm ")
			.append("on rm.siteid =  fn.siteid left outer join outdoorunits odu on rm.refid = odu.refid and odu.parentid is null ")
			.append("left outer join indoorunits idu on odu.id = idu.outdoorunit_id left outer join groups gidu on gidu.id = idu.group_id ")
			.append("order by fn.supplygroupid ,rm.refid) temp group by supplygroupid,refrigerantid,supplygroupname,refid ) t ")
			.append("left join groups g on g.id = t.supplygroupid left join companies c on c.id = g.company_id");

	@SuppressWarnings("unchecked")
	@Override
	public String getEfficiencyRanking(StatsRequestVO requestVO)
			throws JsonProcessingException, ParseException {

		Map<String, List<EfficiencyGroupRefVO>> refMap = null;
		List<EfficiencyGroupRefVO> efficiencyGroupRefVOList = null;
		if (requestVO.getGrouplevel() > 6) {
			efficiencyGroupRefVOList = new ArrayList<EfficiencyGroupRefVO>();
			getEfficiencyAfterLevel6(requestVO, efficiencyGroupRefVOList);
		}
		StatsResponseVO responseVO = getStatsDetails(requestVO);
		List<String> categoryList = responseVO.getCategories();
		List<Map<String, Object>> series = responseVO.getSeries();
		Map<String, Double> efficiencyRankingMap = new HashMap<String, Double>();
		if (series != null && !series.isEmpty()) {
			if (series.get(0) != null && !series.get(0).isEmpty()) {
				if (series.get(0).containsKey(
						BizConstants.KEY_JSON_RESPONSE_NAME)
						&& series.get(0).containsKey(
								BizConstants.KEY_JSON_RESPONSE_DATA)) {
					if (StringUtils.equalsIgnoreCase((String) series.get(0)
							.get(BizConstants.KEY_JSON_RESPONSE_NAME),
							BizConstants.SERIES_EFFICIENCY)) {
						List<Double> list = (List<Double>) series.get(0).get(
								BizConstants.KEY_JSON_RESPONSE_DATA);
						if (categoryList.size() == list.size()) {
							if (requestVO.getGrouplevel() > 6) {
								for (int i = 0; i < categoryList.size(); i++) {
									String refid = categoryList.get(i).split(
											"-")[2].replace("RC", "");
									try {
										EfficiencyGroupRefVO efficiencyGroupRefObj = efficiencyGroupRefVOList
												.stream()
												.filter(efficiencyGroupRef -> efficiencyGroupRef
														.getRefrigerantId()
														.equals(new Long(refid)))
												.findFirst().get();
										efficiencyGroupRefObj
												.setEfficiency(list.get(i));
									} catch (NoSuchElementException ex) {
										// Do Nothing
										logger.error("Element not found");
									}

								}
								refMap = efficiencyGroupRefVOList
										.stream()
										.collect(
												Collectors
														.groupingBy(EfficiencyGroupRefVO::getKey));
								for (Map.Entry<String, List<EfficiencyGroupRefVO>> entry : refMap
										.entrySet()) {
									efficiencyRankingMap
											.put(entry.getKey(),
													entry.getValue()
															.stream()
															.mapToDouble(
																	vo -> vo.getEfficiency() == null ? 0
																			: vo.getEfficiency())
															.sum());
								}

							} else {
								for (int i = 0; i < categoryList.size(); i++) {
									Double efficiencAvgVal = 0d;
									if (list.get(i) != null) {
										efficiencAvgVal = list.get(i);
									}
									efficiencyRankingMap.put(
											categoryList.get(i),
											efficiencAvgVal);

								}
							}
						}
					}
				}
			}
		}

		String jsonResponse = "";
		Map<String, Double> efficiencyRankingDescMap = new HashMap<String, Double>();
		if (efficiencyRankingMap != null && !efficiencyRankingMap.isEmpty()) {
			efficiencyRankingDescMap = (Map<String, Double>) CommonUtil
					.mapSortedByValues(efficiencyRankingMap);
		}

		List<String> compare_by = new LinkedList<String>();
		List<Double> value = new LinkedList<Double>();
		EfficiencyRankingResponseVO efficiencyRankingResponseVO = new EfficiencyRankingResponseVO();

		for (Entry<String, Double> entry : efficiencyRankingDescMap.entrySet()) {
			compare_by.add(entry.getKey().split("-")[0]);
			value.add(CommonUtil.getFormattedValueUpToTwoDecimal(entry
					.getValue()));
		}

		if (compare_by != null && !compare_by.isEmpty() && value != null
				&& !value.isEmpty()) {
			efficiencyRankingResponseVO.setCompare_by(compare_by);
			efficiencyRankingResponseVO.setValue(value);
			jsonResponse = CommonUtil
					.convertFromEntityToJsonStr(efficiencyRankingResponseVO);

		}

		return jsonResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.efficiency.service.EfficiencyRankingService
	 * #getFilePath(java.util.List,
	 * com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public String getFilePath(StatsRequestVO requestVO) throws IOException,
			URISyntaxException, IllegalAccessException,
			InvocationTargetException, HibernateException,
			GenericFailureException, ParseException {

		List<StatsReportVO> statsList = null;
		String filePath = BizConstants.EMPTY_STRING;
		List<EfficiencyRankingDownloadVO> downloadVOList = new ArrayList<EfficiencyRankingDownloadVO>();
		Map<String, Double> efficiencyRankingMap = new HashMap<String, Double>();
		Map<String, StatsReportVO> efficiencyRankingMapWithData = new HashMap<String, StatsReportVO>();
		Map<String, List<EfficiencyGroupRefVO>> refMap = null;
		List<EfficiencyGroupRefVO> efficiencyGroupRefVOList = null;

		if (requestVO.getGrouplevel() > 6) {
			efficiencyGroupRefVOList = new ArrayList<EfficiencyGroupRefVO>();
			getEfficiencyAfterLevel6(requestVO, efficiencyGroupRefVOList);
			statsList = statsManagementService.getStatsReportDetails(requestVO);

			for (int i = 0; i < statsList.size(); i++) {
				String refid = statsList.get(i).getLevelName().split("/")[1]
						.split("-")[2];
				try {
					EfficiencyGroupRefVO efficiencyGroupRefObj = efficiencyGroupRefVOList
							.stream()
							.filter(efficiencyGroupRef -> efficiencyGroupRef
									.getRefrigerantId().equals(new Long(refid)))
							.findFirst().get();
					efficiencyGroupRefObj.setEfficiency(statsList.get(i)
							.getEfficiency());
				} catch (NoSuchElementException ex) {
					// Do Nothing
					logger.error("Element not found");
				}

			}
			refMap = efficiencyGroupRefVOList.stream().collect(
					Collectors.groupingBy(EfficiencyGroupRefVO::getSitePath));
			for (Map.Entry<String, List<EfficiencyGroupRefVO>> entry : refMap
					.entrySet()) {
				efficiencyRankingMap.put(
						entry.getKey(),
						entry.getValue()
								.stream()
								.mapToDouble(
										vo -> vo.getEfficiency() == null ? 0d
												: vo.getEfficiency()).sum());
			}

		} else {
			statsList = statsManagementService.getStatsReportDetails(requestVO);
			if (statsList != null && !statsList.isEmpty()
					&& statsList.size() > 0) {

				for (StatsReportVO stats : statsList) {
					efficiencyRankingMap.put(stats.getLevelName(),
							stats.getEfficiency() == null ? new Double(0)
									: stats.getEfficiency());
					efficiencyRankingMapWithData.put(stats.getLevelName(),
							stats);
				}
			}

		}

		Map<String, Double> efficiencyRankingDescMap = new HashMap<String, Double>();
		if (efficiencyRankingMap != null && !efficiencyRankingMap.isEmpty()) {
			efficiencyRankingDescMap = (Map<String, Double>) CommonUtil
					.mapSortedByValues(efficiencyRankingMap);
		}
		int count = 1;
		for (Entry<String, Double> entry : efficiencyRankingDescMap.entrySet()) {
			EfficiencyRankingDownloadVO downloadVO = new EfficiencyRankingDownloadVO();
			downloadVO.setLevel(entry.getKey());
			downloadVO.setValue(CommonUtil
					.getFormattedValueUpToTwoDecimal(entry.getValue()));
			downloadVO.setEfficiencyRanking(count++);

			if (requestVO.getGrouplevel() > 6) {
				downloadVO.setCustomerName(efficiencyGroupRefVOList
						.stream()
						.filter(efficiencyGroupRef -> efficiencyGroupRef
								.getSitePath().equals(entry.getKey()))
						.findFirst().get().getCompanyName());
			} else {
				downloadVO.setCustomerName(efficiencyRankingMapWithData.get(
						entry.getKey()).getCustomerName());
			}
			downloadVOList.add(downloadVO);

		}

		if (StringUtils.equalsIgnoreCase(requestVO.getFileType(),
				BizConstants.REPORT_TYPE_EXCEL)) {

			// generate excel file for given list at a specific position
			filePath = generateEfficiencyRankingExcelReport(downloadVOList,
					requestVO);

		} else {

			// generate csv file for given list at a specific position
			filePath = generateEfficiencyRankingCsvReport(downloadVOList,
					requestVO);

		}

		return filePath;
	}

	// @Override
	public StatsResponseVO getStatsDetails(StatsRequestVO requestVO)
			throws JsonProcessingException, GenericFailureException,
			ParseException {
		statsManagementService.setPeriodStrategy(requestVO);
		StatsResponseVO responseVO = statssqldao.getStatsDetails(requestVO);
		return responseVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getEfficiencyOnDashboard(Long userID, String userTimezone)
			throws GenericFailureException, ParseException,
			JsonProcessingException {

		List<Companiesuser> companiesuser = companyUserService
				.findGroupFromCompaniesuserByUser(userID);
		if (companiesuser.size() == 0) {
			throw new GenericFailureException(
					BizConstants.USER_GROUP_NOT_AVAILABLE);

		}
		List<Group> groupList = (List<Group>) CollectionUtils.collect(
				companiesuser, new BeanToPropertyValueTransformer("group"));
		groupList.removeIf(s -> s.getGroupLevel().getId() > 6);

		if (groupList.isEmpty()) {
			throw new GenericFailureException(
					BizConstants.USER_GROUP_NOT_AVAILABLE);
		}
		List<GroupLevel> group_level = (List<GroupLevel>) CollectionUtils
				.collect(groupList, new BeanToPropertyValueTransformer(
						"groupLevel"));
		List<Long> group_level_id = (List<Long>) CollectionUtils.collect(
				group_level, new BeanToPropertyValueTransformer("id"));
		List<Long> idList = (List<Long>) CollectionUtils.collect(groupList,
				new BeanToPropertyValueTransformer("id"));
		String idListString = StringUtils.join(idList, ',');

		Long groupLevel = group_level_id.stream()
				.min(Comparator.comparing(i -> i)).get();

		StatsRequestVO requestVO = new StatsRequestVO();
		requestVO.setGrouplevel(groupLevel.intValue());
		requestVO.setId(idListString);

		requestVO.setIdType("group");
		requestVO.setApiCallFor(BizConstants.STATS_API_CALL_BY_GROUP);
		requestVO.setChartType(BizConstants.API_CHART_EFFICIENCY_RANKING);

		Calendar endCal = GregorianCalendar.getInstance();
		endCal.setFirstDayOfWeek(Calendar.MONDAY);
		endCal = CommonUtil.setUserTimeZone(endCal, userTimezone);
		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setFirstDayOfWeek(Calendar.MONDAY);
		startCal = CommonUtil.setUserTimeZone(startCal, userTimezone);
		startCal.add(GregorianCalendar.DATE, -6);

		String startDate = CommonUtil.dateToString(startCal.getTime(),
				BizConstants.DATE_FORMAT_YYYYMMDD);
		String endDate = CommonUtil.dateToString(endCal.getTime(),
				BizConstants.DATE_FORMAT_YYYYMMDD);

		requestVO.setStartDate(startDate);
		requestVO.setEndDate(endDate);

		requestVO.setType("accumulated");
		requestVO.setParameter("efficiency");

		requestVO.setUserTimeZone(userTimezone);

		String efficiency = getEfficiencyRanking(requestVO);

		return efficiency;
	}

	private void getEfficiencyAfterLevel6(StatsRequestVO requestVO,
			List<EfficiencyGroupRefVO> efficiencyGroupRefVOList)
			throws JsonProcessingException {
		EfficiencyGroupRefVO efficiencyGroupRefObj = null;

		Map<String, Object> queryMap = new LinkedHashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_IDS, requestVO.getId());
		queryMap.put(BizConstants.GROUP_LEVEL, requestVO.getGrouplevel());
		String sqlQuery = SQL_GET_EFFCIENCY_AFTER_LEVEL_6.toString();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put("supplygroupname", StandardBasicTypes.STRING); // 0
		scalarMapping.put("supplygroupid", StandardBasicTypes.LONG); // 1
		scalarMapping.put("refrigerantid", StandardBasicTypes.LONG); // 2
		scalarMapping.put("status", StandardBasicTypes.STRING); // 3
		scalarMapping.put("path_name", StandardBasicTypes.STRING); // 4
		scalarMapping.put("name", StandardBasicTypes.STRING); // 5
		scalarMapping.put("refid", StandardBasicTypes.LONG); // 6

		List<?> result = sqldao.executeSQLSelect(sqlQuery, scalarMapping,
				queryMap);

		if (!result.isEmpty()) {

			Iterator<?> itr = result.iterator();
			Object[] rowData = null;

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();
				if (rowData[3].toString().equalsIgnoreCase("true")) {
					efficiencyGroupRefObj = new EfficiencyGroupRefVO();
					efficiencyGroupRefObj.setSupplyGroupName(rowData[0]
							.toString());
					efficiencyGroupRefObj.setSupplyGroupId((Long) rowData[1]);
					efficiencyGroupRefObj.setRefrigerantId((Long) rowData[2]);
					efficiencyGroupRefObj.setSitePath(rowData[4].toString());
					efficiencyGroupRefObj.setCompanyName(rowData[5].toString());
					efficiencyGroupRefObj.setRefId((Long) rowData[6]);
					efficiencyGroupRefVOList.add(efficiencyGroupRefObj);
				}
			}
		}
		@SuppressWarnings("unchecked")
		String refId = StringUtils.join((List<Long>) CollectionUtils.collect(
				efficiencyGroupRefVOList, new BeanToPropertyValueTransformer(
						"refId")), ',');
		if (!efficiencyGroupRefVOList.isEmpty() && !refId.isEmpty()) {
			requestVO.setIdType("refrigerantcircuit");
			requestVO
					.setApiCallFor(BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT);
			requestVO.setId(refId);
			requestVO.setChartType(null);
		} else {
			throw new GenericFailureException(
					CommonUtil
							.convertFromEntityToJsonStr(BizConstants.NO_RECORDS_FOUND));
		}
	}

	private String generateEfficiencyRankingExcelReport(
			List<EfficiencyRankingDownloadVO> efficiencyRankingList,
			StatsRequestVO requestVO) throws IllegalAccessException,
			InvocationTargetException {

		// Generates an excel report
		ReportGenerator<EfficiencyRankingDownloadVO> fixture = new ExcelReportGenerator<EfficiencyRankingDownloadVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				EfficiencyRankingDownloadVO.class, "Efficiency Ranking",
				"Efficiency-Ranking-" + new Date().getTime());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Efficiency Ranking");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setEfficiencyRankingHeaderText(requestVO);
		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setEfficiencyRankingTableHeader(requestVO);
		metadata.setDataTableHeading(tableHeadings);

		Collection<EfficiencyRankingDownloadVO> dataList = efficiencyRankingList;

		String fileName = null;

		try {
			// Writes the excel metadata and dataList received in the excel
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			logger.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	private String generateEfficiencyRankingCsvReport(
			List<EfficiencyRankingDownloadVO> efficiencyRankingList,
			StatsRequestVO requestVO) throws IllegalAccessException,
			InvocationTargetException {

		// Generates a csv report
		ReportGenerator<EfficiencyRankingDownloadVO> fixture = new CSVReportGenerator<EfficiencyRankingDownloadVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				EfficiencyRankingDownloadVO.class, "Efficiency Ranking",
				"Efficiency-Ranking-" + new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setEfficiencyRankingHeaderText(requestVO);
		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setEfficiencyRankingTableHeader(requestVO);
		metadata.setDataTableHeading(tableHeadings);

		Collection<EfficiencyRankingDownloadVO> dataList = efficiencyRankingList;

		String fileName = null;

		try {
			// Writes the csv metadata and dataList received in the csv
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			logger.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	/**
	 * Set Header for report for EfficiencyRanking download
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private List<HeadingTextProperties> setEfficiencyRankingHeaderText(
			StatsRequestVO requestVO) throws IllegalAccessException,
			InvocationTargetException {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Efficiency Ranking");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		headertext.setValue(CommonUtil.dateToString(new Date(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_DATE_RANGE);
		headertext.setValue(ReportUtil.getDateRange(requestVO));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_LEVEL_SELECTION);

		headertext.setValue(groupLevelService.getLevelNameById(
				requestVO.getGrouplevel()).getName());

		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	/**
	 * Set column names for EfficiencyRanking download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setEfficiencyRankingTableHeader(
			StatsRequestVO requestVO) {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();
		DataTableHeader heading = new DataTableHeader();

		if (StringUtils.equalsIgnoreCase(requestVO.getAddCustName(),
				BizConstants.YES)) {

			heading = new DataTableHeader();
			heading.setColumnName("customerName");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_CUSTOMER_NAME);
			heading.setSequence(0);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);
		}

		heading = new DataTableHeader();
		heading.setColumnName("efficiencyRanking");
		heading.setDisplayName("Efficiency Ranking");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("value");
		heading.setDisplayName("Efficiency Level (%)");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("level");
		heading.setDisplayName("Group/Site Name");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;
	}

}

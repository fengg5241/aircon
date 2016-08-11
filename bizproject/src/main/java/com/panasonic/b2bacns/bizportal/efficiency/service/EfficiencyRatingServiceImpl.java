package com.panasonic.b2bacns.bizportal.efficiency.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRatingRequestVO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRatingResponseVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.GroupLevel;
import com.panasonic.b2bacns.bizportal.service.CompanyUserService;
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
public class EfficiencyRatingServiceImpl implements EfficiencyRatingService {

	private static final Logger LOGGER = Logger
			.getLogger(EfficiencyRatingServiceImpl.class);

	// Modified by Ravi
	private static final StringBuilder SQL_GET_EFFICIENCY_DETAIL_BY_GROUP_AND_LEVEL = new StringBuilder(
			"Select fnouter.groupid ,fnouter.supplygroupname, fnouter.path, sum(t.pctotal_power_consumption) as pctotal_power_consumption, ")
			.append("fnouter.co2factor as co2factor from usp_getindoorunits_supplylevelgroupname_rating(:groupIds,:groupLevel,:date) fnouter ")
			.append("left outer join (Select ( COALESCE(avg(pc.total_power_consumption),0)) pctotal_power_consumption,fn.supplygroupname,")
			.append("pc.indoorunit_id as pcindoorunit_id from usp_getindoorunits_supplylevelgroupname(:groupIds1,:groupLevel1) fn ")
			.append("Left Outer Join indoorunits idu on idu.id = fn.indoorunitid Left Outer Join (Select sum(total_power_consumption) total_power_consumption,")
			.append("count(indoorunit_id) pccount , indoorunit_id from power_consumption_capacity pc where (cast(pc.logtime as date) between to_date(:startDate,'YYYY-MM-DD') ")
			.append("and to_date(:endDate,'YYYY-MM-DD')) group by pc.indoorunit_id) pc on idu.id = pc.indoorunit_id group by pc.indoorunit_id, fn.supplygroupname) T ")
			.append("On fnouter.supplygroupname = t.supplygroupname  and ( fnouter.indoorunitid = t.pcindoorunit_id  or fnouter.indoorunitid = t.pcindoorunit_id) ")
			.append("group by fnouter.supplygroupname,fnouter.co2factor,fnouter.groupid,fnouter.path");

	@Autowired
	private SQLDAO sqldao;

	@Autowired
	private CompanyUserService companyUserService;

	@Override
	@SuppressWarnings("unchecked")
	public List<EfficiencyRatingResponseVO> getEfficiencyRatingByGroupsAndLevel(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO) {
		List<EfficiencyRatingResponseVO> finalEfficiencyRatingResponseVOs = null;

		List<Companiesuser> companiesuser = companyUserService
				.findGroupFromCompaniesuserByUser(efficiencyRatingRequestVO
						.getUserId());

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
		List<Long> idList = (List<Long>) CollectionUtils.collect(groupList,
				new BeanToPropertyValueTransformer("id"));
		List<GroupLevel> group_level = (List<GroupLevel>) CollectionUtils
				.collect(groupList, new BeanToPropertyValueTransformer(
						"groupLevel"));
		List<Long> group_level_id = (List<Long>) CollectionUtils.collect(
				group_level, new BeanToPropertyValueTransformer("id"));

		Long groupLevel = group_level_id.stream()
				.min(Comparator.comparing(i -> i)).get();

		String StrIdList = StringUtils.join(idList, ',');
		efficiencyRatingRequestVO.setGroupId(StrIdList);
		efficiencyRatingRequestVO.setGrouplevel(groupLevel.intValue());
		if (efficiencyRatingRequestVO.getGrouplevel() < 6) {
			finalEfficiencyRatingResponseVOs = getEfficiencyRatinguptoSiteAndLevel(efficiencyRatingRequestVO);
		} else {
			finalEfficiencyRatingResponseVOs = getEfficiencyRating(efficiencyRatingRequestVO);
		}

		return finalEfficiencyRatingResponseVOs;
	}

	@Override
	public List<EfficiencyRatingResponseVO> getEfficiencyRating(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO) {
		List<?> resultList = getEfficiencyDetailByGroupAndLevel(efficiencyRatingRequestVO);
		List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs = new ArrayList<EfficiencyRatingResponseVO>();

		Object[] columns = null;
		
		if (resultList != null) {
			for (Object row : resultList) {
				columns = (Object[]) row;

				EfficiencyRatingResponseVO efficiencyRatingResponseVO = new EfficiencyRatingResponseVO();
				if (columns[0] != null)
					efficiencyRatingResponseVO
							.setGroupId(columns[0].toString());
				efficiencyRatingResponseVO.setGroupName((String) columns[1]);
				Integer treeCount = 0;

				Double powerConsumptionVal = 0.0;
				Double co2factorVal = 0.0;
				// (Integer) columns[2]
				if (columns[3] != null)
					powerConsumptionVal = Double.parseDouble(columns[3]
							.toString());
				if (columns[4] != null)
					co2factorVal = Double.parseDouble(columns[4].toString());

				Double treeCountDouble = powerConsumptionVal * co2factorVal
						/ BizConstants.EFFICIENCY_TREE_COUNT;
				treeCount = treeCountDouble.intValue();
				efficiencyRatingResponseVO.setTreesCount(treeCount);
				efficiencyRatingResponseVO.setPath((String) columns[2]);
				efficiencyRatingResponseVO
						.setPowerConsumption(powerConsumptionVal);
				efficiencyRatingResponseVO.setCo2FactorVal(co2factorVal);
				efficiencyRatingResponseVOs.add(efficiencyRatingResponseVO);
			}
		}
		return efficiencyRatingResponseVOs;
	}

	public List<EfficiencyRatingResponseVO> getEfficiencyRatinguptoSiteAndLevel(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO) {
		List<EfficiencyRatingResponseVO> finalEfficiencyRatingResponseVOs = new ArrayList<EfficiencyRatingResponseVO>();
		List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs = getEfficiencyRating(efficiencyRatingRequestVO);
		EfficiencyRatingRequestVO efficiencyRatingRequestVOFor6thLevel = new EfficiencyRatingRequestVO();
		efficiencyRatingRequestVOFor6thLevel.setGrouplevel(6);
		efficiencyRatingRequestVOFor6thLevel
				.setGroupId(efficiencyRatingRequestVO.getGroupId());
		efficiencyRatingRequestVOFor6thLevel
				.setUserTimeZone(efficiencyRatingRequestVO.getUserTimeZone());
		List<EfficiencyRatingResponseVO> efficiencyRatingResponse6thLevelVOs = getEfficiencyRating(efficiencyRatingRequestVOFor6thLevel);
		Map<String, Double> efficiencyRatingResponseVOMap = new LinkedHashMap<String, Double>();
		for (EfficiencyRatingResponseVO efficiencyRatingResponseVO : efficiencyRatingResponseVOs) {
			efficiencyRatingResponseVOMap.put(
					efficiencyRatingResponseVO.getGroupId(), 0.0);
		}
		for (EfficiencyRatingResponseVO efficiencyRatingResponse6thLevelVO : efficiencyRatingResponse6thLevelVOs) {
			String pathString[] = efficiencyRatingResponse6thLevelVO.getPath()
					.split("\\|");
			String groupId = pathString[efficiencyRatingRequestVO
					.getGrouplevel()];
			if (efficiencyRatingResponseVOMap.containsKey(groupId)) {
				Double powerAndCo2TempVal = efficiencyRatingResponse6thLevelVO
						.getCo2FactorVal()
						* efficiencyRatingResponse6thLevelVO
								.getPowerConsumption();
				Double powerAndCo2Val = efficiencyRatingResponseVOMap
						.get(groupId);
				Double powerAndCo2Sum = powerAndCo2TempVal + powerAndCo2Val;
				efficiencyRatingResponseVOMap.put(groupId, powerAndCo2Sum);
			}

			else {
				Double powerAndCo2 = efficiencyRatingResponse6thLevelVO
						.getCo2FactorVal()
						* efficiencyRatingResponse6thLevelVO
								.getPowerConsumption();
				efficiencyRatingResponseVOMap.put(groupId, powerAndCo2);
			}
		}

		for (EfficiencyRatingResponseVO efficiencyRatingResponseVO : efficiencyRatingResponseVOs) {
			String groupId = efficiencyRatingResponseVO.getGroupId();

			Double treeCountDouble = efficiencyRatingResponseVOMap.get(groupId)
					/ BizConstants.EFFICIENCY_TREE_COUNT;
			int treeCount = treeCountDouble.intValue();
			efficiencyRatingResponseVO.setTreesCount(treeCount);
			finalEfficiencyRatingResponseVOs.add(efficiencyRatingResponseVO);
		}
		return finalEfficiencyRatingResponseVOs;
	}

	public Boolean isStringContains() {
		Boolean isContain = Boolean.FALSE;

		return isContain;
	}

	@Override
	public List<?> getEfficiencyDetailByGroupAndLevel(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO) {
		Calendar endCal = GregorianCalendar.getInstance();
		String userTimeZone = efficiencyRatingRequestVO.getUserTimeZone();
		endCal.setFirstDayOfWeek(Calendar.MONDAY);
		endCal = CommonUtil.setUserTimeZone(endCal, userTimeZone);
		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setFirstDayOfWeek(Calendar.MONDAY);
		startCal = CommonUtil.setUserTimeZone(startCal, userTimeZone);
		startCal.add(GregorianCalendar.DATE, -6);

		String startDate = CommonUtil.dateToString(startCal.getTime(),
				BizConstants.DATE_FORMAT_YYYYMMDD);
		String endDate = CommonUtil.dateToString(endCal.getTime(),
				BizConstants.DATE_FORMAT_YYYYMMDD);
		Map<String, Object> queryMap = new LinkedHashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_IDS,
				efficiencyRatingRequestVO.getGroupId());
		queryMap.put(BizConstants.GROUP_LEVEL,
				efficiencyRatingRequestVO.getGrouplevel());
		queryMap.put(BizConstants.GROUP_IDS1,
				efficiencyRatingRequestVO.getGroupId());
		queryMap.put(BizConstants.GROUP_LEVEL1,
				efficiencyRatingRequestVO.getGrouplevel());
		queryMap.put(BizConstants.START_DATE, startDate);
		queryMap.put(BizConstants.END_DATE, endDate);
		//Modifed by ravi
		queryMap.put(
				"date",
				new java.sql.Timestamp(CommonUtil.stringToDate(endDate+" 23:59:59.999",
						"yyyy-MM-dd HH:mm:ss.SSS").getTime()));
		String SQL_QUERY = String
				.format(SQL_GET_EFFICIENCY_DETAIL_BY_GROUP_AND_LEVEL.toString());
		List<?> resultList = sqldao.executeSQLSelect(SQL_QUERY, queryMap);
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.efficiency.service.EfficiencyRatingService
	 * #generateEfficiencyRatingExcelReport(java.util.List)
	 */
	@Override
	public String generateEfficiencyRatingExcelReport(
			List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs) {

		// Generates an excel report
		ReportGenerator<EfficiencyRatingResponseVO> fixture = new ExcelReportGenerator<EfficiencyRatingResponseVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				EfficiencyRatingResponseVO.class, "Efficiency Rating",
				"Efficiency-Rating-" + new Date().getTime());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Efficiency Rating");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setEfficiencyRatingHeaderText(efficiencyRatingResponseVOs);
		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setEfficiencyRatingTableHeader();
		metadata.setDataTableHeading(tableHeadings);

		Collection<EfficiencyRatingResponseVO> dataList = efficiencyRatingResponseVOs;

		String fileName = null;

		// Writes the excel metadata and dataList received in the excel
		try {
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			LOGGER.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.efficiency.service.EfficiencyRatingService
	 * #generateEfficiencyRatingCsvReport(java.util.List)
	 */
	@Override
	public String generateEfficiencyRatingCsvReport(
			List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs) {

		// Generates a csv report
		ReportGenerator<EfficiencyRatingResponseVO> fixture = new CSVReportGenerator<EfficiencyRatingResponseVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				EfficiencyRatingResponseVO.class, "Efficiency Rating",
				"Efficiency-Rating-" + new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setEfficiencyRatingHeaderText(efficiencyRatingResponseVOs);
		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setEfficiencyRatingTableHeader();
		metadata.setDataTableHeading(tableHeadings);

		Collection<EfficiencyRatingResponseVO> dataList = efficiencyRatingResponseVOs;

		String fileName = null;

		// Writes the excel metadata and dataList received in the csv
		try {
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			LOGGER.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	/**
	 * Set Header for report for EfficiencyRating download
	 * 
	 * @param efficiencyRatingResponseVOs
	 * @return
	 */
	private List<HeadingTextProperties> setEfficiencyRatingHeaderText(
			List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs) {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName("Report Generation Date");
		headertext.setValue(new Date().toString());
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	/**
	 * Set column names for EfficiencyRating download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setEfficiencyRatingTableHeader() {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = null;

		heading = new DataTableHeader();
		heading.setColumnName("groupName");
		heading.setDisplayName("Group Name");
		heading.setSequence(0);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("treesCount");
		heading.setDisplayName("Trees Count");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;
	}

}

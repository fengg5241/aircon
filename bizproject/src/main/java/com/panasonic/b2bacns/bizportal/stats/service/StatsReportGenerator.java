/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.service.GroupLevelService;
import com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
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
 * @author akansha
 * 
 */
@Component
public class StatsReportGenerator {

	private static final Logger logger = Logger
			.getLogger(StatsReportGenerator.class);

	@Autowired
	private GroupLevelService groupLevelService;

	@Autowired
	private StatsSQLDAO sqldao;

	/**
	 * Generates report
	 * 
	 * @param statsList
	 * @param requestVO
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getFilePath(List<StatsReportVO> statsList,
			StatsRequestVO requestVO) throws IOException, URISyntaxException,
			IllegalAccessException, InvocationTargetException {

		String filePath = null;

		if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
				BizConstants.PERIOD_24HOURS)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getType(),
					BizConstants.STATISTICS_ACCUMULATED)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.ID_TYPE_REFRIGERANT_CIRCUIT)) {

					if (requestVO.getStartDate() == null
							&& requestVO.getEndDate() == null) {

						requestVO = sqldao
								.setTimeZoneForStatsByGroup(requestVO);

					}

				} else {

					requestVO = sqldao.setTimeZoneForStatsByGroup(requestVO);
				}

			} else if (StringUtils.equalsIgnoreCase(requestVO.getType(),
					BizConstants.STATISTICS_CHRONOLOGICAL)) {

				if (requestVO.getStartDate() == null
						&& requestVO.getEndDate() == null) {

					requestVO = sqldao.setTimeZoneForStatsByGroup(requestVO);

				}

			}

			if (requestVO.getStartDate() == null) {

				requestVO.setStartDate(requestVO.getPeriodStrategyMap()
						.get(BizConstants.KEY_START_RESIDUAL_DATE1).toString());

			}

			if (requestVO.getEndDate() == null) {

				requestVO.setEndDate(requestVO.getPeriodStrategyMap()
						.get(BizConstants.KEY_END_RESIDUAL_DATE1).toString());

			}

		}

		if (StringUtils.equalsIgnoreCase(requestVO.getFileType(),
				BizConstants.REPORT_TYPE_EXCEL)) {

			// generate excel file for given list at a specific position
			filePath = generateStatsExcelReport(statsList, requestVO);

		} else {

			// generate csv file for given list at a specific position
			filePath = generateStatsCsvReport(statsList, requestVO);

		}

		return filePath;
	}

	/**
	 * Generates excel report
	 * 
	 * @param statsList
	 * @param requestVO
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private String generateStatsExcelReport(List<StatsReportVO> statsList,
			StatsRequestVO requestVO) throws IOException, URISyntaxException,
			IllegalAccessException, InvocationTargetException {

		// Generates an excel report
		ReportGenerator<StatsReportVO> fixture = new ExcelReportGenerator<StatsReportVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				StatsReportVO.class, null, requestVO.getIdType()
						+ "_"
						+ requestVO.getType()
						+ "_"
						+ requestVO.getParameter()
						+ "_"
						+ CommonUtil.setUserTimeZone(Calendar.getInstance(),
								requestVO.getUserTimeZone()).getTimeInMillis());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName(requestVO.getParameter());
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		Double totalCapacity = null;

		if (statsList != null && !statsList.isEmpty()) {

			totalCapacity = statsList.get(statsList.size() - 1)
					.getTotalCapacity() != null ? statsList.get(
					statsList.size() - 1).getTotalCapacity() : 0.0;
		}

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setStatsHeaderText(
				requestVO, totalCapacity);

		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setStatsTableHeader(requestVO);

		metadata.setDataTableHeading(tableHeadings);

		Collection<StatsReportVO> dataList = statsList;

		String fileName = null;

		// Writes the excel metadata and dataList received in the excel
		try {
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			logger.error("Error occurred while writing data to Excel", e);
		}

		return fileName;
	}

	/**
	 * Generates csv report
	 * 
	 * @param statsList
	 * @param requestVO
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private String generateStatsCsvReport(List<StatsReportVO> statsList,
			StatsRequestVO requestVO) throws IllegalAccessException,
			InvocationTargetException {

		// Generates a csv report
		ReportGenerator<StatsReportVO> fixture = new CSVReportGenerator<StatsReportVO>();

		Double totalCapacity = null;

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(StatsReportVO.class,
				null, requestVO.getIdType()
						+ "_"
						+ requestVO.getType()
						+ "_"
						+ requestVO.getParameter()
						+ "_"
						+ CommonUtil.setUserTimeZone(Calendar.getInstance(),
								requestVO.getUserTimeZone()).getTimeInMillis());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		if (statsList.size() > 0) {
			totalCapacity = statsList.get(statsList.size() - 1)
					.getTotalCapacity() != null ? statsList.get(
					statsList.size() - 1).getTotalCapacity() : 0.0;
		}

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setStatsHeaderText(
				requestVO, totalCapacity);

		metadata.setHeadingTextProperties(headerTextList);

		// Set headings for table columns
		List<DataTableHeader> tableHeadings = setStatsTableHeader(requestVO);

		metadata.setDataTableHeading(tableHeadings);

		Collection<StatsReportVO> dataList = statsList;

		String fileName = null;

		try {
			// Writes the csv metadata and dataList received in the csv
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			logger.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	// private File getLogoFile() throws IOException, URISyntaxException {
	// // return resourceLoader.getResource("file:webapp/" + path).getFile();
	//
	// return new File(this.getClass().getClassLoader()
	// .getResource("panasonic.png").toURI());
	// }

	/**
	 * Set header text
	 * 
	 * @param requestVO
	 * @param totalCapacity
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private List<HeadingTextProperties> setStatsHeaderText(
			StatsRequestVO requestVO, Double totalCapacity)
			throws IllegalAccessException, InvocationTargetException {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue(ReportUtil.getReportTitle(requestVO.getParameter(),
				requestVO.getType()));
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

		if (!StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_AIRCON)
				&& !StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
				&& !StringUtils.equalsIgnoreCase(requestVO.getType(),
						BizConstants.STATISTICS_CHRONOLOGICAL)) {

			headertext = new HeadingTextProperties();
			headertext.setName(BizConstants.REPORT_HEADER_LEVEL_SELECTION);
			headertext.setValue(groupLevelService.getLevelNameById(
					requestVO.getGrouplevel()).getName());
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);
		}

		// if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
		// BizConstants.CAPACITY)) {
		// headertext = new HeadingTextProperties();
		// headertext.setName("Total");
		// headertext.setValue(String.valueOf(totalCapacity));
		// headertext.setDisplayPosition("LEFT");
		// headerTextList.add(headertext);
		// }

		return headerTextList;
	}

	/**
	 * Set column names for tables generated
	 * 
	 * @param requestVO
	 * @return
	 */
	private List<DataTableHeader> setStatsTableHeader(StatsRequestVO requestVO) {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = null;

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
		if (StringUtils.equalsIgnoreCase(requestVO.getType(),
				BizConstants.STATISTICS_CHRONOLOGICAL)) {
			heading.setColumnName("levelName");
		} else {

			heading.setColumnName("levelName");
		}
		heading.setDisplayName(BizConstants.REPORT_COLUMN_ENTITY_NAME);
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		if (StringUtils.equalsIgnoreCase(requestVO.getType(),
				BizConstants.STATISTICS_CHRONOLOGICAL)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_TODAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.RANGE_DAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

				heading = new DataTableHeader();
				heading.setColumnName("timeline");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_DATA_START_DATE_TIME);
				heading.setSequence(2);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("dataDuration");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_DATA_DURATION);
				heading.setSequence(3);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);
			} else {

				heading = new DataTableHeader();
				heading.setColumnName("timeline");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_DATA_DATE_TIME);
				heading.setSequence(2);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);
			}

		}

		switch (requestVO.getParameter()) {
		case BizConstants.POWER_CONSUMPTION:

			heading = new DataTableHeader();
			heading.setColumnName("power");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_POWER_CONSUMPTION);
			heading.setSequence(4);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			break;

		case BizConstants.CAPACITY:

			heading = new DataTableHeader();
			heading.setColumnName("rated");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_RATED_CAPACITY);
			heading.setSequence(4);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			heading = new DataTableHeader();
			heading.setColumnName("current");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_CURRENT_CAPACITY);
			heading.setSequence(5);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			heading = new DataTableHeader();
			heading.setColumnName("outdoorTemp");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_AVERAGE_OUTDOOR_TEMP);
			heading.setSequence(6);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			break;

		case BizConstants.EFFICIENCY:

			heading = new DataTableHeader();
			heading.setColumnName("efficiency");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_EFFICIENCY);
			heading.setSequence(4);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			// heading = new DataTableHeader();
			// heading.setColumnName("settingTemp");
			// heading.setDisplayName("Setting Temp");
			// heading.setSequence(5);
			// heading.setAlignment("LEFT");
			// tableHeadings.add(heading);
			//
			// heading = new DataTableHeader();
			// heading.setColumnName("rmTemp");
			// heading.setDisplayName("Rm Temp");
			// heading.setSequence(6);
			// heading.setAlignment("LEFT");
			// tableHeadings.add(heading);
			//
			// heading = new DataTableHeader();
			// heading.setColumnName("outdoorTemp");
			// heading.setDisplayName("Outdoor Temp");
			// heading.setSequence(7);
			// heading.setAlignment("LEFT");
			// tableHeadings.add(heading);

			break;
		case BizConstants.DIFF_TEMPERATURE:

			heading = new DataTableHeader();
			heading.setColumnName("roomTemp");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_ROOM_TEMP);
			heading.setSequence(4);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			heading = new DataTableHeader();
			heading.setColumnName("settingTemp");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_SET_TEMP);
			heading.setSequence(5);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			heading = new DataTableHeader();
			heading.setColumnName("differentialTemp");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_DIFF_TEMP);
			heading.setSequence(6);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);

			break;
		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				heading = new DataTableHeader();
				heading.setColumnName("compressor1");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_COMPRESSOR1);
				heading.setSequence(4);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				//commented by ravi AS GHP not supported by April release
				/*heading = new DataTableHeader();
				heading.setColumnName("engine");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_COMPRESSOR4);
				heading.setSequence(4);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);*/

				heading = new DataTableHeader();
				heading.setColumnName("compressor2");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_COMPRESSOR2);
				heading.setSequence(5);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("compressor3");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_COMPRESSOR3);
				heading.setSequence(6);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

			} else {

				// heading = new DataTableHeader();
				// heading.setColumnName("thermostatOff");
				// heading.setDisplayName("Thermostat");
				// heading.setSequence(2);
				// heading.setAlignment("LEFT");
				// tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("lowOff");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_THERMO_OFF_LOW);
				heading.setSequence(4);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("mediumOff");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_THERMO_OFF_MEDIUM);
				heading.setSequence(5);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("highOff");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_THERMO_OFF_HIGH);
				heading.setSequence(6);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				// heading = new DataTableHeader();
				// heading.setColumnName("thermostatOn");
				// heading.setDisplayName("Thermostat");
				// heading.setSequence(6);
				// heading.setAlignment("LEFT");
				// tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("lowOn");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_THERMO_ON_LOW);
				heading.setSequence(7);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("mediumOn");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_THERMO_ON_MEDIUM);
				heading.setSequence(8);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);

				heading = new DataTableHeader();
				heading.setColumnName("highOn");
				heading.setDisplayName(BizConstants.REPORT_COLUMN_THERMO_ON_HIGH);
				heading.setSequence(9);
				heading.setAlignment("LEFT");
				tableHeadings.add(heading);
			}

			break;
		}

		return tableHeadings;
	}

}

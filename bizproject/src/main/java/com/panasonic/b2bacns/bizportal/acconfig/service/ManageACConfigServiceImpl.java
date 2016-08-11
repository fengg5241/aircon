/**
 * 
 */
package com.panasonic.b2bacns.bizportal.acconfig.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUListVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamsDownloadVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamsVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
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
 * @author amitesh.arya
 * 
 */
@Service
public class ManageACConfigServiceImpl implements ManageACConfigService {

	private static final Logger logger = Logger
			.getLogger(ManageACConfigServiceImpl.class);

	@Autowired
	private ACConfigDAO acConfigDAO;

	@Resource(name = "properties")
	private Properties bizProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public ACConfigIDUVO getACConfiguration(ACConfigRequest acConfigRequest) {

		ACConfigIDUVO acConfigDetails = null;
		try {
			acConfigDetails = acConfigDAO.getACConfigDetails(acConfigRequest);
			logger.debug("Get AC Config IDU Details request: "
					+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Config IDU Details : "
					+ e.getMessage());
		}
		return acConfigDetails;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getODUACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<ACConfigODUVO> getODUACConfiguration(
			ACConfigRequest acConfigRequest) {

		Set<ACConfigODUVO> acODUDetails = null;
		try {
			acODUDetails = acConfigDAO.getODUACConfigDetails(acConfigRequest);
			logger.debug("Get AC Config ODU Details request: "
					+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Config ODU Details : "
					+ e.getMessage());
		}
		return acODUDetails;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateACDetailsODUCsvReport(java.util.Set)
	 */
	@Override
	public String generateACDetailsCsvReport(Set<ACConfigVO> indoorDataList,
			String addCustomer) throws Exception {

		// Generates a csv report
		ReportGenerator<ACConfigVO> fixture = new CSVReportGenerator<ACConfigVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(ACConfigVO.class,
				"Current IDU Status", "Current-IDU-Status-"
						+ new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		metadata.setHeadingTextProperties(setACIDUHeaderText());

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setACDetailsTableHeader(addCustomer);

		metadata.setDataTableHeading(tableHeadings);

		Collection<ACConfigVO> dataList = new ArrayList<ACConfigVO>(
				indoorDataList);

		// Writes the csv metadata and dataList received in the csv
		return fixture.writeTabularReport(metadata, dataList);

	}

	private List<HeadingTextProperties> setACIDUHeaderText() {
		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Current IDU Status");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		headertext.setValue(CommonUtil.dateToString(new Date(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateACDetailsODUExcelReport(java.util.Set)
	 */
	@Override
	public String generateACDetailsExcelReport(Set<ACConfigVO> inputDataList,
			String addCustomer) throws Exception {

		// Generates an excel report
		ReportGenerator<ACConfigVO> fixture = new ExcelReportGenerator<ACConfigVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				ACConfigVO.class, "Current IDU Status", "Current-IDU-Status-"
						+ new Date().getTime());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Current IDU Status");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		metadata.setHeadingTextProperties(setACIDUHeaderText());

		metadata.setDataTableHeading(setACDetailsTableHeader(addCustomer));

		Collection<ACConfigVO> dataList = new ArrayList<ACConfigVO>(
				inputDataList);

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	private File getLogoFile() throws IOException, URISyntaxException {
		// return resourceLoader.getResource("file:webapp/" + path).getFile();
		URL getURL = this.getClass().getClassLoader()
				.getResource("panasonic.png");

		if (getURL != null) {
			URI getURI = getURL.toURI();
			return new File(getURI);

		} else
			return new File(BizConstants.EMPTY_STRING);

	}

	/**
	 * Set column names for AC Details download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setACDetailsTableHeader(String addCustomer) {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();

		if (StringUtils.equalsIgnoreCase(addCustomer, BizConstants.YES)) {

			heading = new DataTableHeader();
			heading.setColumnName("customerName");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_CUSTOMER_NAME);
			heading.setSequence(0);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);
		}

		heading = new DataTableHeader();
		heading.setColumnName("sitePath");
		heading.setDisplayName("I/D Unit Name");
		heading.setSequence(1);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		//add by shanf
		heading = new DataTableHeader();
		heading.setColumnName("deviceModel");
		heading.setDisplayName("Device Model");
		heading.setSequence(2);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);
		//change by shanf start
		heading = new DataTableHeader();
		heading.setColumnName("powerDownload");
		heading.setDisplayName("ON/OFF (status)");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("temperatureDownload");
		heading.setDisplayName("Set Temp. (°C)");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("roomTempDownload");
		heading.setDisplayName("Room Temp. (°C)");
		heading.setSequence(5);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("modeDownload");
		heading.setDisplayName("Operation Mode (status)");
		heading.setSequence(6);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("fanSpeedDownload");
		heading.setDisplayName("Fan Speed (status)");
		heading.setSequence(7);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("flapModeDownload");
		heading.setDisplayName("Flap (status)");
		heading.setSequence(8);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("energy_savingDownload");
		heading.setDisplayName("Energy Saving Status");
		heading.setSequence(9);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitRCPowerDownload");
		heading.setDisplayName("Prohib R/C Use (ON/OFF)");
		heading.setSequence(10);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitRCTempDownload");
		heading.setDisplayName("Prohibit R/C Use (temperature)");
		heading.setSequence(11);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitRCModeDownload");
		heading.setDisplayName("Prohib R/C Use (mode)");
		heading.setSequence(12);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitRCFanSpeedDownload");
		heading.setDisplayName("Prohibit R/C Use (fan speed)");
		heading.setSequence(13);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitRCFlapModeDownload");
		heading.setDisplayName("Prohibit R/C Use (flap)");
		heading.setSequence(14);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitRCEnergySavingDownload");
		heading.setDisplayName("Prohibit R/C Use (energy saving)");
		heading.setSequence(15);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarmCodeDownload");
		heading.setDisplayName("Alarm Code");
		heading.setSequence(16);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("type");
		heading.setDisplayName("Category");
		heading.setSequence(17);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("parentChild");
		heading.setDisplayName("Main/Sub");
		heading.setSequence(18);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("caStatus");
		heading.setDisplayName("Cloud Adaptor Status");
		heading.setSequence(19);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);
		//change by shanf end
		return tableHeadings;
	}

	/**
	 * This Method provides OutDoorData for given IndoorUnitIds in Json format
	 * 
	 * @param IndoorUnitIds
	 *            ID of the IndoorUnits
	 * 
	 * @return OutDoor Units Data
	 */
	@Override
	public String getODUList(Long[] id) throws HibernateException,
			JsonProcessingException, IllegalAccessException, Exception {

		List<ODUListVO> oduVoList = null;
		String json = BizConstants.EMPTY_STRING;

		oduVoList = acConfigDAO.getODUList(id);

		if (oduVoList != null && oduVoList.size() == 0) {
			throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
		} else if (oduVoList != null && oduVoList.size() > 0) {
			json = CommonUtil.convertFromEntityToJsonStr(oduVoList);
		}
		return json;
	}

	/**
	 * This Method provides OutDoorData for given IndoorUnitIds in Json format
	 * 
	 * @param GroupUnitIds
	 *            ID of the GroupUnits
	 * 
	 * @return OutDoor Units Data
	 */

	@Override
	public String getGroupODUList(Long[] id) throws HibernateException,
			JsonProcessingException, IllegalAccessException, Exception {
		List<ODUListVO> oduListVO = null;
		String json = null;

		oduListVO = acConfigDAO.getODUDataForGroups(id);
		if (oduListVO != null && oduListVO.size() == 0) {
			throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
		} else if (oduListVO != null && oduListVO.size() > 0) {
			json = CommonUtil.convertFromEntityToJsonStr(oduListVO);
		}
		return json;
	}

	/**
	 * This Method provides OutDoorData for given OutdoorUnitIds in Json format
	 * 
	 * @param OutDoorUnitIds
	 *            ID of the OutDoorUnits
	 * 
	 * @return list
	 */
	@Override
	public List<ODUParamVO> getODUParams(Long id, List<String> paramWithG1,
			List<String> paramsForGHPlist, List<String> paramsForVRFlist,
			String idType) throws HibernateException {
		List<ODUParamVO> oduParamList = acConfigDAO.getODUParams(id,
				paramWithG1, paramsForGHPlist, paramsForVRFlist, idType);

		return oduParamList;
	}

	/**
	 * This Method provides ODUParameter for given idType in Json format
	 * 
	 * @param idType
	 *            IDType of the Params
	 * 
	 * @return json
	 */
	@Override
	public String getODUParameterList(String idType) throws HibernateException,
			JsonProcessingException, IllegalAccessException, Exception {
		List<ODUParamsVO> oduParamsList = null;

		String json = BizConstants.EMPTY_STRING;

		oduParamsList = acConfigDAO.getODUParameterList(idType);

		if (oduParamsList != null && oduParamsList.size() == 0) {
			throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
		} else if (oduParamsList != null && oduParamsList.size() > 0) {
			json = CommonUtil.convertFromEntityToJsonStr(oduParamsList);
		}

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateODUParamsReport(java.util.List)
	 */
	@Override
	public String generateODUParamsExcelReport(List<ODUParamVO> oduParamList)
			throws Exception {

		// Generates an excel report
		ReportGenerator<ODUParamsDownloadVO> fixture = new ExcelReportGenerator<ODUParamsDownloadVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				ODUParamsDownloadVO.class, "ODU Params", "ODU-Params-"
						+ new Date().getTime());

		Logo logo = new Logo();
		logo.setImage(getLogoFile());
		logo.setImageRelativePosition("RIGHT");

		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("ODU Params");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setODUParamsHeaderText(oduParamList);
		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setODUParamsTableHeader();
		metadata.setDataTableHeading(tableHeadings);

		Collection<ODUParamsDownloadVO> dataList = getODUParamsDownloadVO(oduParamList);

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateODUParamsCsvReport(java.util.List)
	 */
	@Override
	public String generateODUParamsCsvReport(List<ODUParamVO> oduParamList)
			throws Exception {

		// Generates a csv report
		ReportGenerator<ODUParamsDownloadVO> fixture = new CSVReportGenerator<ODUParamsDownloadVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				ODUParamsDownloadVO.class, "ODU Params", "ODU-Params-"
						+ new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setODUParamsHeaderText(oduParamList);
		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setODUParamsTableHeader();
		metadata.setDataTableHeading(tableHeadings);

		Collection<ODUParamsDownloadVO> dataList = getODUParamsDownloadVO(oduParamList);

		// Writes the csv metadata and dataList received in the csv
		return fixture.writeTabularReport(metadata, dataList);

	}

	/**
	 * Set Header for report for ODUParams download
	 * 
	 * @param oduParamList
	 * @return
	 */
	private List<HeadingTextProperties> setODUParamsHeaderText(
			List<ODUParamVO> oduParamList) {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName("Report Generation Date");
		headertext.setValue(new Date().toString());
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		if (oduParamList != null && !oduParamList.isEmpty()) {

			headertext = new HeadingTextProperties();
			headertext.setName("Model Name");
			headertext.setValue(oduParamList.get(0).getModelName());
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);

			headertext = new HeadingTextProperties();
			headertext.setName("type");
			headertext.setValue(oduParamList.get(0).getType());
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);

			headertext = new HeadingTextProperties();
			headertext.setName("Dimension");
			headertext.setValue(oduParamList.get(0).getDimension());
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);

			headertext = new HeadingTextProperties();
			headertext.setName("Current Utilization");
			headertext.setValue(String.valueOf(oduParamList.get(0)
					.getCurrentUtilization()));
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);

			headertext = new HeadingTextProperties();
			headertext.setName("Working Time");
			headertext.setValue(String.valueOf(oduParamList.get(0)
					.getWorkingtime()));
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);
		}

		return headerTextList;
	}

	/**
	 * Set column names for ODUParams download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setODUParamsTableHeader() {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = null;

		heading = new DataTableHeader();
		heading.setColumnName("oduParamName");
		heading.setDisplayName("ODU Param Name");
		heading.setSequence(0);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("oduParamValue");
		heading.setDisplayName("Value");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;

	}

	/**
	 * Convert List of {@link ODUParamVO} to List of {@link ODUParamsDownloadVO}
	 * 
	 * @param oduParamsList
	 * @return
	 */
	private Collection<ODUParamsDownloadVO> getODUParamsDownloadVO(
			List<ODUParamVO> oduParamsList) {

		List<ODUParamsDownloadVO> oduParamsDownloadVOList = new ArrayList<ODUParamsDownloadVO>();
		ODUParamsDownloadVO downloadVO = null;

		if (oduParamsList != null && !oduParamsList.isEmpty()) {

			for (ODUParamVO oduParamVO : oduParamsList) {

				Map<String, String> oduParamMap = oduParamVO.getOduParam();

				if (oduParamMap != null) {

					for (int i = 0; i < oduParamMap.size(); i++) {

						String[] oduParamNames = Arrays.copyOf(oduParamMap
								.keySet().toArray(), oduParamMap.keySet()
								.toArray().length, String[].class);

						downloadVO = new ODUParamsDownloadVO();
						downloadVO.setOduParamName(oduParamNames[i]);

						downloadVO.setOduParamValue(oduParamMap.get(downloadVO
								.getOduParamName()) != null ? Double
								.valueOf(oduParamMap.get(downloadVO
										.getOduParamName())) : null);

						oduParamsDownloadVOList.add(downloadVO);
					}
				} else {

					downloadVO = new ODUParamsDownloadVO();
					oduParamsDownloadVOList.add(downloadVO);
				}

			}

		}

		return oduParamsDownloadVOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateACDetailsODUExcelReport(java.util.List)
	 */
	@Override
	public String generateACDetailsODUExcelReport(
			Set<ACConfigODUVO> outdoorDataList, String addCustomer)
			throws Exception {

		// Generates an excel report
		ReportGenerator<ACConfigODUVO> fixture = new ExcelReportGenerator<ACConfigODUVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				ACConfigODUVO.class, "Current ODU Status",
				"Current-ODU-Status-" + new Date().getTime());

		Logo logo = new Logo();
		// logo.setImage(getLogoFile());
		// logo.setImageRelativePosition("RIGHT");

		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Current ODU Status");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		metadata.setHeadingTextProperties(setACODUHeaderText());

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setACDetailsODUTableHeader(addCustomer);

		metadata.setDataTableHeading(tableHeadings);

		Collection<ACConfigODUVO> dataList = new ArrayList<ACConfigODUVO>(
				outdoorDataList);

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateACDetailsODUCsvReport(java.util.Set)
	 */
	@Override
	public String generateACDetailsODUCsvReport(
			Set<ACConfigODUVO> outdoorDataList, String addCustomer)
			throws Exception {

		// Generates a csv report
		ReportGenerator<ACConfigODUVO> fixture = new CSVReportGenerator<ACConfigODUVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(ACConfigODUVO.class,
				"Current ODU Status", "Current-ODU-Status-"
						+ new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		metadata.setHeadingTextProperties(setACODUHeaderText());

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setACDetailsODUTableHeader(addCustomer);

		metadata.setDataTableHeading(tableHeadings);

		Collection<ACConfigODUVO> dataList = new ArrayList<ACConfigODUVO>(
				outdoorDataList);

		// Writes the csv metadata and dataList received in the csv
		return fixture.writeTabularReport(metadata, dataList);

	}

	private List<HeadingTextProperties> setACODUHeaderText() {
		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Current ODU Status");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		headertext.setValue(CommonUtil.dateToString(new Date(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	/**
	 * Set column names for AC Details Outdoor download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setACDetailsODUTableHeader(String addCustomer) {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();

		if (StringUtils.equalsIgnoreCase(addCustomer, BizConstants.YES)) {

			heading = new DataTableHeader();
			heading.setColumnName("customerName");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_CUSTOMER_NAME);
			heading.setSequence(0);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);
		}

		heading = new DataTableHeader();
		heading.setColumnName("sitePath");
		heading.setDisplayName("O/D Unit Name");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);
		
		//add by shanf
		heading = new DataTableHeader();
		heading.setColumnName("deviceModel");
		heading.setDisplayName("Device Model");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);
		
		//change by shanf start
		heading = new DataTableHeader();
		heading.setColumnName("sLinkAddress");
		heading.setDisplayName("S-Link Address");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarmCode");
		heading.setDisplayName("Alarm Code");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("outdoorTemp");
		heading.setDisplayName("Outdoor Air Temp. (°C)");
		heading.setSequence(5);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("demandDownload");
		heading.setDisplayName("Demand (%)");
		heading.setSequence(6);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("maintenanceCountDownComp1Download");
		heading.setDisplayName("VRF Comp1 Remaining (hours)");
		heading.setSequence(7);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("maintenanceCountDownComp2Download");
		heading.setDisplayName("VRF Comp2 Remaining (hours)");
		heading.setSequence(8);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("maintenanceCountDownComp3Download");
		heading.setDisplayName("VRF Comp3 Remaining (hours)");
		heading.setSequence(9);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("generationPowerDownload");
		heading.setDisplayName("Power Generation (wh)");
		heading.setSequence(10);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("ghpOilCheckCountDownDownload");
		heading.setDisplayName("GHP Oil Check Countdown (hours)");
		heading.setSequence(11);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("ghpEngineServiceCountDownDownload");
		heading.setDisplayName("GHP Engine Service Countdown (hours)");
		heading.setSequence(12);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("type");
		heading.setDisplayName("Category");
		heading.setSequence(13);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("parentChild");
		heading.setDisplayName("Main/Sub");
		heading.setSequence(14);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);
		//change by shanf end
		
		return tableHeadings;
	}

}

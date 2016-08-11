package com.panasonic.b2bacns.bizportal.notification.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationCountVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailList;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailsVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.notification.dao.NotificationDAO;
import com.panasonic.b2bacns.bizportal.notification.vo.AlarmTypeVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewRequest;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationRequestDownloadVO;
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

@Service
public class ManageNotificationServiceImpl implements ManageNotificationService {

	private static final Logger logger = Logger
			.getLogger(ManageNotificationServiceImpl.class);
	@Autowired
	private SQLDAO sqldao;

	@Autowired
	private NotificationDAO notificationDao;

	@Resource(name = "properties")
	private Properties bizProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.service.
	 * ManageNotificationService#getAlarmTypes()
	 */
	@Override
	public String getAlarmTypes() throws JsonProcessingException,
			HibernateException {

		List<AlarmTypeVO> alarmTypeVO = null;

		String json = BizConstants.EMPTY_STRING;

		alarmTypeVO = notificationDao.getAlarmTypeList();

		if (alarmTypeVO.size() > 0 && !alarmTypeVO.isEmpty()) {

			json = CommonUtil.convertFromEntityToJsonStr(alarmTypeVO);
		} else {
			throw new GenericFailureException("error in getAlarmTypes");
		}

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.service.
	 * ManageNotificationService#getAlarmNotificationOverView(request)
	 */
	@Override
	public String getAlarmNotificationOverView(
			NotificationOverViewRequest notificationOverViewRequest)
			throws JsonProcessingException, ParseException {

		List<Long> groupID = null;
		String startDate = null;
		String endDate = null;
		String alarmType = null;
		String period = null;
		String timeZone = null;
		int grouplevel = 0;

		String json = BizConstants.NO_RECORDS_FOUND;

		Map<String, Object> notificationOverViewResultMap = null;

		if (notificationOverViewRequest.getGroupIds() != null
				&& !notificationOverViewRequest.getGroupIds().isEmpty()) {

			groupID = notificationOverViewRequest.getGroupIds();
			startDate = notificationOverViewRequest.getStartDate();
			endDate = notificationOverViewRequest.getEndDate();
			alarmType = notificationOverViewRequest.getAlarmType();
			period = notificationOverViewRequest.getPeriod();
			grouplevel = notificationOverViewRequest.getGrouplevel();
			timeZone = notificationOverViewRequest.getTimeZone();

			try {
				notificationOverViewResultMap = notificationDao
						.getNotificationOverViewDataForGroups(groupID,
								startDate, endDate, alarmType, period,
								grouplevel, timeZone);

				if (notificationOverViewResultMap != null
						&& notificationOverViewResultMap.size() > 0) {
					json = CommonUtil
							.convertFromEntityToJsonStr(notificationOverViewResultMap);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} catch (JsonProcessingException jpExp) {
				logger.error("Some error occured while parsing object to JSON : "
						+ jpExp.getMessage());
			}
		}
		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.service.
	 * ManageNotificationService#getNotificationCount(java.lang.Long)
	 */
	@Override
	public String getNotificationCount(Long userId)
			throws JsonProcessingException {
		String alarmJson = BizConstants.NO_RECORDS_FOUND;

		NotificationCountVO alarmVO = notificationDao
				.getNotificationCount(userId);

		if (alarmVO != null) {
			alarmJson = CommonUtil.convertFromEntityToJsonStr(alarmVO);
		}

		return alarmJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.service.
	 * ManageNotificationService
	 * #getNotificationDetails(com.panasonic.b2bacns.bizportal
	 * .dashboard.vo.NotificationRequestVO, java.lang.String)
	 */
	@Override
	public NotificationDetailList getNotificationDetails(
			NotificationRequestVO request, String userTimeZone) {
		NotificationDetailList notificationList = null;
		try {
			if (request.getAlarmOccurredStartDate() == null
					&& request.getAlarmFixedStartDate() == null) {
				Calendar cal = Calendar.getInstance();
				cal = CommonUtil.setUserTimeZone(cal, userTimeZone);
				String endDate = CommonUtil.dateToString(cal.getTime(),
						BizConstants.DD_MM_YYYY);
				cal.add(Calendar.DATE, -6);
				String startDate = CommonUtil.dateToString(cal.getTime(),
						BizConstants.DD_MM_YYYY);
				request.setAlarmOccurredStartDate(startDate);
				request.setAlarmOccurredEndDate(endDate);

			}
			logger.debug("Get Notification Details request: " + request);
			notificationList = notificationDao.getNotificationDetails(request);
		} catch (Exception e) {
			logger.error("Some error occured while fetching the notification Details : "
					+ e.getMessage());
		}
		return notificationList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.service.
	 * ManageNotificationService
	 * #generateNotificationDetailsExcelReport(java.util.Set,
	 * com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO)
	 */
	@Override
	public String generateNotificationDetailsExcelReport(
			Set<NotificationDetailsVO> notificationList,
			NotificationRequestVO requestVO) throws Exception {

		// Generates an excel report
		ReportGenerator<NotificationDetailsVO> fixture = new ExcelReportGenerator<NotificationDetailsVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				NotificationDetailsVO.class, "Notification Details",
				"Notification-Details-" + new Date().getTime());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Notification Details");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		// Set header text for report
		metadata.setHeadingTextProperties(setNotificationDetailsHeaderText());

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setNotificationDetailsTableHeader(requestVO
				.getAddCustName());

		metadata.setDataTableHeading(tableHeadings);

		Collection<NotificationDetailsVO> dataList = new ArrayList<NotificationDetailsVO>(
				notificationList);

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.service.
	 * ManageNotificationService
	 * #generateNotificationDetailsCsvReport(java.util.Set,
	 * com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO)
	 */
	@Override
	public String generateNotificationDetailsCsvReport(
			Set<NotificationDetailsVO> notificationList,
			NotificationRequestVO requestVO) throws Exception {

		// Generates a csv report
		ReportGenerator<NotificationDetailsVO> fixture = new CSVReportGenerator<NotificationDetailsVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				NotificationDetailsVO.class, "Notification Details",
				"Notification-Details-" + new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		metadata.setHeadingTextProperties(setNotificationDetailsHeaderText());

		// Set headings for table columns
		List<DataTableHeader> tableHeadings = setNotificationDetailsTableHeader(requestVO
				.getAddCustName());

		metadata.setDataTableHeading(tableHeadings);

		Collection<NotificationDetailsVO> dataList = new ArrayList<NotificationDetailsVO>(
				notificationList);

		// Writes the csv metadata and dataList received in the csv
		return fixture.writeTabularReport(metadata, dataList);

	}

	/**
	 * Set Text for Header for Notification Details download
	 * 
	 * @return
	 */
	private List<HeadingTextProperties> setNotificationDetailsHeaderText() {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Notification Details");
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
	 * Set column names for Notification Details download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setNotificationDetailsTableHeader(
			String addCustomer) {

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
		heading.setColumnName("severity");
		heading.setDisplayName("Notification Category");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("code");
		heading.setDisplayName("Alarm Code");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarmOccurred");
		heading.setDisplayName("Occurred Date and Time");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("notificationID");
		heading.setDisplayName("Notification ID");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("sitePath");
		heading.setDisplayName("Unit Name");
		heading.setSequence(5);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarmFixed");
		heading.setDisplayName("Fixed Date and Time");
		heading.setSequence(6);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarmStatus");
		heading.setDisplayName("Notification Status");
		heading.setSequence(7);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;

	}

	@Override
	public List<NotificationOverViewVO> getAlarmNotificationOverViewForDownload(
			NotificationOverViewRequest notificationOverViewRequest)
			throws HibernateException, ParseException, JsonProcessingException {

		List<Long> groupID = null;
		String startDate = null;
		String endDate = null;
		String alarmType = null;
		String period = null;
		String timeZone = null;
		int grouplevel = 0;

		List<NotificationOverViewVO> notificationOverViewResultList = null;

		if (notificationOverViewRequest.getGroupIds() != null
				&& !notificationOverViewRequest.getGroupIds().isEmpty()) {

			groupID = notificationOverViewRequest.getGroupIds();
			startDate = notificationOverViewRequest.getStartDate();
			endDate = notificationOverViewRequest.getEndDate();
			alarmType = notificationOverViewRequest.getAlarmType();
			period = notificationOverViewRequest.getPeriod();
			grouplevel = notificationOverViewRequest.getGrouplevel();
			timeZone = notificationOverViewRequest.getTimeZone();

			notificationOverViewResultList = notificationDao
					.getNotificationOverViewDataForDownload(groupID, startDate,
							endDate, alarmType, period, grouplevel, timeZone);

			if (notificationOverViewResultList != null
					&& notificationOverViewResultList.size() > 0) {

				return notificationOverViewResultList;
			}
		}
		return notificationOverViewResultList;

	}

	@Override
	public String generateNotificationOverViewExcelReport(
			List<NotificationOverViewVO> notificationList,
			NotificationOverViewRequest notificationOverViewRequest)
			throws Exception {
		// Generates an excel report
		//Added by seshu
		ReportGenerator<NotificationOverViewVO> fixture = new CSVReportGenerator<NotificationOverViewVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				NotificationOverViewVO.class, "Notification OverView",
				"Notification OverView-" + new Date().getTime());
		//Added by seshu
		//metadata.setDataFontSize((short) 12);
		//metadata.setSheetName("Notification OverView");
	//	metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		//metadata.setReportNameFontSize((short) 14);
		//metadata.setDisplayGirdLines(true);

		// Set header text for report
		List<HeadingTextProperties> headerTextList = setNotificationOverviewHeaderText(notificationOverViewRequest);

		metadata.setHeadingTextProperties(headerTextList);

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setNotificationOverviewHeader(notificationOverViewRequest);

		metadata.setDataTableHeading(tableHeadings);

		Collection<NotificationOverViewVO> dataList = notificationList;

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	/**
	 * Set Header for report for NotificationOverview download
	 * 
	 * @throws ParseException
	 * 
	 */

	private List<HeadingTextProperties> setNotificationOverviewHeaderText(
			NotificationOverViewRequest notificationOverViewRequest)
			throws ParseException {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Notification Overview");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		headertext.setValue(CommonUtil.dateToString(new Date(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_CATEGORY_LEVEL);
		headertext.setValue(Integer.toString(notificationOverViewRequest
				.getGrouplevel()));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_ALARM_CODE);
		headertext.setValue(notificationOverViewRequest.getAlarmType());
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		if (notificationOverViewRequest.getPeriod() != null
				&& !notificationOverViewRequest.getPeriod().isEmpty()) {
			Calendar cal = Calendar.getInstance();

			Date startDateWithTimeZone = cal.getTime();
			Date endDateWithTimeZone = cal.getTime();
			Calendar currentDateWithTimeZone = CommonUtil.setUserTimeZone(cal,
					notificationOverViewRequest.getTimeZone());

			endDateWithTimeZone = currentDateWithTimeZone.getTime();

			if (notificationOverViewRequest.getPeriod().equalsIgnoreCase(
					BizConstants.PERIOD_THISYEAR)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_YEAR, 1);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thisyear coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (notificationOverViewRequest.getPeriod()
					.equalsIgnoreCase(BizConstants.PERIOD_THISMONTH)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_MONTH, 1);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thismonth coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (notificationOverViewRequest.getPeriod()
					.equalsIgnoreCase(BizConstants.PERIOD_THISWEEK)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_WEEK,
						Calendar.MONDAY);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thisweek coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (notificationOverViewRequest.getPeriod()
					.equalsIgnoreCase(BizConstants.PERIOD_TODAY)) {

				currentDateWithTimeZone.set(GregorianCalendar.HOUR_OF_DAY, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when today coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();

			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			headertext = new HeadingTextProperties();
			headertext.setName(BizConstants.REPORT_HEADER_DATE_RANGE);
			headertext.setValue(sdf.format(startDateWithTimeZone) + " to "
					+ sdf.format(endDateWithTimeZone));
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);

		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat yyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
			Date startDateWithTimeZone = yyyMMdd
					.parse(notificationOverViewRequest.getStartDate());
			Date enDateWithTimeZone = yyyMMdd.parse(notificationOverViewRequest
					.getEndDate());

			String startDate = sdf.format(startDateWithTimeZone);
			String endDate = sdf.format(enDateWithTimeZone);

			headertext = new HeadingTextProperties();
			headertext.setName(BizConstants.REPORT_HEADER_DATE_RANGE);
			headertext.setValue(startDate + " to " + endDate);
			headertext.setDisplayPosition("LEFT");
			headerTextList.add(headertext);

		}

		return headerTextList;
	}

	/**
	 * Set column names for NotificationOverview download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setNotificationOverviewHeader(
			NotificationOverViewRequest notificationOverViewRequest) {
		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();
		DataTableHeader heading = new DataTableHeader();
		heading = new DataTableHeader();
		heading.setColumnName("companyName");
		heading.setDisplayName(BizConstants.REPORT_COLUMN_CUSTOMER_NAME);
		heading.setSequence(0);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("pathName");
		heading.setDisplayName("Group/Site Name");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarmcount");
		heading.setDisplayName("Notification Count");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;
	}

	@Override
	public List<NotificationDetailsVO> getNotificationDetailsDownloadData(
			List<Long> notificationIds) {

		return notificationDao
				.getNotificationDetailsDownloadData(notificationIds);

	}

	@Override
	public String generateNotificationDetailsExcelReport(
			List<NotificationDetailsVO> notificationList,
			NotificationRequestDownloadVO notificationRequestVO)
			throws Exception {

		// Generates an excel report
		ReportGenerator<NotificationDetailsVO> fixture = new ExcelReportGenerator<NotificationDetailsVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				NotificationDetailsVO.class, "Notification Details",
				"Notification-Details-" + new Date().getTime());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Notification Details");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		// Set header text for report
		metadata.setHeadingTextProperties(setNotificationDetailsHeaderText());

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setNotificationDetailsTableHeader(notificationRequestVO
				.getAddCustName());

		metadata.setDataTableHeading(tableHeadings);

		Collection<NotificationDetailsVO> dataList = new ArrayList<NotificationDetailsVO>(
				notificationList);

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	@Override
	public String generateNotificationDetailsCsvReport(
			List<NotificationDetailsVO> notificationList,
			NotificationRequestDownloadVO notificationRequestVO)
			throws Exception {

		// Generates a csv report
		ReportGenerator<NotificationDetailsVO> fixture = new CSVReportGenerator<NotificationDetailsVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				NotificationDetailsVO.class, "Notification Details",
				"Notification-Details-" + new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		metadata.setHeadingTextProperties(setNotificationDetailsHeaderText());

		// Set headings for table columns
		List<DataTableHeader> tableHeadings = setNotificationDetailsTableHeader(notificationRequestVO
				.getAddCustName());

		metadata.setDataTableHeading(tableHeadings);

		Collection<NotificationDetailsVO> dataList = new ArrayList<NotificationDetailsVO>(
				notificationList);

		// Writes the csv metadata and dataList received in the csv
		return fixture.writeTabularReport(metadata, dataList);

	}

}

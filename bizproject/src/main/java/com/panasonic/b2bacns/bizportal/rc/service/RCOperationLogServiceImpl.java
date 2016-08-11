package com.panasonic.b2bacns.bizportal.rc.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;
import com.panasonic.b2bacns.bizportal.persistence.RcoperationLog;
import com.panasonic.b2bacns.bizportal.persistence.Timezonemaster;
import com.panasonic.b2bacns.bizportal.rc.RCOperation;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogRequest;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogVO;
import com.panasonic.b2bacns.bizportal.service.FanSpeedMasterService;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;
import com.panasonic.b2bacns.bizportal.service.WindDirectionMasterService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.TimeZoneUtil;
import com.panasonic.b2bacns.common.reports.DataTableHeader;
import com.panasonic.b2bacns.common.reports.HeadingTextProperties;
import com.panasonic.b2bacns.common.reports.Logo;
import com.panasonic.b2bacns.common.reports.ReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportMetadata;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportGenerator;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportMetadata;

/**
 * Implementation of RcoperationLogService
 * 
 * @author shobhit.singh
 * @author simanchal.patra
 * 
 */
@Service
public class RCOperationLogServiceImpl implements RCOperationLogService {

	public static final String STR_COLLON = ":";

	public static final String STR_AIRCONMODE = "airconmode";

	public static final String STR_COMMA = ",";

	private static Logger logger = Logger
			.getLogger(RCOperationLogServiceImpl.class);

	private static final StringBuffer SQL_GET_RCOPERATIONSLOG_BY_DATETIME = new StringBuffer(
			"select rclog.indoorunit_id IDUId, idu.serialnumber serialNumber, adapter.name CAName, rclog.airconmode , ")
			.append(" rclog.temperature, rclog.fanspeed ,rclog.flapMode,  rclog.powerstatus, rclog.energysaving ,   ")
			.append(" rclog.prohibitionpowerstatus , rclog.prohibitonmode , rclog.prohibitionfanspeed , ")
			.append(" rclog.prohibitionwindriection,  rclog.prohibitionsettemp,  date(rclog.requestedtime) date, ")
			.append(" cast(rclog.requestedtime as time) , rclog.success result,")
			//change by shanf
//			.append(" CASE WHEN rclog.user_id IS NOT NULL THEN (SELECT usr.firstname ||' ' || usr.lastname")
			.append(" CASE WHEN rclog.user_id IS NOT NULL THEN (SELECT usr.loginid")
			.append(" FROM users usr WHERE usr.id=rclog.user_id ) ELSE 'SYSTEM' END AS username,")
			.append(" rclog.prohibitionenergy_saving,")
			.append(" rclog.roomtemp as roomtemp, idu.name as iduName, cg.name as controlgroup, gs.name as site, rclog.id")
			.append(" from   rcoperation_log rclog")
			.append(" inner join indoorunits idu on idu.id=rclog.indoorunit_id ")
			.append(" inner join adapters adapter on adapter.id=idu.adapters_id ")
			.append(" left join groups gs on idu.siteid = gs.uniqueid ")
			.append(" left join groups cg on idu.group_id = cg.id ")
			.append(" where cast(rclog.requestedtime as date) BETWEEN cast('%s' as date) AND cast('%s' as date) ")
			.append(" and rclog.indoorunit_id in (%s)  order by rclog.requestedtime desc, rclog.id ");

	private static final StringBuffer SQL_PAGINATION = new StringBuffer(
			" LIMIT %d OFFSET (%d-1) * %d");

	private static final StringBuffer SQL_GET_RCOPERATIONSLOG_FOR_PAGE_COUNT = new StringBuffer(
			"select count(*) from rcoperation_log rclog")
			.append(" inner join indoorunits idu on idu.id=rclog.indoorunit_id ")
			.append(" inner join adapters adapter on adapter.id=idu.adapters_id ")
			.append(" left join groups gs on idu.siteid = gs.uniqueid ")
			.append(" left join groups cg on idu.group_id = cg.id ")
			.append(" where cast(rclog.requestedtime as date) BETWEEN cast('%s' as date) AND cast('%s' as date) ")
			.append(" and rclog.indoorunit_id in (%s) ");

	public static StringBuilder SQL_INSERT_RCOPERATIONSLOG = new StringBuilder(
			"INSERT INTO rcoperation_log(")
			.append("creationdate, requestedtime, indoorunit_id, success, user_id, $ )")
			.append(" VALUES (cast(:creationdate as timestamp), cast(:requestedtime as timestamp), :indoorunit_id, :success, :userID, $)");

	private GenericDAO<RcoperationLog> dao;

	@Resource(name = "properties")
	private Properties bizProperties;

	@Autowired
	private IndoorUnitsService indoorUnitsService;

	@Autowired
	private SQLDAO sqlDao;

	@Autowired
	private FanSpeedMasterService fanspeedmasterService;

	@Autowired
	private WindDirectionMasterService winddirectionmasterService;

	@Autowired
	public void setDao(GenericDAO<RcoperationLog> daoToSet) {
		dao = daoToSet;
		dao.setClazz(RcoperationLog.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.RCOperationLogService#
	 * logRCoperationPerformedByUser(java.lang.Long, java.util.List,
	 * java.util.Map)
	 */
	@Transactional
	public void logRCoperationsPerformedByUser(Long userID, Set<Long> iduIDs,
			Map<String, String> rcOperationPerformedMap, Boolean isSuccess)
			throws HibernateException {
		throw new UnsupportedOperationException(
				"Operation supported only for single IDU");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.RCOperationLogService#
	 * getRcoperationLogsByDateTimeRange(java.lang.String, java.lang.String)
	 */
	@Override
	public List<RCOperationLogVO> getRcoperationLogsByDateTimeRange(
			RCOperationLogRequest logRequest, Long userid) throws Exception {
		List<RCOperationLogVO> rcoperationLogVOs = getRCOperationsLogByDateTimeRange(
				logRequest, userid);
		return rcoperationLogVOs;
	}

	@Transactional
	@Override
	public void logRCoperationsPerformedByUser(Long userID, Long iduID,
			Map<String, String> rcOperationPerformedMap, Boolean isSuccess)
			throws HibernateException {

		// RcoperationLog rcoperationLog = null;

		try {

			String actionName = null;
			String actionValue = null;
			StringBuilder columns = new StringBuilder();
			StringBuilder placeHolders = new StringBuilder();

			// rcoperationLog = new RcoperationLog();

			Map<String, Object> params = new HashMap<String, Object>();

			LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

			for (Map.Entry<String, String> entry : rcOperationPerformedMap
					.entrySet()) {
				actionName = entry.getKey();
				actionValue = entry.getValue();

				switch (RCOperation.valueOf(actionName.toUpperCase())) {
				case MODE:

					columns.append(STR_COMMA).append(STR_AIRCONMODE);

					placeHolders.append(STR_COMMA).append(STR_COLLON)
							.append(STR_AIRCONMODE);

					params.put(STR_AIRCONMODE, actionValue);
					scalarMapping
							.put(STR_AIRCONMODE, StandardBasicTypes.STRING);
					break;
				case TEMPERATURE:

					columns.append(STR_COMMA).append("temperature");

					placeHolders.append(STR_COMMA).append(":temperature");

					params.put("temperature", actionValue);
					scalarMapping.put("temperature", StandardBasicTypes.STRING);
					break;
				case FANSPEED:

					columns.append(STR_COMMA).append("fanspeed");

					placeHolders.append(STR_COMMA).append(":fanspeed");

					params.put("fanspeed", actionValue);
					scalarMapping.put("fanspeed", StandardBasicTypes.STRING);
					break;
				case WINDDIRECTION:

					columns.append(STR_COMMA).append("flapmode");

					placeHolders.append(STR_COMMA).append(":flapmode");

					params.put("flapmode", actionValue);
					scalarMapping.put("flapmode", StandardBasicTypes.STRING);
					break;
				case POWERSTATUS:

					columns.append(STR_COMMA).append("powerstatus");

					placeHolders.append(STR_COMMA).append(":powerstatus");

					params.put("powerstatus", actionValue);
					scalarMapping.put("powerstatus", StandardBasicTypes.STRING);
					break;
				case ENERGY_SAVING:

					columns.append(STR_COMMA).append("energysaving");

					placeHolders.append(STR_COMMA).append(":energysaving");

					params.put("energysaving", actionValue);
					scalarMapping
							.put("energysaving", StandardBasicTypes.STRING);
					break;
				case PROHIBITION_POWERSTATUS:

					columns.append(STR_COMMA).append("prohibitionpowerstatus");

					placeHolders.append(STR_COMMA).append(
							":prohibitionpowerstatus");

					params.put("prohibitionpowerstatus", actionValue);
					scalarMapping.put("prohibitionpowerstatus",
							StandardBasicTypes.STRING);
					break;
				case PROHIBITON_MODE:

					columns.append(STR_COMMA).append("prohibitonmode");

					placeHolders.append(STR_COMMA).append(":prohibitonmode");

					params.put("prohibitonmode", actionValue);
					scalarMapping.put("prohibitonmode",
							StandardBasicTypes.STRING);
					break;
				case PROHIBITION_FANSPEED:

					columns.append(STR_COMMA).append("prohibitionfanspeed");

					placeHolders.append(STR_COMMA).append(
							":prohibitionfanspeed");

					params.put("prohibitionfanspeed", actionValue);
					scalarMapping.put("prohibitionfanspeed",
							StandardBasicTypes.STRING);
					break;
				case PROHIBITION_WINDRIECTION:

					columns.append(STR_COMMA).append("prohibitionwindriection");

					placeHolders.append(STR_COMMA).append(
							":prohibitionwindriection");

					params.put("prohibitionwindriection", actionValue);
					scalarMapping.put("prohibitionwindriection",
							StandardBasicTypes.STRING);
					break;
				case PROHIBITION_SET_TEMP:

					columns.append(STR_COMMA).append("prohibitionsettemp");

					placeHolders.append(STR_COMMA)
							.append(":prohibitionsettemp");

					params.put("prohibitionsettemp", actionValue);
					scalarMapping.put("prohibitionsettemp",
							StandardBasicTypes.STRING);
					break;
				case PROHIBITION_ENERGY_SAVING:

					columns.append(STR_COMMA)
							.append("prohibitionenergy_saving");

					placeHolders.append(STR_COMMA).append(
							":prohibitionenergy_saving");

					params.put("prohibitionenergy_saving", actionValue);
					scalarMapping.put("prohibitionenergy_saving",
							StandardBasicTypes.STRING);
					break;
				default:
					break;
				}
			}

			Indoorunit indoorunit = indoorUnitsService.getIndoorunitById(iduID);
			Timezonemaster caTimeZone = indoorunit.getAdapter()
					.getTimezonemaster();

			String actionInitiatedTime = TimeZoneUtil.convertDateToTimeZone(
					caTimeZone.getTimezone(), new Date());

			params.put("indoorunit_id", iduID);
			scalarMapping.put("indoorunit_id", StandardBasicTypes.LONG);
			params.put("userID", userID);
			scalarMapping.put("userID", StandardBasicTypes.LONG);
			params.put("success", isSuccess);
			scalarMapping.put("success", StandardBasicTypes.BOOLEAN);
			params.put("creationdate", CommonUtil.dateToString(new Date(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));
			scalarMapping.put("creationdate", StandardBasicTypes.STRING);
			params.put("requestedtime", actionInitiatedTime);
			scalarMapping.put("requestedtime", StandardBasicTypes.STRING);

			columns.delete(0, 1);
			placeHolders.delete(0, 1);

			String query = SQL_INSERT_RCOPERATIONSLOG.toString().replaceFirst(
					"\\$", columns.toString());

			query = query.replace("$", placeHolders.toString());

			sqlDao.executeSQLUpdateQuery(query, params, scalarMapping);

		} catch (Exception ex) {
			logger.error(
					"Error occured in logging RC Operations performed by user for indoor units:"
							+ iduID, ex);
			throw new GenericFailureException("db.error", ex);
		}

	}

	@Transactional
	public List<RCOperationLogVO> getRCOperationsLogByDateTimeRange(
			RCOperationLogRequest logRequest, Long userid) throws Exception {

		StringBuilder unitIDs = new StringBuilder();

		for (int i = 0; i < logRequest.getUnitIDs().size(); i++) {

			unitIDs.append(logRequest.getUnitIDs().get(i));

			if (i < logRequest.getUnitIDs().size() - 1) {
				unitIDs.append(BizConstants.COMMA_STRING);
			}
		}

		// To Read value of pagination record count from biz.properties
		Integer recordCount = Integer.valueOf(bizProperties
				.getProperty(BizConstants.RCLOG_PAGINATION));

		String query = String.format(
				SQL_GET_RCOPERATIONSLOG_BY_DATETIME.toString()
						+ SQL_PAGINATION.toString(),
				logRequest.getFromDateTime(), logRequest.getToDateTime(),
				unitIDs.toString(), recordCount, logRequest.getPageNo(),
				recordCount);

		List<?> resultList = sqlDao.executeSQLSelect(query);

		boolean isDownload = false;

		List<RCOperationLogVO> rcOperationLogVOs = processResult(resultList,
				isDownload);

		return rcOperationLogVOs;
	}

	/**
	 * @param resultList
	 * @return
	 */
	private List<RCOperationLogVO> processResult(List<?> resultList,
			boolean isDownload) {

		List<RCOperationLogVO> rcOperationLogVOs = new ArrayList<>();

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;
			RCOperationLogVO rcOperationLogVO = null;

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

				rcOperationLogVO = new RCOperationLogVO();

				rcOperationLogVO.setIDUId(rowData[0] != null ? String
						.valueOf(rowData[0]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setSerialNumber(rowData[1] != null ? String
						.valueOf(rowData[1]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setCAName(rowData[2] != null ? String
						.valueOf(rowData[2]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setAirconmode(rowData[3] != null ? String
						.valueOf(rowData[3]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setTemperature(rowData[4] != null ? String
						.valueOf(rowData[4]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setFanspeed(rowData[5] != null ? String
						.valueOf(rowData[5]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setFlapMode(rowData[6] != null ? String
						.valueOf(rowData[6]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setPowerstatus(rowData[7] != null ? String
						.valueOf(rowData[7]) : BizConstants.DASH_STRING);

				if (isDownload) {

					// Modified By Ravi
					rcOperationLogVO
							.setEnergysaving(rowData[8] != null ? (String
									.valueOf(rowData[8]).equals(
											BizConstants.ONE) ? BizConstants.ON
									: BizConstants.OFF)
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionpowerstatus(rowData[9] != null ? String
									.valueOf(rowData[9]).equals(
											BizConstants.ONE) ? BizConstants.YES
									: BizConstants.NO
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitonmode(rowData[10] != null ? String
									.valueOf(rowData[10]).equals(
											BizConstants.ONE) ? BizConstants.YES
									: BizConstants.NO
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionfanspeed(rowData[11] != null ? String
									.valueOf(rowData[11]).equals(
											BizConstants.ONE) ? BizConstants.YES
									: BizConstants.NO
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionwindriection(rowData[12] != null ? String
									.valueOf(rowData[12]).equals(
											BizConstants.ONE) ? BizConstants.YES
									: BizConstants.NO
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionsettemp(rowData[13] != null ? String
									.valueOf(rowData[13]).equals(
											BizConstants.ONE) ? BizConstants.YES
									: BizConstants.NO
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionEnergySaving(rowData[18] != null ? String
									.valueOf(rowData[18]).equals(
											BizConstants.ONE) ? BizConstants.YES
									: BizConstants.NO
									: BizConstants.DASH_STRING);
				} else {

					rcOperationLogVO
							.setEnergysaving(rowData[8] != null ? String
									.valueOf(rowData[8])
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionpowerstatus(rowData[9] != null ? String
									.valueOf(rowData[9])
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitonmode(rowData[10] != null ? String
									.valueOf(rowData[10])
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionfanspeed(rowData[11] != null ? String
									.valueOf(rowData[11])
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionwindriection(rowData[12] != null ? String
									.valueOf(rowData[12])
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionsettemp(rowData[13] != null ? String
									.valueOf(rowData[13])
									: BizConstants.DASH_STRING);

					rcOperationLogVO
							.setProhibitionEnergySaving(rowData[18] != null ? String
									.valueOf(rowData[18])
									: BizConstants.DASH_STRING);

				}

				rcOperationLogVO.setDate(rowData[14] != null ? String
						.valueOf(rowData[14]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setTime(rowData[15] != null ? String
						.valueOf(rowData[15]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setResult(rowData[16] != null ? String
						.valueOf(rowData[16]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setUsername(rowData[17] != null ? String
						.valueOf(rowData[17]) : BizConstants.DASH_STRING);

				rcOperationLogVO
						.setRoomTemperature(rowData[19] != null ? String
								.valueOf(rowData[19])
								: BizConstants.DASH_STRING);

				rcOperationLogVO.setIduName(rowData[20] != null ? String
						.valueOf(rowData[20]) : BizConstants.DASH_STRING);

				//Modified By Ravi
				rcOperationLogVO.setSite(rowData[22] != null ? String
						.valueOf(rowData[22]) : BizConstants.DASH_STRING);

				rcOperationLogVO.setLocation(rowData[21] != null ? String
						.valueOf(rowData[21]) : BizConstants.DASH_STRING);
				//End of Modification

				rcOperationLogVOs.add(rcOperationLogVO);
			}
		}
		return rcOperationLogVOs;
	}

	@Override
	@Transactional
	public Integer getRcoperationLogsPageCount(
			RCOperationLogRequest logRequest, Long userid) throws Exception {

		StringBuilder unitIDs = new StringBuilder();

		for (int i = 0; i < logRequest.getUnitIDs().size(); i++) {

			unitIDs.append(logRequest.getUnitIDs().get(i));

			if (i < logRequest.getUnitIDs().size() - 1) {
				unitIDs.append(BizConstants.COMMA_STRING);
			}
		}

		// To Read value of pagination record count from biz.properties
		Integer recordCount = Integer.valueOf(bizProperties
				.getProperty(BizConstants.RCLOG_PAGINATION));

		// Default page count
		Double pageCount = 1D;

		String query = String.format(
				SQL_GET_RCOPERATIONSLOG_FOR_PAGE_COUNT.toString(),
				logRequest.getFromDateTime(), logRequest.getToDateTime(),
				unitIDs.toString());

		List<?> resultList = sqlDao.executeSQLSelect(query);

		if (resultList != null && resultList.size() > 0) {
			pageCount = ((BigInteger) resultList.get(0)).doubleValue();
		}

		return (int) Math.ceil(pageCount / recordCount);
	}

	@Override
	@Transactional
	public String generateReportRCOperationsLogByDateTimeRange(
			RCOperationLogRequest logRequest, String reportType,
			SessionInfo sessionInfo) throws Exception {

		String filePath = null;

		StringBuilder unitIDs = new StringBuilder();

		for (int i = 0; i < logRequest.getUnitIDs().size(); i++) {

			unitIDs.append(logRequest.getUnitIDs().get(i));

			if (i < logRequest.getUnitIDs().size() - 1) {
				unitIDs.append(BizConstants.COMMA_STRING);
			}
		}

		String query = String.format(
				SQL_GET_RCOPERATIONSLOG_BY_DATETIME.toString(),
				logRequest.getFromDateTime(), logRequest.getToDateTime(),
				unitIDs.toString());

		List<?> resultList = sqlDao.executeSQLSelect(query);

		boolean isDownload = true;

		List<RCOperationLogVO> rcOperationLogVOs = processResult(resultList,
				isDownload);

		if (reportType.equals(BizConstants.REPORT_TYPE_EXCEL)) {
			filePath = generateExcelReport(rcOperationLogVOs, logRequest,
					sessionInfo);
		} else if (reportType.equals(BizConstants.REPORT_TYPE_CSV)) {
			filePath = generateCSVReport(rcOperationLogVOs, logRequest,
					sessionInfo);
		} else {
			throw new UnsupportedOperationException(reportType
					+ " - This report type not supported");
		}

		return filePath;
	}

	private String generateCSVReport(List<RCOperationLogVO> rcOperationLogVOs,
			RCOperationLogRequest logRequest, SessionInfo sessionInfo) {

		String filePath = null;

		// Generates an excel report
		ReportGenerator<RCOperationLogVO> reportGenerator = new CSVReportGenerator<RCOperationLogVO>();

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DISTREPORT_HEADER);

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				RCOperationLogVO.class, "View Log Report", "ViewLog_"
						+ dateFormat.format(new Date()));

		// metadata.setDataTableHeaderTextAlignment("CENTERE");

		metadata.setHeadingTextProperties(getReportHeadings(
				sessionInfo.getUserTimeZone(), logRequest));

		metadata.setDataTableHeading(getDataTableHeaders());

		// Writes the excel metadata and dataList received in the excel
		try {
			filePath = reportGenerator.writeTabularReport(metadata,
					rcOperationLogVOs);
		} catch (Exception e) {
			logger.error(
					"Error occured while generating CSV report for View Log", e);
		}

		return filePath;
	}

	private String generateExcelReport(
			List<RCOperationLogVO> rcOperationLogVOs,
			RCOperationLogRequest logRequest, SessionInfo sessionInfo) {

		String filePath = null;

		// Generates an CSV report
		ReportGenerator<RCOperationLogVO> reportGenerator = new ExcelReportGenerator<RCOperationLogVO>();

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DISTREPORT_HEADER);

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				RCOperationLogVO.class, "View Log Report", "ViewLog_"
						+ dateFormat.format(new Date()));

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("View Log Report");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		metadata.setHeadingTextProperties(getReportHeadings(
				sessionInfo.getUserTimeZone(), logRequest));

		metadata.setDataTableHeading(getDataTableHeaders());

		// Writes the excel metadata and dataList received in the excel
		try {
			filePath = reportGenerator.writeTabularReport(metadata,
					rcOperationLogVOs);
		} catch (Exception e) {
			logger.error(
					"Error occured while generating Excel report for View Log",
					e);
		}

		return filePath;
	}

	private List<HeadingTextProperties> getReportHeadings(String userTimeZone,
			RCOperationLogRequest logRequest) {
		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("View Log Report");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		try {
			//Modified By Ravi
			headertext.setValue(TimeZoneUtil.convertDateToTimeZone(
					userTimeZone, new Date(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD));
		} catch (ParseException e) {
			logger.error("Error occured while Converting current time to User Timezone");
		}

		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName("Specified display term");
		headertext.setValue(logRequest.getFromDateTime() + " to "
				+ logRequest.getToDateTime());
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	/**
	 * Set column names for AC Details download
	 * 
	 * @return
	 */
	private List<DataTableHeader> getDataTableHeaders() {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();

		heading = new DataTableHeader();
		heading.setColumnName("site");
		heading.setDisplayName("Site");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("location");
		heading.setDisplayName("Location");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("serialNumber");
		heading.setDisplayName("I/D Serial Number");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("iduName");
		heading.setDisplayName("I/D Unit Name");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("CAName");
		heading.setDisplayName("CA Name");
		heading.setSequence(5);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("powerstatus");
		heading.setDisplayName("On/Off Status");
		heading.setSequence(6);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("temperature");
		heading.setDisplayName("Setting Temp");
		heading.setSequence(7);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("airconmode");
		heading.setDisplayName("Operation Mode");
		heading.setSequence(8);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("fanspeed");
		heading.setDisplayName("Fan Speed");
		heading.setSequence(9);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("flapMode");
		heading.setDisplayName("Vane Direction");
		heading.setSequence(10);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("energysaving");
		heading.setDisplayName("Energy Saving");
		heading.setSequence(11);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("roomTemperature");
		heading.setDisplayName("Room Temp");
		heading.setSequence(12);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitionpowerstatus");
		heading.setDisplayName("Prohibit R/C Use (On/Off)");
		heading.setSequence(13);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitonmode");
		heading.setDisplayName("Prohibit R/C Use (mode)");
		heading.setSequence(14);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitionfanspeed");
		heading.setDisplayName("Prohibit R/C Use (fan speed)");
		heading.setSequence(15);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitionwindriection");
		heading.setDisplayName("Prohibit R/C Use (flap)");
		heading.setSequence(16);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitionsettemp");
		heading.setDisplayName("Prohibit R/C Use (Set Temp)");
		heading.setSequence(17);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("prohibitionEnergySaving");
		heading.setDisplayName("Prohibit R/C Use (energy saving)");
		heading.setSequence(18);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("date");
		heading.setDisplayName("Date");
		heading.setSequence(19);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("time");
		heading.setDisplayName("Time");
		heading.setSequence(20);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("username");
		heading.setDisplayName("User ID");
		heading.setSequence(21);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;
	}

}

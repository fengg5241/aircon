/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.cr.dao.service.CutoffRequestDAOservice;
import com.panasonic.b2bacns.bizportal.cr.dao.service.CutoffRequestTranscDAOservice;
import com.panasonic.b2bacns.bizportal.cr.dao.service.DistributionDetailDAOservice;
import com.panasonic.b2bacns.bizportal.cr.dao.service.DistributionRatioDAOservice;
import com.panasonic.b2bacns.bizportal.cr.vo.DistibutionGroupVo;
import com.panasonic.b2bacns.bizportal.cr.vo.IdPowerDetailVo;
import com.panasonic.b2bacns.bizportal.cr.vo.PowerDetail;
import com.panasonic.b2bacns.bizportal.cr.vo.PowerDistHeader;
import com.panasonic.b2bacns.bizportal.cr.vo.PowerRatio;
import com.panasonic.b2bacns.bizportal.cr.vo.RegisteredCutoffRequestDetails;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.persistence.CutoffRequest;
import com.panasonic.b2bacns.bizportal.persistence.CutoffRequestTransaction;
import com.panasonic.b2bacns.bizportal.persistence.DistributionDetailData;
import com.panasonic.b2bacns.bizportal.persistence.DistributionDetailDataPK;
import com.panasonic.b2bacns.bizportal.persistence.DistributionGroup;
import com.panasonic.b2bacns.bizportal.persistence.DistributionRatioData;
import com.panasonic.b2bacns.bizportal.persistence.DistributionRatioDataPK;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.service.GroupService;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.common.reports.DataTableHeader;
import com.panasonic.b2bacns.common.reports.HeadingTextProperties;
import com.panasonic.b2bacns.common.reports.ReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportMetadata;
import com.panasonic.spf.b2bac.dataaggregation.DataAggregationException;
import com.panasonic.spf.b2bac.dataaggregation.api.DistributeCutoffManager;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.DistributeCutoffRequest;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.RegistCutoffRequestParam;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.RegistCutoffRequestResult;

/**
 * @author simanchal.patra
 *
 */
@Service
public class ManageCutoffRequestServiceImpl implements
		ManageCutoffRequestService {

	private static final Logger LOGGER = Logger
			.getLogger(ManageCutoffRequestServiceImpl.class);

	private static final String GET_DISTRIBUTION_RATIO_DATA = "select dcinfo.cutoff_request_id, dcresult.distribution_rate,"
			+ " dcresult.distribution_val,  dcresult.cutoff_actual_start_datetime,  dcresult.cutoff_actual_end_datetime,"
			+ " idu.id as indoorunitid, pm.id as pulsemeterid, dg.id as distribute_grp_id, dcresult.facl_id facl_id"
			+ " from  vw_distribute_cutoff_info dcinfo"
			+ " inner join vw_distribute_cutoff_result dcresult"
			+ " on dcinfo.cutoff_distribution_id = dcresult.cutoff_distribution_id"
			+ " left outer join indoorunits idu"
			+ " on idu.oid = dcresult.facl_id"
			+ " left join pulse_meter pm on pm.oid = dcresult.facl_id"
			+ " Left outer join distribution_group dg on dg.id = (CASE WHEN idu.id IS NOT NULL THEN idu.distribution_group_id ELSE pm.distribution_group_id END)"
			+ " where dcinfo.cutoff_request_id  in (%s)"
			+ " order by cutoff_request_id, distribute_grp_id, idu.id";

	private static final String GET_DISTRIBUTION_DETAIL_DATA = "select dcinfo.cutoff_request_id, idu.id as indoorunitid,"
			+ " dcinfo.cutoff_actual_start_datetime, dcinfo.cutoff_actual_end_datetime,"
			+ " dcevi.evidence_item_cd, dcevi.evidence_value, "			
			+ " pm.id as pulsemeterid, dg.id as distribute_grp_id, dcevi.facl_id facl_id"
			+ " from  vw_distribute_cutoff_info dcinfo inner join vw_distribute_cutoff_evidence dcevi"
			+ " on dcinfo.cutoff_distribution_id = dcevi.cutoff_distribution_id"
			+ " left outer join indoorunits idu on idu.oid = dcevi.facl_id"			
			+ " left join pulse_meter pm on pm.oid = dcevi.facl_id"
			+ " Left outer join distribution_group dg on dg.id = (CASE WHEN idu.id IS NOT NULL THEN idu.distribution_group_id ELSE pm.distribution_group_id END)"
			+ " where dcinfo.cutoff_request_id in (%s)"
			+ " order by cutoff_request_id, distribute_grp_id, idu.id,"
			+ " dcinfo.cutoff_actual_start_datetime, dcinfo.cutoff_actual_end_datetime";

	private static final String GET_CTR_STATUS = "select dcinfo.cutoff_request_id,  dcinfo.distribution_cutoff_status, dcinfo.cutoff_actual_end_datetime"
			+ " from vw_distribute_cutoff_info dcinfo where dcinfo.cutoff_request_id in (%s)";

	private static final String RESULT_NOPROGRESS = "00";

	//Modified by Ravi
	private static final String DAY_START_TIME = " 00:00:00";
	private static final String DAY_END_TIME = " 00:00:00";
	//End of Modification by Ravi
	
	//Added by Ravi
	private static final String GET_TIMEZONES = "select tzm.* from adapters a inner join timezonemaster tzm on tzm.id = a.timezone where a.siteid in ('%s') group by tzm.id";

	@Value("${ctr.history.list.size}")
	private Integer requestListSize;

	private DistributeCutoffManager disCutoofManager = new DistributeCutoffManager();

	@Autowired
	private CutoffRequestDAOservice cutoffRequestDAOservice;

	@Autowired
	private CutoffRequestTranscDAOservice cutoffRequestTranscDAOservice;

	@Autowired
	private GroupService groupService;

	@Autowired
	private IndoorUnitsService iduDAOService;

	@Autowired
	private SQLDAO sqlDAO;

	@Autowired
	private DistributionRatioDAOservice distributionRatioDAOservice;

	@Autowired
	private DistributionDetailDAOservice distributionDetailDAOservice;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.cr.service.CutoffRequestService#
	 * getAllRegisteredCutoffRequests(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<RegisteredCutoffRequestDetails> getAllRegisteredCutoffRequests(
			Long userID) {

		List<String> stausList = new ArrayList<>();
		stausList.add("10");

		Criteria crFilter = cutoffRequestDAOservice.createCriteria();
		// crFilter.setFetchMode("cutoffRequestTransactions",
		// FetchMode.JOIN);
		crFilter.createAlias("cutoffRequestTransactions", "trnsc",
				JoinType.INNER_JOIN);
		crFilter.add(Restrictions.eq("createdby", userID));
		crFilter.add(Restrictions.not(Restrictions.in("status", stausList)));
		crFilter.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		crFilter.addOrder(Order.desc("creationdate"));

		List<CutoffRequest> cutoffRequests = (List<CutoffRequest>) crFilter
				.list();

		Map<Long, CutoffRequest> cutoffRequestsMap = new HashMap<>();

		RegisteredCutoffRequestDetails ctfRequestVo = null;
		List<RegisteredCutoffRequestDetails> resultList = new ArrayList<>();

		StringBuilder requestID = new StringBuilder();

		CutoffRequest ctfRequest = null;

		LOGGER.debug("Status of " + cutoffRequests.size()
				+ " cutoff requests need to check with PF");

		// Check status with PF
		for (int i = 0; i < cutoffRequests.size(); i++) {

			ctfRequest = cutoffRequests.get(i);

			// if (StringUtils.isBlank(ctfRequest.getStatus())
			// || !ctfRequest.getStatus().equals("10")) {

			// Cutoff request status
			// 00: Not Progress
			// 01: In Progress ===== Need for display
			// 10: Complete ====== Need for Display
			// 11: InComplete ===== Need for Display
			// 12: No Data ======= Need for Display
			// 90: Abnormal Stop

			cutoffRequestsMap.put(ctfRequest.getPlatformtransactionId(),
					ctfRequest);
			if (requestID.length() > 0) {
				requestID.append(",");
			}
			requestID.append(String.valueOf(ctfRequest
					.getPlatformtransactionId()));
			// }
		}

		if (!cutoffRequestsMap.isEmpty()) {

			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("transactionIDs", requestID.toString());

			LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();
			scalarMapping.put("cutoff_request_id", StandardBasicTypes.LONG);
			scalarMapping.put("distribution_cutoff_status",
					StandardBasicTypes.STRING);
			scalarMapping.put("cutoff_actual_end_datetime",
					StandardBasicTypes.TIMESTAMP);

			List<?> sqlResultList = sqlDAO.executeSQLSelect(
					String.format(GET_CTR_STATUS, requestID.toString()),
					scalarMapping);

			if (sqlResultList != null && sqlResultList.size() > 0) {

				Object[] row = null;

				List<CutoffRequest> updateableList = new ArrayList<>();

				List<CutoffRequest> dataDownloadableList = new ArrayList<>();

				ListIterator<?> resultItr = sqlResultList.listIterator();
				while (resultItr.hasNext()) {
					row = (Object[]) resultItr.next();

					ctfRequest = cutoffRequestsMap.get((Long) row[0]);
					ctfRequest.setStatus((String) row[1]);

					if (ctfRequest.getStatus().equals("10")
							|| ctfRequest.getStatus().equals("11")) {
						dataDownloadableList.add(ctfRequest);
						// update the completion date in table
						ctfRequest.setCompletionDate(new Date(
								((Timestamp) row[2]).getTime()));
					}

					if (ctfRequest.getDistdetailReportFilepath() != null) {
						// so that next report should be generated with updated
						// data, otherwise it will read the existing report
						ctfRequest.setDistdetailReportFilepath(null);
					}
					if (ctfRequest.getDistratioReportFilepath() != null) {
						// so that next report should be generated with updated
						// data, otherwise it will read the existing report
						ctfRequest.setDistratioReportFilepath(null);
					}
					updateableList.add(ctfRequest);
				}

				// Update the status in APP DB
				cutoffRequestDAOservice.batchSaveOrUpdate(updateableList);

				if (dataDownloadableList.size() > 0) {

					LOGGER.debug("Downloding data from PF for cutoff requests, count - "
							+ dataDownloadableList.size());
					try {
						getAndSaveDistributionRatioData(dataDownloadableList,
								cutoffRequestsMap);
					} catch (Exception e) {
						LOGGER.error(
								"Error occurred while downloading and saving"
										+ " Distribution Ratio data", e);
					}

					try {
						getAndSaveDistributionDetailData(dataDownloadableList,
								cutoffRequestsMap);
					} catch (Exception e) {
						LOGGER.error(
								"Error occurred while downloading and saving"
										+ " Distribution Detail data", e);
					}

				}
			}
		}

		// Get Updated data from DB, as data updated in DB
		crFilter = cutoffRequestDAOservice.createCriteria();
		crFilter.createAlias("cutoffRequestTransactions", "trnsc",
				JoinType.INNER_JOIN);
		crFilter.add(Restrictions.eq("createdby", userID));
		crFilter.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		crFilter.addOrder(Order.desc("creationdate"));

		cutoffRequests = (List<CutoffRequest>) crFilter.list();

		List<String> siteList = null;

		int count = 0;

		// Download data from PF DB
		for (CutoffRequest ctfRequest1 : cutoffRequests) {

			if (count == requestListSize) {
				break;
			}

			ctfRequestVo = new RegisteredCutoffRequestDetails();

			ctfRequestVo.setAppRegistaionID(ctfRequest1.getId());

			if (ctfRequest1.getTodate() != null) {
				ctfRequestVo.setCompletionDate(CommonUtil.dateToString(
						ctfRequest1.getTodate(),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));
			}

			if (ctfRequest1.getFromdate() != null) {
				ctfRequestVo.setStartingDate(CommonUtil
						.dateToString(ctfRequest1.getFromdate()));
			}
			ctfRequestVo.setTransactionId(ctfRequest1
					.getPlatformtransactionId());

			if (ctfRequest1.getCreationdate() != null) {
				ctfRequestVo.setTransactionTime(CommonUtil.dateToString(
						ctfRequest1.getCreationdate(),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));
			}

			switch (ctfRequest1.getStatus()) {
			// Cutoff request status
			// 00: Not Progress
			// 01: In Progress ===== Need for display
			// 10: Complete ====== Need for Display
			// 11: InComplete ===== Need for Display
			// 12: No Data ======= Need for Display
			// 90: Abnormal Stop
			case "00":
				ctfRequestVo.setResult("Not Progress");
				break;

			case "01":
				ctfRequestVo.setResult("In Progress");
				break;
			case "10":
				ctfRequestVo.setResult("Complete");
				break;
			case "11":
				//fix typo issue by shanf
				ctfRequestVo.setResult("Incomplete");
				break;
			case "12":
				ctfRequestVo.setResult("No Data");
				break;
			case "90":
				ctfRequestVo.setResult("Abnormal Stop");
				break;
			default:
				break;
			}

			siteList = new ArrayList<String>();

			for (CutoffRequestTransaction details : ctfRequest1
					.getCutoffRequestTransactions()) {
				siteList.add(details.getGroup().getName());
			}
			ctfRequestVo.setSites(siteList);

			resultList.add(ctfRequestVo);

			count++;
		}

		return resultList;
	}

	@Transactional(propagation = Propagation.NESTED)
	private void getAndSaveDistributionDetailData(
			List<CutoffRequest> updateableList,
			Map<Long, CutoffRequest> cutoffRequestsMap) {

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("transactionIDs", getIDCommaSeparated(updateableList));

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();
		scalarMapping.put("cutoff_request_id", StandardBasicTypes.LONG);
		scalarMapping.put("indoorunitid", StandardBasicTypes.LONG);
		scalarMapping.put("cutoff_actual_start_datetime",
				StandardBasicTypes.STRING);
		scalarMapping.put("cutoff_actual_end_datetime",
				StandardBasicTypes.STRING);
		scalarMapping.put("evidence_item_cd", StandardBasicTypes.STRING);
		scalarMapping.put("evidence_value", StandardBasicTypes.BIG_DECIMAL);
		// scalarMapping.put("powerusage_kwh", StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("pulsemeterid", StandardBasicTypes.LONG);
		scalarMapping.put("distribute_grp_id", StandardBasicTypes.LONG);
		scalarMapping.put("facl_id", StandardBasicTypes.STRING);

		List<?> dataSqlResultList = sqlDAO.executeSQLSelect(String.format(
				GET_DISTRIBUTION_DETAIL_DATA,
				getIDCommaSeparated(updateableList)), scalarMapping);

		Object[] row = null;

		DistributionDetailData record = null;

		IdPowerDetailVo key = null;

		long pfTranscID = 0;
		Long iduID = null;
		Long pmID = null;

		String evidenceItemCode = null;

		BigDecimal evidenceVal = null;

		if (dataSqlResultList != null && dataSqlResultList.size() > 0) {

			List<DistributionDetailData> insertDistributionDetailDataList = new ArrayList<>();

			Iterator<?> resultItr = dataSqlResultList.listIterator();

			Map<IdPowerDetailVo, DistributionDetailData> dataMap = new HashMap<>();
			
			//Added by ravi
			Long id = 0l;
			TimeZone tz = null;
			//End of Adding by ravi

			while (resultItr.hasNext()) {

				row = (Object[]) resultItr.next();

				pfTranscID = (Long) row[0];

				if (row[1] != null) {
					iduID = (Long) row[1];
					pmID = null;
					key = new IdPowerDetailVo(pfTranscID, iduID, pmID);
				} else if (row[6] != null) {
					pmID = (Long) row[6];
					iduID = null;
					key = new IdPowerDetailVo(pfTranscID, iduID, pmID);
				} else {
					// Ignore this record
					continue;
				}

				if (dataMap.containsKey(key)) {
					record = dataMap.get(key);
				} else {
					record = new DistributionDetailData();

					record.setCutoffRequest(cutoffRequestsMap.get(pfTranscID));

					record.setId(new DistributionDetailDataPK(record
							.getCutoffRequest().getId(), (String) row[8]));

					record.setIndoorunitId(iduID);

					record.setPulsemeterId(pmID);

					//Start of Modification by Ravi
					if(id != record.getCutoffRequest().getPlatformtransactionId() || tz == null){
						id = record.getCutoffRequest().getPlatformtransactionId();
						PowerDistHeader distHeader = cutoffRequestDAOservice.getPowerDistHeader(id);
						tz = TimeZone.getTimeZone(distHeader.getTimezone());
					}
					
					if(row[2] != null){
						Date timeStart = CommonUtil.stringToDate((String) row[2], BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
						String timeStartStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(timeStart.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
						Date timeStartCA = CommonUtil.stringToDate(timeStartStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
						record.setCutoffstartActualTime(timeStartCA);
					} else {
						record.setCutoffstartActualTime(null);
					}
					
					if(row[3] != null){
						Date timeEnd = CommonUtil.stringToDate((String) row[3], BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
						String timeEndStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(timeEnd.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
						Date timeEndCA = CommonUtil.stringToDate(timeEndStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
						record.setCutoffendActualTime(timeEndCA);
					} else {
						record.setCutoffendActualTime(null);
					}
					//End of Modification by Ravi
					// if (iduID != null) {
					// record.setPowerusageKwh((BigDecimal) row[6]);
					// }

					dataMap.put(key, record);
				}

				evidenceItemCode = (String) row[4];

				evidenceVal = (BigDecimal) row[5];

				switch (evidenceItemCode) {

				case "B6":
					// Working hours with thermostat off, high fan speed
					record.setWorkinghoursTstatOffHighFan(evidenceVal);
					break;
				case "B7":
					// Working hours with thermostat off, medium fan speed
					record.setWorkinghoursTstatOffMedFan(evidenceVal);
					break;
				case "B8":
					// Working hours with thermostat off, low fan speed
					record.setWorkinghoursTstatOffLowFan(evidenceVal);
					break;
				case "B9":
					// Working hours with thermostat on, high fan speed
					record.setWorkinghoursTstatOnhighFan(evidenceVal);
					break;
				case "B10":
					// Working hours with thermostat on, medium fan speed
					record.setWorkinghoursTstatOnMedFan(evidenceVal);
					break;
				case "B11":
					// Working hours with thermostat on, low fan speed
					record.setWorkinghoursTstatOnLowFan(evidenceVal);
					break;
				case "B12":
					// Rated Capacity
					record.setRatedcapacityKw(evidenceVal);
					break;
				case "M1":
					// Pulse meter
					record.setPulsemeterPowerUsage(evidenceVal);
					break;
				case "TEO":
					// "Power Usage Indexes" value for Facility ID: IDU = TEO in
					// distribute_cutoff_evidence" table
					record.setPowerusageKwh(evidenceVal);
					break;
				default:
					break;
				}

				evidenceItemCode = null;
				evidenceVal = null;
				// insertDistributionRatioDataList.add(record);

				evidenceItemCode = null;
			}

			insertDistributionDetailDataList.addAll(dataMap.values());

			distributionDetailDAOservice
					.batchSaveOrUpdate(insertDistributionDetailDataList);
		}

	}

	@Transactional(propagation = Propagation.NESTED)
	private void getAndSaveDistributionRatioData(
			List<CutoffRequest> updateableList,
			Map<Long, CutoffRequest> cutoffRequestsMap) {

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("transactionIDs", getIDCommaSeparated(updateableList));

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();
		scalarMapping.put("cutoff_request_id", StandardBasicTypes.LONG);
		scalarMapping.put("distribution_rate", StandardBasicTypes.DOUBLE);
		scalarMapping.put("distribution_val", StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("cutoff_actual_start_datetime",
				StandardBasicTypes.STRING);
		scalarMapping.put("cutoff_actual_end_datetime",
				StandardBasicTypes.STRING);
		scalarMapping.put("indoorunitid", StandardBasicTypes.LONG);
		scalarMapping.put("pulsemeterid", StandardBasicTypes.LONG);
		scalarMapping.put("facl_id", StandardBasicTypes.STRING);

		List<?> dataSqlResultList = sqlDAO.executeSQLSelect(String.format(
				GET_DISTRIBUTION_RATIO_DATA,
				getIDCommaSeparated(updateableList)), scalarMapping);

		Object[] row = null;

		DistributionRatioData record = null;

		if (dataSqlResultList != null && dataSqlResultList.size() > 0) {

			List<DistributionRatioData> insertDistributionRatioDataList = new ArrayList<>();

			Iterator<?> resultItr = dataSqlResultList.listIterator();
			
			//Added by ravi
			Long id = 0l;
			TimeZone tz = null;
			//End of Adding by ravi

			while (resultItr.hasNext()) {
				row = (Object[]) resultItr.next();

				if (row[5] == null && row[6] == null) {
					continue;
				}

				record = new DistributionRatioData();

				record.setCutoffRequest(cutoffRequestsMap.get((Long) row[0]));

				record.setId(new DistributionRatioDataPK(record
						.getCutoffRequest().getId(), (String) row[7]));

				if (row[1] != null) {
					record.setPowerdistributionRatio((Double) row[1]);
				}

				if (row[2] != null) {
					record.setPowerusageKwh((BigDecimal) row[2]);
				}
				//Start of Modification by Ravi
				if(id != record.getCutoffRequest().getPlatformtransactionId() || tz == null){
					id = record.getCutoffRequest().getPlatformtransactionId();
					PowerDistHeader distHeader = cutoffRequestDAOservice.getPowerDistHeader(id);
					tz = TimeZone.getTimeZone(distHeader.getTimezone());
				}
				if (row[3] != null){
					Date timeStart = CommonUtil.stringToDate((String) row[3], BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
					String timeStartStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(timeStart.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
					Date timeStartCA = CommonUtil.stringToDate(timeStartStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
					record.setCutoffstartActualTime(timeStartCA);
				} else {
					record.setCutoffstartActualTime(null);
				}
				if(row[4] != null){
					Date timeEnd = CommonUtil.stringToDate((String) row[4], BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
					String timeEndStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(timeEnd.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
					Date timeEndCA = CommonUtil.stringToDate(timeEndStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
					record.setCutoffendActualTime(timeEndCA);
				} else {
					record.setCutoffendActualTime(null);
				}
				//End of Modification by Ravi

				if (row[5] != null) {
					record.setIndoorunitId((Long) row[5]);
				}

				if (row[6] != null) {
					record.setPulsemeterId((Long) row[6]);
				}

				insertDistributionRatioDataList.add(record);
			}

			distributionRatioDAOservice
					.batchSaveOrUpdate(insertDistributionRatioDataList);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.cr.service.CutoffRequestService#
	 * registerCutoffRequest(java.lang.String, java.lang.String, java.util.List)
	 */
	@Transactional
	@Override
	// Modified By Ravi
	public RegisteredCutoffRequestDetails registerCutoffRequest(
			String fromdate, String todate, List<Long> siteIDList,
			String userLoginID, Long userID, Long companyID, List<?> timezone)
			throws BusinessFailureException {

		RegisteredCutoffRequestDetails registeredCutoffRequestDetails = new RegisteredCutoffRequestDetails();

		//change by shanf & Ravi as fromdate is DD/MM/YYYY
		Date endDate = CommonUtil.stringToDate(todate.concat(DAY_END_TIME),
				BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		
		//change by shanf & Ravi as fromdate is DD/MM/YYYY
		Date startDate = CommonUtil.stringToDate(fromdate.concat(DAY_START_TIME),
				BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		
		// Added by ravi
		long endDatePlus24Hours = endDate.getTime() + (24  * 60 * 60 * 1000);
		
		endDate = new Date(endDatePlus24Hours);
		
		if (endDate.before(startDate)) {
			throw new BusinessFailureException("invalid.date");
		}

		if (endDate.after(new Date())) {
			throw new BusinessFailureException("not.past.date");
		}

		Object[] row = (Object[]) timezone.get(0);
		
		TimeZone tz = TimeZone.getTimeZone(row[1].toString());
		
		String endTimeUTCStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toUTC(endDate.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		
		String startTimeUTCStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toUTC(startDate.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		
		Date endDateUTC = CommonUtil.stringToDate(endTimeUTCStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		
		Date startDateUTC = CommonUtil.stringToDate(startTimeUTCStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		// End of Adding by ravi

		RegistCutoffRequestParam cutoffReq = new RegistCutoffRequestParam();

		List<DistributeCutoffRequest> distCutoffReqList = new ArrayList<>();

		cutoffReq.distributeCutoffRequests = distCutoffReqList;

		DistributeCutoffRequest distributeCutoffRequest = null;

		Group site = null;

		StringBuilder siteIDs = new StringBuilder();

		List<String> siteNameList = new ArrayList<>();

		try {

			Date time = new Date();
			
			// Added by ravi for changing creation date to UTC
			String timeStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(time.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
			Date timeCA = CommonUtil.stringToDate(timeStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
			// End of adding by ravi for changing creation date to UTC

			CutoffRequest ctr = new CutoffRequest();
			ctr.setCreatedby(userID);
			// Modified By Ravi
			ctr.setCreationdate(timeCA);
			ctr.setFromdate(startDate);
			ctr.setTodate(endDate);
			ctr.setUpdatedate(time);
			ctr.setStatus(RESULT_NOPROGRESS);
			ctr.setName("Test Name, replace with actual name");

			createCutoffRequest(ctr);

			// Save the request

			CutoffRequestTransaction reqTrnsc = null;

			Set<Long> dgIDs = new HashSet<>();

			for (Long siteID : siteIDList) {

				siteIDs.append(String.valueOf(siteID)).append(",");

				site = groupService.findByID(siteID);

				siteNameList.add(site.getName());

				for (DistributionGroup dg : getDistributionGroups(site)) {

					if (!dgIDs.contains(dg.getId())) {

						dgIDs.add(dg.getId());

						distributeCutoffRequest = new DistributeCutoffRequest();

						distributeCutoffRequest.customerId = String
								.valueOf(companyID);

						// Modified By Ravi
						distributeCutoffRequest.cutoffRequestEndDate = endDateUTC;

						// Modified By Ravi
						distributeCutoffRequest.cutoffRequestStartDate = startDateUTC;

						distributeCutoffRequest.distributionGroupId = String
								.valueOf(dg.getId());

						cutoffReq.distributeCutoffRequests
								.add(distributeCutoffRequest);
					}
				}

				reqTrnsc = new CutoffRequestTransaction();
				reqTrnsc.setGroup(site);
				reqTrnsc.setCreationdate(time);
				reqTrnsc.setUpdatedate(time);
				reqTrnsc.setCutoffRequest(ctr);
				cutoffRequestTranscDAOservice.create(reqTrnsc);
			}

			RegistCutoffRequestResult result = disCutoofManager
					.registCutoffRequest(cutoffReq, userLoginID);

			registeredCutoffRequestDetails.setSites(siteNameList);
			registeredCutoffRequestDetails
					.setStartingDate(CommonUtil.dateToString(new Date(),
							BizConstants.DATE_FORMAT_YYYYMMDD));
			registeredCutoffRequestDetails.setTransactionTime(CommonUtil
					.dateToString(new Date(),
							BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

			if (result.cutoffRequestId != null) {

				registeredCutoffRequestDetails.setTransactionId(Long
						.valueOf(result.cutoffRequestId));
				registeredCutoffRequestDetails.setResult(RESULT_NOPROGRESS);

				ctr.setPlatformtransactionId(Long
						.valueOf(result.cutoffRequestId));
				cutoffRequestDAOservice.update(ctr);

				LOGGER.info(String
						.format("Transaction ID - [%s], New Cutoff Request has been successfully registered"
								+ " with Service Platform for Site [%s] time FROM [%s] TO [%s], by [%s]",
								result.cutoffRequestId, siteIDs.toString(),
								fromdate, todate, userLoginID));
			} else {

				registeredCutoffRequestDetails.setTransactionId(null);

				registeredCutoffRequestDetails.setResult(RESULT_NOPROGRESS);

				LOGGER.info(String
						.format("New Cutoff Request has failed to register"
								+ " with Service Platform for Site [%s] time FROM [%s] TO [%s], by [%s]",
								siteIDs.toString(), fromdate, todate,
								userLoginID));
			}

		} catch (DataAggregationException e) {
			LOGGER.error(String.format(
					"Error occurred while registering new cutoff"
							+ " request, time FROM [%s] TO [%s] by [%s]",
					fromdate, todate, userLoginID), e);
		}
		return registeredCutoffRequestDetails;
	}

	@Transactional(propagation = Propagation.NESTED)
	private CutoffRequest createCutoffRequest(CutoffRequest ctr) {

		try {
			ctr.setId((Long) cutoffRequestDAOservice.create(ctr));
		} catch (BusinessFailureException e) {
			LOGGER.error(
					String.format("Error occurred while registering new cutoff"
							+ " request, time FROM [%s] TO [%s] by [%s]",
							ctr.getFromdate(), ctr.getTodate(),
							ctr.getCreatedby()), e);
		}

		return ctr;
	}

	private String getIDCommaSeparated(List<CutoffRequest> cutoffRequests) {

		StringBuilder requestIDs = new StringBuilder();

		CutoffRequest ctfRequest = null;

		for (int i = 0; i < cutoffRequests.size(); i++) {
			ctfRequest = cutoffRequests.get(i);
			requestIDs.append(String.valueOf(ctfRequest
					.getPlatformtransactionId()));
			if (i < cutoffRequests.size() - 1) {
				requestIDs.append(",");
			}
		}
		return requestIDs.toString();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	private List<DistributionGroup> getDistributionGroups(Group site) {

		Criteria dgCriteria = iduDAOService.getCriteria();
		dgCriteria.createAlias("distributionGroup", "dg");
		dgCriteria.add(Restrictions.eq("site", site));
		dgCriteria
				.setProjection(Projections.groupProperty("distributionGroup"));
		// dgCriteria.setProjection(Projections.projectionList()
		// .add(Projections.property("dg.id"))
		// .add(Projections.property("dg.groupName"))
		// .add(Projections.property("dg.typeMeasurment")));
		dgCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return dgCriteria.list();
	}

	@Transactional
	@Override
	public List<DistibutionGroupVo> getDistributionGroupsBySiteID(Long siteID) {

		Group site = groupService.findByID(siteID);

		List<DistributionGroup> resultList = getDistributionGroups(site);

		Iterator<DistributionGroup> itr = resultList.iterator();

		DistibutionGroupVo vo = null;

		DistributionGroup distributionGroup = null;

		List<DistibutionGroupVo> dgDetailsList = new ArrayList<>();

		while (itr.hasNext()) {
			distributionGroup = itr.next();

			vo = new DistibutionGroupVo();

			vo.setId(distributionGroup.getId());
			vo.setGroupName(distributionGroup.getGroupName());
			vo.setTypeMeasurment(distributionGroup.getTypeMeasurment());

			dgDetailsList.add(vo);
		}

		return dgDetailsList;
	}

	@Transactional
	@Override
	public String getPowerRatioReportPath(Long transactionId,
			HttpServletResponse response) throws IOException,
			BusinessFailureException {

		String filePath = null;

		String errorMessage = null;

		List<CutoffRequest> cutoffRequests = cutoffRequestDAOservice
				.findAllByProperty("platformtransactionId", transactionId);

		if (cutoffRequests != null && cutoffRequests.size() > 0) {

			CutoffRequest cutoffRequest = cutoffRequests.get(0);

			if (cutoffRequest.getDistratioReportFilepath() != null) {

				filePath = cutoffRequest.getDistratioReportFilepath();

				if (filePath != null) {
					CommonUtil.writeDownloadableFile(response, new File(
							filePath));
				}
			} else {

				// creating list of Power Ratio
				List<PowerRatio> powerRatioData = distributionRatioDAOservice
						.getPowerRatioData(transactionId);

				// check if data exist in list
				if (powerRatioData != null && !powerRatioData.isEmpty()) {

					LOGGER.debug("Generationg power Ratio report for transaction ID - "
							+ transactionId);

					// get data for report header
					PowerDistHeader distHeader = cutoffRequestDAOservice
							.getPowerDistHeader(transactionId);

					// generate csv file for given list at a specific position
					filePath = generatePowerRatioReport(powerRatioData,
							distHeader);

					LOGGER.debug(String.format("PowerRatio Report generated "
							+ "for transaction ID %d and  file path is %s ",
							transactionId, filePath));

					// check if file is successfully generated
					if (filePath != null && StringUtils.isNotBlank(filePath)) {

						// update file path in DB
						cutoffRequest.setDistratioReportFilepath(filePath);

						cutoffRequestDAOservice.update(cutoffRequest);

						CommonUtil.writeDownloadableFile(response, new File(
								filePath));

					} else {
						errorMessage = CommonUtil
								.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
					}

				} else {
					errorMessage = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			}
		} else {

			errorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
		}

		// Report content already written in response body, so provide only
		// error message
		return errorMessage != null ? errorMessage : BizConstants.EMPTY_STRING;
	}

	private String generatePowerRatioReport(List<PowerRatio> powerRatioData,
			PowerDistHeader distHeader) {

		// Generates a csv report
		ReportGenerator<PowerRatio> fixture = new CSVReportGenerator<PowerRatio>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				PowerRatio.class,
				null,
				"Distr.Ratio-"
						+ CommonUtil.dateToString(
								CommonUtil.stringToDate(
										distHeader.getFromDate(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
								BizConstants.DATE_FORMAT_YYYYMMDD_DISTREPORT_HEADER)
						+ "to"
						+ CommonUtil.dateToString(
								CommonUtil.stringToDate(
										distHeader.getToDate(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
								BizConstants.DATE_FORMAT_YYYYMMDD_DISTREPORT_HEADER)
						+ "("
						+ CommonUtil
								.dateToString(
										new Date(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DISTREPORT_HEADER)
						+ ")");

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		metadata.setHeadingTextProperties(setPowerRatioHeaderText(distHeader));

		// Set headings for table columns
		List<DataTableHeader> tableHeadings = setPowerRatioTableHeader();

		metadata.setDataTableHeading(tableHeadings);

		Collection<PowerRatio> dataList = new ArrayList<PowerRatio>(
				powerRatioData);

		String fileName = null;

		try {
			// Writes the excel metadata and dataList received in the csv
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			LOGGER.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	private List<HeadingTextProperties> setPowerRatioHeaderText(
			PowerDistHeader distHeader) {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Distribution Ratio Report");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		// Start of modification by Ravi
		Date time = new Date();
		TimeZone tz = TimeZone.getTimeZone(distHeader.getTimezone());
		String timeStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(time.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		Date timeCA = CommonUtil.stringToDate(timeStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		headertext.setValue(CommonUtil.dateToString(timeCA,
				BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD));
		// end of modification by Ravi
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName("Specified display term");
		//Modified by ravi
		headertext.setValue(CommonUtil.dateToString(CommonUtil.stringToDate(
				distHeader.getFromDate(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
				BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD)
				+ " to "
				+ CommonUtil.dateToString(CommonUtil.stringToDate(
						distHeader.getToDate(),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName("Selected location");
		headertext.setValue(distHeader.getSite());
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	private List<DataTableHeader> setPowerRatioTableHeader() {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();

		heading = new DataTableHeader();
		heading.setColumnName("site");
		heading.setDisplayName("Site");
		heading.setSequence(0);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("distributionGroup");
		heading.setDisplayName("Distribution Group");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("iduId");
		heading.setDisplayName("IDU ID");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("pulseMeterId");
		heading.setDisplayName("Pulse Meter ID");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("area");
		heading.setDisplayName("Area");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("iduName");
		heading.setDisplayName("IDU Name");
		heading.setSequence(5);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("distributionRatio");
		heading.setDisplayName("Distr. Ratio");
		heading.setSequence(6);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("powerUsage");
		heading.setDisplayName("Power Usage (kWh)");
		heading.setSequence(7);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel1");
		heading.setDisplayName("Site Group Level 1");
		heading.setSequence(8);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel2");
		heading.setDisplayName("Site Group Level 2");
		heading.setSequence(9);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel3");
		heading.setDisplayName("Site Group Level 3");
		heading.setSequence(10);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel4");
		heading.setDisplayName("Site Group Level 4");
		heading.setSequence(11);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel5");
		heading.setDisplayName("Site Group Level 5");
		heading.setSequence(12);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("cutOffStartDateTime");
		heading.setDisplayName("Cut Off Start Time");
		heading.setSequence(13);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("cutOffEndDateTime");
		heading.setDisplayName("Cut Off End Time");
		heading.setSequence(14);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;

	}

	@Transactional
	@Override
	public String getPowerDetailReportPath(Long transactionId,
			HttpServletResponse response) throws IOException,
			BusinessFailureException {

		String filePath = null;

		String errorMessage = null;

		List<CutoffRequest> cutoffRequests = cutoffRequestDAOservice
				.findAllByProperty("platformtransactionId", transactionId);

		if (cutoffRequests != null && cutoffRequests.size() > 0) {

			CutoffRequest cutoffRequest = cutoffRequests.get(0);

			if (cutoffRequest.getDistdetailReportFilepath() != null) {

				filePath = cutoffRequest.getDistdetailReportFilepath();

				CommonUtil.writeDownloadableFile(response, new File(filePath));
			} else {

				// creating list of Power Detail
				List<PowerDetail> powerDetailData = distributionDetailDAOservice
						.getPowerDetailData(transactionId);

				LOGGER.debug("Generationg Power Distribution Deatail "
						+ "report for transaction ID " + transactionId);

				// check if data exist in list
				if (powerDetailData != null && !powerDetailData.isEmpty()) {

					// get data for report header
					PowerDistHeader distHeader = cutoffRequestDAOservice
							.getPowerDistHeader(transactionId);

					// generate csv file for given list at a specific position
					filePath = generatePowerDetailReport(powerDetailData,
							distHeader);

					LOGGER.debug(String
							.format("Power Distribution Detail report generated"
									+ " for transaction ID %d, file saved at location - %s",
									transactionId, filePath));

					// check if file is successfully generated
					if (filePath != null && StringUtils.isNotBlank(filePath)) {

						// update file path in DB
						cutoffRequest.setDistdetailReportFilepath(filePath);

						cutoffRequestDAOservice.update(cutoffRequest);

						CommonUtil.writeDownloadableFile(response, new File(
								filePath));
					} else {
						errorMessage = CommonUtil
								.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
					}

				} else {
					errorMessage = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}

			}

		} else {

			errorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
		}
		// Report content already written in response body, so provide only
		// error message
		return errorMessage != null ? errorMessage : BizConstants.EMPTY_STRING;
	}

	private String generatePowerDetailReport(List<PowerDetail> powerDetailData,
			PowerDistHeader distHeader) {

		// Generates a csv report
		ReportGenerator<PowerDetail> fixture = new CSVReportGenerator<PowerDetail>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(
				PowerDetail.class,
				null,
				"Distr.Detail-"
						+ CommonUtil.dateToString(
								CommonUtil.stringToDate(
										distHeader.getFromDate(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
								BizConstants.DATE_FORMAT_YYYYMMDD_DISTREPORT_HEADER)
						+ "to"
						+ CommonUtil.dateToString(
								CommonUtil.stringToDate(
										distHeader.getToDate(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
								BizConstants.DATE_FORMAT_YYYYMMDD_DISTREPORT_HEADER)
						+ "("
						+ CommonUtil
								.dateToString(
										new Date(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DISTREPORT_HEADER)
						+ ")");

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		// Set header text for report
		metadata.setHeadingTextProperties(setPowerDetailHeaderText(distHeader));

		// Set headings for table columns
		List<DataTableHeader> tableHeadings = setPowerDetailTableHeader();

		metadata.setDataTableHeading(tableHeadings);

		Collection<PowerDetail> dataList = new ArrayList<PowerDetail>(
				powerDetailData);
		String fileName = null;

		try {
			// Writes the excel metadata and dataList received in the csv
			fileName = fixture.writeTabularReport(metadata, dataList);
		} catch (Exception e) {
			LOGGER.error("Error occurred while writing data to Excel", e);
		}

		return fileName;

	}

	private List<HeadingTextProperties> setPowerDetailHeaderText(
			PowerDistHeader distHeader) {

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Distribution Detail Report");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		// Start of modification by Ravi
		Date time = new Date();
		TimeZone tz = TimeZone.getTimeZone(distHeader.getTimezone());
		String timeStr = CommonUtil.getCalendarWithDateFormat(CommonUtil.toLocalTime(time.getTime(), tz), BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		Date timeCA = CommonUtil.stringToDate(timeStr, BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD);
		headertext.setValue(CommonUtil.dateToString(timeCA,
				BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD));
		// end of modification by Ravi
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName("Specified display term");
		//Modified by ravi
		headertext.setValue(CommonUtil.dateToString(CommonUtil.stringToDate(
				distHeader.getFromDate(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
				BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD)
				+ " to "
				+ CommonUtil.dateToString(CommonUtil.stringToDate(
						distHeader.getToDate(),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName("Selected location");
		headertext.setValue(distHeader.getSite());
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	private List<DataTableHeader> setPowerDetailTableHeader() {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();

		heading = new DataTableHeader();
		heading.setColumnName("site");
		heading.setDisplayName("Site");
		heading.setSequence(0);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("distributionGroup");
		heading.setDisplayName("Distribution Group");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("iduId");
		heading.setDisplayName("IDU ID");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("pulseMeterId");
		heading.setDisplayName("Pulse Meter ID");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("area");
		heading.setDisplayName("Area");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("iduName");
		heading.setDisplayName("IDU Name");
		heading.setSequence(5);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("powerUsageIndexes");
		heading.setDisplayName("Power Usage Indexes");
		heading.setSequence(6);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("ratedCapacity");
		heading.setDisplayName("Rated Capacity (kw)");
		heading.setSequence(7);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("tsOnHighFan");
		// Modified by Ravi
		heading.setDisplayName("T/S On High Fan (mins)");
		heading.setSequence(8);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("tsOnMedFan");
		// Modified by Ravi
		heading.setDisplayName("T/S On Med Fan (mins)");
		heading.setSequence(9);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("tsOnLowFan");
		// Modified by Ravi
		heading.setDisplayName("T/S On Low Fan (mins)");
		heading.setSequence(10);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("tsOffHighFan");
		// Modified by Ravi
		heading.setDisplayName("T/S Off High Fan (mins)");
		heading.setSequence(11);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("tsOffMedFan");
		// Modified by Ravi
		heading.setDisplayName("T/S Off Med Fan (mins)");
		heading.setSequence(12);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("tsOffLowFan");
		// Modified by Ravi
		heading.setDisplayName("T/S Off Low Fan (mins)");
		heading.setSequence(13);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel1");
		heading.setDisplayName("Site Group Level 1");
		heading.setSequence(14);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel2");
		heading.setDisplayName("Site Group Level 2");
		heading.setSequence(15);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel3");
		heading.setDisplayName("Site Group Level 3");
		heading.setSequence(16);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel4");
		heading.setDisplayName("Site Group Level 4");
		heading.setSequence(17);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("siteGroupLevel5");
		heading.setDisplayName("Site Group Level 5");
		heading.setSequence(18);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("cutOffStartDateTime");
		heading.setDisplayName("Cut Off Start Time");
		heading.setSequence(19);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("cutOffEndDateTime");
		heading.setDisplayName("Cut Off End Time");
		heading.setSequence(20);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		return tableHeadings;

	}

	// Added by ravi
	@Override
	public List<?> getTimezones(List<Long> siteIDList) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();
		scalarMapping.put("id", StandardBasicTypes.LONG);
		scalarMapping.put("timezone",
				StandardBasicTypes.STRING);

		List<?> sqlResultList = sqlDAO.executeSQLSelect(
				String.format(GET_TIMEZONES, StringUtils.join(siteIDList, ',').toString()),
				scalarMapping);
		return sqlResultList;
	}

}

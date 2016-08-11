/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.cr.service.ManageCutoffRequestService;
import com.panasonic.b2bacns.bizportal.cr.vo.DistibutionGroupVo;
import com.panasonic.b2bacns.bizportal.cr.vo.RegisteredCutoffRequestDetails;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author simanchal.patra
 *
 */
@Controller
@RequestMapping(value = "/cr")
public class CutoffRequestController {

	private static final Logger logger = Logger
			.getLogger(CutoffRequestController.class);

	@Autowired
	private ManageCutoffRequestService cutoffRequestService;

	@ResponseBody
	@RequestMapping(value = "/getAllCutoffRequests.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getAllRegisteredCutoffRequests(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		String jsonResponse = BizConstants.EMPTY_STRING;

		List<RegisteredCutoffRequestDetails> resultList = new ArrayList<>();

		try {

			resultList = cutoffRequestService
					.getAllRegisteredCutoffRequests(sessionInfo.getUserId());

			if (resultList != null && resultList.size() > 0) {

				Map<String, List<RegisteredCutoffRequestDetails>> resultMap = new HashMap<>();
				resultMap.put("resultList", resultList);

				jsonResponse = CommonUtil.convertFromEntityToJsonStr(resultMap);
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (GenericFailureException ex) {

			logger.debug(
					"Some Error occurred while fetching Cutoff Requests for User ID : "
							+ sessionInfo.getLoginId(), ex);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured while fetching Cutoff Requests for User ID : "
							+ sessionInfo.getLoginId(), ex);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (Exception ex) {

			logger.error(
					"Error occurred while fetching Cutoff Requests for User ID : "
							+ sessionInfo.getLoginId(), ex);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

	@ResponseBody
	@RequestMapping(value = "/registerCutoffRequest.htm", method = { RequestMethod.POST })
	public String registerCutoffRequest(
			@RequestParam(value = "fromdate") String fromdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "siteidList") List<Long> siteIDList,
			HttpServletRequest request, HttpServletResponse response) { // {"fromdate":"01/01/2015","todate":"01/02/2015",
		// ,"siteidList":[1,2,3...]}

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		String jsonResponse = BizConstants.EMPTY_STRING;

		RegisteredCutoffRequestDetails registeredCutoffRequestDetails = null;

		try {
			if (siteIDList != null && (siteIDList.size() > 0)
					&& StringUtils.isNotBlank(fromdate)
					&& StringUtils.isNotBlank(todate)) {

				// Start of Modification by Ravi
				List<?> timezone = cutoffRequestService.getTimezones(siteIDList);
				
				logger.info("TimeZone Size for CutoffRequest is "+ timezone.size());
				
				if(timezone.size() == 1){
					registeredCutoffRequestDetails = cutoffRequestService
							.registerCutoffRequest(fromdate, todate, siteIDList,
									sessionInfo.getLoginId(),
									sessionInfo.getUserId(),
									sessionInfo.getCompanyId(), timezone);
	
					if (registeredCutoffRequestDetails != null) {
	
						Map<String, RegisteredCutoffRequestDetails> resultMap = new HashMap<>();
						resultMap.put("newRequest", registeredCutoffRequestDetails);
	
						jsonResponse = CommonUtil
								.convertFromEntityToJsonStr(resultMap);
					} else {
						jsonResponse = CommonUtil
								.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
					}
				} else {
					logger.info("Selected sites have more or less than one timezone");
					jsonResponse = CommonUtil
							.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
				}
				// end of Modification by Ravi
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.EMPTY_REQUEST);
			}

		} catch (BusinessFailureException bfe) {

			logger.debug(String.format(
					"Some Error occurred while Registering new "
							+ "Cutoff Requests for Site ID - %s,"
							+ " fromDate -[%s], toDate - [%s]", siteIDList,
					fromdate, todate), bfe);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (GenericFailureException ex) {

			logger.debug(
					String.format(
							"Some Error occurred while Registering new "
									+ "Cutoff Requests for Site ID - %s, fromDate -[%s], toDate - [%s]",
							siteIDList, fromdate, todate), ex);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {
			logger.debug(
					String.format(
							"Some Error occurred while Registering new "
									+ "Cutoff Requests for Site ID - %s, fromDate -[%s], toDate - [%s]",
							siteIDList, fromdate, todate), ex);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (Exception ex) {

			logger.debug(
					String.format(
							"Some Error occurred while Registering new "
									+ "Cutoff Requests for Site ID - %s, fromDate -[%s], toDate - [%s]",
							siteIDList, fromdate, todate), ex);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;

	}

	@ResponseBody
	@RequestMapping(value = "/downloadPowerRatioReport.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String downloadPowerRatioReport(@RequestParam Long transactionId,
			HttpServletResponse response) {

		String filePath = BizConstants.EMPTY_STRING;

		try {
			logger.info("Download Power Ratio Report - transactionId : "
					+ transactionId);
			filePath = cutoffRequestService.getPowerRatioReportPath(
					transactionId, response);

			// Added By Ravi
			if (filePath != null && StringUtils.isNotBlank(filePath)
					&& !filePath.contains(BizConstants.NO_RECORDS_FOUND)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				if(!filePath.contains(BizConstants.NO_RECORDS_FOUND)){
					filePath = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}
			}

		} catch (IOException e) {
			filePath = CommonUtil.getJSONErrorMessage("error.application");
			logger.error("Exception occured in downloadPowerRatioReport ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (BusinessFailureException e) {
			logger.error("Exception occured in downloadPowerRatioReport ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Exception occured in downloadPowerRatioReport ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return filePath;

	}

	// Added Post Support by Ravi
	@ResponseBody
	@RequestMapping(value = "/downloadPowerDetailReport.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String downloadPowerDetailReport(@RequestParam Long transactionId,
			HttpServletResponse response) {

		String filePath = BizConstants.EMPTY_STRING;

		try {
			logger.info("" + "Download Power Detail Report - transactionId : "
					+ transactionId);

			filePath = cutoffRequestService.getPowerDetailReportPath(
					transactionId, response);

			// Added By Ravi
			if (filePath != null && StringUtils.isNotBlank(filePath)
					&& !filePath.contains(BizConstants.NO_RECORDS_FOUND)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				if(!filePath.contains(BizConstants.NO_RECORDS_FOUND)){
					filePath = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}
			}

		} catch (IOException e) {
			logger.error("Exception occured in downloadPowerDetailReport ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (BusinessFailureException e) {
			logger.error("Exception occured in downloadPowerDetailReport ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Exception occured in downloadPowerDetailReport ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return filePath;

	}

	@ResponseBody
	@RequestMapping(value = "/getDistributionGroupsBySiteId.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getDistributionGroups(
			@RequestParam(value = "siteID") Long siteID,
			HttpServletRequest request, HttpServletResponse response) {

		String jsonResponse = BizConstants.EMPTY_STRING;

		List<DistibutionGroupVo> resultList = new ArrayList<>();

		try {
			if (siteID != null) {
				resultList = cutoffRequestService
						.getDistributionGroupsBySiteID(siteID);
			}

			if (resultList != null && resultList.size() > 0) {
				jsonResponse = CommonUtil
						.convertFromEntityToJsonStr(resultList);
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (GenericFailureException ex) {
			logger.debug(
					"Some Error occurred while fetching Distribution groups for Site ID : "
							+ siteID, ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured while fetching Distribution groups for Site ID : "
							+ siteID, ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error(
					"Error occurred while fetching Distribution groups for Site ID : "
							+ siteID, ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return jsonResponse;
	}

}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import com.panasonic.b2bacns.bizportal.common.Response;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogPaginationVO;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogRequest;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogVO;
import com.panasonic.b2bacns.bizportal.rc.ValidateRC;
import com.panasonic.b2bacns.bizportal.rc.service.ManageRCService;
import com.panasonic.b2bacns.bizportal.rc.service.RCOperationLogService;
import com.panasonic.b2bacns.bizportal.rcset.vo.RCSetControlRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This controller handles request for DashBoard RC data for Group and Indoor
 * Unit
 * 
 * @author shobhit.singh, akansha
 * 
 */
@Controller
@RequestMapping(value = "/rc")
public class RCController {

	/** Logger instance. **/
	private static final Logger logger = Logger.getLogger(RCController.class);

	private static final String JSON_REQUEST = "json_request";

	@Autowired
	private ManageRCService manageRCService;

	@Autowired
	private RCOperationLogService rcOperationLogService;

	/**
	 * Provides Control status display for provided indoor units.
	 * 
	 * @param iduIDs
	 * @return
	 */
	@RequestMapping(value = "/RCValidate.htm", method = { RequestMethod.GET })
	public @ResponseBody String getRCValidation(
			@RequestParam(value = "id") List<Long> iduIDs) {

		logger.debug("Received request to provide Control status display of "
				+ iduIDs);

		ValidateRC validateRC = null;
		String jsonResponse = null;

		try {

			jsonResponse = CommonUtil
					.convertFromEntityToJsonStr(new ValidateRC());

			validateRC = manageRCService.getRCValidation(iduIDs);

			if (validateRC != null) {
				jsonResponse = CommonUtil
						.convertFromEntityToJsonStr(validateRC);
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (GenericFailureException ex) {

			logger.error("Error occured in RC validation for ID " + iduIDs, ex);

			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");

		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in RC validation for ID: "
							+ iduIDs, ex);

			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");

		} catch (Exception ex) {

			logger.error("Error occured in RC validation  for ID: " + iduIDs,
					ex);

			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		}

		return jsonResponse;
	}

	/**
	 * This method sets the RC PowerStatus, FanSpeed, FlapMode, AirConMode,
	 * Temperature of indoor units and returns action status true/false in JSON
	 * 
	 * @param id
	 * @param idType
	 * @param powerStatus
	 * @param temperature
	 * @param mode
	 * @param fanSpeed
	 * @param windDirection
	 * @return
	 */
	@RequestMapping(value = "/setControlRC.htm", method = { RequestMethod.POST })
	public @ResponseBody String setControlRC(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);

		String jsonResponse = null;

		Response response = new Response();

		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		try {

			RCSetControlRequestVO rcSetControlRequestVO = (RCSetControlRequestVO) CommonUtil
					.convertFromJsonStrToEntity(RCSetControlRequestVO.class,
							json_request);

			Boolean excutionStatus = manageRCService.setControlRC(
					sessionInfo.getUserId(), rcSetControlRequestVO.getId(),
					rcSetControlRequestVO.getOperation());

			response.setSuccess(excutionStatus);

			jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);

		} catch (IOException e) {
			logger.error("Error occured in setControlRC", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in setControlRC", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

	/**
	 * 
	 * @param fromDateTime
	 * @param toDateTime
	 * @return
	 */
	@RequestMapping(value = "/getRCOperationsLog.htm", method = { RequestMethod.POST })
	public @ResponseBody String getRCOperationsLog(
			@RequestParam(value = "unitIDs") List<Long> unitIDs,
			@RequestParam(value = "fromDateTime") String fromDateTime,
			@RequestParam(value = "toDateTime") String toDateTime,
			@RequestParam(value = "pageNo") Integer pageNo,
			HttpServletRequest request) {

		String jsonResponse = null;
		RCOperationLogRequest logRequest = new RCOperationLogRequest();

		try {
			logRequest.setUnitIDs(unitIDs);
			logRequest.setFromDateTime(fromDateTime);
			logRequest.setToDateTime(toDateTime);
			logRequest.setPageNo(pageNo);

		} catch (Exception e) {
			logger.error("Error occurred while converting RC operation Log"
					+ " JSon request to Object", e);
		}
		List<RCOperationLogVO> rcOperationLogVOs = null;
		SessionInfo sessioninfo = CommonUtil.getSessionInfo(request);
		Long userId = sessioninfo.getUserId();
		try {

			if (CommonUtil.isAfterToday(sessioninfo.getUserTimeZone(),
					logRequest.getToDateTime())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			if (CommonUtil.isAfterToday(sessioninfo.getUserTimeZone(),
					logRequest.getFromDateTime())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

			Long noOfDays = CommonUtil.getDifferenceInDays(
					logRequest.getFromDateTime(), logRequest.getToDateTime());
			if (noOfDays <= 90) {

				rcOperationLogVOs = rcOperationLogService
						.getRcoperationLogsByDateTimeRange(logRequest, userId);

				if (rcOperationLogVOs != null && !rcOperationLogVOs.isEmpty()){
					//add by shanf,add new property for pagination
					Integer pageCount = rcOperationLogService
							.getRcoperationLogsPageCount(logRequest, userId);
					RCOperationLogPaginationVO rcPaginationVO = new RCOperationLogPaginationVO();
					if (pageCount != null
							&& pageCount != 0){
						rcPaginationVO.setData(rcOperationLogVOs);
						rcPaginationVO.setPageCount(pageCount);
					}
					
					jsonResponse = CommonUtil
							.convertFromEntityToJsonStr(rcPaginationVO);
					//end of add by shanf
				}
				else {
					jsonResponse = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.OUT_OF_DAY_RANGE);
			}

		} catch (GenericFailureException ex) {
			// error message for 'no records found'
			logger.error(
					"Error occured in getRCOperationsLog for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in getRCOperationsLog for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		} catch (Exception ex) {
			logger.error(
					"Exception occured in getRCOperationsLog for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		}

		return jsonResponse;
	}

	@RequestMapping(value = "/getRCOpLogPageCount.htm", method = { RequestMethod.POST })
	public @ResponseBody String getRCOpLogPageCount(
			@RequestParam(value = "unitIDs") List<Long> unitIDs,
			@RequestParam(value = "fromDateTime") String fromDateTime,
			@RequestParam(value = "toDateTime") String toDateTime,
			HttpServletRequest request) {

		String jsonResponse = null;
		RCOperationLogRequest logRequest = new RCOperationLogRequest();

		try {
			logRequest.setUnitIDs(unitIDs);
			logRequest.setFromDateTime(fromDateTime);
			logRequest.setToDateTime(toDateTime);

		} catch (Exception e) {
			logger.error(
					"Error occurred while converting RC operation Log Page Count"
							+ " JSon request to Object", e);
		}

		Integer rcOperationLogPageCount = null;
		SessionInfo sessioninfo = CommonUtil.getSessionInfo(request);
		Long userId = sessioninfo.getUserId();
		try {

			if (CommonUtil.isAfterToday(sessioninfo.getUserTimeZone(),
					logRequest.getToDateTime())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			if (CommonUtil.isAfterToday(sessioninfo.getUserTimeZone(),
					logRequest.getFromDateTime())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

			Long noOfDays = CommonUtil.getDifferenceInDays(
					logRequest.getFromDateTime(), logRequest.getToDateTime());
			if (noOfDays <= 90) {

				rcOperationLogPageCount = rcOperationLogService
						.getRcoperationLogsPageCount(logRequest, userId);

				if (rcOperationLogPageCount != null
						&& rcOperationLogPageCount != 0)
					jsonResponse = "{\"pageCount\":" + "\""
							+ rcOperationLogPageCount + "\"" + "}";
				else {
					jsonResponse = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.OUT_OF_DAY_RANGE);
			}

		} catch (GenericFailureException ex) {
			// error message for 'no records found'
			logger.error(
					"Error occured in getRCOpLogPageCount for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in getRCOpLogPageCount for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		} catch (Exception ex) {
			logger.error(
					"Exception occured in getRCOpLogPageCount for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		}

		return jsonResponse;
	}

	@RequestMapping(value = "/downloadRCOperationsLog.htm", method = { RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String downloadRCOperationsLog(
			@RequestParam(value = "unitIDs") List<Long> unitIDs,
			@RequestParam(value = "fromDateTime") String fromDateTime,
			@RequestParam(value = "toDateTime") String toDateTime,
			@RequestParam(value = "reportType") String reportType,
			HttpServletRequest request, HttpServletResponse response) {

		String jsonResponse = null;
		RCOperationLogRequest logRequest = new RCOperationLogRequest();

		String filePath = null;

		try {
			logRequest.setUnitIDs(unitIDs);
			logRequest.setFromDateTime(fromDateTime);
			logRequest.setToDateTime(toDateTime);
		} catch (Exception e) {
			logger.error("Error occurred while converting RC operation Log"
					+ " JSon request to Object", e);
		}

		SessionInfo sessioninfo = CommonUtil.getSessionInfo(request);
		try {

			if (CommonUtil.isAfterToday(sessioninfo.getUserTimeZone(),
					logRequest.getToDateTime())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			if (CommonUtil.isAfterToday(sessioninfo.getUserTimeZone(),
					logRequest.getFromDateTime())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

			Long noOfDays = CommonUtil.getDifferenceInDays(
					logRequest.getFromDateTime(), logRequest.getToDateTime());
			if (noOfDays <= 90) {

				filePath = rcOperationLogService
						.generateReportRCOperationsLogByDateTimeRange(
								logRequest, reportType, sessioninfo);

				if (filePath != null && StringUtils.isNotBlank(filePath)) {
					File file = new File(filePath);
					CommonUtil.writeDownloadableFile(response, file);
				} else {
					jsonResponse = CommonUtil
							.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}

			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.OUT_OF_DAY_RANGE);
			}

		} catch (GenericFailureException ex) {
			// error message for 'no records found'
			logger.error(
					"Error occured in getRCOperationsLog for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in getRCOperationsLog for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		} catch (Exception ex) {
			logger.error(
					"Exception occured in getRCOperationsLog for "
							+ logRequest.getFromDateTime() + "::"
							+ logRequest.getToDateTime(), ex);
			jsonResponse = CommonUtil
					.getJSONErrorMessage("some.error.occurred");
		}

		return jsonResponse;
	}

}

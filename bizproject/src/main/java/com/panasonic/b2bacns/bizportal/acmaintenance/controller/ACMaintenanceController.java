package com.panasonic.b2bacns.bizportal.acmaintenance.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.acmaintenance.service.ManageACMaintenanceService;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceRequest;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceSettingVO;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceUserVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.common.Status;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

@Controller
@RequestMapping(value = "/maintenance")
public class ACMaintenanceController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(ACMaintenanceController.class);

	@Autowired
	private ManageACMaintenanceService acMaintenanceService;

	/**
	 * Provides the AC Maintenance Setting Details for a Site.
	 * 
	 * @param request
	 * @return AC Maintenance Setting Details in JSON format.
	 */
	@RequestMapping(value = "/getMaintenanceSetting.htm", method = { RequestMethod.GET })
	@ResponseBody
	public String getMaintenanceSetting(HttpServletRequest request) {

		String json = null;
		try {
			String acMaintenance_request = request.getParameter("json_request");
			List<ACMaintenanceSettingVO> acMaintenanceSettingList = null;
			ACMaintenanceRequest acMaintenanceRequest = (ACMaintenanceRequest) CommonUtil
					.convertFromJsonStrToEntity(ACMaintenanceRequest.class,
							acMaintenance_request);
			logger.debug("Get Maintenance Setting request: "
					+ acMaintenanceRequest);

			if (acMaintenanceRequest.getSiteID() != null) {
				acMaintenanceSettingList = acMaintenanceService
						.getMaintenanceSetting(acMaintenanceRequest);
				if (acMaintenanceSettingList != null
						&& !acMaintenanceSettingList.isEmpty()) {
					json = CommonUtil
							.convertFromEntityToJsonStr(acMaintenanceSettingList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in getMaintenanceSetting", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getMaintenanceSetting", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * Update the AC Maintenance Setting Details for a Site.
	 * 
	 * @param request
	 * @return Status:true/false in JSON format.
	 */
	@RequestMapping(value = "/setMaintenanceSetting.htm", method = { RequestMethod.POST })
	@ResponseBody
	public String setMaintenanceSetting(HttpServletRequest request) {

		String json = null;
		try {
			String acMaintenance_request = request.getParameter("json_request");
			boolean status;
			ACMaintenanceRequest acMaintenanceRequest = (ACMaintenanceRequest) CommonUtil
					.convertFromJsonStrToEntity(ACMaintenanceRequest.class,
							acMaintenance_request);
			logger.debug("Set Maintenance Setting request: "
					+ acMaintenanceRequest);

			if (acMaintenanceRequest.getSiteID() != null
					&& acMaintenanceRequest.getMaintenanceTypeList() != null
					&& !acMaintenanceRequest.getMaintenanceTypeList().isEmpty()) {
				status = acMaintenanceService
						.setMaintenanceSetting(acMaintenanceRequest);
				Status response = new Status();
				response.setStatus(status);
				json = CommonUtil.convertFromEntityToJsonStr(response);

			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in setMaintenanceSetting ",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException e) {
			logger.error("Error occured in setMaintenanceSetting", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in setMaintenanceSetting", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * Provides the AC Maintenance User Details for a Maintenance Setting.
	 * 
	 * @param request
	 * @return AC Maintenance User Details in JSON format.
	 */
	@RequestMapping(value = "/getMaintenanceSettingMailList.htm", method = { RequestMethod.GET })
	@ResponseBody
	public String getMaintenanceSettingMailList(HttpServletRequest request) {

		String json = null;
		try {
			String acMaintenance_request = request.getParameter("json_request");
			List<ACMaintenanceUserVO> acMaintenanceUserList = null;
			ACMaintenanceRequest acMaintenanceRequest = (ACMaintenanceRequest) CommonUtil
					.convertFromJsonStrToEntity(ACMaintenanceRequest.class,
							acMaintenance_request);
			logger.debug("Get Maintenance User request: "
					+ acMaintenanceRequest);

			if (acMaintenanceRequest.getCompanyID() != null) {
				acMaintenanceUserList = acMaintenanceService
						.getMaintenanceSettingMailList(acMaintenanceRequest);
				if (acMaintenanceUserList != null
						&& !acMaintenanceUserList.isEmpty()) {
					json = CommonUtil
							.convertFromEntityToJsonStr(acMaintenanceUserList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in getMaintenanceSettingMailList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getMaintenanceSettingMailList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * Update the AC Maintenance User Email for a requested Setting type.
	 * 
	 * @param request
	 * @return Status:true/false in JSON format.
	 */
	@RequestMapping(value = "/setMaintenanceSettingMailList.htm", method = { RequestMethod.POST })
	@ResponseBody
	public String setMaintenanceSettingMailList(HttpServletRequest request) {

		String json = null;
		try {
			String acMaintenance_request = request.getParameter("json_request");
			boolean status;
			ACMaintenanceRequest acMaintenanceRequest = (ACMaintenanceRequest) CommonUtil
					.convertFromJsonStrToEntity(ACMaintenanceRequest.class,
							acMaintenance_request);
			logger.debug("Set Maintenance User Email Details request: "
					+ acMaintenanceRequest);

			if (acMaintenanceRequest.getCompanyID() != null
					&& ((acMaintenanceRequest.getAddUserList() != null && !acMaintenanceRequest
							.getAddUserList().isEmpty()) || (acMaintenanceRequest
							.getDeleteUserList() != null && !acMaintenanceRequest
							.getDeleteUserList().isEmpty()))) {
				status = acMaintenanceService
						.setMaintenanceSettingMailList(acMaintenanceRequest);
				Status response = new Status();
				response.setStatus(status);
				json = CommonUtil.convertFromEntityToJsonStr(response);

			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in setMaintenanceSettingMailList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in setMaintenanceSettingMailList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * Provides the AC Maintenance Status Data for a Outdoor.
	 * 
	 * @param request
	 * @return AC Maintenance Status Data in JSON format.
	 */
	@RequestMapping(value = "/getCurrentRemainingMaintenanceTime.htm", method = { RequestMethod.GET })
	@ResponseBody
	public String getCurrentRemainingMaintenanceTime(HttpServletRequest request) {

		String json = null;
		try {
			String acMaintenance_request = request.getParameter("json_request");
			List<ACMaintenanceSettingVO> acMaintenanceSettingList = null;
			ACMaintenanceRequest acMaintenanceRequest = (ACMaintenanceRequest) CommonUtil
					.convertFromJsonStrToEntity(ACMaintenanceRequest.class,
							acMaintenance_request);
			logger.debug("Get Maintenance  Status Data request: "
					+ acMaintenanceRequest);

			if (acMaintenanceRequest.getOduID() != null) {
				acMaintenanceSettingList = acMaintenanceService
						.getCurrentRemainingMaintenanceTime(acMaintenanceRequest);
				if (acMaintenanceSettingList != null
						&& !acMaintenanceSettingList.isEmpty()) {
					json = CommonUtil
							.convertFromEntityToJsonStr(acMaintenanceSettingList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in getCurrentRemainingMaintenanceTime",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getCurrentRemainingMaintenanceTime",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * Reset AC Threshold Alert for a requested Outdoor type.
	 * 
	 * @param request
	 * @return Status:true/false in JSON format.
	 */
	@RequestMapping(value = "/resetThreshodAlert.htm", method = { RequestMethod.POST })
	@ResponseBody
	public String resetThreshodAlert(HttpServletRequest request) {

		String json = null;
		try {
			String acMaintenance_request = request.getParameter("json_request");
			boolean status;
			ACMaintenanceRequest acMaintenanceRequest = (ACMaintenanceRequest) CommonUtil
					.convertFromJsonStrToEntity(ACMaintenanceRequest.class,
							acMaintenance_request);
			logger.debug("Reset AC Threshold Alert request: "
					+ acMaintenanceRequest);

			SessionInfo sessioninfo = CommonUtil.getSessionInfo(request);

			String timeZone = sessioninfo.getUserTimeZone();

			if (acMaintenanceRequest.getMaintenanceID() != null
					&& acMaintenanceRequest.getOduID() != null) {
				status = acMaintenanceService.resetThresholdAlert(
						acMaintenanceRequest, timeZone);
				Status response = new Status();
				response.setStatus(status);
				json = CommonUtil.convertFromEntityToJsonStr(response);

			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in resetThreshodAlert", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in resetThreshodAlert", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

}

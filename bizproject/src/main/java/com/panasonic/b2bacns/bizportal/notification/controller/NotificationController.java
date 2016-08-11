/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.PermissionVO;
import com.panasonic.b2bacns.bizportal.common.Response;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailList;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailsVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.notification.service.ManageNotificationService;
import com.panasonic.b2bacns.bizportal.notification.service.NotificationSettingCommonService;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewRequest;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationRequestDownloadVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Narendra.Kumar
 * 
 */
@Controller
@RequestMapping(value = "/notification")
public class NotificationController {
	private static final Logger logger = Logger
			.getLogger(NotificationController.class);

	@Autowired
	private NotificationSettingCommonService notificationSettingCommonService;

	@Autowired
	private ManageNotificationService manageNotificationService;

	private static final String JSON_REQUEST = "json_request";

	/**
	 * This method handle the view notification detail requests and takes logged
	 * in user's company id from the session and return notification detail from
	 * "notificationsettings" table.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewNotification.htm", method = { RequestMethod.GET })
	public ModelAndView viewNotification(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		try {

			if (sessionInfo.getLastSelectedGroupID() != null) {
				// add last selected Group ID from session
				modelAndView.getModelMap().put(
						BizConstants.LAST_SELECTED_GROUP_ATTRIB_NAME,
						sessionInfo.getLastSelectedGroupID().toString());
			}

			modelAndView.setViewName("/notification/notification");

		} catch (Exception e) {
			logger.error("Error: while viewing dashboard ", e);
		}

		return modelAndView;

	}
	
	
	
	/**
	 * This method handle the view notification detail requests and takes group
	 * ids and return notification detail from "notificationsettings" table.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNotificationSetting.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getNotificationSetting(HttpServletRequest request) {

		Long groupId = (!request.getParameter(BizConstants.GROUP_ID).equals(
				BizConstants.NULL_STRING) ? Long.parseLong(request
				.getParameter(BizConstants.GROUP_ID)) : null);
		String getNotificationSettingJson = BizConstants.EMPTY_STRING;
		try {
			Map<String, String> notificationCategorySettingVOs = notificationSettingCommonService
					.getNotificationSetting(groupId);
			if (notificationCategorySettingVOs != null
					&& !notificationCategorySettingVOs.isEmpty())
				getNotificationSettingJson = CommonUtil
						.convertFromEntityToJsonStr(notificationCategorySettingVOs);
			else {
				getNotificationSettingJson = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in getNotificationSetting", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in getNotificationSetting",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error("An Exception occured in getNotificationSetting", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getNotificationSettingJson;
	}

	/**
	 * This method handles the save requests of user notification setting. It
	 * takes "groupid" "notificationCategoryId", "notification" value as a json
	 * parameter and perform save and update into the "notificationsettings"
	 * table notificationsettings table will contain unique combination of
	 * groupid and notificationtype_id (notificationCategoryId).
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setNotificationSetting.htm", method = RequestMethod.POST)
	@ResponseBody
	public String setNotificationSetting(HttpServletRequest request) {

		String setNotificationSettingJsonStringParam = (request
				.getParameter(BizConstants.SET_NOTIFICATION_SETTING) != null ? request
				.getParameter(BizConstants.SET_NOTIFICATION_SETTING)
				: BizConstants.EMPTY_STRING);

		String setNotificationSettingJson = BizConstants.EMPTY_STRING;

		Response response = new Response();
		try {
			@SuppressWarnings("unchecked")
			List<NotificationCategorySettingVO> list = (List<NotificationCategorySettingVO>) CommonUtil
					.convertFromJsonStrToEntityList(
							NotificationCategorySettingVO.class,
							setNotificationSettingJsonStringParam);
			if (list != null) {
				response.setSuccess(notificationSettingCommonService
						.setNotificationSetting(list));
				setNotificationSettingJson = CommonUtil
						.convertFromEntityToJsonStr(response);
			} else {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in setNotificationSetting", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in setNotificationSetting",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error("An Exception occured in setNotificationSetting", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return setNotificationSettingJson;
	}

	/**
	 * This method handles the get request of Alarmtype . It takes alarmTypeId
	 * and return alarmcode ,maintenance_description from errorcode_master in
	 * json format
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAlarmType.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getAlarmType(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		String idType = (request.getParameter(BizConstants.REQUEST_ID_TYPE) != null ? request
				.getParameter(BizConstants.REQUEST_ID_TYPE)
				: BizConstants.EMPTY_STRING);

		if (idType != null
				&& idType.equalsIgnoreCase(BizConstants.ID_TYPE_AlARMTYPE)) {

			try {
				json = manageNotificationService.getAlarmTypes();
			} catch (GenericFailureException gfe) {
				logger.error("Error occured in getAlarmType", gfe);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (JsonProcessingException | HibernateException e) {
				logger.error("Error occured in getAlarmType", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (Exception e) {
				logger.error("Error occured in getAlarmType", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
			return json;
		}
		return json;
	}

	/**
	 * This method handles the get request of Notification Over View . It takes
	 * parameter from request and return notification over view data in json
	 * format
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNotificationOverView.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getNotificationOverView(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		try {
			String json_request = request.getParameter(JSON_REQUEST);

			NotificationOverViewRequest notificationOverViewRequest = (NotificationOverViewRequest) CommonUtil
					.convertFromJsonStrToEntity(
							NotificationOverViewRequest.class, json_request);

			logger.info("notification request for the NotificationOverView GroupIds"
					+ notificationOverViewRequest.getGroupIds()
					+ "grouplevel:"
					+ notificationOverViewRequest.getGrouplevel()
					+ " startDate:"
					+ notificationOverViewRequest.getStartDate()
					+ " endDate: "
					+ notificationOverViewRequest.getEndDate()
					+ " alaramType: "
					+ notificationOverViewRequest.getAlarmType()
					+ " period: "
					+ notificationOverViewRequest.getPeriod());

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			String timeZone = sessionInfo.getUserTimeZone();

			notificationOverViewRequest.setTimeZone(timeZone);
			json = manageNotificationService
					.getAlarmNotificationOverView(notificationOverViewRequest);

		} catch (GenericFailureException gfe) {

			logger.error("Error occured in getNotificatioOverView", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException jpExp) {

			logger.error(
					"JSON Processing Exception occured in getNotificatioOverView",
					jpExp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("An Exception occured in getNotificatioOverView", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * API to fetch Notification for the Notification Details
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNotificationDetails.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getNotificationDetails(HttpServletRequest request) {

		String json = null;
		try {
			String notification_request = request.getParameter("json_request");

			NotificationRequestVO notificationRequestVO = (NotificationRequestVO) CommonUtil
					.convertFromJsonStrToEntity(NotificationRequestVO.class,
							notification_request);
			logger.debug("Get Notification Details request: "
					+ notificationRequestVO);

			if ((notificationRequestVO.getAlarmFixedStartDate() != null && notificationRequestVO
					.getAlarmFixedEndDate() == null)
					|| (notificationRequestVO.getAlarmFixedStartDate() == null && notificationRequestVO
							.getAlarmFixedEndDate() != null)
					|| (notificationRequestVO.getAlarmOccurredStartDate() != null && notificationRequestVO
							.getAlarmOccurredEndDate() == null)
					|| (notificationRequestVO.getAlarmOccurredStartDate() == null && notificationRequestVO
							.getAlarmOccurredEndDate() != null)
					|| !(notificationRequestVO.getId() != null
							&& !notificationRequestVO.getId().isEmpty()
							&& notificationRequestVO.getIdType() != null
							&& !notificationRequestVO.getIdType().isEmpty()
							&& notificationRequestVO.getAddCustName() != null && !notificationRequestVO
							.getAddCustName().isEmpty())) {
				return CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
			List<PermissionVO> permissionList = sessionInfo
					.getPermissionsList();
			if (permissionList != null && !permissionList.isEmpty()) {
				for (PermissionVO permission : permissionList) {
					if (permission.getPermissionName().equalsIgnoreCase(
							BizConstants.NOTIFICATION_MESSAGE_MAINTENANCE)) {
						notificationRequestVO
								.setDiscription(BizConstants.MAINTENANCE);
						break;
					} else {
						notificationRequestVO
								.setDiscription(BizConstants.CUSTOMER);
					}
				}
			} else {
				notificationRequestVO.setDiscription(BizConstants.CUSTOMER);
			}

			NotificationDetailList notificationList = manageNotificationService
					.getNotificationDetails(notificationRequestVO,
							sessionInfo.getUserTimeZone());

			if (notificationList != null
					&& notificationList.getNotificationList() != null
					&& !notificationList.getNotificationList().isEmpty()) {
				json = CommonUtil.convertFromEntityToJsonStr(notificationList);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in getNotificationDetails ",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Exception occured in getNotificationDetails ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}

	/**
	 * Ajax Request to get alarm counts for the groups & indoor/ Outdoor units.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNotificationCount.htm", method = { RequestMethod.GET })
	@ResponseBody
	public String getNotificationCount(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		String json_response = BizConstants.EMPTY_STRING;

		try {
			json_response = manageNotificationService
					.getNotificationCount(sessionInfo.getUserId());
		} catch (JsonProcessingException e) {
			logger.error("Exception occured in getNotificationCount ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Exception occured in getNotificationCount ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json_response;

	}

	/**
	 * Download Notification Details in excel and csv format
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadNotificationDetails.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String downloadNotificationDetails(HttpServletRequest request,
			HttpServletResponse response) {

		String json = BizConstants.EMPTY_STRING;
		try {
			String jsonRequest = request.getParameter("json_request");

			logger.debug("Request for downloadNotificationDetails --->"
					+ jsonRequest);

			// converting json received from request to a vo
			NotificationRequestDownloadVO requestVO = (NotificationRequestDownloadVO) CommonUtil
					.convertFromJsonStrToEntity(
							NotificationRequestDownloadVO.class, jsonRequest);

			if (!StringUtils.equalsIgnoreCase(requestVO.getFileType(),
					BizConstants.REPORT_TYPE_CSV)
					&& !StringUtils.equalsIgnoreCase(requestVO.getFileType(),
							BizConstants.REPORT_TYPE_EXCEL)) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
				throw new GenericFailureException(customErrorMessage);

			}

			// creating list of Notification Details
			List<NotificationDetailsVO> notificationList = manageNotificationService
					.getNotificationDetailsDownloadData(requestVO
							.getNotificationIds());

			logger.debug("notificationList-->" + notificationList);

			// check if data exist in list
			if (notificationList != null && !notificationList.isEmpty()) {

				String filePath = null;

				if (StringUtils.equalsIgnoreCase(requestVO.getFileType(),
						BizConstants.REPORT_TYPE_EXCEL)) {

					// generate excel file for given list at a specific position
					filePath = manageNotificationService
							.generateNotificationDetailsExcelReport(
									notificationList, requestVO);

				} else {

					// generate csv file for given list at a specific position
					filePath = manageNotificationService
							.generateNotificationDetailsCsvReport(
									notificationList, requestVO);

				}

				logger.debug("NotificationDetailsReport is saved at --->"
						+ filePath);

				// check if file is successfully generated
				if (filePath != null && StringUtils.isNotBlank(filePath)) {
					File file = new File(filePath);
					CommonUtil.writeDownloadableFile(response, file);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in downloadNotificationDetails ",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {

			logger.error("Error occured in downloadNotificationDetails", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Exception occured in downloadNotificationDetails ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * Download Notification Overview in excel format
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadNotificationOverView.htm", method = { RequestMethod.GET })
	public String downloadNotificationOverView(HttpServletRequest request,
			HttpServletResponse response) {

		String json = BizConstants.NO_RECORDS_FOUND;

		try {
			String json_request = request.getParameter(JSON_REQUEST);

			NotificationOverViewRequest notificationOverViewRequest = (NotificationOverViewRequest) CommonUtil
					.convertFromJsonStrToEntity(
							NotificationOverViewRequest.class, json_request);

			logger.info("notification request for the NotificationOverView GroupIds"
					+ notificationOverViewRequest.getGroupIds()
					+ "grouplevel:"
					+ notificationOverViewRequest.getGrouplevel()
					+ " startDate:"
					+ notificationOverViewRequest.getStartDate()
					+ " endDate: "
					+ notificationOverViewRequest.getEndDate()
					+ " alaramType: "
					+ notificationOverViewRequest.getAlarmType()
					+ " period: "
					+ notificationOverViewRequest.getPeriod());

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			String timeZone = sessionInfo.getUserTimeZone();

			notificationOverViewRequest.setTimeZone(timeZone);

			List<NotificationOverViewVO> notificationOverViewlist = manageNotificationService
					.getAlarmNotificationOverViewForDownload(notificationOverViewRequest);

			// generate excel file for given list at a specific position
			String filePath = manageNotificationService
					.generateNotificationOverViewExcelReport(
							notificationOverViewlist,
							notificationOverViewRequest);

			logger.debug("Notification OverView ExcelReport saved at --->"
					+ filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (GenericFailureException gfe) {
			logger.error("Error occured in getNotificatioOverView", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException jpExp) {
			logger.error(
					"JSON Processing Exception occured in getNotificatioOverView",
					jpExp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("An Exception occured in getNotificatioOverView", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json;
	}
}

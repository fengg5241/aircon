/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.Response;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.notification.service.NotificationSettingCommonService;
import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingInJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingOutJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UpdateAdvanceNotificationSettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UserListVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Narendra.Kumar
 * 
 */
@Controller
@RequestMapping(value = "/notification")
public class UserNotificationController {
	private static final Logger logger = Logger
			.getLogger(UserNotificationController.class);
	private static final String JSON_REQUEST = "json_request";

	@Autowired
	private NotificationSettingCommonService notificationSettingCommonService;

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateAdvanceNotificationSetting.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updateAdvanceNotificationSetting(HttpServletRequest request) {

		String updateAdvanceNotificationSettingJsonStringParam = (!request
				.getParameter(BizConstants.UPDATE_ADVANCE_NOTIFICATION_SETTING)
				.equals(BizConstants.NULL_STRING) ? request
				.getParameter(BizConstants.UPDATE_ADVANCE_NOTIFICATION_SETTING)
				: BizConstants.EMPTY_STRING);

		String updateAdvanceNotificationSettingJsonString = BizConstants.EMPTY_STRING;

		Response response = new Response();
		try {
			UpdateAdvanceNotificationSettingVO updateAdvanceNotificationSettingVO = (UpdateAdvanceNotificationSettingVO) CommonUtil
					.convertFromJsonStrToEntity(
							UpdateAdvanceNotificationSettingVO.class,
							updateAdvanceNotificationSettingJsonStringParam);
			response.setSuccess(notificationSettingCommonService
					.updateAdvanceNotificationSetting(updateAdvanceNotificationSettingVO));
			updateAdvanceNotificationSettingJsonString = CommonUtil
					.convertFromEntityToJsonStr(response);

		} catch (GenericFailureException gfe) {

			logger.error(
					"Error occured in updateAdvanceNotificationSettingJsonString",
					gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {

			logger.error(
					"JSON Processing Exception occured in updateAdvanceNotificationSettingJsonString",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {

			logger.error(
					"An Exception occured in updateAdvanceNotificationSettingJsonString",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return updateAdvanceNotificationSettingJsonString;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAdvanceNotificationSetting.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getAdvanceNotificationSetting(HttpServletRequest request) {
		String getAdvanceNotificationSettingJsonStringParam = (!request
				.getParameter(BizConstants.GET_ADVANCE_NOTIFICATION_SETTING)
				.equals(BizConstants.NULL_STRING) ? request
				.getParameter(BizConstants.GET_ADVANCE_NOTIFICATION_SETTING)
				: BizConstants.EMPTY_STRING);

		String getAdvanceNotificationSettingJson = BizConstants.NO_RECORDS_FOUND;
		try {
			AdvanceNotificationSettingInJsonVO userNotificationSettingVO = (AdvanceNotificationSettingInJsonVO) CommonUtil
					.convertFromJsonStrToEntity(
							AdvanceNotificationSettingInJsonVO.class,
							getAdvanceNotificationSettingJsonStringParam);

			List<AdvanceNotificationSettingOutJsonVO> notificationCategorySettingVOs = notificationSettingCommonService
					.getAdvanceNotificationSetting(userNotificationSettingVO);
			if (notificationCategorySettingVOs != null
					&& !notificationCategorySettingVOs.isEmpty())

				getAdvanceNotificationSettingJson = CommonUtil
						.convertFromEntityToJsonStr(notificationCategorySettingVOs);
			else {
				getAdvanceNotificationSettingJson = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {

			logger.error("Error occured in getAdvanceNotificationSetting", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {

			logger.error(
					"JSON Processing Exception occured in getAdvanceNotificationSetting",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {

			logger.error(
					"An Exception occured in getAdvanceNotificationSetting", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getAdvanceNotificationSettingJson;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserList.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUserList(HttpServletRequest request) {
		String json_request = request.getParameter(JSON_REQUEST);
		String getUserListJson = BizConstants.NO_RECORDS_FOUND;
		Long companyId = null;
		try {
			if (StringUtils.isNotBlank(json_request)) {
				JSONObject jsonObject = new JSONObject(json_request);
				if (StringUtils.isNotBlank(jsonObject.getString("companyId")))
					companyId = Long.parseLong(jsonObject
							.getString("companyId"));
				else
					return getUserListJson;
			} else {
				return getUserListJson;
			}

			List<UserListVO> userListVOsList = notificationSettingCommonService
					.getUserListByCompanyId(companyId);
			if (userListVOsList != null && !userListVOsList.isEmpty())

				getUserListJson = CommonUtil
						.convertFromEntityToJsonStr(userListVOsList);
			else {
				getUserListJson = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			}
		} catch (JSONException e) {

			logger.error("Error occured in getUserListJson", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (GenericFailureException gfe) {

			logger.error("Error occured in getUserListJson", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {

			logger.error(
					"JSON Processing Exception occured in getUserListJson", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {

			logger.error("An Exception occured in getUserListJson", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getUserListJson;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateMasterNotificationSetting.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updateMasterNotificationSetting(HttpServletRequest request) {

		String updateMasterNotificationSettingJsonStringParam = (!request
				.getParameter(BizConstants.UPDATE_ADVANCE_NOTIFICATION_SETTING)
				.equals(BizConstants.NULL_STRING) ? request
				.getParameter(BizConstants.UPDATE_ADVANCE_NOTIFICATION_SETTING)
				: BizConstants.EMPTY_STRING);

		String updateMasterNotificationSettingJsonString = BizConstants.EMPTY_STRING;

		Response response = new Response();
		try {
			UpdateAdvanceNotificationSettingVO updateAdvanceNotificationSettingVO = (UpdateAdvanceNotificationSettingVO) CommonUtil
					.convertFromJsonStrToEntity(
							UpdateAdvanceNotificationSettingVO.class,
							updateMasterNotificationSettingJsonStringParam);
			response.setSuccess(notificationSettingCommonService
					.updateMasterNotificationSetting(updateAdvanceNotificationSettingVO));
			updateMasterNotificationSettingJsonString = CommonUtil
					.convertFromEntityToJsonStr(response);

		} catch (GenericFailureException gfe) {

			logger.error("Error occured in updateMasterNotificationSetting",
					gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {

			logger.error(
					"JSON Processing Exception occured in updateMasterNotificationSetting",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {

			logger.error(
					"An Exception occured in updateMasterNotificationSetting",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return updateMasterNotificationSettingJsonString;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMasterNotificationSetting.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getMasterNotificationSetting(HttpServletRequest request) {
		String getMasterNotificationSettingJsonStringParam = (!request
				.getParameter(BizConstants.GET_MASTER_NOTIFICATION_SETTING)
				.equals(BizConstants.NULL_STRING) ? request
				.getParameter(BizConstants.GET_MASTER_NOTIFICATION_SETTING)
				: BizConstants.EMPTY_STRING);

		String getMasterNotificationSettingJson = BizConstants.NO_RECORDS_FOUND;
		try {
			AdvanceNotificationSettingInJsonVO userNotificationSettingVO = (AdvanceNotificationSettingInJsonVO) CommonUtil
					.convertFromJsonStrToEntity(
							AdvanceNotificationSettingInJsonVO.class,
							getMasterNotificationSettingJsonStringParam);

			List<AdvanceNotificationSettingOutJsonVO> notificationCategorySettingVOs = notificationSettingCommonService
					.getMasterNotificationSetting(userNotificationSettingVO);
			if (notificationCategorySettingVOs != null
					&& !notificationCategorySettingVOs.isEmpty())

				getMasterNotificationSettingJson = CommonUtil
						.convertFromEntityToJsonStr(notificationCategorySettingVOs);
			else {
				getMasterNotificationSettingJson = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {

			logger.error("Error occured in getMasterNotificationSetting", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {

			logger.error(
					"JSON Processing Exception occured in getMasterNotificationSetting",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {

			logger.error(
					"An Exception occured in getMasterNotificationSetting", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getMasterNotificationSettingJson;
	}
}

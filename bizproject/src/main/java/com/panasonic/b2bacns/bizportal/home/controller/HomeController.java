package com.panasonic.b2bacns.bizportal.home.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.home.vo.HomeCompanySiteVO;
import com.panasonic.b2bacns.bizportal.home.vo.LocationVO;
import com.panasonic.b2bacns.bizportal.home.vo.UserSelectionVO;
import com.panasonic.b2bacns.bizportal.home.vo.Weather;
import com.panasonic.b2bacns.bizportal.service.HomeService;
import com.panasonic.b2bacns.bizportal.service.UserService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This controller is responsible to work with home screen
 * 
 * @author shobhit.singh
 * 
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {

	/** Logger instance. **/
	private static final Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private UserService userService;
	
	//Added By Shan
	@Autowired
	private HomeService homeService;

	/**
	 * This method returns home screen.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/homeScreen.htm")
	public ModelAndView homeScreen() {
		ModelAndView modelAndView = new ModelAndView("/dashboard/dashboard");
		return modelAndView;
	}

	/**
	 * Provides last selected groups
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLastVisitedGroups.htm", method = { RequestMethod.POST })
	@ResponseBody
	public String getLastGroupSelection(HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("Received request to provide last selected groups");

		List<Long> entityIDs = new ArrayList<>();

		try {
			HttpSession session = request.getSession(false);

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

			if (sessionInfo != null) {

				List<UserSelectionVO> userSelectionVOList = sessionInfo
						.getUserSelectionsList();

				if (userSelectionVOList == null) {

					String lastVisitedGroupsJsonStr = userService
							.getLastVisitedGroups(sessionInfo.getUserId());

					userSelectionVOList = (List<UserSelectionVO>) CommonUtil
							.convertFromJsonStrToEntityList(
									UserSelectionVO.class,
									lastVisitedGroupsJsonStr);

					sessionInfo.setUserSelectionsList(userSelectionVOList);

					CommonUtil.setSessionInfo(session, sessionInfo);
				}
				
				if (userSelectionVOList != null) {
					for (UserSelectionVO userSelectionVO : userSelectionVOList) {
						entityIDs.add(userSelectionVO.getEntityId());
					}
				}
			}

		} catch (JsonProcessingException ex) {
			logger.error(
					"Json Processing Exception occured while providing last selected groups",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error(
					"Exception occur while saving last selected groups of IDs ",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return entityIDs.toString();
	}

	/**
	 * Save last selected groups by user
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveLastVisitedGroups.htm", method = { RequestMethod.POST })
	public void saveLastGroupSelection(
			@RequestParam(value = "id") List<Long> entityIDs,
			HttpServletRequest request, HttpServletResponse response) {

		logger.debug("Received request to save last selected groups of IDs"
				+ entityIDs);

		String lastVisitedGroupsJsonStr = null;

		try {

			List<UserSelectionVO> userSelectionVOList = new ArrayList<>();

			for (Long entityID : entityIDs) {
				UserSelectionVO userSelectionVO = new UserSelectionVO();
				userSelectionVO.setEntityId(entityID);
				// userSelectionVO.setExpanded(false);
				// userSelectionVO.setSelected(true);
				userSelectionVOList.add(userSelectionVO);
			}

			HttpSession session = request.getSession(false);

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

			sessionInfo.setUserSelectionsList(userSelectionVOList);
			
			CommonUtil.setSessionInfo(session, sessionInfo);

			lastVisitedGroupsJsonStr = CommonUtil
					.convertFromEntityToJsonStr(userSelectionVOList);

			userService.saveLastVisitedGroups(sessionInfo.getUserId(),
					lastVisitedGroupsJsonStr);

		} catch (Exception ex) {
			logger.error(
					"Exception occur while saving last selected groups of IDs "
							+ entityIDs, ex);
		}
	}

	@RequestMapping(value = "/saveUserTimeZone.htm", method = { RequestMethod.GET })
	public void saveUserTimeZone(String userTimeZone, HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		// TODO use to validate with TimeZoneMaster from DB
		sessionInfo.setUserTimeZone(userTimeZone);
	}

	@RequestMapping(value = "/getUserTimeZone.htm", method = { RequestMethod.GET })
	@ResponseBody
	public final String getUserTimeZone(String userTimeZone,
			HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		return sessionInfo.getUserTimeZone();

	}
	
	//add by shanf
	@RequestMapping(value = "/getForecastInfo.htm", method = {RequestMethod.POST })
	@ResponseBody
	public final String getForecastInfo(HttpServletRequest request,
			@RequestParam(value = "locationId") long locationId,
			@RequestParam(value = "currentTime") String currentTime) {
		String jsonStr = BizConstants.EMPTY_STRING;
		List<Weather> weatherList = homeService.getForecastInfo(locationId,currentTime);
		try {
			jsonStr = CommonUtil
					.convertFromEntityToJsonStr(weatherList);
		} catch (JsonProcessingException e) {
		}
		return jsonStr;
	}
	
	//add by shanf
	@RequestMapping(value = "/getLocationList.htm", method = { RequestMethod.GET })
	@ResponseBody
	public final String getForecastInfo(HttpServletRequest request) {
		String jsonStr = BizConstants.EMPTY_STRING;
		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		
		List<LocationVO> locationList= homeService.getLocationList(sessionInfo.getCompanyId());
		try {
			jsonStr = CommonUtil
					.convertFromEntityToJsonStr(locationList);
		} catch (JsonProcessingException e) {
		}
		return jsonStr;
	}
	
	//add by shanf
	@RequestMapping(value = "/getHomeSiteByCompanyId.htm", method = {RequestMethod.POST })
	@ResponseBody
	public final String getSitesByCompanyId(HttpServletRequest request) {
		String jsonStr = BizConstants.EMPTY_STRING;
		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		
		HomeCompanySiteVO homeSite= homeService.getHomeSiteByCompanyId(sessionInfo.getCompanyId());
		try {
			jsonStr = CommonUtil
					.convertFromEntityToJsonStr(homeSite);
		} catch (JsonProcessingException e) {
		}
		return jsonStr;
	}
}

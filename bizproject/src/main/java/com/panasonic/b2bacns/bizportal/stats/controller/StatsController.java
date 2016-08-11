/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.stats.service.StatsManagementService;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This controller handles request for charts data
 * 
 * @author akansha
 * @author ravi
 */
@Controller
@RequestMapping(value = "/stats")
public class StatsController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(StatsController.class);

	@Autowired
	private StatsManagementService statsManagementService;

	private static final String JSON_REQUEST = "json_request";

	/**
	 * This method is used to display Visualization page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewVisualization.htm", method = RequestMethod.GET)
	public ModelAndView getVisualization(HttpServletRequest request,
			ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();

		try {
			modelAndView.setViewName(BizConstants.STATS_VIEW);
		} catch (Exception e) {
			logger.error("Error: while viewing stats ", e);
		}

		return modelAndView;
	}

	/**
	 * This method is used to handle call to statisticsByGroup API for different
	 * parameters and time periods
	 * 
	 * Statistics - Power Consumption, Differential Temperature, Capacity,
	 * Efficiency, Working Hours
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/statisticsByGroup.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String statisticsByGroup(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);

			logger.info("statistic for the chart " + requestVO.getParameter()
					+ ":" + requestVO.getPeriod() + " of "
					+ requestVO.getIdType() + " ID: " + requestVO.getId());

			requestVO.setApiCallFor(BizConstants.STATS_API_CALL_BY_GROUP);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			json_response = statsManagementService.getStatsDetails(requestVO);

		} catch (IOException | ParseException | JSONException e) {
			logger.error("Error occured in statisticsByGroup", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in statisticsByGroup", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in statisticsByGroup", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

	/**
	 * This method is used to handle call to statisticsByAircon API for
	 * different parameters and time periods
	 * 
	 * Statistics - Power Consumption, Differential Temperature, Capacity,
	 * Efficiency, Working Hours
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/statisticsByAircon.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String statisticsByAircon(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);

			logger.info("statistic for the chart " + requestVO.getParameter()
					+ ":" + requestVO.getPeriod() + " of "
					+ requestVO.getIdType() + " ID: " + requestVO.getId());

			requestVO.setApiCallFor(BizConstants.STATS_API_CALL_BY_AIRCON);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			json_response = statsManagementService.getStatsDetails(requestVO);

		} catch (IOException | ParseException | JSONException e) {
			logger.error("Error occured in statisticsByAircon", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in statisticsByAircon", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in statisticsByAircon", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

	/**
	 * To get data for energy consumption graph
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/energyConsumption.htm", method = RequestMethod.GET)
	@ResponseBody
	public String eneryConsumptionGraph(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		if (sessionInfo != null) {

			try {
				StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
						.convertFromJsonStrToEntity(StatsRequestVO.class,
								json_request);

				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put(BizConstants.KEY_USER_ID,
						sessionInfo.getUserId());
				requestVO.setPeriodStrategyMap(parameterMap);

				requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

				json_response = statsManagementService
						.getDataForEnergyConsumptionGraph(requestVO);
			} catch (IOException e) {
				logger.error("Error occured in energyConsumption", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (GenericFailureException gfe) {
				logger.error("Error occured in energyConsumption", gfe);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (Exception e2) {
				logger.error("Error occured in energyConsumption", e2);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
		}

		return json_response;
	}

	/**
	 * This method is used to handle call to statisticsByRefrigerantCircuit API
	 * for different parameters and time periods
	 * 
	 * Statistics - Power Consumption, Differential Temperature, Capacity,
	 * Efficiency, Working Hours
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/statisticsByRefrigerantCircuit.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String statisticsByRefrigerantCircuit(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);

			logger.info("statistic for the chart " + requestVO.getParameter()
					+ ":" + requestVO.getPeriod() + " of "
					+ requestVO.getIdType() + " ID: " + requestVO.getId());

			requestVO
					.setApiCallFor(BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			json_response = statsManagementService.getStatsDetails(requestVO);

		} catch (IOException | ParseException | JSONException e) {
			logger.error("Error occured in statisticsByRefrigerantCircuit", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in statisticsByRefrigerantCircuit", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in statisticsByRefrigerantCircuit", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

	/**
	 * This method is used to get RefrigerantCircuits By GroupIds
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRefrigerantCircuitsByGroupIds.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getRefrigerantCircuitsByGroupIds(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		if (sessionInfo != null) {

			try {

				StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
						.convertFromJsonStrToEntity(StatsRequestVO.class,
								json_request);

				json_response = statsManagementService
						.getRefrigerantCircuitsByGroupIds(requestVO);

			} catch (IOException e) {
				logger.error(
						"Error occured in getRefrigerantCircuitsByGroupIds", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (GenericFailureException gfe) {
				logger.error(
						"Error occured in getRefrigerantCircuitsByGroupIds",
						gfe);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (Exception e) {
				logger.error(
						"Error occured in getRefrigerantCircuitsByGroupIds", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
		}
		return json_response;

	}

	/**
	 * This method is used to handle call to downloadStatisticsByGroup API for
	 * different parameters and time periods
	 * 
	 * Statistics - Power Consumption, Differential Temperature, Capacity,
	 * Efficiency, Working Hours
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downloadStatisticsByGroup.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String downloadStatisticsByGroup(HttpServletRequest request,
			HttpServletResponse response) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);

			logger.info("download statistic for the chart "
					+ requestVO.getParameter() + ":" + requestVO.getPeriod()
					+ " of " + requestVO.getIdType() + " ID: "
					+ requestVO.getId());

			requestVO.setApiCallFor(BizConstants.STATS_API_CALL_BY_GROUP);

			requestVO.setApiType(BizConstants.STATS_API_CALL_FOR_DOWNLOAD);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			List<StatsReportVO> statsList = statsManagementService
					.getStatsReportDetails(requestVO);

			if (statsList != null) {

				logger.debug("statsList-->" + statsList.size());

				String filePath = statsManagementService.getStatsFilePath(
						statsList, requestVO);

				logger.debug("StatsReport is saved at --->" + filePath);

				// check if file is successfully generated
				if (filePath != null && StringUtils.isNotBlank(filePath)) {
					File file = new File(filePath);
					CommonUtil.writeDownloadableFile(response, file);
				} else {
					json_response = CommonUtil
							.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}

			} else {

				throw new Exception(BizConstants.NO_RECORDS_FOUND);

			}

			return json_response;

		} catch (IOException | URISyntaxException | IllegalAccessException
				| InvocationTargetException | HibernateException
				| ParseException e) {
			logger.error("Error occured in downloadStatisticsByGroup", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in downloadStatisticsByGroup", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in downloadStatisticsByGroup : ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

	}

	/**
	 * This method is used to handle call to downloadStatisticsByAircon API for
	 * different parameters and time periods
	 * 
	 * Statistics - Power Consumption, Differential Temperature, Capacity,
	 * Efficiency, Working Hours
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downloadStatisticsByAircon.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String downloadStatisticsByAircon(HttpServletRequest request,
			HttpServletResponse response) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);

			logger.info("download statistic for the chart "
					+ requestVO.getParameter() + ":" + requestVO.getPeriod()
					+ " of " + requestVO.getIdType() + " ID: "
					+ requestVO.getId());

			requestVO.setApiCallFor(BizConstants.STATS_API_CALL_BY_AIRCON);

			requestVO.setApiType(BizConstants.STATS_API_CALL_FOR_DOWNLOAD);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			List<StatsReportVO> statsList = statsManagementService
					.getStatsReportDetails(requestVO);

			// check if data exist in list
			if (statsList != null && !statsList.isEmpty()) {

				logger.debug("statsList-->" + statsList.size());

				String filePath = statsManagementService.getStatsFilePath(
						statsList, requestVO);

				logger.debug("StatsReport is saved at --->" + filePath);

				// check if file is successfully generated
				if (filePath != null && StringUtils.isNotBlank(filePath)) {
					File file = new File(filePath);
					CommonUtil.writeDownloadableFile(response, file);

				} else {
					json_response = CommonUtil
							.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}
			} else {

				if (statsList == null) {

					throw new Exception(BizConstants.NO_RECORDS_FOUND);

				}

				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			}

		} catch (GenericFailureException gfe) {
			logger.error("Error occured in downloadStatisticsByAircon", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (URISyntaxException | IllegalAccessException
				| InvocationTargetException | HibernateException
				| ParseException e) {
			logger.error("Error occured in downloadStatisticsByAircon", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in downloadStatisticsByAircon : ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

	/**
	 * /** This method is used to handle call to
	 * downloadStatisticsByRefrigerantCircuit API for different parameters and
	 * time periods
	 * 
	 * Statistics - Power Consumption, Differential Temperature, Capacity,
	 * Efficiency, Working Hours
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadStatisticsByRefrigerantCircuit.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String downloadStatisticsByRefrigerantCircuit(
			HttpServletRequest request, HttpServletResponse response) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);

			logger.info("download statistic for the chart "
					+ requestVO.getParameter() + ":" + requestVO.getPeriod()
					+ " of " + requestVO.getIdType() + " ID: "
					+ requestVO.getId());

			requestVO
					.setApiCallFor(BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT);

			requestVO.setApiType(BizConstants.STATS_API_CALL_FOR_DOWNLOAD);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			List<StatsReportVO> statsList = statsManagementService
					.getStatsReportDetails(requestVO);

			// check if data exist in list
			if (statsList != null && !statsList.isEmpty()) {

				logger.debug("statsList-->" + statsList.size());

				String filePath = statsManagementService.getStatsFilePath(
						statsList, requestVO);

				logger.debug("StatsReport is saved at --->" + filePath);

				// check if file is successfully generated
				if (filePath != null && StringUtils.isNotBlank(filePath)) {
					File file = new File(filePath);
					CommonUtil.writeDownloadableFile(response, file);
				} else {
					json_response = CommonUtil
							.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
				}
			} else {

				if (statsList == null) {

					throw new Exception(BizConstants.NO_RECORDS_FOUND);

				}

				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			}

		} catch (IOException | URISyntaxException | IllegalAccessException
				| InvocationTargetException | HibernateException
				| ParseException e) {
			logger.error(
					"Error occured in downloadStatisticsByRefrigerantCircuit",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error(
					"Error occured in downloadStatisticsByRefrigerantCircuit",
					gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error(
					"Error occured in downloadStatisticsByRefrigerantCircuit",
					e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

}

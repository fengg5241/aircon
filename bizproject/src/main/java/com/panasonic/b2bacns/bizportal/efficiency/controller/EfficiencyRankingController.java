/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.efficiency.service.EfficiencyRankingService;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This controller handles request for efficiency ranking data
 * 
 * @author Narendra.Kumar
 * 
 */
@Controller
@RequestMapping(value = "/stats")
public class EfficiencyRankingController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(EfficiencyRankingController.class);

	@Autowired
	private EfficiencyRankingService efficiencyRankingService;

	private static final String JSON_REQUEST = "json_request";

	@RequestMapping(value = "/getEfficiencyonDashboard.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getEfficiencyonDashboard(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
		String jsonResponse = BizConstants.EMPTY_STRING;

		try {

			jsonResponse = efficiencyRankingService.getEfficiencyOnDashboard(
					sessionInfo.getUserId(), sessionInfo.getUserTimeZone());
			if (jsonResponse == null || StringUtils.isBlank(jsonResponse)) {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in efficiencyRanking", gfe);
			jsonResponse = CommonUtil.getJSONErrorMessage(gfe
					.getCustomErrorMsg());

		} catch (Exception ex) {
			logger.error("An Exception occured in efficiencyRanking", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return jsonResponse;
	}

	/**
	 * This method is used to handle call to efficiencyRanking API for different
	 * parameters and time periods
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/efficiencyRanking.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String efficiencyRanking(HttpServletRequest request) {

		String json_request = request.getParameter(JSON_REQUEST);
		String json_response = BizConstants.EMPTY_STRING;
		try {

			StatsRequestVO requestVO = (StatsRequestVO) CommonUtil
					.convertFromJsonStrToEntity(StatsRequestVO.class,
							json_request);
			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
			logger.info("efficiencyRanking for  " + requestVO.getParameter()
					+ ":" + requestVO.getPeriod() + " of "
					+ requestVO.getIdType() + " ID: " + requestVO.getId());

			requestVO.setApiCallFor(BizConstants.STATS_API_CALL_BY_GROUP);
			requestVO.setChartType(BizConstants.API_CHART_EFFICIENCY_RANKING);
			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());
			json_response = efficiencyRankingService
					.getEfficiencyRanking(requestVO);

			if (json_response == null || StringUtils.isBlank(json_response)) {
				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {

			logger.error("Error occured in efficiencyRanking", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {

			logger.error(
					"JSON Processing Exception occured in efficiencyRanking",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {

			logger.error("An Exception occured in efficiencyRanking", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json_response;
	}

	/**
	 * This method is used to handle call to download EfficiencyRanking API for
	 * different parameters and time periods
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadEfficiencyRanking.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String downloadEfficiencyRanking(HttpServletRequest request,
			HttpServletResponse response) {

		String json_request = request.getParameter(JSON_REQUEST) == null ? BizConstants.EMPTY_STRING
				: request.getParameter(JSON_REQUEST);
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

			requestVO.setChartType(BizConstants.API_CHART_EFFICIENCY_RANKING);

			requestVO.setUserTimeZone(sessionInfo.getUserTimeZone());

			String filePath = efficiencyRankingService.getFilePath(requestVO);

			logger.debug("Efficiency Ranking is saved at --->" + filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (IllegalAccessException | InvocationTargetException
				| IOException | URISyntaxException | HibernateException
				| ParseException e) {
			logger.error("Error occured in downloadEfficiencyRanking", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in downloadEfficiencyRanking", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in downloadEfficiencyRanking", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

}

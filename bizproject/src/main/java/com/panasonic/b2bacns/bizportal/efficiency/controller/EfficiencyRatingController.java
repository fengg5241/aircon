/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.efficiency.service.EfficiencyRatingService;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRatingRequestVO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRatingResponseVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This controller handles request for Efficiency Rating data
 * 
 * @author Narendra.Kumar
 * 
 */
@Controller
@RequestMapping(value = "/dashboard")
public class EfficiencyRatingController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(EfficiencyRatingController.class);

	@Autowired
	private EfficiencyRatingService efficiencyRatingService;

	/**
	 * This method is used to handle call to EfficiencyRating API for different
	 * parameters and time periods
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getEfficiencyRating.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getEfficiencyRating(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
		String jsonResponse = BizConstants.EMPTY_STRING;

		try {

			EfficiencyRatingRequestVO efficiencyRatingRequestVO = new EfficiencyRatingRequestVO();
			efficiencyRatingRequestVO.setUserTimeZone(sessionInfo
					.getUserTimeZone());
			efficiencyRatingRequestVO.setUserId(sessionInfo.getUserId());
			List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs = efficiencyRatingService
					.getEfficiencyRatingByGroupsAndLevel(efficiencyRatingRequestVO);
			if (efficiencyRatingResponseVOs != null
					&& !efficiencyRatingResponseVOs.isEmpty())

				jsonResponse = CommonUtil
						.convertFromEntityToJsonStr(efficiencyRatingResponseVOs);
			else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in getEfficiencyRating", gfe);
			return CommonUtil
					.getJSONErrorMessage(gfe.getCustomErrorMsg());
		} catch (JsonProcessingException ex) {
			logger.error(
					"JSON Processing Exception occured in getEfficiencyRating",
					ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error("An Exception occured in getEfficiencyRating", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

}

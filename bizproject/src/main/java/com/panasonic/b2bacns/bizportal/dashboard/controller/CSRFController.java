package com.panasonic.b2bacns.bizportal.dashboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This class handles Notification and Ranking for Group ,IDU & ODU and alarm
 * count
 * 
 * @author ravi
 * 
 */
@Controller
@RequestMapping(value = "/csrf")
public class CSRFController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(CSRFController.class);

	/**
	 * This provides the Current CSRF Token in Json format.
	 * 
	 * @param request
	 * 
	 * @return Current CSRF token.
	 * 
	 */
	@RequestMapping(value = "/getCSRFToken.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getCSRFToken(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		String jsonResponse = BizConstants.EMPTY_STRING;

		try {
			jsonResponse = sessionInfo.getCsrfToken();
			if (jsonResponse == null || StringUtils.isBlank(jsonResponse)) {
				jsonResponse = BizConstants.NO_RECORDS_FOUND;
			}
		} catch (Exception ex) {
			logger.error("An Exception occured in getting token", ex);
			jsonResponse = BizConstants.SOME_ERROR_OCCURRED;
		}
		return jsonResponse;
	}
}

package com.panasonic.b2bacns.bizportal.co2factor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.co2factor.service.CO2FactorService;
import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorRequestVO;
import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorVO;
import com.panasonic.b2bacns.bizportal.common.Response;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Narendra.Kumar
 * 
 */
@Controller
@RequestMapping(value = "/co2Factor")
public class CO2FactorController {
	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(CO2FactorController.class);
	@Autowired
	private CO2FactorService co2FactorService;

	/**
	 * This is used to get co2 factor value from group table . it takes user id
	 * and company id as a input parameter
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCO2Factor.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getCO2Factor(HttpServletRequest request) {
		SessionInfo sessioninfo = CommonUtil.getSessionInfo(request);
		String siteId = request.getParameter("siteId");

		Long userId = sessioninfo.getUserId();
		Long companyId = sessioninfo.getCompanyId();
		List<CO2FactorVO> co2FactorVOs = null;
		String getCO2FactorJson = BizConstants.EMPTY_STRING;
		try {
			if (!NumberUtils.isDigits(siteId)) {
				co2FactorVOs = co2FactorService.getCO2Factor(companyId, userId);
			} else {
				co2FactorVOs = co2FactorService.getCO2Factor(new Long(siteId));
			}
			if (co2FactorVOs != null && !co2FactorVOs.isEmpty())
				getCO2FactorJson = CommonUtil
						.convertFromEntityToJsonStr(co2FactorVOs);
			else {
				getCO2FactorJson = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in getCO2Factor", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException ex) {
			logger.error("Error occured in getCO2Factor", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error("Error occured in getCO2Factor", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getCO2FactorJson;
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/viewSettings.htm", method = { RequestMethod.GET })
	public ModelAndView viewSeetings(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();

		SessionInfo sessioninfo = CommonUtil.getSessionInfo(request);

		try {

			if (sessioninfo.getLastSelectedGroupID() != null) {
				// add last selected Group ID from session
				modelAndView.getModelMap().put(
						BizConstants.LAST_SELECTED_GROUP_ATTRIB_NAME,
						sessioninfo.getLastSelectedGroupID().toString());
			}

			modelAndView.setViewName("/settings/setting");

		} catch (Exception e) {
			logger.error("Error: while viewing dashboard ", e);
		}

		return modelAndView;

	}
	
	/**
	 * This method is used to save the co2 factor value in group table
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveCO2Factor.htm", method = RequestMethod.POST)
	@ResponseBody
	public String saveCO2Factor(HttpServletRequest request) {
		String saveCO2FactorParam = (!request.getParameter(
				BizConstants.SAVE_CO2FACTOR).equals(BizConstants.NULL_STRING) ? request
				.getParameter(BizConstants.SAVE_CO2FACTOR)
				: BizConstants.EMPTY_STRING);

		String saveCO2FactorJson = BizConstants.EMPTY_STRING;

		Response response = new Response();
		try {
			@SuppressWarnings("unchecked")
			List<CO2FactorRequestVO> co2FactorRequestVOList = (List<CO2FactorRequestVO>) CommonUtil
					.convertFromJsonStrToEntityList(CO2FactorRequestVO.class,
							saveCO2FactorParam);

			if (co2FactorRequestVOList == null
					|| co2FactorRequestVOList.isEmpty()) {
				saveCO2FactorJson = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			} else {
				response.setSuccess(co2FactorService
						.saveCO2Factor(co2FactorRequestVOList));
				saveCO2FactorJson = CommonUtil
						.convertFromEntityToJsonStr(response);
			}

		} catch (GenericFailureException gfe) {
			logger.error("Error occured in saveCO2Factor", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException ex) {
			logger.error("Error occured in saveCO2Factor", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error("Error occured in saveCO2Factor", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return saveCO2FactorJson;
	}
}

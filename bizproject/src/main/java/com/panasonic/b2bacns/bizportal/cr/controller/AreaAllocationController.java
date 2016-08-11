/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.panasonic.b2bacns.bizportal.common.Response;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.cr.service.ManageAreaAllocationService;
import com.panasonic.b2bacns.bizportal.cr.vo.Area;
import com.panasonic.b2bacns.bizportal.cr.vo.AreadAllocationDetails;
import com.panasonic.b2bacns.bizportal.cr.vo.IDUAreaMapping;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author simanchal.patra
 *
 */
@Controller
@RequestMapping(value = "/cr/aa")
public class AreaAllocationController {

	private static final Logger logger = Logger
			.getLogger(AreaAllocationController.class);

	@Autowired
	private ManageAreaAllocationService areaAllocationService;

	/**
	 * Get all the IDUs and Aears based on seleted site, distribution group and
	 * user access rights
	 * 
	 * @param siteID
	 * @param distributionGroupID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllocatedAreas.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getAllocatedAreas(
			@RequestParam(value = "siteId") Long siteID,
			@RequestParam(value = "distributionId") Long distributionGroupID,
			HttpServletRequest request, HttpServletResponse response) {

		String jsonResponse = BizConstants.EMPTY_STRING;

		AreadAllocationDetails areadAllocationDetails = null;

		try {
			if (siteID != null && distributionGroupID != null) {

				areadAllocationDetails = areaAllocationService
						.getAllocatedAreas(siteID, distributionGroupID);

				if (areadAllocationDetails != null) {
					jsonResponse = CommonUtil
							.convertFromEntityToJsonStr(areadAllocationDetails);
				} else {
					jsonResponse = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}

			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (GenericFailureException ex) {
			logger.error(String.format(
					"Error occurred while retrieving all Allocated areas"
							+ " of Site [%d], Distribution Group ID [%d] ",
					siteID, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (JsonProcessingException ex) {
			logger.error(String.format(
					"Error occurred while retrieving all Allocated areas"
							+ " of Site [%d], Distribution Group ID [%d] ",
					siteID, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (Exception ex) {
			logger.error(String.format(
					"Error occurred while retrieving all Allocated areas"
							+ " of Site [%d], Distribution Group ID [%d] ",
					siteID, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

	/**
	 * Create area Noted:need to check whether areaName is unique under this
	 * siteId
	 * 
	 * @param siteID
	 * @param distributionGroupID
	 * @param areaName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createArea.htm", method = { RequestMethod.POST })
	public String createArea(@RequestParam(value = "siteId") Long siteID,
			@RequestParam(value = "distributionId") Long distributionGroupID,
			@RequestParam(value = "areaName") String areaName,
			HttpServletRequest request, HttpServletResponse httpResponse) {

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		//add by shanf
		Response response = new Response(false);


		String jsonResponse = BizConstants.EMPTY_STRING;

		try {
			//add by shanf
			// success : false
			jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);
			
			if (areaName != null && distributionGroupID != null) {

				areaAllocationService.createArea(siteID, distributionGroupID,
						areaName, sessionInfo);
				//add by shanf
				response.setSuccess(true);

				jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);
				//comment by shanf, please don't remove
//				Map<String, List<Area>> areaList = new HashMap<>();
//				areaList.put("arealist",
//						areaAllocationService.getAllAreas(distributionGroupID));
//
//				jsonResponse = CommonUtil.convertFromEntityToJsonStr(areaList);

			}

		} catch (BusinessFailureException bfe) {
			//add by shanf
			response.setSuccess(false);
			logger.error(String.format(
					"Error occurred while Creating Area with"
							+ " Name [%s] in Distribution Group ID [%d]",
					areaName, distributionGroupID), bfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (GenericFailureException ex) {
			logger.error(String.format(
					"Error occurred while Creating Area with"
							+ " Name [%s] in Distribution Group ID [%d]",
					areaName, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (Exception ex) {
			logger.error(String.format(
					"Error occurred while Creating Area with"
							+ " Name [%s] in Distribution Group ID [%d]",
					areaName, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

	/**
	 * Remove area Noted:need to check whether areaName is assigned to any IDU
	 * in the distribution group under this siteId, if yes, cannot remove.
	 * 
	 * @param siteID
	 * @param distributionGroupID
	 * @param areaName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeArea.htm", method = { RequestMethod.POST })
	public String removeArea(@RequestParam(value = "siteId") Long siteID,
			@RequestParam(value = "distributionId") Long distributionGroupID,
			@RequestParam(value = "areaName") String areaName) {

		//add by shanf
		Response response = new Response(false);
		
		String jsonResponse = BizConstants.EMPTY_STRING;

		try {
			//add by shanf
			// success : false
			jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);
			
			if (areaName != null && distributionGroupID != null) {

				areaAllocationService.removeArea(siteID, distributionGroupID,
						areaName);
				//add by shanf
				response.setSuccess(true);

				jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);
				//comment by shanf, please don't remove
//				Map<String, List<Area>> areaList = new HashMap<>();
//				areaList.put("arealist",
//						areaAllocationService.getAllAreas(distributionGroupID));
//
//				jsonResponse = CommonUtil.convertFromEntityToJsonStr(areaList);
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (BusinessFailureException bfe) {
			//add by shanf
			response.setSuccess(false);
			logger.error(String.format(
					"Error occurred while Deleting Area with"
							+ " Name [%s] in Distribution Group ID [%d]",
					areaName, distributionGroupID), bfe);

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (GenericFailureException ex) {

			logger.error(String.format(
					"Error occurred while Deleting Area with"
							+ " Name [%s] in Distribution Group ID [%d]",
					areaName, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (Exception ex) {
			logger.error(String.format(
					"Error occurred while Deleting Area with"
							+ " Name [%s] in Distribution Group ID [%d]",
					areaName, distributionGroupID), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateIDUAreaMapping.htm", method = { RequestMethod.POST })
	public String updateIndoorUnitAreaMapping(
			@RequestParam(value = "areaMappingList") String areaMappingListStr) {

		Response response = new Response(false);

		String jsonResponse = BizConstants.EMPTY_STRING;

		List<IDUAreaMapping> areaMappingList = null;

		try {

			// success : false
			jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

			areaMappingList = (List<IDUAreaMapping>) mapper.readValue(
					areaMappingListStr,
					TypeFactory.defaultInstance().constructCollectionType(
							List.class, IDUAreaMapping.class));

			if (areaMappingList != null && !areaMappingList.isEmpty()) {

				areaAllocationService.updateIDUAreaMapping(areaMappingList);
				response.setSuccess(true);

				jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (GenericFailureException ex) {

			logger.error("Error occurred while updating IDU & Area mapping", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

		} catch (Exception ex) {
			logger.error("Error occurred while updating IDU & Area mapping", ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return jsonResponse;
	}

	@ResponseBody
	@RequestMapping(value = "/isAreaAssigned.htm", method = { RequestMethod.GET })
	public String isAreaAssigned(@RequestParam(value = "areaId") Long areaId) {

		String jsonResponse = BizConstants.EMPTY_STRING;

		Response response = new Response(false);

		try {

			jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);

			if (areaId != null) {

				response.setSuccess(areaAllocationService
						.isAreaAssigned(areaId));

				jsonResponse = CommonUtil.convertFromEntityToJsonStr(response);
			} else {
				jsonResponse = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (BusinessFailureException bfe) {
			logger.error(String.format(
					"Error occurred while Checking is Area with"
							+ " ID [%s] is associated with any IndoorUnits",
					areaId));
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gex) {
			logger.error(String.format(
					"Error occurred while Checking is Area with"
							+ " ID [%s] is associated with any IndoorUnits",
					areaId), gex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			logger.error(String.format(
					"Error occurred while Checking is Area with"
							+ " ID [%s] is associated with any IndoorUnits",
					areaId), ex);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return jsonResponse;
	}
}

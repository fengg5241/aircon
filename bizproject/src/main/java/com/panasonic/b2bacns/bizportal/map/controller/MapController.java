/**
 * 
 */
package com.panasonic.b2bacns.bizportal.map.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.map.service.ManageMapService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This class handles Map Data
 * 
 * @author diksha.rattan
 * 
 */
@Controller
@RequestMapping(value = "/map")
public class MapController {

	/** Logger instance. **/
	private static final Logger logger = Logger.getLogger(MapController.class);

	@Autowired
	private ManageMapService manageMapService;

	/**
	 * 
	 * Returns company Map,Group Map for the given group and Outdoor Map details
	 * of the given Outdoor unit corresponding to the per given 1d and idType
	 * 
	 * @param id
	 * @param idType
	 * @return
	 */
	@RequestMapping(value = "/getMapData.htm", method = { RequestMethod.GET })
	public @ResponseBody String getMapData(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "idType") String idType) {

		String jsonResponse = null;

		try {
			jsonResponse = manageMapService.getMapData(id, idType);

		} catch (GenericFailureException e) {

			logger.error("Error occured in getting SVG Map Data ", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception ex) {
			jsonResponse = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error occured in getting Map data for group", ex);

		}

		return jsonResponse;
	}

}

package com.panasonic.b2bacns.bizportal.metadata.controller;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.metadata.service.ManageMetaDataService;
import com.panasonic.b2bacns.bizportal.metadata.vo.MetaIndoorUnitVO;
import com.panasonic.b2bacns.bizportal.metadata.vo.MetaOutdoorUnitVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This API handles MetaData of Indoor & Outdoor unit
 * 
 * 
 * @author verma.ashish
 * 
 */
@Controller
@RequestMapping(value = "/metadata")
public class MetaDataController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(MetaDataController.class);

	@Autowired
	private ManageMetaDataService metaDataService;

	/**
	 * This provides the Metadata of an given OutdoorUnit in Json format.
	 * 
	 * @param outdoorUnitID
	 *            ID of the OutdoorUnit
	 * @param idType
	 *            Type of the ID
	 * 
	 * @return Metadata of an OutdoorUnit.
	 */
	@RequestMapping(value = "/getOutDoorMetaData.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getOutdoorMetaData(@RequestParam("id") long id,
			@RequestParam("idType") String idType) {

		String json = BizConstants.NO_RECORDS_FOUND;

		if (idType != null
				&& idType.equalsIgnoreCase(BizConstants.ID_TYPE_OUTDOOR)) {

			try {
				MetaOutdoorUnitVO metaOutdoorUnitvo = metaDataService
						.getOutdoorMetaData(id);

				json = CommonUtil.convertFromEntityToJsonStr(metaOutdoorUnitvo);

			} catch (GenericFailureException e) {
				logger.error(
						"Error occured in getting outdoormetadata for outdoorunitid",
						e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (JsonProcessingException | IllegalAccessException
					| InvocationTargetException | HibernateException e) {
				logger.error("error occured in getting metaoutdoor data ", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (Exception e) {
				logger.error("error occured in getting metaoutdoor data ", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}

			return json;
		}

		return json;
	}

	/**
	 * This provides the Metadata of an given IndoorUnit in Json format.
	 * 
	 * @param indoorUnitID
	 *            ID of the IndoorUnit
	 * @param idType
	 *            Type of the ID
	 * 
	 * @return Metadata of an IndoorUnit.
	 */
	@RequestMapping(value = "/getIndoorMetaData.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getIndoorMetaData(@RequestParam("id") long id,
			@RequestParam("idType") String idType) {

		String json = BizConstants.NO_RECORDS_FOUND;

		if (idType != null
				&& idType.equalsIgnoreCase(BizConstants.ID_TYPE_INDOOR)) {
			try {
				MetaIndoorUnitVO metaIndoorUnitvo = metaDataService
						.getIndoorMetaData(id);

				json = CommonUtil.convertFromEntityToJsonStr(metaIndoorUnitvo);

			} catch (GenericFailureException e) {
				logger.error(
						"Error occured in getting indoormetadata for indoorunitid",
						e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (JsonProcessingException | IllegalAccessException
					| InvocationTargetException | HibernateException e) {
				logger.error("error occured in getting metaindoor data ", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (Exception e) {
				logger.error("error occured in getting metaindoor data ", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
			return json;
		}
		return json;
	}
}

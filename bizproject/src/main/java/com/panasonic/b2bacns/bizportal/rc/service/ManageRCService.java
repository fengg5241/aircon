package com.panasonic.b2bacns.bizportal.rc.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.rc.ControlRCVO;
import com.panasonic.b2bacns.bizportal.rc.ValidateRC;

/**
 * This interface contains all RC related methods
 * 
 * @author diksha.rattan, shobhit.singh, akansha
 * 
 */
public interface ManageRCService {

	/**
	 * Get control status of units
	 * 
	 * @param iduIDs
	 * @return
	 * @throws GenericFailureException
	 *             error message for 'No records found'
	 */
	public ValidateRC getRCValidation(List<Long> iduIDs)
			throws GenericFailureException;

	/**
	 * This method sets the AC PowerStatus, FanSpeed, FlapMode, AirConMode,
	 * Temperature of units and returns action status true/false
	 * 
	 * @param long1
	 * 
	 * @param iduIDs
	 * @param actionMap
	 * @return
	 * @throws GenericFailureException
	 */
	public boolean setControlRC(Long userID, Set<Long> iduIDs,
			Map<String, String> operationListMap)
			throws GenericFailureException;

}

package com.panasonic.b2bacns.bizportal.service;

import com.panasonic.b2bacns.bizportal.persistence.WinddirectionMaster;

/**
 * This interface declares methods for working with WinddirectionMaster
 * 
 * @author shobhit.singh
 *
 */
public interface WindDirectionMasterService {
	
	/**
	 * Fetches the list of WinddirectionMaster from the database.
	 * 
	 * @return
	 */
	public WinddirectionMaster getWinddirectionmasterbyId(long windDirectionID);
	
	/**
	 * 
	 * @param windDirectionName
	 * @return
	 */
	public WinddirectionMaster getWinddirectionmasterbyName(String windDirectionName);

}

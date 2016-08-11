package com.panasonic.b2bacns.bizportal.service;

import com.panasonic.b2bacns.bizportal.persistence.FanspeedMaster;

public interface FanSpeedMasterService {

	/**
	 * Fetches the list of Fanspeedmaster from the database.
	 * 
	 * @return
	 */
	public FanspeedMaster getFanspeedmasterbyId(long fanspeedID);

	/**
	 * 
	 * @param fanspeedName
	 * @return
	 */
	public FanspeedMaster getFanspeedmasterbyName(String fanspeedName);

}

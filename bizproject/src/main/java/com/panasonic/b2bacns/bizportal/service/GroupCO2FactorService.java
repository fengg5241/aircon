package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.persistence.Groupco2factor;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
public interface GroupCO2FactorService {

	/**
	 * this method used to save and update the batch of groupco2factor
	 * 
	 * @param groupCO2Factor
	 * @return
	 */
	public void saveOrUpdateGroupCO2Factor(List<Groupco2factor> groupCO2Factor);

}

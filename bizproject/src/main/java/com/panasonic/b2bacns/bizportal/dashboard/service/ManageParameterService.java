/**
 * 
 */
package com.panasonic.b2bacns.bizportal.dashboard.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.panasonic.b2bacns.bizportal.dashboard.vo.ODUParameterInfo;

/**
 * This interface contains all ODU Parameters related methods
 * 
 * @author akansha
 *
 */
public interface ManageParameterService {

	/**
	 * 
	 * @param id
	 *            The ID of Entity
	 * @param idType
	 *            The IDType of Entity
	 * @param selectedParamList
	 *            The List of selected parameters forEntity
	 * @return The List of {@link ODUParameterInfo}
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<ODUParameterInfo> getODUParameters(Long id, String idType,
			String selectedParamList) throws IllegalAccessException,
			InvocationTargetException;
	
	public void getODUParameterDetails(Long id, String idType);

}

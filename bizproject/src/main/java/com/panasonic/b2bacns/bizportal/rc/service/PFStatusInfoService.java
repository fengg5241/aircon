/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc.service;

import java.util.List;

import org.hibernate.HibernateException;

/**
 * @author Narendra.Kumar
 * 
 */
public interface PFStatusInfoService {
	public Object getIDUInfo(String faclId, String propertyId)
			throws HibernateException;

	public Object getIDUInfo(String faclId, String lowTempPropId,
			String highTempPropId, String tempToBeCheck);

	public Object getIDURCOPVals(String faclId, String operation,
			List<String> propertyIds) throws HibernateException;

}

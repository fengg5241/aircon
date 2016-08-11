package com.panasonic.b2bacns.bizportal.map.service;

import java.lang.reflect.InvocationTargetException;

import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
public interface ManageMapService {

	/**
	 * This method is used to return group details , children group
	 * details,Sibling group details,Unit details of the given group
	 * 
	 * @param groupVO
	 * @return
	 */

	public String getMapData(String id, String idType) throws HibernateException,
			IllegalAccessException, InvocationTargetException,
			BusinessFailureException, JsonProcessingException;

}

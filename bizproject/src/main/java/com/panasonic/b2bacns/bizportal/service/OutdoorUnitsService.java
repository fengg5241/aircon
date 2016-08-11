package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Outdoorunit;

public interface OutdoorUnitsService {

	
	/**
	 * This method is used to find the List of outdoorunits for the given properties
	 */
	public List<Outdoorunit> getOutdoorunitListByProperties(
			Map<String, Object> properties) throws HibernateException;

	
	/**
	 * This method is used to find the outdoor units for a given outdoor ID
	 */

	public Outdoorunit getOutdoorUnitById(long id)
			throws HibernateException;

}

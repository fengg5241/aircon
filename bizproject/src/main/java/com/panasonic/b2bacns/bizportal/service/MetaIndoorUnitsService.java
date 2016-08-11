package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Metaindoorunit;
/**
 * DAO Service class for metaindoorunits
 * @author jwchan
 * 
 */
/**
 * 
 * @author Jia Wei
 *
 */

public interface MetaIndoorUnitsService {

	public boolean addMetaIndoorUnits(Metaindoorunit metaindoorunit);

		
	/**
	 * This method is used to find the List of metaindoorunits for the given properties
	 */
	public List<Metaindoorunit> getMetaIndoorUnitListByProperties(
			Map<String, Object> properties) throws HibernateException;

	
	/**
	 * This method is used to find the metaindoor units for a given metaindoorunit ID
	 */

	public Metaindoorunit getMetaIndoorUnitById(long id)
			throws HibernateException;

}

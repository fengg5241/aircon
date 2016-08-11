package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;

/**
 * @author shobhit.singh
 * */
public interface IndoorUnitsService {

	/**
	 * get Indoor unit List By Properties
	 * 
	 * @param properties
	 * @return
	 */
	public List<Indoorunit> getIndoorunitListByProperties(
			Map<String, Object> properties) throws HibernateException;

	/**
	 * get Indoor unit By Id
	 * 
	 * @param id
	 * @return
	 */
	public Indoorunit getIndoorunitById(long id) throws HibernateException;

	/**
	 * get Indoor unit By ID
	 * 
	 * @param indoorUnitsId
	 * @return
	 */
	public Indoorunit getIndoorunitByID(long indoorUnitsId)
			throws HibernateException;

	/**
	 * get Indoor units By IDs
	 * 
	 * @param iduIds
	 * @return
	 * @throws HibernateException
	 */
	public List<Indoorunit> getIndoorUnitsByIds(List<Long> iduIds)
			throws HibernateException;

	/**
	 * @param iduIds
	 * @return
	 * @throws HibernateException
	 */
	public List<Indoorunit> getIndoorUnitsBatchByIds(List<Long> iduIds)
			throws HibernateException;

	public void saveOrUpdate(Indoorunit idu);

	public void batchSaveOrUpdate(List<Indoorunit> iduList);

	/**
	 * Provides a
	 * 
	 * @return
	 */
	public Criteria getCriteria();

}

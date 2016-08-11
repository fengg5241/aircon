package com.panasonic.b2bacns.bizportal.stats.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;

public interface GenericDAOService<T> {

	/**
	 * @param entity
	 * @return
	 * @throws BusinessFailureException 
	 */
	public Object create(final T entity) throws BusinessFailureException;

	/**
	 * @param entity
	 */
	public void saveOrUpdate(final T entity); 

	/**
	 * @return
	 */
	public List<T> findAll();

	/**
	 * @param entity
	 * @return
	 * @throws BusinessFailureException
	 */
	public void update(final T entity) throws BusinessFailureException;

	/**
	 * @param entity
	 * @throws BusinessFailureException
	 */
	public void delete(final T entity) throws BusinessFailureException;

	/**
	 * @param entityId
	 */
	public void deleteById(final long entityId);

	/**
	 * @param id
	 * @return
	 */
	public T findByID(final long id);

	/**
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findAllByProperty(String propertyName, Object value);

	/**
	 * @param properties
	 * @return
	 * @throws OperationNotSupportedException
	 * @throws HibernateException
	 */
	public List<T> findAllByProperty(HashMap<String, Object> properties) throws HibernateException, OperationNotSupportedException;

	/**
	 * @param criteriaMap
	 * @param orderBy
	 * @param orderByPropertyName
	 * @return
	 */
	public List<T> findBySortCriteria(Map<String, Object> criteriaMap,
			String orderBy, String orderByPropertyName);

	/**
	 * @param criterion
	 * @return
	 */
	public Criteria createCriteria();

	/**
	 * 
	 * @param entity
	 * @throws HibernateException
	 */
	public void merge(T entity) throws HibernateException;

	/**
	 * @param entities
	 * @throws HibernateException
	 */
	public void batchSaveOrUpdate(List<T> entities) throws HibernateException;

	/**
	 * 
	 * @param entities
	 * @throws HibernateException
	 */
	public void batchDelete(List<T> entities) throws HibernateException;

}

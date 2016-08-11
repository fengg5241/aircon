/**
 * (c) R Systems International Ltd.
 */
package com.panasonic.b2bacns.bizportal.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session.LockRequest;

/**
 * The purpose of using a Generic DAO is the CRUD operations that can be
 * performed on each entity.
 * 
 * @param <T>
 *            The Entity on which CRUD operations to be performed.
 * 
 * @author abhishek.vishnoi
 */
public interface GenericDAO<T> {

	/**
	 * Set the Entity on which database operations to be performed.
	 * 
	 * @param clazz
	 */
	public void setClazz(Class<T> clazz);

	/**
	 * Reads all the Entities present in the system.
	 * 
	 * @return List of the Entities.
	 * @throws HibernateException
	 */
	public List<T> findAll() throws HibernateException;

	/**
	 * Reads the Entity which matches the specified property.
	 * 
	 * @param propertyName
	 *            Name of the property to be matched.
	 * @param value
	 *            Value of the property to be matched.
	 * 
	 * @return List of the Entities
	 * 
	 * @throws HibernateException
	 */
	public List<T> findAllByProperty(String propertyName, Object value)
			throws HibernateException;

	/**
	 * Reads the Entity which matches all the specified properties.
	 * 
	 * @param propertiesMap
	 *            Map containing the properties to be matched.
	 * 
	 * @return List of the Entities
	 * 
	 * @throws HibernateException
	 */
	public List<T> findAllByProperty(Map<String, Object> propertiesMap)
			throws HibernateException;

	/**
	 * Reads the Entity which matches all the specified properties.
	 * 
	 * @param propertiesMap
	 *            Map containing the properties to be matched.
	 * 
	 * @return List of the Entities
	 * 
	 * @throws HibernateException
	 */
	public List<T> findAllByProperties(Map<String, Object> propertiesMap)
			throws HibernateException;

	/**
	 * Update the Entity in the system with new values.
	 * 
	 * @param entity
	 *            The Entity to be updated in the System.
	 * 
	 * @return The updated Entity
	 * 
	 * @throws HibernateException
	 */
	public void update(final T entity) throws HibernateException;

	/**
	 * Remove the Entity from the system.
	 * 
	 * @param entity
	 *            The Entity to be removed.
	 * 
	 * @throws HibernateException
	 */
	public void delete(final T entity) throws HibernateException;

	/**
	 * Remove the Entity from the system specified by the Identifier.
	 * 
	 * @param entityId
	 *            The Identifier to be removed.
	 * 
	 * @throws HibernateException
	 */
	public void deleteById(final long entityId) throws HibernateException;

	/**
	 * Save or Update the Entity in the System.
	 * 
	 * @param entity
	 *            The Entity to be Updated or Saved.
	 * 
	 * @throws HibernateException
	 */
	public void saveOrUpdate(T entity) throws HibernateException;

	/**
	 * Reads the Entity from the system specified by the Identifier.
	 * 
	 * @param id
	 *            Identifier of the Entity whose details required.
	 * 
	 * @return The Entity
	 * 
	 * @throws HibernateException
	 */
	public T findByID(long id) throws HibernateException;

	/**
	 * This saves the specified Entity into the System.
	 * 
	 * @param entity
	 *            The Entity to be saved.
	 * 
	 * @return Identifier of the newly created Entity.
	 * 
	 * @throws HibernateException
	 */
	public Long create(final T entity) throws HibernateException;

	public List<T> findAllByFilters(Map<String, Object> filter_criteria)
			throws ParseException;

	public List<?> executeSQLQuery(String sqlQuery) throws HibernateException;

	public Query executeHQLQuery(String hqlQuery) throws HibernateException;

	public List<?> executeHQLSelect(String hqlQuery,
			Map<String, Object> parameter) throws HibernateException;

	/**
	 * 
	 * Executes a hibernate query on the basis of parameters passed in it.
	 * 
	 * @param hqlQuery
	 *            Hibernate query
	 * @param parameter
	 *            Parameters passed in query
	 * @return
	 * @throws HibernateException
	 */
	public Query executeHQLQuery(String hqlQuery, Map<String, Object> parameter)
			throws HibernateException;

	/**
	 * Reads the Entity which matches the specified property. The Property must
	 * have String Value. The values will be checked with case Ignored.
	 * 
	 * @param propertyName
	 *            Name of the property to be matched.
	 * @param value
	 *            String Value of the property to be matched.
	 * 
	 * @return List of the Entities
	 * 
	 * @throws HibernateException
	 */
	public List<T> findAllByStringPropertyIgnoreCase(String propertyName,
			String value) throws HibernateException;

	/**
	 * 
	 * @param propertiesMap
	 * @return
	 * @throws HibernateException
	 */
	public List<T> findAllByPropertyCountandDate(
			Map<String, Object> propertiesMap) throws HibernateException;

	/**
	 * Reads the Entity which matches the specified Map. The values will be
	 * checked with case Ignored.
	 * 
	 * @param propertiesMap
	 *            Name of the properties to be matched.
	 * 
	 * @return List of the Entities
	 * 
	 * @throws HibernateException
	 */
	public List<T> findAllByStringPropertyIgnoreCase(
			Map<String, Object> propertiesMap) throws HibernateException;

	/**
	 * Reads the Entity which matches all the specified properties.
	 * 
	 * @param propertiesMap
	 *            Map containing the properties to be matched.
	 * 
	 * @return List of the Entities
	 * 
	 * @throws HibernateException
	 */
	public List<T> findAllByPropertyIgnoreCase(Map<String, Object> propertiesMap)
			throws HibernateException;

	/**
	 * Create a Criteria for that Entity type.
	 * 
	 * @return A Criteria
	 */
	public Criteria createCriteria();

	/**
	 * Flush current session
	 */
	public void sessionFlush();

	/**
	 * Clear current session
	 */
	public void sessionClear();

	/**
	 * Save or Update the Batch Entities in the System.
	 * 
	 * @param entities
	 *            The Entities to be Updated or Saved.
	 * @throws HibernateException
	 */
	public void batchSaveOrUpdate(List<T> entities) throws HibernateException;
	/**
	 * add By shanf
	 * @param sqlQuery
	 * @throws HibernateException
	 */
	
	public int executeSQLUpdateQuery(final String sqlQuery)
			throws HibernateException;

	/**
	 * 
	 * @param entities
	 * @throws HibernateException
	 */
	public void batchDelete(List<T> entities) throws HibernateException;

	/**
	 * This will be used to create criteria with join
	 * 
	 * @param alias
	 * @return
	 */
	public Criteria findByCriteria(String alias);

	/**
	 * Copy the state of the given object onto the persistent object with the
	 * same identifier. If there is no persistent instance currently associated
	 * with the session, it will be loaded. Return the persistent instance. If
	 * the given instance is unsaved, save a copy of and return it as a newly
	 * persistent instance. The given instance does not become associated with
	 * the session. This operation cascades to associated instances if the
	 * association is mapped with cascade="merge"
	 * 
	 * @param entity
	 * @throws HibernateException
	 */
	public void merge(T entity) throws HibernateException;

	/**
	 * Provides lock on that table.
	 * 
	 * @param entityClass
	 * @param lockMode
	 * @return
	 */
	public LockRequest getLockRequest(T entityClass, LockMode lockMode,
			int timeout);

	/**
	 * Provides lock on that table.
	 * 
	 * @param entityClass
	 * @param lockOptn
	 * @return
	 */
	public LockRequest getLockRequest(T entityClass, LockOptions lockOptn);

}

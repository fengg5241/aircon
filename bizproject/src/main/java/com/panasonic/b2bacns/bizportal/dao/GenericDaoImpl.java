/**
 * (c) R Systems International Ltd.
 */
package com.panasonic.b2bacns.bizportal.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Session.LockRequest;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;

/**
 * The purpose of using a Generic DAO is the CRUD operations that can be
 * performed on each entity.
 * 
 * @author abhishek.vishnoi
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoImpl<T> implements GenericDAO<T> {

	/** Logger instance. **/
	private static final Logger logger = Logger.getLogger(GenericDaoImpl.class);

	/** The Class represents the Entity Class */
	private Class<T> clazz;

	@Resource
	private Properties properties;

	/** The Hibernate Session Factory */
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${hibernate.jdbc.batch_size}")
	private int maxBatchSize;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#setClazz(java.lang.Class)
	 */
	@Override
	public final void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
		logger.debug(String.format(
				"Generic DAO insantiated for peristence class - %s",
				clazzToSet.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#create(java.lang.Object)
	 */
	@Override
	public Long create(T entity) throws HibernateException {
		return (Long) getCurrentSession().save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() throws HibernateException {
		return getCurrentSession().createQuery("from " + clazz.getName())
				.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#findByID(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findByID(long id) throws HibernateException {
		return (T) getCurrentSession().get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAllByProperty(java
	 * .lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByProperty(String propertyName, Object value)
			throws HibernateException {
		Criteria crit = getCurrentSession().createCriteria(clazz.getName());
		crit.add(Restrictions.eq(propertyName, value));
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#
	 * findAllByStringPropertyIgnoreCase(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByStringPropertyIgnoreCase(String propertyName,
			String value) throws HibernateException {
		Criteria crit = getCurrentSession().createCriteria(clazz.getName());
		value = StringUtils.trim(value);
		crit.add(Restrictions.eq(propertyName, value).ignoreCase());
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAllByProperty(java
	 * .util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByProperty(Map<String, Object> propertiesMap)
			throws HibernateException {

		Criteria crit = getCurrentSession().createCriteria(clazz);

		Iterator<Entry<String, Object>> itr = propertiesMap.entrySet()
				.iterator();
		Entry<String, Object> propertyName = null;
		while (itr.hasNext()) {
			propertyName = itr.next();

			if (propertyName.getKey().equalsIgnoreCase("desc"))
				crit.addOrder(Order.desc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("asc"))
				crit.addOrder(Order.asc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("count"))
				crit.setMaxResults((int) propertyName.getValue());
			else
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()));
		}

		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAllByProperties(java
	 * .util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByProperties(Map<String, Object> propertiesMap)
			throws HibernateException {

		Criteria crit = getCurrentSession().createCriteria(clazz);

		Iterator<Entry<String, Object>> itr = propertiesMap.entrySet()
				.iterator();
		Entry<String, Object> propertyName = null;
		while (itr.hasNext()) {
			propertyName = itr.next();

			if (propertyName.getKey().equalsIgnoreCase("desc"))
				crit.addOrder(Order.desc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("asc"))
				crit.addOrder(Order.asc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("count"))
				crit.setMaxResults((int) propertyName.getValue());
			else if (propertyName.getKey().contains("-ge-"))
				crit.add(Restrictions.ge(
						propertyName.getKey().replace("-ge-", ""),
						propertyName.getValue()));
			else if (propertyName.getKey().contains("-le-"))
				crit.add(Restrictions.le(
						propertyName.getKey().replace("-le-", ""),
						propertyName.getValue()));
			else if (propertyName.getKey().contains("-gt-"))
				crit.add(Restrictions.gt(
						propertyName.getKey().replace("-gt-", ""),
						propertyName.getValue()));
			else if (propertyName.getValue().getClass().equals(String.class))
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()).ignoreCase());
			else if (propertyName.getValue() instanceof List<?>) {

				ArrayList<String> l2 = (ArrayList<String>) propertyName
						.getValue();
				if (l2.isEmpty()) {
					throw new GenericFailureException(
							"Provided list is empty",
							new OperationNotSupportedException(
									"Provided list is empty, so not including in criteria"));
				}
				crit.add(Restrictions.in(propertyName.getKey(), l2));

			} else {
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()));
			}

		}

		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAllByPropertyIgnoreCase
	 * (java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByPropertyIgnoreCase(Map<String, Object> propertiesMap)
			throws HibernateException {

		Criteria crit = getCurrentSession().createCriteria(clazz);

		Iterator<Entry<String, Object>> itr = propertiesMap.entrySet()
				.iterator();
		Entry<String, Object> propertyName = null;
		while (itr.hasNext()) {
			propertyName = itr.next();

			if (propertyName.getKey().equalsIgnoreCase("desc"))
				crit.addOrder(Order.desc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("asc"))
				crit.addOrder(Order.asc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("count"))
				crit.setMaxResults((int) propertyName.getValue());
			else if (propertyName.getValue().getClass().equals(String.class))
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()).ignoreCase());
			else
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()));

		}

		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAllByPropertyCountandDate
	 * (java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByPropertyCountandDate(
			Map<String, Object> propertiesMap) throws HibernateException {

		Criteria crit = getCurrentSession().createCriteria(clazz);

		Iterator<Entry<String, Object>> itr = propertiesMap.entrySet()
				.iterator();
		Entry<String, Object> propertyName = null;
		while (itr.hasNext()) {
			propertyName = itr.next();

			if (propertyName.getKey().equalsIgnoreCase("desc"))
				crit.addOrder(Order.desc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("asc"))
				crit.addOrder(Order.asc(propertyName.getValue().toString()));
			else if (propertyName.getKey().equalsIgnoreCase("count"))
				crit.setMaxResults((int) propertyName.getValue());
			else if (propertyName.getKey().contains("Cal_")) {

				try {
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					// get current date time with Date()
					Date date = new Date();
					String datetype = dateFormat.format(date);

					Date date1 = dateFormat.parse(datetype);
					// get current date time with Calendar()
					Calendar todaystartdate = Calendar.getInstance();
					todaystartdate.setTimeInMillis(date1.getTime());
					todaystartdate.add(Calendar.HOUR, 00);
					todaystartdate.add(Calendar.MINUTE, 00);
					todaystartdate.add(Calendar.SECOND, 00);
					Calendar todayenddate = Calendar.getInstance();
					todayenddate.setTimeInMillis(date1.getTime());
					todayenddate.add(Calendar.HOUR, 23);
					todayenddate.add(Calendar.MINUTE, 59);
					todayenddate.add(Calendar.SECOND, 59);

					crit.add(Restrictions.between(propertyName.getKey()
							.substring(4), todaystartdate, todayenddate));
				} catch (ParseException e) {

					e.printStackTrace();
				}
			} else
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()));
		}

		return crit.list();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) throws HibernateException {
		getCurrentSession().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void merge(T entity) throws HibernateException {
		getCurrentSession().merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#saveOrUpdate(java.lang
	 * .Object)
	 */
	@Override
	public void saveOrUpdate(T entity) throws HibernateException {
		getCurrentSession().saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) throws HibernateException {
		getCurrentSession().delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#deleteById(long)
	 */
	@Override
	public void deleteById(long entityId) throws HibernateException {
		T entity = findByID(entityId);
		delete(entity);
	}

	/**
	 * Provides the current Hibernate Session.
	 * 
	 * @return
	 * @throws HibernateException
	 */
	protected final Session getCurrentSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#sessionFlush()
	 */
	@Override
	public void sessionFlush() {
		getCurrentSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#sessionClear()
	 */
	@Override
	public void sessionClear() {
		getCurrentSession().clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#findAllByFilters(java.
	 * util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByFilters(Map<String, Object> filter_criteria)
			throws ParseException {

		// Create Criteria with Parent table.
		Criteria criteria = getCurrentSession().createCriteria(clazz.getName());
		// Add Sorted order as per the last value of filter_criteria
		if (filter_criteria.keySet().contains("desc")) {
			criteria.addOrder(Order
					.desc(filter_criteria.get("desc").toString()));
			filter_criteria.remove("desc");
		} else if (filter_criteria.keySet().contains("asc")) {
			criteria.addOrder(Order.asc(filter_criteria.get("asc").toString()));
			filter_criteria.remove("asc");
		}

		Object[] tableMapping = filter_criteria.keySet().toArray();
		String baseTable = null;
		Object[] filterValue = filter_criteria.values().toArray();
		List<String> trackAlias = new ArrayList<String>();

		// Loop to add Restriction at runtime.
		for (int offset = 0; offset < filter_criteria.size(); offset++) {
			// baseTable = (String) tableMapping.get(offset);
			int start = tableMapping[offset].toString().indexOf('.');
			int limit = tableMapping[offset].toString().lastIndexOf('.');
			baseTable = (String) tableMapping[offset].toString().substring(
					start + 1, limit);

			// If property is not Null.
			if (filterValue[offset] != null) {
				String param = filter_criteria.keySet().toArray()[offset]
						.toString();
				// If Property belong to nested table.
				if (baseTable.indexOf(".") != -1) {
					String[] tableHierarchy = baseTable.split("\\.");
					// I Consider that the way to reach to specific Pojo is same
					// from parent Pojo.
					String alias = null;
					if (trackAlias
							.contains(tableHierarchy[tableHierarchy.length - 1])) {
						alias = tableHierarchy[tableHierarchy.length - 1]
								+ "_alias.";
					} else {
						for (int i = 1; i < tableHierarchy.length; i++) {
							if (i == 1) {
								criteria.createAlias(tableHierarchy[i],
										tableHierarchy[i] + "_alias");
								alias = tableHierarchy[i] + "_alias.";
							} else {
								criteria.createAlias(alias + tableHierarchy[i],
										tableHierarchy[i] + "_alias");
								alias = tableHierarchy[i] + "_alias.";
							}
							trackAlias.add(tableHierarchy[i]);
						}
					}
					// If type is Calendar.
					if (filterValue[offset].toString().contains("Calendar_")) {
						Calendar order_start_date = Calendar.getInstance();
						Calendar order_end_date = Calendar.getInstance();
						order_start_date.setTimeInMillis(Long
								.valueOf(filterValue[offset].toString()
										.substring(9)));
						order_end_date.setTimeInMillis(Long
								.valueOf(filterValue[offset].toString()
										.substring(9)));
						order_end_date.add(Calendar.HOUR, 23);
						order_end_date.add(Calendar.MINUTE, 59);
						order_end_date.add(Calendar.SECOND, 59);
						criteria.add(Restrictions.between(param.substring(
								param.lastIndexOf('.') + 1, param.length()),
								order_start_date, order_end_date));
					}
					// If type is Date.
					else if (filterValue[offset].toString().contains("Date_")) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						Date order_start_date = sdf.parse(filterValue[offset]
								.toString().substring(5) + " 00:00:00");
						Date order_end_date = sdf.parse(filterValue[offset]
								.toString().substring(5) + " 23:59:59");
						criteria.add(Restrictions.between(param.substring(
								param.lastIndexOf('.') + 1, param.length()),
								order_start_date, order_end_date));
					}// If type is String.
					else if (filterValue[offset].getClass()
							.equals(String.class)) {

						if (param.substring(param.lastIndexOf('.') + 1,
								param.length()).equalsIgnoreCase("vcstts")) {

							criteria.add(Restrictions.eq(
									alias
											+ param.substring(
													param.lastIndexOf('.') + 1,
													param.length()),
									filterValue[offset]).ignoreCase());

						} else {
							String like_param = "%" + filterValue[offset] + "%";
							criteria.add(Restrictions.ilike(
									alias
											+ param.substring(
													param.lastIndexOf('.') + 1,
													param.length()), like_param));

						}

					} else
						criteria.add(Restrictions.eq(
								alias
										+ param.substring(
												param.lastIndexOf('.') + 1,
												param.length()),
								filterValue[offset]));
				}
				// If Property belong to parent/base table.
				else {
					// If type is Calendar.
					if (filterValue[offset].toString().contains("Calendar_")) {
						Calendar order_start_date = Calendar.getInstance();
						Calendar order_end_date = Calendar.getInstance();

						order_start_date.setTimeInMillis(Long
								.valueOf(filterValue[offset].toString()
										.substring(9)));
						order_end_date.setTimeInMillis(Long
								.valueOf(filterValue[offset].toString()
										.substring(9)));
						order_end_date.add(Calendar.HOUR, 23);
						order_end_date.add(Calendar.MINUTE, 59);
						order_end_date.add(Calendar.SECOND, 59);
						criteria.add(Restrictions.between(param.substring(
								param.lastIndexOf('.') + 1, param.length()),
								order_start_date, order_end_date));
					}
					// If type is Date.
					else if (filterValue[offset].toString().contains("Date_")) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						Date order_start_date = sdf.parse(filterValue[offset]
								.toString().substring(5) + " 00:00:00");
						Date order_end_date = sdf.parse(filterValue[offset]
								.toString().substring(5) + " 23:59:59");
						criteria.add(Restrictions.between(param.substring(
								param.lastIndexOf('.') + 1, param.length()),
								order_start_date, order_end_date));
					} // If type is String.
					else if (filterValue[offset].getClass()
							.equals(String.class)) {

						// Modified for Status filter
						if (param.substring(param.lastIndexOf('.') + 1,
								param.length()).equalsIgnoreCase("vcstts")) {
							criteria.add(Restrictions.eq(
									param.substring(param.lastIndexOf('.') + 1,
											param.length()),
									filterValue[offset]).ignoreCase());
						} else {
							String like_param = "%" + filterValue[offset] + "%";
							criteria.add(Restrictions.ilike(
									param.substring(param.lastIndexOf('.') + 1,
											param.length()), like_param));
						}

					} else {
						criteria.add(Restrictions.eq(param.substring(
								param.lastIndexOf('.') + 1, param.length()),
								filterValue[offset]));
					}
				}
			}
		}

		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#executeSQLQuery(java.lang
	 * .String)
	 */
	@Override
	public List<?> executeSQLQuery(String sqlQuery) throws HibernateException {

		Query query = getCurrentSession().createSQLQuery(sqlQuery);

		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#
	 * findAllByStringPropertyIgnoreCase(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByStringPropertyIgnoreCase(
			Map<String, Object> propertiesMap) throws HibernateException {

		Criteria crit = getCurrentSession().createCriteria(clazz);

		Iterator<Entry<String, Object>> itr = propertiesMap.entrySet()
				.iterator();
		Entry<String, Object> propertyName = null;
		while (itr.hasNext()) {
			propertyName = itr.next();

			if (propertyName.getKey().equalsIgnoreCase("desc"))
				crit.addOrder(Order.desc(propertyName.getValue().toString()));

			else if (propertyName.getKey().equalsIgnoreCase("asc"))
				crit.addOrder(Order.asc(propertyName.getValue().toString()));

			else if (propertyName.getKey().equalsIgnoreCase("count"))
				crit.setMaxResults((int) propertyName.getValue());

			else if (propertyName.getValue().getClass().equals(String.class)) {
				String value = StringUtils.trim(propertyName.getValue()
						.toString());
				crit.add(Restrictions.eq(propertyName.getKey(), value)
						.ignoreCase());
			}

			else
				crit.add(Restrictions.eq(propertyName.getKey(),
						propertyName.getValue()));

		}

		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.GenericDAO#executeHQLQuery(java.lang
	 * .String)
	 */
	@Override
	public Query executeHQLQuery(String hqlQuery) throws HibernateException {
		return getCurrentSession().createQuery(hqlQuery);
	}

	@Override
	public List<?> executeHQLSelect(String hqlQuery,
			Map<String, Object> parameter) throws HibernateException {
		Query prepareHQLQuery = getCurrentSession().createQuery(hqlQuery);
		for (Map.Entry<String, Object> entry : parameter.entrySet()) {

			if (entry.getValue() instanceof ArrayList) {
				prepareHQLQuery.setParameterList(entry.getKey(),
						(List<?>) entry.getValue());

			} else {

				prepareHQLQuery.setParameter(entry.getKey(), entry.getValue());
			}

		}

		logger.debug("HQL query to be executed is : "
				+ prepareHQLQuery.getQueryString());
		logger.debug("HQL query parameters are : " + parameter.toString());
		try {
			return prepareHQLQuery.list();
		} catch (HibernateException hbExp) {
			logger.error(
					String.format(
							"Error occured while executing query without Scalar"
									+ " mapping - %s ",
							prepareHQLQuery.getQueryString()), hbExp);
		}

		return null;
	}

	@Override
	public Query executeHQLQuery(String hqlQuery, Map<String, Object> parameter)
			throws HibernateException {
		Query prepareHQLQuery = getCurrentSession().createQuery(hqlQuery);
		for (Map.Entry<String, Object> entry : parameter.entrySet()) {

			if (entry.getValue() instanceof ArrayList) {
				prepareHQLQuery.setParameterList(entry.getKey(),
						(List<?>) entry.getValue());

			} else {

				prepareHQLQuery.setParameter(entry.getKey(), entry.getValue());
			}

		}

		logger.debug("HQL query to be executed is : "
				+ prepareHQLQuery.getQueryString());
		logger.debug("HQL query parameters are : " + parameter.toString());
		try {
			return prepareHQLQuery;
		} catch (HibernateException hbExp) {
			logger.error(
					String.format(
							"Error occured while executing query without Scalar"
									+ " mapping - %s ",
							prepareHQLQuery.getQueryString()), hbExp);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#findByCriteria()
	 */
	@Override
	public Criteria createCriteria() {
		return getCurrentSession().createCriteria(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.GenericDAO#findByCriteria()
	 */
	@Override
	public Criteria findByCriteria(String alias) {
		return getCurrentSession().createCriteria(clazz, alias);
	}

	@Override
	@Transactional
	public void batchSaveOrUpdate(List<T> entities) throws HibernateException {
		if (entities != null && !entities.isEmpty()) {
			int size = entities.size();
			int batchSize = 1;
			int transactionRecordCount = 0;

			maxBatchSize = maxBatchSize <= 0 ? 1 : maxBatchSize;

			if (size <= maxBatchSize) {
				batchSize = size;
			} else {
				batchSize = maxBatchSize;
			}

			for (T entity : entities) {
				getCurrentSession().saveOrUpdate(entity);
				if (++transactionRecordCount % batchSize == 0) {
					getCurrentSession().flush();
					getCurrentSession().clear();

				}
			}
		}

	}

	@Override
	@Transactional
	public void batchDelete(List<T> entities) throws HibernateException {
		if (entities != null && !entities.isEmpty()) {
			int size = entities.size() == 0 ? 1 : entities.size();
			int batchSize = 1;
			int transactionRecordCount = 0;

			maxBatchSize = maxBatchSize <= 0 ? 1 : maxBatchSize;

			if (size <= maxBatchSize) {
				batchSize = size;
			} else {
				batchSize = maxBatchSize;
			}

			for (T entity : entities) {
				getCurrentSession().delete(entity);
				if (++transactionRecordCount % batchSize == 0) {
					getCurrentSession().flush();
					getCurrentSession().clear();

				}
			}
		}

	}

	@Override
	public LockRequest getLockRequest(T entityClass, LockMode lockMode,
			int timeout) {

		LockOptions lockOptn = new LockOptions();
		if (lockMode != null) {
			lockOptn.setLockMode(lockMode);
		} else {

		}
		// lockOptn.setScope(true);
		lockOptn.setTimeOut(timeout);
		return getCurrentSession().buildLockRequest(lockOptn);
	}

	@Override
	public LockRequest getLockRequest(T entityClass, LockOptions lockOptn) {
		return getCurrentSession().buildLockRequest(lockOptn);
	}

	@Override
	@Transactional
	public int executeSQLUpdateQuery(String sqlQuery) throws HibernateException {

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		logger.debug("SQL query to be executed is : " + query);

		int noOfRecordsUpdatedOrDeleted;

		try {
			noOfRecordsUpdatedOrDeleted = query.executeUpdate();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query without Scalar"
							+ " mapping - %s ", query.getQueryString()), hbExp);
			throw hbExp;
		}
		return noOfRecordsUpdatedOrDeleted;
	}
}

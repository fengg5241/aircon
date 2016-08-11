/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.DistributionGroup;

/**
 * @author simanchal.patra
 *
 */
@Service
public class DistributionGroupDAOServiceImpl implements
		DistributionGroupDAOservice {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(DistributionGroupDAOServiceImpl.class);

	private GenericDAO<DistributionGroup> dao;

	@Autowired
	public void setDAO(GenericDAO<DistributionGroup> daoToSet) {
		logger.debug("daoToSet " + daoToSet);
		dao = daoToSet;
		dao.setClazz(DistributionGroup.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#create
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public Object create(DistributionGroup entity) {

		return dao.create(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * saveOrUpdate(java.lang.Object)
	 */
	@Transactional
	@Override
	public void saveOrUpdate(DistributionGroup entity) {
		dao.saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findAll
	 * ()
	 */
	@Transactional
	@Override
	public List<DistributionGroup> findAll() {

		return dao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#update
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public void update(DistributionGroup entity) {
		dao.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#delete
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public void delete(DistributionGroup entity) {
		dao.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * deleteById(long)
	 */
	@Transactional
	@Override
	public void deleteById(long entityId) {
		dao.deleteById(entityId);
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findByID
	 * (long)
	 */
	@Transactional
	@Override
	public DistributionGroup findByID(long id) {

		return dao.findByID(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.lang.String, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<DistributionGroup> findAllByProperty(String propertyName,
			Object value) {

		return dao.findAllByProperty(propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.util.HashMap)
	 */
	@Transactional
	@Override
	public List<DistributionGroup> findAllByProperty(
			HashMap<String, Object> properties) {

		return dao.findAllByProperty(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findBySortCriteria(java.util.Map, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public List<DistributionGroup> findBySortCriteria(
			Map<String, Object> criteriaMap, String orderBy,
			String orderByPropertyName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void merge(DistributionGroup entity) throws HibernateException {
		dao.merge(entity);
	}

	@Override
	public Criteria createCriteria() {

		return dao.createCriteria();
	}

	@Override
	public void batchSaveOrUpdate(List<DistributionGroup> entities)
			throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void batchDelete(List<DistributionGroup> entities)
			throws HibernateException {
		// TODO Auto-generated method stub

	}

}

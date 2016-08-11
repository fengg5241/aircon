/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Area;

/**
 * @author simanchal.patra
 *
 */
@Service
public class AreaDAOServiceImpl implements AreaDAOservice {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(AreaDAOServiceImpl.class);

	private GenericDAO<Area> dao;

	private Lock reEntrantLock = new ReentrantLock();

	@Autowired
	public void setDAO(GenericDAO<Area> daoToSet) {
		logger.debug("daoToSet " + daoToSet);
		dao = daoToSet;
		dao.setClazz(Area.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#create
	 * (java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public Object create(Area entity) throws BusinessFailureException {
		boolean locked = false;
		Long id = null;

		try {
			locked = reEntrantLock.tryLock();

			if (locked) {

				Criteria dgCriteria = dao.createCriteria();
				dgCriteria.add(Restrictions.eq("distributionGroup",
						entity.getDistributionGroup()));
				dgCriteria.add(Restrictions.ilike("name", entity.getName(),
						MatchMode.EXACT));

				List<?> areaList = dgCriteria.list();

				if (areaList != null && areaList.isEmpty()) {
					id = dao.create(entity);
				} else {
					throw new BusinessFailureException("area.exists.in.dg");
				}
			}
		} catch (Exception e) {
			logger.error("Error occured while creating new area with name "
					+ entity.getName());
		} finally {
			if (locked) {
				reEntrantLock.unlock();
			}
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * saveOrUpdate(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void saveOrUpdate(Area entity) {
		// Due to restriction in
		throw new UnsupportedOperationException(
				"Please use either Save or Update");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findAll
	 * ()
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<Area> findAll() {
		return dao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#update
	 * (java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void update(Area entity) throws BusinessFailureException {

		boolean locked = false;

		if (entity.getId() == null) {
			logger.info("Area with ID null cannot be updated, name - "
					+ entity.getName());
			throw new NullPointerException(
					"Area with ID null cannot be updated, name - "
							+ entity.getName());
		}

		try {
			locked = reEntrantLock.tryLock();

			if (locked) {

				Criteria dgCriteria = dao.createCriteria();
				dgCriteria.add(Restrictions.eq("distributionGroup",
						entity.getDistributionGroup()));
				dgCriteria.add(Restrictions.ilike("name", entity.getName()));
				dgCriteria.add(Restrictions.ne("id", entity.getId()));

				List<?> areaList = dgCriteria.list();

				if (areaList != null && areaList.isEmpty()) {
					dao.update(entity);
				} else {
					throw new BusinessFailureException("area.exists.in.dg");
				}
			} else {
				throw new BusinessFailureException("Some error ocurred");
			}
		} catch (Exception e) {
			logger.error("Error occured while updating area with name "
					+ entity.getName());
		} finally {
			if (locked) {
				reEntrantLock.unlock();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#delete
	 * (java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void delete(Area entity) throws BusinessFailureException {

		boolean locked = false;

		try {
			if (entity != null) {
				if (entity.getId() == null) {

					Criteria dgCriteria = dao.createCriteria();
					dgCriteria.add(Restrictions.eq("distributionGroup",
							entity.getDistributionGroup()));
					dgCriteria
							.add(Restrictions.ilike("name", entity.getName()));

					List<?> areaList = dgCriteria.list();

					if (areaList != null && !areaList.isEmpty()) {
						entity = (Area) areaList.get(0);
					} else {
						entity = null;
					}
				} else {
					Criteria dgCriteria = dao.createCriteria();
					dgCriteria.add(Restrictions.eq("id", entity.getId()));

					List<?> areaList = dgCriteria.list();

					if (areaList != null && !areaList.isEmpty()) {
						entity = (Area) areaList.get(0);
					} else {
						entity = null;
					}
				}

				locked = reEntrantLock.tryLock();

				if (locked) {

					dao.delete(entity);
				} else {
					throw new BusinessFailureException("not.able.to.get.lock");
				}
			} else {
				throw new BusinessFailureException("area.not.found");
			}
		} catch (Exception e) {
			logger.error("Error occured while deleting area" + entity != null ? "with name "
					+ entity.getName()
					: ", which is not available");
		} finally {
			if (locked) {
				reEntrantLock.unlock();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * deleteById(long)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void deleteById(long entityId) {
		Area area = new Area();
		area.setId(entityId);
		dao.delete(area);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findByID
	 * (long)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public Area findByID(long id) {

		return dao.findByID(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.lang.String, java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<Area> findAllByProperty(String propertyName, Object value) {
		return dao.findAllByProperty(propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.util.HashMap)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<Area> findAllByProperty(HashMap<String, Object> properties)
			throws HibernateException, OperationNotSupportedException {
		return dao.findAllByProperties(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findBySortCriteria(java.util.Map, java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<Area> findBySortCriteria(Map<String, Object> criteriaMap,
			String orderBy, String orderByPropertyName) {
		// return dao
		// .findBySortCriteria(criteriaMap, orderBy, orderByPropertyName);
		throw new UnsupportedOperationException();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void merge(Area entity) throws HibernateException {
		dao.merge(entity);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public Criteria createCriteria() {
		return dao.createCriteria();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void batchSaveOrUpdate(List<Area> entities)
			throws HibernateException {
		dao.batchSaveOrUpdate(entities);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void batchDelete(List<Area> entities) throws HibernateException {
		dao.batchDelete(entities);
	}

}

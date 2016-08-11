package com.panasonic.b2bacns.bizportal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author shobhit.singh
 * */
@Service
public class IndoorUnitsServiceImpl implements IndoorUnitsService {

	private static final String HQL_GET_IDUS_IN = "from Indoorunit idu where idu.id IN (:ids)";
	private GenericDAO<Indoorunit> dao;

	@Autowired
	public void setDao(GenericDAO<Indoorunit> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Indoorunit.class);
	}

	@Resource(name = "properties")
	private Properties bizProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.IndoorUnitsService#
	 * getIndoorunitListByProperties(java.util.Map)
	 */
	@Override
	@Transactional
	public List<Indoorunit> getIndoorunitListByProperties(
			Map<String, Object> properties) throws HibernateException {
		return dao.findAllByProperties(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.IndoorUnitsService#getIndoorunitById
	 * (long)
	 */
	@Override
	@Transactional
	public Indoorunit getIndoorunitById(long id) throws HibernateException {
		return dao.findByID(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.IndoorUnitsService#getIndoorunitByID
	 * (long)
	 */
	@Override
	public Indoorunit getIndoorunitByID(long indoorUnitsId)
			throws HibernateException {
		return dao.findByID(indoorUnitsId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.IndoorUnitsLogsService#
	 * getLatestIndoorUnitLogById(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Indoorunit> getIndoorUnitsByIds(List<Long> iduIds)
			throws HibernateException {

		Query query = dao.executeHQLQuery(HQL_GET_IDUS_IN);
		query.setParameterList("ids", iduIds);
		List<Indoorunit> indoorunits = query.list();

		return indoorunits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Indoorunit> getIndoorUnitsBatchByIds(List<Long> iduIds)
			throws HibernateException {

		List<Indoorunit> indoorFinalUnitList = new ArrayList<Indoorunit>();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BizConstants.IDS, iduIds);

		Query query = (Query) dao.executeHQLQuery(HQL_GET_IDUS_IN, queryMap);
		query.setFetchSize(BizConstants.SELECT_BATCH_SIZE);
		List<Indoorunit> indoorunits = query.list();

		if (indoorunits != null && !indoorunits.isEmpty()) {
			indoorFinalUnitList.addAll(indoorunits);
		}

		return indoorFinalUnitList;
	}

	@Override
	public Criteria getCriteria() {
		return dao.createCriteria();
	}

	@Override
	public void saveOrUpdate(Indoorunit entity) {
		dao.saveOrUpdate(entity);
	}

	@Override
	public void batchSaveOrUpdate(List<Indoorunit> entityList) {
		dao.batchSaveOrUpdate(entityList);
	}

}

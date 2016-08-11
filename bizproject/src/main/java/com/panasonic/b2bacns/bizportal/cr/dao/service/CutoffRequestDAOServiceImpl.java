/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.cr.vo.PowerDistHeader;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.persistence.CutoffRequest;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author simanchal.patra
 *
 */
@Service
public class CutoffRequestDAOServiceImpl implements CutoffRequestDAOservice {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(CutoffRequestDAOServiceImpl.class);

	private GenericDAO<CutoffRequest> dao;

	@Autowired
	public void setDAO(GenericDAO<CutoffRequest> daoToSet) {
		logger.debug("daoToSet " + daoToSet);
		dao = daoToSet;
		dao.setClazz(CutoffRequest.class);
	}

	@Autowired
	private SQLDAO sqldao;
	//Modified by Ravi
	StringBuilder GET_POWER_DIST_HEADER = new StringBuilder(
			"select g.name as site,g.id as groupid ,cr.fromdate,cr.todate,tzm.timezone "
					+ "from cutoff_request cr left outer join cutoff_request_transactions crd on cr.id = crd.ctfreq_id "
					+ "Left Outer join groups g on crd.siteid = g.id "
					+ "Left Outer join adapters a on g.uniqueid = a.siteid Left Outer join timezonemaster tzm on a.timezone = tzm.id "
					+ "where cr.platformtransaction_id  = %d group by g.name, g.id, cr.fromdate, cr.todate, tzm.timezone");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#create
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public Object create(CutoffRequest entity) {

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
	public void saveOrUpdate(CutoffRequest entity) {
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
	public List<CutoffRequest> findAll() {

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
	public void update(CutoffRequest entity) {
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
	public void delete(CutoffRequest entity) {
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
	public CutoffRequest findByID(long id) {

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
	public List<CutoffRequest> findAllByProperty(String propertyName,
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
	public List<CutoffRequest> findAllByProperty(
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
	public List<CutoffRequest> findBySortCriteria(
			Map<String, Object> criteriaMap, String orderBy,
			String orderByPropertyName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void merge(CutoffRequest entity) throws HibernateException {
		dao.merge(entity);
	}

	@Override
	public Criteria createCriteria() {
		return dao.createCriteria();
	}

	@Override
	public void batchSaveOrUpdate(List<CutoffRequest> entities)
			throws HibernateException {

		dao.batchSaveOrUpdate(entities);
	}

	@Override
	public void batchDelete(List<CutoffRequest> entities)
			throws HibernateException {
		dao.batchDelete(entities);
	}

	@Override
	public PowerDistHeader getPowerDistHeader(Long transactionId) {

		String query = String.format(GET_POWER_DIST_HEADER.toString(),
				transactionId);

		PowerDistHeader distHeader = new PowerDistHeader();

		List<?> resultList = sqldao.executeSQLSelect(query);

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

				distHeader.setFromDate(String.valueOf(rowData[2]));
				distHeader.setToDate(String.valueOf(rowData[3]));
				distHeader.setSite(distHeader.getSite() != null ? distHeader
						.getSite()
						+ BizConstants.COMMA_STRING
						+ String.valueOf(rowData[0]) : String
						.valueOf(rowData[0]));
				//Added by Ravi
				distHeader.setTimezone(String.valueOf(rowData[4]));

			}

			distHeader.setSite('"' + distHeader.getSite() + '"');
		}
		return distHeader;
	}

}

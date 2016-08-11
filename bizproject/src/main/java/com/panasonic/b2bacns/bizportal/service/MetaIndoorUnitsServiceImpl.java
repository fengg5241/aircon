package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;

import com.panasonic.b2bacns.bizportal.persistence.Metaindoorunit;

/**
 * DAO Service class for metaindoorunits
 * @author jwchan
 * 
 */
@Service
public class MetaIndoorUnitsServiceImpl  implements MetaIndoorUnitsService{


	private GenericDAO<Metaindoorunit> dao;

	@Autowired
	public void setDao(GenericDAO<Metaindoorunit> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Metaindoorunit.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean addMetaIndoorUnits(Metaindoorunit metaindoorunit) {

		dao.create(metaindoorunit);
		return true;
		//return dao.create(metaindoorunit);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorUnitsService#
	 * getOutdoorunitListByProperties(java.util.Map)
	 */
	@Override
	public List<Metaindoorunit> getMetaIndoorUnitListByProperties(
			Map<String, Object> properties) throws HibernateException {
		return dao.findAllByProperties(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.OutdoorUnitsService#getOutdoorUnitById
	 * (long)
	 */
	@Override
	public Metaindoorunit getMetaIndoorUnitById(long id) throws HibernateException {
		return dao.findByID(id);
	}
	
}

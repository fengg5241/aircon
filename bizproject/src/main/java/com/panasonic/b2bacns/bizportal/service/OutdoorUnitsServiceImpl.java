package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Outdoorunit;
import com.panasonic.b2bacns.bizportal.service.OutdoorUnitsService;

@Service
public class OutdoorUnitsServiceImpl  implements OutdoorUnitsService{


	private GenericDAO<Outdoorunit> dao;

	@Autowired
	public void setDao(GenericDAO<Outdoorunit> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Outdoorunit.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorUnitsService#
	 * getOutdoorunitListByProperties(java.util.Map)
	 */
	@Override
	public List<Outdoorunit> getOutdoorunitListByProperties(
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
	public Outdoorunit getOutdoorUnitById(long id) throws HibernateException {
		return dao.findByID(id);
	}
	
}

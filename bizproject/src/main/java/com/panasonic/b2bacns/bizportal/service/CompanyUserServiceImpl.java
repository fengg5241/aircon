package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;

/**
 * @author Sumit Kaushik
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CompanyUserServiceImpl implements CompanyUserService {

	private GenericDAO<Companiesuser> dao;

	@Autowired
	public void setDao(GenericDAO<Companiesuser> daoToSet) {
		dao = (GenericDAO<Companiesuser>) daoToSet;
		dao.setClazz(Companiesuser.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.CompanyUserService#
	 * findAllByProperties(java.util.Map)
	 */
	@Override
	@Transactional
	public List<Companiesuser> findAllByProperties(
			Map<String, Object> properties) {

		return dao.findAllByProperties(properties);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.CompanyUserService#
	 * findGroupFromCompaniesuserByUser(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Companiesuser> findGroupFromCompaniesuserByUser(long userId) {
		String hqlQuery = "from Companiesuser as cu  where cu.user.id="
				+ userId;

		return dao.executeHQLQuery(hqlQuery).list();
	}
}

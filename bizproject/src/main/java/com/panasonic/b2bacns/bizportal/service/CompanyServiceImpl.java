package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Company;

/**
 * @author Sumit Kaushik
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	private GenericDAO<Company> dao;

	@Autowired
	public void setDao(GenericDAO<Company> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Company.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean addCompany(Company company) {

		dao.create(company);
		return true;
	}

	@Override
	public boolean updateCompany(Company company) {

		dao.update(company);
		return true;
	}

	@Override
	@Transactional
	public List<Company> listCompanies() {
		return dao.findAll();

	}

	@Override
	@Transactional
	public Company getCompany(long companyId) {
		return dao.findByID(companyId);
		// return companyDao.getCompany(companyId);
	}

	@Override
	@Transactional
	public void deleteCompany(Company company) {

		dao.delete(getCompany(company.getId()));

	}

}

package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.persistence.Company;

/**
 * @author Sumit Kaushik
 *
 */
public interface CompanyService {

	public boolean addCompany(Company company);

	public boolean updateCompany(Company company);

	public List<Company> listCompanies();

	public Company getCompany(long companyId);

	public void deleteCompany(Company company);

}

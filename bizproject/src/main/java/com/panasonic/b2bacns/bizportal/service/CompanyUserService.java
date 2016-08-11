package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;

public interface CompanyUserService {

	/**
	 * get list of CompanyUser object as per given criteria in the map object
	 * 
	 * @param properties
	 * @return
	 */
	public List<Companiesuser> findAllByProperties(
			Map<String, Object> properties);

	/**
	 * get all groups
	 * 
	 * @param userId
	 * @return
	 */
	public List<Companiesuser> findGroupFromCompaniesuserByUser(long userId);

}

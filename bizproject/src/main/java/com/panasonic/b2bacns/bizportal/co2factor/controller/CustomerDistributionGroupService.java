package com.panasonic.b2bacns.bizportal.co2factor.controller;

import java.util.List;

import org.springframework.stereotype.Service;


/**
 * @author Pramod
 *
 */
@Service
public interface CustomerDistributionGroupService {

	public String create_new_DistributionGroup(String DistributionName,int CostomerId,String type,String calMode);
	
	@SuppressWarnings("rawtypes")
	public List Distributin_group(int CostomerorId);
	
	public List<?> executeSQLSelect(final String sqlQuery);
	
	public List getCustomerSiteIdDistributionGroup(int CostomerorId);
	
	
	public int getCustomerIdCustomerDistribution(int CostomerorId);
	
	
}

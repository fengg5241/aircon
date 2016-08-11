package com.panasonic.b2bacns.bizportal.custmanagement.controller;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;

/**
 * 
 * 
 * @author Srinivas
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Service(value="Cust_DataService")
public class Cust_DataServiceImpl implements Cust_DataService {
	@Autowired
	private SQLDAO sqlDao;

	
	@Autowired
	private GenericDAO<Company> dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Long create_new_cust(String cust_name,Timestamp creationdate,String country, String address,String postal_code,Integer status){
		Company customer = new Company();
		customer.setName(cust_name);
		customer.setCreationdate(creationdate);
		customer.setCountry(country);
		customer.setAddress(address);
		customer.setPostal_code(postal_code);
		customer.setStatus(status);
		System.out.println(customer);
		dao.create(customer);	
		return customer.getId();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean register_cust_siteid(List<Integer> site_id, String customer_name, Long userId){
		
		return sqlDao.register_dao_cust_siteid(site_id, customer_name, userId);

	}
	
	@Override
	public List show_cust(){
		
		return sqlDao.show_cust();
	}
	
	
	
	@Override
	public List Fetch_siteid(String cust_id){
		
		return sqlDao.Fetch_siteid(cust_id);
	}
	
	@Override
	public List Fetch_AllSites(List site_ids,int CustomerID)
	{
		
		return sqlDao.Fetch_AllSites(site_ids,CustomerID);
	}
	
	@Override
	public List Fetch_AllSiteNames(int CustomerID)
	{
		
		return sqlDao.Fetch_AllSiteNames(CustomerID);
	}
	
	
	


}

package com.panasonic.b2bacns.bizportal.custmanagement.controller;

import java.sql.Timestamp;
import java.util.List;


/**
 * 
 * 
 * @author Srinivas
 * 
 */
public interface Cust_DataService {
	
	public Long create_new_cust(String cust_name,Timestamp creationdate,String country, String address,String postal_code,Integer status);
	public List show_cust();
	public List Fetch_siteid(String cust_id);
	public boolean register_cust_siteid(List<Integer> site_id, String customer_name, Long userId);
	public List Fetch_AllSites(List site_ids,int CustomerID);
	public List Fetch_AllSiteNames(int CustomerID);
	
	

}

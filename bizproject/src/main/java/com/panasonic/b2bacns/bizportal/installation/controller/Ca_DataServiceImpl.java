package com.panasonic.b2bacns.bizportal.installation.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
/**
*
* 
* @author Srinivas
* 
*/
@Service(value="Ca_DataService")
	public class Ca_DataServiceImpl implements Ca_DataService {
	
	@Autowired
	private SQLDAO sqlDao;
	
	@Override
	public List Show_Ca_data(String ca_mac) {
		
		return sqlDao.Show_Ca_data(ca_mac);
		
	}
	
	@Override
	public List Fetch_mac_Address(String cust_id){
		
		return sqlDao.Fetch_mac_Address(cust_id);
	}
	
	@Override
	public List Fetch_mac_Address_reg(String cust_id)
	{
		return sqlDao.Fetch_mac_Address_reg(cust_id);
	}
	
	@Override
	public List Fetch_siteidcust(String cust_id){
		return sqlDao.Fetch_siteidcust(cust_id);
	}
	
	@Override
	public List Fetch_caModel(){
		
		return sqlDao.Fetch_caModel();
	}
	
	@Override
	public List show_cust_adapters(){
		
		return sqlDao.show_cust_adapters();
	}
	
	
	@Override

	public List get_roleTypeId(Long User_id){
		
		return sqlDao.get_roleTypeId(User_id);

	}
	

	@Override

	public List show_cust_names(List cust_ids){
		
		return sqlDao.show_cust_names(cust_ids);
	}
	
	

	@Override

	public List show_site_customers(List site_ids){
		 
		return sqlDao.show_site_customers(site_ids);
	 }
	
	
	@Override
	public List Show_Timzones(){
		
		return sqlDao.Show_Timzones();
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List register_ca(String ca_mac_address_reg,String ca_model,Long company_id,String User_id){

		return sqlDao.Insert_caRegister(ca_mac_address_reg,ca_model,company_id,User_id);
	}

	@Override
	public int  Insert_Facility_id(String Facility_Id,Object Inserted_Ca_id_value){
		
		return sqlDao.Insert_Adapter_facility(Facility_Id,Inserted_Ca_id_value);
	}
	
	@Override
	public List  delete_ca(Integer Inserted_Ca_id_value){
		
		return sqlDao.delete_ca(Inserted_Ca_id_value);
	}

	@Override
	public int associate_ca_siteid(String ca_mac_address,String site_id,Integer Timezone,String Latitude,String Longitude,
			Timestamp installed_at,String status,String ca_name) throws ParseException
	{
		return sqlDao.Insert_caAssociate(ca_mac_address,site_id,Timezone,Latitude,Longitude,installed_at,status,ca_name);
		
	}
	
	@Override
	public boolean check_ca_duplicate(String site_id,String cust_name,String ca_name)
	{
		return sqlDao.check_ca_duplicate(site_id,cust_name,ca_name);
	}

	public List show_cust_names_operator(Long User_id) {
		return sqlDao.show_cust_names_operator(User_id);

	}
	
}

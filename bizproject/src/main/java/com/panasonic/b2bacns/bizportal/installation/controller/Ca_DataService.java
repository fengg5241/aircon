package com.panasonic.b2bacns.bizportal.installation.controller;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

/**
 *
 * 
 * @author Srinivas
 * 
 */
public interface Ca_DataService {
	public List Show_Ca_data(String ca_mac);
	public List Fetch_mac_Address(String cust_id);
	public List Fetch_mac_Address_reg(String cust_id);
	public List Fetch_siteidcust(String cust_id);
	public List Fetch_caModel();
	public List show_cust_adapters();
	public List show_cust_names(List cust_ids);
	public List show_cust_names_operator(Long User_id);
	public List get_roleTypeId(Long User_id);
	public List show_site_customers(List site_ids);
	public List Show_Timzones();
	public List register_ca(String ca_mac_address_reg,String ca_model,Long company_id,String User_id);
	public List delete_ca(Integer inserted_Ca_id_value);
	public int  Insert_Facility_id(String Facility_Id,Object inserted_Ca_id_value);
	public int associate_ca_siteid(String ca_mac_address,String site_id,Integer Timezone,String Latitude,String Longitude,
			Timestamp installed_at,String status,String ca_name) throws ParseException;
	
	public boolean check_ca_duplicate(String site_id,String cust_name,String ca_name);


}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.type.Type;

import com.panasonic.b2bacns.bizportal.group.vo.IDUListVO;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupIduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupPlsData;
import com.panasonic.b2bacns.bizportal.topology.grouping.IduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.OduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.Topology;

/**
 * This Interface allows to add methods specific to modules which requires
 * execution SQL queries to achieve the functionality and provides Hibernate
 * interfaces to execute those SQL queries.
 * 
 * @author akansha
 * 
 */

public interface SQLDAO {

	/**
	 * Execute SQL select query
	 * 
	 * @param sqlQuery
	 * @return
	 */
	public List<?> executeSQLSelect(final String sqlQuery);

	/**
	 * Execute SQL select query
	 * 
	 * @param sqlQuery
	 * @param parameter
	 * @return
	 */
	public List<?> executeSQLSelect(String sqlQuery,
			Map<String, Object> parameter);

	/**
	 * @Auther Srnivas
	 * @param sqlQuery
	 * @return
	 */
	public List executeInsertSQLSelect(final String sqlQuery);

	/**
	 * Execute SQL select query
	 * 
	 * @param sqlQuery
	 * @param scalarMapping
	 * @return
	 */
	public List<?> executeSQLSelect(final String sqlQuery,
			LinkedHashMap<String, Type> scalarMapping);

	/**
	 * Execute SQL select query
	 * 
	 * @param sqlQuery
	 * @param scalarMapping
	 * @param parameter
	 * @return
	 */
	public List<?> executeSQLSelect(String sqlQuery,
			LinkedHashMap<String, Type> scalarMapping,
			Map<String, Object> parameter);

	/**
	 * Provides value of parameter for ODU
	 * 
	 * @param id
	 *            The Id of Entity
	 * @param parameterNames
	 *            The List of parameter names
	 * @param idType
	 *            The idType of Entity
	 * @return
	 */
	public Object[] getParameters(Long id, String parameterNames, String idType);

	/**
	 * To get list af all IDUs in group(s)
	 * 
	 * @param groupIds
	 * @return
	 */
	public List<IDUListVO> getIDUList(List<Long> groupIds);

	/**
	 * Execute SQL update query
	 * 
	 * @param sqlQuery
	 * @throws HibernateException
	 */
	public int executeSQLUpdateQuery(final String sqlQuery)
			throws HibernateException;

	/**
	 * Execute Hibernate query
	 * 
	 * @param hqlQuery
	 * @return
	 * @throws HibernateException
	 */
	public List<?> executeHQLQuery(String hqlQuery) throws HibernateException;

	/**
	 * Execute SQL update query
	 * 
	 * @param sqlQuery
	 * @param parameter
	 * @return
	 * @throws HibernateException
	 */
	public int executeSQLUpdateQuery(String sqlQuery,
			Map<String, Object> parameter) throws HibernateException;

	public int executeSQLUpdateQuery(String query, Map<String, Object> params,
			LinkedHashMap<String, Type> scalarMapping);

	/**
	*
	* 
	* @author Srinivas
	* 
	*/
	
	public List Show_Ca_data(String my_id);
	public List Fetch_mac_Address(String cust_id);
	public List Fetch_mac_Address_reg(String cust_id);
	public List Fetch_siteid(String cust_id);
	public List Fetch_AllSites(List site_ids,int CustomerID);
	
	public List Fetch_AllSiteNames(int CustomerId);
	public List Fetch_caModel();
	public List Fetch_siteidcust(String cust_id);
	public List show_cust_adapters();
	public List show_cust_names(List cust_ids);
	public List get_roleTypeId(Long User_id);

	public List show_cust_names_operator(Long User_id);
	
	public List show_site_customers(List site_ids);
	public List Show_Timzones();
	public List create_new_cust(String cust_name,String creationdate);
	public List show_cust();
	public List Insert_caRegister(String ca_mac_address_reg,String ca_model,Long company_id,String User_id);
	public int Insert_Adapter_facility(String Facility_Id,Object Inserted_Ca_id_value);
	public List delete_ca(Integer Inserted_Ca_id_value);
	
	public int Insert_caAssociate(String ca_mac_address,String site_id,Integer Timezone,String Latitude,String Longitude,
			Timestamp installed_at,String status,String ca_name) throws ParseException;
	
	public boolean check_ca_duplicate(String site_id,String cust_name,String ca_name);
	
	public boolean register_dao_cust_siteid(List<Integer> site_id, String customer_name, Long userId);
	
	
	//Retrieve topology and distribution group methods
	
	@SuppressWarnings("rawtypes")
	public List Costomer(List cust_ids);
	@SuppressWarnings("rawtypes")
	public List getSiteIdTopolgy(List siteId);
	@SuppressWarnings("rawtypes")
	public List CaAddress(int siteid,int cust_id);
	
	public List CaAddressDistributionGroup(List siteid,int cust_id);
	
	/*@SuppressWarnings("rawtypes")
	public List saveRetrieveToplogyIdu(List<IduData> idutoplogy,String adapterid);*/
	
/*	@SuppressWarnings("rawtypes")
	public List saveRetrieveToplogyOdu(List<OutdoorUintPf> odutoplogy,String adapterName);*/
	
	//@SuppressWarnings("rawtypes")
	//public List saveRetrieveToplogyPls(List<PulseMeterPf> plstoplogy,String adapterName);
	
	//public List UpdateRetrieveToplogyPls(List<Topology> IDU,int adapter_id,String site_id);
	public List UpdateRetrieveToplogyIdu(List<IduData> PLS,int adapter_id,String site_id);
	
	public boolean InsertRetrieveToplogy(List<OduData> list_1 ,List<IduData> list_2 ,List<Topology> list_3 ,int adapter_id,String site_id);
	
	
	public Boolean UpdateRetrieveToplogy(List<OduData> list_1 ,List<IduData> list_2 ,List<Topology> list_3);

	
	

	
	public List UpdateRetrieveToplogyOdu(List<OduData> ODU,int adapter_id,String site_id);
	
	public List DistributionGroup(int CostomerId);
	
	public List DisplayDistrbutionGroup(int DistributionGroupid);
	
	public Map<String, List<Object>> listDistributionGroupDropDownData(int cust_id);
	
	
	public List getDistributionGroupPls(List adapterid);
	
	public List getDistributionGroupIdu(List adapterid);
	
	public Map<Integer, String> UpdateDistributionGroupIdu(List<DistributionGroupIduData> idu);
	
	public List UpdateDistributionGroupPls();
	public List UpdateDistributionGroupPlsAndIdUMerging(List<DistributionGroupPlsData> pls,Map<Integer, String> GroupByFid);
	
	public List RetrieveAllTopolgy(String site_id,String cloud_adapter_id,String costomer_id);
	
	public String create_new_DistributionGroup(String DistributionName,int CostomerId,String type,String calMode);
	
	public String delete_DistributionGroup_data(Long DistributionGroupId,int costomer);
	
	public String getCalCode(int DistributionGroupId);
	/*
	 * 
	 * @author pramod
	 * 
	 */
	public void updatePlusmeterTableByDisGroup(String id, String facilityId,
			int dist_grp);

	public void updateIduTableByDisGroup(String id, String facilityId,
			int dist_grp);
	
	
	
}

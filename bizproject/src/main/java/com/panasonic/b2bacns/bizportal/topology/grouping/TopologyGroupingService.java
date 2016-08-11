package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public interface TopologyGroupingService {
	
	
	
	@SuppressWarnings("rawtypes")
	public List getCostomer(List cust_ids);
	
	@SuppressWarnings("rawtypes")
	public List getSite(List siteid);
	@SuppressWarnings("rawtypes")
	public List CaAddress(int site_id,int cust_id);
	
	public List CaAddressDistributionGroup(List site_id,int cust_id);
	
	
	
	
	public List RetrieveAllTopolgy(String site_id,String cloud_adapter_id,String costomer_id);
	
	public List<IndoorUnitPlatForm> RetrieveTopolgyIDU(String siteid,String adapterName,String costomer_id) ;
	
	//public List<IndoorUnitPlatForm> RetrieveTopolgyIDUStoreData(List<IndoorUnitPlatForm> idutoplogy,String adapterName) ;
	
	public List<OutdoorUintPf> RetrieveTopolgyODU(String siteid,String adapterName,String costomer_id) ;
	
	//public List<OutdoorUintPf> RetrieveTopolgyODUStoreData(List<OutdoorUintPf> odutoplogy,String adapterName) ;
	
	public List<PulseMeterPf> RetrieveTopolgyPlS(String siteid,String adapterName,String costomer_id) ;
	//public List<PulseMeterPf> RetrieveTopolgyPLSStoreData(List<PulseMeterPf> plstoplogy,String adapterName) ;
	
	/*
	@SuppressWarnings("rawtypes")
	public List UpdateRetrieveTopolgyPls(List<Topology> topologys,int adapter_id,String site_id) ;*/
	@SuppressWarnings("rawtypes")
	public List UpdateRetrieveTopolgyIdu(List<IduData> list_1 ,int adapter_id,String site_id) ;
	
	@SuppressWarnings("rawtypes")
	public Boolean InsertRetrieveToplogy(List<OduData> list_1 ,List<IduData> list_2 ,List<Topology> list_3 ,int adapter_id,String site_id ) ;
	
	
	@SuppressWarnings("rawtypes")
	public Boolean UpdateRetrieveToplogy(List<OduData> list_1 ,List<IduData> list_2 ,List<Topology> list_3) ;
	
	//UpdateRetrieveToplogy(list_1,list_2,list_3);
	
	@SuppressWarnings("rawtypes")
	public List UpdateRetrieveTopolgyOdu(List<OduData> Odu,int adapter_id,String site_id) ;
	
	public List Distributin_group(int CostomerorId);
	
	public List getdDistributionData(int groupId);
	
	public Map<String, List<Object>> getdDistributionDropDownData(int cust_id);
	
	public List getdDistributionDataPLs(List siteid);
	
	
	public List getdDistributionDataIdu(List siteid);
	
	public List updateDistributionGroupData(List<DistributionGroupIduData> Idu,int customerid,String userid);
	
	
	
	public List updateDistributionGroupPlsData(List<DistributionGroupPlsData> Pks,List<DistributionGroupIduData> Idu,int customerid,String userid);
	
	public List getDistributionGroupId();
	public List updateDistributionGroupPlsuDataPlatForm(int customerid ,List fid,String userid);
	
	public String Add_Distributin_group_Data(String DistributionName,int DistributionGroupId,String type,String calMode);
	
	public String updateDistributionGroupIduDataPlatForm(int customerid ,String DistributiongroupId,List <String>  Plsfid,List <String> Idusfid, String user_id,List<DistributionGroupIduData> disGroupList);
	
	public String Delete_Distributin_group_Data(Long DistributionGroupId,int costomer);
	
	

	/*
	 * 
	 * @author pramod
	 * 
	 */
	public String updateTableRelatedToDisGroup(
			List<DistributionGroupIduData> disGroupList);

}

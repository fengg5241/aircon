package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.cr.service.ManageAreaAllocationService;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.persistence.DistributionGroup;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.spf.b2bac.dataaggregation.DataAggregationException;
import com.panasonic.spf.b2bac.dataaggregation.api.DistributionGroupManager;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.DeleteDistributionGroupParam;

@Service(value="TopologyGroupSqlDao")
public class TopologyGroupSqlDaoImpl implements TopologyGroupSqlDao {

	private static final Logger logger = Logger
			.getLogger(TopologyGroupSqlDaoImpl.class);

	
	private GenericDAO<DistributionGroup> dao;
	@Autowired
	public void setDao(GenericDAO<DistributionGroup> daoToSet) {
		dao = daoToSet;
		dao.setClazz(DistributionGroup.class);
	}

	@Autowired
	private ManageAreaAllocationService areaAllocationService;
	
	
	
	
	
	
	@Override
	public String delete_DistributionGroup_data(String DistributionGroupId,
			String costomer,String userid) {
		
		List<String> resultList_area = new ArrayList<String>();
		List<String> resultList_idu_dst = new ArrayList<String>();
		
		String sql_distribution_idus = "SELECT id  FROM indoorunits  where distribution_group_id = " + DistributionGroupId;
		 resultList_idu_dst=(List<String>) dao.executeSQLQuery(sql_distribution_idus);
		
		if (resultList_idu_dst.size()!=0) {
			try {
	            DeleteDistributionGroupParam param = new DeleteDistributionGroupParam();
	            String userId = costomer;
	            
	            param.customerId = costomer;
	            param.distributionGroupId = DistributionGroupId;
	            DistributionGroupManager distributionGroupManager = new DistributionGroupManager();
	           distributionGroupManager.deleteDistributionGroup(param, userId);
	            
	            String sql_area = "SELECT name  FROM area where distribution_group_id = " + DistributionGroupId;
	            resultList_area=(List<String>) dao.executeSQLQuery(sql_area);
	            @SuppressWarnings("rawtypes")
				Iterator itr1 = resultList_area.iterator();
	    		String[] rowData = null;
	            
	            if(resultList_area.size()!=0){
	            	
	            	for (int i = 0; i < resultList_area.size(); i++) {            		            		
	            		System.out.println(resultList_area.get(i));
	            	 @SuppressWarnings("unused")
	            	 String area_name=resultList_area.get(i);
	            	 int Distribution_Group = Integer.valueOf(DistributionGroupId);				
	 				Long l3 = new Long(Distribution_Group);        	 
	            	areaAllocationService.removeArea(null, l3 , area_name);           		
					}        	
	            }       
	            String sql = "UPDATE pulse_meter SET distribution_group_id = NULL where distribution_group_id = " + DistributionGroupId;
	    		dao.executeSQLUpdateQuery(sql);
	    		
	    		String sql1 = "UPDATE indoorunits SET distribution_group_id = NULL where distribution_group_id = " + DistributionGroupId;
	     		dao.executeSQLUpdateQuery(sql1);
	     		
	     		String sql2 = "delete from distribution_group  where id = " + DistributionGroupId;
	     		dao.executeSQLUpdateQuery(sql2);    
			 }catch (DataAggregationException daExp) {
				 daExp.printStackTrace();
				 logger.error("Error: while deleting the  distribution group ", daExp);
				 return CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
				
		        }
			
			catch (Exception exp) {
	        	exp.printStackTrace();
	        	 logger.error("Error: while deleting the  distribution group ", exp);
	        	 return CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
	        }
		} else {
			String sql2 = "delete from distribution_group  where id = " + DistributionGroupId;
     		dao.executeSQLUpdateQuery(sql2); 
		}
		
		
		return CommonUtil.getJSONErrorMessage(BizConstants.SUCCESS_REGISTRATION);
	}
	
	private GenericDAO<DistributionGroup> dao1;
	@Autowired
	public void setDao1(GenericDAO<DistributionGroup> daoToSet) {
		dao1 = daoToSet;
		dao1.setClazz(DistributionGroup.class);
	}
	
	@SuppressWarnings("unused")
	@Override
	public String create_new_DistributionGroup(String DistributionName,
			int CostomerId, String type, String calMode) {
		List<?> resultList = null;
		String groupname="";
		Integer duplictaeName=null;	
		String sql = "select group_name  from distribution_group where  customer_id='"+CostomerId+"' ";
		resultList= dao.executeSQLQuery(sql);	
		for(int i = 0; i < resultList.size(); i++){
			String existingname =(String) resultList.get(i);
			if(DistributionName.equalsIgnoreCase(existingname)){			
				duplictaeName=1;		
			}else{
				System.out.println("duplicate name is not their");		
			}	
		}			
		if (duplictaeName==null) {
			DistributionGroup dst = new DistributionGroup();
			
			Company c = new Company();
			c.setId((long)CostomerId);
			
			dst.setGroupName(DistributionName);
			dst.setCompany(c);
			dst.setType(type);
			dst.setTypeMeasurment(calMode);
			dao1.create(dst);	
			dao1.sessionFlush();
			 groupname=dst.getGroupName();
		} else {
			return CommonUtil.getJSONErrorMessage(BizConstants.DUPLICATE_REGISTRATION);
		}
		
		
		return CommonUtil.getJSONErrorMessage(BizConstants.SUCCESS_REGISTRATION);
	}

	

}

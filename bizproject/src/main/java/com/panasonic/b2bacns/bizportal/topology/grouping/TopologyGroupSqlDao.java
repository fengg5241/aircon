package com.panasonic.b2bacns.bizportal.topology.grouping;

import org.springframework.stereotype.Service;


@Service
public interface TopologyGroupSqlDao {
	
	
	public String delete_DistributionGroup_data(String DistributionGroupId,String costomer,String userid);
	
	public String create_new_DistributionGroup(String DistributionName,int CostomerId,String type,String calMode);
}

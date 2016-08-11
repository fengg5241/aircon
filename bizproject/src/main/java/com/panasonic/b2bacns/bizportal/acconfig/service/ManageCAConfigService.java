/**
 * 
 */
package com.panasonic.b2bacns.bizportal.acconfig.service;

import java.util.List;
import java.util.Set;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.CAConfigVO;

/**
 * @author jwchan
 * 
 */
public interface ManageCAConfigService {


	/**
	 * Provides the AC Configuration Details for a Group or an IDU or an ODU .
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public List<CAConfigVO> getCAConfiguration(ACConfigRequest acConfigRequest);
	public Set<CAConfigVO> getCAConfigurationSet(ACConfigRequest acConfigRequest);
	/**
	 * Provides the AC Configuration Details for a Group or an IDU or an ODU .
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public List<CAConfigVO> getCAConfigurationByMac(ACConfigRequest acConfigRequest);
	
	/**
	 * Provides the AC Configuration Details for a Group or an IDU or an ODU .
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public Set<ACConfigODUVO> getODUConfigurationByMac(ACConfigRequest acConfigRequest);
	public Set<ACConfigODUVO> getODUConfigurationByMac2(ACConfigRequest acConfigRequest);
	
	
	
	/**
	 * Provides the AC Configuration Details for a Group or an IDU or an ODU .
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public ACConfigIDUVO getACConfiguration(ACConfigRequest acConfigRequest);

	public Long setMetaIDUByFacilityId(String facl_id);
	
	/**
	 * Provides the AC Configuration Details for a Group or an ODU .
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public Set<ACConfigODUVO> getODUACConfiguration(
			ACConfigRequest acConfigRequest);
	
	
	
	
	

	/**
	 * Generate excel report for AC Details
	 * 
	 * @param inputDataList
	 * @param addCustomer
	 * @return
	 * @throws Exception
	 */
	public String generateACDetailsExcelReport(Set<CAConfigVO> inputDataList,
			String addCustomer) throws Exception;

	/**
	 * Generate csv report for AC Details
	 * 
	 * @param inputDataList
	 * @param addCustomer
	 * @return
	 * @throws Exception
	 */
	public String generateACDetailsCsvReport(Set<CAConfigVO> inputDataList,
			String addCustomer) throws Exception;
	
	
}

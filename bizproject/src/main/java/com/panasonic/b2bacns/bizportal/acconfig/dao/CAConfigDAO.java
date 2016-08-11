package com.panasonic.b2bacns.bizportal.acconfig.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.CAConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.DetailsLogsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.RefrigerantSVG;
import com.panasonic.b2bacns.bizportal.persistence.Metaindoorunit;


/**
 * This class handles all service for Cloud Adapters
 * 
 * @author jwchan
 * 
 */

public interface CAConfigDAO {


	/**
     * Get ALL CA Details, tagged every CA with groups to filter on client side.
	 * @param
	 * @return
	 * @throws HibernateException
	 */
	public List<CAConfigVO> getCAList(ACConfigRequest acConfigRequest) throws HibernateException;
	
	/**
    * Get ALL CA Details, tagged every CA with groups to filter on client side.
	 * @param
	 * @return
	 * @throws HibernateException
	 */
	public Set<CAConfigVO> getCASet(ACConfigRequest acConfigRequest) throws HibernateException;
	public List<CAConfigVO> getCAListByMac(ACConfigRequest acConfigRequest);
	public Set<ACConfigODUVO> getODUListByMac(ACConfigRequest acConfigRequest);
	
	public ACConfigIDUVO getACConfigDetails(ACConfigRequest acConfigRequest);
	public ACConfigIDUVO getACConfigDetailsCA(ACConfigRequest acConfigRequest);
	public Metaindoorunit getStatusInfoByFacl(String facl_id, String ooid, int adapter_id, String site_id, String company_id);
	public long update_metaindoorunits(Metaindoorunit metaIDUParams);

	public boolean update_indoorunits_meta_by_facl(String facl_id, long id);
	
	/**
	 * Set Refrigerant SVG for all 3 parameter.
	 * 
	 * @param refrigerantSVGList
	 * @param rowData
	 */
	public void getRefrigerantSVG(Set<RefrigerantSVG> refrigerantSVGList,
			Object[] rowData);	
	/**
	 * Set Remote Control Parameter in AC and Notification IDU Details.
	 * 
	 * @param rowData
	 * @return
	 */
	public DetailsLogsVO setRCParameter(Object[] rowData);

	/**
	 * Provides the AC Configuration Details for a Group or an ODU.
	 * 
	 * @param acConfigRequest
	 *            ACConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public Set<ACConfigODUVO> getODUACConfigDetails2(
			ACConfigRequest acConfigRequest);

	public Set<ACConfigODUVO> getODUACConfigDetails(
			ACConfigRequest acConfigRequest);


	
}

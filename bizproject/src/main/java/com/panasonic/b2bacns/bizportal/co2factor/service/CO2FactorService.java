/**
 * 
 */
package com.panasonic.b2bacns.bizportal.co2factor.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorRequestVO;
import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorVO;

/**
 * @author Narendra.Kumar
 * 
 */

public interface CO2FactorService {

	/**
	 * this method return co2 factor value and site id from the group
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public List<CO2FactorVO> getCO2Factor(Long companyId, Long userId);
	
	/**
	 * 
	 * @param siteId
	 * @return
	 */
	public List<CO2FactorVO> getCO2Factor(Long siteId);

	/**
	 * @param cO2FactorRequestVO
	 * @return
	 */
	public boolean saveCO2Factor(List<CO2FactorRequestVO> cO2FactorRequestVO);

}

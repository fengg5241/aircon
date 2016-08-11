/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.cr.vo.PowerRatio;
import com.panasonic.b2bacns.bizportal.persistence.DistributionRatioData;
import com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService;

/**
 * @author simanchal.patra
 *
 */

public interface DistributionRatioDAOservice extends
		GenericDAOService<DistributionRatioData> {

	List<PowerRatio> getPowerRatioData(Long transactionId);

}

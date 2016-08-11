/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.cr.vo.PowerDetail;
import com.panasonic.b2bacns.bizportal.persistence.DistributionDetailData;
import com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService;

/**
 * @author simanchal.patra
 *
 */

public interface DistributionDetailDAOservice extends
		GenericDAOService<DistributionDetailData> {

	List<PowerDetail> getPowerDetailData(Long transactionId);

}

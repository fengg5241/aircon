/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import com.panasonic.b2bacns.bizportal.cr.vo.PowerDistHeader;
import com.panasonic.b2bacns.bizportal.persistence.CutoffRequest;
import com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService;

/**
 * @author simanchal.patra
 *
 */

public interface CutoffRequestDAOservice extends
		GenericDAOService<CutoffRequest> {

	PowerDistHeader getPowerDistHeader(Long transactionId);

}

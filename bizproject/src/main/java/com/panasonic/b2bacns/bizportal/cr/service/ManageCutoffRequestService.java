/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.panasonic.b2bacns.bizportal.cr.vo.DistibutionGroupVo;
import com.panasonic.b2bacns.bizportal.cr.vo.RegisteredCutoffRequestDetails;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;

/**
 * @author simanchal.patra
 *
 */
public interface ManageCutoffRequestService {

	List<RegisteredCutoffRequestDetails> getAllRegisteredCutoffRequests(
			Long userID);

	// Modified by ravi
	RegisteredCutoffRequestDetails registerCutoffRequest(String fromdate,
			String todate, List<Long> siteIDList, String userLoginID,
			Long userID, Long companyID, List<?> timezone) throws BusinessFailureException;

	List<DistibutionGroupVo> getDistributionGroupsBySiteID(Long siteID);

	String getPowerRatioReportPath(Long transactionId,
			HttpServletResponse response) throws IOException,
			BusinessFailureException;

	String getPowerDetailReportPath(Long transactionId,
			HttpServletResponse response) throws IOException,
			BusinessFailureException;

	//Added By Ravi
	List<?> getTimezones(List<Long> siteIDList);
}

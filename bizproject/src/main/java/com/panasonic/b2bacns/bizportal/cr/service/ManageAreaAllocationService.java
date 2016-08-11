/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.cr.vo.Area;
import com.panasonic.b2bacns.bizportal.cr.vo.AreadAllocationDetails;
import com.panasonic.b2bacns.bizportal.cr.vo.IDUAreaMapping;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;

/**
 * @author simanchal.patra
 *
 */
public interface ManageAreaAllocationService {

	AreadAllocationDetails getAllocatedAreas(Long siteID,
			Long distributionGroupID);

	boolean createArea(Long siteID, Long distributionGroupID, String areaName,
			SessionInfo sesseionInfo) throws BusinessFailureException;

	boolean removeArea(Long siteID, Long distributionGroupID, String areaName)
			throws BusinessFailureException;

	void updateIDUAreaMapping(List<IDUAreaMapping> areaMappingList)
			throws BusinessFailureException;

	List<Area> getAllAreas(Long distributionGroupID);

	boolean isAreaAssigned(Long areaId) throws BusinessFailureException;

}

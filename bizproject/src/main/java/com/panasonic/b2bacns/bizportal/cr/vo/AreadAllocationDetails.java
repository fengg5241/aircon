/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author simanchal.patra
 *
 */
public class AreadAllocationDetails implements Serializable {

	private static final long serialVersionUID = -3202958508352325315L;

	private List<IDUDsitributionGroupDetail> distributionGroupDetails;

	private List<Area> availableAreas;

	/**
	 * @return the distributionGroupDetails
	 */
	public List<IDUDsitributionGroupDetail> getDistributionGroupDetails() {
		return distributionGroupDetails;
	}

	/**
	 * @param distributionGroupDetails
	 *            the distributionGroupDetails to set
	 */
	public void setDistributionGroupDetails(
			List<IDUDsitributionGroupDetail> distributionGroupDetails) {
		this.distributionGroupDetails = distributionGroupDetails;
	}

	/**
	 * @return the availableAreas
	 */
	public List<Area> getAvailableAreas() {
		return availableAreas;
	}

	/**
	 * @param availableAreas
	 *            the availableAreas to set
	 */
	public void setAvailableAreas(List<Area> availableAreas) {
		this.availableAreas = availableAreas;
	}

}

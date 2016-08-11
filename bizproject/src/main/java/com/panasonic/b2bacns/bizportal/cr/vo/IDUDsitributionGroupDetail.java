/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * { "distributionGroupDetails": [{ "detailId":1, "iduAddress":"02-01-01",
 * "category":"VRF", "deviceName":"IDU 1",
 * "distribution":"Distribution Group 1", "areaId":1, "areaName":"Camera Shop"
 * }, {} ], "availableAreas": [{ "areaId":1, "areaName":"Salon Shop" }, {} ] }
 * 
 * 
 * @author simanchal.patra
 *
 */
@JsonAutoDetect
public class IDUDsitributionGroupDetail implements Serializable {

	private static final long serialVersionUID = 8662272191189733291L;

	@JsonProperty(value = "detailId")
	private Long distributionGroupID;

	private String iduAddress;

	private String category;

	@JsonProperty(value = "deviceName")
	private String iduName;

	private Long iduId;

	private String distribution;

	private Long areaID;

	private String areaName;

	/**
	 * @return the detailId
	 */
	public Long getDistributionGroupID() {
		return distributionGroupID;
	}

	/**
	 * @param detailId
	 *            the detailId to set
	 */
	public void setDistributionGroupID(Long detailId) {
		this.distributionGroupID = detailId;
	}

	/**
	 * @return the iduAddress
	 */
	public String getIduAddress() {
		return iduAddress;
	}

	/**
	 * @param iduAddress
	 *            the iduAddress to set
	 */
	public void setIduAddress(String iduAddress) {
		this.iduAddress = iduAddress;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return iduName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.iduName = deviceName;
	}

	/**
	 * @return the distribution
	 */
	public String getDistribution() {
		return distribution;
	}

	/**
	 * @param distribution
	 *            the distribution to set
	 */
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	/**
	 * @return the areaID
	 */
	public Long getAreaID() {
		return areaID;
	}

	/**
	 * @param areaID
	 *            the areaID to set
	 */
	public void setAreaID(Long areaID) {
		this.areaID = areaID;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the iduId
	 */
	public Long getIduId() {
		return iduId;
	}

	/**
	 * @param iduId
	 *            the iduId to set
	 */
	public void setIduId(Long iduId) {
		this.iduId = iduId;
	}

}

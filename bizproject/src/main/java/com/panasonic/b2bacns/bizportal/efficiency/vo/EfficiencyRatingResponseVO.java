/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Narendra.Kumar
 * 
 */
public class EfficiencyRatingResponseVO {
	@JsonIgnore
	private String groupId;
	private String groupName;
	private Integer treesCount;
	@JsonIgnore
	private Double powerConsumption;
	@JsonIgnore
	private Double co2FactorVal;
	@JsonIgnore
	private String path;

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the treesCount
	 */
	public Integer getTreesCount() {
		return treesCount;
	}

	/**
	 * @param treesCount
	 *            the treesCount to set
	 */
	public void setTreesCount(Integer treesCount) {
		this.treesCount = treesCount;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the powerConsumption
	 */
	public Double getPowerConsumption() {
		return powerConsumption;
	}

	/**
	 * @param powerConsumption
	 *            the powerConsumption to set
	 */
	public void setPowerConsumption(Double powerConsumption) {
		this.powerConsumption = powerConsumption;
	}

	/**
	 * @return the co2FactorVal
	 */
	public Double getCo2FactorVal() {
		return co2FactorVal;
	}

	/**
	 * @param co2FactorVal
	 *            the co2FactorVal to set
	 */
	public void setCo2FactorVal(Double co2FactorVal) {
		this.co2FactorVal = co2FactorVal;
	}

}

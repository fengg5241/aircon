/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.vo;

/**
 * @author Amitesh.Arya
 * 
 */
public class EfficiencyRankingDownloadVO {

	private String level;
	private Double value;
	private String customerName;
	private Integer efficiencyRanking;

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the efficiencyRanking
	 */
	public Integer getEfficiencyRanking() {
		return efficiencyRanking;
	}

	/**
	 * @param efficiencyRanking
	 *            the efficiencyRanking to set
	 */
	public void setEfficiencyRanking(Integer efficiencyRanking) {
		this.efficiencyRanking = efficiencyRanking;
	}

}

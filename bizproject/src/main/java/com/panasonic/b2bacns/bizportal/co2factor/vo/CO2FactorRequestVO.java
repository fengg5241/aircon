/**
 * 
 */
package com.panasonic.b2bacns.bizportal.co2factor.vo;


/**
 * @author Narendra.Kumar
 * 
 */
public class CO2FactorRequestVO {
	private Long siteIds;
	private Double co2FactorValue;
	private String startDate;

	/**
	 * @return the siteIds
	 */
	public Long getSiteIds() {
		return siteIds;
	}

	/**
	 * @param siteIds
	 *            the siteIds to set
	 */
	public void setSiteIds(Long siteIds) {
		this.siteIds = siteIds;
	}

	/**
	 * @return the co2FactorValue
	 */
	public Double getCo2FactorValue() {
		return co2FactorValue;
	}

	/**
	 * @param co2FactorValue
	 *            the co2FactorValue to set
	 */
	public void setCo2FactorValue(Double co2FactorValue) {
		this.co2FactorValue = co2FactorValue;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}

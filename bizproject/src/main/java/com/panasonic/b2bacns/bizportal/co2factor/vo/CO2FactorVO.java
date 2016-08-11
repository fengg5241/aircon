package com.panasonic.b2bacns.bizportal.co2factor.vo;

public class CO2FactorVO {
	private Long siteId;
	private Double co2FactorValue;
	private String startDate;

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

	/**
	 * @return the siteId
	 */
	public Long getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId
	 *            the siteId to set
	 */
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

}

package com.panasonic.b2bacns.bizportal.efficiency.vo;

public class EfficiencyGroupRefVO {
	private String supplyGroupName;
	private Long supplyGroupId;
	private Long refrigerantId;
	private Long refId;
	private Double efficiency;
	private String sitePath;
	private String companyName;

	/**
	 * @return the refId
	 */
	public Long getRefId() {
		return refId;
	}

	/**
	 * @param refId
	 *            the refId to set
	 */
	public void setRefId(Long refId) {
		this.refId = refId;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return supplyGroupName + "-" + supplyGroupId;
	}

	/**
	 * @return the supplyGroupName
	 */
	public String getSupplyGroupName() {
		return supplyGroupName;
	}

	/**
	 * @param supplyGroupName
	 *            the supplyGroupName to set
	 */
	public void setSupplyGroupName(String supplyGroupName) {
		this.supplyGroupName = supplyGroupName;
	}

	/**
	 * @return the supplyGroupId
	 */
	public Long getSupplyGroupId() {
		return supplyGroupId;
	}

	/**
	 * @param supplyGroupId
	 *            the supplyGroupId to set
	 */
	public void setSupplyGroupId(Long supplyGroupId) {
		this.supplyGroupId = supplyGroupId;
	}

	/**
	 * @return the refrigerantId
	 */
	public Long getRefrigerantId() {
		return refrigerantId;
	}

	/**
	 * @param refrigerantId
	 *            the refrigerantId to set
	 */
	public void setRefrigerantId(Long refrigerantId) {
		this.refrigerantId = refrigerantId;
	}

	/**
	 * @return the efficiency
	 */
	public Double getEfficiency() {
		return efficiency;
	}

	/**
	 * @param efficiency
	 *            the efficiency to set
	 */
	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the sitePath
	 */
	public String getSitePath() {
		return sitePath;
	}

	/**
	 * @param sitePath
	 *            the sitePath to set
	 */
	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

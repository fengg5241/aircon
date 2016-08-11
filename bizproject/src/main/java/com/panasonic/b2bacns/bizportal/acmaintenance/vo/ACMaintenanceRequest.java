package com.panasonic.b2bacns.bizportal.acmaintenance.vo;

import java.util.List;

public class ACMaintenanceRequest {

	private Long siteID;
	private Long oduID;
	private List<ACMaintenanceSettingVO> maintenanceTypeList;
	private Long maintenanceTypeID;
	private List<String> addUserList;
	private List<Long> deleteUserList;
	private Long companyID;
	private Long maintenanceID;
	private String maintenanceType;

	/**
	 * @return the oduID
	 */
	public Long getOduID() {
		return oduID;
	}

	/**
	 * @param oduID
	 *            the oduID to set
	 */
	public void setOduID(Long oduID) {
		this.oduID = oduID;
	}

	/**
	 * @return the maintenanceTypeID
	 */
	public Long getMaintenanceTypeID() {
		return maintenanceTypeID;
	}

	/**
	 * @param maintenanceTypeID
	 *            the maintenanceTypeID to set
	 */
	public void setMaintenanceTypeID(Long maintenanceTypeID) {
		this.maintenanceTypeID = maintenanceTypeID;
	}

	/**
	 * @return the siteID
	 */
	public Long getSiteID() {
		return siteID;
	}

	/**
	 * @param siteID
	 *            the siteID to set
	 */
	public void setSiteID(Long siteID) {
		this.siteID = siteID;
	}

	/**
	 * @return the maintenanceTypeList
	 */
	public List<ACMaintenanceSettingVO> getMaintenanceTypeList() {
		return maintenanceTypeList;
	}

	/**
	 * @param maintenanceTypeList
	 *            the maintenanceTypeList to set
	 */
	public void setMaintenanceTypeList(
			List<ACMaintenanceSettingVO> maintenanceTypeList) {
		this.maintenanceTypeList = maintenanceTypeList;
	}

	/**
	 * @return the addUserList
	 */
	public List<String> getAddUserList() {
		return addUserList;
	}

	/**
	 * @param addUserList
	 *            the addUserList to set
	 */
	public void setAddUserList(List<String> addUserList) {
		this.addUserList = addUserList;
	}

	/**
	 * @return the deleteUserList
	 */
	public List<Long> getDeleteUserList() {
		return deleteUserList;
	}

	/**
	 * @param deleteUserList
	 *            the deleteUserList to set
	 */
	public void setDeleteUserList(List<Long> deleteUserList) {
		this.deleteUserList = deleteUserList;
	}

	/**
	 * @return the companyID
	 */
	public Long getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID
	 *            the companyID to set
	 */
	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	/**
	 * @return the maintenanceID
	 */
	public Long getMaintenanceID() {
		return maintenanceID;
	}

	/**
	 * @param maintenanceID
	 *            the maintenanceID to set
	 */
	public void setMaintenanceID(Long maintenanceID) {
		this.maintenanceID = maintenanceID;
	}

	/**
	 * @return the maintenanceType
	 */
	public String getMaintenanceType() {
		return maintenanceType;
	}

	/**
	 * @param maintenanceType
	 *            the maintenanceType to set
	 */
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ACMaintenanceRequest [siteID=" + siteID + ", oduID=" + oduID
				+ ", maintenanceTypeList=" + maintenanceTypeList
				+ ", maintenanceTypeID=" + maintenanceTypeID + ", addUserList="
				+ addUserList + ", deleteUserList=" + deleteUserList
				+ ", companyID=" + companyID + ", maintenanceID="
				+ maintenanceID + ", maintenanceType=" + maintenanceType + "]";
	}

}

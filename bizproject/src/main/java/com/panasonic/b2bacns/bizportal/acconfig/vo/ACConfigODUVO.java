package com.panasonic.b2bacns.bizportal.acconfig.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

public class ACConfigODUVO {

	private String odu_Name;
	private String unitAddress;
	private Long oduId;
	private String site;
	private String parentChild;
	private Long parentId;
	private Double minlatitude;
	private Double maxlatitude;
	private Double minlongitude;
	private Double maxlongitude;
	private String svg_Location;
	private String caStatus;
	private String alarmCode;
	private String alarmType;
	private String type;
	private String sLinkAddress;
	private Double outdoorTemp;
	private Long svgId;
	private String svgDisplayName;

	private String demand = BizConstants.HYPHEN;
	private String ghpOilCheckCountDown = BizConstants.HYPHEN;
	private String ghpEngineServiceCountDown = BizConstants.HYPHEN;
	private String generationPower = BizConstants.HYPHEN;
	private String maintenanceCountDownComp1 = BizConstants.HYPHEN;
	private String maintenanceCountDownComp2 = BizConstants.HYPHEN;
	private String maintenanceCountDownComp3 = BizConstants.HYPHEN;
	//add by shanf
	private String deviceModel = BizConstants.HYPHEN;

	@JsonIgnore
	private String demandDownload;
	@JsonIgnore
	private String ghpOilCheckCountDownDownload;
	@JsonIgnore
	private String ghpEngineServiceCountDownDownload;
	@JsonIgnore
	private String generationPowerDownload;
	@JsonIgnore
	private String maintenanceCountDownComp1Download;
	@JsonIgnore
	private String maintenanceCountDownComp2Download;
	@JsonIgnore
	private String maintenanceCountDownComp3Download;
	@JsonIgnore
	private String customerName;
	@JsonIgnore
	private String sitePath;

	/**
	 * @return the demandDownload
	 */
	public String getDemandDownload() {
		return demand.equals(BizConstants.HYPHEN) ? null : demand.replace(
				BizConstants.PERCENT, BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the ghpOilCheckCountDownDownload
	 */
	public String getGhpOilCheckCountDownDownload() {
		return ghpOilCheckCountDown.equals(BizConstants.HYPHEN) ? null
				: ghpOilCheckCountDown.replace(BizConstants.HRS,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the ghpEngineServiceCountDownDownload
	 */
	public String getGhpEngineServiceCountDownDownload() {
		return ghpEngineServiceCountDown.equals(BizConstants.HYPHEN) ? null
				: ghpEngineServiceCountDown.replace(BizConstants.HRS,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the generationPowerDownload
	 */
	public String getGenerationPowerDownload() {
		return generationPower.equals(BizConstants.HYPHEN) ? null
				: generationPower.replace(BizConstants.WH,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the maintenanceCountDownComp1Download
	 */
	public String getMaintenanceCountDownComp1Download() {
		return maintenanceCountDownComp1.equals(BizConstants.HYPHEN) ? null
				: maintenanceCountDownComp1.replace(BizConstants.HRS,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the maintenanceCountDownComp2Download
	 */
	public String getMaintenanceCountDownComp2Download() {
		return maintenanceCountDownComp2.equals(BizConstants.HYPHEN) ? null
				: maintenanceCountDownComp2.replace(BizConstants.HRS,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the maintenanceCountDownComp3Download
	 */
	public String getMaintenanceCountDownComp3Download() {
		return maintenanceCountDownComp3.equals(BizConstants.HYPHEN) ? null
				: maintenanceCountDownComp3.replace(BizConstants.HRS,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType
	 *            the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return the svgId
	 */
	public Long getSvgId() {
		return svgId;
	}

	/**
	 * @param svgId
	 *            the svgId to set
	 */
	public void setSvgId(Long svgId) {
		this.svgId = svgId;
	}

	/**
	 * @return the svgDisplayName
	 */
	public String getSvgDisplayName() {
		return svgDisplayName;
	}

	/**
	 * @param svgDisplayName
	 *            the svgDisplayName to set
	 */
	public void setSvgDisplayName(String svgDisplayName) {
		this.svgDisplayName = svgDisplayName;
	}

	/**
	 * @return the odu_Name
	 */
	public String getOdu_Name() {
		return odu_Name;
	}

	/**
	 * @param odu_Name
	 *            the odu_Name to set
	 */
	public void setOdu_Name(String odu_Name) {
		this.odu_Name = odu_Name;
	}

	/**
	 * @return the unitAddress
	 */
	public String getUnitAddress() {
		return unitAddress;
	}

	/**
	 * @param unitAddress
	 *            the unitAddress to set
	 */
	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	/**
	 * @return the oduId
	 */
	public Long getOduId() {
		return oduId;
	}

	/**
	 * @param oduId
	 *            the oduId to set
	 */
	public void setOduId(Long oduId) {
		this.oduId = oduId;
	}

	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}

	/**
	 * @param site
	 *            the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * @return the parentChild
	 */
	public String getParentChild() {
		return parentChild;
	}

	/**
	 * @param parentChild
	 *            the parentChild to set
	 */
	public void setParentChild(String parentChild) {
		this.parentChild = parentChild;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the minlatitude
	 */
	public Double getMinlatitude() {
		return minlatitude;
	}

	/**
	 * @param minlatitude
	 *            the minlatitude to set
	 */
	public void setMinlatitude(Double minlatitude) {
		this.minlatitude = minlatitude;
	}

	/**
	 * @return the maxlatitude
	 */
	public Double getMaxlatitude() {
		return maxlatitude;
	}

	/**
	 * @param maxlatitude
	 *            the maxlatitude to set
	 */
	public void setMaxlatitude(Double maxlatitude) {
		this.maxlatitude = maxlatitude;
	}

	/**
	 * @return the minlongitude
	 */
	public Double getMinlongitude() {
		return minlongitude;
	}

	/**
	 * @param minlongitude
	 *            the minlongitude to set
	 */
	public void setMinlongitude(Double minlongitude) {
		this.minlongitude = minlongitude;
	}

	/**
	 * @return the maxlongitude
	 */
	public Double getMaxlongitude() {
		return maxlongitude;
	}

	/**
	 * @param maxlongitude
	 *            the maxlongitude to set
	 */
	public void setMaxlongitude(Double maxlongitude) {
		this.maxlongitude = maxlongitude;
	}

	/**
	 * @return the svg_Location
	 */
	public String getSvg_Location() {
		return svg_Location;
	}

	/**
	 * @param svg_Location
	 *            the svg_Location to set
	 */
	public void setSvg_Location(String svg_Location) {
		this.svg_Location = svg_Location;
	}

	/**
	 * @return the caStatus
	 */
	public String getCaStatus() {
		return caStatus;
	}

	/**
	 * @param caStatus
	 *            the caStatus to set
	 */
	public void setCaStatus(String caStatus) {
		this.caStatus = caStatus;
	}

	/**
	 * @return the alarmCode
	 */
	public String getAlarmCode() {
		return alarmCode;
	}

	/**
	 * @param alarmCode
	 *            the alarmCode to set
	 */
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the sLinkAddress
	 */
	public String getSLinkAddress() {
		return sLinkAddress;
	}

	/**
	 * @param sLinkAddress
	 *            the sLinkAddress to set
	 */
	public void setSLinkAddress(String sLinkAddress) {
		this.sLinkAddress = sLinkAddress;
	}

	/**
	 * @return the outdoorTemp
	 */
	public Double getOutdoorTemp() {
		return outdoorTemp;
	}

	/**
	 * @param outdoorTemp
	 *            the outdoorTemp to set
	 */
	public void setOutdoorTemp(Double outdoorTemp) {
		this.outdoorTemp = outdoorTemp;
	}

	/**
	 * @return the demand
	 */
	public String getDemand() {
		return demand;
	}

	/**
	 * @param demand
	 *            the demand to set
	 */
	public void setDemand(String demand) {
		this.demand = demand;
	}

	/**
	 * @return the ghpOilCheckCountDown
	 */
	public String getGhpOilCheckCountDown() {
		return ghpOilCheckCountDown;
	}

	/**
	 * @param ghpOilCheckCountDown
	 *            the ghpOilCheckCountDown to set
	 */
	public void setGhpOilCheckCountDown(String ghpOilCheckCountDown) {
		this.ghpOilCheckCountDown = ghpOilCheckCountDown;
	}

	/**
	 * @return the ghpEngineServiceCountDown
	 */
	public String getGhpEngineServiceCountDown() {
		return ghpEngineServiceCountDown;
	}

	/**
	 * @param ghpEngineServiceCountDown
	 *            the ghpEngineServiceCountDown to set
	 */
	public void setGhpEngineServiceCountDown(String ghpEngineServiceCountDown) {
		this.ghpEngineServiceCountDown = ghpEngineServiceCountDown;
	}

	/**
	 * @return the generationPower
	 */
	public String getGenerationPower() {
		return generationPower;
	}

	/**
	 * @param generationPower
	 *            the generationPower to set
	 */
	public void setGenerationPower(String generationPower) {
		this.generationPower = generationPower;
	}

	/**
	 * @return the maintenanceCountDownComp1
	 */
	public String getMaintenanceCountDownComp1() {
		return maintenanceCountDownComp1;
	}

	/**
	 * @param maintenanceCountDownComp1
	 *            the maintenanceCountDownComp1 to set
	 */
	public void setMaintenanceCountDownComp1(String maintenanceCountDownComp1) {
		this.maintenanceCountDownComp1 = maintenanceCountDownComp1;
	}

	/**
	 * @return the maintenanceCountDownComp2
	 */
	public String getMaintenanceCountDownComp2() {
		return maintenanceCountDownComp2;
	}

	/**
	 * @param maintenanceCountDownComp2
	 *            the maintenanceCountDownComp2 to set
	 */
	public void setMaintenanceCountDownComp2(String maintenanceCountDownComp2) {
		this.maintenanceCountDownComp2 = maintenanceCountDownComp2;
	}

	/**
	 * @return the maintenanceCountDownComp3
	 */
	public String getMaintenanceCountDownComp3() {
		return maintenanceCountDownComp3;
	}

	/**
	 * @param maintenanceCountDownComp3
	 *            the maintenanceCountDownComp3 to set
	 */
	public void setMaintenanceCountDownComp3(String maintenanceCountDownComp3) {
		this.maintenanceCountDownComp3 = maintenanceCountDownComp3;
	}
	//add by shanf
	public String getDeviceModel() {
		return deviceModel;
	}
	//add by shanf
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return the sitePath
	 */
	public String getSitePath() {
		return sitePath == null ? odu_Name : sitePath.concat(
				BizConstants.COMMA_STRING).concat(odu_Name);
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param sitePath
	 *            the sitePath to set
	 */
	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oduId == null) ? 0 : oduId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ACConfigODUVO other = (ACConfigODUVO) obj;
		if (oduId == null) {
			if (other.oduId != null)
				return false;
		} else if (!oduId.equals(other.oduId))
			return false;
		return true;
	}

}

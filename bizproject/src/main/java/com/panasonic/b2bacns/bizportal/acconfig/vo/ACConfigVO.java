package com.panasonic.b2bacns.bizportal.acconfig.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

public class ACConfigVO {

	private String idu_Name;
	private String unitAddress;
	private String power;
	private String temperature;
	private String mode;
	private Long iduId;
	private String flapMode;
	private String fanSpeed;
	private String ecoNavi;
	private String site;
	private String location;
	private String parentChild;
	private Long parentId;
	private String roomTemp;
	private Double minlatitude;
	private Double maxlatitude;
	private Double minlongitude;
	private Double maxlongitude;
	private Long linkODUid;
	private String linkODUSVG;
	private String svg_Location;
	private String energy_saving;
	private String caStatus;
	private String alarmCode;
	private String alarmType;
	private String prohibitRCPower;
	private String prohibitRCMode;
	private String prohibitRCTemp;
	private String prohibitRCFanSpeed;
	private String prohibitRCFlapMode;
	private String prohibitRCEnergySaving;
	private String type;
	private String sLinkAddress;
	private Long svgId;
	private String svgDisplayName;
	private Integer prohibit;
	private Integer filterSign;
	private Short rc_flag;
	private Short fixedOperationMode;
	//add by shanf
	private String deviceModel;
	@JsonIgnore
	private String customerName;
	@JsonIgnore
	private String sitePath;
	@JsonIgnore
	private String prohibitRCPowerDownload;
	@JsonIgnore
	private String prohibitRCModeDownload;
	@JsonIgnore
	private String prohibitRCTempDownload;
	@JsonIgnore
	private String prohibitRCFanSpeedDownload;
	@JsonIgnore
	private String prohibitRCFlapModeDownload;
	@JsonIgnore
	private String prohibitRCEnergySavingDownload;
	@JsonIgnore
	private String alarmCodeDownload;
	@JsonIgnore
	private String powerDownload;
	@JsonIgnore
	private String temperatureDownload;
	@JsonIgnore
	private String modeDownload;
	@JsonIgnore
	private String flapModeDownload;
	@JsonIgnore
	private String fanSpeedDownload;
	@JsonIgnore
	private String energy_savingDownload;
	@JsonIgnore
	private String roomTempDownload;

	private final static String NOTPROHIBITED = "notProhibited";
	private final static String OFF = "OFF";
	private final static String ON = "ON";

	/**
	 * @return the filterSign
	 */
	public Integer getFilterSign() {
		return filterSign;
	}

	/**
	 * @param filterSign
	 *            the filterSign to set
	 */
	public void setFilterSign(Integer filterSign) {
		this.filterSign = filterSign;
	}

	/**
	 * @return the prohibitRCPowerDownload
	 */
	public String getProhibitRCPowerDownload() {
		return prohibitRCPower.equals(BizConstants.HYPHEN) ? null
				: prohibitRCPower.equals(NOTPROHIBITED) ? OFF : ON;
	}

	/**
	 * @return the prohibitRCModeDownload
	 */
	public String getProhibitRCModeDownload() {
		return prohibitRCMode.equals(BizConstants.HYPHEN) ? null
				: prohibitRCMode.equals(NOTPROHIBITED) ? OFF : ON;
	}

	/**
	 * @return the prohibitRCTempDownload
	 */
	public String getProhibitRCTempDownload() {
		return prohibitRCTemp.equals(BizConstants.HYPHEN) ? null
				: prohibitRCTemp.equals(NOTPROHIBITED) ? OFF : ON;
	}

	/**
	 * @return the prohibitRCFanSpeedDownload
	 */
	public String getProhibitRCFanSpeedDownload() {
		return prohibitRCFanSpeed.equals(BizConstants.HYPHEN) ? null
				: prohibitRCFanSpeed.equals(NOTPROHIBITED) ? OFF : ON;
	}

	/**
	 * @return the prohibitRCFlapModeDownload
	 */
	public String getProhibitRCFlapModeDownload() {
		return prohibitRCFlapMode.equals(BizConstants.HYPHEN) ? null
				: prohibitRCFlapMode.equals(NOTPROHIBITED) ? OFF : ON;
	}

	/**
	 * @return the prohibitRCEnergySavingDownload
	 */
	public String getProhibitRCEnergySavingDownload() {
		return prohibitRCEnergySaving.equals(BizConstants.HYPHEN) ? null
				: prohibitRCEnergySaving.equals(NOTPROHIBITED) ? OFF : ON;

	}

	/**
	 * @return the alarmCodeDownload
	 */
	public String getAlarmCodeDownload() {
		return alarmCode.equals(BizConstants.HYPHEN) ? null : alarmCode;
	}

	/**
	 * @return the powerDownload
	 */
	public String getPowerDownload() {
		return power.equals(BizConstants.HYPHEN) ? null : power;
	}

	/**
	 * @return the temperatureDownload
	 */
	public String getTemperatureDownload() {
		return temperature.equals(BizConstants.HYPHEN) ? null : temperature;
	}

	/**
	 * @return the modeDownload
	 */
	public String getModeDownload() {
		return mode.equals(BizConstants.HYPHEN) ? null : mode;
	}

	/**
	 * @return the flapModeDownload
	 */
	public String getFlapModeDownload() {
		return flapMode.equals(BizConstants.HYPHEN) ? null : flapMode;
	}

	/**
	 * @return the fanSpeedDownload
	 */
	public String getFanSpeedDownload() {
		return fanSpeed.equals(BizConstants.HYPHEN) ? null : fanSpeed;
	}

	/**
	 * @return the energy_savingDownload
	 */
	public String getEnergy_savingDownload() {
		return energy_saving.equals(BizConstants.HYPHEN) ? null : energy_saving;
	}

	/**
	 * @return the roomTempDownload
	 */
	public String getRoomTempDownload() {
		return roomTemp.equals(BizConstants.HYPHEN) ? null : roomTemp;
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
	 * @return the sitePath
	 */
	public String getSitePath() {
		return sitePath == null ? idu_Name : sitePath.concat(
				BizConstants.COMMA_STRING).concat(idu_Name);
	}

	/**
	 * @param sitePath
	 *            the sitePath to set
	 */
	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}

	/**
	 * @return the rc_flag
	 */
	public Short getRc_flag() {
		return rc_flag;
	}

	/**
	 * @param rc_flag
	 *            the rc_flag to set
	 */
	public void setRc_flag(Short rc_flag) {
		this.rc_flag = rc_flag;
	}

	/**
	 * @return the prohibit
	 */
	public Integer getProhibit() {
		return prohibit;
	}

	/**
	 * @param prohibit
	 *            the prohibit to set
	 */
	public void setProhibit(Integer prohibit) {
		this.prohibit = prohibit;
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
	 * @return the power
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @param power
	 *            the power to set
	 */
	public void setPower(String power) {
		this.power = power;
	}

	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature
	 *            the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the flapMode
	 */
	public String getFlapMode() {
		return flapMode;
	}

	/**
	 * @param flapMode
	 *            the flapMode to set
	 */
	public void setFlapMode(String flapMode) {
		this.flapMode = flapMode;
	}

	/**
	 * @return the fanSpeed
	 */
	public String getFanSpeed() {
		return fanSpeed;
	}

	/**
	 * @param fanSpeed
	 *            the fanSpeed to set
	 */
	public void setFanSpeed(String fanSpeed) {
		this.fanSpeed = fanSpeed;
	}

	/**
	 * @return the ecoNavi
	 */
	public String getEcoNavi() {
		return ecoNavi;
	}

	/**
	 * @param ecoNavi
	 *            the ecoNavi to set
	 */
	public void setEcoNavi(String ecoNavi) {
		this.ecoNavi = ecoNavi;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * @return the roomTemp
	 */
	public String getRoomTemp() {
		return roomTemp;
	}

	/**
	 * @param roomTemp
	 *            the roomTemp to set
	 */
	public void setRoomTemp(String roomTemp) {
		this.roomTemp = roomTemp;
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
	 * @return the linkODUid
	 */
	public Long getLinkODUid() {
		return linkODUid;
	}

	/**
	 * @param linkODUid
	 *            the linkODUid to set
	 */
	public void setLinkODUid(Long linkODUid) {
		this.linkODUid = linkODUid;
	}

	/**
	 * @return the linkODUSVG
	 */
	public String getLinkODUSVG() {
		return linkODUSVG;
	}

	/**
	 * @param linkODUSVG
	 *            the linkODUSVG to set
	 */
	public void setLinkODUSVG(String linkODUSVG) {
		this.linkODUSVG = linkODUSVG;
	}

	/**
	 * @return the energy_saving
	 */
	public String getEnergy_saving() {
		return energy_saving;
	}

	/**
	 * @param energy_saving
	 *            the energy_saving to set
	 */
	public void setEnergy_saving(String energy_saving) {
		this.energy_saving = energy_saving;
	}

	/**
	 * @return the idu_Name
	 */
	public String getIdu_Name() {
		return idu_Name;
	}

	/**
	 * @param idu_Name
	 *            the idu_Name to set
	 */
	public void setIdu_Name(String idu_Name) {
		this.idu_Name = idu_Name;
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
	 * @return the prohibitRCPower
	 */
	public String getProhibitRCPower() {
		return prohibitRCPower;
	}

	/**
	 * @param prohibitRCPower
	 *            the prohibitRCPower to set
	 */
	public void setProhibitRCPower(String prohibitRCPower) {
		this.prohibitRCPower = prohibitRCPower;
	}

	/**
	 * @return the prohibitRCMode
	 */
	public String getProhibitRCMode() {
		return prohibitRCMode;
	}

	/**
	 * @param prohibitRCMode
	 *            the prohibitRCMode to set
	 */
	public void setProhibitRCMode(String prohibitRCMode) {
		this.prohibitRCMode = prohibitRCMode;
	}

	/**
	 * @return the prohibitRCTemp
	 */
	public String getProhibitRCTemp() {
		return prohibitRCTemp;
	}

	/**
	 * @param prohibitRCTemp
	 *            the prohibitRCTemp to set
	 */
	public void setProhibitRCTemp(String prohibitRCTemp) {
		this.prohibitRCTemp = prohibitRCTemp;
	}

	/**
	 * @return the prohibitRCFanSpeed
	 */
	public String getProhibitRCFanSpeed() {
		return prohibitRCFanSpeed;
	}

	/**
	 * @param prohibitRCFanSpeed
	 *            the prohibitRCFanSpeed to set
	 */
	public void setProhibitRCFanSpeed(String prohibitRCFanSpeed) {
		this.prohibitRCFanSpeed = prohibitRCFanSpeed;
	}

	/**
	 * @return the prohibitRCFlapMode
	 */
	public String getProhibitRCFlapMode() {
		return prohibitRCFlapMode;
	}

	/**
	 * @param prohibitRCFlapMode
	 *            the prohibitRCFlapMode to set
	 */
	public void setProhibitRCFlapMode(String prohibitRCFlapMode) {
		this.prohibitRCFlapMode = prohibitRCFlapMode;
	}

	/**
	 * @return the prohibitRCEnergySaving
	 */
	public String getProhibitRCEnergySaving() {
		return prohibitRCEnergySaving;
	}

	/**
	 * @param prohibitRCEnergySaving
	 *            the prohibitRCEnergySaving to set
	 */
	public void setProhibitRCEnergySaving(String prohibitRCEnergySaving) {
		this.prohibitRCEnergySaving = prohibitRCEnergySaving;
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
	public String getsLinkAddress() {
		return sLinkAddress;
	}

	/**
	 * @param sLinkAddress
	 *            the sLinkAddress to set
	 */
	public void setsLinkAddress(String sLinkAddress) {
		this.sLinkAddress = sLinkAddress;
	}

	/**
	 * @return the fixedOperationMode
	 */
	public Short getFixedOperationMode() {
		return fixedOperationMode;
	}

	/**
	 * @param fixedOperationMode
	 *            the fixedOperationMode to set
	 */
	public void setFixedOperationMode(Short fixedOperationMode) {
		this.fixedOperationMode = fixedOperationMode;
	}
	//add by shanf
	public String getDeviceModel() {
		return deviceModel;
	}
	//add by shanf
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
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
		result = prime * result + ((iduId == null) ? 0 : iduId.hashCode());
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
		ACConfigVO other = (ACConfigVO) obj;
		if (iduId == null) {
			if (other.iduId != null)
				return false;
		} else if (!iduId.equals(other.iduId))
			return false;
		return true;
	}

}

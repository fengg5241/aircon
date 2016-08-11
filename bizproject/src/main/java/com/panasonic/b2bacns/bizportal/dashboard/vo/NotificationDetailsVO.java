package com.panasonic.b2bacns.bizportal.dashboard.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

public class NotificationDetailsVO {

	private long notificationID;
	private String alarmStatus;
	private String severity;
	private String category;
	private String code;
	private String deviceName;
	private String site;
	private String area;
	private String alarmFixed;
	private Double minlatitude;
	private Double maxlatitude;
	private Double minlongitude;
	private Double maxlongitude;
	private String notificationName;
	private String svg_Location;
	private String alarmOccurred;
	private String counterMeasure;
	private Long svgId;
	private String svgDisplayName;
	private String unitAddress;
	private String deviceType;

	//add by shanf 
	private String deviceModel;
	
	@JsonInclude(Include.NON_NULL)
	private String power;
	@JsonInclude(Include.NON_NULL)
	private String temperature;
	@JsonInclude(Include.NON_NULL)
	private String mode;
	@JsonInclude(Include.NON_NULL)
	private String flapMode;
	@JsonInclude(Include.NON_NULL)
	private String fanSpeed;
	@JsonInclude(Include.NON_NULL)
	private String energy_saving;
	@JsonInclude(Include.NON_NULL)
	private String ecoNavi;
	@JsonInclude(Include.NON_NULL)
	private String roomTemp;
	@JsonInclude(Include.NON_NULL)
	private Long linkODUid;
	@JsonInclude(Include.NON_NULL)
	private String linkODUSVG;
	@JsonInclude(Include.NON_NULL)
	private Long iduId;
	@JsonInclude(Include.NON_NULL)
	private Long oduId;
	@JsonInclude(Include.NON_NULL)
	private Long caId;
	private String customerName;
	@JsonIgnore
	private String sitePath;
	@JsonIgnore
	private String alarmFixedDownload;

	/**
	 * @return the caId
	 */
	public Long getCaId() {
		return caId;
	}

	/**
	 * @param caId
	 *            the caId to set
	 */
	public void setCaId(Long caId) {
		this.caId = caId;
	}

	/**
	 * @return the alarmFixedDownload
	 */
	public String getAlarmFixedDownload() {
		return alarmFixed.equals(BizConstants.HYPHEN) ? null : alarmFixed;
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
		return sitePath == null ? deviceName : sitePath.concat(
				BizConstants.COMMA_STRING).concat(deviceName);
	}

	/**
	 * @param sitePath
	 *            the sitePath to set
	 */
	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
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
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
	 * @return the notificationID
	 */
	public long getNotificationID() {
		return notificationID;
	}

	/**
	 * @param notificationID
	 *            the notificationID to set
	 */
	public void setNotificationID(long notificationID) {
		this.notificationID = notificationID;
	}

	/**
	 * @return the alarmStatus
	 */
	public String getAlarmStatus() {
		return alarmStatus;
	}

	/**
	 * @param alarmStatus
	 *            the alarmStatus to set
	 */
	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the alarmFixed
	 */
	public String getAlarmFixed() {
		return alarmFixed;
	}

	/**
	 * @param alarmFixed
	 *            the alarmFixed to set
	 */
	public void setAlarmFixed(String alarmFixed) {
		this.alarmFixed = alarmFixed;
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
	 * @return the notificationName
	 */
	public String getNotificationName() {
		return notificationName;
	}

	/**
	 * @param notificationName
	 *            the notificationName to set
	 */
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
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
	 * @return the alarmOccurred
	 */
	public String getAlarmOccurred() {
		return alarmOccurred;
	}

	/**
	 * @param alarmOccurred
	 *            the alarmOccurred to set
	 */
	public void setAlarmOccurred(String alarmOccurred) {
		this.alarmOccurred = alarmOccurred;
	}

	/**
	 * @return the counterMeasure
	 */
	public String getCounterMeasure() {
		return counterMeasure;
	}

	/**
	 * @param counterMeasure
	 *            the counterMeasure to set
	 */
	public void setCounterMeasure(String counterMeasure) {
		this.counterMeasure = counterMeasure;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (notificationID ^ (notificationID >>> 32));
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
		NotificationDetailsVO other = (NotificationDetailsVO) obj;
		if (notificationID != other.notificationID)
			return false;
		return true;
	}

}

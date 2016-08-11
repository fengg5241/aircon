package com.panasonic.b2bacns.bizportal.map.vo;

public class IndoorUnitVO {

	private Long id;

	private Long groupId;

	private Double svgMinLatitude;

	private Double svgMinLongitude;

	private Double svgMaxLatitude;

	private Double svgMaxLongitude;

	private String modelName;

	private String address;

	private String state;

	private String mode;

	private Double temprature;

	private Double roomTemprature;

	private Integer econaviMode;

	private String alarmCount;

	private String oduLabel;

	private Long linkODUid;

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
	 * @param oduLabel
	 *            the oduLabel to set
	 */
	public void setOduLabel(String oduLabel) {
		this.oduLabel = oduLabel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Double getSvgMinLatitude() {
		return svgMinLatitude;
	}

	public void setSvgMinLatitude(Double svgMinLatitude) {
		this.svgMinLatitude = svgMinLatitude;
	}

	public Double getSvgMinLongitude() {
		return svgMinLongitude;
	}

	public void setSvgMinLongitude(Double svgMinLongitude) {
		this.svgMinLongitude = svgMinLongitude;
	}

	public Double getSvgMaxLatitude() {
		return svgMaxLatitude;
	}

	public void setSvgMaxLatitude(Double svgMaxLatitude) {
		this.svgMaxLatitude = svgMaxLatitude;
	}

	public Double getSvgMaxLongitude() {
		return svgMaxLongitude;
	}

	public void setSvgMaxLongitude(Double svgMaxLongitude) {
		this.svgMaxLongitude = svgMaxLongitude;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Double getTemprature() {
		return temprature;
	}

	public void setTemprature(Double temprature) {
		this.temprature = temprature;
	}

	public String getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(String alarmCount) {
		this.alarmCount = alarmCount;
	}

	public String getOduLabel() {
		return oduLabel;
	}

	public Double getRoomTemprature() {
		return roomTemprature;
	}

	public void setRoomTemprature(Double roomTemprature) {
		this.roomTemprature = roomTemprature;
	}

	public Integer getEconaviMode() {
		return econaviMode;
	}

	public void setEconaviMode(Integer econaviMode) {
		this.econaviMode = econaviMode;
	}

}

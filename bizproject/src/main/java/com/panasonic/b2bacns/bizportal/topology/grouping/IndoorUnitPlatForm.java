package com.panasonic.b2bacns.bizportal.topology.grouping;



import java.util.Date;

public class IndoorUnitPlatForm {
	

	/*
	 * 
	 * @author pramod
	 * 
	 */
	private String facilityId;
	private String connectionIduAddress;
	private String mainIduAddress;
	private String connectionNumber;
	private String structureDatetime;
	private Integer mainIduFlag;
	private String refrigCircuitId;
	private String connectionType;
	private String centralControlAddress;
	private String Type;
	private String DeviceModel;
	private String DeviceName;
	private String adapterId;
	private String Slink;
	private String centralAddress;
	

	public Date getStructureDatetime2() {
		return structureDatetime2;
	}
	public void setStructureDatetime2(Date structureDatetime2) {
		this.structureDatetime2 = structureDatetime2;
	}
	public String getCentralAddress() {
		return centralAddress;
	}
	public void setCentralAddress(String centralAddress) {
		this.centralAddress = centralAddress;
	}
	public String getSlink() {
		return Slink;
	}
	public void setSlink(String slink) {
		Slink = slink;
	}
	public String getAdapterId() {
		return adapterId;
	}
	public void setAdapterId(String adapterId) {
		this.adapterId = adapterId;
	}
	public String getDeviceModel() {
		return DeviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		DeviceModel = deviceModel;
	}
	public String getDeviceName() {
		return DeviceName;
	}
	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getCentralControlAddress() {
		return centralControlAddress;
	}
	public String setCentralControlAddress(String centralControlAddress) {
		return this.centralControlAddress = centralControlAddress;
	}
	public String getRefrigCircuitId() {
		return refrigCircuitId;
	}
	public String setRefrigCircuitId(String refrigCircuitId) {
		return this.refrigCircuitId = refrigCircuitId;
	}

	private Date structureDatetime2;

	public String getConnectionType() {
		return connectionType;
	}
	public String setConnectionType(String connectionType) {
		return this.connectionType = connectionType;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String setFacilityId(String facilityId) {
		return this.facilityId = facilityId;
	}
	public String getConnectionIduAddress() {
		return connectionIduAddress;
	}
	public String setConnectionIduAddress(String connectionIduAddress) {
		return this.connectionIduAddress = connectionIduAddress;
	}
	public String getMainIduAddress() {
		return mainIduAddress;
	}
	public String setMainIduAddress(String mainIduAddress2) {
		return this.mainIduAddress = mainIduAddress2;
	}
	public String getConnectionNumber() {
		return connectionNumber;
	}
	public String setConnectionNumber(String connectionNumber) {
		return this.connectionNumber = connectionNumber;
	}
	public String getStructureDatetime() {
		return structureDatetime;
	}
	public void setStructureDatetime(String structureDatetime) {
		this.structureDatetime = structureDatetime;
	}
	public Integer getMainIduFlag() {
		return mainIduFlag;
	}
	public Integer setMainIduFlag(Integer mainIduFlag2) {
		return this.mainIduFlag = mainIduFlag2;
	}
	public Date setStructureDatetime(Date structureDatetime2) {
		// TODO Auto-generated method stub
		return this.structureDatetime2 = structureDatetime2;
	}
	
	public IndoorUnitPlatForm() {
		super();
	}

}

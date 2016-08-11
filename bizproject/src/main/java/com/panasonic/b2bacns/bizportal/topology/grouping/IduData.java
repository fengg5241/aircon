package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.io.Serializable;

public class IduData implements Serializable {
	
	/*
	 * 
	 * @author pramod
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String s_linkaddress;
	private String port_number;
	private String facilityId;
	private String p_c;
	private String model;
	private String deviceName;
	

	private String connectionType;
	private String	refrigCircuitGroupOduId;
	private String	refrigCircuitId;
	private String	connectionNumber;
	
	private String centralControlAddress;
	private String mainIduAddress;
	private String connectionIduAddress;

	
	private String parent_id;
	private String odu_facility_id;
	
	private int id;

	public IduData(String s_linkaddress, String port_number, String facilityId, String p_c, String model,
			String deviceName, String connectionType, String refrigCircuitGroupOduId, String refrigCircuitId,
			String connectionNumber, String centralControlAddress, String mainIduAddress, String connectionIduAddress,
			String parent_id, String odu_facility_id, int id, String s_link) {
		super();
		this.s_linkaddress = s_linkaddress;
		this.port_number = port_number;
		this.facilityId = facilityId;
		this.p_c = p_c;
		this.model = model;
		this.deviceName = deviceName;
		this.connectionType = connectionType;
		this.refrigCircuitGroupOduId = refrigCircuitGroupOduId;
		this.refrigCircuitId = refrigCircuitId;
		this.connectionNumber = connectionNumber;
		this.centralControlAddress = centralControlAddress;
		this.mainIduAddress = mainIduAddress;
		this.connectionIduAddress = connectionIduAddress;
		this.parent_id = parent_id;
		this.odu_facility_id = odu_facility_id;
		this.id = id;
		this.s_link = s_link;
	}





	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getCentralControlAddress() {
		return centralControlAddress;
	}





	public void setCentralControlAddress(String centralControlAddress) {
		this.centralControlAddress = centralControlAddress;
	}





	public String getMainIduAddress() {
		return mainIduAddress;
	}





	public void setMainIduAddress(String mainIduAddress) {
		this.mainIduAddress = mainIduAddress;
	}





	public String getConnectionIduAddress() {
		return connectionIduAddress;
	}





	public void setConnectionIduAddress(String connectionIduAddress) {
		this.connectionIduAddress = connectionIduAddress;
	}


	




	public String getParent_id() {
		return parent_id;
	}





	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}





	public String getOdu_facility_id() {
		return odu_facility_id;
	}





	public void setOdu_facility_id(String odu_facility_id) {
		this.odu_facility_id = odu_facility_id;
	}





	public String getConnectionNumber() {
		return connectionNumber;
	}





	public void setConnectionNumber(String connectionNumber) {
		this.connectionNumber = connectionNumber;
	}





	private String	s_link;
	
	
	
	
	
	public String getS_linkaddress() {
		return s_linkaddress;
	}





	public void setS_linkaddress(String s_linkaddress) {
		this.s_linkaddress = s_linkaddress;
	}





	public String getPort_number() {
		return port_number;
	}





	public void setPort_number(String port_number) {
		this.port_number = port_number;
	}





	public String getFacilityId() {
		return facilityId;
	}





	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}





	public String getP_c() {
		return p_c;
	}





	public void setP_c(String p_c) {
		this.p_c = p_c;
	}





	public String getModel() {
		return model;
	}





	public void setModel(String model) {
		this.model = model;
	}





	public String getDeviceName() {
		return deviceName;
	}





	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}





	public String getConnectionType() {
		return connectionType;
	}





	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}





	public String getRefrigCircuitGroupOduId() {
		return refrigCircuitGroupOduId;
	}





	public void setRefrigCircuitGroupOduId(String refrigCircuitGroupOduId) {
		this.refrigCircuitGroupOduId = refrigCircuitGroupOduId;
	}





	public String getRefrigCircuitId() {
		return refrigCircuitId;
	}





	public void setRefrigCircuitId(String refrigCircuitId) {
		this.refrigCircuitId = refrigCircuitId;
	}





	public String getS_link() {
		return s_link;
	}





	public void setS_link(String s_link) {
		this.s_link = s_link;
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	public IduData() {
		super();
	}
	
}

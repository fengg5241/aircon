package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.util.Date;

public class PulseMeterPf {

	/*
	 * 
	 * @author pramod
	 * 
	 */
	private String facilityId;
	private String connectionType;
	private String connectionNumber;
	private Date structureDatetime;
	private String port_number;
	private int adapteid;
	private String type;
	private String Dname;
	private String Model;
	private String metertype;
	private String multi_factor;
	
	public String getMulti_factor() {
		return multi_factor;
	}
	public void setMulti_factor(String multi_factor) {
		this.multi_factor = multi_factor;
	}
	public String getMetertype() {
		return metertype;
	}
	public void setMetertype(String metertype) {
		this.metertype = metertype;
	}
	public String getDname() {
		return Dname;
	}
	public void setDname(String dname) {
		Dname = dname;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAdapteid() {
		return adapteid;
	}
	public void setAdapteid(int adapteid) {
		this.adapteid = adapteid;
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
	public String setFacilityId(String facilityId) {
		return this.facilityId = facilityId;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public String setConnectionType(String connectionType) {
		return this.connectionType = connectionType;
	}
	public String getConnectionNumber() {
		return connectionNumber;
	}
	public String setConnectionNumber(String connectionNumber) {
		return this.connectionNumber = connectionNumber;
	}
	public Date getStructureDatetime() {
		return structureDatetime;
	}
	public Date setStructureDatetime(Date structureDatetime2) {
		return this.structureDatetime = structureDatetime2;
	}
	
}

package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.io.Serializable;

public class Topology implements Serializable {

	/*
	 * 
	 * @author pramod
	 * 
	 */
	private String model;
private int id;
	
	public String getMetertype() {
		return metertype;
	}

	public void setMetertype(String metertype) {
		this.metertype = metertype;
	}

	
	public Topology(String model, int id, String deviceName, String port_number, String facilityId, String metertype,
			String multi_factor, String connectionType) {
		super();
		this.model = model;
		this.id = id;
		this.deviceName = deviceName;
		this.port_number = port_number;
		this.facilityId = facilityId;
		this.metertype = metertype;
		this.multi_factor = multi_factor;
		this.connectionType = connectionType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMulti_factor() {
		return multi_factor;
	}

	public void setMulti_factor(String multi_factor) {
		this.multi_factor = multi_factor;
	}

	private String deviceName;
	private String port_number;
	private String facilityId;
	
	private String metertype;
	private String multi_factor;


	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	private String connectionType;;

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public Topology() {
		super();
	}

	public String getPort_number() {
		return port_number;
	}

	public void setPort_number(String port_number) {
		this.port_number = port_number;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

}

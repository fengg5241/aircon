package com.panasonic.b2bacns.bizportal.acconfig.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ODUParamVO {

	private String modelName;
	private String type;
	private String dimension;
	private double currentUtilization = 0;
	private double workingtime = 0;
	private Map<String, String> OduParam = null;
	@JsonIgnore
	private String propertyID;

	public Map<String, String> getOduParam() {
		return OduParam;
	}

	public void setOduParam(Map<String, String> oduParam) {
		OduParam = oduParam;
	}

	public String getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}

	public double getCurrentUtilization() {
		return currentUtilization;
	}

	public void setCurrentUtilization(double currentUtilization) {
		this.currentUtilization = currentUtilization;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public double getWorkingtime() {
		return workingtime;
	}

	public void setWorkingtime(double workingtime) {
		this.workingtime = workingtime;
	}



}

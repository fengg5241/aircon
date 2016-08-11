package com.panasonic.b2bacns.bizportal.map.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class OutdoorUnitVO {

	private Long id;

	private Long groupId;

	private Double svgMinLatitude;

	private Double svgMinLongitude;

	private Double svgMaxLatitude;

	private Double svgMaxLongitude;

	private String modelName;

	private String address;

	private Double workingTime1;

	private Double workingTime2;

	private Double workingTime3;

	private Double utilizationRatio;

	@JsonInclude(Include.NON_NULL)
	private String alarmCount;

	@JsonInclude(Include.NON_NULL)
	private List<LinkedIduUnitVO> linkedIduUnitList;

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

	public Double getWorkingTime1() {
		return workingTime1;
	}

	public void setWorkingTime1(Double workingTime1) {
		this.workingTime1 = workingTime1;
	}

	public Double getWorkingTime2() {
		return workingTime2;
	}

	public void setWorkingTime2(Double workingTime2) {
		this.workingTime2 = workingTime2;
	}

	public Double getWorkingTime3() {
		return workingTime3;
	}

	public void setWorkingTime3(Double workingTime3) {
		this.workingTime3 = workingTime3;
	}

	public Double getUtilizationRatio() {
		return utilizationRatio;
	}

	public void setUtilizationRatio(Double utilizationRatio) {
		this.utilizationRatio = utilizationRatio;
	}

	public String getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(String alarmCount) {
		this.alarmCount = alarmCount;
	}

	public List<LinkedIduUnitVO> getLinkedIduUnitList() {
		return linkedIduUnitList;
	}

	public void setLinkedIduUnitList(List<LinkedIduUnitVO> linkedIduUnitList) {
		this.linkedIduUnitList = linkedIduUnitList;
	}

}

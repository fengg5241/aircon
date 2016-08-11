package com.panasonic.b2bacns.bizportal.map.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class GroupVO {

	@JsonInclude(Include.NON_NULL)
	private Long companyId;

	private Long groupId;

	private String svg;
	
	//add by shanf, for svg map title name
	private String name;

	private Double svgMinLatitude;

	private Double svgMinLongitude;

	private Double svgMaxLatitude;

	private Double svgMaxLongitude;

	@JsonInclude(Include.NON_NULL)
	private List<GroupVO> children;

	@JsonInclude(Include.NON_NULL)
	private List<GroupVO> siblings;

	@JsonInclude(Include.NON_NULL)
	List<IndoorUnitVO> iduList;

	@JsonInclude(Include.NON_NULL)
	List<OutdoorUnitVO> oduList;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getSvg() {
		return svg;
	}

	public void setSvg(String svg) {
		this.svg = svg;
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

	public List<GroupVO> getChildren() {
		return children;
	}

	public void setChildren(List<GroupVO> children) {
		this.children = children;
	}

	public List<GroupVO> getSiblings() {
		return siblings;
	}

	public void setSiblings(List<GroupVO> siblings) {
		this.siblings = siblings;
	}

	public List<IndoorUnitVO> getIduList() {
		return iduList;
	}

	public void setIduList(List<IndoorUnitVO> iduList) {
		this.iduList = iduList;
	}

	public List<OutdoorUnitVO> getOduList() {
		return oduList;
	}

	public void setOduList(List<OutdoorUnitVO> oduList) {
		this.oduList = oduList;
	}

	//add by shanf
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	//add by shanf
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}

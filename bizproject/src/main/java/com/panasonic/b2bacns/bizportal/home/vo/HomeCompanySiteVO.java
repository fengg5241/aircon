package com.panasonic.b2bacns.bizportal.home.vo;

import java.util.List;

import com.panasonic.b2bacns.bizportal.map.vo.GroupVO;

public class HomeCompanySiteVO {

	private long companyId;
	
	private String svgFileName;
	
	private String svgMapName;
	
	private List<GroupVO> sites;

	/**
	 * @return the companyId
	 */
	public long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the svgMapName
	 */
	public String getSvgMapName() {
		return svgMapName;
	}

	/**
	 * @param svgMapName the svgMapName to set
	 */
	public void setSvgMapName(String svgMapName) {
		this.svgMapName = svgMapName;
	}

	/**
	 * @return the sites
	 */
	public List<GroupVO> getSites() {
		return sites;
	}

	/**
	 * @param sites the sites to set
	 */
	public void setSites(List<GroupVO> sites) {
		this.sites = sites;
	}

	/**
	 * @return the svgFileName
	 */
	public String getSvgFileName() {
		return svgFileName;
	}

	/**
	 * @param svgFileName the svgFileName to set
	 */
	public void setSvgFileName(String svgFileName) {
		this.svgFileName = svgFileName;
	}
}

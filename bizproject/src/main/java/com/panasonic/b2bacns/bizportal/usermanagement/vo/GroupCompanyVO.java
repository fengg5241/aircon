package com.panasonic.b2bacns.bizportal.usermanagement.vo;

import java.util.List;

public class GroupCompanyVO {
	
	private long companyID;

	private String companyName;

	private List<GroupUserVO> groups;
	
	
	//added by seshu.
	private String text;
	
	private List<GroupUserVO> children;
	
	/**
	 * @return the text
	 */
	public final String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public final void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the children
	 */
	public final List<GroupUserVO> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public final void setChildren(List<GroupUserVO> children) {
		this.children = children;
	}
	

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<GroupUserVO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupUserVO> groups) {
		this.groups = groups;
	}
	
	

}

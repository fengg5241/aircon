/**
 * 
 */
package com.panasonic.b2bacns.bizportal.group.controller;

import java.util.List;

import com.panasonic.b2bacns.bizportal.group.vo.GroupLeftMenuVO;

/**
 * @author simanchal.patra
 *
 */
public class GroupsByCompany {

	private long companyID;
	
	//change by shanf
	private String text;
	
	private List<GroupLeftMenuVO> children;

	/**
	 * @return the companyID
	 */
	public final long getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID
	 *            the companyID to set
	 */
	public final void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

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
	public final List<GroupLeftMenuVO> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public final void setChildren(List<GroupLeftMenuVO> children) {
		this.children = children;
	}

}

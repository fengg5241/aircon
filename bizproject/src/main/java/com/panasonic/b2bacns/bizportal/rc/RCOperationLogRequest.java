/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

import java.util.List;

/**
 * @author simanchal.patra
 *
 */
public class RCOperationLogRequest {

	private List<Long> unitIDs;

	private String fromDateTime;

	private String toDateTime;
	
	private Integer pageNo;

	/**
	 * @return
	 */
	public final Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 */
	public final void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the unitIDs
	 */
	public final List<Long> getUnitIDs() {
		return unitIDs;
	}

	/**
	 * @param unitIDs
	 *            the unitIDs to set
	 */
	public final void setUnitIDs(List<Long> unitIDs) {
		this.unitIDs = unitIDs;
	}

	/**
	 * @return the fromDateTime
	 */
	public final String getFromDateTime() {
		return fromDateTime;
	}

	/**
	 * @param fromDateTime
	 *            the fromDateTime to set
	 */
	public final void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	/**
	 * @return the toDateTime
	 */
	public final String getToDateTime() {
		return toDateTime;
	}

	/**
	 * @param toDateTime
	 *            the toDateTime to set
	 */
	public final void setToDateTime(String toDateTime) {
		this.toDateTime = toDateTime;
	}

}

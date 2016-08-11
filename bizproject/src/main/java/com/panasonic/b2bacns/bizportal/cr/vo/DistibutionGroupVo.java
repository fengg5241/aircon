/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author simanchal.patra
 *
 */
@JsonAutoDetect
public class DistibutionGroupVo implements Serializable {

	private static final long serialVersionUID = 374746636841641543L;

	private Long id;

	private String groupName;

	private String typeMeasurment;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the typeMeasurment
	 */
	public String getTypeMeasurment() {
		return typeMeasurment;
	}

	/**
	 * @param typeMeasurment
	 *            the typeMeasurment to set
	 */
	public void setTypeMeasurment(String typeMeasurment) {
		this.typeMeasurment = typeMeasurment;
	}

}

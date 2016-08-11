/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author simanchal.patra
 *
 */
@JsonAutoDetect
public class IDUAreaMapping implements Serializable {

	private static final long serialVersionUID = -5780021819829626536L;

	// [{"iduId":1,"areaId":1},{"iduId":2,"areaId":2}, {"iduId":3,"areaId":null}….{}]

	@JsonProperty
	private Long iduId;

	@JsonProperty
	private Long areaId;

	public IDUAreaMapping() {
	}

	public IDUAreaMapping(Long iduId, Long areaId) {
		this.iduId = iduId;
		this.areaId = areaId;
	}

	/**
	 * @return the iduId
	 */
	public Long getIduId() {
		return iduId;
	}

	/**
	 * @param iduId
	 *            the iduId to set
	 */
	public void setIduId(Long iduId) {
		this.iduId = iduId;
	}

	/**
	 * @return the areaId
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

}

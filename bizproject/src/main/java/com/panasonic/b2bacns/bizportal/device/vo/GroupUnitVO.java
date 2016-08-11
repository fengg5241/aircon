/**
 * 
 */
package com.panasonic.b2bacns.bizportal.device.vo;

/**
 * @author akansha
 *
 */
public class GroupUnitVO {
	
	private int id;

	private Long indoorunit_id;

	private Long group_id;

	private Long outdoorunit_id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the indoorunit_id
	 */
	public Long getIndoorunit_id() {
		return indoorunit_id;
	}

	/**
	 * @param indoorunit_id the indoorunit_id to set
	 */
	public void setIndoorunit_id(Long indoorunit_id) {
		this.indoorunit_id = indoorunit_id;
	}

	/**
	 * @return the group_id
	 */
	public Long getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return the outdoorunit_id
	 */
	public Long getOutdoorunit_id() {
		return outdoorunit_id;
	}

	/**
	 * @param outdoorunit_id the outdoorunit_id to set
	 */
	public void setOutdoorunit_id(Long outdoorunit_id) {
		this.outdoorunit_id = outdoorunit_id;
	}

}

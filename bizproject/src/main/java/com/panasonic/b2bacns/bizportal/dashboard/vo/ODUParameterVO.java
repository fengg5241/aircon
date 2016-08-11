/**
 * 
 */
package com.panasonic.b2bacns.bizportal.dashboard.vo;

/**
 * This class is used for ODUParameter data - It contains its name, type and
 * unit information
 * 
 * @author akansha
 *
 */
public class ODUParameterVO {

	private Long id;

	private String type;

	private String parameterName;

	private String displayName;

	private String parameterUnit;

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
	 * @return the oDUType
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param oDUType
	 *            the oDUType to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @param parameterName
	 *            the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the parameterUnit
	 */
	public String getParameterUnit() {
		return parameterUnit;
	}

	/**
	 * @param parameterUnit
	 *            the parameterUnit to set
	 */
	public void setParameterUnit(String parameterUnit) {
		this.parameterUnit = parameterUnit;
	}

}

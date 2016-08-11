/**
 * 
 */
package com.panasonic.b2bacns.bizportal.group.vo;

/**
 * @author akansha
 * @author Ravi
 *
 */
public class IDUListVO {

	private Long id;

	private String name;

	private String pathName;

	private String companyName;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location path
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * @param pathName
	 *            the location info 
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param company name
	 *            the company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

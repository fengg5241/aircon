package com.panasonic.b2bacns.bizportal.home.vo;

public class LocationVO {
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	private long id;
	
	private String name;

	public LocationVO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}

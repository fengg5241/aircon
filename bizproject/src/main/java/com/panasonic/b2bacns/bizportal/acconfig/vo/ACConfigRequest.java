package com.panasonic.b2bacns.bizportal.acconfig.vo;

import java.util.List;

public class ACConfigRequest {

	private List<Long> id;
	private String idType;
	private String fileType;
	private String addCustName;

	/**
	 * @return the addCustName
	 */
	public String getAddCustName() {
		return addCustName;
	}

	/**
	 * @param addCustName
	 *            the addCustName to set
	 */
	public void setAddCustName(String addCustName) {
		this.addCustName = addCustName;
	}

	/**
	 * @return the id
	 */
	public List<Long> getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(List<Long> id) {
		this.id = id;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ACConfigRequest [id=" + id + ", idType=" + idType + "]";
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}

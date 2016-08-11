package com.panasonic.b2bacns.bizportal.dashboard.vo;

import java.util.List;

public class NotificationRequestVO {

	private List<Long> id;
	private String idType;
	private List<String> severity;
	private List<String> status;
	private String alarmType;
	private String alarmOccurredStartDate;
	private String alarmOccurredEndDate;
	private String alarmFixedStartDate;
	private String alarmFixedEndDate;
	private String discription = "";
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

	/**
	 * @return the severity
	 */
	public List<String> getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(List<String> severity) {
		this.severity = severity;
	}

	/**
	 * @return the status
	 */
	public List<String> getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(List<String> status) {
		this.status = status;
	}

	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType
	 *            the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return the alarmOccurredStartDate
	 */
	public String getAlarmOccurredStartDate() {
		return alarmOccurredStartDate;
	}

	/**
	 * @param alarmOccurredStartDate
	 *            the alarmOccurredStartDate to set
	 */
	public void setAlarmOccurredStartDate(String alarmOccurredStartDate) {
		this.alarmOccurredStartDate = alarmOccurredStartDate;
	}

	/**
	 * @return the alarmOccurredEndDate
	 */
	public String getAlarmOccurredEndDate() {
		return alarmOccurredEndDate;
	}

	/**
	 * @param alarmOccurredEndDate
	 *            the alarmOccurredEndDate to set
	 */
	public void setAlarmOccurredEndDate(String alarmOccurredEndDate) {
		this.alarmOccurredEndDate = alarmOccurredEndDate;
	}

	/**
	 * @return the alarmFixedStartDate
	 */
	public String getAlarmFixedStartDate() {
		return alarmFixedStartDate;
	}

	/**
	 * @param alarmFixedStartDate
	 *            the alarmFixedStartDate to set
	 */
	public void setAlarmFixedStartDate(String alarmFixedStartDate) {
		this.alarmFixedStartDate = alarmFixedStartDate;
	}

	/**
	 * @return the alarmFixedEndDate
	 */
	public String getAlarmFixedEndDate() {
		return alarmFixedEndDate;
	}

	/**
	 * @param alarmFixedEndDate
	 *            the alarmFixedEndDate to set
	 */
	public void setAlarmFixedEndDate(String alarmFixedEndDate) {
		this.alarmFixedEndDate = alarmFixedEndDate;
	}

	/**
	 * @return the discription
	 */
	public String getDiscription() {
		return discription;
	}

	/**
	 * @param discription
	 *            the discription to set
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotificationRequestVO [id=" + id + ", idType=" + idType
				+ ", severity=" + severity + ", status=" + status
				+ ", alarmType=" + alarmType + ", alarmOccurredStartDate="
				+ alarmOccurredStartDate + ", alarmOccurredEndDate="
				+ alarmOccurredEndDate + ", alarmFixedStartDate="
				+ alarmFixedStartDate + ", alarmFixedEndDate="
				+ alarmFixedEndDate + ", discription=" + discription
				+ ", addCustName=" + addCustName + "]";
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

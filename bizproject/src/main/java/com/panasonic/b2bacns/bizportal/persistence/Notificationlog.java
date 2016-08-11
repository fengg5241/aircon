package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the notificationlog database table.
 * 
 */
@Entity
@Table(name = "notificationlog")
@NamedQuery(name = "Notificationlog.findAll", query = "SELECT n FROM Notificationlog n")
public class Notificationlog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "alarm_type", length = 255)
	private String alarmType;

	@Column(length = 255)
	private String code;

	@Column(length = 45)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	@Column(length = 500)
	private String description;

	@Column(name = "fixed_time")
	private Timestamp fixedTime;

	@Column(length = 45)
	private String severity;

	@Column(length = 45)
	private String status;

	private Timestamp time;

	private Timestamp updatedate;

	@Column(length = 45)
	private String updatedby;

	// bi-directional many-to-one association to Adapter
	@ManyToOne
	@JoinColumn(name = "adapterid")
	private Adapter adapter;

	// bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name = "indoorunit_id")
	private Indoorunit indoorunit;

	// bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name = "outdoorunit_id")
	private Outdoorunit outdoorunit;

	public Notificationlog() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlarmType() {
		return this.alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Timestamp getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

	/**
	 * @return the fixedTime
	 */
	public Timestamp getFixedTime() {
		return fixedTime;
	}

	/**
	 * @param fixedTime
	 *            the fixedTime to set
	 */
	public void setFixedTime(Timestamp fixedTime) {
		this.fixedTime = fixedTime;
	}

}
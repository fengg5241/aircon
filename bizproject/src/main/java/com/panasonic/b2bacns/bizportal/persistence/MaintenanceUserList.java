package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the maintenance_user_list database table.
 * 
 */
@Entity
@Table(name = "maintenance_user_list")
@NamedQuery(name = "MaintenanceUserList.findAll", query = "SELECT m FROM MaintenanceUserList m")
public class MaintenanceUserList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MAINTENANCE_USER_LIST_ID_GENERATOR", sequenceName = "maintenance_user_list_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAINTENANCE_USER_LIST_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Long id;

	private Boolean issend;

	@Column(name = "user_mail_id", length = 256)
	private String userMailId;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public MaintenanceUserList() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIssend() {
		return this.issend;
	}

	public void setIssend(Boolean issend) {
		this.issend = issend;
	}

	public String getUserMailId() {
		return this.userMailId;
	}

	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
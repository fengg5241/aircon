package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the rolefunctionalgrp database table.
 * 
 */
@Entity
@NamedQuery(name="Rolefunctionalgrp.findAll", query="SELECT r FROM Rolefunctionalgrp r")
@Table(name = "rolefunctionalgrp")
public class Rolefunctionalgrp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name="roleid")
	private Integer roleid;

	@Column(name="companyid")
	private Integer companyid;

	@Column(name="createdby")
	private String createdby;

	@Column(name="creationdate")
	private Timestamp creationdate;

	@Column(name="funcgroupids")
	private Integer funcgroupids;

	@Column(name="updatedate")
	private Timestamp updatedate;

	@Column(name="updatedby")
	private String updatedby;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "companyid")
	private Company company;

	
	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "roleid")
	private Role role;
	
	// bi-directional many-to-one association to Companyrole
	/*@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "roleid")
	private Companyrole companyrole;*/

	
	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "funcgroupids")
	private Functionalgroup functGrp;
	
	/*
	 * //bi-directional many-to-one association to Roletype
	 * 
	 * @ManyToOne private Roletype roletype;
	 */
	public Rolefunctionalgrp() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getFuncgroupids() {
		return this.funcgroupids;
	}

	public void setFuncgroupids(Integer funcgroupids) {
		this.funcgroupids = funcgroupids;
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

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

/*	public Companyrole getCompanyrole() {
		return this.companyrole;
	}

	public void setCompanyrole(Companyrole companyrole) {
		this.companyrole = companyrole;
	}*/

	/*
	 * public Roletype getRoletype() { return this.roletype; }
	 * 
	 * public void setRoletype(Roletype roletype) { this.roletype = roletype; }
	 */

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Functionalgroup getFunctGrp() {
		return functGrp;
	}

	public void setFunctGrp(Functionalgroup functGrp) {
		this.functGrp = functGrp;
	}

	
	
}

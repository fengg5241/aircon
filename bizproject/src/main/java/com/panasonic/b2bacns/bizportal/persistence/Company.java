package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the companies database table.
 * 
 */
@Entity
@Table(name = "companies")
@NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 45)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	@Column(precision = 10)
	private BigDecimal deploymentid;

	@Column(length = 255)
	private String imagepath;

	private Boolean isdel;

	@Column(nullable = false, length = 45)
	private String name;

	@Column(length=255)
	private String country;
	
	@Column(length=255)
	private String address;
	
	@Column(length=255)
	private String postal_code;
	
	@Column(length=255)
	private Integer status;
	
	private Timestamp updatedate;

	@Column(length = 45)
	private String updatedby;

	// bi-directional many-to-one association to Adapter
	@OneToMany(mappedBy = "company")
	private List<Adapter> adapters;

	// bi-directional many-to-one association to Companiesuser
	@OneToMany(mappedBy = "company")
	private List<Companiesuser> companiesusers;

	// bi-directional many-to-one association to Group
	@OneToMany(mappedBy = "company")
	private List<Group> groups;

	// bi-directional many-to-one association to MaintenanceUserList
	@OneToMany(mappedBy = "company")
	private List<MaintenanceUserList> maintenanceUserLists;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<User> users;

	/*
	 * //bi-directional many-to-one association to Notificationsetting
	 * 
	 * @OneToMany(mappedBy="company") private List<Notificationsetting>
	 * notificationsettings;
	 */

	public Company() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public BigDecimal getDeploymentid() {
		return this.deploymentid;
	}

	public void setDeploymentid(BigDecimal deploymentid) {
		this.deploymentid = deploymentid;
	}

	public String getImagepath() {
		return this.imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public Boolean getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostal_code() {
		return this.postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<Adapter> getAdapters() {
		return this.adapters;
	}

	public void setAdapters(List<Adapter> adapters) {
		this.adapters = adapters;
	}

	public Adapter addAdapter(Adapter adapter) {
		getAdapters().add(adapter);
		adapter.setCompany(this);

		return adapter;
	}

	public Adapter removeAdapter(Adapter adapter) {
		getAdapters().remove(adapter);
		adapter.setCompany(null);

		return adapter;
	}

	public List<Companiesuser> getCompaniesusers() {
		return this.companiesusers;
	}

	public void setCompaniesusers(List<Companiesuser> companiesusers) {
		this.companiesusers = companiesusers;
	}

	public Companiesuser addCompaniesuser(Companiesuser companiesuser) {
		getCompaniesusers().add(companiesuser);
		companiesuser.setCompany(this);

		return companiesuser;
	}

	public Companiesuser removeCompaniesuser(Companiesuser companiesuser) {
		getCompaniesusers().remove(companiesuser);
		companiesuser.setCompany(null);

		return companiesuser;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setCompany(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setCompany(null);

		return group;
	}

	public List<MaintenanceUserList> getMaintenanceUserLists() {
		return this.maintenanceUserLists;
	}

	public void setMaintenanceUserLists(
			List<MaintenanceUserList> maintenanceUserLists) {
		this.maintenanceUserLists = maintenanceUserLists;
	}

	public MaintenanceUserList addMaintenanceUserList(
			MaintenanceUserList maintenanceUserList) {
		getMaintenanceUserLists().add(maintenanceUserList);
		maintenanceUserList.setCompany(this);

		return maintenanceUserList;
	}

	public MaintenanceUserList removeMaintenanceUserList(
			MaintenanceUserList maintenanceUserList) {
		getMaintenanceUserLists().remove(maintenanceUserList);
		maintenanceUserList.setCompany(null);

		return maintenanceUserList;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

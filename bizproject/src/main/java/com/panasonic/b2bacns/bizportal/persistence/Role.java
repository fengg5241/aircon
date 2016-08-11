package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String createdby;
	
	@Column(length=16)
	private int roletype_id;

	private Timestamp creationdate;

	private Boolean isdel;

	@Column(length=255)
	private String name;

	private Timestamp updatedate;

	@Column(length=255)
	private String updatedby;
	
	// bi-directional many-to-one association to Role Type
	@ManyToOne
	@JoinColumn(insertable=false, updatable=false, name = "roletype_id")
	private Roletype roletype;
	
	@Column(length=255)
	private int company_id;
	

	/*//bi-directional many-to-one association to Rolepermission
	@OneToMany(mappedBy="role")
	private List<Rolepermission> rolepermissions;*/

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="role",cascade=CascadeType.ALL)
	private List<User> users;
	
	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="role",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Rolefunctionalgrp> roleFunctGrp;

	public Role() {
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

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

/*	public List<Rolepermission> getRolepermissions() {
		return this.rolepermissions;
	}*/
	
	

	public Roletype getRoletype() {
		return roletype;
	}

	public void setRoletype(Roletype roletype) {
		this.roletype = roletype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

/*	public void setRolepermissions(List<Rolepermission> rolepermissions) {
		this.rolepermissions = rolepermissions;
	}

	public Rolepermission addRolepermission(Rolepermission rolepermission) {
		getRolepermissions().add(rolepermission);
		rolepermission.setRole(this);

		return rolepermission;
	}*/

/*	public Rolepermission removeRolepermission(Rolepermission rolepermission) {
		getRolepermissions().remove(rolepermission);
		rolepermission.setRole(null);

		return rolepermission;
	}
	*/
	

	public int getRoletype_id() {
		return roletype_id;
	}

	public void setRoletype_id(int roletype_id) {
		this.roletype_id = roletype_id;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setRole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setRole(null);

		return user;
	}

	public List<Rolefunctionalgrp> getRoleFunctGrp() {
		return roleFunctGrp;
	}

	public void setRoleFunctGrp(List<Rolefunctionalgrp> roleFunctGrp) {
		this.roleFunctGrp = roleFunctGrp;
	}
	
	

}

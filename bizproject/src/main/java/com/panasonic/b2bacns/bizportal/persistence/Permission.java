package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the permissions database table.
 * 
 */
@Entity
@Table(name="permissions")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String createdby;

	private Timestamp creationdate;

	private Boolean isdel;

	private Boolean isleftmenu;

	@Column(length=255)
	private String name;

	private Timestamp updatedate;

	@Column(length=255)
	private String updatedby;

	@Column(length=1024)
	private String url;

	/*//bi-directional many-to-one association to Rolepermission
	@OneToMany(mappedBy="permission")
	private List<Rolepermission> rolepermissions;*/
	
	//bi-directional many-to-one association to FunctionalgroupPermission
	@OneToMany(mappedBy="permission")
	private List<FunctionalgroupPermission> functionalgroupPermissions;

	public Permission() {
	}

	public List<FunctionalgroupPermission> getFunctionalgroupPermissions() {
		return functionalgroupPermissions;
	}

	public void setFunctionalgroupPermissions(
			List<FunctionalgroupPermission> functionalgroupPermissions) {
		this.functionalgroupPermissions = functionalgroupPermissions;
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

	public Boolean getIsleftmenu() {
		return this.isleftmenu;
	}

	public void setIsleftmenu(Boolean isleftmenu) {
		this.isleftmenu = isleftmenu;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

/*	public List<Rolepermission> getRolepermissions() {
		return this.rolepermissions;
	}

	public void setRolepermissions(List<Rolepermission> rolepermissions) {
		this.rolepermissions = rolepermissions;
	}*/

/*	public Rolepermission addRolepermission(Rolepermission rolepermission) {
		getRolepermissions().add(rolepermission);
		rolepermission.setPermission(this);

		return rolepermission;
	}

	public Rolepermission removeRolepermission(Rolepermission rolepermission) {
		getRolepermissions().remove(rolepermission);
		rolepermission.setPermission(null);

		return rolepermission;
	}*/

}

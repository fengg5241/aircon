package com.panasonic.b2bacns.bizportal.persistence;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the functionalgroup_permission database table.
 * 
 */
@Entity
@Table(name="functionalgroup_permission")
@NamedQuery(name="FunctionalgroupPermission.findAll", query="SELECT f FROM FunctionalgroupPermission f")
public class FunctionalgroupPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	//bi-directional many-to-one association to Functionalgroup
	@ManyToOne
	@JoinColumn(name="functional_groupid")
	private Functionalgroup functionalgroup;

	//bi-directional many-to-one association to Permission
	@ManyToOne
	@JoinColumn(name="permissionid")
	private Permission permission;

	public FunctionalgroupPermission() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Functionalgroup getFunctionalgroup() {
		return this.functionalgroup;
	}

	public void setFunctionalgroup(Functionalgroup functionalgroup) {
		this.functionalgroup = functionalgroup;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

}
package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the roletype database table.
 * 
 */
@Entity
@NamedQuery(name="Roletype.findAll", query="SELECT r FROM Roletype r")
public class Roletype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String roletypename;
	
	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="roletype")
	private List<Role> roles;

	//bi-directional many-to-one association to Rolefunctionalgrp
	/*@OneToMany(mappedBy="roletype")
	private List<Rolefunctionalgrp> rolefunctionalgrps;*/

	//bi-directional many-to-one association to Rolefunctionalgrp
	@OneToMany(mappedBy="roletype")
	private List<Functionalgroup> functionalgroup;
	
	public Roletype() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoletypename() {
		return this.roletypename;
	}

	public void setRoletypename(String roletypename) {
		this.roletypename = roletypename;
	}

	/*public List<Rolefunctionalgrp> getRolefunctionalgrps() {
		return this.rolefunctionalgrps;
	}
	*/
	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*public void setRolefunctionalgrps(List<Rolefunctionalgrp> rolefunctionalgrps) {
		this.rolefunctionalgrps = rolefunctionalgrps;
	}*/

	/*public Rolefunctionalgrp addRolefunctionalgrp(Rolefunctionalgrp rolefunctionalgrp) {
		getRolefunctionalgrps().add(rolefunctionalgrp);
		rolefunctionalgrp.setRoletype(this);

		return rolefunctionalgrp;
	}

	public Rolefunctionalgrp removeRolefunctionalgrp(Rolefunctionalgrp rolefunctionalgrp) {
		getRolefunctionalgrps().remove(rolefunctionalgrp);
		rolefunctionalgrp.setRoletype(null);

		return rolefunctionalgrp;
	}*/

	public List<Functionalgroup> getFunctionalgroup() {
		return functionalgroup;
	}

	public void setFunctionalgroup(List<Functionalgroup> functionalgroup) {
		this.functionalgroup = functionalgroup;
	}

	
}
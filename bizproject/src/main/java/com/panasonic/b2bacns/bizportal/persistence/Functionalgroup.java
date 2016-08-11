package com.panasonic.b2bacns.bizportal.persistence;


import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the functionalgroup database table.
 * 
 */
@Entity
@NamedQuery(name="Functionalgroup.findAll", query="SELECT f FROM Functionalgroup f")
public class Functionalgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="functional_groupid")
	private Long functionalGroupid;

	@Column(name="functional_groupname")
	private String functionalGroupname;

	//bi-directional many-to-one association to FunctionalgroupPermission
	@OneToMany(mappedBy="functionalgroup")
	private List<FunctionalgroupPermission> functionalgroupPermissions;
	
	//bi-directional many-to-one association to FunctionalgroupPermission
	@ManyToOne
	@JoinColumn(name = "roletypeid")
	private Roletype roletype;
	
	
	//bi-directional many-to-one association to FunctionalgroupPermission
	@OneToMany(mappedBy="functGrp")
	private List<Rolefunctionalgrp> roleFunctionalGroup;

	public Functionalgroup() {
	}

	public Long getFunctionalGroupid() {
		return this.functionalGroupid;
	}

	public void setFunctionalGroupid(Long functionalGroupid) {
		this.functionalGroupid = functionalGroupid;
	}

	public String getFunctionalGroupname() {
		return this.functionalGroupname;
	}

	public void setFunctionalGroupname(String functionalGroupname) {
		this.functionalGroupname = functionalGroupname;
	}

	public List<FunctionalgroupPermission> getFunctionalgroupPermissions() {
		return this.functionalgroupPermissions;
	}

	public void setFunctionalgroupPermissions(List<FunctionalgroupPermission> functionalgroupPermissions) {
		this.functionalgroupPermissions = functionalgroupPermissions;
	}

	public FunctionalgroupPermission addFunctionalgroupPermission(FunctionalgroupPermission functionalgroupPermission) {
		getFunctionalgroupPermissions().add(functionalgroupPermission);
		functionalgroupPermission.setFunctionalgroup(this);

		return functionalgroupPermission;
	}

	public FunctionalgroupPermission removeFunctionalgroupPermission(FunctionalgroupPermission functionalgroupPermission) {
		getFunctionalgroupPermissions().remove(functionalgroupPermission);
		functionalgroupPermission.setFunctionalgroup(null);

		return functionalgroupPermission;
	}

	public Roletype getRoletype() {
		return roletype;
	}

	public void setRoletype(Roletype roletype) {
		this.roletype = roletype;
	}

	public List<Rolefunctionalgrp> getRoleFunctionalGroup() {
		return roleFunctionalGroup;
	}

	public void setRoleFunctionalGroup(List<Rolefunctionalgrp> roleFunctionalGroup) {
		this.roleFunctionalGroup = roleFunctionalGroup;
	}

	
}

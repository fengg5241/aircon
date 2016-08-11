package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the outdoorunitparameters database table.
 * 
 */
@Entity
@Table(name="outdoorunitparameters")
@NamedQuery(name="Outdoorunitparameter.findAll", query="SELECT o FROM Outdoorunitparameter o")
public class Outdoorunitparameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="display_name", length=255)
	private String displayName;

	@Column(name="parameter_name", length=255)
	private String parameterName;

	@Column(name="parameter_unit", length=255)
	private String parameterUnit;

	@Column(length=255)
	private String type;

	public Outdoorunitparameter() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterUnit() {
		return this.parameterUnit;
	}

	public void setParameterUnit(String parameterUnit) {
		this.parameterUnit = parameterUnit;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
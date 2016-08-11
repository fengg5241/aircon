package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the groupco2factor database table.
 * 
 */
@Entity
@NamedQuery(name="Groupco2factor.findAll", query="SELECT g FROM Groupco2factor g")
public class Groupco2factor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	private double co2factor;

	private Timestamp logdate;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="groupid")
	private Group group;

	public Groupco2factor() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getCo2factor() {
		return this.co2factor;
	}

	public void setCo2factor(double co2factor) {
		this.co2factor = co2factor;
	}

	public Timestamp getLogdate() {
		return this.logdate;
	}

	public void setLogdate(Timestamp logdate) {
		this.logdate = logdate;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
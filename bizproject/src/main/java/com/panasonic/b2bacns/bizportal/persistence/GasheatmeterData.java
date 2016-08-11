package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gasheatmeter_data database table.
 * 
 */
@Entity
@Table(name="gasheatmeter_data")
@NamedQuery(name="GasheatmeterData.findAll", query="SELECT g FROM GasheatmeterData g")
public class GasheatmeterData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	private Timestamp creationdate;

	private Timestamp logtime;

	@Column(name="totalenergy_consumed")
	private double totalenergyConsumed;

	private Timestamp updatedate;

	//bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name="outdoorunit_id")
	private Outdoorunit outdoorunit;

	public GasheatmeterData() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public Timestamp getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Timestamp logtime) {
		this.logtime = logtime;
	}

	public double getTotalenergyConsumed() {
		return this.totalenergyConsumed;
	}

	public void setTotalenergyConsumed(double totalenergyConsumed) {
		this.totalenergyConsumed = totalenergyConsumed;
	}

	public Timestamp getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

}
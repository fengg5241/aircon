package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gasheatmeter_data_yearly database table.
 * 
 */
@Entity
@Table(name="gasheatmeter_data_yearly")
@NamedQuery(name="GasheatmeterDataYearly.findAll", query="SELECT g FROM GasheatmeterDataYearly g")
public class GasheatmeterDataYearly implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="ghpwater_heat_exchanger")
	private Integer ghpwaterHeatExchanger;

	private Timestamp logtime;

	@Column(name="totalenergy_consumed")
	private double totalenergyConsumed;

	@Column(name="vrfwater_heat_exchanger")
	private Integer vrfwaterHeatExchanger;

	@Column(nullable=false)
	private Integer year;

	//bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name="outdoorunit_id")
	private Outdoorunit outdoorunit;

	public GasheatmeterDataYearly() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGhpwaterHeatExchanger() {
		return this.ghpwaterHeatExchanger;
	}

	public void setGhpwaterHeatExchanger(Integer ghpwaterHeatExchanger) {
		this.ghpwaterHeatExchanger = ghpwaterHeatExchanger;
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

	public Integer getVrfwaterHeatExchanger() {
		return this.vrfwaterHeatExchanger;
	}

	public void setVrfwaterHeatExchanger(Integer vrfwaterHeatExchanger) {
		this.vrfwaterHeatExchanger = vrfwaterHeatExchanger;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

}
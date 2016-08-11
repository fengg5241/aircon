package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gasheatmeter_data_daily database table.
 * 
 */
@Entity
@Table(name="gasheatmeter_data_daily")
@NamedQuery(name="GasheatmeterDataDaily.findAll", query="SELECT g FROM GasheatmeterDataDaily g")
public class GasheatmeterDataDaily implements Serializable {
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

	//bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name="outdoorunit_id")
	private Outdoorunit outdoorunit;

	public GasheatmeterDataDaily() {
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

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

}
package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the power_consumption_capacity_daily database table.
 * 
 */
@Entity
@Table(name="power_consumption_capacity_daily")
@NamedQuery(name="PowerConsumptionCapacityDaily.findAll", query="SELECT p FROM PowerConsumptionCapacityDaily p")
public class PowerConsumptionCapacityDaily implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	private Timestamp logtime;

	@Column(nullable=false)
	private float roomtemp;

	@Column(nullable=false)
	private float setpointtemp;

	@Column(name="total_power_consumption")
	private double totalPowerConsumption;

	//bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name="indoorunit_id")
	private Indoorunit indoorunit;

	public PowerConsumptionCapacityDaily() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Timestamp logtime) {
		this.logtime = logtime;
	}

	public float getRoomtemp() {
		return this.roomtemp;
	}

	public void setRoomtemp(float roomtemp) {
		this.roomtemp = roomtemp;
	}

	public float getSetpointtemp() {
		return this.setpointtemp;
	}

	public void setSetpointtemp(float setpointtemp) {
		this.setpointtemp = setpointtemp;
	}

	public double getTotalPowerConsumption() {
		return this.totalPowerConsumption;
	}

	public void setTotalPowerConsumption(double totalPowerConsumption) {
		this.totalPowerConsumption = totalPowerConsumption;
	}

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

}

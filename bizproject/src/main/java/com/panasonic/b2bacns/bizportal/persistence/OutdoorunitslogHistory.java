package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the outdoorunitslog_history database table.
 * 
 */
@Entity
@Table(name="outdoorunitslog_history")
@NamedQuery(name="OutdoorunitslogHistory.findAll", query="SELECT o FROM OutdoorunitslogHistory o")
public class OutdoorunitslogHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="accumulatedpower_consumption")
	private Integer accumulatedpowerConsumption;

	private Integer checkoil;

	private Integer clutch;

	private Integer compressorheater;

	@Column(nullable=false)
	private float instaneouscurrent;

	private Timestamp nextoilchange;

	private Timestamp nextrefreshmaint;

	private Integer oilpump;

	private double oiltimeafterchange;

	private Integer outdoorhp;

	@Column(name="outdoormodel_info")
	private Integer outdoormodelInfo;

	private float outsideairtemperature;

	private Integer prealarminformation;

	@Column(name="ratedcooling_refrigerant_circulating")
	private Integer ratedcoolingRefrigerantCirculating;

	@Column(nullable=false)
	private double ratedcurrent;

	@Column(name="ratedheating_refrigerant_circulating")
	private Integer ratedheatingRefrigerantCirculating;

	@Column(name="ratedsystem_current")
	private Integer ratedsystemCurrent;

	private Integer refrigerantcirculating;

	private Timestamp time;

	private float utilizationrate;

	@Column(nullable=false)
	private double workingtime;

	@Column(precision=19, scale=2)
	private BigDecimal workingtime1;

	@Column(precision=19, scale=2)
	private BigDecimal workingtime2;

	@Column(precision=19, scale=2)
	private BigDecimal workingtime3;

	//bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name="outdoorunit_id")
	private Outdoorunit outdoorunit;

	public OutdoorunitslogHistory() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAccumulatedpowerConsumption() {
		return this.accumulatedpowerConsumption;
	}

	public void setAccumulatedpowerConsumption(Integer accumulatedpowerConsumption) {
		this.accumulatedpowerConsumption = accumulatedpowerConsumption;
	}

	public Integer getCheckoil() {
		return this.checkoil;
	}

	public void setCheckoil(Integer checkoil) {
		this.checkoil = checkoil;
	}

	public Integer getClutch() {
		return this.clutch;
	}

	public void setClutch(Integer clutch) {
		this.clutch = clutch;
	}

	public Integer getCompressorheater() {
		return this.compressorheater;
	}

	public void setCompressorheater(Integer compressorheater) {
		this.compressorheater = compressorheater;
	}

	public float getInstaneouscurrent() {
		return this.instaneouscurrent;
	}

	public void setInstaneouscurrent(float instaneouscurrent) {
		this.instaneouscurrent = instaneouscurrent;
	}

	public Timestamp getNextoilchange() {
		return this.nextoilchange;
	}

	public void setNextoilchange(Timestamp nextoilchange) {
		this.nextoilchange = nextoilchange;
	}

	public Timestamp getNextrefreshmaint() {
		return this.nextrefreshmaint;
	}

	public void setNextrefreshmaint(Timestamp nextrefreshmaint) {
		this.nextrefreshmaint = nextrefreshmaint;
	}

	public Integer getOilpump() {
		return this.oilpump;
	}

	public void setOilpump(Integer oilpump) {
		this.oilpump = oilpump;
	}

	public double getOiltimeafterchange() {
		return this.oiltimeafterchange;
	}

	public void setOiltimeafterchange(double oiltimeafterchange) {
		this.oiltimeafterchange = oiltimeafterchange;
	}

	public Integer getOutdoorhp() {
		return this.outdoorhp;
	}

	public void setOutdoorhp(Integer outdoorhp) {
		this.outdoorhp = outdoorhp;
	}

	public Integer getOutdoormodelInfo() {
		return this.outdoormodelInfo;
	}

	public void setOutdoormodelInfo(Integer outdoormodelInfo) {
		this.outdoormodelInfo = outdoormodelInfo;
	}

	public float getOutsideairtemperature() {
		return this.outsideairtemperature;
	}

	public void setOutsideairtemperature(float outsideairtemperature) {
		this.outsideairtemperature = outsideairtemperature;
	}

	public Integer getPrealarminformation() {
		return this.prealarminformation;
	}

	public void setPrealarminformation(Integer prealarminformation) {
		this.prealarminformation = prealarminformation;
	}

	public Integer getRatedcoolingRefrigerantCirculating() {
		return this.ratedcoolingRefrigerantCirculating;
	}

	public void setRatedcoolingRefrigerantCirculating(Integer ratedcoolingRefrigerantCirculating) {
		this.ratedcoolingRefrigerantCirculating = ratedcoolingRefrigerantCirculating;
	}

	public double getRatedcurrent() {
		return this.ratedcurrent;
	}

	public void setRatedcurrent(double ratedcurrent) {
		this.ratedcurrent = ratedcurrent;
	}

	public Integer getRatedheatingRefrigerantCirculating() {
		return this.ratedheatingRefrigerantCirculating;
	}

	public void setRatedheatingRefrigerantCirculating(Integer ratedheatingRefrigerantCirculating) {
		this.ratedheatingRefrigerantCirculating = ratedheatingRefrigerantCirculating;
	}

	public Integer getRatedsystemCurrent() {
		return this.ratedsystemCurrent;
	}

	public void setRatedsystemCurrent(Integer ratedsystemCurrent) {
		this.ratedsystemCurrent = ratedsystemCurrent;
	}

	public Integer getRefrigerantcirculating() {
		return this.refrigerantcirculating;
	}

	public void setRefrigerantcirculating(Integer refrigerantcirculating) {
		this.refrigerantcirculating = refrigerantcirculating;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public float getUtilizationrate() {
		return this.utilizationrate;
	}

	public void setUtilizationrate(float utilizationrate) {
		this.utilizationrate = utilizationrate;
	}

	public double getWorkingtime() {
		return this.workingtime;
	}

	public void setWorkingtime(double workingtime) {
		this.workingtime = workingtime;
	}

	public BigDecimal getWorkingtime1() {
		return this.workingtime1;
	}

	public void setWorkingtime1(BigDecimal workingtime1) {
		this.workingtime1 = workingtime1;
	}

	public BigDecimal getWorkingtime2() {
		return this.workingtime2;
	}

	public void setWorkingtime2(BigDecimal workingtime2) {
		this.workingtime2 = workingtime2;
	}

	public BigDecimal getWorkingtime3() {
		return this.workingtime3;
	}

	public void setWorkingtime3(BigDecimal workingtime3) {
		this.workingtime3 = workingtime3;
	}

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

}
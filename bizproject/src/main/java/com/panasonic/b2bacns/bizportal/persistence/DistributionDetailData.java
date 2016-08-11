package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the distribution_detail_data database table.
 * 
 */
@Entity
@Table(name = "distribution_detail_data")
@NamedQuery(name = "DistributionDetailData.findAll", query = "SELECT d FROM DistributionDetailData d")
public class DistributionDetailData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DistributionDetailDataPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cutoffend_actual_time")
	private Date cutoffendActualTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cutoffstart_actual_time")
	private Date cutoffstartActualTime;

	@Column(name = "indoorunit_id")
	private Long indoorunitId;

	@Column(name = "powerusage_kwh")
	private BigDecimal powerusageKwh;

	@Column(name = "pulsemeter_id")
	private Long pulsemeterId;

	@Column(name = "pulsemeter_power_usage")
	private BigDecimal pulsemeterPowerUsage;

	@Column(name = "ratedcapacity_kw")
	private BigDecimal ratedcapacityKw;

	@Column(name = "workinghours_tstat_off_high_fan")
	private BigDecimal workinghoursTstatOffHighFan;

	@Column(name = "workinghours_tstat_off_low_fan")
	private BigDecimal workinghoursTstatOffLowFan;

	@Column(name = "workinghours_tstat_off_med_fan")
	private BigDecimal workinghoursTstatOffMedFan;

	@Column(name = "workinghours_tstat_on_low_fan")
	private BigDecimal workinghoursTstatOnLowFan;

	@Column(name = "workinghours_tstat_on_med_fan")
	private BigDecimal workinghoursTstatOnMedFan;

	@Column(name = "workinghours_tstat_onhigh_fan")
	private BigDecimal workinghoursTstatOnhighFan;

	// bi-directional many-to-one association to CutoffRequest
	@ManyToOne
	@JoinColumn(name = "cutoffreq_id", insertable = false, updatable = false)
	private CutoffRequest cutoffRequest;

	public DistributionDetailData() {
	}

	public DistributionDetailDataPK getId() {
		return this.id;
	}

	public void setId(DistributionDetailDataPK id) {
		this.id = id;
	}

	public Date getCutoffendActualTime() {
		return this.cutoffendActualTime;
	}

	public void setCutoffendActualTime(Date cutoffendActualTime) {
		this.cutoffendActualTime = cutoffendActualTime;
	}

	public Date getCutoffstartActualTime() {
		return this.cutoffstartActualTime;
	}

	public void setCutoffstartActualTime(Date cutoffstartActualTime) {
		this.cutoffstartActualTime = cutoffstartActualTime;
	}

	public Long getIndoorunitId() {
		return this.indoorunitId;
	}

	public void setIndoorunitId(Long indoorunitId) {
		this.indoorunitId = indoorunitId;
	}

	public BigDecimal getPowerusageKwh() {
		return this.powerusageKwh;
	}

	public void setPowerusageKwh(BigDecimal powerusageKwh) {
		this.powerusageKwh = powerusageKwh;
	}

	public Long getPulsemeterId() {
		return this.pulsemeterId;
	}

	public void setPulsemeterId(Long pulsemeterId) {
		this.pulsemeterId = pulsemeterId;
	}

	public BigDecimal getPulsemeterPowerUsage() {
		return this.pulsemeterPowerUsage;
	}

	public void setPulsemeterPowerUsage(BigDecimal pulsemeterPowerUsage) {
		this.pulsemeterPowerUsage = pulsemeterPowerUsage;
	}

	public BigDecimal getRatedcapacityKw() {
		return this.ratedcapacityKw;
	}

	public void setRatedcapacityKw(BigDecimal ratedcapacityKw) {
		this.ratedcapacityKw = ratedcapacityKw;
	}

	public BigDecimal getWorkinghoursTstatOffHighFan() {
		return this.workinghoursTstatOffHighFan;
	}

	public void setWorkinghoursTstatOffHighFan(
			BigDecimal workinghoursTstatOffHighFan) {
		this.workinghoursTstatOffHighFan = workinghoursTstatOffHighFan;
	}

	public BigDecimal getWorkinghoursTstatOffLowFan() {
		return this.workinghoursTstatOffLowFan;
	}

	public void setWorkinghoursTstatOffLowFan(
			BigDecimal workinghoursTstatOffLowFan) {
		this.workinghoursTstatOffLowFan = workinghoursTstatOffLowFan;
	}

	public BigDecimal getWorkinghoursTstatOffMedFan() {
		return this.workinghoursTstatOffMedFan;
	}

	public void setWorkinghoursTstatOffMedFan(
			BigDecimal workinghoursTstatOffMedFan) {
		this.workinghoursTstatOffMedFan = workinghoursTstatOffMedFan;
	}

	public BigDecimal getWorkinghoursTstatOnLowFan() {
		return this.workinghoursTstatOnLowFan;
	}

	public void setWorkinghoursTstatOnLowFan(
			BigDecimal workinghoursTstatOnLowFan) {
		this.workinghoursTstatOnLowFan = workinghoursTstatOnLowFan;
	}

	public BigDecimal getWorkinghoursTstatOnMedFan() {
		return this.workinghoursTstatOnMedFan;
	}

	public void setWorkinghoursTstatOnMedFan(
			BigDecimal workinghoursTstatOnMedFan) {
		this.workinghoursTstatOnMedFan = workinghoursTstatOnMedFan;
	}

	public BigDecimal getWorkinghoursTstatOnhighFan() {
		return this.workinghoursTstatOnhighFan;
	}

	public void setWorkinghoursTstatOnhighFan(
			BigDecimal workinghoursTstatOnhighFan) {
		this.workinghoursTstatOnhighFan = workinghoursTstatOnhighFan;
	}

	public CutoffRequest getCutoffRequest() {
		return this.cutoffRequest;
	}

	public void setCutoffRequest(CutoffRequest cutoffRequest) {
		this.cutoffRequest = cutoffRequest;
	}

}

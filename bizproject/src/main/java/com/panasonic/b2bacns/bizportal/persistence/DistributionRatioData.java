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
 * The persistent class for the distribution_ratio_data database table.
 * 
 */
@Entity
@Table(name = "distribution_ratio_data")
@NamedQuery(name = "DistributionRatioData.findAll", query = "SELECT d FROM DistributionRatioData d")
public class DistributionRatioData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DistributionRatioDataPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cutoffend_actual_time")
	private Date cutoffendActualTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cutoffstart_actual_time")
	private Date cutoffstartActualTime;

	@Column(name = "indoorunit_id")
	private Long indoorunitId;

	@Column(name = "powerdistribution_ratio")
	private Double powerdistributionRatio;

	@Column(name = "powerusage_kwh")
	private BigDecimal powerusageKwh;

	@Column(name = "pulsemeter_id")
	private Long pulsemeterId;

	// bi-directional many-to-one association to CutoffRequest
	@ManyToOne
	@JoinColumn(name = "cutoffreq_id", insertable=false, updatable=false)
	private CutoffRequest cutoffRequest;

	public DistributionRatioData() {
	}

	public DistributionRatioDataPK getId() {
		return this.id;
	}

	public void setId(DistributionRatioDataPK id) {
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

	public Double getPowerdistributionRatio() {
		return this.powerdistributionRatio;
	}

	public void setPowerdistributionRatio(Double powerdistributionRatio) {
		this.powerdistributionRatio = powerdistributionRatio;
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

	public CutoffRequest getCutoffRequest() {
		return this.cutoffRequest;
	}

	public void setCutoffRequest(CutoffRequest cutoffRequest) {
		this.cutoffRequest = cutoffRequest;
	}

}

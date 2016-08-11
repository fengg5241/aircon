package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the indoorunitstatistics_yearly database table.
 * 
 */
@Entity
@Table(name="indoorunitstatistics_yearly")
@NamedQuery(name="IndoorunitstatisticsYearly.findAll", query="SELECT i FROM IndoorunitstatisticsYearly i")
public class IndoorunitstatisticsYearly implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	private Integer filtersignclear;

	private Integer instantpower;

	private Timestamp logtime;

	private Integer otelectricheater;

	@Column(nullable=false)
	private Integer othighfscooling;

	@Column(nullable=false)
	private Integer othighfsheating;

	@Column(nullable=false)
	private Integer othighfsthermoff;

	@Column(nullable=false)
	private Integer othighfsthermon;

	@Column(nullable=false)
	private Integer otlowfscooling;

	@Column(nullable=false)
	private Integer otlowfsheating;

	@Column(nullable=false)
	private Integer otlowfsthermoff;

	@Column(nullable=false)
	private Integer otlowfsthermon;

	@Column(nullable=false)
	private Integer otmediumfscooling;

	@Column(nullable=false)
	private Integer otmediumfsheating;

	@Column(nullable=false)
	private Integer otmediumfsthermoff;

	@Column(nullable=false)
	private Integer otmediumfsthermon;

	private Integer settablefanspeed;

	private Integer settablemode;

	private Timestamp updatefiltersign;

	private Integer ventilation;

	@Column(nullable=false)
	private Integer year;

	//bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name="indoorunit_id")
	private Indoorunit indoorunit;

	public IndoorunitstatisticsYearly() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFiltersignclear() {
		return this.filtersignclear;
	}

	public void setFiltersignclear(Integer filtersignclear) {
		this.filtersignclear = filtersignclear;
	}

	public Integer getInstantpower() {
		return this.instantpower;
	}

	public void setInstantpower(Integer instantpower) {
		this.instantpower = instantpower;
	}

	public Timestamp getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Timestamp logtime) {
		this.logtime = logtime;
	}

	public Integer getOtelectricheater() {
		return this.otelectricheater;
	}

	public void setOtelectricheater(Integer otelectricheater) {
		this.otelectricheater = otelectricheater;
	}

	public Integer getOthighfscooling() {
		return this.othighfscooling;
	}

	public void setOthighfscooling(Integer othighfscooling) {
		this.othighfscooling = othighfscooling;
	}

	public Integer getOthighfsheating() {
		return this.othighfsheating;
	}

	public void setOthighfsheating(Integer othighfsheating) {
		this.othighfsheating = othighfsheating;
	}

	public Integer getOthighfsthermoff() {
		return this.othighfsthermoff;
	}

	public void setOthighfsthermoff(Integer othighfsthermoff) {
		this.othighfsthermoff = othighfsthermoff;
	}

	public Integer getOthighfsthermon() {
		return this.othighfsthermon;
	}

	public void setOthighfsthermon(Integer othighfsthermon) {
		this.othighfsthermon = othighfsthermon;
	}

	public Integer getOtlowfscooling() {
		return this.otlowfscooling;
	}

	public void setOtlowfscooling(Integer otlowfscooling) {
		this.otlowfscooling = otlowfscooling;
	}

	public Integer getOtlowfsheating() {
		return this.otlowfsheating;
	}

	public void setOtlowfsheating(Integer otlowfsheating) {
		this.otlowfsheating = otlowfsheating;
	}

	public Integer getOtlowfsthermoff() {
		return this.otlowfsthermoff;
	}

	public void setOtlowfsthermoff(Integer otlowfsthermoff) {
		this.otlowfsthermoff = otlowfsthermoff;
	}

	public Integer getOtlowfsthermon() {
		return this.otlowfsthermon;
	}

	public void setOtlowfsthermon(Integer otlowfsthermon) {
		this.otlowfsthermon = otlowfsthermon;
	}

	public Integer getOtmediumfscooling() {
		return this.otmediumfscooling;
	}

	public void setOtmediumfscooling(Integer otmediumfscooling) {
		this.otmediumfscooling = otmediumfscooling;
	}

	public Integer getOtmediumfsheating() {
		return this.otmediumfsheating;
	}

	public void setOtmediumfsheating(Integer otmediumfsheating) {
		this.otmediumfsheating = otmediumfsheating;
	}

	public Integer getOtmediumfsthermoff() {
		return this.otmediumfsthermoff;
	}

	public void setOtmediumfsthermoff(Integer otmediumfsthermoff) {
		this.otmediumfsthermoff = otmediumfsthermoff;
	}

	public Integer getOtmediumfsthermon() {
		return this.otmediumfsthermon;
	}

	public void setOtmediumfsthermon(Integer otmediumfsthermon) {
		this.otmediumfsthermon = otmediumfsthermon;
	}

	public Integer getSettablefanspeed() {
		return this.settablefanspeed;
	}

	public void setSettablefanspeed(Integer settablefanspeed) {
		this.settablefanspeed = settablefanspeed;
	}

	public Integer getSettablemode() {
		return this.settablemode;
	}

	public void setSettablemode(Integer settablemode) {
		this.settablemode = settablemode;
	}

	public Timestamp getUpdatefiltersign() {
		return this.updatefiltersign;
	}

	public void setUpdatefiltersign(Timestamp updatefiltersign) {
		this.updatefiltersign = updatefiltersign;
	}

	public Integer getVentilation() {
		return this.ventilation;
	}

	public void setVentilation(Integer ventilation) {
		this.ventilation = ventilation;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

}
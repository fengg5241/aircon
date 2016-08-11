package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

/**
 * The persistent class for the metaoutdoorunits database table.
 * 
 */
@Entity
@Table(name = "metaoutdoorunits")
@NamedQuery(name = "Metaoutdoorunit.findAll", query = "SELECT m FROM Metaoutdoorunit m")
public class Metaoutdoorunit implements Serializable {
	private static final long serialVersionUID = 1L;

	


	public BigDecimal getRated_cooling_capacity() {
		return rated_cooling_capacity;
	}

	public void setRated_cooling_capacity(BigDecimal rated_cooling_capacity) {
		this.rated_cooling_capacity = rated_cooling_capacity;
	}

	public BigDecimal getRated_heating_capacity() {
		return rated_heating_capacity;
	}

	public void setRated_heating_capacity(BigDecimal rated_heating_capacity) {
		this.rated_heating_capacity = rated_heating_capacity;
	}

	public BigDecimal getRated_cooling_power() {
		return rated_cooling_power;
	}

	public void setRated_cooling_power(BigDecimal rated_cooling_power) {
		this.rated_cooling_power = rated_cooling_power;
	}

	public BigDecimal getRated_heating_power() {
		return rated_heating_power;
	}

	public void setRated_heating_power(BigDecimal rated_heating_power) {
		this.rated_heating_power = rated_heating_power;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 255)
	private String createdby;

	private Timestamp creationdate;

	@Column(nullable = true)
	private double fuelcounsumptioncooling;

	@Column(nullable = true)
	private double fuelcounsumptionheating;

	@Column(length = 255)
	private String fusedisolatora;

	@Column(nullable = true)
	private double inputpower220vw;

	@Column(nullable = true)
	private double inputpower230vw;

	@Column(nullable = true)
	private double inputpower240vw;

	@Column(nullable = true)
	private Integer inputpower380vw;

	@Column(nullable = true)
	private Integer inputpower400vw;

	@Column(nullable = true)
	private Integer inputpower415vw;

	@Column(length = 255)
	private String kind;

	@Column(length = 255)
	private String logo;

	@Column(length = 255)
	private String modelname;

	@Column(nullable = true)
	private double nominalcoolingkw;

	@Column(nullable = true)
	private double nominalheatingkw;

	@Column(nullable = true)
	private Integer phase;

	@Column(nullable = true)
	private double runningcurrent220va;

	@Column(nullable = true)
	private double runningcurrent230va;

	@Column(nullable = true)
	private double runningcurrent240va;

	@Column(nullable = true)
	private double runningcurrent380va;

	@Column(nullable = true)
	private double runningcurrent400va;

	@Column(nullable = true)
	private double runningcurrent415va;

	@Column(nullable = true)
	private double unitdepthm;

	@Column(nullable = true)
	private double unitheightm;

	@Column(nullable = true)
	private double unitwidthtm;

	private Timestamp updatedate;

	@Column(length = 255)
	private String updatedby;
	
	@Column(nullable = true)
	@Digits(integer=5, fraction=2)	
	private BigDecimal  rated_cooling_capacity;
	
	@Column(nullable = true)
	@Digits(integer=5, fraction=2)	
	private BigDecimal  rated_heating_capacity;
	
	
	@Column(nullable = true)
	@Digits(integer=5, fraction=2)	
	private BigDecimal  rated_cooling_power;
	
	@Column(nullable = true)
	@Digits(integer=5, fraction=2)	
	private BigDecimal  rated_heating_power;
	
	

	// bi-directional many-to-one association to Outdoorunit
	@OneToMany(mappedBy = "metaoutdoorunit")
	private List<Outdoorunit> outdoorunits;

	public Metaoutdoorunit() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public double getFuelcounsumptioncooling() {
		return this.fuelcounsumptioncooling;
	}

	public void setFuelcounsumptioncooling(double fuelcounsumptioncooling) {
		this.fuelcounsumptioncooling = fuelcounsumptioncooling;
	}

	public double getFuelcounsumptionheating() {
		return this.fuelcounsumptionheating;
	}

	public void setFuelcounsumptionheating(double fuelcounsumptionheating) {
		this.fuelcounsumptionheating = fuelcounsumptionheating;
	}

	public String getFusedisolatora() {
		return this.fusedisolatora;
	}

	public void setFusedisolatora(String fusedisolatora) {
		this.fusedisolatora = fusedisolatora;
	}

	public double getInputpower220vw() {
		return this.inputpower220vw;
	}

	public void setInputpower220vw(double inputpower220vw) {
		this.inputpower220vw = inputpower220vw;
	}

	public double getInputpower230vw() {
		return this.inputpower230vw;
	}

	public void setInputpower230vw(double inputpower230vw) {
		this.inputpower230vw = inputpower230vw;
	}

	public double getInputpower240vw() {
		return this.inputpower240vw;
	}

	public void setInputpower240vw(double inputpower240vw) {
		this.inputpower240vw = inputpower240vw;
	}

	public Integer getInputpower380vw() {
		return this.inputpower380vw;
	}

	public void setInputpower380vw(Integer inputpower380vw) {
		this.inputpower380vw = inputpower380vw;
	}

	public Integer getInputpower400vw() {
		return this.inputpower400vw;
	}

	public void setInputpower400vw(Integer inputpower400vw) {
		this.inputpower400vw = inputpower400vw;
	}

	public Integer getInputpower415vw() {
		return this.inputpower415vw;
	}

	public void setInputpower415vw(Integer inputpower415vw) {
		this.inputpower415vw = inputpower415vw;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getModelname() {
		return this.modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public double getNominalcoolingkw() {
		return this.nominalcoolingkw;
	}

	public void setNominalcoolingkw(double nominalcoolingkw) {
		this.nominalcoolingkw = nominalcoolingkw;
	}

	public double getNominalheatingkw() {
		return this.nominalheatingkw;
	}

	public void setNominalheatingkw(double nominalheatingkw) {
		this.nominalheatingkw = nominalheatingkw;
	}

	public Integer getPhase() {
		return this.phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public double getRunningcurrent220va() {
		return this.runningcurrent220va;
	}

	public void setRunningcurrent220va(double runningcurrent220va) {
		this.runningcurrent220va = runningcurrent220va;
	}

	public double getRunningcurrent230va() {
		return this.runningcurrent230va;
	}

	public void setRunningcurrent230va(double runningcurrent230va) {
		this.runningcurrent230va = runningcurrent230va;
	}

	public double getRunningcurrent240va() {
		return this.runningcurrent240va;
	}

	public void setRunningcurrent240va(double runningcurrent240va) {
		this.runningcurrent240va = runningcurrent240va;
	}

	public double getRunningcurrent380va() {
		return this.runningcurrent380va;
	}

	public void setRunningcurrent380va(double runningcurrent380va) {
		this.runningcurrent380va = runningcurrent380va;
	}

	public double getRunningcurrent400va() {
		return this.runningcurrent400va;
	}

	public void setRunningcurrent400va(double runningcurrent400va) {
		this.runningcurrent400va = runningcurrent400va;
	}

	public double getRunningcurrent415va() {
		return this.runningcurrent415va;
	}

	public void setRunningcurrent415va(double runningcurrent415va) {
		this.runningcurrent415va = runningcurrent415va;
	}

	public double getUnitdepthm() {
		return this.unitdepthm;
	}

	public void setUnitdepthm(double unitdepthm) {
		this.unitdepthm = unitdepthm;
	}

	public double getUnitheightm() {
		return this.unitheightm;
	}

	public void setUnitheightm(double unitheightm) {
		this.unitheightm = unitheightm;
	}

	public double getUnitwidthtm() {
		return this.unitwidthtm;
	}

	public void setUnitwidthtm(double unitwidthtm) {
		this.unitwidthtm = unitwidthtm;
	}

	public Timestamp getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public List<Outdoorunit> getOutdoorunits() {
		return this.outdoorunits;
	}

	public void setOutdoorunits(List<Outdoorunit> outdoorunits) {
		this.outdoorunits = outdoorunits;
	}

	public Outdoorunit addOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().add(outdoorunit);
		outdoorunit.setMetaoutdoorunit(this);

		return outdoorunit;
	}

	public Outdoorunit removeOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().remove(outdoorunit);
		outdoorunit.setMetaoutdoorunit(null);

		return outdoorunit;
	}

}

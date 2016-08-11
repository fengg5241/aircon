package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the metaindoorunits database table.
 * @author RSI
 * @author jwchan
 *
 */
@Entity
@Table(name = "metaindoorunits")
@NamedQuery(name = "Metaindoorunit.findAll", query = "SELECT m FROM Metaindoorunit m")
public class Metaindoorunit implements Serializable {
	private static final long serialVersionUID = 1L;

    //Please mark the ID as GenerationType Identity for insertion
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationdate;

	@Column(name = "fixedoperation_heat_mode",  nullable = true)
	private Integer fixedoperationHeatMode;

	@Column(name = "fixedoperation_cool_mode",  nullable = true)
	private Integer fixedoperationCoolMode;
	
	@Column(name = "fixedoperation_dry_mode",  nullable = true)
	private Integer fixedoperationDryMode;
	
	@Column(name = "fixedoperation_fan_mode",  nullable = true)
	private Integer fixedoperationFanMode;
	
	@Column(name = "is3way_system",  nullable = false)
	private boolean is3waySystem;

	@Column(name = "central_control_address",  nullable = true)
	private String centralControlAddress;
	
	private String logo;

	private String modelname;

	@Column(name = "settablefan_speed_auto",  nullable = true)
	private Boolean settablefanSpeedAuto;


	@Column(name = "settablefan_speed_high",  nullable = true)
	private Boolean settablefanSpeedHigh;

	@Column(name = "settablefan_speed_low",  nullable = true)
	private Boolean settablefanSpeedLow;

	@Column(name = "settablefan_speed_medium",  nullable = true)
	private Boolean settablefanSpeedMedium;

	@Column(name = "settablefan_speed_manual")
	private Boolean settablefanSpeedManual;	
	
	@Column(name = "settableflap",  nullable = true)
	private Boolean settableflap;

	@Column(name = "settablemode_auto",  nullable = true)
	private Boolean settablemodeAuto;

	@Column(name = "settablemode_cool",  nullable = true)
	private Boolean settablemodeCool;

	@Column(name = "settablemode_dry",  nullable = true)
	private Boolean settablemodeDry;

	@Column(name = "settablemode_fan",  nullable = true)
	private Boolean settablemodeFan;

	@Column(name = "settablemode_heat",  nullable = true)
	private Boolean settablemodeHeat;

	@Column(name = "settableswing",  nullable = true)
	private Boolean settableswing;
	
	@Column(name = "settableauto_mode",  nullable = true)
	private Boolean settableauto;
	
	@Column(name = "settableenergy_saving_mode",  nullable = true)
	private Boolean settableEnergysaving;

	@Column(name = "settabletemp_limit_lower_auto",  nullable = true)
	private Integer settabletempLimitLowerAuto;

	@Column(name = "settabletemp_limit_lower_cool",  nullable = true)
	private Integer settabletempLimitLowerCool;

	@Column(name = "settabletemp_limit_lower_dry",  nullable = true)
	private Integer settabletempLimitLowerDry;

	@Column(name = "settabletemp_limit_lower_heat",  nullable = true)
	private Integer settabletempLimitLowerHeat;

	@Column(name = "settabletemp_limit_upper_auto",  nullable = true)
	private Integer settabletempLimitUpperAuto;

	@Column(name = "settabletemp_limit_upper_cool",  nullable = true)
	private Integer settabletempLimitUpperCool;

	@Column(name = "settabletemp_limit_upper_dry",  nullable = true)
	private Integer settabletempLimitUpperDry;

	@Column(name = "settabletemp_limit_upper_heat",  nullable = true)
	private Integer settabletempLimitUpperHeat;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private String updatedby;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "metaindoorunit")
	private List<Indoorunit> indoorunits;

	public Metaindoorunit() {
	}

	public String getCentralControlAddress() {
		return centralControlAddress;
	}

	public void setCentralControlAddress(String centralControlAddress) {
		this.centralControlAddress = centralControlAddress;
	}
	public Integer getFixedoperationHeatMode() {
		return fixedoperationHeatMode;
	}

	public void setFixedoperationHeatMode(Integer fixedoperationHeatMode) {
		this.fixedoperationHeatMode = fixedoperationHeatMode;
	}

	public Integer getFixedoperationCoolMode() {
		return fixedoperationCoolMode;
	}

	public void setFixedoperationCoolMode(Integer fixedoperationCoolMode) {
		this.fixedoperationCoolMode = fixedoperationCoolMode;
	}

	public Integer getFixedoperationDryMode() {
		return fixedoperationDryMode;
	}

	public void setFixedoperationDryMode(Integer fixedoperationDryMode) {
		this.fixedoperationDryMode = fixedoperationDryMode;
	}

	public Integer getFixedoperationFanMode() {
		return fixedoperationFanMode;
	}

	public void setFixedoperationFanMode(Integer fixedoperationFanMode) {
		this.fixedoperationFanMode = fixedoperationFanMode;
	}

	public Boolean getSettablefanSpeedManual() {
		return settablefanSpeedManual;
	}

	public void setSettablefanSpeedManual(Boolean settablefanSpeedManual) {
		this.settablefanSpeedManual = settablefanSpeedManual;
	}

	public Boolean getSettableauto() {
		return settableauto;
	}

	public void setSettableauto(Boolean settableauto) {
		this.settableauto = settableauto;
	}

	public Boolean getSettableEnergysaving() {
		return settableEnergysaving;
	}

	public void setSettableEnergysaving(Boolean settableEnergysaving) {
		this.settableEnergysaving = settableEnergysaving;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}


	public boolean getIs3waySystem() {
		return this.is3waySystem;
	}

	public void setIs3waySystem(boolean is3waySystem) {
		this.is3waySystem = is3waySystem;
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

	public Boolean getSettablefanSpeedAuto() {
		return this.settablefanSpeedAuto;
	}

	public void setSettablefanSpeedAuto(Boolean settablefanSpeedAuto) {
		this.settablefanSpeedAuto = settablefanSpeedAuto;
	}

	public Boolean getSettablefanSpeedHigh() {
		return this.settablefanSpeedHigh;
	}

	public void setSettablefanSpeedHigh(Boolean settablefanSpeedHigh) {
		this.settablefanSpeedHigh = settablefanSpeedHigh;
	}

	public Boolean getSettablefanSpeedLow() {
		return this.settablefanSpeedLow;
	}

	public void setSettablefanSpeedLow(Boolean settablefanSpeedLow) {
		this.settablefanSpeedLow = settablefanSpeedLow;
	}

	public Boolean getSettablefanSpeedMedium() {
		return this.settablefanSpeedMedium;
	}

	public void setSettablefanSpeedMedium(Boolean settablefanSpeedMedium) {
		this.settablefanSpeedMedium = settablefanSpeedMedium;
	}

	public Boolean getSettableflap() {
		return this.settableflap;
	}

	public void setSettableflap(Boolean settableflap) {
		this.settableflap = settableflap;
	}

	public Boolean getSettablemodeAuto() {
		return this.settablemodeAuto;
	}

	public void setSettablemodeAuto(Boolean settablemodeAuto) {
		this.settablemodeAuto = settablemodeAuto;
	}

	public Boolean getSettablemodeCool() {
		return this.settablemodeCool;
	}

	public void setSettablemodeCool(Boolean settablemodeCool) {
		this.settablemodeCool = settablemodeCool;
	}

	public Boolean getSettablemodeDry() {
		return this.settablemodeDry;
	}

	public void setSettablemodeDry(Boolean settablemodeDry) {
		this.settablemodeDry = settablemodeDry;
	}

	public Boolean getSettablemodeFan() {
		return this.settablemodeFan;
	}

	public void setSettablemodeFan(Boolean settablemodeFan) {
		this.settablemodeFan = settablemodeFan;
	}

	public Boolean getSettablemodeHeat() {
		return this.settablemodeHeat;
	}

	public void setSettablemodeHeat(Boolean settablemodeHeat) {
		this.settablemodeHeat = settablemodeHeat;
	}

	public Boolean getSettableswing() {
		return this.settableswing;
	}

	public void setSettableswing(Boolean settableswing) {
		this.settableswing = settableswing;
	}

	public Integer getSettabletempLimitLowerAuto() {
		return this.settabletempLimitLowerAuto;
	}

	public void setSettabletempLimitLowerAuto(Integer settabletempLimitLowerAuto) {
		this.settabletempLimitLowerAuto = settabletempLimitLowerAuto;
	}

	public Integer getSettabletempLimitLowerCool() {
		return this.settabletempLimitLowerCool;
	}

	public void setSettabletempLimitLowerCool(Integer settabletempLimitLowerCool) {
		this.settabletempLimitLowerCool = settabletempLimitLowerCool;
	}

	public Integer getSettabletempLimitLowerDry() {
		return this.settabletempLimitLowerDry;
	}

	public void setSettabletempLimitLowerDry(Integer settabletempLimitLowerDry) {
		this.settabletempLimitLowerDry = settabletempLimitLowerDry;
	}

	public Integer getSettabletempLimitLowerHeat() {
		return this.settabletempLimitLowerHeat;
	}

	public void setSettabletempLimitLowerHeat(Integer settabletempLimitLowerHeat) {
		this.settabletempLimitLowerHeat = settabletempLimitLowerHeat;
	}

	public Integer getSettabletempLimitUpperAuto() {
		return this.settabletempLimitUpperAuto;
	}

	public void setSettabletempLimitUpperAuto(Integer settabletempLimitUpperAuto) {
		this.settabletempLimitUpperAuto = settabletempLimitUpperAuto;
	}

	public Integer getSettabletempLimitUpperCool() {
		return this.settabletempLimitUpperCool;
	}

	public void setSettabletempLimitUpperCool(Integer settabletempLimitUpperCool) {
		this.settabletempLimitUpperCool = settabletempLimitUpperCool;
	}

	public Integer getSettabletempLimitUpperDry() {
		return this.settabletempLimitUpperDry;
	}

	public void setSettabletempLimitUpperDry(Integer settabletempLimitUpperDry) {
		this.settabletempLimitUpperDry = settabletempLimitUpperDry;
	}

	public Integer getSettabletempLimitUpperHeat() {
		return this.settabletempLimitUpperHeat;
	}

	public void setSettabletempLimitUpperHeat(Integer settabletempLimitUpperHeat) {
		this.settabletempLimitUpperHeat = settabletempLimitUpperHeat;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public List<Indoorunit> getIndoorunits() {
		return this.indoorunits;
	}

	public void setIndoorunits(List<Indoorunit> indoorunits) {
		this.indoorunits = indoorunits;
	}

	public Indoorunit addIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().add(indoorunit);
		indoorunit.setMetaindoorunit(this);

		return indoorunit;
	}

	public Indoorunit removeIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().remove(indoorunit);
		indoorunit.setMetaindoorunit(null);

		return indoorunit;
	}

}

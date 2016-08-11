package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the ghpparameter_statistics database table.
 * 
 */
@Entity
@Table(name="ghpparameter_statistics")
@NamedQuery(name="GhpparameterStatistic.findAll", query="SELECT g FROM GhpparameterStatistic g")
public class GhpparameterStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false)
	private Integer balancevalve;

	@Column(name="bypassvalve_opening")
	private Integer bypassvalveOpening;

	@Column(nullable=false)
	private Integer catalyzertemperature;

	@Column(nullable=false)
	private Integer clutch;

	@Column(nullable=false)
	private Integer clutch2;

	@Column(nullable=false)
	private Integer clutchcoiltemperature;

	@Column(nullable=false)
	private Integer clutchcoiltemperature2;

	@Column(nullable=false)
	private Integer comperssoroillevel;

	@Column(nullable=false)
	private Integer compressorheater;

	@Column(name="compressorinlet_pressure")
	private Integer compressorinletPressure;

	@Column(name="compressorinlet_temperature")
	private Integer compressorinletTemperature;

	@Column(name="compressoroutlet_pressure")
	private Integer compressoroutletPressure;

	@Column(name="compressoroutlet_temperature")
	private Integer compressoroutletTemperature;

	@Column(nullable=false)
	private Integer coolanttemperature;

	@Column(length=45)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;

	@Column(nullable=false)
	private Integer dischargesolenoidvalve1;

	@Column(nullable=false)
	private Integer dischargesolenoidvalve2;

	@Column(nullable=false)
	private Integer drainfilterheater1;

	@Column(nullable=false)
	private Integer drainfilterheater2;

	@Column(name="engineoperation_time", precision=19, scale=2)
	private BigDecimal engineoperationTime;

	@Column(nullable=false)
	private Integer enginerevolution;

	@Column(nullable=false)
	private Integer exhaustgastemperature;

	@Column(name="exhaustheat_recovery_valve_opening")
	private Integer exhaustheatRecoveryValveOpening;

	@Column(name="expansionvalve_opening")
	private Integer expansionvalveOpening;

	@Column(name="expansionvalve_opening2")
	private Integer expansionvalveOpening2;

	@Column(nullable=false)
	private Integer flushingvalve;

	@Column(name="fuelgas_regulating_valve_opening")
	private Integer fuelgasRegulatingValveOpening;

	@Column(name="fuelgasshut_offvalve1")
	private Integer fuelgasshutOffvalve1;

	@Column(name="fuelgasshut_offvalve2")
	private Integer fuelgasshutOffvalve2;

	@Column(name="gasrefrigerantshut_offvalve")
	private Integer gasrefrigerantshutOffvalve;

	@Column(nullable=false)
	private Integer generationpower;

	@Column(nullable=false)
	private Integer ghpoilsign;

	@Column(name="heaterfor_cold_region")
	private Integer heaterforColdRegion;

	@Column(name="heatexchangerinlet_temperature")
	private Integer heatexchangerinletTemperature;

	@Column(name="heatexchangerinlet_temperature2")
	private Integer heatexchangerinletTemperature2;

	@Column(nullable=false)
	private Integer hotwatertemperature;

	@Column(nullable=false)
	private Integer ignitiontiming;

	@Column(nullable=false)
	private double instantgas;

	@Column(nullable=false)
	private double instantheat;

	@Column(name="liquidvalve_opening")
	private Integer liquidvalveOpening;

	private Timestamp logtime;

	@Column(nullable=false)
	private Integer oilleveltemperature;

	@Column(nullable=false)
	private Integer oilpump;

	@Column(nullable=false)
	private Integer oilrecoveryvalve;

	@Column(nullable=false)
	private Integer outdoorunitfanoutput;

	@Column(nullable=false)
	private Integer pumpforhotwater;

	@Column(nullable=false)
	private Integer receivertankvalve1;

	@Column(nullable=false)
	private Integer receivertankvalve2;

	@Column(nullable=false)
	private Integer startermotor;

	@Column(nullable=false)
	private Integer startermotorcurrent;

	@Column(name="statermotor_power")
	private Integer statermotorPower;

	@Column(nullable=false)
	private Integer suctionsolenoidevalve1;

	@Column(nullable=false)
	private Integer suctionsolenoidevalve2;

	@Column(name="superheat_level_of_compressor_unit")
	private Integer superheatLevelOfCompressorUnit;

	private Integer threewaycoolervalve;

	private Integer threewayvalveforcoolant;

	private Integer threewayvalveforhotwater;

	@Column(nullable=false)
	private Integer thtottle;

	@Column(name="timeafter_changing_engine_oil", precision=19, scale=2)
	private BigDecimal timeafterChangingEngineOil;

	private Timestamp updatedate;

	@Column(length=255)
	private String updatedby;

	//bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name="outdoorunit_id")
	private Outdoorunit outdoorunit;

	public GhpparameterStatistic() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBalancevalve() {
		return this.balancevalve;
	}

	public void setBalancevalve(Integer balancevalve) {
		this.balancevalve = balancevalve;
	}

	public Integer getBypassvalveOpening() {
		return this.bypassvalveOpening;
	}

	public void setBypassvalveOpening(Integer bypassvalveOpening) {
		this.bypassvalveOpening = bypassvalveOpening;
	}

	public Integer getCatalyzertemperature() {
		return this.catalyzertemperature;
	}

	public void setCatalyzertemperature(Integer catalyzertemperature) {
		this.catalyzertemperature = catalyzertemperature;
	}

	public Integer getClutch() {
		return this.clutch;
	}

	public void setClutch(Integer clutch) {
		this.clutch = clutch;
	}

	public Integer getClutch2() {
		return this.clutch2;
	}

	public void setClutch2(Integer clutch2) {
		this.clutch2 = clutch2;
	}

	public Integer getClutchcoiltemperature() {
		return this.clutchcoiltemperature;
	}

	public void setClutchcoiltemperature(Integer clutchcoiltemperature) {
		this.clutchcoiltemperature = clutchcoiltemperature;
	}

	public Integer getClutchcoiltemperature2() {
		return this.clutchcoiltemperature2;
	}

	public void setClutchcoiltemperature2(Integer clutchcoiltemperature2) {
		this.clutchcoiltemperature2 = clutchcoiltemperature2;
	}

	public Integer getComperssoroillevel() {
		return this.comperssoroillevel;
	}

	public void setComperssoroillevel(Integer comperssoroillevel) {
		this.comperssoroillevel = comperssoroillevel;
	}

	public Integer getCompressorheater() {
		return this.compressorheater;
	}

	public void setCompressorheater(Integer compressorheater) {
		this.compressorheater = compressorheater;
	}

	public Integer getCompressorinletPressure() {
		return this.compressorinletPressure;
	}

	public void setCompressorinletPressure(Integer compressorinletPressure) {
		this.compressorinletPressure = compressorinletPressure;
	}

	public Integer getCompressorinletTemperature() {
		return this.compressorinletTemperature;
	}

	public void setCompressorinletTemperature(Integer compressorinletTemperature) {
		this.compressorinletTemperature = compressorinletTemperature;
	}

	public Integer getCompressoroutletPressure() {
		return this.compressoroutletPressure;
	}

	public void setCompressoroutletPressure(Integer compressoroutletPressure) {
		this.compressoroutletPressure = compressoroutletPressure;
	}

	public Integer getCompressoroutletTemperature() {
		return this.compressoroutletTemperature;
	}

	public void setCompressoroutletTemperature(Integer compressoroutletTemperature) {
		this.compressoroutletTemperature = compressoroutletTemperature;
	}

	public Integer getCoolanttemperature() {
		return this.coolanttemperature;
	}

	public void setCoolanttemperature(Integer coolanttemperature) {
		this.coolanttemperature = coolanttemperature;
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

	public Integer getDischargesolenoidvalve1() {
		return this.dischargesolenoidvalve1;
	}

	public void setDischargesolenoidvalve1(Integer dischargesolenoidvalve1) {
		this.dischargesolenoidvalve1 = dischargesolenoidvalve1;
	}

	public Integer getDischargesolenoidvalve2() {
		return this.dischargesolenoidvalve2;
	}

	public void setDischargesolenoidvalve2(Integer dischargesolenoidvalve2) {
		this.dischargesolenoidvalve2 = dischargesolenoidvalve2;
	}

	public Integer getDrainfilterheater1() {
		return this.drainfilterheater1;
	}

	public void setDrainfilterheater1(Integer drainfilterheater1) {
		this.drainfilterheater1 = drainfilterheater1;
	}

	public Integer getDrainfilterheater2() {
		return this.drainfilterheater2;
	}

	public void setDrainfilterheater2(Integer drainfilterheater2) {
		this.drainfilterheater2 = drainfilterheater2;
	}

	public BigDecimal getEngineoperationTime() {
		return this.engineoperationTime;
	}

	public void setEngineoperationTime(BigDecimal engineoperationTime) {
		this.engineoperationTime = engineoperationTime;
	}

	public Integer getEnginerevolution() {
		return this.enginerevolution;
	}

	public void setEnginerevolution(Integer enginerevolution) {
		this.enginerevolution = enginerevolution;
	}

	public Integer getExhaustgastemperature() {
		return this.exhaustgastemperature;
	}

	public void setExhaustgastemperature(Integer exhaustgastemperature) {
		this.exhaustgastemperature = exhaustgastemperature;
	}

	public Integer getExhaustheatRecoveryValveOpening() {
		return this.exhaustheatRecoveryValveOpening;
	}

	public void setExhaustheatRecoveryValveOpening(Integer exhaustheatRecoveryValveOpening) {
		this.exhaustheatRecoveryValveOpening = exhaustheatRecoveryValveOpening;
	}

	public Integer getExpansionvalveOpening() {
		return this.expansionvalveOpening;
	}

	public void setExpansionvalveOpening(Integer expansionvalveOpening) {
		this.expansionvalveOpening = expansionvalveOpening;
	}

	public Integer getExpansionvalveOpening2() {
		return this.expansionvalveOpening2;
	}

	public void setExpansionvalveOpening2(Integer expansionvalveOpening2) {
		this.expansionvalveOpening2 = expansionvalveOpening2;
	}

	public Integer getFlushingvalve() {
		return this.flushingvalve;
	}

	public void setFlushingvalve(Integer flushingvalve) {
		this.flushingvalve = flushingvalve;
	}

	public Integer getFuelgasRegulatingValveOpening() {
		return this.fuelgasRegulatingValveOpening;
	}

	public void setFuelgasRegulatingValveOpening(Integer fuelgasRegulatingValveOpening) {
		this.fuelgasRegulatingValveOpening = fuelgasRegulatingValveOpening;
	}

	public Integer getFuelgasshutOffvalve1() {
		return this.fuelgasshutOffvalve1;
	}

	public void setFuelgasshutOffvalve1(Integer fuelgasshutOffvalve1) {
		this.fuelgasshutOffvalve1 = fuelgasshutOffvalve1;
	}

	public Integer getFuelgasshutOffvalve2() {
		return this.fuelgasshutOffvalve2;
	}

	public void setFuelgasshutOffvalve2(Integer fuelgasshutOffvalve2) {
		this.fuelgasshutOffvalve2 = fuelgasshutOffvalve2;
	}

	public Integer getGasrefrigerantshutOffvalve() {
		return this.gasrefrigerantshutOffvalve;
	}

	public void setGasrefrigerantshutOffvalve(Integer gasrefrigerantshutOffvalve) {
		this.gasrefrigerantshutOffvalve = gasrefrigerantshutOffvalve;
	}

	public Integer getGenerationpower() {
		return this.generationpower;
	}

	public void setGenerationpower(Integer generationpower) {
		this.generationpower = generationpower;
	}

	public Integer getGhpoilsign() {
		return this.ghpoilsign;
	}

	public void setGhpoilsign(Integer ghpoilsign) {
		this.ghpoilsign = ghpoilsign;
	}

	public Integer getHeaterforColdRegion() {
		return this.heaterforColdRegion;
	}

	public void setHeaterforColdRegion(Integer heaterforColdRegion) {
		this.heaterforColdRegion = heaterforColdRegion;
	}

	public Integer getHeatexchangerinletTemperature() {
		return this.heatexchangerinletTemperature;
	}

	public void setHeatexchangerinletTemperature(Integer heatexchangerinletTemperature) {
		this.heatexchangerinletTemperature = heatexchangerinletTemperature;
	}

	public Integer getHeatexchangerinletTemperature2() {
		return this.heatexchangerinletTemperature2;
	}

	public void setHeatexchangerinletTemperature2(Integer heatexchangerinletTemperature2) {
		this.heatexchangerinletTemperature2 = heatexchangerinletTemperature2;
	}

	public Integer getHotwatertemperature() {
		return this.hotwatertemperature;
	}

	public void setHotwatertemperature(Integer hotwatertemperature) {
		this.hotwatertemperature = hotwatertemperature;
	}

	public Integer getIgnitiontiming() {
		return this.ignitiontiming;
	}

	public void setIgnitiontiming(Integer ignitiontiming) {
		this.ignitiontiming = ignitiontiming;
	}

	public double getInstantgas() {
		return this.instantgas;
	}

	public void setInstantgas(double instantgas) {
		this.instantgas = instantgas;
	}

	public double getInstantheat() {
		return this.instantheat;
	}

	public void setInstantheat(double instantheat) {
		this.instantheat = instantheat;
	}

	public Integer getLiquidvalveOpening() {
		return this.liquidvalveOpening;
	}

	public void setLiquidvalveOpening(Integer liquidvalveOpening) {
		this.liquidvalveOpening = liquidvalveOpening;
	}

	public Timestamp getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Timestamp logtime) {
		this.logtime = logtime;
	}

	public Integer getOilleveltemperature() {
		return this.oilleveltemperature;
	}

	public void setOilleveltemperature(Integer oilleveltemperature) {
		this.oilleveltemperature = oilleveltemperature;
	}

	public Integer getOilpump() {
		return this.oilpump;
	}

	public void setOilpump(Integer oilpump) {
		this.oilpump = oilpump;
	}

	public Integer getOilrecoveryvalve() {
		return this.oilrecoveryvalve;
	}

	public void setOilrecoveryvalve(Integer oilrecoveryvalve) {
		this.oilrecoveryvalve = oilrecoveryvalve;
	}

	public Integer getOutdoorunitfanoutput() {
		return this.outdoorunitfanoutput;
	}

	public void setOutdoorunitfanoutput(Integer outdoorunitfanoutput) {
		this.outdoorunitfanoutput = outdoorunitfanoutput;
	}

	public Integer getPumpforhotwater() {
		return this.pumpforhotwater;
	}

	public void setPumpforhotwater(Integer pumpforhotwater) {
		this.pumpforhotwater = pumpforhotwater;
	}

	public Integer getReceivertankvalve1() {
		return this.receivertankvalve1;
	}

	public void setReceivertankvalve1(Integer receivertankvalve1) {
		this.receivertankvalve1 = receivertankvalve1;
	}

	public Integer getReceivertankvalve2() {
		return this.receivertankvalve2;
	}

	public void setReceivertankvalve2(Integer receivertankvalve2) {
		this.receivertankvalve2 = receivertankvalve2;
	}

	public Integer getStartermotor() {
		return this.startermotor;
	}

	public void setStartermotor(Integer startermotor) {
		this.startermotor = startermotor;
	}

	public Integer getStartermotorcurrent() {
		return this.startermotorcurrent;
	}

	public void setStartermotorcurrent(Integer startermotorcurrent) {
		this.startermotorcurrent = startermotorcurrent;
	}

	public Integer getStatermotorPower() {
		return this.statermotorPower;
	}

	public void setStatermotorPower(Integer statermotorPower) {
		this.statermotorPower = statermotorPower;
	}

	public Integer getSuctionsolenoidevalve1() {
		return this.suctionsolenoidevalve1;
	}

	public void setSuctionsolenoidevalve1(Integer suctionsolenoidevalve1) {
		this.suctionsolenoidevalve1 = suctionsolenoidevalve1;
	}

	public Integer getSuctionsolenoidevalve2() {
		return this.suctionsolenoidevalve2;
	}

	public void setSuctionsolenoidevalve2(Integer suctionsolenoidevalve2) {
		this.suctionsolenoidevalve2 = suctionsolenoidevalve2;
	}

	public Integer getSuperheatLevelOfCompressorUnit() {
		return this.superheatLevelOfCompressorUnit;
	}

	public void setSuperheatLevelOfCompressorUnit(Integer superheatLevelOfCompressorUnit) {
		this.superheatLevelOfCompressorUnit = superheatLevelOfCompressorUnit;
	}

	public Integer getThreewaycoolervalve() {
		return this.threewaycoolervalve;
	}

	public void setThreewaycoolervalve(Integer threewaycoolervalve) {
		this.threewaycoolervalve = threewaycoolervalve;
	}

	public Integer getThreewayvalveforcoolant() {
		return this.threewayvalveforcoolant;
	}

	public void setThreewayvalveforcoolant(Integer threewayvalveforcoolant) {
		this.threewayvalveforcoolant = threewayvalveforcoolant;
	}

	public Integer getThreewayvalveforhotwater() {
		return this.threewayvalveforhotwater;
	}

	public void setThreewayvalveforhotwater(Integer threewayvalveforhotwater) {
		this.threewayvalveforhotwater = threewayvalveforhotwater;
	}

	public Integer getThtottle() {
		return this.thtottle;
	}

	public void setThtottle(Integer thtottle) {
		this.thtottle = thtottle;
	}

	public BigDecimal getTimeafterChangingEngineOil() {
		return this.timeafterChangingEngineOil;
	}

	public void setTimeafterChangingEngineOil(BigDecimal timeafterChangingEngineOil) {
		this.timeafterChangingEngineOil = timeafterChangingEngineOil;
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

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

}
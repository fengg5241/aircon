package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the vrfparameter_statistics database table.
 * 
 */
@Entity
@Table(name="vrfparameter_statistics")
@NamedQuery(name="VrfparameterStatistic.findAll", query="SELECT v FROM VrfparameterStatistic v")
public class VrfparameterStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(precision=19, scale=2)
	private BigDecimal compressor1workingtime;

	@Column(precision=19, scale=2)
	private BigDecimal compressor2workingtime;

	@Column(precision=19, scale=2)
	private BigDecimal compressor3workingtime;

	@Column(length=255)
	private String createdby;

	private Timestamp creationdate;

	@Column(nullable=false)
	private Integer current2;

	@Column(nullable=false)
	private Integer current3;

	@Column(nullable=false)
	private Integer demand;

	@Column(nullable=false)
	private Integer fanmode;

	@Column(nullable=false)
	private Integer fanrotation;

	@Column(nullable=false)
	private Integer fixedspeedcomp1;

	@Column(nullable=false)
	private Integer fixedspeedcomp2;

	@Column(nullable=false)
	private Integer gastempofoutdoorcoil1;

	@Column(nullable=false)
	private Integer gastempofoutdoorcoil2;

	@Column(nullable=false)
	private Integer highpresure;

	@Column(nullable=false)
	private Integer inlettempoutdoorunit;

	@Column(nullable=false)
	private Integer invcompactualhz;

	@Column(nullable=false)
	private Integer invcomptargethz;

	@Column(nullable=false)
	private Integer invprimarycurrent;

	@Column(nullable=false)
	private Integer invsecondarycurrent;

	@Column(nullable=false)
	private Integer liquidtempofoutdoorcoil1;

	@Column(nullable=false)
	private Integer liquidtempofoutdoorcoil2;

	private Timestamp logtime;

	@Column(nullable=false)
	private Integer lowpresure;

	@Column(nullable=false)
	private Integer mov1pulse;

	@Column(nullable=false)
	private Integer mov2pulse;

	@Column(nullable=false)
	private Integer mov4pulse;

	@Column(nullable=false)
	private Integer outdoorunitstatus;

	@Column(nullable=false)
	private Integer saturatedtemphighpress;

	@Column(nullable=false)
	private Integer saturatedtemplowpress;

	@Column(nullable=false)
	private Integer scg;

	@Column(nullable=false)
	private Integer tempcompressordischarge1;

	@Column(nullable=false)
	private Integer tempcompressordischarge2;

	@Column(nullable=false)
	private Integer tempcompressordischarge3;

	@Column(nullable=false)
	private Integer tempoil1;

	@Column(nullable=false)
	private Integer tempoil2;

	@Column(nullable=false)
	private Integer temppil3;

	private Timestamp updatedate;

	@Column(length=255)
	private String updatedby;

	//bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name="outdoorunit_id")
	private Outdoorunit outdoorunit;

	public VrfparameterStatistic() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCompressor1workingtime() {
		return this.compressor1workingtime;
	}

	public void setCompressor1workingtime(BigDecimal compressor1workingtime) {
		this.compressor1workingtime = compressor1workingtime;
	}

	public BigDecimal getCompressor2workingtime() {
		return this.compressor2workingtime;
	}

	public void setCompressor2workingtime(BigDecimal compressor2workingtime) {
		this.compressor2workingtime = compressor2workingtime;
	}

	public BigDecimal getCompressor3workingtime() {
		return this.compressor3workingtime;
	}

	public void setCompressor3workingtime(BigDecimal compressor3workingtime) {
		this.compressor3workingtime = compressor3workingtime;
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

	public Integer getCurrent2() {
		return this.current2;
	}

	public void setCurrent2(Integer current2) {
		this.current2 = current2;
	}

	public Integer getCurrent3() {
		return this.current3;
	}

	public void setCurrent3(Integer current3) {
		this.current3 = current3;
	}

	public Integer getDemand() {
		return this.demand;
	}

	public void setDemand(Integer demand) {
		this.demand = demand;
	}

	public Integer getFanmode() {
		return this.fanmode;
	}

	public void setFanmode(Integer fanmode) {
		this.fanmode = fanmode;
	}

	public Integer getFanrotation() {
		return this.fanrotation;
	}

	public void setFanrotation(Integer fanrotation) {
		this.fanrotation = fanrotation;
	}

	public Integer getFixedspeedcomp1() {
		return this.fixedspeedcomp1;
	}

	public void setFixedspeedcomp1(Integer fixedspeedcomp1) {
		this.fixedspeedcomp1 = fixedspeedcomp1;
	}

	public Integer getFixedspeedcomp2() {
		return this.fixedspeedcomp2;
	}

	public void setFixedspeedcomp2(Integer fixedspeedcomp2) {
		this.fixedspeedcomp2 = fixedspeedcomp2;
	}

	public Integer getGastempofoutdoorcoil1() {
		return this.gastempofoutdoorcoil1;
	}

	public void setGastempofoutdoorcoil1(Integer gastempofoutdoorcoil1) {
		this.gastempofoutdoorcoil1 = gastempofoutdoorcoil1;
	}

	public Integer getGastempofoutdoorcoil2() {
		return this.gastempofoutdoorcoil2;
	}

	public void setGastempofoutdoorcoil2(Integer gastempofoutdoorcoil2) {
		this.gastempofoutdoorcoil2 = gastempofoutdoorcoil2;
	}

	public Integer getHighpresure() {
		return this.highpresure;
	}

	public void setHighpresure(Integer highpresure) {
		this.highpresure = highpresure;
	}

	public Integer getInlettempoutdoorunit() {
		return this.inlettempoutdoorunit;
	}

	public void setInlettempoutdoorunit(Integer inlettempoutdoorunit) {
		this.inlettempoutdoorunit = inlettempoutdoorunit;
	}

	public Integer getInvcompactualhz() {
		return this.invcompactualhz;
	}

	public void setInvcompactualhz(Integer invcompactualhz) {
		this.invcompactualhz = invcompactualhz;
	}

	public Integer getInvcomptargethz() {
		return this.invcomptargethz;
	}

	public void setInvcomptargethz(Integer invcomptargethz) {
		this.invcomptargethz = invcomptargethz;
	}

	public Integer getInvprimarycurrent() {
		return this.invprimarycurrent;
	}

	public void setInvprimarycurrent(Integer invprimarycurrent) {
		this.invprimarycurrent = invprimarycurrent;
	}

	public Integer getInvsecondarycurrent() {
		return this.invsecondarycurrent;
	}

	public void setInvsecondarycurrent(Integer invsecondarycurrent) {
		this.invsecondarycurrent = invsecondarycurrent;
	}

	public Integer getLiquidtempofoutdoorcoil1() {
		return this.liquidtempofoutdoorcoil1;
	}

	public void setLiquidtempofoutdoorcoil1(Integer liquidtempofoutdoorcoil1) {
		this.liquidtempofoutdoorcoil1 = liquidtempofoutdoorcoil1;
	}

	public Integer getLiquidtempofoutdoorcoil2() {
		return this.liquidtempofoutdoorcoil2;
	}

	public void setLiquidtempofoutdoorcoil2(Integer liquidtempofoutdoorcoil2) {
		this.liquidtempofoutdoorcoil2 = liquidtempofoutdoorcoil2;
	}

	public Timestamp getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Timestamp logtime) {
		this.logtime = logtime;
	}

	public Integer getLowpresure() {
		return this.lowpresure;
	}

	public void setLowpresure(Integer lowpresure) {
		this.lowpresure = lowpresure;
	}

	public Integer getMov1pulse() {
		return this.mov1pulse;
	}

	public void setMov1pulse(Integer mov1pulse) {
		this.mov1pulse = mov1pulse;
	}

	public Integer getMov2pulse() {
		return this.mov2pulse;
	}

	public void setMov2pulse(Integer mov2pulse) {
		this.mov2pulse = mov2pulse;
	}

	public Integer getMov4pulse() {
		return this.mov4pulse;
	}

	public void setMov4pulse(Integer mov4pulse) {
		this.mov4pulse = mov4pulse;
	}

	public Integer getOutdoorunitstatus() {
		return this.outdoorunitstatus;
	}

	public void setOutdoorunitstatus(Integer outdoorunitstatus) {
		this.outdoorunitstatus = outdoorunitstatus;
	}

	public Integer getSaturatedtemphighpress() {
		return this.saturatedtemphighpress;
	}

	public void setSaturatedtemphighpress(Integer saturatedtemphighpress) {
		this.saturatedtemphighpress = saturatedtemphighpress;
	}

	public Integer getSaturatedtemplowpress() {
		return this.saturatedtemplowpress;
	}

	public void setSaturatedtemplowpress(Integer saturatedtemplowpress) {
		this.saturatedtemplowpress = saturatedtemplowpress;
	}

	public Integer getScg() {
		return this.scg;
	}

	public void setScg(Integer scg) {
		this.scg = scg;
	}

	public Integer getTempcompressordischarge1() {
		return this.tempcompressordischarge1;
	}

	public void setTempcompressordischarge1(Integer tempcompressordischarge1) {
		this.tempcompressordischarge1 = tempcompressordischarge1;
	}

	public Integer getTempcompressordischarge2() {
		return this.tempcompressordischarge2;
	}

	public void setTempcompressordischarge2(Integer tempcompressordischarge2) {
		this.tempcompressordischarge2 = tempcompressordischarge2;
	}

	public Integer getTempcompressordischarge3() {
		return this.tempcompressordischarge3;
	}

	public void setTempcompressordischarge3(Integer tempcompressordischarge3) {
		this.tempcompressordischarge3 = tempcompressordischarge3;
	}

	public Integer getTempoil1() {
		return this.tempoil1;
	}

	public void setTempoil1(Integer tempoil1) {
		this.tempoil1 = tempoil1;
	}

	public Integer getTempoil2() {
		return this.tempoil2;
	}

	public void setTempoil2(Integer tempoil2) {
		this.tempoil2 = tempoil2;
	}

	public Integer getTemppil3() {
		return this.temppil3;
	}

	public void setTemppil3(Integer temppil3) {
		this.temppil3 = temppil3;
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
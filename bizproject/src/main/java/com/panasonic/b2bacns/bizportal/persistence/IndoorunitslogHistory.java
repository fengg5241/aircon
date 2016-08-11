package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the indoorunitslog_history database table.
 * 
 */
@Entity
@Table(name="indoorunitslog_history")
@NamedQuery(name="IndoorunitslogHistory.findAll", query="SELECT i FROM IndoorunitslogHistory i")
public class IndoorunitslogHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	private Integer iseconavi;

	@Column(name="lastfilter_cleaning_date")
	private Timestamp lastfilterCleaningDate;

	private Integer modeltype;

	private Integer powerstatus;

	private Integer rcprohibitfanspeed;

	private Integer rcprohibitmode;

	private Integer rcprohibitpwr;

	private Integer rcprohibitsettemp;

	private Integer rcprohibitvanepos;

	private double roomtemp;

	private double setpointtempreture;

	@Column(length=24)
	private String status;

	private Timestamp updateeconavi;

	//bi-directional many-to-one association to FanspeedMaster
	@ManyToOne
	@JoinColumn(name="fanspeed")
	private FanspeedMaster fanspeedMaster;

	//bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name="indoorunit_id")
	private Indoorunit indoorunit;

	//bi-directional many-to-one association to Modemaster
	@ManyToOne
	@JoinColumn(name="acmode")
	private Modemaster modemaster;

	//bi-directional many-to-one association to WinddirectionMaster
	@ManyToOne
	@JoinColumn(name="flapmode")
	private WinddirectionMaster winddirectionMaster;

	public IndoorunitslogHistory() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIseconavi() {
		return this.iseconavi;
	}

	public void setIseconavi(Integer iseconavi) {
		this.iseconavi = iseconavi;
	}

	public Timestamp getLastfilterCleaningDate() {
		return this.lastfilterCleaningDate;
	}

	public void setLastfilterCleaningDate(Timestamp lastfilterCleaningDate) {
		this.lastfilterCleaningDate = lastfilterCleaningDate;
	}

	public Integer getModeltype() {
		return this.modeltype;
	}

	public void setModeltype(Integer modeltype) {
		this.modeltype = modeltype;
	}

	public Integer getPowerstatus() {
		return this.powerstatus;
	}

	public void setPowerstatus(Integer powerstatus) {
		this.powerstatus = powerstatus;
	}

	public Integer getRcprohibitfanspeed() {
		return this.rcprohibitfanspeed;
	}

	public void setRcprohibitfanspeed(Integer rcprohibitfanspeed) {
		this.rcprohibitfanspeed = rcprohibitfanspeed;
	}

	public Integer getRcprohibitmode() {
		return this.rcprohibitmode;
	}

	public void setRcprohibitmode(Integer rcprohibitmode) {
		this.rcprohibitmode = rcprohibitmode;
	}

	public Integer getRcprohibitpwr() {
		return this.rcprohibitpwr;
	}

	public void setRcprohibitpwr(Integer rcprohibitpwr) {
		this.rcprohibitpwr = rcprohibitpwr;
	}

	public Integer getRcprohibitsettemp() {
		return this.rcprohibitsettemp;
	}

	public void setRcprohibitsettemp(Integer rcprohibitsettemp) {
		this.rcprohibitsettemp = rcprohibitsettemp;
	}

	public Integer getRcprohibitvanepos() {
		return this.rcprohibitvanepos;
	}

	public void setRcprohibitvanepos(Integer rcprohibitvanepos) {
		this.rcprohibitvanepos = rcprohibitvanepos;
	}

	public double getRoomtemp() {
		return this.roomtemp;
	}

	public void setRoomtemp(double roomtemp) {
		this.roomtemp = roomtemp;
	}

	public double getSetpointtempreture() {
		return this.setpointtempreture;
	}

	public void setSetpointtempreture(double setpointtempreture) {
		this.setpointtempreture = setpointtempreture;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdateeconavi() {
		return this.updateeconavi;
	}

	public void setUpdateeconavi(Timestamp updateeconavi) {
		this.updateeconavi = updateeconavi;
	}

	public FanspeedMaster getFanspeedMaster() {
		return this.fanspeedMaster;
	}

	public void setFanspeedMaster(FanspeedMaster fanspeedMaster) {
		this.fanspeedMaster = fanspeedMaster;
	}

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

	public Modemaster getModemaster() {
		return this.modemaster;
	}

	public void setModemaster(Modemaster modemaster) {
		this.modemaster = modemaster;
	}

	public WinddirectionMaster getWinddirectionMaster() {
		return this.winddirectionMaster;
	}

	public void setWinddirectionMaster(WinddirectionMaster winddirectionMaster) {
		this.winddirectionMaster = winddirectionMaster;
	}

}
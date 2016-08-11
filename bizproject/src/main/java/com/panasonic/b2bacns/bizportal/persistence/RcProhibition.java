package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rc_prohibition database table.
 * 
 */
@Entity
@Table(name="rc_prohibition")
@NamedQuery(name="RcProhibition.findAll", query="SELECT r FROM RcProhibition r")
public class RcProhibition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String createdby;

	private Timestamp creationdate;

	private Integer ison;

	@Column(nullable=false)
	private Integer rcprohibitfanspeed;

	@Column(nullable=false)
	private Integer rcprohibitmode;

	@Column(nullable=false)
	private Integer rcprohibitpwr;

	@Column(nullable=false)
	private Integer rcprohibitsettemp;

	@Column(nullable=false)
	private Integer rcprohibitvanepos;

	private Timestamp updatedate;

	@Column(length=255)
	private String updatedby;

	//bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name="indoorunit_id")
	private Indoorunit indoorunit;

	public RcProhibition() {
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

	public Integer getIson() {
		return this.ison;
	}

	public void setIson(Integer ison) {
		this.ison = ison;
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

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

}
package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the rcoperation_log database table.
 * 
 */
@Entity
@Table(name = "rcoperation_log")
@NamedQuery(name = "RcoperationLog.findAll", query = "SELECT r FROM RcoperationLog r")
public class RcoperationLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	private String powerstatus;

	private String airconmode;

	private String energysaving;

	private String fanspeed;

	private String flapmode;

	private String temperature;

	private String prohibitionfanspeed;

	private String prohibitionpowerstatus;

	private String prohibitionsettemp;

	private String prohibitionwindriection;

	private String prohibitonmode;
	
	@Column(name="prohibitionenergy_saving")
	private String prohibitionEnergySaving;

	private String requestedtime;

	private Boolean success;

	private String creationdate;

	// bi-directional many-to-one association to Indoorunit
	@ManyToOne
	private Indoorunit indoorunit;

	// bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public RcoperationLog() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAirconmode() {
		return this.airconmode;
	}

	public void setAirconmode(String airconmode) {
		this.airconmode = airconmode;
	}

	public String getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getEnergysaving() {
		return this.energysaving;
	}

	public void setEnergysaving(String energysaving) {
		this.energysaving = energysaving;
	}

	public String getFanspeed() {
		return this.fanspeed;
	}

	public void setFanspeed(String fanspeed) {
		this.fanspeed = fanspeed;
	}

	public String getFlapmode() {
		return this.flapmode;
	}

	public void setFlapmode(String flapmode) {
		this.flapmode = flapmode;
	}

	public String getPowerstatus() {
		return this.powerstatus;
	}

	public void setPowerstatus(String powerstatus) {
		this.powerstatus = powerstatus;
	}

	public String getProhibitionfanspeed() {
		return this.prohibitionfanspeed;
	}

	public void setProhibitionfanspeed(String prohibitionfanspeed) {
		this.prohibitionfanspeed = prohibitionfanspeed;
	}

	public String getProhibitionpowerstatus() {
		return this.prohibitionpowerstatus;
	}

	public void setProhibitionpowerstatus(String prohibitionpowerstatus) {
		this.prohibitionpowerstatus = prohibitionpowerstatus;
	}

	public String getProhibitionsettemp() {
		return this.prohibitionsettemp;
	}

	public void setProhibitionsettemp(String prohibitionsettemp) {
		this.prohibitionsettemp = prohibitionsettemp;
	}

	public String getProhibitionwindriection() {
		return this.prohibitionwindriection;
	}

	public void setProhibitionwindriection(String prohibitionwindriection) {
		this.prohibitionwindriection = prohibitionwindriection;
	}

	public String getProhibitonmode() {
		return this.prohibitonmode;
	}

	public void setProhibitonmode(String prohibitonmode) {
		this.prohibitonmode = prohibitonmode;
	}

	public String getRequestedtime() {
		return this.requestedtime;
	}

	public void setRequestedtime(String requestedtime) {
		this.requestedtime = requestedtime;
	}

	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getTemperature() {
		return this.temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the prohibitonEnergySaving
	 */
	public String getProhibitionEnergySaving() {
		return prohibitionEnergySaving;
	}

	/**
	 * @param prohibitonEnergySaving
	 *            the prohibitonEnergySaving to set
	 */
	public void setProhibitionEnergySaving(String prohibitonEnergySaving) {
		this.prohibitionEnergySaving = prohibitonEnergySaving;
	}

}

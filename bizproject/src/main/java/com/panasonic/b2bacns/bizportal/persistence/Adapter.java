package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the adapters database table.
 * 
 */
@Entity
@Table(name = "adapters")
@NamedQuery(name = "Adapter.findAll", query = "SELECT a FROM Adapter a")
public class Adapter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	private Timestamp activatedate;

	@Column(length = 255)
	private String address;

	@Column(length = 255)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	@Column(name = "enabled_date")
	private Timestamp enabledDate;

	@Column(length = 255)
	private String ipnumber;

	private Float latitude;

	private Float longitude;

	@Column(precision = 19, scale = 2)
	private BigDecimal notificationrecipients;

	@Column(name = "scanned_date")
	private Timestamp scannedDate;

	@Column(name = "scanning_date")
	private Timestamp scanningDate;

	@Column(length = 255)
	private String serialnumber;

	@Column(length = 32)
	private String siteid;

	@Column(length = 255)
	private String state;

	@Column(name = "timelinefetched_date")
	private Timestamp timelinefetchedDate;

	@Column(length = 255)
	private String timelinefetchedperiod;

//	@Column(length = 255)
//	private String timezone;

	@ManyToOne
	@JoinColumn(name = "timezone", insertable = false, updatable = false)
	private Timezonemaster timezonemaster;

	private Timestamp updatedate;

	@Column(length = 255)
	private String updatedby;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "defualtgroupid")
	private Group group;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "adapter")
	private List<Indoorunit> indoorunits;

	// bi-directional many-to-one association to Notificationlog
	@OneToMany(mappedBy = "adapter")
	private List<Notificationlog> notificationlogs;

	// bi-directional many-to-one association to Outdoorunit
	@OneToMany(mappedBy = "adapter")
	private List<Outdoorunit> outdoorunits;

	// bi-directional many-to-one association to Timeline
	@OneToMany(mappedBy = "adapter")
	private List<Timeline> timelines;

	public Adapter() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getActivatedate() {
		return this.activatedate;
	}

	public void setActivatedate(Timestamp activatedate) {
		this.activatedate = activatedate;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Timestamp getEnabledDate() {
		return this.enabledDate;
	}

	public void setEnabledDate(Timestamp enabledDate) {
		this.enabledDate = enabledDate;
	}

	public String getIpnumber() {
		return this.ipnumber;
	}

	public void setIpnumber(String ipnumber) {
		this.ipnumber = ipnumber;
	}

	public Float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getNotificationrecipients() {
		return this.notificationrecipients;
	}

	public void setNotificationrecipients(BigDecimal notificationrecipients) {
		this.notificationrecipients = notificationrecipients;
	}

	public Timestamp getScannedDate() {
		return this.scannedDate;
	}

	public void setScannedDate(Timestamp scannedDate) {
		this.scannedDate = scannedDate;
	}

	public Timestamp getScanningDate() {
		return this.scanningDate;
	}

	public void setScanningDate(Timestamp scanningDate) {
		this.scanningDate = scanningDate;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getTimelinefetchedDate() {
		return this.timelinefetchedDate;
	}

	public void setTimelinefetchedDate(Timestamp timelinefetchedDate) {
		this.timelinefetchedDate = timelinefetchedDate;
	}

	public String getTimelinefetchedperiod() {
		return this.timelinefetchedperiod;
	}

	public void setTimelinefetchedperiod(String timelinefetchedperiod) {
		this.timelinefetchedperiod = timelinefetchedperiod;
	}

//	public String getTimezone() {
//		return this.timezone;
//	}
//
//	public void setTimezone(String timezone) {
//		this.timezone = timezone;
//	}

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

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Indoorunit> getIndoorunits() {
		return this.indoorunits;
	}

	public void setIndoorunits(List<Indoorunit> indoorunits) {
		this.indoorunits = indoorunits;
	}

	public Indoorunit addIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().add(indoorunit);
		indoorunit.setAdapter(this);

		return indoorunit;
	}

	public Indoorunit removeIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().remove(indoorunit);
		indoorunit.setAdapter(null);

		return indoorunit;
	}

	public List<Notificationlog> getNotificationlogs() {
		return this.notificationlogs;
	}

	public void setNotificationlogs(List<Notificationlog> notificationlogs) {
		this.notificationlogs = notificationlogs;
	}

	public Notificationlog addNotificationlog(Notificationlog notificationlog) {
		getNotificationlogs().add(notificationlog);
		notificationlog.setAdapter(this);

		return notificationlog;
	}

	public Notificationlog removeNotificationlog(Notificationlog notificationlog) {
		getNotificationlogs().remove(notificationlog);
		notificationlog.setAdapter(null);

		return notificationlog;
	}

	public List<Outdoorunit> getOutdoorunits() {
		return this.outdoorunits;
	}

	public void setOutdoorunits(List<Outdoorunit> outdoorunits) {
		this.outdoorunits = outdoorunits;
	}

	public Outdoorunit addOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().add(outdoorunit);
		outdoorunit.setAdapter(this);

		return outdoorunit;
	}

	public Outdoorunit removeOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().remove(outdoorunit);
		outdoorunit.setAdapter(null);

		return outdoorunit;
	}

	public List<Timeline> getTimelines() {
		return this.timelines;
	}

	public void setTimelines(List<Timeline> timelines) {
		this.timelines = timelines;
	}

	public Timeline addTimeline(Timeline timeline) {
		getTimelines().add(timeline);
		timeline.setAdapter(this);

		return timeline;
	}

	public Timeline removeTimeline(Timeline timeline) {
		getTimelines().remove(timeline);
		timeline.setAdapter(null);

		return timeline;
	}

	/**
	 * @return the timezonemaster
	 */
	public Timezonemaster getTimezonemaster() {
		return timezonemaster;
	}

	/**
	 * @param timezonemaster
	 *            the timezonemaster to set
	 */
	public void setTimezonemaster(Timezonemaster timezonemaster) {
		this.timezonemaster = timezonemaster;
	}

}

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the indoorunits database table.
 * 
 */
@Entity
@Table(name = "indoorunits")
@NamedQuery(name = "Indoorunit.findAll", query = "SELECT i FROM Indoorunit i")
public class Indoorunit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 45)
	private String centraladdress;

	//add by shanf
	@Column(name = "centralcontroladdress",length = 255)
	private String centralControlAddress;

	@Column(length = 45)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	private Timestamp currenttime;

	@Column(length = 32)
	private String name;

	// Modified by Ravi
	@Column(nullable = false, name = "rc_flag")
	private Integer rcFlag = 1;

	@Column(nullable = false, length = 45)
	private String serialnumber;

	@Column(length = 128)
	private String slinkaddress;

	@Column(length = 128)
	private String oid;

	@Column(name = "svg_max_latitude", precision = 17, scale = 14)
	private BigDecimal svgMaxLatitude;

	@Column(name = "svg_max_longitude", precision = 17, scale = 14)
	private BigDecimal svgMaxLongitude;

	@Column(name = "svg_min_latitude", precision = 17, scale = 14)
	private BigDecimal svgMinLatitude;

	@Column(name = "svg_min_longitude", precision = 17, scale = 14)
	private BigDecimal svgMinLongitude;

	@Column(length = 45)
	private String type;

	private Timestamp updatedate;

	@Column(length = 45)
	private String updatedby;

	// bi-directional many-to-one association to Availabletemp
	@OneToMany(mappedBy = "indoorunit")
	private List<Availabletemp> availabletemps;

	// bi-directional many-to-one association to Adapter
	@ManyToOne
	@JoinColumn(name = "adapters_id")
	private Adapter adapter;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "siteid", referencedColumnName = "uniqueid")
	private Group site;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group controlGroup;

	//add by shanf
	@ManyToOne
	@JoinColumn(name="distribution_group_id")
	private DistributionGroup distributionGroup;
	
	

	public DistributionGroup getDistribution_group_id() {
		return distributionGroup;
	}

	public void setDistribution_group_id(DistributionGroup distribution_group_id) {
		this.distributionGroup = distributionGroup;
	}

	// bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Indoorunit indoorunit;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "indoorunit")
	private List<Indoorunit> indoorunits;

	// bi-directional many-to-one association to Metaindoorunit
	@ManyToOne
	@JoinColumn(name = "metaindoorunit_id")
	private Metaindoorunit metaindoorunit;

	// bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name = "outdoorunit_id")
	private Outdoorunit outdoorunit;

	// bi-directional many-to-one association to SvgMaster
	@ManyToOne
	@JoinColumn(name = "svg_id")
	private SvgMaster svgMaster;

	// bi-directional many-to-one association to Indoorunitslog
	@OneToMany(mappedBy = "indoorunit")
	private List<Indoorunitslog> indoorunitslogs;

	// bi-directional many-to-one association to IndoorunitslogHistory
	@OneToMany(mappedBy = "indoorunit")
	private List<IndoorunitslogHistory> indoorunitslogHistories;

	// bi-directional many-to-one association to Indoorunitstatistic
	@OneToMany(mappedBy = "indoorunit")
	private List<Indoorunitstatistic> indoorunitstatistics;

	// bi-directional many-to-one association to IndoorunitstatisticsDaily
	@OneToMany(mappedBy = "indoorunit")
	private List<IndoorunitstatisticsDaily> indoorunitstatisticsDailies;

	// bi-directional many-to-one association to IndoorunitstatisticsMonthly
	@OneToMany(mappedBy = "indoorunit")
	private List<IndoorunitstatisticsMonthly> indoorunitstatisticsMonthlies;

	// bi-directional many-to-one association to IndoorunitstatisticsWeekly
	@OneToMany(mappedBy = "indoorunit")
	private List<IndoorunitstatisticsWeekly> indoorunitstatisticsWeeklies;

	// bi-directional many-to-one association to IndoorunitstatisticsYearly
	@OneToMany(mappedBy = "indoorunit")
	private List<IndoorunitstatisticsYearly> indoorunitstatisticsYearlies;

	// bi-directional many-to-one association to Notificationlog
	@OneToMany(mappedBy = "indoorunit")
	private List<Notificationlog> notificationlogs;

	// bi-directional many-to-one association to PowerConsumptionCapacity
	@OneToMany(mappedBy = "indoorunit")
	private List<PowerConsumptionCapacity> powerConsumptionCapacities;

	// bi-directional many-to-one association to PowerConsumptionCapacityDaily
	@OneToMany(mappedBy = "indoorunit")
	private List<PowerConsumptionCapacityDaily> powerConsumptionCapacityDailies;

	// bi-directional many-to-one association to PowerConsumptionCapacityMonthly
	@OneToMany(mappedBy = "indoorunit")
	private List<PowerConsumptionCapacityMonthly> powerConsumptionCapacityMonthlies;

	// bi-directional many-to-one association to PowerConsumptionCapacityWeekly
	@OneToMany(mappedBy = "indoorunit")
	private List<PowerConsumptionCapacityWeekly> powerConsumptionCapacityWeeklies;

	// bi-directional many-to-one association to PowerConsumptionCapacityYearly
	@OneToMany(mappedBy = "indoorunit")
	private List<PowerConsumptionCapacityYearly> powerConsumptionCapacityYearlies;

	// bi-directional many-to-one association to RcProhibition
	@OneToMany(mappedBy = "indoorunit")
	private List<RcProhibition> rcProhibitions;

	// bi-directional many-to-one association to RcoperationLog
	@OneToMany(mappedBy = "indoorunit")
	private List<RcoperationLog> rcoperationLogs;

	// bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name = "area_id")
	private Area area;

	/* Added by ravi */
	@Column(length = 255)
	private String connectionnumber;
	
	@Column(length = 255)
	private String device_model;
	
	@Column(length = 255)
	private String mainiduaddress;
	
	@Column(length = 255)
	private String refrigcircuitno;
	
	@Column(length = 255)
	private String connectiontype;
	
	@Column(length = 255)
	private String connectionaddress;
	/* end of adding by ravi*/
	
	public Indoorunit() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCentraladdress() {
		return this.centraladdress;
	}

	public void setCentraladdress(String centraladdress) {
		this.centraladdress = centraladdress;
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

	public Timestamp getCurrenttime() {
		return this.currenttime;
	}

	public void setCurrenttime(Timestamp currenttime) {
		this.currenttime = currenttime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public BigDecimal getSvgMaxLatitude() {
		return this.svgMaxLatitude;
	}

	public void setSvgMaxLatitude(BigDecimal svgMaxLatitude) {
		this.svgMaxLatitude = svgMaxLatitude;
	}

	public BigDecimal getSvgMaxLongitude() {
		return this.svgMaxLongitude;
	}

	public void setSvgMaxLongitude(BigDecimal svgMaxLongitude) {
		this.svgMaxLongitude = svgMaxLongitude;
	}

	public BigDecimal getSvgMinLatitude() {
		return this.svgMinLatitude;
	}

	public void setSvgMinLatitude(BigDecimal svgMinLatitude) {
		this.svgMinLatitude = svgMinLatitude;
	}

	public BigDecimal getSvgMinLongitude() {
		return this.svgMinLongitude;
	}

	public void setSvgMinLongitude(BigDecimal svgMinLongitude) {
		this.svgMinLongitude = svgMinLongitude;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<Availabletemp> getAvailabletemps() {
		return this.availabletemps;
	}

	public void setAvailabletemps(List<Availabletemp> availabletemps) {
		this.availabletemps = availabletemps;
	}

	public Availabletemp addAvailabletemp(Availabletemp availabletemp) {
		getAvailabletemps().add(availabletemp);
		availabletemp.setIndoorunit(this);

		return availabletemp;
	}

	public Availabletemp removeAvailabletemp(Availabletemp availabletemp) {
		getAvailabletemps().remove(availabletemp);
		availabletemp.setIndoorunit(null);

		return availabletemp;
	}

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	public Indoorunit getIndoorunit() {
		return this.indoorunit;
	}

	public void setIndoorunit(Indoorunit indoorunit) {
		this.indoorunit = indoorunit;
	}

	public List<Indoorunit> getIndoorunits() {
		return this.indoorunits;
	}

	public void setIndoorunits(List<Indoorunit> indoorunits) {
		this.indoorunits = indoorunits;
	}

	public Indoorunit addIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().add(indoorunit);
		indoorunit.setIndoorunit(this);

		return indoorunit;
	}

	public Indoorunit removeIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().remove(indoorunit);
		indoorunit.setIndoorunit(null);

		return indoorunit;
	}

	public Metaindoorunit getMetaindoorunit() {
		return this.metaindoorunit;
	}

	public void setMetaindoorunit(Metaindoorunit metaindoorunit) {
		this.metaindoorunit = metaindoorunit;
	}

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

	public List<Indoorunitslog> getIndoorunitslogs() {
		return this.indoorunitslogs;
	}

	public void setIndoorunitslogs(List<Indoorunitslog> indoorunitslogs) {
		this.indoorunitslogs = indoorunitslogs;
	}

	public Indoorunitslog addIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().add(indoorunitslog);
		indoorunitslog.setIndoorunit(this);

		return indoorunitslog;
	}

	public Indoorunitslog removeIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().remove(indoorunitslog);
		indoorunitslog.setIndoorunit(null);

		return indoorunitslog;
	}

	public List<IndoorunitslogHistory> getIndoorunitslogHistories() {
		return this.indoorunitslogHistories;
	}

	public void setIndoorunitslogHistories(
			List<IndoorunitslogHistory> indoorunitslogHistories) {
		this.indoorunitslogHistories = indoorunitslogHistories;
	}

	public IndoorunitslogHistory addIndoorunitslogHistory(
			IndoorunitslogHistory indoorunitslogHistory) {
		getIndoorunitslogHistories().add(indoorunitslogHistory);
		indoorunitslogHistory.setIndoorunit(this);

		return indoorunitslogHistory;
	}

	public IndoorunitslogHistory removeIndoorunitslogHistory(
			IndoorunitslogHistory indoorunitslogHistory) {
		getIndoorunitslogHistories().remove(indoorunitslogHistory);
		indoorunitslogHistory.setIndoorunit(null);

		return indoorunitslogHistory;
	}

	public List<Indoorunitstatistic> getIndoorunitstatistics() {
		return this.indoorunitstatistics;
	}

	public void setIndoorunitstatistics(
			List<Indoorunitstatistic> indoorunitstatistics) {
		this.indoorunitstatistics = indoorunitstatistics;
	}

	public Indoorunitstatistic addIndoorunitstatistic(
			Indoorunitstatistic indoorunitstatistic) {
		getIndoorunitstatistics().add(indoorunitstatistic);
		indoorunitstatistic.setIndoorunit(this);

		return indoorunitstatistic;
	}

	public Indoorunitstatistic removeIndoorunitstatistic(
			Indoorunitstatistic indoorunitstatistic) {
		getIndoorunitstatistics().remove(indoorunitstatistic);
		indoorunitstatistic.setIndoorunit(null);

		return indoorunitstatistic;
	}

	public List<IndoorunitstatisticsDaily> getIndoorunitstatisticsDailies() {
		return this.indoorunitstatisticsDailies;
	}

	public void setIndoorunitstatisticsDailies(
			List<IndoorunitstatisticsDaily> indoorunitstatisticsDailies) {
		this.indoorunitstatisticsDailies = indoorunitstatisticsDailies;
	}

	public IndoorunitstatisticsDaily addIndoorunitstatisticsDaily(
			IndoorunitstatisticsDaily indoorunitstatisticsDaily) {
		getIndoorunitstatisticsDailies().add(indoorunitstatisticsDaily);
		indoorunitstatisticsDaily.setIndoorunit(this);

		return indoorunitstatisticsDaily;
	}

	public IndoorunitstatisticsDaily removeIndoorunitstatisticsDaily(
			IndoorunitstatisticsDaily indoorunitstatisticsDaily) {
		getIndoorunitstatisticsDailies().remove(indoorunitstatisticsDaily);
		indoorunitstatisticsDaily.setIndoorunit(null);

		return indoorunitstatisticsDaily;
	}

	public List<IndoorunitstatisticsMonthly> getIndoorunitstatisticsMonthlies() {
		return this.indoorunitstatisticsMonthlies;
	}

	public void setIndoorunitstatisticsMonthlies(
			List<IndoorunitstatisticsMonthly> indoorunitstatisticsMonthlies) {
		this.indoorunitstatisticsMonthlies = indoorunitstatisticsMonthlies;
	}

	public IndoorunitstatisticsMonthly addIndoorunitstatisticsMonthly(
			IndoorunitstatisticsMonthly indoorunitstatisticsMonthly) {
		getIndoorunitstatisticsMonthlies().add(indoorunitstatisticsMonthly);
		indoorunitstatisticsMonthly.setIndoorunit(this);

		return indoorunitstatisticsMonthly;
	}

	public IndoorunitstatisticsMonthly removeIndoorunitstatisticsMonthly(
			IndoorunitstatisticsMonthly indoorunitstatisticsMonthly) {
		getIndoorunitstatisticsMonthlies().remove(indoorunitstatisticsMonthly);
		indoorunitstatisticsMonthly.setIndoorunit(null);

		return indoorunitstatisticsMonthly;
	}

	public List<IndoorunitstatisticsWeekly> getIndoorunitstatisticsWeeklies() {
		return this.indoorunitstatisticsWeeklies;
	}

	public void setIndoorunitstatisticsWeeklies(
			List<IndoorunitstatisticsWeekly> indoorunitstatisticsWeeklies) {
		this.indoorunitstatisticsWeeklies = indoorunitstatisticsWeeklies;
	}

	public IndoorunitstatisticsWeekly addIndoorunitstatisticsWeekly(
			IndoorunitstatisticsWeekly indoorunitstatisticsWeekly) {
		getIndoorunitstatisticsWeeklies().add(indoorunitstatisticsWeekly);
		indoorunitstatisticsWeekly.setIndoorunit(this);

		return indoorunitstatisticsWeekly;
	}

	public IndoorunitstatisticsWeekly removeIndoorunitstatisticsWeekly(
			IndoorunitstatisticsWeekly indoorunitstatisticsWeekly) {
		getIndoorunitstatisticsWeeklies().remove(indoorunitstatisticsWeekly);
		indoorunitstatisticsWeekly.setIndoorunit(null);

		return indoorunitstatisticsWeekly;
	}

	public List<IndoorunitstatisticsYearly> getIndoorunitstatisticsYearlies() {
		return this.indoorunitstatisticsYearlies;
	}

	public void setIndoorunitstatisticsYearlies(
			List<IndoorunitstatisticsYearly> indoorunitstatisticsYearlies) {
		this.indoorunitstatisticsYearlies = indoorunitstatisticsYearlies;
	}

	public IndoorunitstatisticsYearly addIndoorunitstatisticsYearly(
			IndoorunitstatisticsYearly indoorunitstatisticsYearly) {
		getIndoorunitstatisticsYearlies().add(indoorunitstatisticsYearly);
		indoorunitstatisticsYearly.setIndoorunit(this);

		return indoorunitstatisticsYearly;
	}

	public IndoorunitstatisticsYearly removeIndoorunitstatisticsYearly(
			IndoorunitstatisticsYearly indoorunitstatisticsYearly) {
		getIndoorunitstatisticsYearlies().remove(indoorunitstatisticsYearly);
		indoorunitstatisticsYearly.setIndoorunit(null);

		return indoorunitstatisticsYearly;
	}

	public List<Notificationlog> getNotificationlogs() {
		return this.notificationlogs;
	}

	public void setNotificationlogs(List<Notificationlog> notificationlogs) {
		this.notificationlogs = notificationlogs;
	}

	public Notificationlog addNotificationlog(Notificationlog notificationlog) {
		getNotificationlogs().add(notificationlog);
		notificationlog.setIndoorunit(this);

		return notificationlog;
	}

	public Notificationlog removeNotificationlog(Notificationlog notificationlog) {
		getNotificationlogs().remove(notificationlog);
		notificationlog.setIndoorunit(null);

		return notificationlog;
	}

	public List<PowerConsumptionCapacity> getPowerConsumptionCapacities() {
		return this.powerConsumptionCapacities;
	}

	public void setPowerConsumptionCapacities(
			List<PowerConsumptionCapacity> powerConsumptionCapacities) {
		this.powerConsumptionCapacities = powerConsumptionCapacities;
	}

	public PowerConsumptionCapacity addPowerConsumptionCapacity(
			PowerConsumptionCapacity powerConsumptionCapacity) {
		getPowerConsumptionCapacities().add(powerConsumptionCapacity);
		powerConsumptionCapacity.setIndoorunit(this);

		return powerConsumptionCapacity;
	}

	public PowerConsumptionCapacity removePowerConsumptionCapacity(
			PowerConsumptionCapacity powerConsumptionCapacity) {
		getPowerConsumptionCapacities().remove(powerConsumptionCapacity);
		powerConsumptionCapacity.setIndoorunit(null);

		return powerConsumptionCapacity;
	}

	public List<PowerConsumptionCapacityDaily> getPowerConsumptionCapacityDailies() {
		return this.powerConsumptionCapacityDailies;
	}

	public void setPowerConsumptionCapacityDailies(
			List<PowerConsumptionCapacityDaily> powerConsumptionCapacityDailies) {
		this.powerConsumptionCapacityDailies = powerConsumptionCapacityDailies;
	}

	public PowerConsumptionCapacityDaily addPowerConsumptionCapacityDaily(
			PowerConsumptionCapacityDaily powerConsumptionCapacityDaily) {
		getPowerConsumptionCapacityDailies().add(powerConsumptionCapacityDaily);
		powerConsumptionCapacityDaily.setIndoorunit(this);

		return powerConsumptionCapacityDaily;
	}

	public PowerConsumptionCapacityDaily removePowerConsumptionCapacityDaily(
			PowerConsumptionCapacityDaily powerConsumptionCapacityDaily) {
		getPowerConsumptionCapacityDailies().remove(
				powerConsumptionCapacityDaily);
		powerConsumptionCapacityDaily.setIndoorunit(null);

		return powerConsumptionCapacityDaily;
	}

	public List<PowerConsumptionCapacityMonthly> getPowerConsumptionCapacityMonthlies() {
		return this.powerConsumptionCapacityMonthlies;
	}

	public void setPowerConsumptionCapacityMonthlies(
			List<PowerConsumptionCapacityMonthly> powerConsumptionCapacityMonthlies) {
		this.powerConsumptionCapacityMonthlies = powerConsumptionCapacityMonthlies;
	}

	public PowerConsumptionCapacityMonthly addPowerConsumptionCapacityMonthly(
			PowerConsumptionCapacityMonthly powerConsumptionCapacityMonthly) {
		getPowerConsumptionCapacityMonthlies().add(
				powerConsumptionCapacityMonthly);
		powerConsumptionCapacityMonthly.setIndoorunit(this);

		return powerConsumptionCapacityMonthly;
	}

	public PowerConsumptionCapacityMonthly removePowerConsumptionCapacityMonthly(
			PowerConsumptionCapacityMonthly powerConsumptionCapacityMonthly) {
		getPowerConsumptionCapacityMonthlies().remove(
				powerConsumptionCapacityMonthly);
		powerConsumptionCapacityMonthly.setIndoorunit(null);

		return powerConsumptionCapacityMonthly;
	}

	public List<PowerConsumptionCapacityWeekly> getPowerConsumptionCapacityWeeklies() {
		return this.powerConsumptionCapacityWeeklies;
	}

	public void setPowerConsumptionCapacityWeeklies(
			List<PowerConsumptionCapacityWeekly> powerConsumptionCapacityWeeklies) {
		this.powerConsumptionCapacityWeeklies = powerConsumptionCapacityWeeklies;
	}

	public PowerConsumptionCapacityWeekly addPowerConsumptionCapacityWeekly(
			PowerConsumptionCapacityWeekly powerConsumptionCapacityWeekly) {
		getPowerConsumptionCapacityWeeklies().add(
				powerConsumptionCapacityWeekly);
		powerConsumptionCapacityWeekly.setIndoorunit(this);

		return powerConsumptionCapacityWeekly;
	}

	public PowerConsumptionCapacityWeekly removePowerConsumptionCapacityWeekly(
			PowerConsumptionCapacityWeekly powerConsumptionCapacityWeekly) {
		getPowerConsumptionCapacityWeeklies().remove(
				powerConsumptionCapacityWeekly);
		powerConsumptionCapacityWeekly.setIndoorunit(null);

		return powerConsumptionCapacityWeekly;
	}

	public List<PowerConsumptionCapacityYearly> getPowerConsumptionCapacityYearlies() {
		return this.powerConsumptionCapacityYearlies;
	}

	public void setPowerConsumptionCapacityYearlies(
			List<PowerConsumptionCapacityYearly> powerConsumptionCapacityYearlies) {
		this.powerConsumptionCapacityYearlies = powerConsumptionCapacityYearlies;
	}

	public PowerConsumptionCapacityYearly addPowerConsumptionCapacityYearly(
			PowerConsumptionCapacityYearly powerConsumptionCapacityYearly) {
		getPowerConsumptionCapacityYearlies().add(
				powerConsumptionCapacityYearly);
		powerConsumptionCapacityYearly.setIndoorunit(this);

		return powerConsumptionCapacityYearly;
	}

	public PowerConsumptionCapacityYearly removePowerConsumptionCapacityYearly(
			PowerConsumptionCapacityYearly powerConsumptionCapacityYearly) {
		getPowerConsumptionCapacityYearlies().remove(
				powerConsumptionCapacityYearly);
		powerConsumptionCapacityYearly.setIndoorunit(null);

		return powerConsumptionCapacityYearly;
	}

	public List<RcProhibition> getRcProhibitions() {
		return this.rcProhibitions;
	}

	public void setRcProhibitions(List<RcProhibition> rcProhibitions) {
		this.rcProhibitions = rcProhibitions;
	}

	public RcProhibition addRcProhibition(RcProhibition rcProhibition) {
		getRcProhibitions().add(rcProhibition);
		rcProhibition.setIndoorunit(this);

		return rcProhibition;
	}

	public RcProhibition removeRcProhibition(RcProhibition rcProhibition) {
		getRcProhibitions().remove(rcProhibition);
		rcProhibition.setIndoorunit(null);

		return rcProhibition;
	}

	public List<RcoperationLog> getRcoperationLogs() {
		return this.rcoperationLogs;
	}

	public void setRcoperationLogs(List<RcoperationLog> rcoperationLogs) {
		this.rcoperationLogs = rcoperationLogs;
	}

	public RcoperationLog addRcoperationLog(RcoperationLog rcoperationLog) {
		getRcoperationLogs().add(rcoperationLog);
		rcoperationLog.setIndoorunit(this);

		return rcoperationLog;
	}

	public RcoperationLog removeRcoperationLog(RcoperationLog rcoperationLog) {
		getRcoperationLogs().remove(rcoperationLog);
		rcoperationLog.setIndoorunit(null);

		return rcoperationLog;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public SvgMaster getSvgMaster() {
		return this.svgMaster;
	}

	public void setSvgMaster(SvgMaster svgMaster) {
		this.svgMaster = svgMaster;
	}

	public Group getSite() {
		return this.site;
	}

	public void setSite(Group group1) {
		this.site = group1;
	}

	public Group getControlGroup() {
		return this.controlGroup;
	}

	public void setControlGroup(Group controlGroup) {
		this.controlGroup = controlGroup;
	}

	public Integer getRcFlag() {
		return this.rcFlag;
	}

	public void setRcFlag(Integer rcFlag) {
		this.rcFlag = rcFlag;
	}

	public String getSlinkaddress() {
		return this.slinkaddress;
	}

	public void setSlinkaddress(String slinkaddress) {
		this.slinkaddress = slinkaddress;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	/* Added by ravi */
	public String getConnectionnumber() {
		return connectionnumber;
	}

	public void setConnectionnumber(String connectionnumber) {
		this.connectionnumber = connectionnumber;
	}
	
	public String getDevice_model() {
		return device_model;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	
	public String getMainiduaddress() {
		return mainiduaddress;
	}

	public void setMainiduaddress(String mainiduaddress) {
		this.mainiduaddress = mainiduaddress;
	}
	
	public String getRefrigcircuitno() {
		return refrigcircuitno;
	}

	public void setRefrigcircuitno(String refrigcircuitno) {
		this.refrigcircuitno = refrigcircuitno;
	}
	
	public String getConnectiontype() {
		return connectiontype;
	}

	public void setConnectiontype(String connectiontype) {
		this.connectiontype = connectiontype;
	}

	public String getConnectionaddress() {
		return connectionaddress;
	}

	public void setConnectionaddress(String connectionaddress) {
		this.connectionaddress = connectionaddress;
	}
	/* End of adding by ravi */

	//add by shanf
	public String getCentralControlAddress() {
		return centralControlAddress;
	}

	public void setCentralControlAddress(String centralControlAddress) {
		this.centralControlAddress = centralControlAddress;
	}
}

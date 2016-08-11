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
 * The persistent class for the outdoorunits database table.
 * 
 */
@Entity
@Table(name = "outdoorunits")
@NamedQuery(name = "Outdoorunit.findAll", query = "SELECT o FROM Outdoorunit o")
public class Outdoorunit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 255)
	private String centraladdress;

	@Column(length = 255)
	private String createdby;

	private Timestamp creationdate;

	private Integer iswaterheatexchanger;

	@Column(length = 255)
	private String serialnumber;

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

	@Column(length = 255)
	private String type;

	private Timestamp updatedate;

	@Column(length = 255)
	private String updatedby;

	// bi-directional many-to-one association to GasheatmeterData
	@OneToMany(mappedBy = "outdoorunit")
	private List<GasheatmeterData> gasheatmeterData;

	// bi-directional many-to-one association to GasheatmeterDataDaily
	@OneToMany(mappedBy = "outdoorunit")
	private List<GasheatmeterDataDaily> gasheatmeterDataDailies;

	// bi-directional many-to-one association to GasheatmeterDataMonthly
	@OneToMany(mappedBy = "outdoorunit")
	private List<GasheatmeterDataMonthly> gasheatmeterDataMonthlies;

	// bi-directional many-to-one association to GasheatmeterDataWeekly
	@OneToMany(mappedBy = "outdoorunit")
	private List<GasheatmeterDataWeekly> gasheatmeterDataWeeklies;

	// bi-directional many-to-one association to GasheatmeterDataYearly
	@OneToMany(mappedBy = "outdoorunit")
	private List<GasheatmeterDataYearly> gasheatmeterDataYearlies;

	// bi-directional many-to-one association to GhpparameterStatistic
	@OneToMany(mappedBy = "outdoorunit")
	private List<GhpparameterStatistic> ghpparameterStatistics;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "outdoorunit")
	private List<Indoorunit> indoorunits;

	// bi-directional many-to-one association to Notificationlog
	@OneToMany(mappedBy = "outdoorunit")
	private List<Notificationlog> notificationlogs;

	// bi-directional many-to-one association to Adapter
	@ManyToOne
	@JoinColumn(name = "adapters_id")
	private Adapter adapter;

	// bi-directional many-to-one association to Metaoutdoorunit
	@ManyToOne
	@JoinColumn(name = "metaoutdoorunit_id")
	private Metaoutdoorunit metaoutdoorunit;

	// bi-directional many-to-one association to Outdoorunitslog
	@OneToMany(mappedBy = "outdoorunit")
	private List<Outdoorunitslog> outdoorunitslogs;

	// bi-directional many-to-one association to OutdoorunitslogHistory
	@OneToMany(mappedBy = "outdoorunit")
	private List<OutdoorunitslogHistory> outdoorunitslogHistories;

	// bi-directional many-to-one association to VrfparameterStatistic
	@OneToMany(mappedBy = "outdoorunit")
	private List<VrfparameterStatistic> vrfparameterStatistics;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "siteid", referencedColumnName = "uniqueid")
	private Group group;

	// bi-directional many-to-one association to Outdoorunit
	@ManyToOne
	@JoinColumn(name = "parentid")
	private Outdoorunit outdoorunit;

	// bi-directional many-to-one association to Outdoorunit
	@OneToMany(mappedBy = "outdoorunit")
	private List<Outdoorunit> outdoorunits;

	// bi-directional many-to-one association to Refrigerantmaster
	@ManyToOne
	@JoinColumn(name = "refid")
	private Refrigerantmaster refrigerantmaster;

	// bi-directional many-to-one association to SvgMaster
	@ManyToOne
	@JoinColumn(name = "svg_id")
	private SvgMaster svgMaster;

	@Column(length = 128)
	private String slinkaddress;

	/* Added by Ravi */
	@Column(length = 255)
	private String refrigcircuitgroupoduid;
	
	@Column(length = 255)
	private Integer refrigcircuitno;
	
	@Column(length = 255)
	private String device_model;
	
	@Column(length = 255)
	private String connectionnumber;
	
	@Column(length = 32)
	private String name;
	
	Long connectiontype;
	
	private Integer refrigerantid;
	/* end of adding by ravi*/

	public Outdoorunit() {
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

	public Integer getIswaterheatexchanger() {
		return this.iswaterheatexchanger;
	}

	public void setIswaterheatexchanger(Integer iswaterheatexchanger) {
		this.iswaterheatexchanger = iswaterheatexchanger;
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

	public List<GasheatmeterData> getGasheatmeterData() {
		return this.gasheatmeterData;
	}

	public void setGasheatmeterData(List<GasheatmeterData> gasheatmeterData) {
		this.gasheatmeterData = gasheatmeterData;
	}

	public GasheatmeterData addGasheatmeterData(
			GasheatmeterData gasheatmeterData) {
		getGasheatmeterData().add(gasheatmeterData);
		gasheatmeterData.setOutdoorunit(this);

		return gasheatmeterData;
	}

	public GasheatmeterData removeGasheatmeterData(
			GasheatmeterData gasheatmeterData) {
		getGasheatmeterData().remove(gasheatmeterData);
		gasheatmeterData.setOutdoorunit(null);

		return gasheatmeterData;
	}

	public List<GasheatmeterDataDaily> getGasheatmeterDataDailies() {
		return this.gasheatmeterDataDailies;
	}

	public void setGasheatmeterDataDailies(
			List<GasheatmeterDataDaily> gasheatmeterDataDailies) {
		this.gasheatmeterDataDailies = gasheatmeterDataDailies;
	}

	public GasheatmeterDataDaily addGasheatmeterDataDaily(
			GasheatmeterDataDaily gasheatmeterDataDaily) {
		getGasheatmeterDataDailies().add(gasheatmeterDataDaily);
		gasheatmeterDataDaily.setOutdoorunit(this);

		return gasheatmeterDataDaily;
	}

	public GasheatmeterDataDaily removeGasheatmeterDataDaily(
			GasheatmeterDataDaily gasheatmeterDataDaily) {
		getGasheatmeterDataDailies().remove(gasheatmeterDataDaily);
		gasheatmeterDataDaily.setOutdoorunit(null);

		return gasheatmeterDataDaily;
	}

	public List<GasheatmeterDataMonthly> getGasheatmeterDataMonthlies() {
		return this.gasheatmeterDataMonthlies;
	}

	public void setGasheatmeterDataMonthlies(
			List<GasheatmeterDataMonthly> gasheatmeterDataMonthlies) {
		this.gasheatmeterDataMonthlies = gasheatmeterDataMonthlies;
	}

	public GasheatmeterDataMonthly addGasheatmeterDataMonthly(
			GasheatmeterDataMonthly gasheatmeterDataMonthly) {
		getGasheatmeterDataMonthlies().add(gasheatmeterDataMonthly);
		gasheatmeterDataMonthly.setOutdoorunit(this);

		return gasheatmeterDataMonthly;
	}

	public GasheatmeterDataMonthly removeGasheatmeterDataMonthly(
			GasheatmeterDataMonthly gasheatmeterDataMonthly) {
		getGasheatmeterDataMonthlies().remove(gasheatmeterDataMonthly);
		gasheatmeterDataMonthly.setOutdoorunit(null);

		return gasheatmeterDataMonthly;
	}

	public List<GasheatmeterDataWeekly> getGasheatmeterDataWeeklies() {
		return this.gasheatmeterDataWeeklies;
	}

	public void setGasheatmeterDataWeeklies(
			List<GasheatmeterDataWeekly> gasheatmeterDataWeeklies) {
		this.gasheatmeterDataWeeklies = gasheatmeterDataWeeklies;
	}

	public GasheatmeterDataWeekly addGasheatmeterDataWeekly(
			GasheatmeterDataWeekly gasheatmeterDataWeekly) {
		getGasheatmeterDataWeeklies().add(gasheatmeterDataWeekly);
		gasheatmeterDataWeekly.setOutdoorunit(this);

		return gasheatmeterDataWeekly;
	}

	public GasheatmeterDataWeekly removeGasheatmeterDataWeekly(
			GasheatmeterDataWeekly gasheatmeterDataWeekly) {
		getGasheatmeterDataWeeklies().remove(gasheatmeterDataWeekly);
		gasheatmeterDataWeekly.setOutdoorunit(null);

		return gasheatmeterDataWeekly;
	}

	public List<GasheatmeterDataYearly> getGasheatmeterDataYearlies() {
		return this.gasheatmeterDataYearlies;
	}

	public void setGasheatmeterDataYearlies(
			List<GasheatmeterDataYearly> gasheatmeterDataYearlies) {
		this.gasheatmeterDataYearlies = gasheatmeterDataYearlies;
	}

	public GasheatmeterDataYearly addGasheatmeterDataYearly(
			GasheatmeterDataYearly gasheatmeterDataYearly) {
		getGasheatmeterDataYearlies().add(gasheatmeterDataYearly);
		gasheatmeterDataYearly.setOutdoorunit(this);

		return gasheatmeterDataYearly;
	}

	public GasheatmeterDataYearly removeGasheatmeterDataYearly(
			GasheatmeterDataYearly gasheatmeterDataYearly) {
		getGasheatmeterDataYearlies().remove(gasheatmeterDataYearly);
		gasheatmeterDataYearly.setOutdoorunit(null);

		return gasheatmeterDataYearly;
	}

	public List<GhpparameterStatistic> getGhpparameterStatistics() {
		return this.ghpparameterStatistics;
	}

	public void setGhpparameterStatistics(
			List<GhpparameterStatistic> ghpparameterStatistics) {
		this.ghpparameterStatistics = ghpparameterStatistics;
	}

	public GhpparameterStatistic addGhpparameterStatistic(
			GhpparameterStatistic ghpparameterStatistic) {
		getGhpparameterStatistics().add(ghpparameterStatistic);
		ghpparameterStatistic.setOutdoorunit(this);

		return ghpparameterStatistic;
	}

	public GhpparameterStatistic removeGhpparameterStatistic(
			GhpparameterStatistic ghpparameterStatistic) {
		getGhpparameterStatistics().remove(ghpparameterStatistic);
		ghpparameterStatistic.setOutdoorunit(null);

		return ghpparameterStatistic;
	}

	public List<Indoorunit> getIndoorunits() {
		return this.indoorunits;
	}

	public void setIndoorunits(List<Indoorunit> indoorunits) {
		this.indoorunits = indoorunits;
	}

	public Indoorunit addIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().add(indoorunit);
		indoorunit.setOutdoorunit(this);

		return indoorunit;
	}

	public Indoorunit removeIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().remove(indoorunit);
		indoorunit.setOutdoorunit(null);

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
		notificationlog.setOutdoorunit(this);

		return notificationlog;
	}

	public Notificationlog removeNotificationlog(Notificationlog notificationlog) {
		getNotificationlogs().remove(notificationlog);
		notificationlog.setOutdoorunit(null);

		return notificationlog;
	}

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	public Metaoutdoorunit getMetaoutdoorunit() {
		return this.metaoutdoorunit;
	}

	public void setMetaoutdoorunit(Metaoutdoorunit metaoutdoorunit) {
		this.metaoutdoorunit = metaoutdoorunit;
	}

	public List<Outdoorunitslog> getOutdoorunitslogs() {
		return this.outdoorunitslogs;
	}

	public void setOutdoorunitslogs(List<Outdoorunitslog> outdoorunitslogs) {
		this.outdoorunitslogs = outdoorunitslogs;
	}

	public Outdoorunitslog addOutdoorunitslog(Outdoorunitslog outdoorunitslog) {
		getOutdoorunitslogs().add(outdoorunitslog);
		outdoorunitslog.setOutdoorunit(this);

		return outdoorunitslog;
	}

	public Outdoorunitslog removeOutdoorunitslog(Outdoorunitslog outdoorunitslog) {
		getOutdoorunitslogs().remove(outdoorunitslog);
		outdoorunitslog.setOutdoorunit(null);

		return outdoorunitslog;
	}

	public List<OutdoorunitslogHistory> getOutdoorunitslogHistories() {
		return this.outdoorunitslogHistories;
	}

	public void setOutdoorunitslogHistories(
			List<OutdoorunitslogHistory> outdoorunitslogHistories) {
		this.outdoorunitslogHistories = outdoorunitslogHistories;
	}

	public OutdoorunitslogHistory addOutdoorunitslogHistory(
			OutdoorunitslogHistory outdoorunitslogHistory) {
		getOutdoorunitslogHistories().add(outdoorunitslogHistory);
		outdoorunitslogHistory.setOutdoorunit(this);

		return outdoorunitslogHistory;
	}

	public OutdoorunitslogHistory removeOutdoorunitslogHistory(
			OutdoorunitslogHistory outdoorunitslogHistory) {
		getOutdoorunitslogHistories().remove(outdoorunitslogHistory);
		outdoorunitslogHistory.setOutdoorunit(null);

		return outdoorunitslogHistory;
	}

	public List<VrfparameterStatistic> getVrfparameterStatistics() {
		return this.vrfparameterStatistics;
	}

	public void setVrfparameterStatistics(
			List<VrfparameterStatistic> vrfparameterStatistics) {
		this.vrfparameterStatistics = vrfparameterStatistics;
	}

	public VrfparameterStatistic addVrfparameterStatistic(
			VrfparameterStatistic vrfparameterStatistic) {
		getVrfparameterStatistics().add(vrfparameterStatistic);
		vrfparameterStatistic.setOutdoorunit(this);

		return vrfparameterStatistic;
	}

	public VrfparameterStatistic removeVrfparameterStatistic(
			VrfparameterStatistic vrfparameterStatistic) {
		getVrfparameterStatistics().remove(vrfparameterStatistic);
		vrfparameterStatistic.setOutdoorunit(null);

		return vrfparameterStatistic;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getSlinkaddress() {
		return this.slinkaddress;
	}

	public void setSlinkaddress(String slinkaddress) {
		this.slinkaddress = slinkaddress;
	}

	public Outdoorunit getOutdoorunit() {
		return this.outdoorunit;
	}

	public void setOutdoorunit(Outdoorunit outdoorunit) {
		this.outdoorunit = outdoorunit;
	}

	public List<Outdoorunit> getOutdoorunits() {
		return this.outdoorunits;
	}

	public void setOutdoorunits(List<Outdoorunit> outdoorunits) {
		this.outdoorunits = outdoorunits;
	}

	public Outdoorunit addOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().add(outdoorunit);
		outdoorunit.setOutdoorunit(this);

		return outdoorunit;
	}

	public Outdoorunit removeOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().remove(outdoorunit);
		outdoorunit.setOutdoorunit(null);

		return outdoorunit;
	}

	public Refrigerantmaster getRefrigerantmaster() {
		return this.refrigerantmaster;
	}

	public void setRefrigerantmaster(Refrigerantmaster refrigerantmaster) {
		this.refrigerantmaster = refrigerantmaster;
	}

	public SvgMaster getSvgMaster() {
		return this.svgMaster;
	}

	public void setSvgMaster(SvgMaster svgMaster) {
		this.svgMaster = svgMaster;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	/* Added by Ravi */
	public String getRefrigcircuitgroupoduid() {
		return refrigcircuitgroupoduid;
	}

	public void setRefrigcircuitgroupoduid(String refrigcircuitgroupoduid) {
		this.refrigcircuitgroupoduid = refrigcircuitgroupoduid;
	}
	
	public Integer getRefrigcircuitno() {
		return refrigcircuitno;
	}

	public void setRefrigcircuitno(Integer integer) {
		this.refrigcircuitno = integer;
	}
	
	public String getDevice_model() {
		return device_model;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	
	public String getConnectionnumber() {
		return connectionnumber;
	}

	public void setConnectionnumber(String connectionnumber) {
		this.connectionnumber = connectionnumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getConnectiontype() {
		return connectiontype;
	}

	public void setConnectiontype(Long connectiontype) {
		this.connectiontype = connectiontype;
	}

	public Integer getRefrigerantid() {
		return refrigerantid;
	}

	public void setRefrigerantid(Integer refrigerantid) {
		this.refrigerantid = refrigerantid;
	}
	/* end of adding by Ravi */
}

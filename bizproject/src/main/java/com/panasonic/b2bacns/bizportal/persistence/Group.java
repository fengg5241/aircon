package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name = "groups")
@NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 45)
	private String createdby;

	private Timestamp creationdate;

	@ManyToOne
	@JoinColumn(name = "groupcategoryid")
	private Groupcategory groupcategory;

	@Column(length = 45)
	private String iconimage;

	private Boolean isdel;

	@Column(name = "last_access_groupids", length = 1024)
	private String lastAccessGroupids;

	@Column(nullable = false)
	private Integer level;

	@Column(name = "map_latitude", precision = 17, scale = 14)
	private BigDecimal mapLatitude;

	@Column(name = "map_longitude", precision = 17, scale = 14)
	private BigDecimal mapLongitude;

	@Column(nullable = false, length = 45)
	private String name;

	@Column(length = 45)
	private String path;

	@Column(name = "svg_max_latitude", precision = 17, scale = 14)
	private BigDecimal svgMaxLatitude;

	@Column(name = "svg_max_longitude", precision = 17, scale = 14)
	private BigDecimal svgMaxLongitude;

	@Column(name = "svg_min_latitude", precision = 17, scale = 14)
	private BigDecimal svgMinLatitude;

	@Column(name = "svg_min_longitude", precision = 17, scale = 14)
	private BigDecimal svgMinLongitude;

	@Column(name = "svg_path", length = 255)
	private String svgPath;

	@Column(nullable = false, length = 16)
	private String uniqueid;

	private Timestamp updatedate;

	@Column(length = 45)
	private String updatedby;

	// bi-directional many-to-one association to Adapter
	@OneToMany(mappedBy = "group")
	private List<Adapter> adapters;

	// bi-directional many-to-one association to Companiesuser
	@OneToMany(mappedBy = "group")
	private List<Companiesuser> companiesusers;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "parentid")
	private Group group;

	// bi-directional many-to-one association to Group
	@OneToMany(mappedBy = "group")
	private List<Group> groups;

	// bi-directional many-to-one association to Groupscriteria
	@ManyToOne
	@JoinColumn(name = "groupcriteria_id")
	private Groupscriteria groupscriteria;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "controlGroup")
	private List<Indoorunit> indoorunits1;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "site")
	private List<Indoorunit> indoorunits2;

	// bi-directional many-to-one association to MaintenanceSetting
	@OneToMany(mappedBy = "group")
	private List<MaintenanceSetting> maintenanceSettings;

	// bi-directional many-to-one association to Outdoorunit
	@OneToMany(mappedBy = "group")
	private List<Outdoorunit> outdoorunits;

	// bi-directional many-to-one association to Refrigerantmaster
	@OneToMany(mappedBy = "group")
	private List<Refrigerantmaster> refrigerantmasters;

	// bi-directional many-to-one association to Notificationsetting
	@OneToMany(mappedBy = "group")
	private Set<Notificationsetting> notificationsettings;

	// bi-directional many-to-one association to Timezonemaster
	@ManyToOne
	@JoinColumn(name = "timezone")
	private Timezonemaster timezonemaster;

	// bi-directional many-to-one association to GroupLevel
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_level_id")
	private GroupLevel groupLevel;

	@Column(name = "isunit_exists")
	private Integer isUnitExists;

	// bi-directional many-to-one association to CutoffRequestTransaction
	@OneToMany(mappedBy = "group")
	private List<CutoffRequestTransaction> cutoffRequestTransactions;

	public GroupLevel getGroupLevel() {
		return this.groupLevel;
	}

	public Group() {
	}

	public Timezonemaster getTimezonemaster() {
		return this.timezonemaster;
	}

	public void setTimezonemaster(Timezonemaster timezonemaster) {
		this.timezonemaster = timezonemaster;
	}

	public void setGroupLevel(GroupLevel groupLevel) {
		this.groupLevel = groupLevel;
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

	/**
	 * @return the groupcategory
	 */
	public Groupcategory getGroupcategory() {
		return groupcategory;
	}

	/**
	 * @param groupcategory
	 *            the groupcategory to set
	 */
	public void setGroupcategory(Groupcategory groupcategory) {
		this.groupcategory = groupcategory;
	}

	public String getIconimage() {
		return this.iconimage;
	}

	public void setIconimage(String iconimage) {
		this.iconimage = iconimage;
	}

	public Boolean getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public String getLastAccessGroupids() {
		return this.lastAccessGroupids;
	}

	public void setLastAccessGroupids(String lastAccessGroupids) {
		this.lastAccessGroupids = lastAccessGroupids;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BigDecimal getMapLatitude() {
		return this.mapLatitude;
	}

	public void setMapLatitude(BigDecimal mapLatitude) {
		this.mapLatitude = mapLatitude;
	}

	public BigDecimal getMapLongitude() {
		return this.mapLongitude;
	}

	public void setMapLongitude(BigDecimal mapLongitude) {
		this.mapLongitude = mapLongitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getSvgPath() {
		return this.svgPath;
	}

	public void setSvgPath(String svgPath) {
		this.svgPath = svgPath;
	}

	public String getUniqueid() {
		return this.uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
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

	public List<Adapter> getAdapters() {
		return this.adapters;
	}

	public void setAdapters(List<Adapter> adapters) {
		this.adapters = adapters;
	}

	public Adapter addAdapter(Adapter adapter) {
		getAdapters().add(adapter);
		adapter.setGroup(this);

		return adapter;
	}

	public Adapter removeAdapter(Adapter adapter) {
		getAdapters().remove(adapter);
		adapter.setGroup(null);

		return adapter;
	}

	public List<Companiesuser> getCompaniesusers() {
		return this.companiesusers;
	}

	public void setCompaniesusers(List<Companiesuser> companiesusers) {
		this.companiesusers = companiesusers;
	}

	public Companiesuser addCompaniesuser(Companiesuser companiesuser) {
		getCompaniesusers().add(companiesuser);
		companiesuser.setGroup(this);

		return companiesuser;
	}

	public Companiesuser removeCompaniesuser(Companiesuser companiesuser) {
		getCompaniesusers().remove(companiesuser);
		companiesuser.setGroup(null);

		return companiesuser;
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

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setGroup(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setGroup(null);

		return group;
	}

	public Groupscriteria getGroupscriteria() {
		return this.groupscriteria;
	}

	public void setGroupscriteria(Groupscriteria groupscriteria) {
		this.groupscriteria = groupscriteria;
	}

	public Set<Notificationsetting> getNotificationsettings() {
		return this.notificationsettings;
	}

	public void setNotificationsettings(
			Set<Notificationsetting> notificationsettings) {
		this.notificationsettings = notificationsettings;
	}

	/**
	 * @return the isUnitExists
	 */
	public final Integer getIsUnitExists() {
		return isUnitExists;
	}

	/**
	 * @param isUnitExists
	 *            the isUnitExists to set
	 */
	public final void setIsUnitExists(Integer isUnitExists) {
		this.isUnitExists = isUnitExists;
	}

	public List<Indoorunit> getIndoorunits1() {
		return this.indoorunits1;
	}

	public void setIndoorunits1(List<Indoorunit> indoorunits1) {
		this.indoorunits1 = indoorunits1;
	}

	public Indoorunit addIndoorunits1(Indoorunit indoorunits1) {
		getIndoorunits1().add(indoorunits1);
		indoorunits1.setSite(this);

		return indoorunits1;
	}

	public Indoorunit removeIndoorunits1(Indoorunit indoorunits1) {
		getIndoorunits1().remove(indoorunits1);
		indoorunits1.setSite(null);

		return indoorunits1;
	}

	public List<Indoorunit> getIndoorunits2() {
		return this.indoorunits2;
	}

	public void setIndoorunits2(List<Indoorunit> indoorunits2) {
		this.indoorunits2 = indoorunits2;
	}

	public Indoorunit addIndoorunits2(Indoorunit indoorunits2) {
		getIndoorunits2().add(indoorunits2);
		indoorunits2.setControlGroup(this);

		return indoorunits2;
	}

	public Indoorunit removeIndoorunits2(Indoorunit indoorunits2) {
		getIndoorunits2().remove(indoorunits2);
		indoorunits2.setControlGroup(null);

		return indoorunits2;
	}

	public List<MaintenanceSetting> getMaintenanceSettings() {
		return this.maintenanceSettings;
	}

	public void setMaintenanceSettings(
			List<MaintenanceSetting> maintenanceSettings) {
		this.maintenanceSettings = maintenanceSettings;
	}

	public MaintenanceSetting addMaintenanceSetting(
			MaintenanceSetting maintenanceSetting) {
		getMaintenanceSettings().add(maintenanceSetting);
		maintenanceSetting.setGroup(this);

		return maintenanceSetting;
	}

	public MaintenanceSetting removeMaintenanceSetting(
			MaintenanceSetting maintenanceSetting) {
		getMaintenanceSettings().remove(maintenanceSetting);
		maintenanceSetting.setGroup(null);

		return maintenanceSetting;
	}

	public List<Outdoorunit> getOutdoorunits() {
		return this.outdoorunits;
	}

	public void setOutdoorunits(List<Outdoorunit> outdoorunits) {
		this.outdoorunits = outdoorunits;
	}

	public Outdoorunit addOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().add(outdoorunit);
		outdoorunit.setGroup(this);

		return outdoorunit;
	}

	public Outdoorunit removeOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().remove(outdoorunit);
		outdoorunit.setGroup(null);

		return outdoorunit;
	}

	public List<Refrigerantmaster> getRefrigerantmasters() {
		return this.refrigerantmasters;
	}

	public void setRefrigerantmasters(List<Refrigerantmaster> refrigerantmasters) {
		this.refrigerantmasters = refrigerantmasters;
	}

	public Refrigerantmaster addRefrigerantmaster(
			Refrigerantmaster refrigerantmaster) {
		getRefrigerantmasters().add(refrigerantmaster);
		refrigerantmaster.setGroup(this);

		return refrigerantmaster;
	}

	public Refrigerantmaster removeRefrigerantmaster(
			Refrigerantmaster refrigerantmaster) {
		getRefrigerantmasters().remove(refrigerantmaster);
		refrigerantmaster.setGroup(null);

		return refrigerantmaster;
	}

	public List<CutoffRequestTransaction> getCutoffRequestTransactions() {
		return this.cutoffRequestTransactions;
	}

	public void setCutoffRequestTransactions(
			List<CutoffRequestTransaction> cutoffRequestTransactions) {
		this.cutoffRequestTransactions = cutoffRequestTransactions;
	}

	public CutoffRequestTransaction addCutoffRequestTransaction(
			CutoffRequestTransaction cutoffRequestTransaction) {
		getCutoffRequestTransactions().add(cutoffRequestTransaction);
		cutoffRequestTransaction.setGroup(this);

		return cutoffRequestTransaction;
	}

	public CutoffRequestTransaction removeCutoffRequestTransaction(
			CutoffRequestTransaction cutoffRequestTransaction) {
		getCutoffRequestTransactions().remove(cutoffRequestTransaction);
		cutoffRequestTransaction.setGroup(null);

		return cutoffRequestTransaction;
	}

}

package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


	/**
	 * @author pramod
	 * @author RSI
	 */


/**
 * The persistent class for the distribution_group database table.
 * 
 */
@Entity
@Table(name = "distribution_group")
@NamedQuery(name = "DistributionGroup.findAll", query = "SELECT d FROM DistributionGroup d")
public class DistributionGroup implements Serializable {

	private static final long serialVersionUID = 1124979443969263108L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "type_measurment")
	private String typeMeasurment;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Company company;

	// bi-directional many-to-one association to Area
	@OneToMany(mappedBy = "distributionGroup")
	private List<Area> areas;

	@Column(length=255)
	private String type;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "distributionGroup")
	private List<Indoorunit> indoorunits;

	public DistributionGroup() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Area addArea(Area area) {
		getAreas().add(area);
		area.setDistributionGroup(this);

		return area;
	}

	public Area removeArea(Area area) {
		getAreas().remove(area);
		area.setDistributionGroup(null);

		return area;
	}

	public List<Indoorunit> getIndoorunits() {
		return this.indoorunits;
	}

	public void setIndoorunits(List<Indoorunit> indoorunits) {
		this.indoorunits = indoorunits;
	}

	public Indoorunit addIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().add(indoorunit);
		indoorunit.setDistribution_group_id(this);

		return indoorunit;
	}

	public Indoorunit removeIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().remove(indoorunit);
		indoorunit.setDistribution_group_id(null);

		return indoorunit;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the typeMeasurment
	 */
	public String getTypeMeasurment() {
		return typeMeasurment;
	}

	/**
	 * @param typeMeasurment the typeMeasurment to set
	 */
	public void setTypeMeasurment(String typeMeasurment) {
		this.typeMeasurment = typeMeasurment;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

}

package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the refrigerantmaster database table.
 * 
 */
@Entity
@Table(name = "refrigerantmaster")
@NamedQuery(name = "Refrigerantmaster.findAll", query = "SELECT r FROM Refrigerantmaster r")
public class Refrigerantmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer refid;

	@Column(nullable = false)
	private Integer refrigerantid;

	@Column(length = 16)
	private String linenumber;

	@Column(length = 128)
	private String refrigerantname;

	@Column(name = "svg_max_latitude1", precision = 17, scale = 14)
	private BigDecimal svgMaxLatitude1;

	@Column(name = "svg_max_latitude2", precision = 17, scale = 14)
	private BigDecimal svgMaxLatitude2;

	@Column(name = "svg_max_latitude3", precision = 17, scale = 14)
	private BigDecimal svgMaxLatitude3;

	@Column(name = "svg_max_longitude1", precision = 17, scale = 14)
	private BigDecimal svgMaxLongitude1;

	@Column(name = "svg_max_longitude2", precision = 17, scale = 14)
	private BigDecimal svgMaxLongitude2;

	@Column(name = "svg_max_longitude3", precision = 17, scale = 14)
	private BigDecimal svgMaxLongitude3;

	@Column(name = "svg_min_latitude1", precision = 17, scale = 14)
	private BigDecimal svgMinLatitude1;

	@Column(name = "svg_min_latitude2", precision = 17, scale = 14)
	private BigDecimal svgMinLatitude2;

	@Column(name = "svg_min_latitude3", precision = 17, scale = 14)
	private BigDecimal svgMinLatitude3;

	@Column(name = "svg_min_longitude1", precision = 17, scale = 14)
	private BigDecimal svgMinLongitude1;

	@Column(name = "svg_min_longitude2", precision = 17, scale = 14)
	private BigDecimal svgMinLongitude2;

	@Column(name = "svg_min_longitude3", precision = 17, scale = 14)
	private BigDecimal svgMinLongitude3;

	// bi-directional many-to-one association to Outdoorunit
	@OneToMany(mappedBy = "refrigerantmaster")
	private List<Outdoorunit> outdoorunits;

	// bi-directional many-to-one association to Adapter
	@ManyToOne
	@JoinColumn(name = "adapterid")
	private Adapter adapter;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "siteid", referencedColumnName = "uniqueid")
	private Group group;

	// bi-directional many-to-one association to SvgMaster
	@ManyToOne
	@JoinColumn(name = "svg_id1")
	private SvgMaster svgMaster1;

	// bi-directional many-to-one association to SvgMaster
	@ManyToOne
	@JoinColumn(name = "svg_id2")
	private SvgMaster svgMaster2;

	// bi-directional many-to-one association to SvgMaster
	@ManyToOne
	@JoinColumn(name = "svg_id3")
	private SvgMaster svgMaster3;

	public Refrigerantmaster() {
	}

	public Integer getRefid() {
		return this.refid;
	}

	public void setRefid(Integer refid) {
		this.refid = refid;
	}

	public Integer getRefrigerantid() {
		return this.refrigerantid;
	}

	public void setRefrigerantid(Integer refrigerantid) {
		this.refrigerantid = refrigerantid;
	}

	public String getLinenumber() {
		return this.linenumber;
	}

	public void setLinenumber(String linenumber) {
		this.linenumber = linenumber;
	}

	public String getRefrigerantname() {
		return this.refrigerantname;
	}

	public void setRefrigerantname(String refrigerantname) {
		this.refrigerantname = refrigerantname;
	}

	public BigDecimal getSvgMaxLatitude1() {
		return this.svgMaxLatitude1;
	}

	public void setSvgMaxLatitude1(BigDecimal svgMaxLatitude1) {
		this.svgMaxLatitude1 = svgMaxLatitude1;
	}

	public BigDecimal getSvgMaxLatitude2() {
		return this.svgMaxLatitude2;
	}

	public void setSvgMaxLatitude2(BigDecimal svgMaxLatitude2) {
		this.svgMaxLatitude2 = svgMaxLatitude2;
	}

	public BigDecimal getSvgMaxLatitude3() {
		return this.svgMaxLatitude3;
	}

	public void setSvgMaxLatitude3(BigDecimal svgMaxLatitude3) {
		this.svgMaxLatitude3 = svgMaxLatitude3;
	}

	public BigDecimal getSvgMaxLongitude1() {
		return this.svgMaxLongitude1;
	}

	public void setSvgMaxLongitude1(BigDecimal svgMaxLongitude1) {
		this.svgMaxLongitude1 = svgMaxLongitude1;
	}

	public BigDecimal getSvgMaxLongitude2() {
		return this.svgMaxLongitude2;
	}

	public void setSvgMaxLongitude2(BigDecimal svgMaxLongitude2) {
		this.svgMaxLongitude2 = svgMaxLongitude2;
	}

	public BigDecimal getSvgMaxLongitude3() {
		return this.svgMaxLongitude3;
	}

	public void setSvgMaxLongitude3(BigDecimal svgMaxLongitude3) {
		this.svgMaxLongitude3 = svgMaxLongitude3;
	}

	public BigDecimal getSvgMinLatitude1() {
		return this.svgMinLatitude1;
	}

	public void setSvgMinLatitude1(BigDecimal svgMinLatitude1) {
		this.svgMinLatitude1 = svgMinLatitude1;
	}

	public BigDecimal getSvgMinLatitude2() {
		return this.svgMinLatitude2;
	}

	public void setSvgMinLatitude2(BigDecimal svgMinLatitude2) {
		this.svgMinLatitude2 = svgMinLatitude2;
	}

	public BigDecimal getSvgMinLatitude3() {
		return this.svgMinLatitude3;
	}

	public void setSvgMinLatitude3(BigDecimal svgMinLatitude3) {
		this.svgMinLatitude3 = svgMinLatitude3;
	}

	public BigDecimal getSvgMinLongitude1() {
		return this.svgMinLongitude1;
	}

	public void setSvgMinLongitude1(BigDecimal svgMinLongitude1) {
		this.svgMinLongitude1 = svgMinLongitude1;
	}

	public BigDecimal getSvgMinLongitude2() {
		return this.svgMinLongitude2;
	}

	public void setSvgMinLongitude2(BigDecimal svgMinLongitude2) {
		this.svgMinLongitude2 = svgMinLongitude2;
	}

	public BigDecimal getSvgMinLongitude3() {
		return this.svgMinLongitude3;
	}

	public void setSvgMinLongitude3(BigDecimal svgMinLongitude3) {
		this.svgMinLongitude3 = svgMinLongitude3;
	}

	public List<Outdoorunit> getOutdoorunits() {
		return this.outdoorunits;
	}

	public void setOutdoorunits(List<Outdoorunit> outdoorunits) {
		this.outdoorunits = outdoorunits;
	}

	public Outdoorunit addOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().add(outdoorunit);
		outdoorunit.setRefrigerantmaster(this);

		return outdoorunit;
	}

	public Outdoorunit removeOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().remove(outdoorunit);
		outdoorunit.setRefrigerantmaster(null);

		return outdoorunit;
	}

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public SvgMaster getSvgMaster1() {
		return this.svgMaster1;
	}

	public void setSvgMaster1(SvgMaster svgMaster1) {
		this.svgMaster1 = svgMaster1;
	}

	public SvgMaster getSvgMaster2() {
		return this.svgMaster2;
	}

	public void setSvgMaster2(SvgMaster svgMaster2) {
		this.svgMaster2 = svgMaster2;
	}

	public SvgMaster getSvgMaster3() {
		return this.svgMaster3;
	}

	public void setSvgMaster3(SvgMaster svgMaster3) {
		this.svgMaster3 = svgMaster3;
	}

}

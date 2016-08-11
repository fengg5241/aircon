package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the svg_master database table.
 * 
 */
@Entity
@Table(name = "svg_master")
@NamedQuery(name = "SvgMaster.findAll", query = "SELECT s FROM SvgMaster s")
public class SvgMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "svg_file_name", length = 255)
	private String svgFileName;

	@Column(name = "svg_name", length = 255)
	private String svgName;

	// bi-directional many-to-one association to Indoorunit
	@OneToMany(mappedBy = "svgMaster")
	private List<Indoorunit> indoorunits;

	// bi-directional many-to-one association to Outdoorunit
	@OneToMany(mappedBy = "svgMaster")
	private List<Outdoorunit> outdoorunits;

	// bi-directional many-to-one association to Refrigerantmaster
	@OneToMany(mappedBy = "svgMaster1")
	private List<Refrigerantmaster> refrigerantmasters1;

	// bi-directional many-to-one association to Refrigerantmaster
	@OneToMany(mappedBy = "svgMaster2")
	private List<Refrigerantmaster> refrigerantmasters2;

	// bi-directional many-to-one association to Refrigerantmaster
	@OneToMany(mappedBy = "svgMaster3")
	private List<Refrigerantmaster> refrigerantmasters3;

	public SvgMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSvgFileName() {
		return this.svgFileName;
	}

	public void setSvgFileName(String svgFileName) {
		this.svgFileName = svgFileName;
	}

	/**
	 * @return the svgName
	 */
	public String getSvgName() {
		return svgName;
	}

	/**
	 * @param svgName
	 *            the svgName to set
	 */
	public void setSvgName(String svgName) {
		this.svgName = svgName;
	}

	public List<Indoorunit> getIndoorunits() {
		return this.indoorunits;
	}

	public void setIndoorunits(List<Indoorunit> indoorunits) {
		this.indoorunits = indoorunits;
	}

	public Indoorunit addIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().add(indoorunit);
		indoorunit.setSvgMaster(this);

		return indoorunit;
	}

	public Indoorunit removeIndoorunit(Indoorunit indoorunit) {
		getIndoorunits().remove(indoorunit);
		indoorunit.setSvgMaster(null);

		return indoorunit;
	}

	public List<Outdoorunit> getOutdoorunits() {
		return this.outdoorunits;
	}

	public void setOutdoorunits(List<Outdoorunit> outdoorunits) {
		this.outdoorunits = outdoorunits;
	}

	public Outdoorunit addOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().add(outdoorunit);
		outdoorunit.setSvgMaster(this);

		return outdoorunit;
	}

	public Outdoorunit removeOutdoorunit(Outdoorunit outdoorunit) {
		getOutdoorunits().remove(outdoorunit);
		outdoorunit.setSvgMaster(null);

		return outdoorunit;
	}

	public List<Refrigerantmaster> getRefrigerantmasters1() {
		return this.refrigerantmasters1;
	}

	public void setRefrigerantmasters1(
			List<Refrigerantmaster> refrigerantmasters1) {
		this.refrigerantmasters1 = refrigerantmasters1;
	}

	public Refrigerantmaster addRefrigerantmasters1(
			Refrigerantmaster refrigerantmasters1) {
		getRefrigerantmasters1().add(refrigerantmasters1);
		refrigerantmasters1.setSvgMaster1(this);

		return refrigerantmasters1;
	}

	public Refrigerantmaster removeRefrigerantmasters1(
			Refrigerantmaster refrigerantmasters1) {
		getRefrigerantmasters1().remove(refrigerantmasters1);
		refrigerantmasters1.setSvgMaster1(null);

		return refrigerantmasters1;
	}

	public List<Refrigerantmaster> getRefrigerantmasters2() {
		return this.refrigerantmasters2;
	}

	public void setRefrigerantmasters2(
			List<Refrigerantmaster> refrigerantmasters2) {
		this.refrigerantmasters2 = refrigerantmasters2;
	}

	public Refrigerantmaster addRefrigerantmasters2(
			Refrigerantmaster refrigerantmasters2) {
		getRefrigerantmasters2().add(refrigerantmasters2);
		refrigerantmasters2.setSvgMaster2(this);

		return refrigerantmasters2;
	}

	public Refrigerantmaster removeRefrigerantmasters2(
			Refrigerantmaster refrigerantmasters2) {
		getRefrigerantmasters2().remove(refrigerantmasters2);
		refrigerantmasters2.setSvgMaster2(null);

		return refrigerantmasters2;
	}

	public List<Refrigerantmaster> getRefrigerantmasters3() {
		return this.refrigerantmasters3;
	}

	public void setRefrigerantmasters3(
			List<Refrigerantmaster> refrigerantmasters3) {
		this.refrigerantmasters3 = refrigerantmasters3;
	}

	public Refrigerantmaster addRefrigerantmasters3(
			Refrigerantmaster refrigerantmasters3) {
		getRefrigerantmasters3().add(refrigerantmasters3);
		refrigerantmasters3.setSvgMaster3(this);

		return refrigerantmasters3;
	}

	public Refrigerantmaster removeRefrigerantmasters3(
			Refrigerantmaster refrigerantmasters3) {
		getRefrigerantmasters3().remove(refrigerantmasters3);
		refrigerantmasters3.setSvgMaster3(null);

		return refrigerantmasters3;
	}

}

package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the availabletemp database table.
 * 
 */
@Entity
@Table(name="availabletemp")
@NamedQuery(name="Availabletemp.findAll", query="SELECT a FROM Availabletemp a")
public class Availabletemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=45)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;

	@Column(precision=10)
	private BigDecimal max;

	@Column(precision=10)
	private BigDecimal min;

	@Column(length=45)
	private String mode;

	private Timestamp updatedate;

	@Column(length=45)
	private String updatedby;

	//bi-directional many-to-one association to Indoorunit
	@ManyToOne
	@JoinColumn(name="indoorunit_id")
	private Indoorunit indoorunit;

	public Availabletemp() {
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

	public BigDecimal getMax() {
		return this.max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return this.min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public String getMode() {
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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
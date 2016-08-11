package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the timeline database table.
 * 
 */
@Entity
@Table(name="timeline")
@NamedQuery(name="Timeline.findAll", query="SELECT t FROM Timeline t")
public class Timeline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=45)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;

	@Column(length=45)
	private String state;

	private Timestamp updatedate;

	@Column(length=45)
	private String updatedby;

	//bi-directional many-to-one association to Adapter
	@ManyToOne
	@JoinColumn(name="adapter_id")
	private Adapter adapter;

	public Timeline() {
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

}
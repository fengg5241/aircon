package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the cutoff_request_transactions database table.
 * 
 */
@Entity
@Table(name = "cutoff_request_transactions")
@NamedQuery(name = "CutoffRequestTransaction.findAll", query = "SELECT c FROM CutoffRequestTransaction c")
public class CutoffRequestTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	// bi-directional many-to-one association to CutoffRequest
	@ManyToOne
	@JoinColumn(name = "ctfreq_id")
	private CutoffRequest cutoffRequest;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "siteid", referencedColumnName = "id")
	private Group group;

	public CutoffRequestTransaction() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public CutoffRequest getCutoffRequest() {
		return this.cutoffRequest;
	}

	public void setCutoffRequest(CutoffRequest cutoffRequest) {
		this.cutoffRequest = cutoffRequest;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}

package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the companiesusers database table.
 * 
 */
@Entity
@Table(name = "companiesusers")
@NamedQuery(name = "Companiesuser.findAll", query = "SELECT c FROM Companiesuser c")
public class Companiesuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name="company_id")
	private Long companyId;

	@Column(name="user_id")
	private Long userId;

	@Column(name="group_id")
	private Long groupId;

	@Column(length = 45)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	private Timestamp updatedate;

	@Column(length = 45)
	private String updatedby;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false, insertable = false, updatable = false)
	private Company company;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
	private Group group;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;

	public Companiesuser() {
	}

	
	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getCompanyId() {
		return companyId;
	}




	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}




	public Long getUserId() {
		return userId;
	}




	public void setUserId(Long userId) {
		this.userId = userId;
	}




	public Long getGroupId() {
		return groupId;
	}




	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

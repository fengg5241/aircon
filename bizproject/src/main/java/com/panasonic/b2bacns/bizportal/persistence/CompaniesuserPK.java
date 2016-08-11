package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the companiesusers database table.
 * 
 */
@Embeddable
public class CompaniesuserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="company_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Long companyId;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Long userId;

	@Column(name="group_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Long groupId;

	public CompaniesuserPK() {
	}
	public Long getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUserId() {
		return this.userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getGroupId() {
		return this.groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CompaniesuserPK)) {
			return false;
		}
		CompaniesuserPK castOther = (CompaniesuserPK)other;
		return 
			this.companyId.equals(castOther.companyId)
			&& this.userId.equals(castOther.userId)
			&& this.groupId.equals(castOther.groupId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.groupId.hashCode();
		
		return hash;
	}
}
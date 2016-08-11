package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the distribution_ratio_data database table.
 * 
 */
@Embeddable
public class DistributionRatioDataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="cutoffreq_id", insertable=false, updatable=false)
	private Long cutoffreqId;

	@Column(name="device_oid")
	private String deviceOid;

	public DistributionRatioDataPK(Long cutoffreqId, String deviceOid) {
		this.cutoffreqId = cutoffreqId;
		this.deviceOid = deviceOid;
	}
	public Long getCutoffreqId() {
		return this.cutoffreqId;
	}
	public void setCutoffreqId(Long cutoffreqId) {
		this.cutoffreqId = cutoffreqId;
	}
	public String getDeviceOid() {
		return this.deviceOid;
	}
	public void setDeviceOid(String deviceOid) {
		this.deviceOid = deviceOid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DistributionRatioDataPK)) {
			return false;
		}
		DistributionRatioDataPK castOther = (DistributionRatioDataPK)other;
		return 
			this.cutoffreqId.equals(castOther.cutoffreqId)
			&& this.deviceOid.equals(castOther.deviceOid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cutoffreqId.hashCode();
		hash = hash * prime + this.deviceOid.hashCode();
		
		return hash;
	}
}
/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.vo;

/**
 * @author simanchal.patra
 *
 */
public class IdPowerDetailVo {

	private long pfTranscID;

	private Long iduID;

	private Long pmID;

	public IdPowerDetailVo(long pfTranscID, Long iduID, Long pmID) {
		this.pfTranscID = pfTranscID;
		this.iduID = iduID;
		this.pmID = pmID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iduID == null) ? 0 : iduID.hashCode());
		result = prime * result + (int) (pfTranscID ^ (pfTranscID >>> 32));
		result = prime * result + ((pmID == null) ? 0 : pmID.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IdPowerDetailVo other = (IdPowerDetailVo) obj;
		if (iduID == null) {
			if (other.iduID != null) {
				return false;
			}
		} else if (!iduID.equals(other.iduID)) {
			return false;
		}
		if (pfTranscID != other.pfTranscID) {
			return false;
		}
		if (pmID == null) {
			if (other.pmID != null) {
				return false;
			}
		} else if (!pmID.equals(other.pmID)) {
			return false;
		}
		return true;
	}

}

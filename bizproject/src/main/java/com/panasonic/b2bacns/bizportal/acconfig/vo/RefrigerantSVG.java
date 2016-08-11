package com.panasonic.b2bacns.bizportal.acconfig.vo;

public class RefrigerantSVG {

	private Long id;
	private Double minlatitude;
	private Double maxlatitude;
	private Double minlongitude;
	private Double maxlongitude;
	private Long linkedSvgId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the minlatitude
	 */
	public Double getMinlatitude() {
		return minlatitude;
	}

	/**
	 * @param minlatitude
	 *            the minlatitude to set
	 */
	public void setMinlatitude(Double minlatitude) {
		this.minlatitude = minlatitude;
	}

	/**
	 * @return the maxlatitude
	 */
	public Double getMaxlatitude() {
		return maxlatitude;
	}

	/**
	 * @param maxlatitude
	 *            the maxlatitude to set
	 */
	public void setMaxlatitude(Double maxlatitude) {
		this.maxlatitude = maxlatitude;
	}

	/**
	 * @return the minlongitude
	 */
	public Double getMinlongitude() {
		return minlongitude;
	}

	/**
	 * @param minlongitude
	 *            the minlongitude to set
	 */
	public void setMinlongitude(Double minlongitude) {
		this.minlongitude = minlongitude;
	}

	/**
	 * @return the maxlongitude
	 */
	public Double getMaxlongitude() {
		return maxlongitude;
	}

	/**
	 * @param maxlongitude
	 *            the maxlongitude to set
	 */
	public void setMaxlongitude(Double maxlongitude) {
		this.maxlongitude = maxlongitude;
	}

	/**
	 * @return the linkedSvgId
	 */
	public Long getLinkedSvgId() {
		return linkedSvgId;
	}

	/**
	 * @param linkedSvgId
	 *            the linkedSvgId to set
	 */
	public void setLinkedSvgId(Long linkedSvgId) {
		this.linkedSvgId = linkedSvgId;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((linkedSvgId == null) ? 0 : linkedSvgId.hashCode());
		result = prime * result
				+ ((maxlatitude == null) ? 0 : maxlatitude.hashCode());
		result = prime * result
				+ ((maxlongitude == null) ? 0 : maxlongitude.hashCode());
		result = prime * result
				+ ((minlatitude == null) ? 0 : minlatitude.hashCode());
		result = prime * result
				+ ((minlongitude == null) ? 0 : minlongitude.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefrigerantSVG other = (RefrigerantSVG) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linkedSvgId == null) {
			if (other.linkedSvgId != null)
				return false;
		} else if (!linkedSvgId.equals(other.linkedSvgId))
			return false;
		if (maxlatitude == null) {
			if (other.maxlatitude != null)
				return false;
		} else if (!maxlatitude.equals(other.maxlatitude))
			return false;
		if (maxlongitude == null) {
			if (other.maxlongitude != null)
				return false;
		} else if (!maxlongitude.equals(other.maxlongitude))
			return false;
		if (minlatitude == null) {
			if (other.minlatitude != null)
				return false;
		} else if (!minlatitude.equals(other.minlatitude))
			return false;
		if (minlongitude == null) {
			if (other.minlongitude != null)
				return false;
		} else if (!minlongitude.equals(other.minlongitude))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefrigerantSVG [id=" + id + ", minlatitude=" + minlatitude
				+ ", maxlatitude=" + maxlatitude + ", minlongitude="
				+ minlongitude + ", maxlongitude=" + maxlongitude
				+ ", linkedSvgId=" + linkedSvgId + "]";
	}

}

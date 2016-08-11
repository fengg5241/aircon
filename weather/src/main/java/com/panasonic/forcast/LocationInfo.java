package com.panasonic.forcast;

public class LocationInfo {
	
	public LocationInfo(long locationId, Double mapLatitude, Double mapLongitude) {
		super();
		this.locationId = locationId;
		this.mapLatitude = mapLatitude;
		this.mapLongitude = mapLongitude;
	}

	private long locationId;
	
	private Double mapLatitude;
	
	private Double mapLongitude;

	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the mapLatitude
	 */
	public Double getMapLatitude() {
		return mapLatitude;
	}

	/**
	 * @param mapLatitude the mapLatitude to set
	 */
	public void setMapLatitude(Double mapLatitude) {
		this.mapLatitude = mapLatitude;
	}

	/**
	 * @return the mapLongitude
	 */
	public Double getMapLongitude() {
		return mapLongitude;
	}

	/**
	 * @param mapLongitude the mapLongitude to set
	 */
	public void setMapLongitude(Double mapLongitude) {
		this.mapLongitude = mapLongitude;
	}
}

/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

/**
 * @author akansha
 * 
 */
public class StatsReportVO implements Comparable<StatsReportVO> {

	private String levelName;
	private String timeline;
	private Integer index;
	private Integer dataDuration;
	private String customerName;
	private String entityName;

	// POWER CONSUMPTION
	private Double power;

	// CAPACITY
	private Double rated;
	private Double current;
	private Double totalCapacity;
	private Double outdoorTemp;

	// EFFICIENCY
	private Double efficiency;
	private Double settingTemp;
	private Double rmTemp;

	// DIFF TEMP
	private Double roomTemp;
	private Double differentialTemp;

	// WORK HOUR
	private String thermostatOff;
	private String thermostatOn;
	private Double lowOff;
	private Double mediumOff;
	private Double highOff;
	private Double lowOn;
	private Double mediumOn;
	private Double highOn;
	private Double totalHoursOff;
	private Double totalHoursOn;
	private Double compressor1;
	private Double compressor2;
	private Double compressor3;
	private Double engine;

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param levelName
	 *            the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return the timeline
	 */
	public String getTimeline() {
		return timeline;
	}

	/**
	 * @param timeline
	 *            the timeline to set
	 */
	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the dataDuration
	 */
	public Integer getDataDuration() {
		return dataDuration;
	}

	/**
	 * @param dataDuration
	 *            the dataDuration to set
	 */
	public void setDataDuration(Integer dataDuration) {
		this.dataDuration = dataDuration;
	}

	/**
	 * @return the power
	 */
	public Double getPower() {
		return power;
	}

	/**
	 * @param power
	 *            the power to set
	 */
	public void setPower(Double power) {
		this.power = power;
	}

	/**
	 * @return the rated
	 */
	public Double getRated() {
		return rated;
	}

	/**
	 * @param rated
	 *            the rated to set
	 */
	public void setRated(Double rated) {
		this.rated = rated;
	}

	/**
	 * @return the current
	 */
	public Double getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(Double current) {
		this.current = current;
	}

	/**
	 * @return the totalCapacity
	 */
	public Double getTotalCapacity() {
		return totalCapacity;
	}

	/**
	 * @param totalCapacity
	 *            the totalCapacity to set
	 */
	public void setTotalCapacity(Double totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	/**
	 * @return the efficiency
	 */
	public Double getEfficiency() {
		return efficiency;
	}

	/**
	 * @param efficiency
	 *            the efficiency to set
	 */
	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the settingTemp
	 */
	public Double getSettingTemp() {
		return settingTemp;
	}

	/**
	 * @param settingTemp
	 *            the settingTemp to set
	 */
	public void setSettingTemp(Double settingTemp) {
		this.settingTemp = settingTemp;
	}

	/**
	 * @return the rmTemp
	 */
	public Double getRmTemp() {
		return rmTemp;
	}

	/**
	 * @param rmTemp
	 *            the rmTemp to set
	 */
	public void setRmTemp(Double rmTemp) {
		this.rmTemp = rmTemp;
	}

	/**
	 * @return the outdoorTemp
	 */
	public Double getOutdoorTemp() {
		return outdoorTemp;
	}

	/**
	 * @param outdoorTemp
	 *            the outdoorTemp to set
	 */
	public void setOutdoorTemp(Double outdoorTemp) {
		this.outdoorTemp = outdoorTemp;
	}

	/**
	 * @return the roomTemp
	 */
	public Double getRoomTemp() {
		return roomTemp;
	}

	/**
	 * @param roomTemp
	 *            the roomTemp to set
	 */
	public void setRoomTemp(Double roomTemp) {
		this.roomTemp = roomTemp;
	}

	/**
	 * @return the differentialTemp
	 */
	public Double getDifferentialTemp() {
		return differentialTemp;
	}

	/**
	 * @param differentialTemp
	 *            the differentialTemp to set
	 */
	public void setDifferentialTemp(Double differentialTemp) {
		this.differentialTemp = differentialTemp;
	}

	/**
	 * @return the thermostatOff
	 */
	public String getThermostatOff() {
		return thermostatOff;
	}

	/**
	 * @param thermostatOff
	 *            the thermostatOff to set
	 */
	public void setThermostatOff(String thermostatOff) {
		this.thermostatOff = thermostatOff;
	}

	/**
	 * @return the thermostatOn
	 */
	public String getThermostatOn() {
		return thermostatOn;
	}

	/**
	 * @param thermostatOn
	 *            the thermostatOn to set
	 */
	public void setThermostatOn(String thermostatOn) {
		this.thermostatOn = thermostatOn;
	}

	/**
	 * @return the lowOff
	 */
	public Double getLowOff() {
		return lowOff;
	}

	/**
	 * @param lowOff
	 *            the lowOff to set
	 */
	public void setLowOff(Double lowOff) {
		this.lowOff = lowOff;
	}

	/**
	 * @return the mediumOff
	 */
	public Double getMediumOff() {
		return mediumOff;
	}

	/**
	 * @param mediumOff
	 *            the mediumOff to set
	 */
	public void setMediumOff(Double mediumOff) {
		this.mediumOff = mediumOff;
	}

	/**
	 * @return the highOff
	 */
	public Double getHighOff() {
		return highOff;
	}

	/**
	 * @param highOff
	 *            the highOff to set
	 */
	public void setHighOff(Double highOff) {
		this.highOff = highOff;
	}

	/**
	 * @return the lowOn
	 */
	public Double getLowOn() {
		return lowOn;
	}

	/**
	 * @param lowOn
	 *            the lowOn to set
	 */
	public void setLowOn(Double lowOn) {
		this.lowOn = lowOn;
	}

	/**
	 * @return the mediumOn
	 */
	public Double getMediumOn() {
		return mediumOn;
	}

	/**
	 * @param mediumOn
	 *            the mediumOn to set
	 */
	public void setMediumOn(Double mediumOn) {
		this.mediumOn = mediumOn;
	}

	/**
	 * @return the highOn
	 */
	public Double getHighOn() {
		return highOn;
	}

	/**
	 * @param highOn
	 *            the highOn to set
	 */
	public void setHighOn(Double highOn) {
		this.highOn = highOn;
	}

	/**
	 * @return the totalHoursOff
	 */
	public Double getTotalHoursOff() {
		return totalHoursOff;
	}

	/**
	 * @param totalHoursOff
	 *            the totalHoursOff to set
	 */
	public void setTotalHoursOff(Double totalHoursOff) {
		this.totalHoursOff = totalHoursOff;
	}

	/**
	 * @return the totalHoursOn
	 */
	public Double getTotalHoursOn() {
		return totalHoursOn;
	}

	/**
	 * @param totalHoursOn
	 *            the totalHoursOn to set
	 */
	public void setTotalHoursOn(Double totalHoursOn) {
		this.totalHoursOn = totalHoursOn;
	}

	/**
	 * @return the compressor1
	 */
	public Double getCompressor1() {
		return compressor1;
	}

	/**
	 * @param compressor1
	 *            the compressor1 to set
	 */
	public void setCompressor1(Double compressor1) {
		this.compressor1 = compressor1;
	}

	/**
	 * @return the compressor2
	 */
	public Double getCompressor2() {
		return compressor2;
	}

	/**
	 * @param compressor2
	 *            the compressor2 to set
	 */
	public void setCompressor2(Double compressor2) {
		this.compressor2 = compressor2;
	}

	/**
	 * @return the compressor3
	 */
	public Double getCompressor3() {
		return compressor3;
	}

	/**
	 * @param compressor3
	 *            the compressor3 to set
	 */
	public void setCompressor3(Double compressor3) {
		this.compressor3 = compressor3;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getEngine() {
		return engine;
	}

	public void setEngine(Double engine) {
		this.engine = engine;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StatsReportVO o) {

		int i = levelName.compareTo(o.levelName);
		if (i != 0)
			return i;

		// this.getLevelName().compareTo(o.getLevelName());

		return this.getIndex().compareTo(o.getIndex());
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
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result
				+ ((levelName == null) ? 0 : levelName.hashCode());

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
		StatsReportVO other = (StatsReportVO) obj;
		if (!index.equals(other.index))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;

		return true;
	}

}

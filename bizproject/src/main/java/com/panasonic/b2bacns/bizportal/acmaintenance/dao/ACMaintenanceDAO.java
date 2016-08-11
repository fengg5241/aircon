package com.panasonic.b2bacns.bizportal.acmaintenance.dao;

import java.util.List;

import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceSettingVO;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceUserVO;

public interface ACMaintenanceDAO {

	/**
	 * Fetch records from Database for the AC Maintenance Setting for a site.
	 * 
	 * @param siteID
	 * @return
	 */
	public List<ACMaintenanceSettingVO> getMaintenanceSetting(Long siteID);

	/**
	 * Fetch records from Database for the AC Maintenance User List for
	 * requested Setting type.
	 * 
	 * @param maintenanceTypeID
	 * @return
	 */
	public List<ACMaintenanceUserVO> getMaintenanceSettingMailList(
			Long maintenanceTypeID);

	/**
	 * Fetch records from Database for the AC Maintenance Status Data for
	 * requested Outdoor.
	 * 
	 * @param outdoorID
	 * @return
	 */
	public List<ACMaintenanceSettingVO> getCurrentRemainingMaintenanceTime(
			Long outdoorID);

	/**
	 * Reset the Threshold Alert from Maintenance Status Data.
	 * 
	 * @param oduID
	 * @param maintID
	 * @param timezone
	 * @return
	 */
	public boolean resetThresholdAlert(Long oduID, Long maintID, String timezone);
}

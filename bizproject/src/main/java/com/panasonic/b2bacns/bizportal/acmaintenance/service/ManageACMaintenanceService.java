/**
 * 
 */
package com.panasonic.b2bacns.bizportal.acmaintenance.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceRequest;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceSettingVO;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceUserVO;

/**
 * @author amitesh.arya
 * 
 */
public interface ManageACMaintenanceService {

	/**
	 * Provides the AC Maintenance Setting Details for a Site.
	 * 
	 * @param acMaintenanceRequest
	 * @return AC Maintenance Setting Details for the requested Site.
	 */
	public List<ACMaintenanceSettingVO> getMaintenanceSetting(
			ACMaintenanceRequest acMaintenanceRequest);

	/**
	 * Update the AC Maintenance Setting Details for a Site.
	 * 
	 * @param acMaintenanceRequest
	 * @return Status:true/false
	 */
	public boolean setMaintenanceSetting(
			ACMaintenanceRequest acMaintenanceRequest);

	/**
	 * Provides the AC Maintenance User Details for a Site.
	 * 
	 * @param acMaintenanceRequest
	 * @return AC Maintenance User Details for the requested Maintenance Setting
	 *         Type.
	 */
	public List<ACMaintenanceUserVO> getMaintenanceSettingMailList(
			ACMaintenanceRequest acMaintenanceRequest);

	/**
	 * Update the AC Maintenance User Email Details for a Setting Type.
	 * 
	 * @param acMaintenanceRequest
	 * @return Status:true/false
	 */
	public boolean setMaintenanceSettingMailList(
			ACMaintenanceRequest acMaintenanceRequest);

	/**
	 * Provides the AC Maintenance Status Data for a Outdoor.
	 * 
	 * @param acMaintenanceRequest
	 * @return AC Maintenance Status Data for the requested Outdoor.
	 */
	public List<ACMaintenanceSettingVO> getCurrentRemainingMaintenanceTime(
			ACMaintenanceRequest acMaintenanceRequest);

	/**
	 * Reset the Threshold Alert from Maintenance Status Data.
	 * 
	 * @param acMaintenanceRequest
	 * @param timeZone
	 * @return
	 */
	public boolean resetThresholdAlert(
			ACMaintenanceRequest acMaintenanceRequest, String timeZone);
}

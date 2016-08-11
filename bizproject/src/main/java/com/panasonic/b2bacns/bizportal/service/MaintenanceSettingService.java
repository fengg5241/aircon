package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.persistence.MaintenanceSetting;

public interface MaintenanceSettingService {

	/**
	 * Update records from Database for the AC Maintenance Setting for a site.
	 * 
	 * @param acMaintenanceList
	 * @return
	 */
	public boolean setMaintenanceSetting(
			List<MaintenanceSetting> acMaintenanceList);
}

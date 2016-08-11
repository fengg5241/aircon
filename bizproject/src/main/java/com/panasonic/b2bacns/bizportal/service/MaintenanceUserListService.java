package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.persistence.MaintenanceUserList;

public interface MaintenanceUserListService {

	/**
	 * Add User Email in MaintenanceUserList table.
	 * 
	 * @param acMaintenanceList
	 * @return
	 */
	boolean setMaintenanceUserList(List<MaintenanceUserList> acMaintenanceList);

	/**
	 * Delete User Email in MaintenanceUserList table.
	 * 
	 * @param acMaintenanceList
	 * @return
	 */
	boolean deleteMaintenanceUserList(
			List<MaintenanceUserList> acMaintenanceList);
}

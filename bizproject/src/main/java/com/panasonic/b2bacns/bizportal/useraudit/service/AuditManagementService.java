package com.panasonic.b2bacns.bizportal.useraudit.service;

import com.panasonic.b2bacns.bizportal.persistence.User;

/**
 * This service Interface is intended to provide all the functionalities to log
 * the User action in DB.
 * 
 * @author shobhit.singh
 * 
 */
public interface AuditManagementService {

	/**
	 * Logs the user action in DB for a provided Email ID of the User.
	 * 
	 * @param loginID
	 *            Login ID of the User
	 * @param auditAction
	 *            The audit action to log
	 */
	public void storeUserActivity(String loginID, String auditAction);

	/**
	 * Logs the user action in DB for a provided User.
	 * 
	 * @param user
	 *            The {@link User}
	 * @param auditAction
	 *            The audit action to log
	 */
	public void storeUserActivity(User user, String auditAction);
}

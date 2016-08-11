package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Session;

/**
 * This service Interface is intended to provide all functionalities related to
 * User Session.
 * 
 * @author simanchal.patra
 * @author kumar.madhukar
 *
 */
public interface SessionService {

	/**
	 * This provides all the active sessions for the provided User.
	 * 
	 * @param userLoginID
	 *            Login ID of the User
	 * @return List of {@link Session}
	 */
	public List<Session> getActiveSessions(String userLoginID);

	/**
	 * This deletes the active session from DB for the provided User.
	 * 
	 * @param userLoginID
	 *            Login ID of the User
	 */
	public void deleteSessions(String userLoginID);

	/**
	 * This saves the active session ID of an User in to DB.
	 * 
	 * @param userLoginID
	 *            Login ID of the User
	 * @param sessionId
	 *            ID of the active session
	 */
	public void saveSessions(String userLoginID, String sessionId);

	/**
	 * This provides all the sessions for the provided User.
	 * 
	 * @param criteria
	 *            Criteria to filter out the Sessions
	 * 
	 * @return List of {@link Session}, which matches the Criteria
	 */
	public List<Session> getSessions(Map<String, Object> criteria)
			throws HibernateException;

}

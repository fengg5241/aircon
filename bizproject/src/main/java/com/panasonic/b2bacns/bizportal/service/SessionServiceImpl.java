package com.panasonic.b2bacns.bizportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Session;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * This service class provides all functionalities related to User Session.
 * 
 * @author simanchal.patra
 * @author kumar.madhukar
 *
 */
@Service
public class SessionServiceImpl implements SessionService {

	//private static final String PROPERTY_EMAIL = "email";

	private static final String PROPERTY_STATUS = "status";

	private static final String PROPERTY_LOGIN_ID = "loginid";

	private GenericDAO<Session> dao;

	@Autowired
	public void setDao(GenericDAO<Session> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Session.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.SessionService#getActiveSessions
	 * (java.lang.String)
	 */
	@Override
	@Transactional
	public List<Session> getActiveSessions(String userLoginID) {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		properties.put(PROPERTY_STATUS, BizConstants.USER_STATUS_ACTIVE);
		return getSessions(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.SessionService#deleteSessions
	 * (java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteSessions(String userLoginID) {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		properties.put(PROPERTY_STATUS, BizConstants.USER_STATUS_ACTIVE);
		List<Session> sessionList = getSessions(properties);
		if (sessionList.size() > 0) {
			for (Session session : sessionList) {
				session.setStatus(BizConstants.USER_STATUS_INACTIVE);
				session.setUpdatedate(new java.sql.Timestamp(System
						.currentTimeMillis()));
				dao.update(session);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.SessionService#saveSessions(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void saveSessions(String userLoginID, String sessionId) {
		Session entity = new Session();
		// entity.setEmail(userEmailID);
		entity.setLoginid(userLoginID);
		entity.setStatus(BizConstants.USER_STATUS_ACTIVE);
		entity.setUniquesessionid(sessionId);
		entity.setCreationdate(new java.sql.Timestamp(System
				.currentTimeMillis()));
		dao.create(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.SessionService#getSessions(java
	 * .util.Map)
	 */
	@Override
	public List<Session> getSessions(Map<String, Object> properties)
			throws HibernateException {

		return dao.findAllByProperties(properties);
	}

}

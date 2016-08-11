package com.panasonic.b2bacns.bizportal.useraudit.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.persistence.Useraudit;
import com.panasonic.b2bacns.bizportal.service.UserAuditService;
import com.panasonic.b2bacns.bizportal.service.UserService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * This service class implements the {@link AuditManagementService} to provide
 * all the functionalities to log the User action in DB
 * 
 * @author shobhit.singh
 * 
 */
@Service
public class AuditManagementServiceImpl implements AuditManagementService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserAuditService userAuditService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.useraudit.service.AuditManagementService
	 * #storeUserActivity(java.lang.String, java.lang.String)
	 */
	@Override
	public void storeUserActivity(String loginID, String auditAction) {

		if (loginID != null && !BizConstants.EMPTY_STRING.equals(loginID)) {
			User user = userService.getUser(loginID);
			Useraudit userAudit = new Useraudit();
			userAudit.setUser(user);
			userAudit.setAuditaction(auditAction);
			userAudit.setCreationdate(new Timestamp(new Date().getTime()));
			userAuditService.createUserAudit(userAudit);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.useraudit.service.AuditManagementService
	 * #storeUserActivity(com.panasonic.b2bacns.bizportal.persistence.User,
	 * java.lang.String)
	 */
	@Override
	public void storeUserActivity(User user, String auditAction) {

		Useraudit userAudit = new Useraudit();
		userAudit.setUser(user);
		userAudit.setAuditaction(auditAction);
		userAudit.setCreationdate(new Timestamp(new Date().getTime()));
		userAuditService.createUserAudit(userAudit);
	}

}

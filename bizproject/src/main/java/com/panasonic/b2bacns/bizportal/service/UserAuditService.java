package com.panasonic.b2bacns.bizportal.service;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Useraudit;

public interface UserAuditService {

	/**
	 * This method is used to create a new UserAudit and inserts in the
	 * UserAudit table.
	 * 
	 * @param UserAudit
	 * @return
	 */
	public boolean createUserAudit(Useraudit userAudit)
			throws HibernateException;

}

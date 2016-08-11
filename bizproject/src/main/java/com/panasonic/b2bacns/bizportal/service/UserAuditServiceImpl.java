package com.panasonic.b2bacns.bizportal.service;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Useraudit;

@Service
public class UserAuditServiceImpl implements UserAuditService {

	private GenericDAO<Useraudit> userAuditDao;

	@Autowired
	public void setDao(GenericDAO<Useraudit> daoToSet) {
		userAuditDao = daoToSet;
		userAuditDao.setClazz(Useraudit.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserAuditService#createUserAudit
	 * (com.panasonic.b2bacns.bizportal.persistence.Useraudit)
	 */
	@Override
	@Transactional
	public boolean createUserAudit(Useraudit userAudit)
			throws HibernateException {
		userAuditDao.create(userAudit);

		return true;
	}

}

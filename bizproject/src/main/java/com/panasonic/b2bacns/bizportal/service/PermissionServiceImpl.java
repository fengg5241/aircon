package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.email.dao.Email;
import com.panasonic.b2bacns.bizportal.persistence.Permission;

/**
 * This is the implementation for the Permission service.
 * 
 * 
 * 
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	private GenericDAO<Permission> dao;

	@Autowired
	private SQLDAO sqlDao;

	@Autowired
	public void setDao(GenericDAO<Permission> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Permission.class);
	}

	@Autowired
	private Email emailDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#getPermissionList()
	 */
	@Override
	@Transactional
	public List<Permission> getPermissionList() {
		return dao.findAll();
	}

}

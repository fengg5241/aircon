package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Permission;

/**
 * This service Interface is intended to provide all functionalities related to
 * {@link Permission}
 * 
 */
public interface PermissionService {

	/**
	 * This provides all the Permissions in DB.
	 * 
	 * @return List of {@link Permission}
	 */
	public List<Permission> getPermissionList() throws HibernateException;

}

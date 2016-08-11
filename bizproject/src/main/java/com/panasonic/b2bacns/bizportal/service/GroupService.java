package com.panasonic.b2bacns.bizportal.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.persistence.Group;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
public interface GroupService {

	/**
	 * This method is used to get list of groups as per criteria given in map of
	 * properties
	 * 
	 * @param properties
	 * @return
	 * @throws HibernateException
	 */
	public List<Group> getGroupListByProperties(Map<String, Object> properties)
			throws HibernateException;

	/**
	 * This method is used to get group by given id
	 * 
	 * @param groupID
	 * @return
	 * @throws HibernateException
	 */
	public Group findByID(long groupID) throws HibernateException;

	/**
	 * This method is used to get groups company wise for which parentid is null
	 * 
	 * @param companyID
	 * @return
	 * @throws HibernateException
	 */
	public List<Group> getParentGroupsByCompanyID(long companyID)
			throws HibernateException;

	/**
	 * this methos used to save and update the batch of groups
	 * 
	 * @param groups
	 * @return
	 */
	public void saveOrUpdateGroup(List<Group> groups);

	/**
	 * Get all the groups having multiple group ids.
	 * 
	 * @param groupIds
	 * @return
	 * @throws HibernateException
	 */
	public List<Group> getGroupsByGroupIds(List<Long> groupIds)
			throws HibernateException;

}

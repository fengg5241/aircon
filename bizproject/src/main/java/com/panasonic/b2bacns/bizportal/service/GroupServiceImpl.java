package com.panasonic.b2bacns.bizportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
@Service
public class GroupServiceImpl implements GroupService {

	private static final StringBuilder GET_GROUPS_BY_GROUP_IDS = new StringBuilder(
			"from Group as gr where gr.id in (:groupIds)");
	private GenericDAO<Group> dao;
	@Autowired
	public void setDao(GenericDAO<Group> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Group.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.GroupService#getGroupListByProperties
	 * (java.util.Map)
	 */
	@Override
	public List<Group> getGroupListByProperties(Map<String, Object> properties)
			throws HibernateException {
		return dao.findAllByProperties(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.GroupService#findByID(int)
	 */
	@Override
	public Group findByID(long groupID) throws HibernateException {
		return dao.findByID(groupID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.GroupService#
	 * getParentGroupsByCompanyID (int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getParentGroupsByCompanyID(long companyID)
			throws HibernateException {
		String sql = " from Group as gr where gr.group is null and gr.company.id="
				+ companyID;
		return dao.executeHQLQuery(sql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.GroupService#getGroupsByGroupIds
	 * (java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getGroupsByGroupIds(List<Long> groupIds)
			throws HibernateException {
		String sqlQuery = String.format(GET_GROUPS_BY_GROUP_IDS.toString());
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put(BizConstants.GROUP_IDS, groupIds);
		return (List<Group>) dao.executeHQLSelect(sqlQuery,queryMap);
	}

	@Override
	public void saveOrUpdateGroup(List<Group> groups) {
		dao.batchSaveOrUpdate(groups);
	}
}

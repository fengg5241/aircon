package com.panasonic.b2bacns.bizportal.group.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.group.controller.GroupsByCompany;
import com.panasonic.b2bacns.bizportal.group.vo.GroupLeftMenuVO;
import com.panasonic.b2bacns.bizportal.group.vo.IDUListVO;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
public interface ManageGroupService {

	/**
	 * This method is used to get all the groups by user id for left menu.
	 * 
	 * @param userId
	 * @param userRole
	 * @param companyId
	 * @return
	 * @throws HibernateException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BusinessFailureException
	 */
	public List<GroupLeftMenuVO> getParentGroupsByUserId(Long userId,
			Long roleTypeID, String roleTypeName, Long companyId)
			throws HibernateException, IllegalAccessException,
			InvocationTargetException, BusinessFailureException;

	/**
	 * This method is used to get all the group details by company id for left
	 * menu.
	 * 
	 * @param sessionInfo
	 * @return
	 * @throws HibernateException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BusinessFailureException
	 */
	public List<GroupsByCompany> getParentGroupsByCompanyId(
			SessionInfo sessionInfo) throws HibernateException,
			IllegalAccessException, InvocationTargetException,
			BusinessFailureException;

	/**
	 * To get list af all IDUs in group(s)
	 * 
	 * @param groupIds
	 * @return
	 */
	public List<IDUListVO> getIDUList(List<Long> groupIds);

}

package com.panasonic.b2bacns.bizportal.group.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.IDUInfo;
import com.panasonic.b2bacns.bizportal.dashboard.vo.ODUInfo;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.group.controller.GroupsByCompany;
import com.panasonic.b2bacns.bizportal.group.vo.GroupLeftMenuLiAttrVO;
import com.panasonic.b2bacns.bizportal.group.vo.GroupLeftMenuStateVO;
import com.panasonic.b2bacns.bizportal.group.vo.GroupLeftMenuVO;
import com.panasonic.b2bacns.bizportal.group.vo.IDUListVO;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.service.CompanyService;
import com.panasonic.b2bacns.bizportal.service.CompanyUserService;
import com.panasonic.b2bacns.bizportal.service.GroupService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.GroupNameComparator;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
@Service
public class ManageGroupServiceImpl implements ManageGroupService {

	private static Logger logger = Logger
			.getLogger(ManageGroupServiceImpl.class);

	private static final String HQL_ALL_GROUP_ALL_COMPANY = "select distinct grp from Group as grp left join fetch grp.groups where cast(grp.isUnitExists as integer) = 1  order by  grp.company";

	private static final String HQL_ALL_GROUP_BY_USER = "select distinct grp from Group as grp left join fetch grp.groups where grp.id in (%s) and cast(grp.isUnitExists as integer) = 1 ";

	@Autowired
	private CompanyUserService companyUserService;

	private GenericDAO<Group> dao;

	@Autowired
	public void setDao(GenericDAO<Group> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Group.class);
	}

	@Autowired
	private SQLDAO sqlDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.group.service.ManageGroupService#
	 * getParentGroupsByUserId(long, java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GroupLeftMenuVO> getParentGroupsByUserId(Long userId,
			Long roleTypeID, String roleTypeName, Long companyId)
			throws HibernateException, IllegalAccessException,
			InvocationTargetException, BusinessFailureException {

		Map<String, Object> properties = new HashMap<>();

		List<GroupLeftMenuVO> groupVoList = null;

		List<Group> groupList = new ArrayList<Group>();

		if (roleTypeName != null) {

			if (StringUtils.equalsIgnoreCase(roleTypeName,
					BizConstants.ROLE_TYPE_PANASONIC)) {

				// fetches all groups with their children as eager collection in
				// the case of User Roles Admin/Super Admin
				Query query = dao.executeHQLQuery(HQL_ALL_GROUP_ALL_COMPANY);
				groupList = (List<Group>) query.list();

				groupVoList = getGroupsDetailsWithSubGroups(groupList,
						roleTypeName);

			} else {

				if (userId != null) {
					User user = new User();
					user.setId(userId);
					properties.put("user", user);
				}

				if (companyId != null) {
					Company company = new Company();
					company.setId(companyId);
					properties.put("company", company);
				}

				List<Companiesuser> companiesUsers = companyUserService
						.findAllByProperties(properties);

				List<Long> groupIds = new ArrayList<Long>();

				for (Companiesuser companiesUser : companiesUsers) {
					if (companiesUser.getGroup() != null) {
						groupIds.add(companiesUser.getGroup().getId());
					}
				}

				if (!groupIds.isEmpty()) {

					String groupIdsSQL = CommonUtil
							.convertCollectionToString(groupIds);

					// fetches all groups with their children as eager
					// collection in the case of User Role Customer
					String hqlQueryCustomer = String.format(
							HQL_ALL_GROUP_BY_USER, groupIdsSQL);

					Query query = dao.executeHQLQuery(hqlQueryCustomer);
					groupList = (List<Group>) query.list();
				}

				groupVoList = getGroupsDetailsWithSubGroups(groupList,
						roleTypeName);

			}

			if (groupVoList != null && groupVoList.size() == 0) {
				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				throw new BusinessFailureException(customErrorMessage);
			}

		}

		return groupVoList;

	}

	/**
	 * populate GroupVO from Group
	 * 
	 * @param vo
	 * @return
	 */
	private GroupLeftMenuVO populateGroupLeftMenuVO(Group vo) {
        GroupLeftMenuVO groupVO = new GroupLeftMenuVO();
        groupVO.setGroupId(vo.getId());
        groupVO.setGroupName(vo.getName());
        groupVO.setCompanyId(vo.getCompany().getId());
        groupVO.setCompanyName(vo.getCompany().getName());
        groupVO.setSvgPath(vo.getSvgPath());
        groupVO.setGroupCriteria(vo.getGroupscriteria().getId());
        groupVO.setGroupCategory(vo.getGroupcategory().getGroupcategoryname());
        groupVO.setGroupTypeLevelID(vo.getGroupLevel().getId());
        groupVO.setGroupTypeLevelName(vo.getGroupLevel().getTypeLevelName());
        
        //added by Shanf start
        groupVO.setText(vo.getName());
        GroupLeftMenuLiAttrVO liVO = new GroupLeftMenuLiAttrVO();
        liVO.setDataId(vo.getId());
        liVO.setSvgPath(vo.getSvgPath());
        groupVO.setLi_attr(liVO);
        
        GroupLeftMenuStateVO stateVO = new GroupLeftMenuStateVO();
        groupVO.setState(stateVO);
        //added by Shanf end
        return groupVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.group.service.ManageGroupService#
	 * getParentGroupsByCompanyId
	 * (com.panasonic.b2bacns.bizportal.common.SessionInfo)
	 */
	@Override
	public List<GroupsByCompany> getParentGroupsByCompanyId(
			SessionInfo sessionInfo) throws HibernateException,
			IllegalAccessException, InvocationTargetException,
			BusinessFailureException {

		Long roleTypeID = null;
		String roleTypeName = null;

		for (Entry<Integer, String> roleTypeDetails : sessionInfo
				.getRoleTypeMap().entrySet()) {
			roleTypeID = Long.valueOf(roleTypeDetails.getKey());
			roleTypeName = roleTypeDetails.getValue();
			break;
		}

		List<GroupLeftMenuVO> groupVoList = getParentGroupsByUserId(
				sessionInfo.getUserId(), roleTypeID, roleTypeName,
				sessionInfo.getCompanyId());

		Map<Long, List<GroupLeftMenuVO>> groupVoMapCompanyWise = new HashMap<Long, List<GroupLeftMenuVO>>();

		List<GroupsByCompany> groupTreeByCompany = new ArrayList<>();

		Map<Long, String> companyInfo = new HashMap<>();

		for (GroupLeftMenuVO vo1 : groupVoList) {

			List<GroupLeftMenuVO> list = new ArrayList<GroupLeftMenuVO>();

			int innerCount = 0;

			if (!groupVoMapCompanyWise.containsKey(vo1.getCompanyId())) {

				for (GroupLeftMenuVO vo2 : groupVoList) {

					if (vo1.getCompanyId() == vo2.getCompanyId()) {

						if (innerCount == 0) {
							list.add(vo2);

							if (!companyInfo.containsKey(vo1.getCompanyId())) {
								companyInfo.put(vo1.getCompanyId(),
										vo1.getCompanyName());
							}
							groupVoMapCompanyWise.put(vo1.getCompanyId(), list);
						} else {
							if (!companyInfo.containsKey(vo1.getCompanyId())) {
								companyInfo.put(vo1.getCompanyId(),
										vo1.getCompanyName());
							}
							groupVoMapCompanyWise.get(vo1.getCompanyId()).add(
									vo2);
						}

						innerCount++;

					}

				}

			}

		}

		GroupsByCompany groupsByCompany = null;

		for (long companyID : companyInfo.keySet()) {
			groupsByCompany = new GroupsByCompany();
			groupsByCompany.setCompanyID(companyID);
			groupsByCompany.setText(companyInfo.get(companyID));
			groupsByCompany.setChildren(groupVoMapCompanyWise.get(companyID));
			groupTreeByCompany.add(groupsByCompany);
		}

		return groupTreeByCompany;
	}

	/**
	 * Get all subgroups of each parent group in the list up to nth level ( in
	 * database restriction should be up to maximum eight levels
	 * 
	 * @param groupList
	 * @return
	 * @throws HibernateException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private List<GroupLeftMenuVO> getGroupsDetailsWithSubGroups(
			List<Group> groupList, String userRole) throws HibernateException,
			IllegalAccessException, InvocationTargetException,
			BusinessFailureException {

		List<GroupLeftMenuVO> groupVoList = new ArrayList<GroupLeftMenuVO>();

		for (Group vo : groupList) {

			GroupLeftMenuVO voSub = populateGroupLeftMenuVO(vo);

			if (StringUtils.equalsIgnoreCase(userRole,
					BizConstants.ROLE_TYPE_PANASONIC)) {
				if (vo.getGroup() == null) {

					groupVoList.add(getAllSubGroups(vo, voSub));
				}
			} else {
				groupVoList.add(getAllSubGroups(vo, voSub));
			}

		}

		return groupVoList;
	}

	/**
	 * Recursive method to get all subgroups of a group and populate
	 * GroupLeftMenuVo
	 * 
	 * @param vo
	 * @param parent
	 * @return
	 */
	private GroupLeftMenuVO getAllSubGroups(Group vo, GroupLeftMenuVO parent) {

		List<GroupLeftMenuVO> sub = new ArrayList<GroupLeftMenuVO>();

		for (Group subGroup : vo.getGroups()) {

			if (subGroup.getIsUnitExists() == 1) {

				GroupLeftMenuVO subgrp = populateGroupLeftMenuVO(subGroup);

				sub.add(subgrp);

				getAllSubGroups(subGroup, subgrp);
			}

		}

		if (!sub.isEmpty()) {

			Collections.sort(sub, new GroupNameComparator());

			parent.setChildren(sub);
		}

		return parent;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.group.service.ManageGroupService#getIDUList
	 * (java.util.List)
	 */
	@Override
	public List<IDUListVO> getIDUList(List<Long> groupIds) {

		List<IDUListVO> IDUList = sqlDao.getIDUList(groupIds);

		if (IDUList != null && IDUList.size() > 0) {

			return IDUList;

		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			throw new GenericFailureException(customErrorMessage);
		}

	}

}

package com.panasonic.b2bacns.bizportal.map.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.map.dao.MapDAO;
import com.panasonic.b2bacns.bizportal.map.vo.CompanyMapVO;
import com.panasonic.b2bacns.bizportal.map.vo.GroupVO;
import com.panasonic.b2bacns.bizportal.map.vo.IndoorOutdoorVO;
import com.panasonic.b2bacns.bizportal.map.vo.ParentGroupVO;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.service.GroupService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
@Service
public class ManageMapServiceImpl implements ManageMapService {

	private static Logger logger = Logger.getLogger(ManageMapServiceImpl.class);

	@Autowired
	GroupService groupService;

	@Autowired
	private MapDAO mapDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.map.service.ManageMapService#getMapData
	 * (long)
	 */
	@Override
	@Transactional
	public String getMapData(String id, String idType)
			throws HibernateException, IllegalAccessException,
			InvocationTargetException, BusinessFailureException,
			JsonProcessingException {

		String JsonStr = BizConstants.EMPTY_STRING;

		switch (idType) {

		case BizConstants.ID_TYPE_COMPANY:
			logger.debug("Calling Map for Company");
			List<ParentGroupVO> parentGroupVO = getParentsByCompany(Long
					.valueOf(id));

			if (parentGroupVO == null) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				throw new GenericFailureException(customErrorMessage);
			}

			CompanyMapVO companyMapVO = new CompanyMapVO();

			companyMapVO.setCompanyId(Long.valueOf(id));

			companyMapVO.setChildren(parentGroupVO);

			JsonStr = CommonUtil.convertFromEntityToJsonStr(companyMapVO);

			break;

		case BizConstants.ID_TYPE_GROUP:
			logger.debug("Calling Map for Group");
			List<GroupVO> groupList = new ArrayList<GroupVO>();
			String[] idArr = id.split(BizConstants.COMMA_STRING);
			List<Long> idString = new ArrayList<Long>();
			for (String ids : idArr) {

				idString.add(Long.parseLong(ids));
			}
			List<Group> groups = groupService.getGroupsByGroupIds(idString);
			if (groups == null || groups.isEmpty()) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				throw new GenericFailureException(customErrorMessage);
			} else
				for (Group group : groups) {

					GroupVO grpVO = new GroupVO();

					List<GroupVO> siblingGroupVO = null;

					List<GroupVO> childrenGroups = null;

					if (group.getGroupscriteria().getIsSibling() == 1) {

						siblingGroupVO = getSiblings(group);

					}

					if (group.getGroupscriteria().getIsChild() == 1) {

						childrenGroups = getChildren(group);

					}

					if (group.getGroupscriteria().getIsUnit() == 1) {

						GroupVO grp = mapDao.getUnitsForGroup(id);

						if (grp != null) {

							grpVO.setIduList(grp.getIduList());
							grpVO.setOduList(grp.getOduList());
						}

					}

					setGroupAttributes(grpVO, group);

					grpVO.setChildren(childrenGroups);

					grpVO.setSiblings(siblingGroupVO);
					groupList.add(grpVO);

				}
			JsonStr = CommonUtil.convertFromEntityToJsonStr(groupList);

			break;

		case BizConstants.ID_TYPE_INDOOR:
			logger.debug("Calling Map for Indoor");
			idArr = id.split(BizConstants.COMMA_STRING);
			idString = new ArrayList<Long>();
			for (String ids : idArr) {

				idString.add(Long.parseLong(ids));
			}
			IndoorOutdoorVO unitVO = mapDao.getIduMap(idString);

			if (unitVO == null) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				throw new GenericFailureException(customErrorMessage);
			}

			JsonStr = CommonUtil.convertFromEntityToJsonStr(unitVO);

			break;

		}

		return JsonStr;

	}

	/**
	 * Get children groups of the given group as parameter
	 * 
	 * @param group
	 * @return
	 */
	private List<GroupVO> getChildren(Group group) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("level", group.getLevel() + 1);
		properties.put("group", group);
		List<Group> groups = groupService.getGroupListByProperties(properties);

		if (groups.size() > 0) {

			List<GroupVO> chilrenlistGroupVO = new ArrayList<GroupVO>();

			for (Group grp : groups) {

				if (!grp.getId().equals(group.getId())) {
					GroupVO vo = new GroupVO();
					setGroupAttributes(vo, grp);
					chilrenlistGroupVO.add(vo);
				}

			}

			return chilrenlistGroupVO;

		}

		return null;

	}

	/**
	 * Get sibling groups of given group as parameter
	 * 
	 * @param group
	 * @return
	 */
	private List<GroupVO> getSiblings(Group group) {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("level", group.getLevel());
		properties.put("group", group.getGroup());
		List<Group> groups = groupService.getGroupListByProperties(properties);

		if (groups.size() > 0) {

			List<GroupVO> siblinglistGroupVO = new ArrayList<GroupVO>();

			for (Group grp : groups) {

				if (!grp.getId().equals(group.getId())) {
					GroupVO vo = new GroupVO();
					setGroupAttributes(vo, grp);
					siblinglistGroupVO.add(vo);
				}

			}

			return siblinglistGroupVO;
		}

		return null;

	}

	/**
	 * Get parent groups of given company as parameter
	 * 
	 * @param group
	 * @return
	 */
	private List<ParentGroupVO> getParentsByCompany(long companyId) {

		HashMap<String, Object> properties = new HashMap<>();
		Company company = new Company();
		company.setId(companyId);
		properties.put("company", company);
		properties.put("level", 1);
		List<Group> groups = groupService.getGroupListByProperties(properties);

		if (groups.size() > 0) {

			List<ParentGroupVO> parentlistGroupVO = new ArrayList<ParentGroupVO>();

			for (Group grp : groups) {

				ParentGroupVO vo = new ParentGroupVO();
				setParentGroupAttributes(vo, grp);
				parentlistGroupVO.add(vo);

			}

			return parentlistGroupVO;
		}

		return null;

	}

	/**
	 * populates groupVo from Group object
	 * 
	 * @param groupVO
	 * @param group
	 */
	private void setGroupAttributes(GroupVO groupVO, Group group) {
		groupVO.setGroupId(group.getId());
		groupVO.setSvg(group.getSvgPath());
		groupVO.setSvgMinLatitude(group.getSvgMinLatitude() != null ? group
				.getSvgMinLatitude().doubleValue() : null);
		groupVO.setSvgMinLongitude(group.getSvgMinLongitude() != null ? group
				.getSvgMinLongitude().doubleValue() : null);
		groupVO.setSvgMaxLatitude(group.getSvgMaxLatitude() != null ? group
				.getSvgMaxLatitude().doubleValue() : null);
		groupVO.setSvgMaxLongitude(group.getSvgMaxLongitude() != null ? group
				.getSvgMaxLongitude().doubleValue() : null);
	}

	/**
	 * populates parentGroupVo from Group object
	 * 
	 * @param groupVO
	 * @param group
	 */
	private void setParentGroupAttributes(ParentGroupVO parentGroupVO,
			Group group) {

		parentGroupVO.setGroupId(group.getId());

		if (group.getMapLatitude() != null) {
			parentGroupVO.setMapLatitude(group.getMapLatitude().doubleValue());
		}

		if (group.getMapLongitude() != null) {

			parentGroupVO
					.setMapLongitude(group.getMapLongitude().doubleValue());

		}

	}

}

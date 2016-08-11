/**
 * 
 */
package com.panasonic.b2bacns.bizportal.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.GroupLevelVO;
import com.panasonic.b2bacns.bizportal.persistence.GroupLevel;

/**
 * @author akansha
 * 
 */
@Service
public class GroupLevelServiceImpl implements GroupLevelService {

	private GenericDAO<GroupLevel> dao;

	private List<GroupLevelVO> cachedGroupLevel = new ArrayList<GroupLevelVO>();

	@Autowired
	public void setDao(GenericDAO<GroupLevel> daoToSet) {
		dao = daoToSet;
		dao.setClazz(GroupLevel.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.GroupLevelService#getLevel()
	 */
	@Override
	public List<GroupLevelVO> getLevel() throws IllegalAccessException,
			InvocationTargetException {

		if (cachedGroupLevel.size() == 0) {

			List<GroupLevel> groupLevelList = dao.findAll();

			GroupLevelVO groupLevelVO = null;

			for (GroupLevel groupLevel : groupLevelList) {

				groupLevelVO = new GroupLevelVO();
				groupLevelVO.setId(groupLevel.getId());
				groupLevelVO.setName(groupLevel.getTypeLevelName());

				cachedGroupLevel.add(groupLevelVO);

			}

		}

		return cachedGroupLevel;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.GroupLevelService#getLevelNameById
	 * (java.lang.Integer)
	 */
	@Override
	public GroupLevelVO getLevelNameById(Integer groupLevelId)
			throws IllegalAccessException, InvocationTargetException {

		if (cachedGroupLevel.size() == 0) {
			getLevel();
		}

		return cachedGroupLevel.get(groupLevelId - 1);
	}

}

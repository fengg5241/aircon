/**
 * 
 */
package com.panasonic.b2bacns.bizportal.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.panasonic.b2bacns.bizportal.dashboard.vo.GroupLevelVO;

/**
 * @author akansha
 *
 */
public interface GroupLevelService {

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<GroupLevelVO> getLevel() throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 
	 * @param groupLevelId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public GroupLevelVO getLevelNameById(Integer groupLevelId)
			throws IllegalAccessException, InvocationTargetException;

}

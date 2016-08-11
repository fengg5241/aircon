package com.panasonic.b2bacns.bizportal.map.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.map.vo.GroupVO;
import com.panasonic.b2bacns.bizportal.map.vo.IndoorOutdoorVO;

public interface MapDAO {

	/**
	 * Get SVG Map data for group.
	 * 
	 * @param groupId
	 * @return
	 * @throws HibernateException
	 */
	public GroupVO getUnitsForGroup(String groupId) throws HibernateException;

	/**
	 * Get SVG Map data for indoor.
	 * 
	 * @param iduId
	 * @return
	 * @throws HibernateException
	 */
	public IndoorOutdoorVO getIduMap(List<Long> iduId)
			throws HibernateException;
}

package com.panasonic.b2bacns.bizportal.notification.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationCountVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailList;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailsVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO;
import com.panasonic.b2bacns.bizportal.notification.vo.AlarmTypeVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewVO;

public interface NotificationDAO {

	/**
	 * Get all the alarms having in the group. indoorUnit, outdoorunit.
	 * 
	 * @param request
	 * @return
	 */
	public NotificationDetailList getNotificationDetails(
			NotificationRequestVO request);

	/**
	 * Get alarms counts for indoor/outdoor units and groups.
	 * 
	 * @param userId
	 * @return
	 * @throws HibernateException
	 */
	public NotificationCountVO getNotificationCount(Long userId)
			throws HibernateException;

	/**
	 * Get NotificationOverView data for groupId and selected params
	 * 
	 * @param groupID
	 *            , startDate, endDate, alarmType , period , grouplevel
	 * @return
	 * @throws HibernateException
	 * @throws ParseException
	 */
	public Map<String, Object> getNotificationOverViewDataForGroups(
			List<Long> groupID, String startDate, String endDate,
			String alarmType, String period, int grouplevel, String timeZone)
			throws HibernateException, ParseException;

	/**
	 * @param alarmType
	 * @return
	 * @throws HibernateException
	 */
	public List<AlarmTypeVO> getAlarmTypeList() throws HibernateException;

	/**
	 * Get NotificationOverView data for groupId and selected params
	 * 
	 * @param groupID
	 *            , startDate, endDate, alarmType , period , grouplevel
	 * @return
	 * @throws HibernateException
	 * @throws ParseException
	 */
	public List<NotificationOverViewVO> getNotificationOverViewDataForDownload(
			List<Long> groupID, String startDate, String endDate,
			String alarmType, String period, int grouplevel, String timeZone)
			throws HibernateException, ParseException;

	public List<NotificationDetailsVO> getNotificationDetailsDownloadData(
			List<Long> notificationIds);

}

package com.panasonic.b2bacns.bizportal.notification.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailList;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailsVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewRequest;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationRequestDownloadVO;

/**
 * This interface contains all Notification Over View Ranking related methods
 * 
 * @author verma.ashish
 * 
 */
public interface ManageNotificationService {

	/**
	 * This method provides the AlarmTypes list for Notification Over view
	 * 
	 * 
	 * @return
	 * @throws HibernateException
	 * @throws JsonProcessingException
	 */
	public String getAlarmTypes() throws JsonProcessingException,
			HibernateException;

	/**
	 * This method provides the Notification Over View data
	 * 
	 * @param notificationOverViewRequest
	 * @return
	 */
	public String getAlarmNotificationOverView(
			NotificationOverViewRequest notificationOverViewRequest)
			throws JsonProcessingException, ParseException;

	/**
	 * Get Alarms count for a given user Id to get alarms for indoor / outdoor
	 * units and groups the result is then processed and converted to JSON
	 * string
	 * 
	 * @param userId
	 * @return
	 * @throws JsonProcessingException
	 */
	String getNotificationCount(Long userId) throws JsonProcessingException;

	/**
	 * Get the alarm data which includes alarm id, status, severity, error code,
	 * description, counter Measure, unit it, unit type.
	 * 
	 * @param request
	 * @param userTimeZone
	 * @return
	 */
	public NotificationDetailList getNotificationDetails(
			NotificationRequestVO request, String userTimeZone);

	/**
	 * This method provides the Notification Over View data for download
	 * 
	 * @param notificationOverViewRequest
	 * @return
	 * @throws ParseException
	 * @throws HibernateException
	 * @throws JsonProcessingException
	 */
	public List<NotificationOverViewVO> getAlarmNotificationOverViewForDownload(
			NotificationOverViewRequest notificationOverViewRequest)
			throws HibernateException, ParseException, JsonProcessingException;

	/**
	 * Generate excel report for Notification Overview
	 * 
	 * @param notificationList
	 * @param notificationOverViewRequest
	 * @return
	 * @throws Exception
	 */
	public String generateNotificationOverViewExcelReport(
			List<NotificationOverViewVO> notificationList,
			NotificationOverViewRequest notificationOverViewRequest)
			throws Exception;

	/**
	 * Generate excel report for Notification Details
	 * 
	 * @param notificationList
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	public String generateNotificationDetailsExcelReport(
			Set<NotificationDetailsVO> notificationList,
			NotificationRequestVO requestVO) throws Exception;

	/**
	 * Generate csv report for Notification Details
	 * 
	 * @param notificationList
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	public String generateNotificationDetailsCsvReport(
			Set<NotificationDetailsVO> notificationList,
			NotificationRequestVO requestVO) throws Exception;

	public List<NotificationDetailsVO> getNotificationDetailsDownloadData(
			List<Long> notificationIds);

	public String generateNotificationDetailsExcelReport(
			List<NotificationDetailsVO> notificationList,
			NotificationRequestDownloadVO notificationRequestVO)
			throws Exception;

	public String generateNotificationDetailsCsvReport(
			List<NotificationDetailsVO> notificationList,
			NotificationRequestDownloadVO notificationRequestVO)
			throws Exception;

}

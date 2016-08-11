/**
 * 
 */
package com.panasonic.b2bacns.bizportal.notification.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.PermissionVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO;
import com.panasonic.b2bacns.bizportal.notification.vo.AdvanceNotificationSettingInJsonVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationCategorySettingVOs;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationRequestDownloadVO;
import com.panasonic.b2bacns.bizportal.notification.vo.UpdateAdvanceNotificationSettingVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Narendra.Kumar
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class NotificationControllerTest {
	private static final Logger logger = Logger
			.getLogger(NotificationControllerTest.class);
	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testMock() throws Exception {
		assertNotNull("Mock MVC cannot be initialized", mockMvc);
	}

	@Test
	public void testNotificationController_1() throws Exception {
		assertNotNull("Controller cannot be initialized");
	}

	private MockHttpServletRequestBuilder get(String uri) {
		return MockMvcRequestBuilders.get(uri);
	}

	private MockHttpServletRequestBuilder post(String uri) {
		return MockMvcRequestBuilders.post(uri);
	}

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		List<PermissionVO> permissionList = new ArrayList<PermissionVO>();
		PermissionVO permission = new PermissionVO();
		permission
				.setPermissionName(BizConstants.NOTIFICATION_MESSAGE_MAINTENANCE);
		permissionList.add(permission);
		sessionInfo.setPermissionsList(permissionList);
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(1l);
		sessionInfo.setCompanyId(1l);
		sessionInfo.setUserTimeZone("Asia/Singapore");
		CommonUtil.setSessionInfo(session, sessionInfo);

		return session;
	}

	@Test
	public void testViewNotificationSetting() throws Exception {

		String getNotificationSettingJson = "";
		getNotificationSettingJson = mockMvc
				.perform(
						get("/notification/getNotificationSetting.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.GROUP_ID, "3"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getNotificationSettingJson - "
				+ getNotificationSettingJson);
		logger.debug("getNotificationSettingJson - "
				+ getNotificationSettingJson);

	}

	@Test
	@Rollback(false)
	public void testSetNotificationSetting() throws Exception {
		NotificationCategorySettingVOs notificationCategorySettingVOs = new NotificationCategorySettingVOs();
		List<NotificationCategorySettingVO> notifiationList = new ArrayList<NotificationCategorySettingVO>();
		NotificationCategorySettingVO notification = new NotificationCategorySettingVO();
		notification.setNotificationCategoryId(1);
		notification.setNotification(BizConstants.off);
		notification.setGroupID(3l);

		notifiationList.add(notification);

		NotificationCategorySettingVO notification1 = new NotificationCategorySettingVO();
		notification1.setNotificationCategoryId(3);
		notification1.setGroupID(3l);
		notification1.setNotification(BizConstants.on);

		notifiationList.add(notification1);
		notificationCategorySettingVOs
				.setNotificationCategorySettingVOs(notifiationList);
		String setNotificationSettingJsonStringParam = CommonUtil
				.convertFromEntityToJsonStr(notifiationList);
		String setNotificationSetting = "";
		setNotificationSetting = mockMvc
				.perform(
						post("/notification/setNotificationSetting.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.SET_NOTIFICATION_SETTING,
										setNotificationSettingJsonStringParam)

				).andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("setNotificationSetting - " + setNotificationSetting);

	}

	@Test
	@Rollback(false)
	public void testUpdateAdvanceNotificationSetting() throws Exception {

		UpdateAdvanceNotificationSettingVO userNotificationSettingsVO = new UpdateAdvanceNotificationSettingVO();
		userNotificationSettingsVO.setNotificationCategoryId(1);
		userNotificationSettingsVO.setGroupID(6l);
		List<Long> addUserList = new ArrayList<Long>();
//		addUserList.add(1l);
//		addUserList.add(2l);
//		addUserList.add(3l);
//		addUserList.add(4l);
//		addUserList.add(5l);
//		addUserList.add(6l);
//		addUserList.add(7l);
		addUserList.add(14l);
//		addUserList.add(9l);
//		addUserList.add(10l);
		userNotificationSettingsVO.setAddusers(addUserList);
		List<Long> deleteUserList = new ArrayList<Long>();
//		deleteUserList.add(3l);
//		deleteUserList.add(4l);
//		deleteUserList.add(5l);
		userNotificationSettingsVO.setDeleteusers(deleteUserList);
		String updateAdvanceNotificationSettingJsonStringParam = CommonUtil
				.convertFromEntityToJsonStr(userNotificationSettingsVO);
		String updateAdvanceNotificationSettingJson = "";
		updateAdvanceNotificationSettingJson = mockMvc
				.perform(
						post(
								"/notification/updateAdvanceNotificationSetting.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.UPDATE_ADVANCE_NOTIFICATION_SETTING,
										updateAdvanceNotificationSettingJsonStringParam)

				).andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("updateAdvanceNotificationSetting - "
				+ updateAdvanceNotificationSettingJson);

	}

	@Test
	public void testViewAdvanceNotificationSetting() throws Exception {
		AdvanceNotificationSettingInJsonVO advanceNotificationSettingInJsonVO = new AdvanceNotificationSettingInJsonVO();
		advanceNotificationSettingInJsonVO.setGroupID(3l);
		advanceNotificationSettingInJsonVO.setNotificationCategoryId(3);
		String getAdvanceNotificationSettingJsonStringParam = CommonUtil
				.convertFromEntityToJsonStr(advanceNotificationSettingInJsonVO);
		String getAdvanceNotificationSettingJson = "";
		getAdvanceNotificationSettingJson = mockMvc
				.perform(
						get("/notification/getAdvanceNotificationSetting.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.GET_ADVANCE_NOTIFICATION_SETTING,
										getAdvanceNotificationSettingJsonStringParam))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getAdvanceNotificationSettingJson - "
				+ getAdvanceNotificationSettingJson);
		logger.debug("getAdvanceNotificationSettingJson - "
				+ getAdvanceNotificationSettingJson);

	}

	@Test
	public void testGetUserList() throws Exception {
		String json_request = "{companyId:1}";
		String getUserListJson = "";
		getUserListJson = mockMvc
				.perform(
						get("/notification/getUserList.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.param("json_request", json_request)
								.session(getSession())

				).andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getUserListJson - " + getUserListJson);
		logger.debug("getUserListJson - " + getUserListJson);

	}

	@Test
	@Rollback(false)
	public void testupdateMasterNotificationSetting() throws Exception {

		UpdateAdvanceNotificationSettingVO userNotificationSettingsVO = new UpdateAdvanceNotificationSettingVO();
		userNotificationSettingsVO.setNotificationCategoryId(4);
		userNotificationSettingsVO.setGroupID(13l);
		List<Long> addUserList = new ArrayList<Long>();
		addUserList.add(1l);
		addUserList.add(2l);
		addUserList.add(3l);
		addUserList.add(4l);
		addUserList.add(5l);
		addUserList.add(6l);
		addUserList.add(7l);
		addUserList.add(8l);
		addUserList.add(9l);
		addUserList.add(10l);
		userNotificationSettingsVO.setAddusers(addUserList);
		List<Long> deleteUserList = new ArrayList<Long>();
		deleteUserList.add(3l);
		deleteUserList.add(4l);
		deleteUserList.add(4l);
		deleteUserList.add(5l);
		deleteUserList.add(6l);
		deleteUserList.add(7l);
		deleteUserList.add(8l);

		userNotificationSettingsVO.setDeleteusers(deleteUserList);

		String updateAdvanceNotificationSettingJsonStringParam = CommonUtil
				.convertFromEntityToJsonStr(userNotificationSettingsVO);
		String updateAdvanceNotificationSettingJson = "";
		updateAdvanceNotificationSettingJson = mockMvc
				.perform(
						post(
								"/notification/updateMasterNotificationSetting.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.UPDATE_ADVANCE_NOTIFICATION_SETTING,
										updateAdvanceNotificationSettingJsonStringParam)

				).andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("updateAdvanceNotificationSetting - "
				+ updateAdvanceNotificationSettingJson);

	}

	@Test
	public void testViewMasterNotificationSetting() throws Exception {
		AdvanceNotificationSettingInJsonVO advanceNotificationSettingInJsonVO = new AdvanceNotificationSettingInJsonVO();
		advanceNotificationSettingInJsonVO.setGroupID(13l);

		String getMasterNotificationSettingJsonStringParam = CommonUtil
				.convertFromEntityToJsonStr(advanceNotificationSettingInJsonVO);
		String getMasterNotificationSettingJson = "";
		getMasterNotificationSettingJson = mockMvc
				.perform(
						get("/notification/getMasterNotificationSetting.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.GET_MASTER_NOTIFICATION_SETTING,
										getMasterNotificationSettingJsonStringParam))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getMasterNotificationSettingJson - "
				+ getMasterNotificationSettingJson);
		logger.debug("getMasterNotificationSettingJson - "
				+ getMasterNotificationSettingJson);

	}

	@Test
	public void testNotificationOverView() throws Exception {
		Locale locale = Locale.ENGLISH;

		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': '2015-05-06','endDate':'2015-11-06' ,'alarmType': '','period':null,'grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'thisweek','grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'thismonth','grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'thisyear','grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':null,'grouplevel': 12}";
		String json_request = "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'today','grouplevel': 12}";

		String getNotificationOverViewData = mockMvc
				.perform(
						get("/notification/getNotificationOverView.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getNotificationOverViewData --> "
				+ getNotificationOverViewData);
	}

	@Test
	public void testgetAlarmType() throws Exception {
		Locale locale = Locale.ENGLISH;

		String getAlramType = mockMvc
				.perform(
						get("/notification/getAlarmType.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("idType", BizConstants.ID_TYPE_AlARMTYPE))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getAlramType --> " + getAlramType);

	}

	@Test
	public void testGetNotificationDetailsGroup() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupId = new ArrayList<Long>();
		groupId.add(1l);
		// groupId.add(34l);
		List<String> severity = new ArrayList<String>();
		severity.add("noncriTical");
		severity.add("critical");
		List<String> status = new ArrayList<String>();
		// status.add("on-hold");
		status.add("new");
		// status.add("fixed");
		NotificationRequestVO request = new NotificationRequestVO();
		request.setId(groupId);
		request.setIdType(BizConstants.ID_TYPE_GROUP);
		request.setAlarmOccurredStartDate("01-01-2013");
		request.setAlarmOccurredEndDate("01-02-2016");
		request.setAlarmOccurredStartDate("01-01-2013");
		request.setAlarmOccurredEndDate("10-03-2016");
		request.setAddCustName("yes");
		
		// request.setSeverity(severity);
		// request.setStatus(status);
		// request.setAlarmType("alert");
		// request.setAlarmFixedStartDate("13-10-2015");
		// request.setAlarmFixedEndDate("13-10-2015");
		String json_request = CommonUtil.convertFromEntityToJsonStr(request);
		// Group and status 'new'
		String alarmViewGroup = mockMvc
				.perform(
						get("/notification/getNotificationDetails.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testGetNotificationDetails Group - "
				+ alarmViewGroup);
	}

	@Test
	public void testGetNotificationDetailsIndoor() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupId = new ArrayList<Long>();
		groupId.add(26l);
		groupId.add(25l);
		groupId.add(27l);
		groupId.add(214l);
		List<String> severity = new ArrayList<String>();
		severity.add("noncriTical");
		severity.add("critical");
		List<String> status = new ArrayList<String>();
		status.add("on-hold");
		status.add("new");
		status.add("fixed");
		NotificationRequestVO request = new NotificationRequestVO();
		request.setId(groupId);
		request.setIdType(BizConstants.ID_TYPE_INDOOR);
		request.setAlarmOccurredStartDate("01-05-2013");
		request.setAlarmOccurredEndDate("01-02-2016");
		// request.setSeverity(severity);
		// request.setStatus(status);
		// request.setAlarmType("all");
		// request.setAlarmFixedStartDate("13-10-2015");
		// request.setAlarmFixedEndDate("13-10-2015");
		String json_request = CommonUtil.convertFromEntityToJsonStr(request);
		// Group and status 'new'
		String alarmViewGroup = mockMvc
				.perform(
						get("/notification/getNotificationDetails.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testGetNotification Details Indoor - "
				+ alarmViewGroup);
	}

	@Test
	public void testGetNotificationDetailsOutdoor() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupId = new ArrayList<Long>();
		groupId.add(15l);
		groupId.add(16l);
		List<String> severity = new ArrayList<String>();
		severity.add("noncriTical");
		severity.add("critical");
		List<String> status = new ArrayList<String>();
		status.add("on-hold");
		status.add("new");
		status.add("fixed");
		NotificationRequestVO request = new NotificationRequestVO();
		request.setId(groupId);
		request.setIdType(BizConstants.ID_TYPE_OUTDOOR);
		request.setAlarmOccurredStartDate("01-05-2013");
		request.setAlarmOccurredEndDate("23-10-2015");
		// request.setSeverity(severity);
		// request.setStatus(status);
		// request.setAlarmType("all");
		// request.setAlarmFixedStartDate("13-10-2015");
		// request.setAlarmFixedEndDate("13-10-2015");
		String json_request = CommonUtil.convertFromEntityToJsonStr(request);
		// Group and status 'new'
		String alarmViewGroup = mockMvc
				.perform(
						get("/notification/getNotificationDetails.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testGetNotification Details Outdoor - "
				+ alarmViewGroup);
	}

	@Test
	public void testGetNotificationDetailsCA() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupId = new ArrayList<Long>();
		groupId.add(3l);
		// groupId.add(16l);
		List<String> severity = new ArrayList<String>();
		severity.add("noncriTical");
		severity.add("critical");
		List<String> status = new ArrayList<String>();
		status.add("on-hold");
		status.add("new");
		status.add("fixed");
		NotificationRequestVO request = new NotificationRequestVO();
		request.setId(groupId);
		request.setIdType(BizConstants.ID_TYPE_CA);
		request.setAlarmOccurredStartDate("01-05-2013");
		request.setAlarmOccurredEndDate("23-12-2015");
		// request.setSeverity(severity);
		// request.setStatus(status);
		// request.setAlarmType("all");
		// request.setAlarmFixedStartDate("13-10-2015");
		// request.setAlarmFixedEndDate("13-10-2015");
		String json_request = CommonUtil.convertFromEntityToJsonStr(request);
		// Group and status 'new'
		String alarmViewGroup = mockMvc
				.perform(
						get("/notification/getNotificationDetails.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testGetNotification Details CA - " + alarmViewGroup);
	}

	@Test
	public void testGetTotalAlarmsCount() throws Exception {

		Locale locale = Locale.ENGLISH;

		String alarmCount = mockMvc
				.perform(
						get("/notification/getNotificationCount.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		if (alarmCount.equals("no.records.found"))
			Assert.fail("No Record Found");
		System.out.println("testGetTotalAlarmsCount - " + alarmCount);

	}

	@Test
	public void testDownloadNotificationDetails() throws Exception {

		Locale locale = Locale.ENGLISH;
		// List<Long> groupId = new ArrayList<Long>();
		// groupId.add(1l);
		// groupId.add(8l);
		// List<String> severity = new ArrayList<String>();
		// severity.add("noncriTical");
		// severity.add("critical");
		// List<String> status = new ArrayList<String>();
		// // status.add("on-hold");
		// status.add("new");
		// // status.add("fixed");
		// NotificationRequestVO request = new NotificationRequestVO();
		// request.setId(groupId);
		// request.setIdType(BizConstants.ID_TYPE_GROUP);
		// request.setAlarmOccurredStartDate("10-09-2015");
		// request.setAlarmOccurredEndDate("10-11-2015");
		// request.setSeverity(severity);
		// request.setStatus(status);
		// request.setAlarmType("all");
		// request.setAlarmFixedStartDate("13-10-2015");
		// request.setAlarmFixedEndDate("13-10-2015");

		NotificationRequestDownloadVO request = new NotificationRequestDownloadVO();

		List<Long> notificationIds = new ArrayList<Long>();
		notificationIds.add(942l);
		notificationIds.add(940l);
		notificationIds.add(938l);
		notificationIds.add(936l);
		notificationIds.add(935l);
		notificationIds.add(934l);
		notificationIds.add(945l);
		notificationIds.add(944l);
		notificationIds.add(946l);
		notificationIds.add(943l);
		notificationIds.add(941l);
		notificationIds.add(939l);
		notificationIds.add(937l);

		request.setNotificationIds(notificationIds);

		// request.setFileType("csv");
		request.setFileType("excel");
		request.setAddCustName("yes");
		String json_request = CommonUtil.convertFromEntityToJsonStr(request);
		// Group and status 'new'
		Long reportFileLength = mockMvc
				.perform(
						get("/notification/downloadNotificationDetails.htm")
								.header("referer", "/notification/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out.println("reportFileLength size - " + reportFileLength);
	}

	@Test
	public void testDownloadNotificationOverView() throws Exception {

		Locale locale = Locale.ENGLISH;

		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': '2015-05-06','endDate':'2015-11-06' ,'alarmType': '','period':null,'grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'thisweek','grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'thismonth','grouplevel': 12}";
		String json_request = "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'thisyear','grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':null,'grouplevel': 12}";
		// String json_request =
		// "{'groupIds': [1,5,12,24,25],'startDate': null,'endDate':null ,'alarmType': '','period':'today','grouplevel': 12}";

		Long reportFileLength = mockMvc
				.perform(
						get("/notification/downloadNotificationOverView.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out.println("getNotificationOverViewData --> "
				+ reportFileLength);

	}

}

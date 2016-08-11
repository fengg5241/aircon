package com.panasonic.b2bacns.bizportal.acmaintenance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

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

import com.panasonic.b2bacns.bizportal.acconfig.controller.ACConfigController;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceRequest;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceSettingVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * The class <code>ACConfigControllerTest</code> contains tests for the class
 * <code>{@link ACConfigController}</code>.
 * 
 * @generatedBy CodePro at 14/09/15 3:40 PM, using the Spring generator
 * @author amitesh.arya
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class ACMaintenanceControllerTest extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Override
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testMock() throws Exception {
		assertNotNull("Mock MVC cannot be initialized", mockMvc);
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
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setLastSelectedGroupID(7l);
		sessionInfo.setUserTimeZone("America/Los_Angeles");
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	@Test
	public void testGetMaintenanceSetting() throws Exception {

		Locale locale = Locale.ENGLISH;
		ACMaintenanceRequest request = new ACMaintenanceRequest();
		request.setSiteID(8l);
		String acMaintenanceDetails;
		acMaintenanceDetails = mockMvc
				.perform(
						get("/maintenance/getMaintenanceSetting.htm")
								.header("referer", "/maintenance/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testGetMaintenanceSetting -- acMaintenanceDetails -- "
						+ acMaintenanceDetails);

	}

	@Test
	public void testGetMaintenanceSettingMailList() throws Exception {

		Locale locale = Locale.ENGLISH;
		ACMaintenanceRequest request = new ACMaintenanceRequest();
		request.setCompanyID(1l);
		String acMaintenanceUserDetails;
		acMaintenanceUserDetails = mockMvc
				.perform(
						get("/maintenance/getMaintenanceSettingMailList.htm")
								.header("referer", "/maintenance/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testGetMaintenanceSettingMailList -- acMaintenanceUserDetails -- "
						+ acMaintenanceUserDetails);

	}

	@Test
	@Rollback(false)
	public void testSetMaintenanceSetting() throws Exception {

		Locale locale = Locale.ENGLISH;
		ACMaintenanceRequest request = new ACMaintenanceRequest();
		request.setSiteID(8l);
		ACMaintenanceSettingVO obj1 = new ACMaintenanceSettingVO();
		obj1.setId(1l);
		obj1.setName("vrf_comp_1_operation_hours");
		obj1.setValue(234l);

		ACMaintenanceSettingVO obj2 = new ACMaintenanceSettingVO();
		obj2.setId(2l);
		obj2.setValue(2234l);
		obj2.setName("vrf_comp_2_operation_hours");

		ACMaintenanceSettingVO obj3 = new ACMaintenanceSettingVO();
		obj3.setId(3l);
		obj3.setValue(3334l);
		obj3.setName("vrf_comp_3_operation_hours");

		ACMaintenanceSettingVO obj4 = new ACMaintenanceSettingVO();
		obj4.setId(4l);
		obj4.setValue(2444l);
		obj4.setName("pac_comp_operation_hours");

		ACMaintenanceSettingVO obj5 = new ACMaintenanceSettingVO();
		obj5.setId(5l);
		obj5.setValue(5544l);
		obj5.setName("ghp_engine_operating_time");

		ACMaintenanceSettingVO obj6 = new ACMaintenanceSettingVO();
		obj6.setId(6l);
		obj6.setValue(234666l);
		obj6.setName("ghp_time_after_oil_change");

		List<ACMaintenanceSettingVO> acMaintenanceSettingList = new ArrayList<ACMaintenanceSettingVO>();
		acMaintenanceSettingList.add(obj1);
		acMaintenanceSettingList.add(obj2);
		acMaintenanceSettingList.add(obj3);
		acMaintenanceSettingList.add(obj4);
		acMaintenanceSettingList.add(obj5);
		acMaintenanceSettingList.add(obj6);
		request.setMaintenanceTypeList(acMaintenanceSettingList);

		String acMaintenanceDetails;
		acMaintenanceDetails = mockMvc
				.perform(
						post("/maintenance/setMaintenanceSetting.htm")
								.header("referer", "/maintenance/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testGetMaintenanceSetting -- acMaintenanceDetails -- "
						+ acMaintenanceDetails);
	}

	@Test
	@Rollback(false)
	public void testSetMaintenanceSettingMailList() throws Exception {

		Locale locale = Locale.ENGLISH;
		ACMaintenanceRequest request = new ACMaintenanceRequest();
		request.setCompanyID(1l);

		List<String> addUser = new ArrayList<String>();
		List<Long> deleteUser = new ArrayList<Long>();

		String email1 = "xyz@aa.com";
		String email2 = "xyz@aa1.com";
		String email3 = "xyz@aa2.com";

		Long id1 = 41l;
		Long id2 = 42l;
		Long id3 = 43l;

		addUser.add(email1);
		addUser.add(email2);
		addUser.add(email3);
		deleteUser.add(id1);
		deleteUser.add(id2);
		deleteUser.add(id3);
		request.setAddUserList(addUser);
		// request.setDeleteUserList(deleteUser);

		String acMaintenanceDetails;
		acMaintenanceDetails = mockMvc
				.perform(
						post("/maintenance/setMaintenanceSettingMailList.htm")
								.header("referer", "/maintenance/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testGetMaintenanceSetting -- setMaintenanceSettingMailList -- "
						+ acMaintenanceDetails);
	}

	@Test
	@Rollback(false)
	public void testResetThreshodAlert() throws Exception {

		Locale locale = Locale.ENGLISH;
		ACMaintenanceRequest request = new ACMaintenanceRequest();
		request.setOduID(15l);
		request.setMaintenanceID(1l);
		String acMaintenanceDetails;
		acMaintenanceDetails = mockMvc
				.perform(
						post("/maintenance/resetThreshodAlert.htm")
								.header("referer", "/maintenance/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testResetThreshodAlert -- testResetThreshodAlert -- "
						+ acMaintenanceDetails);
	}

	@Test
	public void testGetCurrentRemainingMaintenanceTime() throws Exception {

		Locale locale = Locale.ENGLISH;
		ACMaintenanceRequest request = new ACMaintenanceRequest();
		request.setOduID(16l);
		String acMaintenanceDetails;
		acMaintenanceDetails = mockMvc
				.perform(
						get(
								"/maintenance/getCurrentRemainingMaintenanceTime.htm")
								.header("referer", "/maintenance/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testGetCurrentRemainingMaintenanceTime -- acMaintenanceStatusData -- "
						+ acMaintenanceDetails);

	}

}

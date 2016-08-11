package com.panasonic.b2bacns.usermanagement.controller;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class UserManagementControllerTest {

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

	private MockHttpServletRequestBuilder get(String uri) {
		return MockMvcRequestBuilders.get(uri);
	}

	private MockHttpServletRequestBuilder post(String uri) {
		return MockMvcRequestBuilders.post(uri);
	}

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}

	private ResultMatcher redirectedUrl(String expectedUrl) {
		return MockMvcResultMatchers.redirectedUrl(expectedUrl);
	}

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		Map<Integer, String> roleTypeMap = new HashMap<Integer, String>();

		// roleTypeMap.put(3, "Customer");
		roleTypeMap.put(1, "Panasonic");
		SessionInfo sessionInfo = new SessionInfo();

		sessionInfo.setUserTimeZone("Asia/Kolkata");
		/* sessionInfo.setTimezoneId(1); */
		// sessionInfo.setUserRole("1");
		// sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(1l);
		sessionInfo.setRoleTypeMap(roleTypeMap);
		sessionInfo.setCompanyId(1l);
		// sessionInfo.setLastSelectedGroupID(7l);
		sessionInfo.setRoleTypeMap(roleTypeMap);
		CommonUtil.setSessionInfo(session, sessionInfo);

		return session;
	}

	@Test
	@Rollback(false)
	public void testgetGeneratedUserId() throws Exception {

		Locale locale = Locale.ENGLISH;
		String generatedUserId;

		generatedUserId = mockMvc
				.perform(
						get("/usermanagement/getGeneratedUserId.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json")
								.param("companyId", "2").session(getSession())
								.locale(locale)).andExpect(status().is(200))
				.andReturn().getResponse().getContentAsString();
		System.out.println("testgetGeneratedUserId -- User generated ID -- "
				+ generatedUserId);
	}

	/*@Test
	public void testgetSiteGroupByCompanyId() throws Exception {

		Locale locale = Locale.ENGLISH;
		String siteGroupByCompanyId = mockMvc
				.perform(
						get("/usermanagement/getSiteGroupByCompanyId.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyId", "100"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.print("testgetSiteGroupByCompanyId -- SiteId and SitegroupName"
						+ siteGroupByCompanyId);
	}*/

	/*@Test
	public void testgetSiteName() throws Exception {

		Locale locale = Locale.ENGLISH;
		String siteGroupNameByGroupId = mockMvc
				.perform(
						get("/usermanagement/getSiteName.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("groupId", "1,2")
								.param("idType", BizConstants.ID_TYPE_GROUP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetSiteName siteGroupNameByGroupId"
				+ siteGroupNameByGroupId);
	}*/

	/*@Test
	public void testgetLogicalGroup() throws Exception {

		Locale locale = Locale.ENGLISH;
		String logicalGroupNameBySiteId = mockMvc
				.perform(
						get("/usermanagement/getLogicalGroups.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("siteId", "3,28")
								.param("idType", BizConstants.ID_TYPE_SITE))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetLogicalGroup logicalGroupNameBySiteId"
				+ logicalGroupNameBySiteId);
	}*/

	/*@Test
	public void testgetControlGroup() throws Exception {

		Locale locale = Locale.ENGLISH;
		String logicalGroupNameBySiteId = mockMvc
				.perform(
						get("/usermanagement/getControlGroups.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("logicalId", "5,20")
								.param("idType", BizConstants.ID_TYPE_LOGICAL))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetControlGroup controlGroupNameByLogicalId"
				+ logicalGroupNameBySiteId);
	}*/

	@Test
	public void testgetRoleList() throws Exception {

		Locale locale = Locale.ENGLISH;
		String fetchRoleList = mockMvc
				.perform(
						get("/usermanagement/getRoleList.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetRoleList -- role_Id and role_name : "
				+ fetchRoleList);
	}

	@Test
	public void testgetRoleType() throws Exception {

		Locale locale = Locale.ENGLISH;
		String getRoleType = mockMvc
				.perform(
						get("/usermanagement/getRoleType.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetRoleType roleTypeNameByuser_id by roleTypeid"
				+ getRoleType);

	}

	@Test
	public void testgetFuncGrpList() throws Exception {

		Locale locale = Locale.ENGLISH;
		String getFuncList = mockMvc
				.perform(
						get("/usermanagement/getFuncGrpList.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("role_type_id", "3"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.print("testgetFuncGrpList FuncGrp_Id and FuncGrp_name by role_type_id"
						+ getFuncList);
	}

	/*@Test
	public void testgetPermissionDetails() throws Exception {

		Locale locale = Locale.ENGLISH;
		String permissionDetails = mockMvc
				.perform(
						get("/usermanagement/getPermissionDetails.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.print("testgetPermissionDetails -- permissions_Id and permissions_name"
						+ permissionDetails);
	}*/

	@Test
	// @Rollback(false)
	public void testaddNewRole() throws Exception {

		Locale locale = Locale.ENGLISH;
		String role_name = "role6";
		String roletype_id = "3";
		String functional_id = "3";

		String addNewRole = mockMvc
				.perform(
						post("/usermanagement/addNewRole.htm")
								.accept("text/html")
								.header("Referer", "addRole.htm")
								.session(getSession()).locale(locale)
								.param("role_name", role_name)
								.param("roletype_id", roletype_id)
								.param("functional_id", functional_id))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testaddNewRole -- response :: " + addNewRole);

	}

	@Test
	// @Rollback(false)
	public void testeditRole() throws Exception {

		Locale locale = Locale.ENGLISH;
		String role_name = "customer11";
		String roletype_id = "3";
		String functional_id = "2,5";
		String roleId = "5";

		String editRole = mockMvc
				.perform(
						post("/usermanagement/editRole.htm")
								.accept("text/html")
								.header("Referer", "/usermanagement/*,htm")
								.session(getSession()).locale(locale)
								.param("role_name", role_name)
								.param("roletype_id", roletype_id)
								.param("functional_id", functional_id)
								.param("roleId", roleId))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testeditRole -- response ::" + editRole);
	}

	@Test
	// @Rollback(false)
	public void deleteRole() throws Exception {

		Locale locale = Locale.ENGLISH;
		String deleteRole = mockMvc
				.perform(
						get("/usermanagement/deleteRole.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("roleId", "128"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testdeleteRole -- deleteRole" + deleteRole);
	}

	@Test
	public void testviewRoleLog() throws Exception {

		Locale locale = Locale.ENGLISH;
		String viewRoleLog = mockMvc
				.perform(
						get("/usermanagement/viewRoleLog.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyId", "1"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testviewRoleLog -- viewRoleLog : " + viewRoleLog);
	}

	/*@Test
	public void testgetUserIdList() throws Exception {

		Locale locale = Locale.ENGLISH;
		String getUserIdList = mockMvc
				.perform(
						get("/usermanagement/getUserIdList.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyId", ""))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetRoleList -- LoginId List" + getUserIdList);
	}*/

	@Test
	public void testgetUserIdListFull() throws Exception {

		Locale locale = Locale.ENGLISH;
		String getUserIdList = mockMvc
				.perform(
						get("/usermanagement/getUserIdListFull.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetRoleList -- Full List" + getUserIdList);
	}

	@Test
	public void testgetUserGroup() throws Exception {

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		// sessionInfo.setUserRole("Panasonic");
		sessionInfo.setUserRoleId("5");
		sessionInfo.setUserId(8l);
		sessionInfo.setCompanyId(2l);
		Map<Integer, String> roleTypeMap = new HashMap<Integer, String>();
		roleTypeMap.put(3, "Customer");
		// roleTypeMap.put(1, "Panasonic");
		// roleTypeMap.put(2, "Installer");
		sessionInfo.setRoleTypeMap(roleTypeMap);
		CommonUtil.setSessionInfo(session, sessionInfo);

		String getUserGroupTree = mockMvc
				.perform(
						get("/usermanagement/getUserGroup.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json").session(session)
								.param("type", "add"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("getUserGroupTree for User - " + getUserGroupTree);

	}

	@Test
	public void testgetUserDetailGroup() throws Exception {

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		// sessionInfo.setUserRole("Panasonic Admin 2");
		// sessionInfo.setUserRoleId("5");
		sessionInfo.setUserId(1l);
		sessionInfo.setCompanyId(1l);
		Map<Integer, String> roleTypeMap = new HashMap<Integer, String>();
		roleTypeMap.put(1, "Panasonic");
		// roleTypeMap.put(3, "Customer");
		sessionInfo.setRoleTypeMap(roleTypeMap);
		CommonUtil.setSessionInfo(session, sessionInfo);

		String getUserDetailTree = mockMvc
				.perform(
						get("/usermanagement/getUserDetail.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json").session(session)
								.param("type", "update").param("userId", "8"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("User Detail - " + getUserDetailTree);

	}

	@Test
	@Rollback(false)
	public void testuserRegistration() throws Exception {

		Locale locale = Locale.ENGLISH;
		String userRegistration;

		userRegistration = mockMvc
				.perform(
						get("/usermanagement/userRegistration.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyId", "2")
								.param("loginId", "0000085")
								.param("group_id", "12").param("role_Id", "22"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testuserRegistration -- User Registration -- "
				+ userRegistration);
	}

	@Test
	@Rollback(false)
	public void testUpdateUser() throws Exception {

		Locale locale = Locale.ENGLISH;
		String updateUser;

		updateUser = mockMvc
				.perform(
						get("/usermanagement/updateUser.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyId", "1").param("userId", "1")
								.param("group_id", "12,34,38")
								.param("old_group_id", "12")
								.param("userInfoUpdated", "false")
								.param("role_Id", "3")
								.param("account_state", "Valid"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testupdateUser -- User updateUser -- " + updateUser);
	}

	@Test
	public void testgetviewLog() throws Exception {

		Locale locale = Locale.ENGLISH;
		String viewLog;

		viewLog = mockMvc
				.perform(
						get("/usermanagement/viewLog.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("user_id", "14"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testgetviewLog -- Logs -- " + viewLog);
	}

	/*@Test
	// @Rollback(false)
	public void testActivateEmail() throws Exception {

		Locale locale = Locale.ENGLISH;
		String emailAddr = "testtoken6@rsystems.com";
		String emailtoken = "505aff0c-6052-40ef-bf56-9dba6b66798e";
		MvcResult getUserGroupTree = mockMvc
				.perform(
						post("/usermanagement/activateUserEmailId.htm")
								.accept("text/html")
								.header("Referer", "activateUserEmailId.htm")
								.locale(locale).param("emailAddr", emailAddr)
								.param("emailtoken", emailtoken))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/login/login.htm")).andReturn();

		System.out.println("getUserGroupTree for User - " + getUserGroupTree);

	}*/

	@Test
	// @Rollback(false)
	public void testresetUser() throws Exception {

		/*
		 * MockHttpSession session = new MockHttpSession(); SessionInfo
		 * sessionInfo = new SessionInfo(); sessionInfo.setUserRole("Customer");
		 * sessionInfo.setUserRoleId("5"); sessionInfo.setUserId(4l);
		 * sessionInfo.setCompanyId(1l); session.setAttribute("sessionInfo",
		 * sessionInfo);
		 */

		Locale locale = Locale.ENGLISH;

		String resetUser = mockMvc
				.perform(
						get("/usermanagement/resetUser.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("loginId", "verma123"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testresetUser -- testresetUser" + resetUser);
	}

	@Test
	@Rollback(false)
	public void testuserRegistrationFailedForExistingLoginId() throws Exception {

		Locale locale = Locale.ENGLISH;
		String userRegistration;

		userRegistration = mockMvc
				.perform(
						get("/usermanagement/userRegistration.htm")
								.header("referer", "/usermanagement/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyId", "")
								.param("loginId", "NitinVerma")
								.param("group_id", "12").param("role_Id", "50"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testuserRegistration -- User Registration -- "
				+ userRegistration);
	}

	@Test
	public void testgetCompanyList() throws Exception {

		Locale locale = Locale.ENGLISH;
		String fetchCompanyList = mockMvc
				.perform(
						get("/usermanagement/getcompanylist.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetCompanyList -- company_Id and company_name"
				+ fetchCompanyList);
	}

	@Test
	public void testgetUserlistundercompany() throws Exception {

		Locale locale = Locale.ENGLISH;
		String getUserList = mockMvc
				.perform(
						get("/usermanagement/getUserlistundercompany.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("companyids", "1"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetUserlistundercompany -- User List -- > "
				+ getUserList);
	}

	@Test
	public void testgetMultipleCompanyUserGroup() throws Exception {

		Locale locale = Locale.ENGLISH;
		String getUserList = mockMvc
				.perform(
						get("/usermanagement/getmultiplecompanyUserGroup.htm")
								.header("referer", "/usermanagement/*,htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("type", "add")
								.param("companyids", "2,3"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.print("testgetUserlistundercompany -- User List -- > "
				+ getUserList);
	}

}

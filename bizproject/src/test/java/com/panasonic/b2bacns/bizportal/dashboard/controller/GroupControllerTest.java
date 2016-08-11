/**
 * 
 */
package com.panasonic.b2bacns.bizportal.dashboard.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.group.controller.GroupController;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 *
 */
/**
 * The class <code>GroupControllerTest</code> contains tests for the class
 * <code>{@link GroupController}</code>.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class GroupControllerTest extends TestCase {

	@Autowired
	private GroupController groupController;

	/**
	 * Run the GroupController() constructor test.
	 */
	@Test
	public void testGroupController_1() throws Exception {
		assertNotNull("Controller cannot be initialized", groupController);
	}

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

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}

	private ViewResultMatchers view() {
		return MockMvcResultMatchers.view();
		// return MockMvcResultMatchers.redirectedUrl(expectedUrl);
	}

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setLastSelectedGroupID(7l);
		Map<Integer, String> roletypeMap = new HashMap<>();
		roletypeMap.put(BizConstants.ROLE_TYPE_ID_PANASONIC.intValue(),
				BizConstants.ROLE_TYPE_PANASONIC);
		sessionInfo.setRoleTypeMap(roletypeMap);

		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	@Test
	public void testGetIDU() throws Exception {

		Locale locale = Locale.ENGLISH;

		String getIDUList = mockMvc
				.perform(
						get("/group/getIDUs.htm")
								.header("referer", "/group/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "8,25"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		assertNotNull(getIDUList);

		System.out.println("testGetIDU - " + getIDUList);
	}

	@Test
	public void testGetGroupHierarchyTreeForAdminOrSuperAdmin()
			throws Exception {

		// String userRole = BizConstants.ROLE_SUPERADMIN;

		String getGroupHierarchyTree = mockMvc
				.perform(
						get("/group/getGroupHierarchyTree.htm")
								.header("referer", "/group/*.htm")
								.accept("application/json")
								.session(getSession()))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testGetGroupHierarchyTree for Role 'Admin' or 'Super Admin' - "
						+ getGroupHierarchyTree);

	}

	@Test
	public void testGetGroupHierarchyTreeForCustomer() throws Exception {

		// String userRole = BizConstants.ROLE_CUSTOMER;

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("Customer");
		sessionInfo.setUserRoleId("5");
		sessionInfo.setUserId(4l);
		sessionInfo.setCompanyId(2l);
		sessionInfo.setLastSelectedGroupID(7l);
		Map<Integer, String> roletypeMap = new HashMap<>();
		roletypeMap.put(BizConstants.ROLE_TYPE_ID_CUSTOMER.intValue(),
				BizConstants.ROLE_TYPE_CUSTOMER);
		sessionInfo.setRoleTypeMap(roletypeMap);
		CommonUtil.setSessionInfo(session, sessionInfo);

		String getGroupHierarchyTree = mockMvc
				.perform(
						get("/group/getGroupHierarchyTree.htm")
								.header("referer", "/group/*.htm")
								.accept("application/json").session(session))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testGetGroupHierarchyTree for Role 'Customer'- "
				+ getGroupHierarchyTree);

	}

}

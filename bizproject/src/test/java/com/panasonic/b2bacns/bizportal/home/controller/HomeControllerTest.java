package com.panasonic.b2bacns.bizportal.home.controller;

import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.home.vo.UserSelectionVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * The class <code>HomeControllerTest</code> contains tests for the class
 * <code>{@link HomeController}</code>.
 * 
 * @author shobhit.singh
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class HomeControllerTest extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		assertNotNull("Mock MVC cannot be initialized", mockMvc);
	}

	/**
	 * Test for saveLastGroupSelection method
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(false)
	public void testSaveLastGroupSelection() throws Exception {

		String lastSelectedGroups = "1,2,3";

		MockHttpSession session = new MockHttpSession();

		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(12l);
		sessionInfo.setCompanyId(1l);

		session.setAttribute("sessionInfo", sessionInfo);

		MvcResult result = mockMvc
				.perform(
						post("/home/saveLastVisitedGroups.htm")
								.accept("text/html")
								.header("Referer", "loginProcess.htm")
								.session(session)
								.param("id", lastSelectedGroups)
								.locale(Locale.ENGLISH))
				.andExpect(status().is(200)).andReturn();

		assertNotNull("Result should not be null", result);

		sessionInfo = (SessionInfo) result.getRequest().getSession()
				.getAttribute(BizConstants.SESSION_INFO_OBJECT_NAME);

		List<UserSelectionVO> userSelectionVOs = sessionInfo
				.getUserSelectionsList();
		assertNotNull("Result should not be null", userSelectionVOs);

		if (userSelectionVOs != null) {
			System.out.println("Size of userSelectionVOs: "
					+ userSelectionVOs.size());
			System.out.println("Saved JSON in database: "
					+ CommonUtil.convertFromEntityToJsonStr(userSelectionVOs));
		}

	}

	/**
	 * Test for getLastGroupSelection method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetLastGroupSelection() throws Exception {

		MockHttpSession session = new MockHttpSession();

		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(12l);
		sessionInfo.setCompanyId(1l);
		session.setAttribute("sessionInfo", sessionInfo);

		String response = mockMvc
				.perform(
						post("/home/getLastVisitedGroups.htm")
								.accept("application/json", "text/javascript")
								.header("Referer", "loginProcess.htm")
								.session(session)).andExpect(status().is(200))
				.andReturn().getResponse().getContentAsString();

		assertNotNull("response should not be null", response);

		System.out.println("ID of last vistited groups: " + response);

	}

	private MockHttpServletRequestBuilder post(String uri) {
		return MockMvcRequestBuilders.post(uri);
	}

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}
}
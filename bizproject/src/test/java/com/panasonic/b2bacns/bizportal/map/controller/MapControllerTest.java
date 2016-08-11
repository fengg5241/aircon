package com.panasonic.b2bacns.bizportal.map.controller;

import java.util.Locale;

import junit.framework.TestCase;
import junit.framework.TestSuite;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * Test Cases for SVG Map
 * 
 * 
 * @author Diksha.Rattan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class MapControllerTest extends TestCase {

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

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	/**
	 * Run the String testGroupJSON() method test for company.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGroupJSONForCompany() throws Exception {

		try {

			Locale locale = Locale.ENGLISH;

			long companyId = 1;

			String idType = BizConstants.ID_TYPE_COMPANY;

			String jsonResponse = mockMvc
					.perform(
							get("/map/getMapData.htm")
									.header("referer", "/dashboard/*.htm")
									.accept("application/json")
									.session(getSession())
									.locale(locale)
									.param("id",
											companyId
													+ BizConstants.EMPTY_STRING)
									.param("idType", idType))
					.andExpect(status().is(200)).andReturn().getResponse()
					.getContentAsString();

			System.out.println("testGroupJSONForCompany : " + jsonResponse);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * Run the String testGroupJSON() method test for groups.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGroupJSONForGroup() throws Exception {

		try {

			String jsonResponse = mockMvc
					.perform(
							get("/map/getMapData.htm")
									.header("referer", "/dashboard/*.htm")
									.accept("application/json")
									.session(getSession())
									.locale(Locale.ENGLISH)
									.param("id", "4,7")
									.param("idType", BizConstants.ID_TYPE_GROUP))
					.andExpect(status().is(200)).andReturn().getResponse()
					.getContentAsString();

			System.out.println("testGroupJSONForGroups : " + jsonResponse);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * Run the String testGroupJSON() method test for indoor units.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGroupJSONForIndoorUnit() throws Exception {

		try {

			String jsonResponse = mockMvc
					.perform(
							get("/map/getMapData.htm")
									.header("referer", "/dashboard/*.htm")
									.accept("application/json")
									.session(getSession())
									.locale(Locale.ENGLISH)
									.param("id", "25,26,27")
									.param("idType",
											BizConstants.ID_TYPE_INDOOR))
					.andExpect(status().is(200)).andReturn().getResponse()
					.getContentAsString();

			System.out.println("testGroupJSONForindoorUnit : " + jsonResponse);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * Launch the test.
	 * 
	 * @param args
	 *            the command line arguments
	 * 
	 * @generatedBy CodePro at 6/7/15 3:40 PM
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			// Run all of the tests
			junit.textui.TestRunner.run(MapControllerTest.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new MapControllerTest();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}

}
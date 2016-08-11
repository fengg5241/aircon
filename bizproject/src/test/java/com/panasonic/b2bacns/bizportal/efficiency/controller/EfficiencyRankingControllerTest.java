package com.panasonic.b2bacns.bizportal.efficiency.controller;

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

/**
 * The class <code>EfficiencyRankingControllerTest</code> contains tests for the
 * class <code>{@link EfficiencyRankingController}</code>.
 * 
 * @author Narendra.Kumar
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class EfficiencyRankingControllerTest extends TestCase {

	/**
	 * Run the EfficiencyRankingController() constructor test.
	 * 
	 * @generatedBy CodePro at 6/7/15 3:40 PM
	 */
	@Test
	public void testEfficiencyRankingController_1() throws Exception {
		assertNotNull("Controller cannot be initialized efficiencyRankingController");
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

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(6l);
		sessionInfo.setCompanyId(1l);
		sessionInfo.setUserTimeZone("Asia/Singapore");
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	@Test
	public void testGetEfficiencyonDashboard() throws Exception {

		Locale locale = Locale.ENGLISH;

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/getEfficiencyonDashboard.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("getEfficiencyRating - " + dataForGroupOn);

	}
	
	/**
	 * Run the ModelAndView workingHoursBreakdown(HttpServletRequest request,
	 * ModelMap model)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEfficiencyRankingDetailsByGroupPcThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 6}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/efficiencyRanking.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRankingDetailsByGroupPcThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testEfficiencyRankingDetailsByGroupPcThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 9}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/efficiencyRanking.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRankingDetailsByGroupPcThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testEfficiencyRankingDetailsByGroupPcThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 9}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/efficiencyRanking.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRankingDetailsByGroupPcThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testEfficiencyRankingDetailsByGroupPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'today','grouplevel': 6}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/efficiencyRanking.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRankingDetailsByGroupPcToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testEfficiencyRankingDetailsByGroupPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2015-09-01' ,'endDate': '2015-09-06','period': null,'grouplevel': 6}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/efficiencyRanking.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRankingDetailsByGroupPcRange - "
				+ dataForGroupOn);

	}

	@Test
	public void testDownloadEfficiencyRankingDetailsByGroupPcRange()
			throws Exception {

		Locale locale = Locale.ENGLISH;
		String json_request = "{ 'id': '8,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null,'grouplevel': 6,'fileType':'excel','addCustName':'yes'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadEfficiencyRanking.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out.println("downloadEfficiencyRanking reportFileLength- "
				+ reportFileLength);

	}

	public static void main(String[] args) {
		if (args.length == 0) {
			// Run all of the tests
			junit.textui.TestRunner.run(EfficiencyRankingControllerTest.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new EfficiencyRankingControllerTest();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}

}

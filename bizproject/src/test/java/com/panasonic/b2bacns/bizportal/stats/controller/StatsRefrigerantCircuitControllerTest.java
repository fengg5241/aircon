package com.panasonic.b2bacns.bizportal.stats.controller;

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
 * The class <code>StatsRefrigerantCircuitControllerTest</code> contains tests
 * for the class <code>{@link StatsController}</code>.
 * 
 * @author akansha
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class StatsRefrigerantCircuitControllerTest extends TestCase {

	@Autowired
	private StatsController statsController;

	/**
	 * Run the StatsController() constructor test.
	 * 
	 * @generatedBy CodePro at 6/7/15 3:40 PM
	 */
	@Test
	public void testStatsRefrigerantCircuitController_1() throws Exception {
		assertNotNull("Controller cannot be initialized", statsController);
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
		sessionInfo.setUserId(1l);
		sessionInfo.setCompanyId(1l);
		sessionInfo.setUserTimeZone("Asia/Singapore");
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	@Test
	public void testGetRefrigerantCircuitsByGroupIds() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,8','idType': 'group'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/getRefrigerantCircuitsByGroupIds.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testGetRefrigerantCircuitsByGroupIds - "
				+ dataForGroupOn);

	}

	// Accumulative Refrigerant Circuit test cases

	// POWER CONSUMPTION

	/**
	 * Run the ModelAndView workingHoursBreakdownChart(HttpServletRequest
	 * request, ModelMap model)
	 * 
	 * @throws Exception
	 */

	@Test
	public void testStatsByRefrigerantCircuitPcPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitPcPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitPcThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-10-26' ,'endDate': '2015-11-02','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcRange - "
				+ dataForGroupOn);

	}

	// CAPACITY

	@Test
	public void testByRefrigerantCircuitCapacityPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitCapacityThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitCapacityThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitCapacityThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testByRefrigerantCircuitCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitCapacityRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-10-06' ,'endDate': '2015-10-06','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityRange - "
				+ dataForGroupOn);

	}

	// EFFICIENCY

	@Test
	public void testStatsByRefrigerantCircuitEfficiencyPast24()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitEfficiencyThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitEfficiencyThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testStatsByRefrigerantCircuitEfficiencyThisMonth - "
						+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitEfficiencyThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitEfficiencyToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitEfficiencyRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2015-10-05' ,'endDate': '2015-10-11','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyRange - "
				+ dataForGroupOn);

	}

	// WORK HOUR

	@Test
	public void testStatsByRefrigerantCircuitWhPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'today','grouplevel': 1}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByRefrigerantCircuitWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': '2015-10-06' ,'endDate': '2015-10-06','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhRange - "
				+ dataForGroupOn);

	}

	// Chronological Refrigerant Circuit

	// Power Consumption

	@Test
	public void testStatsChronoByRefrigerantCircuitPcPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsChronByRefrigerantCircuitPcPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronoByRefrigerantCircuitPcThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsChronByRefrigerantCircuitPcThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-10-26' ,'endDate': '2015-11-02','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitPcRange - "
				+ dataForGroupOn);

	}

	// CAPACITY

	@Test
	public void testByChronoRefrigerantCircuitCapacityPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitCapacityThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitCapacityThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitCapacityThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testByChronoRefrigerantCircuitCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitCapacityRange()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-10-06' ,'endDate': '2015-10-06','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testByRefrigerantCircuitCapacityRange - "
				+ dataForGroupOn);

	}

	// EFFICIENCY

	@Test
	public void testStatsByChronoRefrigerantCircuitEfficiencyPast24()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitEfficiencyThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitEfficiencyThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testStatsByRefrigerantCircuitEfficiencyThisMonth - "
						+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitEfficiencyThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitEfficiencyToday()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitEfficiencyRange()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2015-10-05' ,'endDate': '2015-10-11','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitEfficiencyRange - "
				+ dataForGroupOn);

	}

	// WORK HOUR

	@Test
	public void testStatsByChronoRefrigerantCircuitWhPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhPast24 - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitWhThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhThisYear - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitWhThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhThisMonth - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitWhThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhThisWeek - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhToday - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsByChronoRefrigerantCircuitWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': '2015-10-06' ,'endDate': '2015-10-06','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testStatsByRefrigerantCircuitWhRange - "
				+ dataForGroupOn);

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
			junit.textui.TestRunner
					.run(StatsRefrigerantCircuitControllerTest.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new StatsRefrigerantCircuitControllerTest();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}

}

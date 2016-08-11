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
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * The class <code>StatsControllerTest</code> contains tests for the class
 * <code>{@link StatsController}</code>.
 * 
 * @author akansha
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class StatsControllerTest extends TestCase {

	@Autowired
	private StatsController statsController;

	/**
	 * Run the StatsController() constructor test.
	 * 
	 * @generatedBy CodePro at 6/7/15 3:40 PM
	 */
	@Test
	public void testStatsController_1() throws Exception {
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

	private ViewResultMatchers view() {
		return MockMvcResultMatchers.view();
		// return MockMvcResultMatchers.redirectedUrl(expectedUrl);
	}

	private ModelResultMatchers model() {
		return MockMvcResultMatchers.model();
	}

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(1l);
		sessionInfo.setCompanyId(1l);
		sessionInfo.setUserTimeZone("Asia/Singapore");
		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	// POWER CONSUMPTION

	/**
	 * Run the ModelAndView workingHoursBreakdownChart(HttpServletRequest
	 * request, ModelMap model)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStatsDetailsChartByGroupPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': 'null','endDate': 'null','period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisYearForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisMonthForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisWeekForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionTodayForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-10-26' ,'endDate': '2015-11-02','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionRangeForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByAirconPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisYearForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisYearForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '34,12','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisYearForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisMonthForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisMonthForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisMonthForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisWeekForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisWeekForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisWeekForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionTodayForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionTodayForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionTodayForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-10-01' ,'endDate': '2015-10-20','period': null}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionRangeForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-10-01' ,'endDate': '2015-10-20','period': null}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionRangeForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-10-01' ,'endDate': '2015-10-20','period': null}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionRangeForAirconGRP - "
				+ dataForAirconGRP);

	}

	// CAPACITY

	@Test
	public void testStatsDetailsChartByGroupCapacityThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisMonthForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisWeekForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityTodayForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-10-06' ,'endDate': '2015-10-06','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityRangeForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByAirconCapacityThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '109,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisYearForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisYearForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisYearForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconCapacityThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisMonthForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisMonthForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisMonthForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconCapacityThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisWeekForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisWeekForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisWeekForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityTodayForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '15,16','idType': 'outdoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityTodayForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityTodayForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconCapacityRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-09-15' ,'endDate': '2015-09-21','period': null}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityRangeForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-09-15' ,'endDate': '2015-09-21','period': null}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityRangeForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-09-15' ,'endDate': '2015-09-21','period': null}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityRangeForAirconGRP - "
				+ dataForAirconGRP);

	}

	// EFFICIENCY

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testEfficiencyThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisMonthForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testEfficiencyThisWeekForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyTodayForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2015-10-05' ,'endDate': '2015-10-11','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRangeForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByAirconEfficiencyThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisYearForAirconIDU - "
				+ dataForAirconIDU);

//		String json_request_ODU = "{ 'id': '15,16'idType': 'outdoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisyear'}";
//
//		String dataForAirconODU = mockMvc
//				.perform(
//						get("/stats/statisticsByAircon.htm")
//								.header("referer", "/dashboard/*.htm")
//								.accept("text/html").session(getSession())
//								.locale(locale)
//								.param("json_request", json_request_ODU))
//				.andExpect(status().is(200)).andReturn().getResponse()
//				.getContentAsString();
//
//		System.out.println("testEfficiencyThisYearForAirconODU - "
//				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisYearForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconEfficiencyThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisMonthForAirconIDU - "
				+ dataForAirconIDU);

//		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thismonth'}";
//
//		String dataForAirconODU = mockMvc
//				.perform(
//						get("/stats/statisticsByAircon.htm")
//								.header("referer", "/dashboard/*.htm")
//								.accept("text/html").session(getSession())
//								.locale(locale)
//								.param("json_request", json_request_ODU))
//				.andExpect(status().is(200)).andReturn().getResponse()
//				.getContentAsString();
//
//		System.out.println("testEfficiencyThisMonthForAirconODU - "
//				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisMonthForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconEfficiencyThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisWeekForAirconIDU - "
				+ dataForAirconIDU);

//		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisweek'}";
//
//		String dataForAirconODU = mockMvc
//				.perform(
//						get("/stats/statisticsByAircon.htm")
//								.header("referer", "/dashboard/*.htm")
//								.accept("text/html").session(getSession())
//								.locale(locale)
//								.param("json_request", json_request_ODU))
//				.andExpect(status().is(200)).andReturn().getResponse()
//				.getContentAsString();
//
//		System.out.println("testEfficiencyThisWeekForAirconODU - "
//				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisWeekForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconEfficiencyToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '109,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyTodayForAirconIDU - "
				+ dataForAirconIDU);

//		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'today'}";
//
//		String dataForAirconODU = mockMvc
//				.perform(
//						get("/stats/statisticsByAircon.htm")
//								.header("referer", "/dashboard/*.htm")
//								.accept("text/html").session(getSession())
//								.locale(locale)
//								.param("json_request", json_request_ODU))
//				.andExpect(status().is(200)).andReturn().getResponse()
//				.getContentAsString();
//
//		System.out.println("testEfficiencyTodayForAirconODU - "
//				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyTodayForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconEfficiencyRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRangeForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRangeForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyRangeForAirconGRP - "
				+ dataForAirconGRP);

	}

	// DIFFERENTIAL TEMPERATURE

	@Test
	public void testStatsDetailsChartByGroupDiffTempThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupDiffTempThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisMonthForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupDiffTempThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisWeekForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupDiffTempToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempTodayForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupDiffTempRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2011-10-01' ,'endDate': '2015-10-20','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempRangeForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisYearForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisYearForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisYearForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisMonthForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisMonthForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisMonthForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisWeekForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisWeekForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisWeekForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempTodayForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempTodayForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempTodayForAirconGRP - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempRangeForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

		String dataForAirconODU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempRangeForAirconODU - "
				+ dataForAirconODU);

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempRangeForAirconGRP - "
				+ dataForAirconGRP);

	}

	// WORK HOUR

	@Test
	public void testStatsDetailsChartByGroupWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisMonthForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisWeekForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'today','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhTodayForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByGroupWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': '2015-10-06' ,'endDate': '2015-10-06','period': null,'grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhRangeForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByAirconWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisYearForAirconIDU - " + dataForAirconIDU);

		// String json_request_ODU =
		// "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisYearForAirconGRP - " + dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisMonthForAirconIDU - " + dataForAirconIDU);

		// String json_request_ODU =
		// "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thismonth'}";

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisMonthForAirconGRP - " + dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisWeekForAirconIDU - " + dataForAirconIDU);

		// String json_request_ODU =
		// "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisweek'}";

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisWeekForAirconGRP - " + dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,109,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhTodayForAirconIDU - " + dataForAirconIDU);

		// String json_request_ODU =
		// "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'today'}";

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'today'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhTodayForAirconGRP - " + dataForAirconGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhRangeForAirconIDU - " + dataForAirconIDU);

		// String json_request_ODU =
		// "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

		String json_request_GRP = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhRangeForAirconGRP - " + dataForAirconGRP);

	}

	/**
	 * Run the ModelAndView workingHoursBreakdownChart(HttpServletRequest
	 * request, ModelMap model)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStatsDetailsChartByAircon() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = requestJsonForPowerConsumption();

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWorkingHoursBreakdownChartForGroupOn - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisYearForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisMonthForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionThisWeekForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionTodayForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2012-10-01' ,'endDate': '2015-09-15','period': null}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionRangeForGroup - "
				+ dataForGroupOn);

	}

	// CAPACITY

	@Test
	public void testStatsChronByGroupCapacityThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupCapacityThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '5,25','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisMonthForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupCapacityThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '5,25','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityThisWeekForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'today'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityTodayForGroup - " + dataForGroupOn);

	}

	// EFFICIENCY

	@Test
	public void testStatsChronByGroupEfficiencyThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testEfficiencyThisYearForGroup - " + dataForGroupOn);

	}
	//------- sample test case
	@Test
	public void testStatsChronByAirconEfficiencyThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testEfficiencyThisYearForAircon - " + dataForGroupOn);

	}

	
	
	@Test
	public void testStatsChronByGroupEfficiencyThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyThisMonthForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupEfficiencyThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testEfficiencyThisWeekForGroup - " + dataForGroupOn);

	}

	// DIFFERENTIAL TEMPERATURE

	@Test
	public void testStatsChronByGroupDiffTempThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupDiffTempThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisMonthForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupDiffTempThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempThisWeekForGroup - " + dataForGroupOn);

	}

	// WORK HOUR

	@Test
	public void testStatsChronByGroupWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': null,'endDate': null,'period': 'thisyear'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisYearForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'onff','startDate': null,'endDate': null,'period': 'thismonth'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisMonthForGroup - " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisweek'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhThisWeekForGroup - " + dataForGroupOn);

	}

	public String requestJsonForPowerConsumption() {

		String json_request_year_pc = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_year_pc_unit = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_month_pc = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String json_request_week_pc = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String json_request_today = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String json_request_range = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-09-18' ,'endDate': '2015-09-21','period': null}";

		return json_request_range;
	}

	public String requestJsonForCapacity() {

		String json_request_year_capacity = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_month_capacity = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thismonth'}";

		String json_request_week_capacity = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thisweek'}";

		String json_request_today_capacity = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'today'}";

		String json_request_range_capacity = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-09-15' ,'endDate': '2015-09-21','period': null}";

		return json_request_range_capacity;
	}

	public String requestJsonForEfficiency() {

		String json_request_year_efficiency = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'seer','startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_month_efficiency = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thismonth'}";

		String json_request_week_efficiency = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisweek'}";

		String json_request_today_efficiency = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'today'}";

		String json_request_range_efficiency = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null}";

		return json_request_range_efficiency;
	}

	public String requestJsonForDifferentialTemp() {

		String json_request_year_difftemp = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_month_difftemp = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

		String json_request_week_difftemp = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

		String json_request_today_difftemp = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

		String json_request_range_difftemp = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

		return json_request_range_difftemp;
	}

	public String requestJsonForWorkHour() {

		String json_request_year_wh = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'cooloff','startDate': null,'endDate': null,'period': 'thisyear'}";

		String json_request_month_wh = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thismonth'}";

		String json_request_week_wh = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisweek'}";

		String json_request_today_wh = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'today'}";

		String json_request_range_wh = "{ 'id': '5,25','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

		return json_request_range_wh;
	}

	@Test
	public void testenergyConsumptionGraph() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'startDate': '2015-10-01','endDate': '2015-10-26'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/energyConsumption.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testenergyConsumptionGraph - " + dataForGroupOn);

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
			junit.textui.TestRunner.run(StatsControllerTest.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new StatsControllerTest();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}

}

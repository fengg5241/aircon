/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.controller;

import java.util.Locale;

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
 * The class <code>StatsAccumulatedControllerTest</code> contains tests for the
 * class <code>{@link StatsController}</code> for accumulated type.
 * 
 * @author akansha
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class StatsAccumulatedControllerTest extends TestCase {

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
		sessionInfo.setUserTimeZone("Asia/Kolkata");
		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	// POWER CONSUMPTION

	/**
	 * Run the ModelAndView powerConsumptionChart(HttpServletRequest request,
	 * ModelMap model)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStatsDetailsChartByGroupPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12}";

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

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-11-01' ,'endDate': '2015-11-25','period': null,'grouplevel': 12}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-09-18' ,'endDate': '2015-09-21','period': null}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-09-18' ,'endDate': '2015-09-21','period': null}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': '2015-08-24' ,'endDate': '2015-09-30','period': null,'grouplevel': 6}";

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

	// EFFICIENCY

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 6}";

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

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 6}";

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

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null,'grouplevel': 6}";

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

	// DIFFERENTIAL TEMPERATURE

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

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
	public void testStatsDetailsChartByAirconWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

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

}

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
 * The class <code>StatsChronologicalControllerTest</code> contains tests for
 * the class <code>{@link StatsController}</code> for chronological type.
 * 
 * @author akansha
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class StatsChronologicalControllerTest extends TestCase {

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
		sessionInfo.setUserTimeZone("Asia/Kolkata");
		sessionInfo.setCompanyId(1l);
		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	// POWER CONSUMPTION

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

		String json_request = "{ 'id': '5,25','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request = "{ 'id': '12','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_5year = "{ 'id': '12','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2011-01-02' ,'endDate': '2015-11-30','period': null}";
		String json_request_3year = "{ 'id': '12','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2013-01-02' ,'endDate': '2015-11-30','period': null}";
		String json_request_1year = "{ 'id': '12','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-01-02' ,'endDate': '2015-10-31','period': null}";
		String json_request_0year = "{ 'id': '12','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-01-02' ,'endDate': '2015-10-30','period': null}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_1year))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionRangeForGroup - "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByAirconPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '17,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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
	public void testStatsChronByAirconPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_GRP = "{ 'id': '17,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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
	public void testStatsChronByAirconPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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
	public void testStatsChronByAirconPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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
	public void testStatsChronByAirconPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-09-18' ,'endDate': '2015-09-21','period': null}";

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

		String json_request_GRP = "{ 'id': '17,34','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-09-18' ,'endDate': '2015-09-21','period': null}";

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
	public void testStatsChronByGroupCapacityThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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
	public void testStatsChronByGroupCapacityRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': '2014-10-30' ,'endDate': '2015-10-30','period': null}";

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
	public void testStatsChronByGroupEfficiencyThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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
	public void testStatsChronByGroupEfficiencyThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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
	public void testStatsChronByGroupEfficiencyToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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
	public void testStatsChronByGroupEfficiencyRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,21,31','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null}";

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
	public void testStatsChronByAirconDiffTempThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear'}";

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
	public void testStatsChronByAirconDiffTempThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth'}";

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
	public void testStatsChronByAirconDiffTempThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek'}";

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
	public void testStatsChronByAirconDiffTempToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today'}";

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
	public void testStatsChronByAirconDiffTempRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null}";

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
	public void testStatsChronByAirconWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '26','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisyear'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisyear'}";

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
	public void testStatsChronByAirconWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thismonth'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thismonth'}";

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
	public void testStatsChronByAirconWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisweek'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'thisweek'}";

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
	public void testStatsChronByAirconWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'today'}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'today'}";

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
	public void testStatsChronByAirconWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

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

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null}";

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

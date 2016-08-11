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
 * The class <code>StatsAccumulatedDownloadControllerTest</code> contains tests
 * for the class <code>{@link StatsController}</code> for accumulated type.
 * 
 * @author akansha
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class StatsAccumulatedDownloadControllerTest extends TestCase {

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

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12,'addCustName':'yes', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out
				.println("testPowerConsumptionThisYearForGroup reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12,'addCustName':'yes', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out
				.println("testPowerConsumptionThisMonthForGroup reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out
				.println("testPowerConsumptionThisWeekForGroup reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out
				.println("testPowerConsumptionTodayForGroup reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-05-06' ,'endDate': '2015-06-06','period': null,'grouplevel': 1,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out
				.println("testPowerConsumptionRangeForGroup reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByAirconPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthIDU > 0);

		System.out
				.println("testPowerConsumptionThisYearForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_ODU = "{ 'id': '25,26,27','idType': 'outdoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear', 'fileType':'excel'}";

		Long reportFileLengthODU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_ODU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthODU > 0);

		System.out
				.println("testPowerConsumptionThisYearForAirconODU reportFileLengthODU- "
						+ reportFileLengthODU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthGRP > 0);

		System.out
				.println("testPowerConsumptionThisYearForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthIDU > 0);

		System.out
				.println("testPowerConsumptionThisMonthForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthGRP > 0);

		System.out
				.println("testPowerConsumptionThisMonthForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthIDU > 0);

		System.out
				.println("testPowerConsumptionThisWeekForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthGRP > 0);

		System.out
				.println("testPowerConsumptionThisWeekForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthIDU > 0);

		System.out
				.println("testPowerConsumptionTodayForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthGRP > 0);

		System.out
				.println("testPowerConsumptionTodayForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-01-18' ,'endDate': '2015-09-21','period': null, 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthIDU > 0);

		System.out
				.println("testPowerConsumptionRangeForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34',,'type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2014-01-18' ,'endDate': '2015-09-21','period': null,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLengthGRP > 0);

		System.out
				.println("testPowerConsumptionRangeForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	// CAPACITY

	@Test
	public void testStatsDetailsChartByGroupCapacityThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityThisYearForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityThisMonthForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityThisWeekForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityTodayForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupCapacityRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-08-24' ,'endDate': '2015-09-30','period': null,'grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityRangeForGroup reportFileLength- "
				+ reportFileLength);

	}

	// EFFICIENCY

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyThisYearForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel':6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyThisMonthForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyThisWeekForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyTodayForGroup reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByGroupEfficiencyRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null,'grouplevel': 6,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyRangeForGroup reportFileLength- "
				+ reportFileLength);

	}

	// DIFFERENTIAL TEMPERATURE

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisYear()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempThisYearForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempThisYearForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisMonth()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempThisMonthForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempThisMonthForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempThisWeek()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempThisWeekForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempThisWeekForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempTodayForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempTodayForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconDiffTempRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null, 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempRangeForAirconIDU reportFileLengthIDU- "
						+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': '2014-12-01' ,'endDate': '2015-01-31','period': null,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testDiffTempRangeForAirconGRP reportFileLengthGRP- "
						+ reportFileLengthGRP);

	}

	// WORK HOUR

	@Test
	public void testStatsDetailsChartByAirconWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisyear', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisYearForAirconIDU reportFileLengthIDU- "
				+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisyear','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisYearForAirconGRP reportFileLengthGRP- "
				+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thismonth','addCustName':'yes', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisMonthForAirconIDU reportFileLengthIDU- "
				+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thismonth','addCustName':'yes', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisMonthForAirconGRP reportFileLengthGRP- "
				+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisweek', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisWeekForAirconIDU reportFileLengthIDU- "
				+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'thisweek','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisWeekForAirconGRP reportFileLengthGRP- "
				+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'today', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhTodayForAirconIDU reportFileLengthIDU- "
				+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'today','addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhTodayForAirconGRP reportFileLengthGRP- "
				+ reportFileLengthGRP);

	}

	@Test
	public void testStatsDetailsChartByAirconWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhRangeForAirconIDU reportFileLengthIDU- "
				+ reportFileLengthIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLengthGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhRangeForAirconGRP reportFileLengthGRP- "
				+ reportFileLengthGRP);

	}

}

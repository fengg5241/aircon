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
import org.springframework.test.web.servlet.result.StatusResultMatchers;
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
public class StatsPast24ControllerTest extends TestCase {

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

	@Test
	public void testStatsDetailsChartByGroupPcPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34,38,44','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionForGroup past 24 Hours- "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsDetailsChartByAirconPcPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '12,34,38,44','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionForAirconIDU  past 24 Hours - "
				+ dataForAirconIDU);

	}

	@Test
	public void testStatsChronByGroupPcPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34,38,44','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionForGroup Past24- "
				+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByAirconPcPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionForAirconIDU Past24- "
				+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '12,34,38,44','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testPowerConsumptionForAirconGRPPast24 - "
				+ dataForAirconGRP);

	}

	// CAPACITY

	@Test
	public void testStatsDetailsChartByGroupCapacityPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 6}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityForGroup Past24- " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupCapacityPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testCapacityForGroup Past24- " + dataForGroupOn);

	}

	// EFFICIENCY

	@Test
	public void testStatsDetailsChartByGroupEfficiencyPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 6}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyForGroup Past24- " + dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupEfficiencyPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForGroupOn = mockMvc
				.perform(
						get("/stats/statisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testEfficiencyForGroup Past24- " + dataForGroupOn);

	}

	// DIFFERENTIAL TEMPERATURE

	@Test
	public void testStatsDetailsChartByAirconDiffTempPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,26,130','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempForAirconIDU past24hours- "
				+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempForAirconGRP past24hours - "
				+ dataForAirconGRP);

	}

	@Test
	public void testStatsChronByAirconDiffTempPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempForAirconIDU Past24- "
				+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testDiffTempForAirconGRP Past24- "
				+ dataForAirconGRP);

	}

	// WORK HOUR

	@Test
	public void testStatsDetailsChartByAirconWhPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,26,130','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhForAirconIDU Past24- " + dataForAirconIDU);

		String json_request_GRP = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'on','startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhForAirconGRP - Past24" + dataForAirconGRP);

	}

	@Test
	public void testStatsChronByAirconWhPast24() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '26','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconIDU = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhForAirconIDU Past24- " + dataForAirconIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : 'coolon','startDate': null,'endDate': null,'period': 'past24hours'}";

		String dataForAirconGRP = mockMvc
				.perform(
						get("/stats/statisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testWhForAirconGRP Past24 - " + dataForAirconGRP);

	}

	// downloads

	// Power Consumption

	@Test
	public void testStatsAccumulatedDetailsChartByGroupPcPast24HoursDownload()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34,38,44','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

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
				.println("testStatsAccumulatedDetailsChartByGroupPcPast24HoursDownload reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsChronDetailsChartByGroupPcPast24HoursDownload()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '12,34,38,44','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','addCustName':'yes', 'fileType':'excel'}";

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
				.println("testStatsChronDetailsChartByGroupPcPast24HoursDownload reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsAccumulatedDetailsChartByAirconPcPast24Download()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,26,130','idType': 'indoorUnit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsAccumulatedDetailsChartByAirconPcPast24Download  past 24 Hours - "
						+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconPcPast24Download past24hours - "
						+ dataForAirconGRP);

	}

	@Test
	public void testStatsChronoDetailsChartByAirconPcPast24Download()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,26,130','idType': 'indoorUnit','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconPcPast24Download  past 24 Hours - "
						+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '8,31,35,30','idType': 'group','type': 'chronological','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		long dataForAirconGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconPcPast24Download past24hours - "
						+ dataForAirconGRP);

	}

	// Capacity

	@Test
	public void testStatsAccumulatedDetailsChartByGroupCapacityPast24Download()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 6, 'fileType':'excel'}";

		Long dataForGroupOn = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsAccumulatedDetailsChartByGroupCapacityPast24Download Past24- "
						+ dataForGroupOn);

	}

	@Test
	public void testStatsChronByGroupCapacityPast24Download() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'chronological','parameter': 'capacity', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForGroupOn = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronByGroupCapacityPast24Download Past24- "
						+ dataForGroupOn);

	}

	// EFFICIENCY

	@Test
	public void testStatsAccumulatedDetailsChartByGroupEfficiencyPast24Download()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours','grouplevel': 6, 'fileType':'excel'}";

		Long dataForGroupOn = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsAccumulatedDetailsChartByGroupEfficiencyPast24Download Past24- "
						+ dataForGroupOn);

	}

	@Test
	public void testStatsChronoChronByGroupEfficiencyPast24Download()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '8,31,35,30','idType': 'group','type': 'chronological','parameter': 'efficiency', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForGroupOn = mockMvc
				.perform(
						get("/stats/downloadStatisticsByGroup.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoChronByGroupEfficiencyPast24Download Past24- "
						+ dataForGroupOn);

	}
	
	// diff temp
	
	@Test
	public void testStatsAccumulatedDetailsChartByAirconDiffTempPast24Hour() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testDiffTempTodayForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'accumulated','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testDiffTempTodayForAirconGRP - "
				+ dataForAirconGRP);

	}
	
	@Test
	public void testStatsChronologicalDetailsChartByAirconDiffTempPast24Hour() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '25,26,27','idType': 'indoorUnit','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testDiffTempTodayForAirconIDU - "
				+ dataForAirconIDU);

		String json_request_GRP = "{ 'id': '12,34','idType': 'group','type': 'chronological','parameter': 'difftemperature', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testDiffTempTodayForAirconGRP - "
				+ dataForAirconGRP);

	}


	// Working hour

	@Test
	public void testStatsAccumulatedDetailsChartByAirconWhPast24DownloadForUnit()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,26,130','idType': 'indoorUnit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconWhPast24Download  past 24 Hours - "
						+ dataForAirconIDU);

	}

	@Test
	public void testStatsAccumulatedDetailsChartByAirconWhPast24DownloadForGroup()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_GRP = "{ 'id': '8,31','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconWhPast24Download past24hours - "
						+ dataForAirconGRP);

	}

	@Test
	public void testStatsChronoDetailsChartByAirconWhPast24DownloadForUnit()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_IDU = "{ 'id': '108,26,130','idType': 'indoorUnit','type': 'chronological','parameter': 'workinghours', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconIDU = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_IDU))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconWhPast24DownloadForUnit  past 24 Hours - "
						+ dataForAirconIDU);

	}

	@Test
	public void testStatsChronoDetailsChartByAirconWhPast24DownloadForGroup()
			throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request_GRP = "{ 'id': '8,34','idType': 'group','type': 'chronological','parameter': 'workinghours', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'past24hours', 'fileType':'excel'}";

		Long dataForAirconGRP = mockMvc
				.perform(
						get("/stats/downloadStatisticsByAircon.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request_GRP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testStatsChronoDetailsChartByAirconWhPast24DownloadForGroup past24hours - "
						+ dataForAirconGRP);

	}

}

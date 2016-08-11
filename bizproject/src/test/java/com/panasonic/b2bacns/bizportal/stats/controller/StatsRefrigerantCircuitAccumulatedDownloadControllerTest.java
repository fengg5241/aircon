/**
 * 
 */
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
public class StatsRefrigerantCircuitAccumulatedDownloadControllerTest extends
		TestCase {

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
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	// POWER CONSUMPTION

	@Test
	public void testStatsDetailsChartByRCPcThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12,'addCustName':'yes', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testPowerConsumptionThisYearForRc reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCPcThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testPowerConsumptionThisMonthForRc reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testPowerConsumptionThisWeekForRc reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCPcToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': null,'endDate': null,'period': 'today','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testPowerConsumptionTodayForRc reportFileLength size - "
						+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCPcRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'power_consumption', 'parameterOption' : null,'startDate': '2015-01-28' ,'endDate': '2015-10-05','period': null,'grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out
				.println("testPowerConsumptionRangeForRc reportFileLength size - "
						+ reportFileLength);

	}

	// CAPACITY

	@Test
	public void testStatsDetailsChartByRCCapacityThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityThisYearForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCCapacityThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'cool','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityThisMonthForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCCapacityThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityThisWeekForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCCapacityToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': null,'endDate': null,'period': 'today','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityTodayForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCCapacityRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'capacity', 'parameterOption' : 'heat','startDate': '2015-08-24' ,'endDate': '2015-09-30','period': null,'grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testCapacityRangeForRc reportFileLength- "
				+ reportFileLength);

	}

	// EFFICIENCY

	@Test
	public void testStatsDetailsChartByRCEfficiencyThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyThisYearForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCEfficiencyThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyThisMonthForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCEfficiencyThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyThisWeekForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCEfficiencyToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': null,'endDate': null,'period': 'today','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyTodayForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCEfficiencyRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'efficiency', 'parameterOption' : 'cop','startDate': '2013-05-26' ,'endDate': '2015-09-21','period': null,'grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testEfficiencyRangeForRc reportFileLength- "
				+ reportFileLength);

	}

	// WORK HOUR

	@Test
	public void testStatsDetailsChartByRCWhThisYear() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisyear','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisYearForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCWhThisMonth() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thismonth','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisMonthForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCWhThisWeek() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'thisweek','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhThisWeekForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCWhToday() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': null,'endDate': null,'period': 'today','grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhTodayForRc reportFileLength- "
				+ reportFileLength);

	}

	@Test
	public void testStatsDetailsChartByRCWhRange() throws Exception {

		Locale locale = Locale.ENGLISH;

		String json_request = "{ 'id': '1,2,3','idType': 'refrigerantcircuit','type': 'accumulated','parameter': 'workinghours', 'parameterOption' : 'onoff','startDate': '2014-12-19' ,'endDate': '2015-02-05','period': null,'grouplevel': 12,'addCustName':'no', 'fileType':'excel'}";

		Long reportFileLength = mockMvc
				.perform(
						get("/stats/downloadStatisticsByRefrigerantCircuit.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("text/html").session(getSession())
								.locale(locale)
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		System.out.println("testWhRangeForRc reportFileLength- "
				+ reportFileLength);

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
					.run(StatsRefrigerantCircuitAccumulatedDownloadControllerTest.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new StatsRefrigerantCircuitAccumulatedDownloadControllerTest();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}

}

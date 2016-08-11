package com.panasonic.b2bacns.bizportal.dashboard.controller;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.rc.AirconModeValidation;
import com.panasonic.b2bacns.bizportal.rc.FanSpeed;
import com.panasonic.b2bacns.bizportal.rc.RCOperation;
import com.panasonic.b2bacns.bizportal.rc.WinDirection;
import com.panasonic.b2bacns.bizportal.rcset.vo.RCSetControlRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * The class contains test cases of RCController
 * 
 * @author shobhit.singh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class RCControllerTest extends TestCase {

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

	/**
	 * Run the String getControlRC(List<Long> iduIDs) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	// @Ignore
	public void testRCValidate() throws Exception {

		String jsonResponse = mockMvc
				.perform(
						get("/rc/RCValidate.htm")
								.accept("application/json", "text/javascript")
								.header("Referer", "/dashboard/*.htm")
								.session(getSession()).param("id", "29"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("Response: " + jsonResponse);

		assertNotNull("jsonResponse should not be null", jsonResponse);
	}

	@Test
	// @Ignore
	public void testGetRCOperationsLog() throws Exception {
		for (int i = 0; i < 3; i++) {
			String jsonResponse = mockMvc
					.perform(
							post("/rc/getRCOperationsLog.htm")
									.accept("application/json",
											"text/javascript")
									.header("Referer", "/dashboard/*.htm")
									.session(getSession())
									.param("unitIDs", "29,25,26,28")
									.param("fromDateTime", "2015-12-19")
									.param("toDateTime", "2016-01-05")
									.param("pageNo", "1"))
					.andExpect(status().is(200)).andReturn().getResponse()
					.getContentAsString();

			System.out.println("Response: " + jsonResponse);

			assertNotNull("jsonResponse should not be null", jsonResponse);
		}
	}

	@Test
	// @Ignore
	public void testGetRCOperationsLogPageCount() throws Exception {

		String jsonResponse = mockMvc
				.perform(
						post("/rc/getRCOpLogPageCount.htm")
								.accept("application/json", "text/javascript")
								.header("Referer", "/dashboard/*.htm")
								.session(getSession())
								.param("unitIDs", "29,25,26,28")
								.param("fromDateTime", "2015-12-19")
								.param("toDateTime", "2016-01-05"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("Response: " + jsonResponse);

		assertNotNull("jsonResponse should not be null", jsonResponse);
	}

	@Test()
	@Rollback(false)
	// @Ignore
	public void testSetRCSetting() throws Exception {
		Set<Long> iduIdList = new HashSet<Long>();
		iduIdList.add(29l);
		iduIdList.add(25l);
		iduIdList.add(26l);
		iduIdList.add(27l);
		// iduIdList.add(33l);
		// iduIdList.add(34l);
		// iduIdList.add(35l);

		Map<String, String> operationMap = new LinkedHashMap<String, String>();
//		operationMap.put(RCOperation.POWERSTATUS.name(), BizConstants.ON);
//		operationMap.put(RCOperation.TEMPERATURE.name(), String.valueOf(30.0));
//		operationMap.put(RCOperation.MODE.name(),
//				AirconModeValidation.HEAT.name());
		operationMap.put(RCOperation.FANSPEED.name(), FanSpeed.LOW.name());
//		operationMap.put(RCOperation.WINDDIRECTION.name(),
//				WinDirection.F1.name());
//		operationMap.put(RCOperation.ENERGY_SAVING.name(), BizConstants.ZERO);
//		operationMap.put(RCOperation.ENERGY_SAVING.name(), BizConstants.ZERO);
//
//		operationMap.put(RCOperation.PROHIBITION_POWERSTATUS.name(),
//				BizConstants.ZERO);
//		operationMap.put(RCOperation.PROHIBITON_MODE.name(), BizConstants.ZERO);
//		operationMap.put(RCOperation.PROHIBITION_FANSPEED.name(),
//				BizConstants.ZERO);
//		operationMap.put(RCOperation.PROHIBITION_WINDRIECTION.name(),
//				BizConstants.ZERO);
//		operationMap.put(RCOperation.PROHIBITION_SET_TEMP.name(),
//				BizConstants.ZERO);
//		operationMap.put(RCOperation.PROHIBITION_ENERGY_SAVING.name(),
//				BizConstants.ZERO);

		RCSetControlRequestVO rcSetControlRequestVO = new RCSetControlRequestVO();
		rcSetControlRequestVO.setId(iduIdList);
		rcSetControlRequestVO.setOperation(operationMap);

		String json_request = CommonUtil
				.convertFromEntityToJsonStr(rcSetControlRequestVO);

		String rcOperationStatus = mockMvc
				.perform(
						post("/rc/setControlRC.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param("json_request", json_request))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("setControlRC  - " + rcOperationStatus);

		Thread.sleep(15 * 1000);

	}

	@Test
	public void downloadRCOperationsLog() throws Exception {

		String jsonResponse = mockMvc
				.perform(
						post("/rc/downloadRCOperationsLog.htm")
								.accept("application/json", "text/javascript")
								.header("Referer", "/dashboard/*.htm")
								.session(getSession())
								.param("unitIDs", "29,25,26,28")
								.param("fromDateTime", "2015-12-19")
								.param("toDateTime", "2016-02-17")
								.param("reportType",
										BizConstants.REPORT_TYPE_EXCEL))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("Response: " + jsonResponse);

		assertNotNull("jsonResponse should not be null", jsonResponse);
	}

	/**
	 * Returns MockHttpServletRequestBuilder for post call
	 * 
	 * @param uri
	 * @return
	 */
	private MockHttpServletRequestBuilder post(String uri) {
		return MockMvcRequestBuilders.post(uri);
	}

	/**
	 * Returns MockHttpServletRequestBuilder for get call
	 * 
	 * @param uri
	 * @return
	 */
	private MockHttpServletRequestBuilder get(String uri) {
		return MockMvcRequestBuilders.get(uri);
	}

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}

	/**
	 * Returns MockHttpSession
	 * 
	 * @return
	 */
	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(2l);
		sessionInfo.setCompanyId(1l);
		sessionInfo.setUserTimeZone("Asia/Kolkata");
		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 * @see TestCase#tearDown()
	 * 
	 * @generatedBy CodePro at 6/7/15 3:40 PM
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// Add additional tear down code here
	}

}

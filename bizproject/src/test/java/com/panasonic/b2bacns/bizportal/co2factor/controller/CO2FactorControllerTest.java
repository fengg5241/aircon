/**
 * 
 */
package com.panasonic.b2bacns.bizportal.co2factor.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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

import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorRequestVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Narendra.Kumar
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class CO2FactorControllerTest {

	protected MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testMock() throws Exception {
		assertNotNull("Mock MVC cannot be initialized", mockMvc);
	}

	@Test
	public void testNotificationController_1() throws Exception {
		assertNotNull("Controller cannot be initialized");
	}

	private MockHttpServletRequestBuilder get(String uri) {
		return MockMvcRequestBuilders.get(uri);
	}

	private MockHttpServletRequestBuilder post(String uri) {
		return MockMvcRequestBuilders.post(uri);
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
		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	@Test
	public void testViewCO2FactorWithSiteID() throws Exception {

		String getCO2FactorJson = "";

		getCO2FactorJson = mockMvc
				.perform(
						get("/co2Factor/getCO2Factor.htm")
								.header("referer", "/co2Factor/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param("siteId","8"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getCO2FactorJson with SiteID - " + getCO2FactorJson);

	}

	@Test
	public void testViewCO2FactorWithoutSiteID() throws Exception {

		String getCO2FactorJson = "";

		getCO2FactorJson = mockMvc
				.perform(
						get("/co2Factor/getCO2Factor.htm")
								.header("referer", "/co2Factor/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								)
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getCO2FactorJson Without SiteID - " + getCO2FactorJson);

	}

	@Test
	@Rollback(false)
	public void testSaveCO2Factor() throws Exception {

		List<CO2FactorRequestVO> co2FactorRequestVOList = new ArrayList<CO2FactorRequestVO>();
		CO2FactorRequestVO co2FactorRequestVO1 = new CO2FactorRequestVO();
		CO2FactorRequestVO co2FactorRequestVO2 = new CO2FactorRequestVO();

		co2FactorRequestVO1.setSiteIds(8l);
		co2FactorRequestVO1.setCo2FactorValue(112.21);
		co2FactorRequestVO1.setStartDate("2016-02-29 3:46:00");
		co2FactorRequestVO2.setSiteIds(5l);
		co2FactorRequestVO2.setCo2FactorValue(2.31);
		co2FactorRequestVO2.setStartDate("2016-02-29 3:47:00");
		//co2FactorRequestVOList.add(co2FactorRequestVO1);
		co2FactorRequestVOList.add(co2FactorRequestVO2);
		String saveCO2FactorJsonParam = CommonUtil
				.convertFromEntityToJsonStr(co2FactorRequestVOList);
		String saveCO2FactorJson = "";
		saveCO2FactorJson = mockMvc
				.perform(
						post("/co2Factor/saveCO2Factor.htm")
								.header("referer", "/co2Factor/*.htm")
								.accept("application/json", "text/javascript")
								.session(getSession())
								.param(BizConstants.SAVE_CO2FACTOR,
										saveCO2FactorJsonParam)

				).andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("saveCO2FactorJson - " + saveCO2FactorJson);
	}

}

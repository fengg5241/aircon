/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author simanchal.patra
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
// @Ignore
public class CutoffRequestControllerTest extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

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
		sessionInfo.setUserId(3l);
		sessionInfo.setLastSelectedGroupID(7l);
		CommonUtil.setSessionInfo(session, sessionInfo);
		return session;
	}

	@Test
	@Rollback(value = false)
	public void testGetAllRegisteredCutoffRequests()
			throws UnsupportedEncodingException, JsonProcessingException,
			Exception {

		String response = mockMvc
				.perform(
						get("/cr/getAllCutoffRequests.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession()))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out
				.println("testGetAllRegisteredCutoffRequests --  " + response);
	}

	@Test
	@Rollback(value = false)
	public void testRegisterCutoffRequest()
			throws UnsupportedEncodingException, JsonProcessingException,
			Exception {

	//	for (int i = 0; i < 100; i++) {

			String response = mockMvc
					.perform(
							post("/cr/registerCutoffRequest.htm")
									.header("referer", "/home.htm")
									.accept("application/json")
									.session(getSession())
									.param("fromdate", "2016-01-20")
									.param("todate", "2016-01-26")
									.param("siteidList", "2,6, 21"))
					.andExpect(status().is(200)).andReturn().getResponse()
					.getContentAsString();
			
			Thread.sleep(1001);

			System.out.println("testRegisterCutoffRequest --  " + response);
	//	}
	}

	@Test
	public void testGetDistributionGroupsBySite()
			throws UnsupportedEncodingException, JsonProcessingException,
			Exception {

		String response = mockMvc
				.perform(
						get("/cr/getDistributionGroupsBySiteId.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession()).param("siteID", "8"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("response --  " + response);

	}

	@Test
	@Rollback(value = false)
	public void testDownloadPowerRatioReport() throws Exception {
		
		Double d = new Double("0.00000000000000000000");
		System.out.println(d.toString());
		System.out.println(String.valueOf(d));
		
		System.out.println(convertToBigDecimal("0.00000000000000000000"));
		
		

		Locale locale = Locale.ENGLISH;
		String powerRatioReport = mockMvc
				.perform(
						get("/cr/downloadPowerRatioReport.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("transactionId", "15"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("powerRatioReport ---> " + powerRatioReport);
	}

	@Test
	@Rollback(value = false)
	public void testDownloadPowerDetailReport() throws Exception {

		Locale locale = Locale.ENGLISH;
		String powerDetailReport = mockMvc
				.perform(
						get("/cr/downloadPowerDetailReport.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("transactionId", "16"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("powerDetailReport ---> " + powerDetailReport);
	}
	
	private static BigDecimal convertToBigDecimal(String bigDecimalStr)
			throws Exception {

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		String pattern = "###.#";

		BigDecimal val = BigDecimal.ZERO;

		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		if (bigDecimalStr != null && !bigDecimalStr.equals(BizConstants.HYPHEN)) {
			try {
				val = (BigDecimal) decimalFormat.parse(bigDecimalStr);
			} catch (ParseException e) {
				throw new Exception(
						String.format(
								"Error occured while converting String %s to BigDecimal",
								bigDecimalStr));
			}
		}
		
		System.out.println(val.toPlainString());
		System.out.println(val.toString());

		return val;
	}
	
}

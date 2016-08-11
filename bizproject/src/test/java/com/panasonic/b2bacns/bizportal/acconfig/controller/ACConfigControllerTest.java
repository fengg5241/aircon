package com.panasonic.b2bacns.bizportal.acconfig.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
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

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * The class <code>ACConfigControllerTest</code> contains tests for the class
 * <code>{@link ACConfigController}</code>.
 * 
 * @generatedBy CodePro at 14/09/15 3:40 PM, using the Spring generator
 * @author amitesh.arya
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class ACConfigControllerTest extends TestCase {

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
		sessionInfo.setUserId(24l);
		sessionInfo.setLastSelectedGroupID(7l);
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	@Test
	public void testViewACConfigurationGroupGET() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(8l);
		// groupIds.add(25l);
		ACConfigRequest request = new ACConfigRequest();
		request.setId(groupIds);
		request.setIdType(BizConstants.ID_TYPE_GROUP);
		String acconfigDetails;
		// Group
		acconfigDetails = mockMvc
				.perform(
						get("/acconfig/getACDetails.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testViewACConfiguration -- acconfigDetails Group-- "
						+ acconfigDetails);

	}

	@Test
	public void testViewACConfigurationGroupPOST() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(8l);
		// groupIds.add(25l);
		ACConfigRequest request = new ACConfigRequest();
		request.setId(groupIds);
		request.setIdType(BizConstants.ID_TYPE_GROUP);
		String acconfigDetails;
		// Group
		acconfigDetails = mockMvc
				.perform(
						post("/acconfig/getACDetails.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testViewACConfiguration -- acconfigDetails Group-- "
						+ acconfigDetails);

	}

	@Test
	public void testViewACConfigurationIndoorUnit() throws Exception {

		Locale locale = Locale.ENGLISH;

		ACConfigRequest request = new ACConfigRequest();

		String acconfigDetails;

		// Indoor Unit
		List<Long> indoorIds = new ArrayList<Long>();
		indoorIds.add(25l);
		indoorIds.add(26l);
		indoorIds.add(27l);
		// indoorIds.add(31l);
		request.setId(indoorIds);
		request.setIdType(BizConstants.ID_TYPE_INDOOR);

		acconfigDetails = mockMvc
				.perform(
						get("/acconfig/getACDetails.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testViewACConfiguration -- acconfigDetails Indoor-- "
						+ acconfigDetails);

	}

	@Test
	public void testViewACConfigurationODUGroup() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(21l);
		//groupIds.add(31l);
		ACConfigRequest request = new ACConfigRequest();
		request.setId(groupIds);
		request.setIdType(BizConstants.ID_TYPE_GROUP);
		String acconfigDetails;
		// Group
		acconfigDetails = mockMvc
				.perform(
						get("/acconfig/getACDetailsODU.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testViewACConfigurationODUGroup -- acconfigDetails ODU Group-- "
						+ acconfigDetails);

	}

	@Test
	public void testViewACConfigurationOutdoorUnit() throws Exception {

		Locale locale = Locale.ENGLISH;

		ACConfigRequest request = new ACConfigRequest();

		String acconfigDetails;

		// Outdoor Unit
		List<Long> odtdoorIds = new ArrayList<Long>();
		odtdoorIds.add(2l);
		//odtdoorIds.add(16l);
		//odtdoorIds.add(17l);
		//odtdoorIds.add(18l);
		request.setId(odtdoorIds);
		request.setIdType(BizConstants.ID_TYPE_OUTDOOR);

		acconfigDetails = mockMvc
				.perform(
						get("/acconfig/getACDetailsODU.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out
				.println("testViewACConfigurationOutdoorUnit -- acconfigDetails Outdoor-- "
						+ acconfigDetails);

	}

	@Test
	public void testDownloadACDetailsODU() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(8l);
		groupIds.add(25l);
		ACConfigRequest request = new ACConfigRequest();
		request.setId(groupIds);
		request.setIdType(BizConstants.ID_TYPE_GROUP);
		request.setFileType("csv");
		request.setAddCustName("yes");
		Long reportFileLength = mockMvc
				.perform(
						get("/acconfig/downloadACDetailsODU.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out
				.println("testDownloadACDetails ODU -- reportFileLength size - "
						+ reportFileLength);
	}

	@Test
	public void testDownloadACDetails() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(8l);
		// groupIds.add(25l);
		ACConfigRequest request = new ACConfigRequest();
		request.setId(groupIds);
		request.setIdType(BizConstants.ID_TYPE_GROUP);
		request.setFileType("excel");
		request.setAddCustName("yes");
		Long reportFileLength = mockMvc
				.perform(
						get("/acconfig/downloadACDetails.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out.println("testDownloadACDetails -- reportFileLength size - "
				+ reportFileLength);
	}

	@Test
	@Ignore
	public void testdownloadODUParams() throws Exception {
		Locale locale = Locale.ENGLISH;
		Long reportFileLength = mockMvc
				.perform(
						get("/acconfig/downloadODUParams.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "18").param("params", "")
								.param("idType", "VRF")
								.param("fileType", "excel"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentLengthLong();

		assert (reportFileLength > 0);

		System.out.println("reportFileLength size - " + reportFileLength);
	}

	@Test
	public void testODUList() throws Exception {
		Locale locale = Locale.ENGLISH;
		String getODUListData = mockMvc
				.perform(
						get("/acconfig/getODUList.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "34,25")
								.param("idType", BizConstants.ID_TYPE_INDOOR))
				// .param("idType", BizConstants.ID_TYPE_GROUP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getODUListData ---> " + getODUListData);

	}

	@Test
	public void testODUFORGroupID() throws Exception {
		Locale locale = Locale.ENGLISH;
		String getODUForGroup = mockMvc
				.perform(
						get("/acconfig/getODUList.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "47,37,21,22")
								.param("idType", BizConstants.ID_TYPE_GROUP))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getODUForGroup --> " + getODUForGroup);

	}

	@Test
	public void testgetODUParams() throws Exception {
		Locale locale = Locale.ENGLISH;
		String getODUParams = mockMvc
				.perform(
						get("/acconfig/getODUParams.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "18")
								// .param("params", "V1,V2,V3")
								// .param("oduType", "VHP")
								.param("params", "").param("idType", "VRF"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getODUParams ---> " + getODUParams);

	}

	@Test
	public void testgetODUParameterList() throws Exception {
		Locale locale = Locale.ENGLISH;
		String getODUParamsList = mockMvc
				.perform(
						get("/acconfig/getODUParameterList.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("idType", "GHP"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getODUParamsList ---> " + getODUParamsList);

	}
}

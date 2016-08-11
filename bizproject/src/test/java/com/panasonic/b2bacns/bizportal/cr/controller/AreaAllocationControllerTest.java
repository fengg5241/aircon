/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
import com.panasonic.b2bacns.bizportal.cr.vo.IDUAreaMapping;
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
public class AreaAllocationControllerTest extends TestCase {

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
		sessionInfo.setUserId(1l);
		sessionInfo.setLastSelectedGroupID(7l);
		CommonUtil.setSessionInfo(session, sessionInfo);
		// session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	@Test
	// @Rollback(value = false)
	public void testGetAllocatedAreas() throws UnsupportedEncodingException,
			JsonProcessingException, Exception {

		String response = mockMvc
				.perform(
						get("/cr/aa/getAllocatedAreas.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession()).param("siteId", "8")
								.param("distributionId", "1"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("getAllocatedAreas --  " + response);
	}

	@Test
	@Rollback(value = false)
	public void testCreateArea() throws UnsupportedEncodingException,
			JsonProcessingException, Exception {

		String response = mockMvc
				.perform(
						post("/cr/aa/createArea.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession()).param("siteId", "8")
								.param("distributionId", "2")
								.param("areaName", "エリア15"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("createArea --  " + response);

	}

	@Test
	@Rollback(value = false)
	public void testDeleteArea() throws UnsupportedEncodingException,
			JsonProcessingException, Exception {

		String response = mockMvc
				.perform(
						post("/cr/aa/removeArea.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession()).param("siteId", "8")
								.param("distributionId", "2")
								.param("areaName", "area23"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("deleteArea --  " + response);

	}

	@Test
	@Rollback(value = false)
	public void testUpdateIndoorUnitAreaMapping()
			throws UnsupportedEncodingException, JsonProcessingException,
			Exception {

		List<IDUAreaMapping> areaMappingList = new ArrayList<>();

		IDUAreaMapping ampp1 = new IDUAreaMapping(87l, 4l);
		IDUAreaMapping ampp2 = new IDUAreaMapping(88l, 2l);
		IDUAreaMapping ampp3 = new IDUAreaMapping(89l, 6l);
		//IDUAreaMapping ampp4 = new IDUAreaMapping(66l, 4l);
		//IDUAreaMapping ampp5 = new IDUAreaMapping(117l, 5l);

		areaMappingList.add(ampp1);
		areaMappingList.add(ampp2);
		areaMappingList.add(ampp3);
		//areaMappingList.add(ampp4);
		//areaMappingList.add(ampp5);

		Map<String, List<IDUAreaMapping>> input = new HashMap<>();
		input.put("areaMappingList", areaMappingList);

		System.out.println("updateIndoorUnitAreaMapping --"
				+ CommonUtil.convertFromEntityToJsonStr(areaMappingList));

		String response = mockMvc
				.perform(
						post("/cr/aa/updateIDUAreaMapping.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession())
								.param("areaMappingList",
										CommonUtil
												.convertFromEntityToJsonStr(areaMappingList)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("updateIndoorUnitAreaMapping --  " + response);

	}

	@Test
	@Rollback(value = false)
	public void testIsAreaAssigned() throws UnsupportedEncodingException,
			JsonProcessingException, Exception {

		String response = mockMvc
				.perform(
						get("/cr/aa/isAreaAssigned.htm")
								.header("referer", "/home.htm")
								.accept("application/json")
								.session(getSession()).param("areaId", "23"))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("isAreaAssigned --  " + response);

	}

}

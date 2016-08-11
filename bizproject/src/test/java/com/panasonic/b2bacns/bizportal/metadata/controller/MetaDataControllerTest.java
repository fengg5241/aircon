package com.panasonic.b2bacns.bizportal.metadata.controller;

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
import com.panasonic.b2bacns.bizportal.login.controller.LoginController;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * The class <code>LoginControllerTest</code> contains tests for the class
 * <code>{@link LoginController}</code>.
 * 
 * @generatedBy CodePro at 20/7/15 3:40 PM, using the Spring generator
 * @author ashish.verma
 * @version $Revision: 1.0 $
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class MetaDataControllerTest extends TestCase {

	@Autowired
	MetaDataController metaDataController;
	
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
	 * Run the MetaDataController() constructor test.
	 * 
	 * @generatedBy CodePro at 20/7/15 3:40 PM
	 */
	
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
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}
	

	@Test
	public void testODUMetaData() throws Exception {
		Locale locale = Locale.ENGLISH;

		String getODUMetaData = mockMvc
				.perform(
						get("/metadata/getOutDoorMetaData.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "15")
								.param("idType", BizConstants.ID_TYPE_OUTDOOR))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("testODUMetaData - " + getODUMetaData);

		
	}

	

	@Test
	public void testIDUMetaData() throws Exception {
		Locale locale = Locale.ENGLISH;

		String getIDUMetaData = mockMvc
				.perform(
						get("/metadata/getIndoorMetaData.htm")
								.header("referer", "/dashboard/*.htm")
								.accept("application/json")
								.session(getSession()).locale(locale)
								.param("id", "26")
								.param("idType", BizConstants.ID_TYPE_INDOOR))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();
		System.out.println("getIDUMetaData - " + getIDUMetaData);

	}
	

}

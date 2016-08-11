package com.panasonic.b2bacns.bizportal.login.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;

/**
 * The class <code>LoginControllerTest</code> contains tests for the class
 * <code>{@link LoginController}</code>.
 * 
 * @author simanchal.patra
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
public class LoginControllerTest extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		assertNotNull("Mock MVC cannot be initialized", mockMvc);
	}

	/**
	 * Simulates the user login process with correct UserName and Password
	 * 
	 * @throws Exception
	 * 
	 * 
	 */

	@Test
	// @Rollback(value = false)
	public void testLoginSuccessful() throws Exception {

		Locale locale = Locale.ENGLISH;

		String loginId = "KKKKKKK";
		String password = "yZjUdL94u6BB9BL";

		HttpSession newSession = mockMvc
				.perform(
						post("/login/loginProcess.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(new MockHttpSession()).locale(locale)
								.param("loginId", loginId)
								.param("password", password)
								.param("userTimeZone", "Asia/Kolkata"))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("./../home/homeScreen.htm"))
				.andReturn().getRequest().getSession();

		Assert.assertNotNull(newSession);

		Assert.assertNotNull(newSession.getAttribute("sessionInfo"));

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(newSession);

		System.out.println("UserRole - " + sessionInfo.getUserRole());
		System.out.println("RoleId - " + sessionInfo.getUserRoleId());
		System.out.println("CompanyId -" + sessionInfo.getCompanyId());
		System.out.println("PermissionsList -"
				+ sessionInfo.getPermissionsList());
	}

	@Test
	// @Rollback(value = false)
	public void testLoginSuccessfulFirstTimeUser() throws Exception {

		Locale locale = Locale.ENGLISH;
		String emailConfirmation = "";
		String loginId = "LLLLLLL";
		String password = "yZjUdL94u6BB9BL";

		HttpSession newSession = mockMvc
				.perform(
						post("/login/loginProcess.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(new MockHttpSession()).locale(locale)
								.param("loginId", loginId)
								.param("password", password)
								.param("userTimeZone", "Asia/Kolkata")
								.param("confirmation", emailConfirmation))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("./../login/firstLogin.htm"))
				.andReturn().getRequest().getSession();

		Assert.assertNotNull(newSession);

		Assert.assertNotNull(newSession.getAttribute("sessionInfo"));

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(newSession);

		System.out.println(sessionInfo.getUserRole());
		System.out.println(sessionInfo.getUserRoleId());
		System.out.println(sessionInfo.getMenuList());
		System.out.println(sessionInfo.getPermissionsList());
	}

	/**
	 * Simulates the user login process with Invalid UserName and Password
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@Test
	@Rollback(value = false)
	public void testLoginAuthenticationFailed() throws Exception {

		Locale locale = Locale.ENGLISH;

		String loginId = "testashish";
		String password = "Rsystems@112c";

		MvcResult result2 = mockMvc
				.perform(
						post("/login/loginProcess.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(new MockHttpSession()).locale(locale)
								.param("loginId", loginId)
								.param("password", password)
								.param("userTimeZone", "Asia/Kolkata"))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/login/loginPage.htm")).andReturn();

		assertNotNull("ModelAndView should not be null", result2);

		ModelAndViewAssert.assertViewName(result2.getModelAndView(),
				"redirect:/login/loginPage.htm");

		// assertEquals("failed.signed.in",
		// result2.getFlashMap().get("errorMessage"));
		System.out.println(result2.getFlashMap().get("errorMessage"));

	}

	// // @Test
	// // @Rollback(value = false)
	// public void testUnlockAccount() throws Exception {
	//
	// Locale locale = Locale.ENGLISH;
	// String emailConfirmation = "";
	// String loginId = "DDDDDDD";
	// String password = "Rsystems@1";
	//
	// MvcResult result = mockMvc
	// .perform(
	// post("/login/loginProcess.htm").accept("text/html")
	// .header("Referer", "loginPage.htm")
	// .session(new MockHttpSession()).locale(locale)
	// .param("loginId", loginId)
	// .param("password", password)
	// .param("userTimeZone", "Asia/Kolkata")
	// .param("confirmation", emailConfirmation))
	// .andExpect(status().is(302))
	// .andExpect(redirectedUrl("/login/loginPage.htm")).andReturn();
	//
	// assertNotNull("ModelAndView should not be null", result);
	//
	// ModelAndViewAssert.assertViewName(result.getModelAndView(),
	// "redirect:/login/loginPage.htm");
	// if (result.getFlashMap().get("errorMessage") != null) {
	// assertEquals("account.lock.for.one.hour",
	// result.getFlashMap().get("errorMessage"));
	// System.out.println(result.getFlashMap().get("errorMessage"));
	// }
	// }

	/**
	 * Simulates the user login process with Empty UserName and Password
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@Test
	public void testLoginEmptyForm() throws Exception {

		Locale locale = Locale.ENGLISH;
		String email = "";
		String password = "";

		MvcResult result2 = mockMvc
				.perform(
						post("/login/loginProcess.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(new MockHttpSession()).locale(locale)
								.param("email", email)
								.param("password", password))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/login/loginPage.htm")).andReturn();

		assertNotNull("ModelAndView should not be null", result2);

		ModelAndViewAssert.assertViewName(result2.getModelAndView(),
				"redirect:/login/loginPage.htm");

		assertEquals(
				((BindingResult) result2.getFlashMap()
						.get("loginBindingResult")).getErrorCount(), 2);

		System.out.println(((BindingResult) result2.getFlashMap().get(
				"loginBindingResult")).getFieldError("email"));

		System.out.println(((BindingResult) result2.getFlashMap().get(
				"loginBindingResult")).getFieldError("password"));
	}

	/**
	 * Simulates the user login process where user have a valid Session Present
	 * and user tries to load the login page from same browser
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@Test
	public void testReLoginValidSessionExists() throws Exception {

		Locale locale = Locale.ENGLISH;

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		CommonUtil.setSessionInfo(session, sessionInfo);

		HttpSession newSession = mockMvc
				.perform(get("/login/loginPage.htm").accept("text/html")
				// .header("Referer", "loginPage.htm")
						.session(session).locale(locale))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("../home/homeScreen.htm")).andReturn()
				.getRequest().getSession();

		Assert.assertNotNull(newSession);

		Assert.assertNotNull(newSession.getAttribute("sessionInfo"));
		System.out.println(newSession.getAttribute("sessionInfo"));

	}

	/**
	 * Simulates the process where clicks the Logout Button
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testLogoutProcess() throws Exception {

		Locale locale = Locale.ENGLISH;

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		sessionInfo.setLoginId("LLLLLLL");
		CommonUtil.setSessionInfo(session, sessionInfo);

		MvcResult result2 = mockMvc
				.perform(
						get("/login/logout.htm").accept("text/html")
								.header("Referer", "viewDashboard.htm")
								.session(session).locale(locale))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/login/loginPage.htm")).andReturn();

		assertNotNull("ModelAndView should not be null", result2);

		ModelAndViewAssert.assertViewName(result2.getModelAndView(),
				"redirect:/login/loginPage.htm");
		System.out.println(result2.getModelAndView());
	}

	/**
	 * Simulates the process where user session is expired
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogoutWhenSessionExpired() throws Exception {

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);		
		sessionInfo.setLoginId("LLLLLLL");
		CommonUtil.setSessionInfo(session, sessionInfo);
		session.invalidate(); // expires the session

		mockMvc.perform(
				get("/home/homeScreen.htm").accept("text/html")
						.header("Referer", "loginProcess.htm").session(session)
						.locale(Locale.ENGLISH)).andExpect(status().is(302))
				.andExpect(redirectedUrl("../login/sessionExpired.htm"))
				.andReturn();
		// System.out.println(result2.getRequest().getSession().getAttribute("sessionInfo"));

	}

	@Test
	public void testCreateLoginPassword() throws Exception {
		System.out.println(PasswordEncryptionDecryption
				.getEncryptedPassword("s7p3radm8n"));
	}

	@Test
	// @Rollback(false)
	public void testFirstLogin() throws Exception {

		Locale locale = Locale.ENGLISH;

		String currentLoginId = "LLLLLLL";
		String newLoginId = "FirstTimeUserIDChanged";
		String currentPassword = "yZjUdL94u6BB9BL";
		String newPassword = "Rsystems@1";
		String confirmPassword = "Rsystems@1";
		String email = "nitin.verma1@rsystems.com";
		String telephone = "9990967637";

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		CommonUtil.setSessionInfo(session, sessionInfo);

		MvcResult result = mockMvc
				.perform(
						post("/login/firstLogin.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(session).locale(locale)
								.param("currentLoginId", currentLoginId)
								.param("newLoginId", newLoginId)
								.param("currentPassword", currentPassword)
								.param("newPassword", newPassword)
								.param("confirmPassword", confirmPassword)
								.param("email", email)
								.param("telephone", telephone)
								.param("locale", ""))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/login/loginPage.htm")).andReturn();

		// assertEquals(
		// ((BindingResult) result.getFlashMap().get("loginBindingResult"))
		// .getErrorCount(), 0);

		System.out
				.println(result.getFlashMap().get("loginBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("loginBindingResult"))
						.getFieldError("currentPassword")
						: "No Validation Error");

		System.out
				.println(result.getFlashMap().get("loginBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("loginBindingResult"))
						.getFieldError("newPassword") : "No Validation Error");

		System.out
				.println(result.getFlashMap().get("loginBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("loginBindingResult"))
						.getFieldError("email") : "No Validation Error");

		assertNotNull("ModelAndView should not be null", result);

		ModelAndViewAssert.assertViewName(result.getModelAndView(),
				"redirect:/login/loginPage.htm");

	}

	@Test
	// @Rollback(false)
	public void testFirstLoginFailed() throws Exception {

		// New User ID already exists

		Locale locale = Locale.ENGLISH;

		String currentLoginId = "LLLLLLL";
		String newLoginId = "KKKKKKK";
		String currentPassword = "yZjUdL94u6BB9BL";
		String newPassword = "Rsystems@1KKKKKKK";
		String confirmPassword = "Rsystems@1KKKKKKK";
		String email = "nitin.verma1@rsystems.com";
		String telephone = "9990967637";

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		CommonUtil.setSessionInfo(session, sessionInfo);

		ResultActions resultAction = mockMvc.perform(
				post("/login/firstLogin.htm").accept("text/html")
						.header("Referer", "loginPage.htm").session(session)
						.locale(locale).param("currentLoginId", currentLoginId)
						.param("newLoginId", newLoginId)
						.param("currentPassword", currentPassword)
						.param("newPassword", newPassword)
						.param("confirmPassword", confirmPassword)
						.param("email", email).param("telephone", telephone)
						.param("locale", "")).andExpect(status().is(302));

		MvcResult result = resultAction.andExpect(
				redirectedUrl("./../login/firstLogin.htm")).andReturn();

		assert (((BindingResult) result.getFlashMap().get("loginBindingResult"))
				.getErrorCount() > 0);

		System.out
				.println(result.getFlashMap().get("loginBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("loginBindingResult"))
						.getFieldError("currentPassword")
						: "No Validation Error");

		System.out
				.println(result.getFlashMap().get("loginBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("loginBindingResult"))
						.getFieldError("newPassword") : "No Validation Error");

		System.out
				.println(result.getFlashMap().get("loginBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("loginBindingResult"))
						.getFieldError("email") : "No Validation Error");

		assertNotNull("ModelAndView should not be null", result);

	}

	@Test
	// @Rollback(value = false)
	public void testChangePassword() throws Exception {

		Locale locale = Locale.ENGLISH;

		String currentLoginId = "KKKKKKK";
		String currentPassword = "yZjUdL94u6BB9BL";
		String newPassword = "Rsystems@2";
		String confirmPassword = "Rsystems@2";

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		CommonUtil.setSessionInfo(session, sessionInfo);

		MvcResult result = mockMvc
				.perform(
						post("/login/changePassword.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(session).locale(locale)
								.param("loginId", currentLoginId)
								.param("currentPassword", currentPassword)
								.param("newPassword", newPassword)
								.param("confirmPassword", confirmPassword)
								.param("locale", ""))
				.andExpect(status().is(302)).andReturn(); // andExpect(redirectedUrl("/login/loginPage.htm"))

		System.out
				.println(result.getFlashMap()
						.get("ChangePasswordBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("ChangePasswordBindingResult"))
						.getFieldError("currentPassword")
						: "No Validation Error");

		System.out
				.println(result.getFlashMap()
						.get("ChangePasswordBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("ChangePasswordBindingResult"))
						.getFieldError("newPassword") : "No Validation Error");

		System.out
				.println(result.getFlashMap()
						.get("ChangePasswordBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("ChangePasswordBindingResult"))
						.getFieldError("email") : "No Validation Error");

		assertNotNull("ModelAndView should not be null", result);

		ModelAndViewAssert.assertViewName(result.getModelAndView(),
				"redirect:./../login/loginPage.htm");

	}

	@Test
	// @Rollback(value =false)
	public void testChangePasswordWrongUserNameAndPassword() throws Exception {

		Locale locale = Locale.ENGLISH;

		String currentLoginId = "NitinVerma123";
		String currentPassword = "Rsystems@123";
		// String currentLoginId = "NitinVerma111";
		// String currentPassword = "Rsystems@1";
		String newPassword = "Rsystems@1234";
		String confirmPassword = "Rsystems@1234";

		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setCompanyId(1l);
		CommonUtil.setSessionInfo(session, sessionInfo);

		MvcResult result = mockMvc
				.perform(
						post("/login/changePassword.htm").accept("text/html")
								.header("Referer", "loginPage.htm")
								.session(session).locale(locale)
								.param("loginId", currentLoginId)
								.param("currentPassword", currentPassword)
								.param("newPassword", newPassword)
								.param("confirmPassword", confirmPassword)
								.param("locale", ""))
				.andExpect(status().is(302)).andReturn();

		if (result.getFlashMap().get("currentPassword") != null) {
			assertEquals("currentPassword",
					result.getFlashMap().get("currentPassword"));
			System.out.println(result.getFlashMap().get("currentPassword"));
		}

		System.out
				.println(result.getFlashMap()
						.get("ChangePasswordBindingResult") != null ? ((BindingResult) result
						.getFlashMap().get("ChangePasswordBindingResult"))
						.getFieldError("currentPassword")
						: "No Validation Error");

		assertNotNull("ModelAndView should not be null", result);

		System.out.println("result value ###### " + result.getModelAndView());

		ModelAndViewAssert.assertViewName(result.getModelAndView(),
				"redirect:./../login/showChangePassword.htm");

	}

	private MockHttpServletRequestBuilder post(String uri) {
		return MockMvcRequestBuilders.post(uri);
	}

	private MockHttpServletRequestBuilder get(String uri) {
		return MockMvcRequestBuilders.get(uri);
	}

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}

	private ResultMatcher redirectedUrl(String expectedUrl) {
		return MockMvcResultMatchers.redirectedUrl(expectedUrl);
	}

}

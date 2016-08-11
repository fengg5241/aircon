package com.panasonic.b2bacns.bizportal.login.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
// import com.panasonic.b2bacns.bizportal.dashboard.service.ManageDashboardService;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean;
import com.panasonic.b2bacns.bizportal.login.form.LoginFormBean;
import com.panasonic.b2bacns.bizportal.login.validator.FirstLoginValidator;
import com.panasonic.b2bacns.bizportal.login.validator.LoginValidator;
import com.panasonic.b2bacns.bizportal.login.vo.LoginVO;
import com.panasonic.b2bacns.bizportal.service.ManageLoginService;
import com.panasonic.b2bacns.bizportal.service.UserAuditService;
import com.panasonic.b2bacns.bizportal.useraudit.service.AuditManagementService;
import com.panasonic.b2bacns.bizportal.usermanagement.changePassword.ChangePasswordFormBean;
import com.panasonic.b2bacns.bizportal.usermanagement.changePassword.ChangePasswordValidator;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * The APIs which are required to Login to the System & Logout from the system.
 * This also keeps track of the user's failed Login attempts and Locks the
 * account after 6th consecutive failed attempt.
 * 
 * @author simanchal.patra
 * @author kumar.madhukar
 */
@Controller
@RequestMapping(value = "/login")
@SessionAttributes
public class LoginController {

	private static final Logger logger = Logger
			.getLogger(LoginController.class);

	private static final String ATTR_IS_SESSION_VALID = "isSessionValid";

	private static final String FALSE = "false";

	// private static final String CACHED_SESSION_MAP_NAME = "cachedSessionMap";

	@Autowired
	private ManageLoginService loginService;

	@Autowired
	private LoginValidator validator;

	@Autowired
	private AuditManagementService auditManagementService;

	@Autowired
	private UserAuditService userAuditService;

	@Autowired
	ChangePasswordValidator changePasswordValidator;

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource properties;

	@Resource(name = "properties")
	private Properties bizProperties;

	@Autowired
	private FirstLoginValidator firstLoginValidator;

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource messageSource;

	/**
	 * This API authenticates the User. If user authentication is successful
	 * then it redirects user to view Dashboard.
	 * 
	 * @param loginFormBean
	 *            Login form data, provided by User
	 * @param result
	 *            Binding result errors, if any
	 * @param request
	 *            The HTTP request associated with user action
	 * @param response
	 *            The HTTP response as a result generated for user action
	 * @param redirectAttributes
	 *            Attributes to be pass to the next action to process in case of
	 *            redirection
	 * @param locale
	 *            User locale
	 * @return A ModelAndView depending upon the successfulness of user
	 *         authentication
	 * @throws BusinessFailureException
	 */
	@RequestMapping(value = "/loginProcess.htm", method = { RequestMethod.POST })
	public ModelAndView loginProcess(
			@ModelAttribute("loginFormBean") LoginFormBean loginFormBean,
			final BindingResult result, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes, Locale locale,
			TimeZone timeZone) {

		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

		LoginVO loginVO = null;

		logger.debug(String.format("User [%s] requested to authenticate",
				loginFormBean.getLoginId()));

		ModelAndView modelAndView = new ModelAndView(
				"redirect:/login/loginPage.htm");

		try {
			if (sessionInfo == null) {

				validator.validate(loginFormBean, result);

				if (!result.hasErrors()) {

					loginVO = populateLoginVO(loginFormBean);

					loginVO = loginService.authenticateUser(loginVO, locale);

					if (loginVO.getErrorMessage() == null) {

						logger.debug(String
								.format("User having User ID [%s] authenticated successfully",
										loginVO.getUserId()));

						deactivateUserSessions(request, loginVO.getLoginId());

						session = createNewSession(request,
								loginVO.getLoginId(), session);

						sessionInfo = saveUserSession(request, session,
								loginVO, loginFormBean.getUserTimeZone());

						// retrieving functional group and role type
						loginService.getFunctionalGroupAndRoleType(loginVO,
								sessionInfo);
						
						//add by jwchan for quick fix for do not update the lastLogin Date to prevent user skipped the firstLogin process
						logger.debug("Login user: "+ loginVO.isFirstLogin());
						if (loginVO.getLastLoginDate() != null) {
							if(!loginVO.isFirstLogin()){
							loginVO.setLastLoginDate(CommonUtil
									.setUserTimeZone(Calendar.getInstance(),
											loginFormBean.getUserTimeZone())
									.getTime());

							loginService.resetFailAttempt(
									BizConstants.FAIL_RESET, loginVO);
							//add by shanf for quick fixing
							List<String> permissionsNameList = sessionInfo.getPermissionNameList();
							String redirectUrl = "redirect:./../home/homeScreen.htm";
							if (permissionsNameList != null && !permissionsNameList.contains("navi-home")) {
								redirectUrl = "redirect:./../acconfig/viewAcConfig.htm";
							}
							
							modelAndView = new ModelAndView(redirectUrl);
							}
						} else {

							/*
							 * loginVO.setLastLoginDate(CommonUtil
							 * .setUserTimeZone(Calendar.getInstance(),
							 * loginFormBean.getUserTimeZone()) .getTime());
							 */

							loginService.resetFailAttempt(
									BizConstants.FAIL_RESET, loginVO);

							modelAndView = new ModelAndView(
									"redirect:./../login/firstLogin.htm");
						}
					} else {
						logger.debug(String.format(
								"User [%s] authentication failed",
								loginVO.getLoginId()));

						loginService.resetFailAttempt(
								BizConstants.FAIL_INCREMENT, loginVO);

						redirectAttributes.addFlashAttribute(
								"redirectErrorMessage",
								loginVO.getErrorMessage());
					}
				} else {
					if (request.getHeader("Referer") != null) {
						redirectAttributes.addFlashAttribute(
								"loginBindingResult", result);
						redirectAttributes.addFlashAttribute("loginFormBean",
								loginFormBean);

					}
				}
			} else {
				loginFormBean = populateFormBean(loginFormBean, sessionInfo);
				if (sessionInfo.getUserRole() != null) {
					

					loginVO = populateLoginVO(loginFormBean);
					loginVO = loginService.authenticateUser(loginVO, locale);
					
					//add by jwchan for quick fixing to disable firstLogin user to login to home screen without proceed firstlogin form
					logger.debug("Login user: " + loginVO.isFirstLogin());
					if(!loginVO.isFirstLogin()){
						//add by shanf for quick fixing
						List<String> permissionsNameList = sessionInfo.getPermissionNameList();
						String redirectUrl = "redirect:./../home/homeScreen.htm";
						if (permissionsNameList != null && !permissionsNameList.contains("navi-home")) {
							redirectUrl = "redirect:./../acconfig/viewAcConfig.htm";
						}
						
						modelAndView = new ModelAndView(redirectUrl);
					}else{
						modelAndView = new ModelAndView("redirect:./../login/firstLogin.htm");
					}
						
				} else {
					modelAndView = new ModelAndView(
							"redirect:/login/sessionExpired.htm");
				}

			}
		} catch (BusinessFailureException bfe) {

			deactivateUserSessions(request, loginVO.getLoginId());

			redirectAttributes.addFlashAttribute("errorMessage",
					bfe.getMessage());

			redirectAttributes.addFlashAttribute("loginid",
					loginFormBean.getLoginId());

			if (bfe.getMessage().equals("failed.signed.in")) {

				logger.debug(String.format("User [%s] authentication failed",
						loginVO.getLoginId()));

				modelAndView.setViewName("redirect:/login/loginPage.htm");

			} else if (bfe.getMessage().equals(
					"password.expired.change.password")) {

				redirectAttributes.addFlashAttribute("isPasswordExpired", true);
				//Modified by seshu.
				session = createNewSession(request,
						loginVO.getLoginId(), session);

				sessionInfo = saveUsersSessions(request, session,
						loginVO, loginFormBean.getUserTimeZone());
				redirectAttributes.addFlashAttribute("redirect", true);
				modelAndView
						.setViewName("redirect:/login/showChangePassword.htm");

				logger.debug("User password has expired, having Login-ID - "
						+ loginVO.getLoginId());
			} else if (bfe.getMessage().equals("account.lock.for.one.hour")) {

				redirectAttributes.addFlashAttribute("isLocked", true);

				modelAndView.setViewName("redirect:/login/loginPage.htm");

				logger.info("User account is locked for 1 hour, having Login-ID - "
						+ loginVO.getLoginId());
			} else {

				logger.debug("Some Business failed, error is "
						+ bfe.getMessage());

				try {
					loginService.resetFailAttempt(BizConstants.FAIL_INCREMENT,
							loginVO);
				} catch (BusinessFailureException bfe2) {
					redirectAttributes.addFlashAttribute("errorMessage",
							bfe.getMessage());
					logger.error(String.format(
							"Error occurred while processing the"
									+ " login activity of the user [%s]",
							loginFormBean.getLoginId()), bfe2);
				}

				modelAndView.setViewName("redirect:/login/loginPage.htm");
			}
		} catch (Exception ex) {
			logger.error(
					String.format(
							"Error occurred while processing the login activity of the user [%s]",
							loginFormBean.getLoginId()), ex);
		}
		return modelAndView;
	}

	/**
	 * Creates the SessionInfo for that user and Caches that session
	 * 
	 * @param request
	 *            The HTTP Request
	 * @param session
	 *            The HTTP session
	 * @param loginVO
	 *            The login related data fetched from DB
	 * 
	 * @return The SessionInfo
	 */
	private SessionInfo saveUserSession(HttpServletRequest request,
			HttpSession session, LoginVO loginVO, String userTimeZone) {

		SessionInfo sessionInfo = null;

		try {
			loginService.saveUserSession(loginVO.getLoginId(), session.getId());

			sessionInfo = populateSessionInfo(loginVO, userTimeZone);

			CommonUtil.setSessionInfo(session, sessionInfo);

			saveSessionIntoCache(request, session);
		} catch (Exception e) {
			logger.error("Exception occured in saveUserSession ", e);
		}

		return sessionInfo;
	}

	/**
	 * Creates a new HTTP session for that user and invalidates the existing
	 * session. But it maintains all the session attributes in new session which
	 * were present in existing session.
	 * 
	 * @param request
	 *            The HTTP request
	 * @param userLoginID
	 *            The registered Login ID of the user
	 * @param session
	 *            The HTTP session
	 * @return A newly created HTTP Session
	 */
	private HttpSession createNewSession(HttpServletRequest request,
			String userLoginID, HttpSession session) {

		Map<String, Object> old = new HashMap<String, Object>();

		try {

			Enumeration<String> sessionAttributeNames = (Enumeration<String>) session
					.getAttributeNames();

			while (sessionAttributeNames.hasMoreElements()) {
				String key = (String) sessionAttributeNames.nextElement();
				old.put(key, session.getAttribute(key));
				session.removeAttribute(key);
			}

			// invalidation session and create new session.
			session.invalidate();
		} catch (IllegalStateException ilgstExp) {
			// just Ignore, invalidating the session which is Invalid again
			// this is just for safer side
		} catch (Exception e) {
			logger.error("Exception occured in createNewSession ", e);
		} finally {

			try {
				session = request.getSession(true);

				// copy key value pairs from map to new session.
				if (old.size() > 0) {
					for (Map.Entry<String, Object> entry : old.entrySet()) {
						session.setAttribute((String) entry.getKey(),
								entry.getValue());
					}
				}
				logger.debug(String.format(
						"New session created for user [%s], session ID [%s] ",
						userLoginID, session.getId()));
			} catch (Exception e2) {
				logger.error("Exception occured in createNewSession ", e2);
			}
		}

		return session;
	}

	
	//Added by seshu.( populate vo)
	private SessionInfo saveUsersSessions(HttpServletRequest request,
			HttpSession session, LoginVO loginVO, String userTimeZone) {

		SessionInfo sessionInfo = null;

		try {
			loginService.saveUserSession(loginVO.getLoginId(), session.getId());

			sessionInfo = populateSessionsInfo(loginVO, userTimeZone);

			CommonUtil.setSessionInfo(session, sessionInfo);
			saveSessionIntoCache(request, session);
		} catch (Exception e) {
			logger.error("Exception occured in saveUserSession ", e);
		}

		return sessionInfo;
	}
	
	//Added by seshu.(populate vo)
	private SessionInfo populateSessionsInfo(LoginVO loginVO, String userTimeZone) {

		SessionInfo sessionInfo = new SessionInfo();		
		sessionInfo.setLoginId(loginVO.getLoginId());
		sessionInfo.setMenuList(loginVO.getMenuList());
		sessionInfo.setPermissionsList(loginVO.getPermissionsList());		
		sessionInfo.setPermissionNameList(loginVO.getPermissionNameList());
		sessionInfo.setUserRole(loginVO.getUserRole());
		sessionInfo.setUserId(loginVO.getUserId());
		sessionInfo.setUserRoleId(loginVO.getUserRoleId());
		sessionInfo.setImagePath((String) bizProperties.get("imagePath"));
		sessionInfo.setFirstLogin(loginVO.isFirstLogin());
		sessionInfo.setUserTimeZone(userTimeZone);
		sessionInfo.setCompanyId(loginVO.getCompanyId());				
		return sessionInfo;
	}
	
	
	
	/**
	 * Invalidates the current HTTP session, and removes it from cache.
	 * 
	 * @param request
	 *            The HTTP request
	 * @param userLoginID
	 *            The registered Login ID of the user
	 */
	private void deactivateUserSessions(HttpServletRequest request,
			String userLoginID) {

		try {
			// Check if user is Already have another session

			List<String> previousSessions = loginService
					.getUserActiveSessions(userLoginID);

			if (previousSessions.size() > 0) {

				logger.debug(String.format(
						"[%d] number of Active sessions found for User [%s]",
						previousSessions.size(), userLoginID));

				for (String sessionID : previousSessions) {

					HttpSession session = getCachedSession(request, sessionID);

					if (session != null) {
						try {
							//add by seshu
							//session.removeAttribute(BizConstants.SESSION_INFO_OBJECT_NAME);
							// may be session already expired or invalidated,
							// but to double confirm
							session.invalidate();
						} catch (Exception e) {
							// If the session is already invalidated, then it
							// throws exception, so no need to log this
							// exception
							logger.error(
									"Exception occured in deactivateUserSessions, "
											+ "may be already invalidated - "
											+ session != null ? session.getId()
											: "", e);
						}
						logger.debug(String
								.format("Terminated Active session with ID [%s] of User [%s]",
										session.getId(), userLoginID));

						deleteSessionFromCache(request, sessionID);
					} else if (userLoginID != null){ // Added by Ravi
						session = request.getSession(true);
						session.setAttribute(BizConstants.SESSION_INFO_OBJECT_NAME, null);
					} 
				}
				// deactivate in database
				loginService.deleteUserSession(userLoginID);
			} else if (userLoginID != null){ // Added by ravi
				HttpSession session = request.getSession(true);
				session.setAttribute(BizConstants.SESSION_INFO_OBJECT_NAME, null);
			} else {
				logger.debug(String.format(
						"No Active sessions found for User [%s]", userLoginID));
			}
		} catch (Exception e) {
			logger.error("Exception occured in deactivateUserSessions ", e);
		} finally {

			HttpSession currentSession = request.getSession(false);
			if (currentSession != null) {
				logger.debug(String.format(
						"DeActivating current sessions, ID [%s] of User [%s]",
						currentSession.getId(),
						userLoginID != null ? userLoginID
								: BizConstants.EMPTY_STRING));
				currentSession.invalidate();
			}
		}
	}

	/**
	 * Logout the user from the System and invalidates user's session.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public ModelAndView logoutProcess(HttpServletRequest request,
			HttpServletResponse response) {
		// Modified By Ravi
		String viewPage = "redirect:/login/loginPage.htm";
		ModelAndView modelAndView = new ModelAndView(viewPage);
		HttpSession session = request.getSession(false);
		SessionInfo sessionInfo = null;
		try {
			if (session != null) {

				sessionInfo = CommonUtil.getSessionInfo(session);

				if (sessionInfo != null) {

					logger.debug(String.format("User [%s] requested to LogOut",
							sessionInfo.getLoginId()));

					// deactivate session
					deactivateUserSessions(request, sessionInfo.getLoginId());
				} else {
					logger.debug(String.format(
							"A User current session [%s] without"
									+ " SessionInfo Deactivated",
							session.getId()));
					session.invalidate();
				}
			} else {
				viewPage = "redirect:/login/sessionExpired.htm";
			}
		} catch (Exception ex) {
			logger.error(
					"Error occurred while processing the logout of the user. User info is : "
							+ sessionInfo.getLoginId(), ex);
		} finally {
			session = request.getSession(true);
			logger.debug("A new session created for anonymous user who requested logout");
			modelAndView.getModelMap().addAttribute("loginFormBean",
					new LoginFormBean());
		}
		return modelAndView;
	}

	/**
	 * Shows the Login (should be Home) page through which user can Login into
	 * the system, Change Password, Unlock Account, request to Resend Unlock
	 * Instructions.
	 * 
	 * @param loginFormBean
	 *            The LoginFormBean
	 * @param result
	 *            The BindingResult for LoginFormBean
	 * @param request
	 *            The HTTP request associated with user action
	 * @param response
	 *            The HTTP response as a result generated for user action
	 * @param model
	 *            The Model
	 * @param redirectAttributes
	 *            Attributes to be pass to the next action to process in case of
	 *            redirection
	 * @param locale
	 *            Locale chosen by User
	 * 
	 * @return A ModelAndView to show the login page
	 */
	@RequestMapping(value = "/loginPage.htm", method = RequestMethod.GET)
	public ModelAndView showLoginPage(
			@ModelAttribute("loginFormBean") LoginFormBean loginFormBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, Model model,
			final RedirectAttributes redirectAttributes, Locale locale) {

		ModelAndView modelAndView = new ModelAndView();

		try {
			String viewPage = "/login/loginPage";
			HttpSession session = request.getSession(false);
			if (session != null && CommonUtil.getSessionInfo(session) != null) {
				viewPage = "redirect:loginProcess.htm";
			}

			if (model.asMap().containsKey("loginBindingResult")) {
				List<String> list = getErrorsFromBindingResult(
						(BindingResult) model.asMap().get("loginBindingResult"),
						loginFormBean);
				loginFormBean.setErrorList(list);
			}

			modelAndView.setViewName(viewPage);
		} catch (Exception e) {
			logger.error("Exception occured in showLoginPage ", e);
		}

		return modelAndView;
	}
	
	//Added by Seshu.
	@RequestMapping(value = "/firstLogin.htm", method = RequestMethod.GET)
	public ModelAndView showFirstLoginPage(
			@ModelAttribute("firstLoginFormBean") FirstLoginFormBean firstLoginFormBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, Model model,
			final RedirectAttributes redirectAttributes, Locale locale) {

		ModelAndView modelAndView = new ModelAndView();

		try {
			String viewPage = "/login/firstLogin";
			modelAndView.setViewName(viewPage);
		} catch (Exception e) {
			logger.error("Exception occured in show First LoginPage ", e);
		}

		return modelAndView;
	}
	
	
	/**
	 * Shows the session Expired Page
	 * 
	 * @param request
	 *            The HTTP request associated with user action
	 * @param response
	 *            The HTTP response as a result generated for user action
	 * 
	 * @return The ModelAndView to show session expired page
	 */
	@RequestMapping(value = "/sessionExpired.htm", method = RequestMethod.GET)
	public ModelAndView showSessionExpiredPage(HttpServletRequest request,
			HttpServletResponse response) {

		String returnResponse = "/login/sessionExpired";
		return new ModelAndView(returnResponse);
	}

	/**
	 * This API to be used to handle back button click by user.
	 * 
	 * @param request
	 *            The HTTP request associated with user action
	 * @param response
	 *            The HTTP response as a result generated for user action
	 * 
	 * @param session
	 *            The HTTP session associated with user's current action
	 * @return
	 */
	@RequestMapping(value = "/backButtonError.htm", method = RequestMethod.GET)
	public ModelAndView processBackButtonError(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		SessionInfo sessionInfo = null;
		String returnResponse = "redirect:sessionExpired.htm";
		if (session != null) {
			sessionInfo = (SessionInfo) CommonUtil.getSessionInfo(session);
			if (sessionInfo != null) {
				LoginVO loginVO = populateLoginVO(sessionInfo);

				deactivateUserSessions(request, loginVO.getLoginId());
			}
		}
		request.setAttribute("backButtoError", true);
		return new ModelAndView(returnResponse);
	}

	/**
	 * This method is used to populate DTO from form bean.
	 * 
	 * @param loginFormBean
	 *            The LoginFormBean
	 * @return The LoginVO
	 */
	private LoginVO populateLoginVO(LoginFormBean loginFormBean) {

		LoginVO loginVO = new LoginVO();
		// loginVO.setEmail(loginFormBean.getEmail().trim());
		loginVO.setLoginId(loginFormBean.getLoginId().trim());
		loginVO.setPassword(loginFormBean.getPassword().trim());

		return loginVO;
	}

	/**
	 * This method is used to populate bean from sessionInfo
	 * 
	 * @param loginFormBean
	 *            The LoginFormBean
	 * @param sessionInfo
	 *            The SessionInfo
	 * @return The LoginFormBean
	 */
	private LoginFormBean populateFormBean(LoginFormBean loginFormBean,
			SessionInfo sessionInfo) {

		// loginFormBean.setEmail(sessionInfo.getEmailId());
		loginFormBean.setLoginId(sessionInfo.getLoginId());
		return loginFormBean;
	}

	/**
	 * This method is used to populate sessionInfo from DTO.
	 * 
	 * @param loginVO
	 *            The LoginVO
	 * @return The SessionInfo
	 */
	private SessionInfo populateSessionInfo(LoginVO loginVO, String userTimeZone) {

		SessionInfo sessionInfo = new SessionInfo();
		// sessionInfo.setEmailId(loginVO.getEmail());
		sessionInfo.setLoginId(loginVO.getLoginId());
		sessionInfo.setMenuList(loginVO.getMenuList());
		sessionInfo.setPermissionsList(loginVO.getPermissionsList());
		//add by shanf
		sessionInfo.setPermissionNameList(loginVO.getPermissionNameList());
		sessionInfo.setUserRole(loginVO.getUserRole());
		sessionInfo.setUserId(loginVO.getUserId());
		sessionInfo.setUserRoleId(loginVO.getUserRoleId());
		sessionInfo.setImagePath((String) bizProperties.get("imagePath"));
		sessionInfo.setFirstLogin(loginVO.isFirstLogin());
		sessionInfo.setUserTimeZone(userTimeZone);
		sessionInfo.setCompanyId(loginVO.getCompanyId());
		Map<Integer, String> roleTypeMap = new HashMap<Integer, String>();
		roleTypeMap.put((int) (long) loginVO.getRole().getRoletype().getId(),
				loginVO.getRole().getRoletype().getRoletypename());
		sessionInfo.setRoleTypeMap(roleTypeMap);

		return sessionInfo;
	}

	/**
	 * This method is used to populate DTO from sessionInfo.
	 * 
	 * @param sessionInfo
	 *            The SessionInfo
	 * @return The LoginVO
	 */
	private LoginVO populateLoginVO(SessionInfo sessionInfo) {

		LoginVO loginVO = new LoginVO();
		// loginVO.setEmail(sessionInfo.getEmailId());
		loginVO.setLoginId(sessionInfo.getLoginId());
		loginVO.setMenuList(sessionInfo.getMenuList());
		loginVO.setUserRole(sessionInfo.getUserRole());

		return loginVO;
	}

	/**
	 * Removes the current session of User, which was cached.
	 * 
	 * @param request
	 *            The HTTP request
	 * @param sessionID
	 *            ID of the Session
	 */
	private void deleteSessionFromCache(HttpServletRequest request,
			String sessionID) {

		HashMap<String, HttpSession> cachedSessionMap = (HashMap<String, HttpSession>) CommonUtil
				.getContextCachedSessionMap(request);

		logger.debug(String.format(
				"Trying to remove Session with ID [%s] from the cache",
				sessionID));

		logger.debug(String
				.format("Before removing Session with ID [%s] from the cache, the cached session list is - {%s} ",
						sessionID, cachedSessionMap));

		HttpSession oldSession = (HttpSession) cachedSessionMap.get(sessionID);

		if (oldSession != null) {
			// already invalidated
			// oldSession.invalidate();
			if (cachedSessionMap.remove(sessionID) != null) {
				CommonUtil
						.setContextCachedSessionMap(request, cachedSessionMap);

				logger.debug(String.format(
						"successfully Removed Session with ID [%s] from cache",
						sessionID));
			}
		}
	}

	/**
	 * This method is used to Get Error codes from binding result and provide
	 * those as a list.
	 * 
	 * @param result
	 *            The BindingResult
	 * @param formBean
	 *            The LoginFormBean
	 * @return List of String
	 */
	private List<String> getErrorsFromBindingResult(BindingResult result,
			LoginFormBean formBean) {

		List<String> list = new ArrayList<String>();

		for (ObjectError object : result.getAllErrors()) {
			if (object instanceof FieldError) {
				list.add(((FieldError) object).getCode());
			}
		}
		return list;
	}

	/**
	 * This caches the user's session in to cachedSessionMap in Servlet Context.
	 * 
	 * @param request
	 *            The HTTP request
	 * @param session
	 *            The Session to be cached.
	 */
	private void saveSessionIntoCache(HttpServletRequest request,
			HttpSession session) {

		HashMap<String, HttpSession> cachedSessionMap = CommonUtil
				.getContextCachedSessionMap(request);

		if (cachedSessionMap == null) {
			cachedSessionMap = new HashMap<String, HttpSession>();
		}

		logger.debug(String.format(
				"Trying to save the session [%s] into the cache",
				session.getId()));

		logger.debug(String.format(
				"Before saving the session [%s], cached list is - %s ",
				session.getId(), cachedSessionMap.keySet()));

		cachedSessionMap.put(session.getId(), session);

		CommonUtil.setContextCachedSessionMap(request, cachedSessionMap);

		logger.debug(String.format(
				"Successfully Saved the session [%s] into the cache",
				session.getId()));
	}

	/**
	 * Provides the cached session associated with the provided ID.
	 * 
	 * @param request
	 *            The HTTP request
	 * @param sessionID
	 *            The ID of the session
	 * 
	 * @return The HTTP session
	 */
	private HttpSession getCachedSession(HttpServletRequest request,
			String sessionID) {

		HttpSession session = null;

		HashMap<String, HttpSession> cachedSessionMap = CommonUtil
				.getContextCachedSessionMap(request);

		if (cachedSessionMap != null) {
			session = cachedSessionMap.get(sessionID);
		}

		return session;
	}

	/**
	 * This sets an attribute "isSessionValid" to "false" in request header to
	 * mark that the session is no longer valid. This attribute should be used
	 * by AJAX calls to redirect to login page to user. A Javascript code which
	 * should be part of all the page or a common page (Header/Footer/Left Menu)
	 * to be included in all JSP pages is as follows -
	 * 
	 * <code>
	 * <pre>
	 * $(document).bind("ajaxComplete", function(event, request, xhr, settings) { 
	 * 	if (request.getResponseHeader('isSessionValid') === 'false') { 
	 * 		window.location.replace("../login/loginPage.htm");
	 * 	}
	 *  }); 
	 * </pre>
	 * </code>
	 * 
	 * @param request
	 *            The HTTP request
	 * @param response
	 *            The HTTP response
	 * @param session
	 *            The HTTP session
	 */
	@RequestMapping(value = "/invalidSession.htm", method = { RequestMethod.GET })
	public void invalidSession(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		response.setHeader(ATTR_IS_SESSION_VALID, FALSE);
	}

	/**
	 * Shows the first Login Page
	 * 
	 * @param request
	 *            The HTTP request associated with user action
	 * @param response
	 *            The HTTP response as a result generated for user action
	 * 
	 * @return The ModelAndView to show first Login Page
	 * @throws IOException
	 */
	@RequestMapping(value = "/firstLoginProcess.htm", method = RequestMethod.POST)
	public ModelAndView firstLogin(
			@ModelAttribute("firstLoginFormBean") FirstLoginFormBean firstLoginFormBean,
			final BindingResult result, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes, Locale locale)
			throws IOException {

		logger.debug(String.format("User [%s] requested to first login",
				firstLoginFormBean.getCurrentLoginId()));

		ModelAndView modelAndView = new ModelAndView(
				"redirect:/login/loginPage.htm");

		String completePath = request.getServletContext().getRealPath(
				"/firstLogin.htm");

		firstLoginFormBean.setLocale(locale);
		try {

			firstLoginValidator.validate(firstLoginFormBean, result);

			if (!result.hasErrors()) {

				boolean userUpdated = loginService.updateUserFirstLoginDetails(
						firstLoginFormBean, locale, completePath);

				if (!userUpdated) {
					//Changed by jia wei
					result.rejectValue("currentPassword", "currentpasswordNotMatch","currentpassword.not.match");
					
					redirectAttributes.addFlashAttribute("loginBindingResult",
							result);
					redirectAttributes.addFlashAttribute("firstLoginFormBean",
							firstLoginFormBean);

					modelAndView = new ModelAndView(
							"redirect:./../login/firstLogin.htm");
				} else {
					deactivateUserSessions(request,
							firstLoginFormBean.getCurrentLoginId());
				}

			} else {
				if (request.getHeader("Referer") != null) {
					redirectAttributes.addFlashAttribute("loginBindingResult",
							result);
					redirectAttributes.addFlashAttribute("firstLoginFormBean",
							firstLoginFormBean);

					modelAndView = new ModelAndView(
							"redirect:./../login/firstLogin.htm");
				}
			}
			
		    // Added by Jia Wei
			for (Object object : result.getAllErrors()) {
			    if(object instanceof FieldError) {
			        FieldError fieldError = (FieldError) object;
			        String message = messageSource.getMessage(fieldError, null);
			        String name = fieldError.getCode();
			        redirectAttributes.addFlashAttribute(name, message);
			        logger.info(name + ":" + message);
			        
			    }
			}
			// End of adding by Jia Wei

		} catch (BusinessFailureException bfe) {

			redirectAttributes.addFlashAttribute("loginBindingResult", result);
			redirectAttributes.addFlashAttribute("firstLoginFormBean",
					firstLoginFormBean);

			modelAndView = new ModelAndView(
					"redirect:./../login/firstLogin.htm");

			logger.error(String.format("Error occurred while processing the"
					+ " first login activity of the user [%s]",
					firstLoginFormBean.getCurrentLoginId()), bfe);

		} catch (NoSuchAlgorithmException ex) {
			logger.error(String.format("Error occurred while processing the"
					+ " first login activity of the user [%s]",
					firstLoginFormBean.getCurrentLoginId()), ex);

			modelAndView = new ModelAndView(
					"redirect:./../login/firstLogin.htm");
			//Modified By Ravi
			redirectAttributes.addFlashAttribute("errorMessage",
					BizConstants.SOME_ERROR_OCCURRED);
		} catch (IOException ioe) {
			logger.error(String.format("Error occurred while processing the"
					+ " first login activity of the user [%s]",
					firstLoginFormBean.getCurrentLoginId()), ioe);

			modelAndView = new ModelAndView(
					"redirect:./../login/firstLogin.htm");
			//Modified By Ravi
			redirectAttributes.addFlashAttribute("errorMessage",
					BizConstants.SOME_ERROR_OCCURRED);
		}
		
		//added by ravi
		if(modelAndView.getViewName().contains("login/loginPage.htm")){
			redirectAttributes.addFlashAttribute("errorMessage",
					BizConstants.FIRST_LOGIN_SUCCESS);
		}
		
		return modelAndView;
	}

	/**
	 * This method is used to show change password page
	 * 
	 * @param changePasswordFormBean
	 * @param result
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 * @throws IOException
	 */
	//Added By Seshu & Modifed By Jia Wei.
	@RequestMapping(value = "/showChangePassword.htm", method = RequestMethod.GET)
	public ModelAndView showChangePasswordPage(
			@ModelAttribute("changePasswordFormBean") @Valid ChangePasswordFormBean changePasswordFormBean,
			final BindingResult result, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes, Locale locale)
			throws IOException {

		ModelAndView modelAndView = new ModelAndView();

		try {
			
			System.out.println("Fetch Flash Attributes By using RequestContextUtils");
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if (flashMap != null) {
			    System.out.println("Redirect from: "+flashMap.get("redirect"));
			    
			}
			if((Boolean)flashMap.get("redirect") == true){
				String viewPage = "/login/changePassword";
				modelAndView.setViewName(viewPage);
			}else{
				String viewPage = "/login/loginPage.htm";
				modelAndView.setViewName(viewPage);
				
			}
			
			
			
			
			
		} catch (Exception e) {
			logger.error("Exception occured in show First LoginPage ", e);
		}

		return modelAndView;
		
	}

    /**
     * API to check user locked before change password.
     * 
     */
	//Added by seshu.
	@RequestMapping(value = "/changePro.htm", method = { RequestMethod.POST })
	public ModelAndView changeProcess(
			@ModelAttribute("loginFormBean") LoginFormBean loginFormBean,
			final BindingResult result, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes, Locale locale,
			TimeZone timeZone) {

		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		
		LoginVO loginVO = null;
 
		
		logger.debug(String.format("User [%s] requested to authenticate",
				loginFormBean.getLoginId()));

		ModelAndView modelAndView = new ModelAndView(
				"redirect:/login/loginPage.htm");

		try {
			if (sessionInfo == null) {

				validator.validate(loginFormBean, result);

				if (!result.hasErrors()) {

					loginVO = populateLoginVO(loginFormBean);

					loginVO = loginService.authenticateUsers(loginVO, locale);

					if (loginVO.getErrorMessage() == null) {
						
						deactivateUserSessions(request, loginVO.getLoginId());

						session = createNewSession(request,
								loginVO.getLoginId(), session);

						sessionInfo = saveUsersSessions(request, session,
								loginVO, loginFormBean.getUserTimeZone());

						logger.debug(String
								.format("User having User ID [%s] authenticated successfully",
										loginVO.getUserId()));												
					} else {
						logger.debug(String.format(
								"User [%s] authentication failed",
								loginVO.getLoginId()));

						/*loginService.resetFailAttempt(
								BizConstants.FAIL_INCREMENT, changeVO);*/

						redirectAttributes.addFlashAttribute(
								"redirectErrorMessage",
								loginVO.getErrorMessage());
					}
				} else {
					if (request.getHeader("Referer") != null) {
						redirectAttributes.addFlashAttribute(
								"loginBindingResult", result);
						redirectAttributes.addFlashAttribute("loginFormBean",
								loginFormBean);

					}
				}
			} else {
				logger.debug("Login user: " + loginVO.isFirstLogin());				
			}
		} catch (BusinessFailureException bfe) {

			deactivateUserSessions(request, loginVO.getLoginId());
			
			redirectAttributes.addFlashAttribute("errorMessage",
					bfe.getMessage());

			redirectAttributes.addFlashAttribute("loginid",
					loginFormBean.getLoginId());

			if (bfe.getMessage().equals("failed.signed.in")) {

				logger.debug(String.format("User [%s] authentication failed",
						loginVO.getLoginId()));

				modelAndView.setViewName("redirect:/login/loginPage.htm");

			} else if (bfe.getMessage().equals(
					"password.expired.change.password")) {

				redirectAttributes.addFlashAttribute("isPasswordExpired", true);
				//Modified by seshu.
				session = createNewSession(request,
						loginVO.getLoginId(), session);

				sessionInfo = saveUsersSessions(request, session,
						loginVO, loginFormBean.getUserTimeZone());
				redirectAttributes.addFlashAttribute("redirect", true);
				modelAndView
						.setViewName("redirect:/login/showChangePassword.htm");

				logger.debug("User password has expired, having Login-ID - "
						+ loginVO.getLoginId());
			}else if (bfe.getMessage().equals(
					"change.password")) {
				//Modified by seshu.
				session = createNewSession(request,
						loginVO.getLoginId(), session);

				sessionInfo = saveUsersSessions(request, session,
						loginVO, loginFormBean.getUserTimeZone());
				
				redirectAttributes.addFlashAttribute("redirect", true);
				modelAndView
						.setViewName("redirect:/login/showChangePassword.htm");

				logger.debug("User password has changed, having Login-ID - "
						+ loginVO.getLoginId());
			} else if (bfe.getMessage().equals("account.lock.for.one.hour")) {

				redirectAttributes.addFlashAttribute("isLocked", true);

				modelAndView.setViewName("redirect:/login/loginPage.htm");

				logger.info("User account is locked for 1 hour, having Login-ID - "
						+ loginVO.getLoginId());
			} else {

				logger.debug(String.format("Some Business failed, error is ",
						bfe.getMessage()));

				modelAndView.setViewName("redirect:/login/loginPage.htm");
			}
		} catch (Exception ex) {
			logger.error(
					String.format(
							"Error occurred while processing the login activity of the user [%s]",
							loginFormBean.getLoginId()), ex);
		}
		return modelAndView;
	}

	/**
	 * This method is used to process request for change password
	 * 
	 * @param request
	 *            The HTTP request associated with change password
	 * @param response
	 *            The HTTP response as a result generated for change password
	 * 
	 * @return The ModelAndView to show Login Page
	 * @throws IOException
	 */
	// Modified By Jia Wei
	@RequestMapping(value = "/changePasswordProcess.htm", method = RequestMethod.POST)
	public ModelAndView changePassword(
			@ModelAttribute("changePasswordFormBean") @Valid ChangePasswordFormBean changePasswordFormBean,
			final BindingResult result, HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes, Locale locale)
			throws IOException, BusinessFailureException {

		logger.debug(String.format("User [%s] requested to change password",
				changePasswordFormBean.getLoginId()));

		ModelAndView modelAndView = new ModelAndView(
				"redirect:/login/showChangePassword.htm");

		changePasswordFormBean.setLocale(locale);
		try {

			changePasswordValidator.validate(changePasswordFormBean, result);

			if (!result.hasErrors()) {

				boolean changePasswordStatus = loginService
						.changePassword(changePasswordFormBean);

				if (!changePasswordStatus) {
					// Modifed By Jia Wei
					result.rejectValue("currentPassword", "currentpasswordNotMatch",
							"currentpassword.not.match");

					redirectAttributes.addFlashAttribute(
							"ChangePasswordBindingResult", result);
					/*redirectAttributes.addFlashAttribute("errorMessage",
							bfe.getMessage());*/

					redirectAttributes.addFlashAttribute(
							"changePasswordFormBean", changePasswordFormBean);
					redirectAttributes.addFlashAttribute("redirect", true);
					modelAndView = new ModelAndView(
							"redirect:./../login/showChangePassword.htm");
				} else {
					deactivateUserSessions(request,
							changePasswordFormBean.getLoginId());

					modelAndView = new ModelAndView(
							"redirect:./../login/loginPage.htm");
				}

			} else {
				if (request.getHeader("Referer") != null) {

					redirectAttributes.addFlashAttribute(
							"ChangePasswordBindingResult", result);

					redirectAttributes.addFlashAttribute(
							"changePasswordFormBean", changePasswordFormBean);
					redirectAttributes.addFlashAttribute("redirect", true);
					modelAndView = new ModelAndView(
							"redirect:./../login/showChangePassword.htm");
				}
			}

		} catch (Exception ex) {

			logger.error(String.format(
					"Error occurred while processing the change password"
							+ " activity of the user [%s]",
					changePasswordFormBean.getLoginId()), ex);
			// Added By Jia Wei
			redirectAttributes.addFlashAttribute("redirect", true);
			modelAndView = new ModelAndView(
					"redirect:./../login/showChangePassword.htm");

			return modelAndView;
			
		}

		// Added By Jia Wei
	    List<String> errorLists = new ArrayList<String>();

		for (Object object : result.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
		        String message = messageSource.getMessage(fieldError, null);
		        String name = fieldError.getCode();
		        redirectAttributes.addFlashAttribute(name, message);
		        
		        //logger.info(name + ":" + message);
		        errorLists.add(message);
		        
		    }
		}
		redirectAttributes.addFlashAttribute("errorLists", errorLists);
		return modelAndView;
	}

}

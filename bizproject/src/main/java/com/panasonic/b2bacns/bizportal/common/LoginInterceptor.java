package com.panasonic.b2bacns.bizportal.common;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.panasonic.b2bacns.bizportal.persistence.Permission;
import com.panasonic.b2bacns.bizportal.service.PermissionService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This class is used to implement the HandlerInterceptor in order to provide
 * various activity tracking before processing the request. It handles browser
 * back button activity , url copy and paste , different tabs activity from same
 * browser etc.
 * 
 * @author simanchal.patra
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger
			.getLogger(LoginInterceptor.class);

	@Autowired
	PermissionService permissionService;

	@Value("${application.host}")
	String host;

	@Value("${filter.urls}")
	private String urls;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("Login Interceptor -- Request URL::"
				+ request.getRequestURL().toString() + ":: Start Time="
				+ System.currentTimeMillis());

		String url = request.getRequestURL().toString();
		String referer = request.getHeader("Referer");

		List<Permission> permissionList = permissionService.getPermissionList();

		String[] urlArray = urls.split(",");
		// System.out.println(urlArray.length);
		List<String> filterUrlList = Arrays.asList(urlArray);

		@SuppressWarnings("unchecked")
		List<String> urlList = (List<String>) CollectionUtils.collect(
				permissionList, new BeanToPropertyValueTransformer("url"));

		urlList.addAll(filterUrlList);

		// System.out.println(urlList.toString());

		boolean isUrlAllowed = false;

		if (urlList != null && urlList.size() > 0) {

			for (String actionUrl : urlList) {
				// checks if referrer starts with our
				// application domain name.
				if ((referer != null && (referer.startsWith("http://" + host)
						|| referer.startsWith("https://" + host)
						|| referer.startsWith("http://www." + host)
						|| referer.startsWith("http://localhost") || referer
							.startsWith("https://localhost")))
						|| (url.indexOf(actionUrl) != -1)) {

					isUrlAllowed = true;

					break;
				}
			}

			if (!isUrlAllowed) {
				response.setStatus(HttpStatus.SC_FORBIDDEN);
				response.getWriter().write(BizConstants.ACCESS_FORBIDDEN);
				return false;
			}

		} else {
			return false;
		}

		HttpSession session = request.getSession(false);

		SessionInfo sessionInfo = null;

		if (session != null) {

			Object sessionInfoObj = CommonUtil.getSessionInfo(request);

			if (sessionInfoObj != null) {

				sessionInfo = (SessionInfo) sessionInfoObj;

				if (sessionInfo != null) {
					session.setAttribute("applicationLocale",
							request.getLocale());

					if (referer == null) {
						if (url.contains("invalidSession.htm")) {
							session = request.getSession(false);
							session.removeAttribute("sessionInfo");
							session.invalidate();
							response.sendRedirect("loginPage.htm");
							return false;
						} else {
							if (url.contains("/login/loginPage.htm")) {

								// add by jwchan for quick fixing to disable
								// firstLogin user to login to home screen
								// without proceed firstlogin form
								String redirectUrl = "../login/firstLogin.htm";
								logger.debug("loginIntercept "
										+ sessionInfo.isFirstLogin());
								if (!sessionInfo.isFirstLogin()) {
									// add by shanf for quick fixing
									List<String> permissionsNameList = sessionInfo
											.getPermissionNameList();
									redirectUrl = "../home/homeScreen.htm";
									if (permissionsNameList != null
											&& !permissionsNameList
													.contains("navi-home")) {
										redirectUrl = "../acconfig/viewAcConfig.htm";
									}
								}

								response.sendRedirect(redirectUrl);
								return false;
							} else {
								return true;
							}
						}
					} else {

						if (url.contains("invalidSession.htm")) {
							session = request.getSession(false);
							session.removeAttribute("sessionInfo");
							session.invalidate();
						} else if (url.contains("loginPage.htm")
								|| url.contains("loginProcess.htm")) {

							// add by jwchan for quick fixing to disable
							// firstLogin user to login to home screen without
							// proceed firstlogin form
							session.getAttribute("firstLogin");
							String redirectUrl = "../login/firstLogin.htm";
							logger.debug("loginIntercept "
									+ sessionInfo.isFirstLogin());
							if (!sessionInfo.isFirstLogin()) {
								// add by shanf for quick fixing
								List<String> permissionsNameList = sessionInfo
										.getPermissionNameList();
								redirectUrl = "../home/homeScreen.htm";
								if (permissionsNameList != null
										&& !permissionsNameList
												.contains("navi-home")) {
									redirectUrl = "../acconfig/viewAcConfig.htm";
								}
							}
							response.sendRedirect(redirectUrl);
							return false;
						} else {
							// may role based check
							return true;
						}
					}
				}
			} else {

				if (referer == null) {
					if (url.contains("loginPage.htm")
							|| url.contains("invalidSession.htm")) {
						return true;
					} else {
						session = request.getSession(false);
						session.invalidate();
						session = request.getSession(true);

						if (request.getHeader("accept").contains(
								"application/json, text/javascript")) {
							response.sendRedirect("../login/invalidSession.htm");
						} else {
							// Added by Seshu.
							if (url.contains("login/changePasswordProcess.htm")
									|| url.contains("unlock/showChangePassword.htm")
									|| url.contains("unlock/unlockUser.htm")

							) {
								return true;
							}
							response.sendRedirect("../login/loginPage.htm");
						}
						return false;
					}
				} else {
					if (referer.contains("loginPage.htm")
							&& (url.contains("loginProcess.htm")
									|| url.contains("showChangePassword.htm") || url
										.contains("changePro.htm"))) { // Added
																		// by
																		// Seshu.
						return true;
					} else if ((referer.contains("showChangePassword.htm") || referer
							.contains("changePasswordProcess.htm"))
							&& (url.contains("changePasswordProcess.htm")
									|| url.contains("loginPage.htm") || url
										.contains("Password"))) { // Added by
																	// Seshu &
																	// Jia Wei.
						return true;
					} else if (url.contains("invalidSession.htm")
							|| url.contains("loginPage.htm")
							|| url.contains("logout.htm")
							|| url.contains("sessionExpired.htm")) {
						return true;
					} else {
						session = request.getSession(false);
						session.invalidate();
						session = request.getSession(true);

						if (request.getHeader("accept").contains(
								"application/json, text/javascript")) {
							response.sendRedirect("../login/invalidSession.htm");
						} else {
							response.sendRedirect("../login/loginPage.htm");
						}
						return false;
					}
				}
			}
		} else {

			// ../login/invalidSession.htm
			session = request.getSession(true);

			if (request.getHeader("accept").contains(
					"application/json, text/javascript")) {
				response.sendRedirect("../login/invalidSession.htm");
			} else {
				if (url.contains("unlock/showChangePassword.htm")
						|| url.contains("usermanagement/activateUserEmailId.htm")) {
					return true;
				}

				if (referer != null) {
					response.sendRedirect("../login/sessionExpired.htm");
				} else {
					response.sendRedirect("../login/loginPage.htm");
				}
			}
			return false;
		}
		return true;
	}

	//Modified by Ravi
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		if (modelAndView != null) {
			if(!modelAndView.getViewName().contains("/login/loginPage.htm")){
				if(CommonUtil.getSessionInfo(request) != null){
					modelAndView.getModelMap().addAttribute(
						BizConstants.SESSION_INFO_OBJECT_NAME,
						CommonUtil.getSessionInfo(request).clone());
				}
			}
		}
	}
}

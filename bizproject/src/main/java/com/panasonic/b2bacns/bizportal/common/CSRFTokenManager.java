/**
 * 
 */
package com.panasonic.b2bacns.bizportal.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author simanchal.patra
 *
 */
@Component
public class CSRFTokenManager {

	private static final char[] VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879"
			.toCharArray();

	private static final int numChars = 64;

	// algorithm to generate key
	private static final String DEFAULT_PRNG = "SHA1PRNG";

	public final static String CSRF_TOKEN_ATTR_KEY = "ctk";

	private static final Logger logger = Logger
			.getLogger(CSRFTokenManager.class);

	public String getToken(HttpSession userSession) {

		String expectedToken = null;

		if (userSession != null) {
			if (CommonUtil.getSessionInfo(userSession) != null) {
				SessionInfo sessionInfo = CommonUtil
						.getSessionInfo(userSession);
				expectedToken = sessionInfo.getCsrfToken();
			} else {
				expectedToken = (String) userSession
						.getAttribute(CSRF_TOKEN_ATTR_KEY);
			}
		}
		return expectedToken;
	}

	public String readToken(HttpServletRequest request) {
		return request.getHeader(CSRF_TOKEN_ATTR_KEY);
	}

	public void verifyToken(HttpServletRequest request,
			HttpServletResponse response) {

		String url = request.getRequestURL().toString();

		String receivedToken = readToken(request);
		String expectedToken = getToken(request.getSession(false));

		if (logger.isDebugEnabled()) {
			logger.debug("verifying token.  expected=" + expectedToken
					+ ", actual=" + receivedToken);
		}

		if (expectedToken == null || receivedToken == null) {
			// Modified by Ravi and shanf
			if (url.contains("login") || url.contains("home/homeScreen.htm")
					|| url.contains("acconfig/viewAcConfig.htm")
					|| url.contains("stats/viewVisualization.htm")
					|| url.contains("notification/viewNotification.htm")
					|| url.contains("co2Factor/viewSettings.htm")
					|| url.contains("usermanagement/viewAccount.htm")
					|| url.contains("ca_data/viewCa.htm")
					|| url.contains("cust_data/view_cust_data.htm")
					|| url.contains("csrf/getCSRFToken.htm")
					|| url.contains("invalidSession.htm")
					|| url.contains("schedule/viewSchedule.htm")
					|| url.contains("sessionExpired.htm")) {
				return;
			} else {
				throw new IllegalArgumentException(
						BizConstants.ACCESS_FORBIDDEN);
			}
		}

		if (!isValidToken(receivedToken)) {
			throw new IllegalArgumentException(BizConstants.ACCESS_FORBIDDEN);
		}

		if (!expectedToken.equals(receivedToken)) {
			throw new RuntimeException(BizConstants.ACCESS_FORBIDDEN);
		}
	}

	private boolean isValidToken(String actualToken) {
		return StringUtils.isAlphanumeric(actualToken);
	}

	// cs = cryptographically secure
	public static String generateToken() throws NoSuchAlgorithmException {
		SecureRandom srand = SecureRandom.getInstance(DEFAULT_PRNG);
		Random rand = new Random();
		char[] buff = new char[numChars];

		for (int i = 0; i < numChars; ++i) {
			// reseed rand once you've used up all available entropy bits
			if ((i % 10) == 0) {
				rand.setSeed(srand.nextLong()); // 64 bits of random!
			}
			buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
		}
		return new String(buff);
	}

	public void resetToken(HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException {

		HttpSession userSession = request.getSession(false);

		String newToken = generateToken();

		if (userSession != null) {
			if (CommonUtil.getSessionInfo(userSession) != null) {

				SessionInfo sessionInfo = (SessionInfo) CommonUtil
						.getSessionInfo(userSession);

				sessionInfo.setCsrfToken(newToken);
				CommonUtil.setSessionInfo(userSession, sessionInfo);
				response.setHeader(CSRF_TOKEN_ATTR_KEY, newToken);
			} else {
				userSession.setAttribute(CSRF_TOKEN_ATTR_KEY, newToken);
				response.setHeader(CSRF_TOKEN_ATTR_KEY, newToken);
			}
		}
	}

	public void verifyAndResetUniqueToken(HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException {
		if (request.getSession(false) == null) {
			resetToken(request, response);
		} else {
			verifyToken(request, response);
			resetToken(request, response);
		}
	}

}

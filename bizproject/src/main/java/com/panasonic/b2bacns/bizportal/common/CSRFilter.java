/**
 * 
 */
package com.panasonic.b2bacns.bizportal.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author simanchal.patra
 *
 */
public class CSRFilter implements Filter {

	private static final Logger logger = Logger.getLogger(CSRFilter.class);

	private WebApplicationContext webApplicationContext;

	@Override
	public void init(FilterConfig config) {
		setWebApplicationContext(WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext()));
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		try {

			if (isPostRequest(httpRequest) || isGetRequest(httpRequest)) {

				if (isAjaxRequest(httpRequest)) {
					log("verifying token for AJAX request "
							+ httpRequest.getRequestURI());
					getTransactionTokenBean().verifyToken(httpRequest,
							(HttpServletResponse) response);
				} else {
					log("verifying and resetting token for non-AJAX request "
							+ httpRequest.getRequestURI());
					getTransactionTokenBean().verifyAndResetUniqueToken(
							httpRequest, (HttpServletResponse) response);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(
					"Some error occured while verifying transaction token", e);
			((HttpServletResponse) response).setStatus(HttpStatus.SC_FORBIDDEN);
			throw new RuntimeException(BizConstants.ACCESS_FORBIDDEN);
		}

		chain.doFilter(request, response);
	}

	private void log(String line) {
		if (logger.isDebugEnabled()) {
			logger.debug(line);
		}
	}

	private boolean isPostRequest(HttpServletRequest request) {
		return "POST".equals(request.getMethod().toUpperCase());
	}

	private boolean isGetRequest(HttpServletRequest request) {
		return "GET".equals(request.getMethod().toUpperCase());
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getParameter("AJAXREQUEST") != null;
	}

	private CSRFTokenManager getTransactionTokenBean() {
		return (CSRFTokenManager) webApplicationContext
				.getBean(CSRFTokenManager.class);
	}

	void setWebApplicationContext(WebApplicationContext context) {
		this.webApplicationContext = context;
	}

}

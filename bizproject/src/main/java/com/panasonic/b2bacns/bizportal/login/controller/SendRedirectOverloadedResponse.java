package com.panasonic.b2bacns.bizportal.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.log4j.Logger;
public class SendRedirectOverloadedResponse extends HttpServletResponseWrapper {
	private static Logger logger = Logger.getLogger(SendRedirectOverloadedResponse.class);

	private String prefix = null;

	private static String HTTP = "http://";
	private static String HTTPS = "https://";

	public SendRedirectOverloadedResponse(HttpServletRequest inRequest, HttpServletResponse response) {
		super(response);
		prefix = getPrefix(inRequest);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if (logger.isDebugEnabled())
			logger.debug("Going originally to:" + location);
		String finalurl = null;

		if (isUrlAbsolute(location)) {
			if (logger.isDebugEnabled())
				logger.debug("This url is absolute. No scheme changes will be attempted");
			finalurl = location;
		} else {
			finalurl = fixForScheme(prefix + location);
			if (logger.isDebugEnabled())
				logger.debug("Going to absolute url:" + finalurl);
		}
		super.sendRedirect(finalurl);
	}

	public boolean isUrlAbsolute(String url) {
		String lowercaseurl = url.toLowerCase();
		if (lowercaseurl.startsWith("http") == true) {
			return true;
		} else {
			return false;
		}
	}

	public String fixForScheme(String url) {
		// alter the url here if you were to change the scheme
		return url;
	}

	public String getPrefix(HttpServletRequest request) {
		StringBuffer str = request.getRequestURL();
		String url = str.toString();
		String uri = request.getRequestURI();
		if (logger.isDebugEnabled()) {
			logger.debug("requesturl:" + url);
			logger.debug("uri:" + uri);
		}
		int offset = url.indexOf(uri);
		String prefix = url.substring(0, offset);
		if (logger.isDebugEnabled())
			logger.debug("prefix:" + prefix);
		if (prefix.startsWith(HTTP))
			prefix = prefix.replaceAll(HTTP, HTTPS);
		return prefix;
	}

}
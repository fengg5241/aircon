package com.panasonic.b2bacns.bizportal.login.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectFilter implements Filter {
	
	private String excludedPath;
	
	
	
    @Override
	public void init(FilterConfig filterConfig) throws ServletException { 
    	
    	excludedPath = filterConfig.getInitParameter("excludedPath");
    	
    	
    	
    }
    
    @Override
	public void destroy() { }
    
    @Override
	public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        //continue the request
		//HttpServletRequest req = (HttpServletRequest) request;
		//HttpServletResponse res = (HttpServletResponse) response;    	
        //chain.doFilter(request, new SendRedirectOverloadedResponse(req, res));
    
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResonse = (HttpServletResponse) response;    	
		String incomingUrl = httpRequest.getRequestURI();
		
		
		if (incomingUrl.indexOf('\r') >= 0) {
			
			String newUrl = incomingUrl.replaceAll("\n", "");
			
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(newUrl);
			requestDispatcher.forward(request, response);
		}
		
		String path = httpRequest.getRequestURI();
		
		if (path.contains(excludedPath)) {
		    chain.doFilter(request, response); // Just continue chain.
		}else if(path.contains("sessionExpired")){
			 chain.doFilter(request, response);
			
		}
		else {
			chain.doFilter(request, new SendRedirectOverloadedResponse(httpRequest, httpResonse));
		}
    }
    
    
    
}

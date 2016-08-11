package com.panasonic.b2bacns.bizportal.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class URLFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * doFilter Will removed the eTag from the header.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		  chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) {
		      public void setHeader(String name, String value) {
		          if (!"etag".equalsIgnoreCase(name)) {
		              super.setHeader(name, value);
		          }
		      }
		  });
		}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

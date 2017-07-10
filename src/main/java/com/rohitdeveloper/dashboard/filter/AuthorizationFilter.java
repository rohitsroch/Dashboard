package com.rohitdeveloper.dashboard.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);
          
			String reqURI = reqt.getRequestURI();
			System.out.println(reqURI);
			
			if ( (reqURI.indexOf("/account.xhtml") >= 0
					|| reqURI.indexOf("/public/") >= 0
					|| reqURI.contains("javax.faces.resource")) 
					&& (ses != null && ses.getAttribute("username") != null)) {
				resp.sendRedirect(reqt.getContextPath() + "/faces/dashboard.xhtml");	
			}else if ( (reqURI.indexOf("/account.xhtml") >= 0
					|| reqURI.indexOf("/public/") >= 0
					|| reqURI.contains("javax.faces.resource")) 
					|| (ses != null && ses.getAttribute("username") != null)) {
				chain.doFilter(request, response);
			}else {
				resp.sendRedirect(reqt.getContextPath() + "/faces/account.xhtml");	
			}
		} catch (Exception e) {
			System.out.println("AuthorizationFiler: "+e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
}


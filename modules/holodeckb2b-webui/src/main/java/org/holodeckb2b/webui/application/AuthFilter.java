package org.holodeckb2b.webui.application;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("HB2BToken")) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		String token = request.getParameter("token");
		String tokenValue = System.getProperty("hb2b.webui.token");
		if (token == null || tokenValue == null || !token.equals(tokenValue)) {
			try {
				((HttpServletResponse) response).sendError(403);
				return;
			} catch (IOException e) {
			}
		} else {
			((HttpServletResponse) response).addCookie(new Cookie("HB2BToken", "42"));
		}
		chain.doFilter(request, response);
	}

}

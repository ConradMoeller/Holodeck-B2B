/**
 * Copyright (C) 2021 The Holodeck B2B Team, Conrad Moeller
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.holodeckb2b.webui.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

import com.helger.commons.base64.Base64;
import com.helger.commons.io.resource.ClassPathResource;

@Component
@Order(1)
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String tokenValue = System.getProperty("hb2b.webui.token");
		if (tokenValue != null) {
			new TokenHandler().authorize(request, response, chain);
		} else {
			new BasicHandler().authorize(request, response, chain);
		}
	}

	public class TokenHandler {

		public void authorize(ServletRequest request, ServletResponse response, FilterChain chain)
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
			String tokenValue = System.getProperty("hb2b.webui.token");
			String token = request.getParameter("token");
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

	public class BasicHandler {

		public void authorize(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {

			String auth = ((HttpServletRequest) request).getHeader("Authorization");
			if (auth == null) {
				((HttpServletResponse) response).addHeader("WWW-Authenticate", "Basic realm=\"HolodeckB2B\"");
				((HttpServletResponse) response).sendError(401);
				return;
			} else {
				InputStream resource = new ClassPathResource("/user.dat").getInputStream();
				Properties users = new Properties();
				users.load(resource);
				String[] pair = new String(Base64.decode(auth.replaceAll("Basic", "").trim())).split(":");
				String pass = users.getProperty(pair[0]);
				if (pass.equals(pair[1])) {
					chain.doFilter(request, response);
				} else {
					((HttpServletResponse) response).addHeader("WWW-Authenticate", "Basic realm=\"HolodeckB2B\"");
					((HttpServletResponse) response).sendError(401);
					return;
				}
			}
		}
	}

}

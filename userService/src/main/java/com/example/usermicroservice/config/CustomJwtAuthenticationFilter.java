package com.example.usermicroservice.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

	
	private static Logger loger = LoggerFactory.getLogger(CustomJwtAuthenticationFilter.class);
	
	@Autowired
	private JwtUtil jwtUtil;

	/*
	 * here authenticate and validate  the token and extract jwt from request
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwtToken = extractJwtFromRequest(request);
			if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
				UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtToken), "",
						jwtUtil.getRolesFromToken(jwtToken));

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, "", jwtUtil.getRolesFromToken(jwtToken));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				logger.error("cannot set security context");
			}

		} catch (ExpiredJwtException e) {

			request.setAttribute("exception", e);

		} catch (BadCredentialsException e) {

			request.setAttribute("exception", e);

		}
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			loger.error("Filter error", e);
		}

	}

	/*
	 * extract jwt from request
	 */
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}

}

package com.balaji.movie_diary.utility;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			try {
				String token = header.substring(7);
				String email = jwtUtil.extractEmail(token);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, null);

				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}

	@Override
protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();

    return request.getMethod().equalsIgnoreCase("OPTIONS")
            || path.startsWith("/auth/")
            || path.startsWith("/movies/");
}
}

package com.myblog5.myblog5.security;

import com.myblog5.myblog5.exception.BlogAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get JWT (token) from HTTP request
        String token = getJWTfromRequest(request);

        // Validate token
        try {
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                // Load user associated with token
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenProvider.getUsernameFromToken(token));

                // Create an Authentication object
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set the Authentication object in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (BlogAPIException e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJWTfromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
       return null;
    }
}

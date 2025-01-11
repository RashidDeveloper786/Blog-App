package com.project.blog_app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper; 


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    // 1. Get Token
    String requestToken = request.getHeader("Authorization");
    System.out.println("Authorization Header: " + requestToken);

    String username = null;
    String token = null;

    // Check if the requestToken is null or doesn't start with "Bearer"
    if (requestToken != null && requestToken.startsWith("Bearer ")) {
        token = requestToken.substring(7);
        try {
            username = this.jwtTokenHelper.getUsernameFromToken(token);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unable to get Jwt Token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Jwt Token has expired...");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid Jwt Token");
        }
    } else {
        System.out.println("JWT Token does not begin with Bearer or is null");
    }

    // Once we get the token...
    // Now we validate the token...
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetail = this.userDetailsService.loadUserByUsername(username);

        if (this.jwtTokenHelper.validateToken(token, userDetail)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetail, token, userDetail.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            System.out.println("Invalid Jwt Token...");
        }
    } else {
        System.out.println("Username is null or SecurityContext is not null...");
    }

    filterChain.doFilter(request, response);
}

}

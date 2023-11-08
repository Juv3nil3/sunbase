package com.example.SunbaseAssign.security;

import com.example.SunbaseAssign.service.ApiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private ApiService apiService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Why the fuck im here??");
        String accessToken = apiService.getAccessToken();
        String requestURI = request.getRequestURI();

        // Permit requests to the login endpoint without token validation
        if (requestURI.equals("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            String token = requestHeader.substring(7);
            try {
                // Here you can directly use the received token for authentication
                // Authenticate the token against the remote API if necessary
                // If authentication is successful, proceed with the filter chain
                if (!token.equals(accessToken)) {
                     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                    return;
                 }

            } catch (Exception e) {
                logger.error("Error occurred while processing the token: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

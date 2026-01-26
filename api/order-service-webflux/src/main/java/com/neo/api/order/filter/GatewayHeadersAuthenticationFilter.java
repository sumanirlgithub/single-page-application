package com.neo.api.order.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GatewayHeadersAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        // Headers forwarded by API Gateway
        String userId = request.getHeader("X-User-Id");
        String rolesHeader = request.getHeader("X-User-Roles");

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            List<SimpleGrantedAuthority> authorities =
                    rolesHeader == null || rolesHeader.isBlank()
                            ? List.of()
                            : Arrays.stream(rolesHeader.split(","))
                            .map(String::trim)
                            .map(role -> role.startsWith("ROLE_")
                                    ? role
                                    : "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .toList();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
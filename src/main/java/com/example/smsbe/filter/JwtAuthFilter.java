package com.example.smsbe.filter;

import com.example.smsbe.config.Whitelist;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.service.ManagerService;
import com.example.smsbe.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtil;
    private final ManagerService managerService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return Whitelist.get().entrySet().stream()
                .anyMatch(entry -> {
                    String pattern = entry.getKey();
                    String allowedMethod = entry.getValue();

                    // Match path and ensure method matches or is "ANY"
                    return PATH_MATCHER.match(pattern, path) &&
                            ("ANY".equalsIgnoreCase(allowedMethod) || allowedMethod.equalsIgnoreCase(method));
                });
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {

        final String accessToken = resolveToken(request);
        if (accessToken == null) {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                new AppException(401, "UNAUTHORIZED"));
            return;
        }

        try {
            final String username = jwtUtil.extractUsername(accessToken);
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails user = managerService.loadUserByUsername(username);

                if (jwtUtil.isTokenValid(accessToken, user)) {
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    new AppException(401, "Token expired"));
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    exception);
        }
    }

    private String resolveToken(@NonNull HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
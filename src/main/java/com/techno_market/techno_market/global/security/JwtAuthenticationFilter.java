package com.techno_market.techno_market.global.security;

import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.global.rq.Rq;
import com.techno_market.techno_market.global.rsData.RsData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Rq rq;
    private final UserService userService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String requestUri = request.getRequestURI();
        if (requestUri.equals("/api/user/logout") || requestUri.startsWith("/api/articles") || requestUri.startsWith("/api/gen")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = rq.getCookieValue("accessToken", "");

        if (!accessToken.isBlank()) {
            if (!userService.validateToken(accessToken)) {
                String refreshToken = rq.getCookieValue("refreshToken", "");

                RsData<String> rs = userService.refreshAccessToken(refreshToken);
                accessToken = rs.getData();
                rq.setCrossDomainCookie("accessToken", accessToken);
            }

            SecurityUser securityUser = userService.getUserFromAccessToken(accessToken);
            rq.setLogin(securityUser);

        }

        filterChain.doFilter(request, response);
    }
}

package com.araa.project.Security;

import com.araa.project.Entity.User;
import com.araa.project.Helper.CookieHelper;
import com.araa.project.Helper.JwtHelper;
import com.araa.project.Repository.RefreshTokenRepository;
import com.araa.project.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static com.araa.project.Helper.CookieHelper.cookieBuilder;
import static com.araa.project.Helper.CookieHelper.cookieGet;

@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private CookieHelper cookieHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional<Cookie> cookie = cookieGet(request);

        cookie.ifPresent(cookie1 -> {
            String accessToken = StringUtils.substringBetween(cookie1.getValue(), "%", "&%");
            String refreshToken = StringUtils.substringAfter(cookie1.getValue(), "&%");

            if (jwtHelper.validateAccessToken(accessToken) && (jwtHelper.validateRefreshToken(refreshToken) && tokenFromDB(refreshToken))) {
                String id = jwtHelper.getUserIdFromAccessToken(accessToken);
                User user = userService.findById(Long.parseLong(id)).get();
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            } else if (!jwtHelper.validateAccessToken(accessToken) && (jwtHelper.validateRefreshToken(refreshToken) && tokenFromDB(refreshToken))) {
                String id = jwtHelper.getUserIdFromRefreshToken(refreshToken);
                User user = userService.findById(Long.parseLong(id)).get();
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
                log.info("Access token from user "+user.getEmail()+" expired, creating new one");
                response.addCookie(cookieBuilder(user, jwtHelper.generateAccessToken(user), refreshToken));
            }
        });

        filterChain.doFilter(request, response);
    }

    private boolean tokenFromDB(String token) {
        Optional<String> id = Optional.of(jwtHelper.getUserIdFromRefreshToken(token));
        if (id.isPresent()) {
            return refreshTokenRepository
                    .findById(Long.parseLong(id.get()))
                    .get()
                    .getRefreshToken()
                    .equals(token);
        }
        return false;
    }
}

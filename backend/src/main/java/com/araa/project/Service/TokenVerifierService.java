package com.araa.project.Service;

import com.araa.project.Entity.User;
import com.araa.project.Helper.CookieHelper;
import com.araa.project.Helper.JwtHelper;
import com.araa.project.Repository.RefreshTokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static com.araa.project.Helper.CookieHelper.cookieBuilder;

@Service
public class TokenVerifierService {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private CookieHelper cookieHelper;


    public boolean isAuthenticated(Optional<Cookie> cookie){
        if(cookie.isPresent()){
            String accessToken = StringUtils.substringBetween(cookie.get().getValue(), "%", "&%");
            String refreshToken = StringUtils.substringAfter(cookie.get().getValue(), "&%");
            if (jwtHelper.validateAccessToken(accessToken)) {
                return true;
            }else if (jwtHelper.validateRefreshToken(refreshToken) && jwtHelper.tokenFromDB(refreshToken)){
                return true;
            }
        }
       return false;
    }
}

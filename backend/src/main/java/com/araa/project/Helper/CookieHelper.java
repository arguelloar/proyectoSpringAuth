package com.araa.project.Helper;

import com.araa.project.Entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieHelper {

    public static ResponseCookie cookieBuilder(User user, String accessToken, String refreshToken){
        String cookie = user.getEmail()+"%"+accessToken+"&%"+refreshToken;
        ResponseCookie responseCookie = ResponseCookie.from("userLogin",cookie)
                .sameSite("None")
                .secure(true)
                .build();
        return responseCookie;
    }

    public static ResponseCookie deleteCookie(){
        ResponseCookie responseCookie = ResponseCookie.from("userLogin",null)
                .sameSite("None")
                .secure(true)
                .maxAge(0)
                .build();
        return responseCookie;
    }

    public static Optional<Cookie> cookieGet(HttpServletRequest request){
       return Stream.of(Optional.ofNullable(request.getCookies())
                        .orElse(new Cookie[0]))
                .filter(cookie1 -> cookie1.getName().equals("userLogin"))
                .findFirst();
    }
}

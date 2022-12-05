package com.araa.project.Helper;

import com.araa.project.Entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieHelper {

    public static Cookie cookieBuilder(User user, String accessToken, String refreshToken){
        String cookie = user.getEmail()+"%"+accessToken+"&%"+refreshToken;
        Cookie setCookie = new Cookie("userLogin",cookie);
        setCookie.setHttpOnly(true);
        setCookie.setPath("/api");
        setCookie.setDomain("localhost");
        return setCookie;
    }

    public static Cookie deleteCookie(){
        Cookie setCookie = new Cookie("userLogin",null);
        setCookie.setHttpOnly(true);
        setCookie.setPath("/api");
        setCookie.setDomain("localhost");
        setCookie.setMaxAge(0);
        return setCookie;
    }

    public static Optional<Cookie> cookieGet(HttpServletRequest request){
       return Stream.of(Optional.ofNullable(request.getCookies())
                        .orElse(new Cookie[0]))
                .filter(cookie1 -> cookie1.getName().equals("userLogin"))
                .findFirst();
    }
}

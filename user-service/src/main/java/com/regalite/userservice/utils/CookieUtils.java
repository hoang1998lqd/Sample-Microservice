package com.regalite.userservice.utils;

import com.regalite.userservice.common.VariableConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

public class CookieUtils {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
    public static void addCookieWithSecure(HttpServletResponse response, String name, String value, int maxAge, HttpServletRequest request) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
//        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(checkProtocol(request));
        response.addCookie(cookie);
    }
    public static boolean checkProtocol(HttpServletRequest request){
        String protocol = request.getScheme();
        return protocol.equalsIgnoreCase(VariableConstants.PROTOCOL_HTTPS);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())));
    }


}

package com.ssafy.tosi.interceptor;

import com.ssafy.tosi.cookieUtil.CookieUtil;
import com.ssafy.tosi.jwt.JwtProperties;
import com.ssafy.tosi.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String accessToken = cookieUtil.getTokenFromCookie(request, "access-token");
//
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            String cookieName = "access-token";
//
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(cookieName)) {
//                    accessToken = cookie.getValue();
//                    System.out.println(accessToken);
//                    break;
//                }
//            }
//        }

        System.out.println(accessToken);
        System.out.println(request.getCookies());

        boolean tokenValidity = false;

        if (accessToken == null) {
            System.out.println("토큰이 없습니다.");
            request.getRequestDispatcher("/redirect/login").forward(request, response);
            return false;
        } else {
            tokenValidity = jwtUtil.validateToken(accessToken);
        }

        if (tokenValidity == false) {
            request.getRequestDispatcher("/jwt/new-access-token").forward(request, response);
            return false;
        }

        Integer userId = jwtUtil.getUserId(accessToken);

        System.out.println(userId);
        request.setAttribute("userId", userId);

        return true;
    }
}

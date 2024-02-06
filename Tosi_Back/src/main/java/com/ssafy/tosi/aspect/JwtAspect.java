package com.ssafy.tosi.aspect;

import com.ssafy.tosi.cookieUtil.CookieUtil;
import com.ssafy.tosi.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class JwtAspect {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    @Before("execution(* com.ssafy.tosi.*..*Controller.*(..)) && !execution(* com.ssafy.tosi.user.UserController.postUser(..)) && !execution(* com.ssafy.tosi.user.UserController.postLogin(..)) && !execution(* com.ssafy.tosi.user.UserController.getLogout(..)) && !execution(* com.ssafy.tosi.user.UserController.getEmailCheck(..)) && !execution(* com.ssafy.tosi.jwt.TokenController.postNewAccessToken(..)) && args(request, response, ..)")
    public void beforeControllerMethod(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String accessToken = cookieUtil.getTokenFromCookie(request, "access-token");

        if (accessToken == null) {
            System.out.println("토큰이 없습니다.");
        }
        System.out.println(accessToken);
        System.out.println(request.getCookies());

        boolean tokenValidity = false;

        if (accessToken == null) {
            throw new Exception("토큰이 없습니다.");
        } else {
            tokenValidity = jwtUtil.validateToken(accessToken);
        }

        if (!tokenValidity) {
            throw new Exception("토큰이 유효하지 않습니다.");
        }

        Integer userId = jwtUtil.getUserId(accessToken);

        System.out.println("userId: " + userId);
        request.setAttribute("userId", userId);
    }
}

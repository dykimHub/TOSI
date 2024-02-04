package com.ssafy.tosi.jwt;

import com.ssafy.tosi.cookieUtil.CookieUtil;
import com.ssafy.tosi.user.UserService;
import com.ssafy.tosi.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    // Access token 발급 회원정보 조회 -> jwt 발급
    // Refresh token 발급
    // token 만료시키기

    public String generateNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!jwtUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Integer userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.selectUser(userId);

        return jwtUtil.generateToken(userId, Duration.ofDays(1));
    }






    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";

    // 리프레쉬 토큰으로 새 access 토큰 발급해서 쿠키로 보내기
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }



}

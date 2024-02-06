package com.ssafy.tosi.jwt.service;

import com.ssafy.tosi.cookieUtil.CookieUtil;
import com.ssafy.tosi.jwt.JwtUtil;
import com.ssafy.tosi.jwt.RefreshTokenRepository;
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


    public String generateNewAccessToken(String refreshToken) {

        if(!jwtUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Integer userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.selectUser(userId);

        return jwtUtil.generateToken(userId, Duration.ofDays(1));
    }



}

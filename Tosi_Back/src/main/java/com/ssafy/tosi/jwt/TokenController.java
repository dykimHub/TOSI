package com.ssafy.tosi.jwt;

import com.ssafy.tosi.cookieUtil.CookieUtil;
import com.ssafy.tosi.jwt.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/jwt")
@RestController
//@CrossOrigin("*")
public class TokenController {

    private final TokenService tokenService;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    @PostMapping("/new-access-token")
    public ResponseEntity<?> postNewAccessToken(HttpServletRequest request) {

        String refreshToken = cookieUtil.getTokenFromCookie(request, "refresh-token");
        String newAccessToken = tokenService.generateNewAccessToken(refreshToken);

        return new ResponseEntity<String>(newAccessToken, HttpStatus.CREATED);
    }

}

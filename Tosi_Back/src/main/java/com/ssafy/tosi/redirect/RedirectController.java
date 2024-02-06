package com.ssafy.tosi.redirect;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

    @GetMapping("/redirect/login")
    public ResponseEntity<Object> redirectToLogin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login"); // Vue3 프론트엔드의 로그인 페이지 URL로 대체
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 상태 코드
    }
}

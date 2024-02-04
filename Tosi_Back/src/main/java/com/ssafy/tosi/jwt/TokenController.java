package com.ssafy.tosi.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/jwt")
@RestController
@CrossOrigin("*")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/new-access-token")
    public ResponseEntity<?> postNewAccessToken(@RequestBody String refreshToken) {
        String newAccessToken = tokenService.generateNewAccessToken(refreshToken);

        return new ResponseEntity<String>(newAccessToken, HttpStatus.CREATED);
    }
}

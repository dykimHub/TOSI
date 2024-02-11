package com.ssafy.tosi.user;

import com.ssafy.tosi.cookieUtil.CookieUtil;
import com.ssafy.tosi.jwt.JwtUtil;
import com.ssafy.tosi.jwt.RefreshToken;
import com.ssafy.tosi.jwt.service.RefreshTokenService;
import com.ssafy.tosi.user.dto.LoginInfo;
import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.dto.UserInfoResponse;
import com.ssafy.tosi.user.entity.Child;
import com.ssafy.tosi.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    // 회원 가입
    @PostMapping("/regist")
    public ResponseEntity<Integer> postUser(@RequestBody UserInfo userInfo) {
        int userId = userService.insertUser(userInfo);

        return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
    }

    // 회원 정보 조회
    @GetMapping
    public ResponseEntity<User> getUser(HttpServletRequest request) {

        Integer userId = (Integer) request.getAttribute("userId");
        User user = userService.selectUser(userId);
//        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
//                .email(user.getEmail())
//                .bookshelfName((user.getBookshelfName()))
//                .childrenList(user.getChildrenList())
//                .build();

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<Void> putUser(HttpServletRequest request, @RequestBody UserInfo userInfo) {
        Integer userId = (Integer) request.getAttribute("userId");
        System.out.println("userId : " + userId);

        userInfo.setUserId(userId);

        userService.updateUser(userInfo);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = (Integer) request.getAttribute("userId");
        userService.deleteUser(userId);

        cookieUtil.deleteCookie(request, response, "access-token");
        cookieUtil.deleteCookie(request, response, "refresh-token");

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody LoginInfo loginInfo, HttpServletResponse response, HttpSession session) {

        Integer userId = userService.login(loginInfo);

        if(userId == null) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        if(loginInfo.getAutoLogin() == false) {
            session.setAttribute("isLoggedIn", "true");
            String accessToken = jwtUtil.generateToken(userId, Duration.ofDays(1));
            cookieUtil.addCookie(response, "access-token", accessToken, (int) Duration.ofDays(1).toSeconds());
            return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
        }

        System.out.println(userId);
        String accessToken = jwtUtil.generateToken(userId, Duration.ofDays(1));
        String refreshToken = jwtUtil.generateToken(userId, Duration.ofDays(7));
        System.out.println(accessToken);
        RefreshToken refreshTokenEntity = RefreshToken.builder().userId(userId).refreshToken(refreshToken).build();
        refreshTokenService.updateRefreshToken(refreshTokenEntity);

        Map<String, String> map = new HashMap<>();
        map.put("access-token", accessToken);
        map.put("refresh-token", refreshToken);

        return new ResponseEntity<Map>(map, HttpStatus.ACCEPTED);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> getLogout(HttpServletRequest request, HttpServletResponse response) {
        cookieUtil.deleteCookie(request, response, "access-token");
        cookieUtil.deleteCookie(request, response, "refresh-token");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 이메일 중복 검색
    @GetMapping("/email-check")
    public ResponseEntity<Boolean> getEmailCheck(@RequestParam String email) {
        boolean result = userService.checkEmailDuplication(email);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    // 아이목록 조회
    @GetMapping("/children-list")
    public ResponseEntity<List<Child>> getChildrenList(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
//        System.out.println("컨트롤러:" + userId);
        List<Child> childrenList = userService.selectChildrenList(userId);
        return new ResponseEntity<List<Child>>(childrenList, HttpStatus.OK);
    }

    // 비밀번호 확인
    @PostMapping("/password-check")
    public ResponseEntity<?> postPasswordCheck(HttpServletRequest request, @RequestBody String password) {
        Integer userId = (Integer) request.getAttribute("userId");
        System.out.println(userId);

        boolean result = userService.checkPassword(userId, password);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}

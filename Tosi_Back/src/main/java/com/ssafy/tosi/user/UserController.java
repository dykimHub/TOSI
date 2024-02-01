package com.ssafy.tosi.user;

import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping
    public ResponseEntity<Integer> postUser(@RequestBody UserInfo userInfo) {
        int userId = userService.insertUser(userInfo);

        return new ResponseEntity<Integer>(userId, HttpStatus.CREATED);
    }

    // 회원 정보 조회
    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam int userId) {
        User user = userService.selectUser(userId);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<Void> putUser(@RequestBody UserInfo userInfo) {
        userService.updateUser(userInfo);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam int userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody User user, HttpSession httpsession) {
        Integer userId = userService.getLogin(user);
        // 로그인 실패 시
        if (userId == null)
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

        httpsession.setAttribute("loginUser", userId);

        return new ResponseEntity<Integer>(userId, HttpStatus.OK);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> getlogout(HttpSession httpsession) {
        httpsession.invalidate();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    // 회원 검색
    @GetMapping("/search")
    public ResponseEntity<Integer> getUserSearch(@RequestParam String email) {
        int result = userService.selectUserBySearch(email);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    // 아이목록 조회??
}

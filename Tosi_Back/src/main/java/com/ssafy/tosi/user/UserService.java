package com.ssafy.tosi.user;

import com.ssafy.tosi.jwt.RefreshTokenRepository;
import com.ssafy.tosi.user.dto.ChildInfo;
import com.ssafy.tosi.user.dto.LoginInfo;
import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.entity.Child;
import com.ssafy.tosi.user.entity.User;
import com.ssafy.tosi.user.repository.ChildRepository;
import com.ssafy.tosi.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원 가입
    @Transactional
    public int insertUser (UserInfo userInfo) {

        User user = userInfo.toEntity();

        User insertedUser = userRepository.save(user);

        List<ChildInfo> childrenList = userInfo.getChildrenList();

        for (int i = 0; i < childrenList.size(); i++) {
            Child child = childrenList.get(i).toEntity(insertedUser.getUserId());
            childRepository.save(child);
        }
        return insertedUser.getUserId();
    }

    // 회원 정보 조회
    public User selectUser (int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Transactional
    // 회원 정보 수정
    public void updateUser (UserInfo userInfo) {
        Optional<User> optionalUser = userRepository.findById(userInfo.getUserId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println(user.toString());
            user.update(userInfo);
        } else {
            // 예외 던지기
        }

        childRepository.deleteByUserId(userInfo.getUserId());

        List<ChildInfo> childrenList = userInfo.getChildrenList();

        for (int i = 0; i < childrenList.size(); i++) {
            childRepository.save(childrenList.get(i).toEntity(userInfo.getUserId()));
        }
    }

    @Transactional
    // 회원 탈퇴
    public void deleteUser (int userId) {
        refreshTokenRepository.deleteByUserId(userId);
        childRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    // 로그인
    public Integer login (LoginInfo loginInfo) {
        User foundUser = userRepository.findByEmail(loginInfo.getEmail());
        System.out.println(foundUser);
        if(foundUser != null && foundUser.getPassword().equals(loginInfo.getPassword())) {
            return foundUser.getUserId();
        }
        return null;
    }

    // 이메일 중복 확인
    public boolean checkEmailDuplication (String email) {
        User searchedUser = userRepository.findByEmail (email);
        if (searchedUser != null){
            System.out.println("false");
            return true;
        }
        return false;
    }

    // 비밀번호 확인
    public boolean checkPassword (Integer userId, String password) {
        System.out.println(userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        
//        User user = null;
        
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getPassword().equals(password)) {
                System.out.println(user.getPassword().equals(password));
                return true;
            }
        }
        return false;
        
    }

    // 아이 목록 조회
    public List<Child> selectChildrenList(Integer userId) {

        System.out.println("서비스:" + userId);
        List<Child> childrenList = childRepository.findByUserId(userId);
        System.out.println(childrenList);
        return childrenList;
    }


}

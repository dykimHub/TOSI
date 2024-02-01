package com.ssafy.tosi.user;

import com.ssafy.tosi.user.dto.ChildInfo;
import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.entity.Child;
import com.ssafy.tosi.user.entity.User;
import com.ssafy.tosi.user.repository.ChildRepository;
import com.ssafy.tosi.user.repository.UserRepository;
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

    // 회원 가입
    @Transactional
    public int insertUser (UserInfo userInfo) {

        User user = userInfo.toEntity();

        User insertedUser = userRepository.save(user);

        List<ChildInfo> children = userInfo.getChildren();

        for (int i = 0; i < children.size(); i++) {
            Child child = children.get(i).toEntity(insertedUser.getUserId());
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
        Optional<User> user = userRepository.findById(userInfo.getUserId());

        user.orElse(null).update(userInfo);

        childRepository.deleteByUserId(userInfo.getUserId());

        List<ChildInfo> children = userInfo.getChildren();

        for (int i = 0; i < children.size(); i++) {
            childRepository.save(children.get(i).toEntity(userInfo.getUserId()));
        }
    }

    @Transactional
    // 회원 탈퇴
    public void deleteUser (int userId) {
//        Optional<User> user = userRepository.findById(userId);
//
//        if (user.isPresent()) {
//            // 사용자가 존재하는 경우에만 자식 엔터티 삭제
//            childRepository.deleteByUserId(userId);
//
//            // 사용자 삭제
//            userRepository.deleteById(userId);
//        } else {
//            System.out.println("주어진 userId " + userId + "에 해당하는 사용자가 존재하지 않습니다.");
//        }
//
//        Optional<User> user = userRepository.findById(userId);
//
//        user.orElse(null).update(userInfo);
//
//        childRepository.deleteByUserId(userInfo.getUserId());
        childRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    // 로그인
    public Integer getLogin (User user) {
        User tmp = userRepository.findByEmail(user.getEmail());
        if(tmp != null && tmp.getPassword().equals(user.getPassword())) {
            return tmp.getUserId();
        }
        return null;
    }

    // 회원 검색
    public int selectUserBySearch (String email) {
        User searchedUser = userRepository.findByEmail (email);
        return searchedUser.getUserId();
    }

}

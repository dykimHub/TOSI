package com.ssafy.tosi.user.repository;

import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.entity.Child;
import com.ssafy.tosi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("SELECT new com.ssafy.tosi.user.dto.UserInfoResponse(u.userId, u.email, u.bookshelfName, u.childrenList) FROM User u WHERE u.userId = :userId")
    UserInfo findUserInfoResponseByUserId(@Param("userId") Integer userId);

//    @Modifying
//    @Query("select * from Child c where c.userId = :userId")
//    void deleteByUserId(@Param("userId") Integer userId);
//
//    @Query("SELECT c FROM Child c WHERE c.userId = :userId")
//    List<Child> findByUserId(@Param("userId") Integer userId);

}
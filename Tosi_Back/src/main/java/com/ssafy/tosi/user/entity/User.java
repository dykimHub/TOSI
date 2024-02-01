package com.ssafy.tosi.user.entity;

import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.entity.Child;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users") // Entity명과 DB테이블명이 일치하지 않는 경우 어떤 테이블과 매칭할 건지 지정
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor // 매개변수가 없는 기본생성자 생성
@Getter
@Entity
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 방식 결정
    @Column(updatable=false)
    private Integer userId;

    @Column(name = "email", nullable = false, unique = true, updatable = false) // DB 속성명과 일치하지 않는 경우 DB 속성 지정
    private String email;

    private String password;

    @CreatedDate
    @Column(name = "regDate", nullable = false)
    private LocalDateTime regDate;

    private String bookshelfName;

    @OneToMany
    @JoinColumn(name = "userId")
    @OrderBy("isMyBaby desc, childName asc")
    private List<Child> children = new ArrayList<>();

    @Builder
    public User (String email, String password, String bookshelfName) {
        this.email = email;
        this.password = password;
        this.bookshelfName = bookshelfName;
    }

    public void update(UserInfo userInfo) {
        this.password = userInfo.getPassword();
        this.bookshelfName = userInfo.getBookshelfName();
    }
//
//    @Builder
//    public User(Integer userId, String email, String password, LocalDateTime regDate, String bookshelfName, List<Child> children) {
//        // 빌더 메서드의 내용은 그대로 두고, 기본 생성자에서는 아무 작업도 수행하지 않아도 됩니다.
//    }

}

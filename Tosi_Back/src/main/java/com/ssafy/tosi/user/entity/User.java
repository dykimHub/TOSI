package com.ssafy.tosi.user.entity;

import com.ssafy.tosi.user.dto.UserInfo;
import com.ssafy.tosi.user.entity.Child;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private Integer userId;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    private String password;

    @CreatedDate
    @Column(name = "regDate", nullable = false)
    private LocalDateTime regDate;

//    @ColumnDefault("나의 책장")
    @Column(name = "bookshelfName", nullable = false, columnDefinition = "나의 책장")
    private String bookshelfName = "나의 책장";

    @OneToMany
    @JoinColumn(name = "userId")
    @OrderBy("myBaby desc, childName asc")
    private List<Child> childrenList = new ArrayList<>();

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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", regDate=" + regDate +
                ", bookshelfName='" + bookshelfName + '\'' +
                ", childrenList=" + childrenList +
                '}';
    }

}

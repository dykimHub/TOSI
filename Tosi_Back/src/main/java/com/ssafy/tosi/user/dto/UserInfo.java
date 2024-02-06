package com.ssafy.tosi.user.dto;

import com.ssafy.tosi.user.dto.ChildInfo;
import com.ssafy.tosi.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserInfo {

    private Integer userId;
    private String email;
    private String password;
    private String bookshelfName;
    private List<ChildInfo> childrenList = new ArrayList<>();

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .bookshelfName(bookshelfName)
                .build();
    }

}

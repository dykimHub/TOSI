package com.ssafy.tosi.user.dto;

import com.ssafy.tosi.user.entity.Child;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoResponse {

    private Integer userId;
    private String email;
    private String bookshelfName;
    private List<Child> childrenList;

    @Builder
    public UserInfoResponse(String email, String bookshelfName, List<Child> childrenList) {
        this.email = email;
        this.bookshelfName = bookshelfName;
        this.childrenList = childrenList;
    }
}

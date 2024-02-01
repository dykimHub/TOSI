package com.ssafy.tosi.user.dto;

import com.ssafy.tosi.user.entity.Child;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChildInfo {

    private String childName;
    private Integer userId;
    private int gender;
    private int isMyBaby;

    public Child toEntity(Integer userId) {
        return Child.builder()
                .userId(userId)
                .childName(childName)
                .gender(gender)
                .isMyBaby(isMyBaby)
                .build();
    }

    public Child toEntity() {
        return Child.builder()
                .userId(userId)
                .childName(childName)
                .gender(gender)
                .isMyBaby(isMyBaby)
                .build();
    }
}

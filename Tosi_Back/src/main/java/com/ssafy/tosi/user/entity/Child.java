package com.ssafy.tosi.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer childId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String childName;

    @Column(nullable = false)
    private int gender;

    @Column(nullable = false)
    private boolean myBaby;

    @Builder
    public Child(Integer userId, String childName, int gender, boolean myBaby) {
        this.userId = userId;
        this.childName = childName;
        this.gender = gender;
        this.myBaby = myBaby;
    }

}

package com.ssafy.tosi.tale;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name ="tales")
//추가: Entity name 지정해줘야 함
@Getter
public class Tale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //TODO 다시 확인
    //추가: (strategy = GenerationType.IDENTITY)
    private int taleId;
    //삭제: column 이름과 일치하기 때문에 @column 삭제

    //다른 도메인에서 공유하는 엔티티임. 위치를 다시 생각해볼 것
    //현재 장소로 유지
    private String title;
    private String content1;
    private String content2;
    private String content3;
    private String content4;
    private String characters;
    private int time;
    private int likeCnt;
}
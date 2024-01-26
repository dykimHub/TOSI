package com.ssafy.tosi.tale;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name ="tales")
@Getter
public class Tale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taleId;

    private String title;
    private String content1;
    private String content2;
    private String content3;
    private String content4;
    private String characters;
    private int time;
    private int likeCnt;
}
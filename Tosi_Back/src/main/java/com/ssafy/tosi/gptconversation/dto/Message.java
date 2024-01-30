package com.ssafy.tosi.gptconversation.dto;

import lombok.Data;

@Data
public class Message {
    private String role;
    private String message;
}

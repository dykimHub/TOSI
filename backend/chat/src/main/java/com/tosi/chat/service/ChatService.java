package com.tosi.chat.service;

import com.tosi.chat.dto.ChatInitInfoDto;
import com.tosi.chat.dto.ChatRequestDto;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;

import java.util.List;

public interface ChatService {

    List<MultiChatMessage> sendInitChat(String accessToken, ChatInitInfoDto chatInitInfoDto);

    List<MultiChatMessage> sendChat(ChatRequestDto chatRequestDto);

    List<MultiChatMessage> sendFinalChat(ChatRequestDto chatRequestDto);
}

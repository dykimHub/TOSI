package com.ssafy.tosi.gptconversation;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;

import java.util.List;

public interface ChatgptService {
    String sendChat(List<MultiChatMessage> messages);
}

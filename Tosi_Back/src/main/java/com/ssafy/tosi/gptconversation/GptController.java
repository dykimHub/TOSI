package com.ssafy.tosi.gptconversation;

import com.ssafy.tosi.gptconversation.dto.GptOutputMessage;
import com.ssafy.tosi.gptconversation.dto.Message;
import com.ssafy.tosi.gptconversation.dto.UserInputMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gptapi")
public class GptController {
    private final ChatgptService chatgptService;

    public GptController(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    @Operation(summary="GPT에게 메세지 보내기",
            description="GPT에게 메세지를 보내고 응답을 반환 받습니다.",
            tags={"GptController"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "GPT가 응답합니다.",
                    content = @Content(schema = @Schema(implementation = GptOutputMessage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/input")
    ResponseEntity<?> putMessage(@Parameter(description="user가 입력하는 메세지", required=true, example="세상에서 제일 맛있는 도너츠는?") @RequestBody UserInputMessage userInputMessage){
        System.out.println("입력된 메세지: "+userInputMessage);

        List<MultiChatMessage> messages=new ArrayList<>();
        messages.add(
                new MultiChatMessage("system",
                        "채팅 상대는 0~13세 아이야. 비속어를 쓰지 않도록 해."
                + """
                                너는 동화속 주인공 중 한 명의 역할을 맡아서 어린이와 대화하면 돼.
                                주인공에 대해서 설명하는 게 아니라
                                너가 주인공이 됐다는 가정 하에 주인공의 입장에서 어린이와 대화하는 거야.
                                동화의 내용은
                                """
                +userInputMessage.getTaleScript()
                + """
                                이고, 너가 이 동화에서 맡을 주인공의 이름은
                                """
                +userInputMessage.getPlayName()
                + """
                                이야. 응답은 되도록이면 간결하게 해 줘. 한 문장이야.
                                응답 형식은 자연스러운 문장이야.
                                이름:내용 형식으로 응답하는 건 자제해줘.
                                마치 채팅방에서 대화하듯 어린이와 소통해야 해."""));

        List<Message> inputMessages=userInputMessage.getUserMessages();

        for(int i=0;i<inputMessages.size();i++){
            Message inputMessage=inputMessages.get(i);
            System.out.println("i:"+i+", "+inputMessage);

            System.out.println(inputMessage.getRole()+","+inputMessage.getMessage());

            MultiChatMessage temp=new MultiChatMessage(inputMessage.getRole(),inputMessage.getMessage());
            messages.add(temp);

            messages.add(new MultiChatMessage(inputMessage.getRole(), inputMessage.getMessage()));
        }

        
        GptOutputMessage responseMessage = new GptOutputMessage(chatgptService.sendChat(messages));

        return new ResponseEntity<>(responseMessage,
                HttpStatus.OK);
    }
}

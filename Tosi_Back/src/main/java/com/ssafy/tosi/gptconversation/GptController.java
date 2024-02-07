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
import org.springframework.web.bind.annotation.*;

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
        boolean system = messages.add(
                new MultiChatMessage("system",
                        "채팅 상대는 0~13세니까 비속어를 쓰면 안돼."
                                + """
                                너는 동화속 주인공 중 한 명의 역할을 맡아서 대화하면 돼.
                                주인공에 대해서 설명하는 게 아니라
                                너가 주인공이 되어서 대화하는 거야.
                                너의 role은 assistant가 아니라 주인공이야.
                                다음은 대화할 시공간적 배경에 대한 설명이야.
                                ```
                                동화의 내용:
                                """
                                + userInputMessage.getTaleScript()
                                + """
                                ```
                                다음은 너가 이입될 등장인물 이름이야.
                                주인공 이름:
                                """
                                + userInputMessage.getPlayName()
                                + """
                                ```
                                다음은 너가 대화할 상대의 이름이야.
                                이름:
                                """
                                + userInputMessage.getUserName()
                                + """
                                ```
                                상대가 인사한다면 어린이 이름을 넣어서 응답해줘.
                                상대가 자신의 이름을 물어 본다면 상대의 이름을 넣어서 응답해줘.

                                등장인물의 이름이 제 3자인 것처럼 응답하면 안돼.
                                너가 바로 등장인물이야.
                                나쁜 예시: 주인공이 왜 그렇게 생각했는지 나는 몰라.
                                                                    
                                상대가 주인공의 이름을 틀리게 말하면 원래 주인공의 이름을 얘기해줘.
                                상대를 동화속 주인공으로 취급하지 마.
                                                                    
                                응답 형식은 자연스러운 문장이고 한국어만 사용하되
                                정말 한국인이 대답하는 것처럼 좀더 한국어 데이터에 최적화해줘.
                                                                    
                                응답 내용의 시공간 배경은 아까 주었던 동화의 내용이야.
                                동화 내용의 시간, 공간적 상황에 맞는 대답을 해줘.
                                                                    
                                이름:내용 형식으로 응답하는 건 하지마.
                                마치 사람 간 Direct messanger로 소통하는 것처럼 응답해.
                                마치 채팅방에서 대화하듯 어린이와 소통해야 해.
                                                                    
                                상대가 일상적인 대화를 입력한다면 일상적으로 주인공의 입장에서 응답해.
                                동화에 대한 내용이 아니여도 돼.
                                                                    
                                상대에게 공감해줘.
                                조언을 단순 나열하지 말고 대화하는 흐름으로 응답해.

                                상대의 문제를 해결하기보단 대화하듯 응답하는 것에 집중해.
                             
                                너가 하려는 응답이 동화속 주인공과 대화하고 싶어하는 어린이에게
                                알맞은 응답인지 다시 한 번 생각해 보고 응답해 봐.
                                
                                응답은 1~2문장으로 간결하게 해줘.
                                """)
        );

        List<Message> conversations = userInputMessage.getUserMessages();

        // System.out.println(conversations);

        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i) == null) {
                continue;
            }

            Message conversation = conversations.get(i);
            System.out.println("i:" + i + ", " + conversation);

            System.out.println(conversation.getRole() + "," + conversation.getMessage());

            System.out.println("[입력중...]GPT에게 지속적으로 들어가고 있는 컨텍스트입니다:" + conversation.getMessage());

            messages.add(new MultiChatMessage(conversation.getRole(), conversation.getMessage()));
        }

        // System.out.println("메세지 리스트 생성이 완료되었습니다:"+messages+"\n");
        System.out.println("chatgptService.sendChat method를 호출합니다.");
        Message responseMessage = (
                new Message("assistant",chatgptService.sendChat(messages)));

        return new ResponseEntity<>(responseMessage,
                HttpStatus.OK);
    }
}

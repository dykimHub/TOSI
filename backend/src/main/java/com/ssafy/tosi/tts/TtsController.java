package com.ssafy.tosi.tts;
import java.io.IOException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tts")
public class TtsController {

    private final TtsService ttsService;

    public TtsController(TtsService ttsService) {
        this.ttsService = ttsService;
    }
    private static final Logger LOGGER = Logger.getLogger(TtsController.class.getName());

    @PostMapping
    public ResponseEntity<byte[]> createTts(@RequestBody TtsDto ttsDto) throws IOException  {
        LOGGER.info("start createTTS.");
        byte[] audioData = ttsService.convertTextToSpeech(ttsDto);
        LOGGER.info("TTS 요청 보냄.");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/mpeg"));
        LOGGER.info("리턴 차례");
        return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
    }
}

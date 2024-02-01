package com.ssafy.tosi.tts;
import lombok.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Data
public class TtsDto {

    private String speaker;
    private String volume;
    private String speed;
    private String pitch;
    private String text;
    private String alpha;
    private String emotion; //지원 speaker : nara, vara, vmikyung, vdain, vyuna(단, nara 는 3: 분노 미지원)
    private String emotionStrength;  //지원 speaker : vara, vmikyung, vdain, vyuna(nara 미지원)
    private String endPitch;  //d로 시작하는 speaker만 가능

    public String toPostParams() {
        try {
    //        String encodedSpeaker = (speaker != null) ? URLEncoder.encode(speaker, "UTF-8") : "";
    //        String encodedText = (text != null) ? URLEncoder.encode(text, "UTF-8") : "";

            String encodedSpeaker = (speaker != null) ? URLEncoder.encode(speaker, "UTF-8") : "";
            String encodedText = (text != null) ? URLEncoder.encode(text, "UTF-8").replace("+", "%20") : "";
            String inputEmotion =(emotion!=null)?emotion:"";
            String inputEStrength=(emotionStrength!=null)?emotionStrength:"";
            return "speaker=" + encodedSpeaker +
                    "&text=" + encodedText +
                    "&volume=0&speed=0&pitch=0&format=mp3" +
                    "&emotion="+inputEmotion+
                    "&emotion-strength="+inputEStrength;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }
    }


}

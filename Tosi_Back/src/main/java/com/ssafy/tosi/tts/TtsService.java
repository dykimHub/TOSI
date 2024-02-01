package com.ssafy.tosi.tts;

import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Service
public class TtsService {

    private static final Logger LOGGER = Logger.getLogger(TtsController.class.getName());

    public byte[] convertTextToSpeech(TtsDto ttsDto) throws IOException {
        String clientId = "xliphnhbz3";
        String clientSecret = "0VebnrNTFHzzLQA04hANy4vJdO81JqVHJTNHwURq";
        String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

        String postParams = ttsDto.toPostParams(); // TtsDto 객체를 사용하여 POST 매개변수 생성
        LOGGER.info("ttsDto: "+postParams);
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(postParams);
            wr.flush();
        }

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            LOGGER.info("response 성공");
            try (InputStream is = con.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] byteBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(byteBuffer)) != -1) {
                    baos.write(byteBuffer, 0, bytesRead);
                }
                return baos.toByteArray();
            }
        } else {
            throw new IOException("TTS API 요청 실패: " + responseCode);

        }
    }
}

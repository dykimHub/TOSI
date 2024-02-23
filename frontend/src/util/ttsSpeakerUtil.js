// ttsUtil.js
// 응답을 바로재생하지 않고 반환
import { useToast } from "vue-toast-notification";
const toast = useToast();
export const generateTTS = async (inputText, speaker, emotion, emotionStrength) => {
    if (!inputText) {
        toast.info("Please enter some text", {
            position: "top",
            duration: 2000,
            queue: true,
            style: {
              backgroundColor: "#f1a8bc",
              color: "white",
            },
          });
        return;
    }

    console.log("ttsUtil.js : ", inputText, speaker, emotion, emotionStrength);

    try {
        const response = await fetch("http://i10b306.p.ssafy.io:8080/tts", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=utf-8",
            },
            body: JSON.stringify({ speaker, text: inputText, emotion, emotionStrength }),
        });

        if (response.ok) {
            return await response.blob();
        } else {
            throw new Error(`Server responded with ${response.status}: ${response.statusText}`);
        }
    } catch (error) {
        console.error("Error in generateTTS:", error);
        throw error; // 호출하는 코드에서 이 오류를 처리하기 위해 오류를 다시 던집니다.
    }
};

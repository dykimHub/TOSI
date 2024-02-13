<template>
    <loading-modal :is-loading="loading"></loading-modal>
  <div class="play">
    <div class="container">
      <div class="topOfTaleList">
        <div class="bigtitle">나만의 동화 만들기</div>
        <div class="taleinfo">
          <div class="leftImg">
            <img
              v-if="customTaleStore.customTaleImage"
              class="thumbnail"
              :src="customTaleStore.customTaleImage"
            />
            <img v-else class="thumbnail" :src="randomImageUrl" alt="" />
          </div>

          <div class="chat" v-if="!customTaleStore.customTaleText.gptMessage">
            <div class="startbox">
              <div class="infobox">
                <div class="voicetitle">오늘의 이야기</div>
                <div class="inputgroup">
                  <div class="input">
                    <label for="prompt1" class="form-label">주인공</label>
                    <select
                      class="form-select"
                      aria-label="Default select example"
                      v-model="selectedChild"
                    >
                      <option value="" disabled selected>
                        주인공을 선택해보세요
                      </option>
                      <option
                        v-for="(child, index) in userStore.userInfo
                          .childrenList"
                        :key="index"
                        :value="child"
                      >
                        {{ child.childName }}
                      </option>
                    </select>
                  </div>

                  <div class="input">
                    <label for="prompt0" class="form-label">배경</label>
                    <div class="input-group">
                      <input
                        type="text"
                        id="prompt0"
                        v-model="prompt[0]"
                        class="form-control"
                        placeholder="예) 놀이터 ..."
                      />
                    </div>
                  </div>

                  <div class="input">
                    <label for="prompt1" class="form-label">키워드</label>
                    <div class="input-group">
                      <input
                        type="text"
                        id="prompt1"
                        v-model="prompt[1]"
                        class="form-control"
                        placeholder="예) 장난감, 우주여행 ..."
                      />
                    </div>
                  </div>
                </div>
                <div class="infobtn">
                  <button class="button" @click="generateCustomTale">
                    동화 생성
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div class="chat" v-else>
            <div class="startbox">
              <div class="voicebox">
                <div class="voicetitle">
                  <img class="mic" src="@/assets/mic.png" />목소리 선택
                </div>
                <div class="align-items-center grid-container">
                  <div
                    v-for="item in items"
                    :key="item.speaker"
                    class="form-wrapper align-items-center"
                  >
                    <label
                      ><input
                        type="radio"
                        :value="item.speaker"
                        v-model="speaker"
                        :name="item.name"
                      />
                      {{ item.name }}
                      <img
                        src="https://talebucket.s3.ap-northeast-2.amazonaws.com/volume_up_FILL0_wght400_GRAD0_opsz24.svg"
                        alt="Speaker Image"
                        class="speaker-image"
                        @click="playVoice(item.url)"
                      />
                    </label>
                  </div>
                </div>
              </div>
              <div class="infobtn">
                <button class="button" @click="readBook">재생</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { useUserStore } from "@/stores/userStore";
import { onMounted, ref } from "vue";
import LoadingModal from "@/components/customTale/loadingModal.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const customTaleStore = useCustomTaleStore();
const userStore = useUserStore();
userStore.getUser();

const generateRandomImageUrl = () => {
  const randomNumber = Math.floor(Math.random() * 9) + 1;
  return `https://customtalebucket.s3.ap-northeast-2.amazonaws.com/customthum${randomNumber}.png`;
};
const randomImageUrl = ref(generateRandomImageUrl());

const prompt = ref(["", ""]);
const selectedChild = ref({ childName: "", gender: 0 });

const loading = ref(false);
const generateCustomTale = async function () {
  try {
    loading.value = true;
    if (prompt.value.some((prompt) => prompt === "")) {
      alert("입력칸을 모두 채워주세요.");
      return;
    }

    const forbiddenWords = ["바보", "멍청이", "병신","시발","씨발","ㅅㅂ","ㅂㅅ","죽어"];

    if (forbiddenWords.some((word) => prompt.value.includes(word))) {
      alert("나쁜 말은 안돼요!!");
      return;
    }
    const genderText = selectedChild.value.gender === 0 ? "girl" : "boy";

    const imagePrompt =
      "3D animation illustrations for children's books with " +
      genderText +
      " child, " +
      prompt.value[0] +
      " is the background and " +
      prompt.value[1] +
      "are the main keywords, bright and lively background, simply express it as a modern character. Don't include any text in the image. only image.";
    // await customTaleStore.getCustomTaleImage(imagePrompt);

    const gptPrompt =
      selectedChild.value.childName +
      "라는" +
      genderText +
      " 아이를 주인공, " +
      prompt.value[0] +
      "을 배경, " +
      prompt.value[1] +
      "를 이용해 500자 내외의 환상적인 동화를 만들어줘. 줄바꿈은 하지 말아줘. 성별언급은 하지말아줘. 자연스럽고 매끄러운 문맥.보내기전에 줄바꿈 모두 없애줘. 보내기전에 문맥이 자연스러운지 확인하고 부자연스러운부분은 자연스럽게 바꿔줘. 말투를 통일해줘.";

    // console.log(gptPrompt);
    // console.log(imagePrompt);
    await customTaleStore.getCustomTaleText(gptPrompt);
    // customTaleStore.getCustomTaleText(gptPrompt);
  } catch (error) {
    console.error("커스텀 동화 생성 오류:", error);
  } finally {
    loading.value = false;
  }
};

//tts
const speaker = ref("vdain");
const items = ref([
  {
    name: "다인",
    speaker: "vdain",
    url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vdain.mp3",
  },
  {
    name: "고은",
    speaker: "vgoeun",
    url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vgoeun.mp3",
  },
  {
    name: "미경",
    speaker: "vmikyung",
    url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vmikyung.mp3",
  },
  {
    name: "이안",
    speaker: "vian",
    url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vian.mp3",
  },
  {
    name: "대성",
    speaker: "vdaeseong",
    url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vdaeseong.mp3",
  },
  {
    name: "원탁",
    speaker: "nwontak",
    url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/nwontak.mp3",
  },
]);
const audioRef = ref(null);
const playVoice = (url) => {
  if (audioRef.value != null) {
    audioRef.value.pause();
  }
  const audio = new Audio(url);
  audioRef.value = audio;
  audioRef.value.play();
};
//end tts
const readBook = async () => {
  try {
    await customTaleStore.readCustomTale(
      customTaleStore.customTaleText.gptMessage
    );
    // console.log(customTaleStore.pages);
    navigateToTalePlay();
  } catch (error) {
    console.error("Error fetching:", error);
  }
};

const navigateToTalePlay = () => {
  const selectedSpeaker = items.value.find(
    (item) => item.speaker === speaker.value
  );
  router.push({
    name: "customTaleCreatePlay",
    params: { speaker: selectedSpeaker.speaker },
  });
};

onMounted(() => {
  // console.log("Image URL:", customTaleStore.customTaleImage);
  // console.log("tale", customTaleStore.customTaleText.gptMessage);
  customTaleStore.resetCustomTale();
});
</script>
<style scoped>
.infobox {
  border: 5px solid #cee8e8;
  border-radius: 30px;
  background-color: rgb(255, 255, 255);
  font-size: 20px;
  width: 350px;
  height: 370px;
}
.inputgroup{
    padding: 10px;
}
.play {
  background-color: white;
  border-radius: 50px;
  margin-top: 35px;
  padding: 40px 0px;
  border: 5px solid #cee8e8;
}
.container {
  display: flex;
  width: 70vw;
  margin: 40px 0 0 60px;
}
.bigtitle {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #c4ecb0;;
  font-size: 40px;
  /* margin: 30px 0px 30px 50px; */
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}
.taleinfo {
  display: flex;
}
.title {
  margin: 30px 0px 25px 0px;
  font-size: 35px;
  width: 300px;
  text-align: center;
  overflow: hidden; /* 글자가 넘칠 경우 숨김 처리 */
  white-space: nowrap; /* 글자가 한 줄에 표시되도록 설정 */
  text-overflow: ellipsis;
}
.leftImg {
  width: 400px;
  height: 450px;
  background-image: url(@/assets/cover.png);
  background-size: cover;
  padding: 10px 10px 0px 57px;
  margin-right: 25px;
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
}
.thumbnail {
  width: 300px;
  height: 300px;
  border: 5px solid white;
  margin-top: 50px;
}

.align-items-center {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.form-wrapper {
  width: 100%;
  max-width: 28rem;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin: 15px 15px 0 15px;
}
.chat {
  width: 520px;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding-right: 60px;
}
.titleimg {
  width: 40px;
  height: 40px;
  margin: 25px 10px 0 0;
}
.selecttitle {
  font-size: 20px;
  margin: 30px 10px 0 0;
}
.form-select {
  font-size: 18px;
}

.speaker-image {
  height: 25px;
  width: 25px;
  cursor: pointer;
}
.startbox {
  display: flex;
  flex-direction: column;
}
.mic {
  width: 30px;
  height: 30px;
  margin-right: 5px;
}
.voicetitle {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 5px solid #cee8e8;
  border-radius: 30px;
  width: 193px;
  height: 60px;
  text-align: center;
  margin: -40px 0px 0px 80px;
  background-color: #ebffdf;
  position: relative;
  z-index: 5;
  font-size: 23px;
}
.voicebox {
  border: 5px solid #cee8e8;
  border-radius: 30px;
  background-color: rgb(255, 255, 255);
  font-size: 20px;
  width: 370px;
  height: 150px;
}
.button {
  margin-top: 20px;
  width: 130px;
  height: 40px;
  /* padding: 10px 25px; */
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}
.button:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}

.infobtn {
  text-align: center;
}
</style>

<template>
  <div id="app" class="talelistContainer">
    <div class="topOfTaleList">
      <div class="title">나만의 동화 만들기</div>
    </div>

    <loading-modal :is-loading="loading"></loading-modal>

    <div class="twoContainer">
      <div class="info-column">
        <div class="info" v-if="!customTaleStore.customTaleText.gptMessage">
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
                  v-for="(child, index) in userStore.userInfo.childrenList"
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
                  placeholder="예) 집, 놀이터 ..."
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

        <div class="info" v-else>
          <h3>목소리 선택</h3>
          <div class="itemform-container">
            <div v-for="item in items" :key="item.speaker" class="itemform">
              <label style="display: flex; flex-direction: row"
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
          <div class="infobtn">
            <button class="button" @click="readBook">재생</button>
          </div>
        </div>
      </div>

      <div class="book-column">
        <div class="book">
          <img
            v-if="customTaleStore.customTaleImage"
            :src="customTaleStore.customTaleImage"
          />
          <img v-else :src="randomImageUrl" alt="" />
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

    const forbiddenWords = ["바보", "멍청이", "병신"];

    if (forbiddenWords.some((word) => prompt.value.includes(word))) {
      alert("나쁜 말 안돼요.");
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
    await customTaleStore.getCustomTaleImage(imagePrompt);

    const gptPrompt =
      selectedChild.value.childName +
      "라는" +
      genderText +
      " 아이를 주인공, " +
      prompt.value[0] +
      "을 배경, " +
      prompt.value[1] +
      "를 이용해 500자 내외의 환상적인 동화를 만들어줘. 줄바꿈은 하지 말아줘. 성별언급은 하지말아줘. 자연스럽고 매끄러운 문맥.보내기전에 줄바꿈 모두 없애줘. 보내기전에 문맥이 자연스러운지 확인해줘.";

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
.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  /* padding-top: 40px;
  padding-bottom: 40px; */
  opacity: 0.95;
  padding: 40px 60px;
  border: 5px solid #cee8e8;
}

.twoContainer {
  display: flex;
  justify-content: space-between;
  margin-left: 10%;
  margin-right: 10%;
  height: 350px;
}
.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #c4ecb0;
  font-size: 40px;
  margin: 30px 0px 0px 50px;
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}
.info-column {
  width: 45%;
  display: flex;
  align-items: center;
}

.inputgroup {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.book-column {
  width: 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.info {
  background-color: #ffffff;
  border: 3px solid #cee8e8;
  border-radius: 20px;
  padding: 20px;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.input {
  margin-bottom: 10px;
}

.book {
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
}

.book img {
  width: 80%;
  height: 80%;
}

.loading-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.loading-content {
  text-align: center;
  background: white;
  padding: 20px;
  border-radius: 8px;
}

.button {
  margin-top: 20px;
  width: 130px;
  height: 40px;
  /* padding: 10px 25px; */
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
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

.itemform-container {
  display: flex;
  flex-wrap: wrap;
}
.itemform {
  flex: 0 0 33.33%;
  box-sizing: border-box;
  /* padding: 0 10px; */
  margin-top: 20px;
}
</style>

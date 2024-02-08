<template>
  <div id="app" class="talelistContainer">
    <div class="topOfTaleList">
      <h2>나만의 동화 만들기</h2>
    </div>

    <loading-modal :is-loading="loading"></loading-modal>

    <div class="twoContainer">
      <div class="info-column">
        <div class="info" v-if="!customTaleStore.customTaleText.gptMessage">
          <div class="input">
            <label for="prompt1" class="form-label">주인공</label>
            <div class="input-group">
              <input
                type="text"
                id="prompt1"
                v-model="prompt[0]"
                class="form-control"
              />
            </div>
          </div>

          <div class="input">
            <label for="prompt2" class="form-label">배경</label>
            <div class="input-group">
              <input
                type="text"
                id="prompt2"
                v-model="prompt[1]"
                class="form-control"
              />
            </div>
          </div>

          <div class="input">
            <label for="prompt3" class="form-label">키워드</label>
            <div class="input-group">
              <input
                type="text"
                id="prompt3"
                v-model="prompt[2]"
                class="form-control"
              />
            </div>
          </div>

          <div >
            <button class="button" @click="generateCustomTale">동화 생성</button>
          </div>
        </div>

        <div class="info" v-else>
          <h3>목소리 선택</h3>
          <div>
            <div v-for="item in items" :key="item.speaker">
              <label
                ><input
                  type="radio"
                  :value="item.speaker"
                  v-model="speaker"
                  :name="item.name"
                />
                {{ item.name }}</label
              >
            </div>
            </div>
          <div class="button" @click="readBook">재생
          </div>
        </div>
      </div>

      <div class="book-column">
        <div class="book">
          <img
            v-if="customTaleStore.customTaleImage"
            :src="customTaleStore.customTaleImage"
            class="img-fluid"
            style="height: 300px"
          />
          <img v-else :src="randomImageUrl" alt="" style="height: 300px" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, ref } from "vue";
import LoadingModal from "@/components/customTale/loadingModal.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const customTaleStore = useCustomTaleStore();

const generateRandomImageUrl = () => {
  const randomNumber = Math.floor(Math.random() * 9) + 1;
  return `https://customtalebucket.s3.ap-northeast-2.amazonaws.com/customthum${randomNumber}.png`;
};
const randomImageUrl = ref(generateRandomImageUrl());

const prompt = ref(["", "", ""]);

const loading = ref(false);
const generateCustomTale = async function () {
  try {
    loading.value = true;
    if (prompt.value.some((prompt) => prompt === "")) {
      alert("키워드를 모두 입력해주세요.");
      return;
    }

    const forbiddenWords = ["바보", "멍청이", "병신"];

    if (forbiddenWords.some((word) => prompt.value.includes(word))) {
      alert("나쁜 말 안돼요.");
      return;
    }

    const imagePrompt =
      "3D animation illustrations for children's books with " +
      prompt.value[0] +
      " child, " +
      prompt.value[1] +
      " is the background and " +
      prompt.value[2] +
      // + ","+ prompt.value[3] + ","+ prompt.value[4]
      "are the main keywords, bright and lively background, simply express it as a modern character. Don't include any text in the image. only image.";
    // await customTaleStore.getCustomTaleImage(imagePrompt);

    const gptPrompt =
      prompt.value[0] +
      "라는 아이를 주인공, " +
      prompt.value[1] +
      "을 배경, " +
      prompt.value[2] +
      "를 이용해 500자 내외의 환상적인 동화를 만들어줘. 줄바꿈은 하지 말아줘. 성별언급은 하지말아줘. 자연스럽고 매끄러운 문맥.보내기전에 줄바꿈 모두 없애줘. 보내기전에 문맥이 자연스러운지 확인해줘.";

    await customTaleStore.getCustomTaleText(gptPrompt);
    // customTaleStore.getCustomTaleText(gptPrompt);
  } catch (error) {
    console.error("커스텀 동화 생성 오류:", error);
  } finally {
    loading.value = false;
  }
};

const speaker = ref("vdain");
const items = ref([
  { name: "다인", speaker: "vdain", emotion: 3, "emotion-strength": 1 },
  { name: "고은", speaker: "vgoeun", emotion: 3, "emotion-strength": 1 },
  { name: "미경", speaker: "vmikyung", emotion: 3, "emotion-strength": 1 },
  { name: "이안", speaker: "vian", emotion: 0, "emotion-strength": 0 },
  { name: "대성", speaker: "vdaeseong", emotion: 3, "emotion-strength": 1 },
  { name: "원탁", speaker: "nwontak", emotion: 0, "emotion-strength": 0 },
]);

const readBook = async () => {
  try {
    await customTaleStore.readCustomTale(customTaleStore.customTaleText.gptMessage)
    // console.log(customTaleStore.pages);
    navigateToTalePlay();
  } catch (error) {
    console.error("Error fetching:", error);
  }
};

const navigateToTalePlay = () => {
  const selectedSpeaker = items.value.find((item) => item.speaker === speaker.value);
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

<style>
.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
}

.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  /* padding-top: 40px;
  padding-bottom: 40px; */
  opacity: 0.95;
  margin: auto;
  width: 90%;
  padding: 40px 60px;
}

.topOfTaleList {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

.twoContainer {
  display: flex;
  justify-content: space-between;
  margin-left: 10%;
  margin-right: 10%;
}

.info-column {
  width: 45%;
}

.book-column {
  width: 50%;
  overflow: hidden;
}

.info {
}

.book {
  width: 100%;
}

.book img {
  width: 100%;
  height: auto;
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
   box-shadow:
   7px 7px 5px 0px #0002,
   4px 4px 5px 0px #0001;
}
</style>

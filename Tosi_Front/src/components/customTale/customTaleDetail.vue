<template>
  <div id="app" class="talelistContainer">
    <div class="topOfTaleList">
      <div class="title">{{ customTaleStore.customTale.title }}</div>
    </div>
    <div class="twoContainer">
      <div class="info-column">
        <div class="info">
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
            :src="customTaleStore.customTale.thumbnail"
            class="img-fluid"
            style="height: 300px"
            alt="커스텀이미지"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const customTaleStore = useCustomTaleStore();

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

//start go customPlay
const readBook = async () => {
  try {
    // console.log("요청 보냄");
    await customTaleStore.readCustomTale(customTaleStore.customTale.content);
    // console.log(customTaleStore.pages);
    // 요청이 성공적으로 완료된 후에 navigateToTalePlay 호출
    // console.log("요청이 성공적으로 완료된 후에 navigateToTalePlay 호출");
    navigateToTalePlay();
  } catch (error) {
    console.error("Error fetching:", error);
  }
};

const navigateToTalePlay = () => {
  //   console.log("다음페이지 보내기");
  const selectedSpeaker = items.value.find(
    (item) => item.speaker === speaker.value
  );
  router.push({
    name: "customTalePlay",
    params: { speaker: selectedSpeaker.speaker },
  });
};
//end go customPlay

//custom
const getRandomCustomTales = ref([]);
function shuffleArray(array) {
  const shuffledArray = array.slice();
  for (let i = shuffledArray.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
  }
  return shuffledArray;
}

onMounted(async () => {
  // 비동기로 데이터를 먼저 로드
  await Promise.all([
    customTaleStore.getCustomTalesList(true),
    customTaleStore.getCustomTale(route.params.customTaleId),
  ]);
  console.log(customTaleStore.customTalesList);
  // 로드된 데이터를 기반으로 랜덤 아이템 선택
  const randomCustomTales = shuffleArray(customTaleStore.customTalesList).slice(
    0,
    4
  );
  getRandomCustomTales.value = randomCustomTales;
});
</script>

<style scoped>
.talelistContainer {
  background-color: white;
  border-radius: 20px;
  margin: 35px;
  opacity: 0.95;
  padding: 40px 0px 60px 0px;
  border: 5px solid #cee8e8;
  width: 80vw;
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
  box-shadow: inset 0 -20px 0 #d3e4ff;
  font-size: 40px;
  margin: 30px 0px 30px 50px;
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
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin: 10px;
  width: 30vw;
}

.input {
  margin-bottom: 10px;
}

.book {
  width: 30vw;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
  padding-left: 10px;
  margin-left: 30px;
  background-image: url("@/assets/bookstart.png");
}

.book img {
  width: 70%;
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
  padding: 0 10px;
  margin-top: 20px;
}
</style>

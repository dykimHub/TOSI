<template>
  <div v-if="customTaleStore.customTale">
    <div class="play">
      <div class="container">
        <div class="topOfTaleList">
          <div class="bigtitle">{{ customTaleStore.customTale.title }}</div>
          <div class="taleinfo">
            <div class="leftImg">
              <div class="title">{{ customTaleStore.customTale.title }}</div>
              <img
                class="thumbnail"
                :src="customTaleStore.customTale.thumbnail"
              />
            </div>

            <div class="chat">
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
                <div class="startbtn">
                  <div class="rocketborder" @click="readBook">
                    <img src="@/assets/rocket.png" class="rocket" />
                  </div>
                  <div class="starttitle">시작</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <div>is Loading...</div>
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
  // console.log(customTaleStore.customTalesList);
  // 로드된 데이터를 기반으로 랜덤 아이템 선택
  const randomCustomTales = shuffleArray(customTaleStore.customTalesList).slice(
    0,
    4
  );
  getRandomCustomTales.value = randomCustomTales;
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
.inputgroup {
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
  box-shadow: inset 0 -20px 0 #d3e4ff;
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
}
.leftImg {
  width: 400px;
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
  /* padding-right: 60px; */
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
.rocketborder {
  width: 120px;
  height: 120px;
  border: 2px solid black;
  border-radius: 50%;
  margin: 30px 0 0 0;
}
.rocket {
  width: 80px;
  height: 80px;
  cursor: pointer;
  margin: 20px 0 0 20px;
  transition: transform 0.5s ease-in-out; /* 애니메이션 효과 부드럽게 */
}
.rocket:hover {
  animation: rocketLaunch 3s forwards; /* 마우스 호버 시 애니메이션 실행 */
}
@keyframes rocketLaunch {
  to {
    transform: translate(300px, -300px) rotate(45deg); /* 대각선 이동 및 회전 */
    opacity: 0; /* 사라지는 효과 */
  }
}
.startbtn {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

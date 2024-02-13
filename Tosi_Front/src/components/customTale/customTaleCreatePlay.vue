<template>
  <div v-if="customTaleStore.pages">
    <div class="play">
      <div class="title">나만의 동화 만들기</div>
      <div class="page-progress">
        &lt; {{ currentPageNum }} / {{ pages.length }} >
      </div>
      <div class="book">
        <div class="cover">
          <img :src="customTaleStore.customTaleImage" class="coverImg" />
        </div>
        <div class="flip-book">
          <div
            class="flip"
            v-for="(page, index) in pages"
            :key="`page-${index}`"
            :class="{ flipped: page.flipped }"
            :style="{ zIndex: zIndexes[index] }"
          >
            <div class="back" v-if="index >= 1">
              <img :src="customTaleStore.customTaleImage" class="leftImg" />
              <img src="@/assets/leftarrow.gif" class="left" />
              <img
                src="@/assets/leftarrowstatic.png"
                class="leftstatic"
                @click="flipPage(index, false)"
              />
            </div>
            <div class="front pre-wrap">
              <div class="page-separator-right"></div>
              <div class="content">{{ pages[index].right }}</div>
              <div v-if="index === 0">
                <img src="@/assets/end.gif" class="end" @click="goToEnd" />
              </div>
              <div v-else>
                <img src="@/assets/rightarrow.gif" class="right" />
                <img
                  src="@/assets/rightarrowstatic.png"
                  class="rightstatic"
                  @click="flipPage(index, true)"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="stopbtn">
        <img
          v-if="isPaused"
          src="@/assets/playaudio.png"
          @click="audioPause"
          class="start"
        />
        <img
          v-else
          src="@/assets/pause.png"
          @click="audioPause"
          class="pause"
        />
        <img src="@/assets/stop.png" class="stop" @click="replay()" />
      </div>
    </div>
  </div>
  <div v-else>is Loading...</div>
</template>

<script setup>
import { ref, computed, reactive, onMounted, watch } from "vue";
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { useRouter } from "vue-router";
import { generateTTS } from "@/util/ttsSpeakerUtil";

const customTaleStore = useCustomTaleStore();
const router = useRouter();
const pages = customTaleStore.pages.reverse();

const props = defineProps({
  speaker: String,
});

const goToEnd = () => {
  audioRef.value.pause();
  router.push({ name: "customTaleSave" });
};

// 페이지 배열의 인덱스를 저장해서 zindex 배열로 만듦
// zindex는 클수록 위쪽에 위치함
// map함수로 pages를 돌면서 index + 1을 저장하고 있음
const zIndexes = reactive(pages.map((_, index) => index + 1));
// 현재 페이지의 인덱스를 저장하는 변수
const currentPageIndex = ref(pages.length - 1);
// index: 뒤집을 페이지의 인덱스
// flip: true; 뒤집은 상태, false; 이전 페이지 펼친 상태
const currentPageNum = ref(1);
function flipPage(index, flip) {
  pages[index].flipped = flip;

  // 페이지를 뒤집을 때
  if (flip) {
    zIndexes.forEach((_, i) => {
      if (i === index) {
        // 페이지 인덱스가 클수록 z-index가 낮아야 함
        // 제일 낮은 인덱스(첫페이지)가 맨 위에 있어야 해서
        zIndexes[i] = pages.length - index;
      }
      // 배열리스트의 현재 인덱스
      currentPageIndex.value = index - 1;
      currentPageNum.value = pages.length - currentPageIndex.value;
    });
  } else {
    // 이전 상태로 되돌리면 해당 페이지가 제일 위에 와야함
    // zindex의 max값에 1을 더한 값을 저장
    const maxZIndex = Math.max(...zIndexes) + 1;
    zIndexes[index] = maxZIndex;

    // 다른 페이지들의 z-index 업데이트
    zIndexes.forEach((z, i) => {
      if (i !== index && pages[i].flipped) {
        zIndexes[i] = z - 1;
      }
    });
    // 배열리스트의 현재 인덱스
    currentPageIndex.value = index;
    currentPageNum.value = pages.length - currentPageIndex.value;
  }
}
// tts
const items = ref([
  { name: "다인", speaker: "vdain", emotion: 3, "emotion-strength": 1 },
  { name: "고은", speaker: "vgoeun", emotion: 3, "emotion-strength": 1 },
  { name: "미경", speaker: "vmikyung", emotion: 3, "emotion-strength": 1 },
  { name: "이안", speaker: "vian", emotion: "", "emotion-strength": "" },
  { name: "대성", speaker: "vdaeseong", emotion: 3, "emotion-strength": 1 },
  { name: "원탁", speaker: "nwontak", emotion: "", "emotion-strength": "" },
]);
const audioRef = ref(null); //오디오 재생을 위한 객체
const audioSrcCache = {}; // 캐시를 저장하는 객체
const isPaused = ref(false);
const ttsMaker = async (text) => {
  //speaker정보
  const selectedSpeaker = items.value.find(
    (item) => item.speaker == props.speaker
  );
  if (selectedSpeaker) {
    const speakerName = selectedSpeaker.speaker;
    const emotion = selectedSpeaker.emotion;
    const emotionStrength = selectedSpeaker["emotion-strength"];
    try {
      const blob = await generateTTS(
        text,
        speakerName,
        emotion,
        emotionStrength
      );
      const url = URL.createObjectURL(blob);
      audioSrcCache[text] = url; // 결과를 캐시에 저장
      return url;
    } catch (error) {
      console.error("Error:", error);
      return "";
    }
  }
};
const autoAudio = (text) => {
  //기존 오디오 끊기
  if (audioRef.value != null) {
    isPaused.value = true;
    audioRef.value.pause();
  }
  // 이미 캐시된 결과가 있는지 확인
  if (audioSrcCache[text] != null) {
    audioRef.value = new Audio(audioSrcCache[text]);
    // audioRef.value.play(); // 재생
    // 재생이 끝나면 Promise를 resolve하도록 설정
    audioRef.value.onended = () => {
      onAudioEnded();
      resolve();
    };
    isPaused.value = false;
    audioRef.value.play(); // 재생
  } else {
    ttsMaker(text).then((url) => {
      if (url) {
        audioRef.value = new Audio(url); // 새로운 오디오를 할당
        // 재생이 끝나면 Promise를 resolve하도록 설정
        audioRef.value.onended = () => {
          onAudioEnded();
          // resolve();
        };
        isPaused.value = false;
        audioRef.value.play(); // 재생
      }
    });
  }
};
// 오디오 재생이 끝날 때 실행되는 콜백 함수
const onAudioEnded = () => {
  if (currentPageIndex.value < pages.length)
    flipPage(currentPageIndex.value, true);
};
// //페이지 변화를 감지해서 틈
watch(pages, (newPages, oldPages) => {
  if (newPages && newPages.length > 0) {
    // 페이지 배열이 변경되었을 때 실행할 코드 작성
    autoAudio(newPages[currentPageIndex.value].right); // 첫 번째 페이지의 오른쪽 텍스트를 넘김
  }
});
const audioPause = () => {
  if (audioRef.value != null) {
    if (isPaused.value) {
      audioRef.value.play();
      isPaused.value = false;
    } else {
      audioRef.value.pause();
      isPaused.value = true;
    }
  }
};
const replay = () => {
  alert("동화를 멈출게요.");
  audioRef.value.pause();
  router.push({ name: "customTaleSave" });
};
onMounted(async () => {
  try {
    if (pages.length > 0) {
      await autoAudio(pages[currentPageIndex.value].right);
    }
  } catch (error) {
    console.error("Error in onMounted:", error);
  }
});
</script>

<style scoped>
.play {
  background-color: white;
  border-radius: 50px;
  margin-top: 35px;
  padding: 40px 60px;
  border: 5px solid #cee8e8;
  /* width: 80vw; */
}
.info {
  display: flex;
  justify-content: space-between;
  width: 950px;
  margin: 45px 10px 0 45px;
}
.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 #c4ecb0;
  font-size: 40px;
  margin-bottom: 40px;
  margin-top: 20px;
  line-height: 1;
}
.cover {
  background-color: #fff;
  box-sizing: border-box;
  width: 500px;
  height: 500px;
  border-radius: 0px 40px 40px 0px;
}
.coverImg,
.leftImg {
  width: 435px;
  height: 450px;
  margin-top: 25px;
  margin-left: 10px;
}
.book {
  margin: 0px 0px 0px 20px;
  padding: 10px 10px 0px 25px;
  display: flex;
  background-color: #21364d;
  width: 950px;
  height: 520px;
  position: relative;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 1);
}
.flip-book {
  width: 500px;
  height: 500px;
  position: relative;
  perspective: 1500px;
  border-radius: 100px;
}
.flip {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  transform-origin: left;
  transform-style: preserve-3d;
  transition: 0.5s;
  color: #000;
}
.front {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  backface-visibility: hidden;
  background-color: #fff;
  box-sizing: border-box;
  padding: 0 13px;
  border-radius: 40px 0px 0px 40px;
  box-shadow: inset 0 0 13px rgba(0, 0, 0, 0.5); /* 내부 그림자 추가 */
}
.page-separator-right {
  position: absolute;
  top: 6.5%;
  right: 100%;
  width: 2px;
  height: 88%;
  /* background-color: #5d4037; */
  background-color: #ede7e0;
  transform: translateX(1px);
  border-radius: 10px;
}
.page-separator-left {
  position: absolute;
  top: 6%;
  left: 100%;
  width: 2px;
  height: 88%;
  /* background-color: #5d4037; */
  background-color: #ede7e0;
  transform: translateX(-1px);
  border-radius: 10px;
}
.back {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  backface-visibility: hidden;
  background-color: #fff;
  transform: rotateY(180deg);
  border-radius: 0px 40px 40px 0px;
  box-shadow: inset 0 0 13px rgba(0, 0, 0, 0.5);
}
.flip.flipped {
  transform: rotateY(-180deg);
}
.pre-wrap {
  white-space: pre-wrap;
}
.left,
.leftstatic,
.right,
.rightstatic {
  width: 40px;
  height: 40px;
  position: absolute;
  background: white;
  cursor: pointer;
  bottom: 13px;
  right: 13px;
  border-radius: 50%;
}
.rightstatic:hover,
.leftstatic:hover {
  width: 40px;
  height: 40px;
  opacity: 0;
  cursor: pointer;
}
.end {
  width: 40px;
  height: 40px;
  position: absolute;
  background: white;
  cursor: pointer;
  bottom: 13px;
  right: 13px;
  border-radius: 50%;
}
.content {
  display: flex;
  align-items: center;
  font-size: 30px;
  height: 100%;
  margin-left: 20px;
  margin-right: 10px;
}
.page-progress {
  font-size: 30px;
  display: flex;
  justify-content: center;
  margin: -10px 0px 20px 0px;
}
.start,
.pause {
  width: 70px;
  height: 70px;
  cursor: pointer;
  margin-top: 20px;
}
.stop {
  width: 70px;
  height: 70px;
  cursor: pointer;
  margin-top: 20px;
  margin-left: 20px;
  border-radius: 50%;
  border: 1px solid black;
}
.stopbtn{
    display: flex;
    justify-content: center;
}
</style>

<template>
    <div v-if="taleDetailStore.pages">
        <div class="play">
            <div class="info">
                <div class="title">{{ taleDetailStore.tale.title }}</div>
                <!-- <img class="like" src="@/assets/like.png" /> -->
                <img class="like" src="@/assets/unlike.png" />
            </div>
            <div class="book">
                <div class="cover"><img :src="taleDetailStore.tale.thumbnail" class="coverImg" /></div>
                <div class="flip-book">
                    <div
                        class="flip"
                        v-for="(page, index) in pages"
                        :key="`page-${index}`"
                        :class="{ flipped: page.flipped }"
                        :style="{ zIndex: zIndexes[index] }"
                    >
                        <div class="back" v-if="index >= 1">
                            <div class="page-separator-left"></div>
                            <img :src="pages[index - 1].left" class="leftImg" />
                            <img src="@/assets/leftarrow.gif" class="left" />
                            <img
                                src="@/assets/leftarrowstatic.png"
                                class="leftstatic"
                                @click="flipPage(index, false)"
                            />
                        </div>
                        <div class="front pre-wrap">
                            <div class="page-separator-right"></div>
                            {{ pages[index].right }}
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
            <div class="container">
                <!-- 배열의 각 요소에 대해 반복하며 div를 생성 -->
                <div
                    v-for="index in pages.length"
                    :key="index"
                    :class="{ 'active-bar': index === pages.length - currentPageIndex }"
                    class="bar"
                >
                    <div class="bar-elem" @click="goSessino(index)">{{ index }}</div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>is Loading...</div>
</template>
<script setup>
import { ref, computed, reactive, onMounted, watch } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";
import { generateTTS } from "@/util/ttsSpeakerUtil";

const taleDetailStore = useTaleDetailStore();
const router = useRouter();
const pages = taleDetailStore.pages.reverse();

//url의 param 받기
const props = defineProps({
    speaker: String,
});
const goToEnd = () => {
    router.push({ name: "taleEnd", params: { taleId: taleDetailStore.taleId } });
};
// 페이지 배열의 인덱스를 저장해서 zindex 배열로 만듦
// zindex는 클수록 위쪽에 위치함
// map함수로 pages를 돌면서 index + 1을 저장하고 있음
const zIndexes = reactive(pages.map((_, index) => index + 1));
// 현재 페이지의 인덱스를 저장하는 변수
const currentPageIndex = ref(pages.length - 1);
// index: 뒤집을 페이지의 인덱스
// flip: true; 뒤집은 상태, false; 이전 페이지 펼친 상태
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
            console.log("true플립에서 감지한 인덱스 : ", currentPageIndex.value, " index: ", index);
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
        console.log("false플립에서 감지한 인덱스 : ", currentPageIndex.value, " index: ", index);
    }
}
//하단 바를 위한 발악
const goSessino = (index) => {
    if (index > currentPageIndex.value) {
        console.log(
            "*******click index : ",
            index,
            "\n currPageIndex: ",
            currentPageIndex.value,
            "\n pages.lenght : ",
            pages.length
        );
        flipPage(index, false);
    } else {
        console.log(
            "*******click index : ",
            index,
            "\n currPageIndex: ",
            currentPageIndex.value,
            "\n pages.lenght : ",
            pages.length
        );
        flipPage(index, true);
    }
};
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
const ttsMaker = async (text) => {
    //speaker정보
    const selectedSpeaker = items.value.find((item) => item.speaker == props.speaker);
    console.log("ttsMaker실행 : ", selectedSpeaker);
    if (selectedSpeaker) {
        const speakerName = selectedSpeaker.speaker;
        const emotion = selectedSpeaker.emotion;
        const emotionStrength = selectedSpeaker["emotion-strength"];
        console.log(text, " ", speakerName, " ", emotion, " ", emotionStrength);
        try {
            const blob = await generateTTS(text, speakerName, emotion, emotionStrength);
            const url = URL.createObjectURL(blob);
            audioSrcCache[text] = url; // 결과를 캐시에 저장
            console.log("ttsMaker생성 : ", url);
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
        audioRef.value.pause();
    }
    console.log("라디오 끊은 후, audioRef.value: ", audioRef.value, "\n text: ", text);
    // 이미 캐시된 결과가 있는지 확인
    if (audioSrcCache[text] != null) {
        console.log("캐시에서 걸림 : ", audioSrcCache[text]);
        audioRef.value = new Audio(audioSrcCache[text]);
        // audioRef.value.play(); // 재생
        // 재생이 끝나면 Promise를 resolve하도록 설정
        audioRef.value.onended = () => {
            onAudioEnded();
            resolve();
        };
        audioRef.value.play(); // 재생
    } else {
        console.log("ttsMaker() 호출, text: ", text);
        ttsMaker(text).then((url) => {
            if (url) {
                audioRef.value = new Audio(url); // 새로운 오디오를 할당
                // audioRef.value.play(); // 재생
                // 재생이 끝나면 Promise를 resolve하도록 설정
                audioRef.value.onended = () => {
                    onAudioEnded();
                    resolve();
                };
                audioRef.value.play(); // 재생
            }
        });
    }
    //캐쉬값 확인용
    // for (const key in audioSrcCache) {
    //     console.log(`Key: ${key}, Value: ${audioSrcCache[key]}`);
    // }
};
// 오디오 재생이 끝날 때 실행되는 콜백 함수
const onAudioEnded = () => {
    if (currentPageIndex.value < pages.length) flipPage(currentPageIndex.value, true);
};
// //페이지 변화를 감지해서 틈
watch(pages, (newPages, oldPages) => {
    if (newPages && newPages.length > 0) {
        // 페이지 배열이 변경되었을 때 실행할 코드 작성
        console.log("watch에서 감지한 인덱스 : ", currentPageIndex.value);
        autoAudio(newPages[currentPageIndex.value].right); // 첫 번째 페이지의 오른쪽 텍스트를 넘김
    }
});
onMounted(async () => {
    console.log("TalePlay component is mounted");
    try {
        if (pages.length > 0) {
            console.log("onMounted's currentPageIndex: ", currentPageIndex.value);
            await autoAudio(pages[currentPageIndex.value].right);
        }
    } catch (error) {
        console.error("Error in onMounted:", error);
    }
    console.log("마운트 끝");
});

userStore.getUser();

const favorite = ref({
  userId: userStore.userInfo.userId,
  taleId: parseInt(taleDetailStore.taleId),
});

console.log(favorite.value);

const postFavorite = () => {
  axios
    .post("http://localhost:8080/favorites", favorite.value)
    .then((res) => {
      console.log(res.data);
      getFavorite();
    })
    .catch((err) => console.log(err));
};

const favoriteId = ref(null);
const getFavorite = () => {
  axios
    .get(`http://localhost:8080/favorites/${userStore.userInfo.userId}/${taleDetailStore.taleId}`)
    .then((res) => {
      favoriteId.value = res.data;
    })
    .catch((err) => console.log(err));
};

const deleteFavorite = () => {
  axios
    .delete(`http://localhost:8080/favorites/${favoriteId.value}`)
    .then((res) => {
      favoriteId.value = null;
    })
    .catch((err) => console.log(err));
};
</script>
<style scoped>
.container {
    display: flex;
}
.bar {
    flex: 1; /* 각 요소가 동일한 너비를 가지도록 함 */
    padding: 10px;
    border: 1px solid #342121;
    margin-right: 0px; /* 각 요소 사이의 간격을 위해 사용 */
    background-color: aquamarine;
    height: 2px;
    justify-content: center; /* 수평 정렬 */
    align-items: center; /* 수직 정렬 */
}
.active-bar {
    background-color: red; /* 원하는 색상으로 변경 */
    /* 기타 스타일 설정 */
}
.bar:last-child {
    margin-right: 0; /* 마지막 요소의 오른쪽 마진 제거 */
}
.bar-elem {
    font-size: 12px;
}
.play {
    width: 1180px;
    height: 800px;
    border: 15px solid #cee8e8;
    margin: 20px 0px 30px 40px;
    border-radius: 50px;
    background-color: #f5f5f5;
}
.info {
    display: flex;
    justify-content: space-between;
}
.title {
    text-decoration: none;
    display: inline-block;
    box-shadow: inset 0 -20px 0 #ffd3d3;
    font-size: 40px;
    margin: 60px 0px 40px 70px;
    line-height: 1;
}
.like {
    width: 50px;
    height: 50px;
    margin: 50px 70px 0px 0px;
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
    width: 450px;
    height: 450px;
    margin-top: 25px;
    margin-left: 5px;
}
.book {
    margin: 0px 0px 0px 45px;
    padding: 10px 10px 0px 25px;
    display: flex;
    background-color: #21364d;
    width: 1050px;
    height: 520px;
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
</style>

<template>
    <div>
        <loading-modal :is-loading="loading"></loading-modal>
        <div class="talelistContainer">
            <h1>{{ customTaleStore.customTale.title }}</h1>

            <div class="twoContainer">
                <div class="info-column">
                    <div class="info" v-if="!play">
                        <h3>목소리 선택</h3>
                        <div v-for="item in items" :key="item.speaker" class="form-wrapper align-items-center">
                            <label
                                ><input type="radio" :value="item.speaker" v-model="speaker" :name="item.name" />
                                {{ item.name }}
                                <img
                                    src="https://talebucket.s3.ap-northeast-2.amazonaws.com/volume_up_FILL0_wght400_GRAD0_opsz24.svg"
                                    alt="Speaker Image"
                                    class="speaker-image"
                                    @click="playVoice(item.url)"
                                />
                            </label>
                        </div>
                        <div class="btn">
                            <button @click="readBook">재생</button>
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
    </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import LoadingModal from "@/components/customTale/loadingModal.vue";
import axios from "axios";

const route = useRoute();
const router = useRouter();

const customTaleStore = useCustomTaleStore();

const play = ref(false);
const playbtn = function () {
    play.value = true;
};

//tts
const speaker = ref("vdain");
const items = ref([
    { name: "다인", speaker: "vdain", url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vdain.mp3" },
    { name: "고은", speaker: "vgoeun", url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vgoeun.mp3" },
    { name: "미경", speaker: "vmikyung", url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vmikyung.mp3" },
    { name: "이안", speaker: "vian", url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vian.mp3" },
    { name: "대성", speaker: "vdaeseong", url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/vdaeseong.mp3" },
    { name: "원탁", speaker: "nwontak", url: "https://talebucket.s3.ap-northeast-2.amazonaws.com/nwontak.mp3" },
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
console.log("customTaleStore.customTaleText", customTaleStore.customTaleText);
console.log("customTaleStore.customTaleText.value", customTaleStore.customTaleText.value);
console.log("customTaleStore.customTale", customTaleStore.customTale);
console.log("customTaleStore.customTale.value", customTaleStore.customTale.value);
console.log();
console.log();
//start go customPlay
const readBook = async () => {
    try {
        console.log("요청 보냄");
        await customTaleStore.readCustomTale(customTaleStore.customTale.content);
        console.log(customTaleStore.pages);
        // 요청이 성공적으로 완료된 후에 navigateToTalePlay 호출
        console.log("요청이 성공적으로 완료된 후에 navigateToTalePlay 호출");
        navigateToTalePlay();
    } catch (error) {
        console.error("Error fetching:", error);
    }
};

const navigateToTalePlay = () => {
    console.log("다음페이지 보내기");
    const selectedSpeaker = items.value.find((item) => item.speaker === speaker.value);
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
    const randomCustomTales = shuffleArray(customTaleStore.customTalesList).slice(0, 4);
    getRandomCustomTales.value = randomCustomTales;
});
</script>

<style>
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
    width: 45%;
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
</style>

<template>
    <div v-if="taleDetailStore.tale">
        <div class="play">
            <div class="book">
                <div class="info">
                    <div class="title">{{ taleDetailStore.tale.title }}</div>
                </div>
                <div class="container">
                    <div class="imgNselect leftImg">
                        <img :src="taleDetailStore.tale.thumbnail" />
                    </div>
                    <div class="imgNselect">
                        <div class="selected">
                            <h1>오늘의 주인공</h1>
                            <div class="selectbox">
                                <select
                                    class="form-select"
                                    aria-label="Default select example"
                                    v-model="selectedCharacter"
                                >
                                    <option
                                        v-for="character in taleDetailStore.tale.characters"
                                        :key="character"
                                        :value="character"
                                        :disabled="isCharacterSelected(character)"
                                        class="character"
                                    >
                                        {{ character }}
                                    </option>
                                </select>
                                <select class="form-select" aria-label="Default select example" v-model="selectedName">
                                    <option v-for="(child, index) in userStore.userInfo.childrenList" :key="index">
                                        {{ child.childName }}
                                    </option>
                                </select>
                            </div>
                            <div v-for="(name, index) in nameMap" :key="index">
                                <h2 class="nameMap">
                                    {{ name[0] }} -> {{ name[1] }} <button @click="deleteName(index)">삭제</button>
                                </h2>
                            </div>
                        </div>
                        <h1>목소리 선택</h1>
                        <div class="align-items-center grid-container">
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
                        </div>
                        <button class="btn btn-primary" type="submit" @click="readBook">동화 시작하기</button>
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
import { ref, computed, watch } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import { useUserStore } from "@/stores/userStore";
import axios from "axios";
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps({
    taleId: String,
});
const taleDetailStore = useTaleDetailStore();
taleDetailStore.taleId = props.taleId;
taleDetailStore.getTaleDetail();
const userStore = useUserStore();
userStore.getUser();
console.log(userStore.userInfo);
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
const selectedCharacter = ref();
const selectedName = ref();
const nameMap = ref([]);
watch([selectedCharacter, selectedName], ([newCharacter, newName]) => {
    if (newCharacter && newName) {
        nameMap.value.push([newCharacter, newName]);
        console.log(nameMap.value);
        selectedCharacter.value = null;
        selectedName.value = null;
    }
});
function isCharacterSelected(character) {
    return nameMap.value.some(([selectedCharacter]) => selectedCharacter === character);
}
function deleteName(index) {
    nameMap.value.splice(index, 1);
}
const readBook = async () => {
    try {
        console.log("요청 보냄");
        // 이름 바꾸기
        for (const [cname, bname] of nameMap.value) {
            axios.post(`http://localhost:8080/tales/${cname}/${bname}`);
        }
        const response = await axios.post("http://localhost:8080/tales/read", taleDetailStore.tale);
        taleDetailStore.pages = response.data;
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
        name: "talePlay",
        params: { speaker: selectedSpeaker.speaker },
    });
};
</script>
<style scoped>
.container {
    display: grid;
    grid-template-columns: auto auto;
    justify-content: center;
    padding: 0 4px;
}
.selectbox {
    display: flex;
}
.selected {
    height: 300px;
    background-color: #f3f0cb;
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
.imgNselect {
    float: left;
    margin: 0 10px;
}
.speaker-image {
    height: 25px;
    width: 25px;
}
.character {
    border: blue;
}
.nameMap {
    color: rgb(31, 28, 35);
    border-radius: 4px solid blue;
}
.grid-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* 세 개의 열을 가진 그리드 생성 */
    gap: 10px; /* 열 사이의 간격 설정 */
}
</style>

<template>
    <div v-if="taleDetailStore.tale">
        <h1>{{ taleDetailStore.tale.title }}</h1>
        <img :src="taleDetailStore.tale.thumbnail" />

        <div class="selected">
            <h1>오늘의 주인공</h1>

            <div class="selectbox">
                <select class="form-select" aria-label="Default select example" v-model="selectedCharacter">
                    <option
                        v-for="(character, index) in taleDetailStore.tale.characters"
                        :key="index"
                        :disabled="isCharacterSelected(character)"
                    >
                        {{ character }}
                    </option>
                </select>

                <select class="form-select" aria-label="Default select example" v-model="selectedName">
                    <option value="다윤">다윤</option>
                    <option value="지민">지민</option>
                    <option value="성주">성주</option>
                    <option value="우진">우진</option>
                    <option value="아진">아진</option>
                    <option value="소연">소연</option>
                </select>
            </div>

            <div v-for="(name, index) in nameMap" :key="index">
                <h2>{{ name[0] }} -> {{ name[1] }} <button @click="deleteName(index)">삭제</button></h2>
            </div>
        </div>

        <h1>목소리 선택</h1>
        <div>
            <div v-for="item in items" :key="item.speaker">
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
    <div v-else>
        <div>is Loading...</div>
    </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();

const taleDetailStore = useTaleDetailStore();

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

const props = defineProps({
    taleId: String,
});

taleDetailStore.taleId = props.taleId;
taleDetailStore.getTaleDetail();

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
button {
    border: 1px solid burlywood;
}

.selectbox {
    display: flex;
}

.selected {
    height: 300px;
    background-color: #c4ecb0;
}
</style>

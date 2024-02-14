<template>
    <div v-if="taleDetailStore.tale">
        <div class="play">
            <div class="likes">
                <div v-if="favoriteId">
                    <img class="like" src="@/assets/like.png" @click="deleteFavorite()" />
                </div>
                <div v-else>
                    <img class="like" src="@/assets/dislike.png" @click="postFavorite()" />
                </div>
                <div class="likecnt">{{ likeCnt }}</div>
            </div>
            <div class="container">
                <div class="leftImg">
                    <div class="title">{{ taleDetailStore.tale.title }}</div>
                    <img class="thumbnail" :src="taleDetailStore.tale.thumbnail" />
                </div>
                <div class="chat">
                    <div class="smallbox">
                        <div class="selectbox">
                            <div class="select">
                                <img class="titleimg" src="@/assets/rabbit.png" />
                                <div class="selecttitle">등장인물</div>
                            </div>
                            <select class="form-select" aria-label="Default select example" v-model="selectedCharacter">
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
                            <div class="select">
                                <img class="titleimg" src="@/assets/boy.png" />
                                <div class="selecttitle">오늘의 주인공</div>
                            </div>
                            <select class="form-select" aria-label="Default select example" v-model="selectedName">
                                <option v-for="(child, index) in userStore.userInfo.childrenList" :key="index">
                                    {{ child.childName }}
                                </option>
                            </select>
                        </div>
                        <div class="changedbox">
                            <div class="chattitle"><img src="@/assets/surprise.png" class="surprise" />변신 상자</div>
                            <div class="selectedbox">
                                <div v-for="(name, index) in nameMap" :key="index">
                                    <div class="namebox">
                                        <div class="namemap">
                                            {{ name[0] }} <img class="change" src="@/assets/change.png" /> {{ name[1] }}
                                        </div>
                                        <img src="@/assets/delete.png" @click="deleteName(index)" class="delete" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="startbox">
                        <div class="voicebox">
                            <div class="voicetitle"><img class="mic" src="@/assets/mic.png" />목소리 선택</div>
                            <div class="align-items-center grid-container">
                                <div v-for="item in items" :key="item.speaker" class="form-wrapper align-items-center">
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
                        <div class="rocketborder" @click="readBook">
                            <img src="@/assets/rocket.png" class="rocket" />
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
import { ref, computed, watch, onMounted } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import { useUserStore } from "@/stores/userStore";
import axios from "@/util/http-common";
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
    const requests = Array.from(nameMap.value).map(([cname, bname]) =>
      axios.post(`/tales/${cname}/${bname}`, { withCredentials: true })
    );

    await Promise.all(requests);

    const response = await axios.post("/tales/read", taleDetailStore.tale, {
      withCredentials: true,
    });
    taleDetailStore.pages = response.data;
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
        params: { speaker: selectedSpeaker.speaker, taleId: taleDetailStore.taleId },
    });
};
const favorite = ref({
    // userId: userStore.userInfo.userId,
    taleId: props.taleId,
});
console.log(favorite.value);
const postFavorite = () => {
  axios
    .post("/favorites", favorite.value, { withCredentials: true })
    .then((res) => {
      console.log(res.data);
      getFavorite();
      getLikeCnt();
    })
    .catch((err) => console.log(err));
};
const favoriteId = ref(null);
const getFavorite = () => {
  axios
    .get(`/favorites/${props.taleId}`, { withCredentials: true })
    .then((res) => {
      favoriteId.value = res.data;
    })
    .catch((err) => console.log(err));
};
const deleteFavorite = () => {
  axios
    .delete(`http://localhost:8080/favorites/${favoriteId.value}`, { withCredentials: true })
    .then((res) => {
      favoriteId.value = null;
      getFavorite();
      getLikeCnt();
    })
    .catch((err) => console.log(err));
};
const likeCnt = ref(null);
const getLikeCnt = () => {
  axios
    .get(`/tales/like/${props.taleId}`)
    .then((res) => {
      return axios.get(`/tales/${props.taleId}`, { withCredentials: true });
    })
    .then((res) => {
      likeCnt.value = res.data.likeCnt;
      console.log(res.data);
    })
    .catch((err) => {
      console.log(err);
    });
};

onMounted(() => {
    getFavorite();
    getLikeCnt();
});
</script>
<style scoped>
.play {
  width: 1050px;
  height: 630px;
  border: 5px solid #cee8e8;
  margin: 20px 0px 30px 0px;
  border-radius: 50px;
  background-color: white;
}
.container {
    display: flex;
    width: 1050px;
    margin: 0px 0 0 30px;
}
.title {
    margin: 30px 0px 25px 0px;
    font-size: 35px;
    width: 300px;
    text-align: center;
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
}
.smallbox {
    display: flex;
    justify-content: space-between;
    font-size: 25px;
    margin-bottom: 20px;
}
.selectbox {
  border: 5px solid #cee8e8;
  background-color: white;
  border-radius: 30px;
  width: 250px;
  height: 260px;
  padding: 0px 15px 10px 15px;
}
.selectedbox {
  border: 5px solid #cee8e8;
  background-color: white;
  border-radius: 30px;
  width: 250px;
  height: 240px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.select {
    display: flex;
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

.surprise {
    width: 27px;
    height: 27px;
    margin-right: 10px;
}
.chattitle {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 5px solid #cee8e8;
  border-radius: 30px;
  width: 180px;
  height: 55px;
  text-align: center;
  margin: 0 0 -35px 35px;
  background-color: #ebffdf;
  position: relative;
  z-index: 5;
  font-size: 23px;
}
@keyframes swing {
    0% {
        transform: translateX(-3px);
    }
    50% {
        transform: translateX(3px);
    }
    100% {
        transform: translateX(-3px);
    }
}
.change {
    animation: swing 1s ease-in-out infinite;
    width: 35px;
    height: 35px;
}
.namebox {
    display: flex;
}
.namemap {
    background-color: white;
    border-radius: 10px;
    padding: 0px 10px 0px 10px;
    margin-bottom: 10px;
    font-size: 22px;
    border: 1px solid yellow;
}
.delete {
    width: 35px;
    height: 35px;
    cursor: pointer;
    margin-top: 3px;
}
.speaker-image {
    height: 25px;
    width: 25px;
    cursor: pointer;
}
.startbox {
    display: flex;
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
  height: 55px;
  text-align: center;
  margin: -30px 0px 0px 80px;
  background-color: #ebffdf;
  position: relative;
  z-index: 5;
  font-size: 23px;
}
.voicebox {
  border: 5px solid #cee8e8;
  background-color: white;
  border-radius: 30px;
  font-size: 20px;
  width: 370px;
  height: 150px;
  margin: 30px 20px 0 0;
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
.likes {
    display: flex;
    margin: 30px 0 25px 30px;
}
.like {
    width: 50px;
    height: 50px;
    cursor: pointer;
    margin-right: 10px;
}
.likecnt {
    font-size: 30px;
}
.starttitle {
  font-size: 23px;
  margin-left: 35px;
}
.readstart {
  margin: 20px 10px 0 0;
  width: 150px;
  height: 150px;
  border: 2px solid #d0d0d0;
  border-radius: 50%;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 3px 3px 5px 0px #0002;
  background-color: white;
  font-size: 25px;
}
</style>

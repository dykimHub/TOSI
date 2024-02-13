<template>
  <div v-if="taleDetailStore.tale && userStore.userInfo">
    <div class="end">
      <div class="book">
        <img src="@/assets/sticker.png" class="sticker" />
        <div class="random">
          <div class="randomc">FROM. {{ randomC }}</div>
          <div class="randomg">{{ randomG }}</div>
        </div>
        <div class="btns">
          <button class="replaybtn" @click="replay">다시 보기</button>
          <button class="listbtn" @click="list">나가기</button>
        </div>
      </div>
      <div class="chat">
        <div class="chattitle">등장인물을 만나보아요</div>
        <div class="selectbox">
          <div class="select">
            <img class="titleimg" src="@/assets/rabbit.png" />
            <div class="selecttitle">등장인물</div>
          </div>
          <select
            class="form-select"
            aria-label="Default select example"
            v-model="selectedCharacter"
          >
            <option v-for="character in taleDetailStore.tale.characters" :key="character.id">
              {{ character }}
            </option>
          </select>
          <div class="select">
            <img class="titleimg" src="@/assets/girl.png" />
            <div class="selecttitle">내 이름</div>
          </div>
          <select class="form-select" aria-label="Default select example" v-model="selectedName">
            <option v-for="child in userStore.userInfo.childrenList" :key="child.id">
              {{ child.childName }}
            </option>
          </select>
        </div>
        <button type="button" class="btn btn-light btn-primary chatstart" @click="chatStart">
          대화 시작하기
        </button>
      </div>
    </div>
    <!-- <img class="unfolded" src="@/assets/bookend.png" /> -->
  </div>
  <div v-else>is Loading...</div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import router from "@/router";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import { useUserStore } from "@/stores/userStore";
import axios from "axios";
import { onBeforeRouteLeave } from "vue-router";

const props = defineProps({
  taleId: String,
});

const taleDetailStore = useTaleDetailStore();
taleDetailStore.taleId = props.taleId;
taleDetailStore.getTaleDetail();

const userStore = useUserStore();
userStore.getUser();

console.log(userStore.userInfo);

const greetings = [
  "마법같은 이야기는 끝나지 않았고, 상상 속 세계가 기다리고 있어요.",
  "이 동화의 여정이 즐거웠기를 바라요. 또 만나요!",
  "우리의 특별한 이야기가 즐거운 추억이 되었으면 좋겠어요.",
  "함께한 모험을 잊지 않을게요, 꿈 속에서 만나요.",
  "매일매일 꿈꾸고 상상하는 거 잊지 말아요!",
  "별들도 우리 이야기를 듣고 싶어 하네요. 굿나잇, 작은 별들!",
  "조용한 밤, 우리의 이야기를 속삭여보세요. 우주도 함께 꿈꾸게 될 거예요.",
  "이 순간이 마음 속에 남아있기를 바라요. 다시 만날 그 날을 기다릴게요!",
  "이야기 세계는 늘 기다리고 있어요. 다음 모험을 기대해주세요.",
  "찾아와줘서 고마워요. 이야기는 잠시 멈췄지만, 우리의 웃음은 계속될 거예요!",
];

const randomGIndex = Math.floor(Math.random() * greetings.length);
const randomG = greetings[randomGIndex];

const randomCIndex = Math.floor(Math.random() * taleDetailStore.tale.characters.length);
const randomC = taleDetailStore.tale.characters[randomCIndex];

const replay = () => {
  router.push({ name: "taleDetail", params: { taleId: props.taleId } });
};

const list = () => {
  router.push({ name: "tales" });
};

const selectedCharacter = ref(taleDetailStore.tale.characters[0]);
const selectedName = ref(userStore.userInfo.childrenList[0].childName);

const chatStart = () => {
  router.push({
    name: "gptConversationSend",
    params: { cname: selectedCharacter.value, bname: selectedName.value },
  });
};
</script>

<style scoped>
.end {
  display: flex;
  justify-content: space-between;
  height: 430px;
  width: 1000px;
  margin-top: 50px;
  margin-bottom: 100px;
}
@keyframes popUp {
  0% {
    transform: scale(0); /* 출발점에서 요소의 크기를 0으로 설정 */
    opacity: 0; /* 완전히 투명하게 만들어서 보이지 않도록 함 */
  }
  60% {
    transform: scale(1.1); /* 약간 튀어나오는 효과를 주기 위해 크기를 조금 더 키움 */
    opacity: 1; /* 완전히 불투명하게 만들어서 요소가 보이도록 함 */
  }
  100% {
    transform: scale(1); /* 최종적으로 요소의 원래 크기로 설정 */
  }
}
.book {
  align-items: center;
  background-image: url(@/assets/book_end.png);
  background-size: cover;
  width: 433px;
  height: 446px;
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
  text-align: center;
  padding: 0 50px 0 40px;
}
.sticker {
  width: 150px;
  height: 50px;
  transform: rotate(10deg);
  margin-top: 35px;
  margin-left: 20px;
}
.random {
  background-color: white;
  height: 300px;
  margin-top: -10px;
}
.randomc {
  font-size: 28px;
  border-bottom: 3.5px solid black;
  padding-top: 40px;
  width: 280px;
  margin: auto;
}
.randomg {
  padding: 0 15px 0 15px;
  font-size: 27px;
  margin-top: 10px;
  text-align: center;
}
.btns {
  display: flex;
  justify-content: center;
}
.replaybtn,
.listbtn {
  margin: 10px 10px 0 0;
  width: 100px;
  height: 40px;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 3px 3px 5px 0px #0002;
  background-color: white;
}

.listbtn {
  margin: 10px 0 0 0;
}

.replaybtn:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}
.listbtn:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}
@keyframes shake {
  0%,
  100% {
    transform: rotate(0deg);
  }
  10%,
  30%,
  50%,
  70%,
  90% {
    transform: rotate(-3deg);
  }
  20%,
  40%,
  60%,
  80% {
    transform: rotate(3deg);
  }
}
.chattitle {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 5px solid #ebffdf;
  border-radius: 30px;
  width: 350px;
  height: 62px;
  text-align: center;
  margin-bottom: -35px;
  background-color: white;
  position: relative;
  z-index: 5;
  font-size: 27px;
  margin-left: 40px;
  margin-top: 20px;
}
.selectbox {
  border: 5px solid #ebffdf;
  border-radius: 30px;
  padding: 30px 50px;
  background-color: aliceblue;
  height: 350px;
  width: 420px;
}
.form-select {
  font-size: 25px;
}
.select {
  display: flex;
}
.titleimg {
  width: 40px;
  height: 40px;
  margin-top: 30px;
  margin-right: 10px;
}
.selecttitle {
  font-size: 28px;
  margin-top: 25px;
  margin-bottom: 3px;
}
.chatstart {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 115px;
  margin-top: -35px;
  width: 200px;
  height: 60px;
  font-size: 25px;
  border: 3px solid #b5ff43;
  border-radius: 30px;
}
</style>

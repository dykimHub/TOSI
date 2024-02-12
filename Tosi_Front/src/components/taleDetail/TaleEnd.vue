<template>
  <div v-if="taleDetailStore.tale && userStore.userInfo">
    <div class="end">
      <div class="book">
        <div class="greet">
          <div class="randomc">FROM. {{ randomC }}</div>
          <div class="randomg">{{ randomG }}</div>
          <button type="button" class="btn btn-light btn-lg replaybtn" @click="replay">
            다시 보기
          </button>
          <button type="button" class="btn btn-light btn-lg listbtn" @click="list">나가기</button>
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
    <img class="unfolded" src="@/assets/bookend.png" />
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
  height: 600px;
  width: 1350px;
  margin-top: 50px;
}
.book {
  display: flex;
  background-size: cover;
  width: 600px;
  height: 1100px;
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
.greet {
  width: 570px;
  height: 570px;
  background-image: url(@/assets/quote.png);
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  animation: popUp 1s ease-out forwards;
}
.randomc {
  width: 300px;
  font-size: 2.5vw;
  margin-top: 130px;
  margin-left: 130px;
  text-align: center;
  border-bottom: 3.5px solid black;
}
.randomg {
  width: 360px;
  font-size: 2.3vw;
  margin-left: 95px;
  margin-top: 5px;
  text-align: center;
}
.replaybtn {
  margin: 20px 15px 0 170px;
  border-radius: 50%;
  box-shadow: 0 4px #0056b3;
}
.listbtn {
  margin-top: 20px;
  border-radius: 50%;
  box-shadow: 0 4px #0056b3;
}
.unfolded {
  width: 1200px;
  height: 300px;
  margin-top: -30px;
  margin-bottom: 60px;
  animation: shake 3s forwards;
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
.chat {
  width: 450px;
  height: 650px;
  font-size: 25px;
  margin-top: 30px;
}
.chattitle {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 10px solid #ebffdf;
  border-radius: 30px;
  width: 370px;
  height: 65px;
  text-align: center;
  margin-left: 45px;
  margin-bottom: -35px;
  background-color: white;
  position: relative;
  z-index: 5;
  font-size: 27px;
}
.selectbox {
  border: 10px solid #ebffdf;
  border-radius: 30px;
  padding: 30px 50px;
  background-color: aliceblue;
  height: 400px;
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
  margin-top: 25px;
  margin-right: 10px;
}
.selecttitle {
  font-size: 28px;
  margin-top: 25px;
  margin-bottom: 10px;
}
.chatstart {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 125px;
  margin-top: -35px;
  width: 200px;
  height: 70px;
  font-size: 25px;
  border: 8px solid rgba(173, 255, 47, 0.6);
  border-radius: 30px;
}
</style>

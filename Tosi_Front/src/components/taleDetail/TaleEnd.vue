<template>
  <div v-if="taleDetailStore.tale">
    <div class="end">
      <div class="book">
        <h1>{{ taleDetailStore.tale.title }}</h1>
        <div v-for="(character, index) in taleDetailStore.tale.characters" :key="character.id">
          <h2>{{ character }}: {{ assignedGreeting[index] }}</h2>
        </div>
        <button type="button" class="btn btn-primary" @click="replay">다시 보기</button>
        <button type="button" class="btn btn-primary" @click="list">나가기</button>
      </div>
      <div class="chat">
        <h1>등장인물을 만나보아요</h1>
        <div class="selectbox">
          <h2>등장인물</h2>
          <select
            class="form-select"
            aria-label="Default select example"
            v-model="selectedCharacter"
          >
            <option v-for="(character, index) in taleDetailStore.tale.characters" :key="index">
              {{ character }}
            </option>
          </select>

          <h2>내 이름</h2>
          <select class="form-select" aria-label="Default select example" v-model="selectedName">
            <option value="다윤">다윤</option>
            <option value="지민">지민</option>
            <option value="성주">성주</option>
            <option value="우진">우진</option>
            <option value="아진">아진</option>
            <option value="소연">소연</option>
          </select>
        </div>
        <button type="button" class="btn btn-primary" @click="chatStart">대화 시작하기</button>
      </div>
    </div>
  </div>
  <div v-else>is Loading...</div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import router from "@/router";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import axios from "axios";

const taleDetailStore = useTaleDetailStore();

const props = defineProps({
  taleId: String,
});

taleDetailStore.taleId = props.taleId;
taleDetailStore.getTaleDetail();

const greetings = [
  "마법같은 이야기는 끝나지 않았고, 상상 속 세계가 기다리고 있어요.",
  "이 동화의 여정이 즐거웠기를 바라요. 또 만나요!",
  "우리의 특별한 이야기가 즐거운 추억이 되었으면 좋겠어요.",
  "함께한 모험을 잊지 않을게요, 꿈 속에서 만나요.",
  "매일매일 꿈꾸고 상상하는 거 잊지 말아요!",
  "별들도 우리 이야기를 듣고 싶어 하네요. 굿나잇, 작은 별들!",
  "조용한 밤, 우리의 이야기를 속삭여보세요. 우주도 함께 꿈꾸게 될 거예요.",
  "이 순간이 마음 속에 남아있기를 바라요. 다시 만날 그날을 기다릴게요!",
  "이야기 세계는 늘 기다리고 있어요. 다음 모험을 상상해보세요.",
  "찾아와줘서 고마워요. 이야기는 잠시 멈췄지만, 우리의 웃음은 계속될 거예요!",
];

const assignedGreeting = ref([]);
onMounted(() => {
  for (let i = 0; i < taleDetailStore.tale.characters.length; i++) {
    assignedGreeting.value.push(getRandomGreeting());
  }
});

const getRandomGreeting = () => {
  const randomIndex = Math.floor(Math.random() * greetings.length);
  const selectedGreeting = greetings.splice(randomIndex, 1)[0];
  return selectedGreeting;
};

const replay = () => {
  router.push({ name: "taleDetail", params: { taleId: props.taleId } });
};

const list = () => {
  router.push({ name: "tales" });
};

const selectedCharacter = ref();
const selectedName = ref();

const chatStart = () => {
  router.push({
    name: "gptConversation",
    params: { cname: selectedCharacter.value, bname: selectedName.value },
  });
};
</script>

<style scoped>
.end {
  display: flex;
}
</style>

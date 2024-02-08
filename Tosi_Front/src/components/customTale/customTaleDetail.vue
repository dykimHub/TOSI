<template>
  <div>
    <loading-modal :is-loading="loading"></loading-modal>
    <div class="talelistContainer">
      <h1>{{ customTaleStore.customTale.title }}</h1>

      <div class="twoContainer">
        <div class="info-column">
          <div class="info" v-if="!play">
            <h3>목소리 선택</h3>
            <div>
              <div v-for="item in items" :key="item.speaker">
                <label
                  ><input
                    type="radio"
                    :value="item.speaker"
                    v-model="speaker"
                    :name="item.name"
                  />
                  {{ item.name }}</label
                >
              </div>
              <div>선택한 목소리 : {{ speaker }}</div>
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

const route = useRoute();
const router = useRouter();

const customTaleStore = useCustomTaleStore();

const play = ref(false);
const playbtn = function () {
  play.value = true;
};

const speaker = ref("vdain");
const items = ref([
  { name: "다인", speaker: "vdain", emotion: 3, "emotion-strength": 1 },
  { name: "고은", speaker: "vgoeun", emotion: 3, "emotion-strength": 1 },
  { name: "미경", speaker: "vmikyung", emotion: 3, "emotion-strength": 1 },
  { name: "이안", speaker: "vian", emotion: 0, "emotion-strength": 0 },
  { name: "대성", speaker: "vdaeseong", emotion: 3, "emotion-strength": 1 },
  { name: "원탁", speaker: "nwontak", emotion: 0, "emotion-strength": 0 },
]);

const getRandomCustomTales = ref([]);
function shuffleArray(array) {
const shuffledArray = array.slice();
for (let i = shuffledArray.length - 1; i > 0; i--) {
  const j = Math.floor(Math.random() * (i + 1));
  [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
}
return shuffledArray;
}

const readBook = async () => {
  try {
    await customTaleStore.readCustomTale(customTaleStore.customTale.content)
    console.log(customTaleStore.pages);
    navigateToTalePlay();
  } catch (error) {
    console.error("Error fetching:", error);
  }
};

const navigateToTalePlay = () => {
  const selectedSpeaker = items.value.find((item) => item.speaker === speaker.value);
  router.push({
    name: "customTalePlay",
    params: { speaker: selectedSpeaker.speaker },
  });
};

onMounted(async () => {
  // 비동기로 데이터를 먼저 로드
  await Promise.all([
    customTaleStore.getCustomTalesList(true),
    customTaleStore.getCustomTale(route.params.customTaleId),
  ]);
  console.log(customTaleStore.customTalesList);
  // 로드된 데이터를 기반으로 랜덤 아이템 선택
  const randomCustomTales = shuffleArray(customTaleStore.customTalesList).slice(
    0,4);
    getRandomCustomTales.value = randomCustomTales;
  }
  );
  
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

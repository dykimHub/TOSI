<template>
  <div>
    <loading-modal :is-loading="loading"></loading-modal>
    <div>
     <h1>{{ customTaleStore.customTale.title }}</h1>
    </div>
      <img
      :src="customTaleStore.customTale.thumbnail"
      class="img-fluid"
      style="height: 300px"
      alt="커스텀이미지"
    />
      
      <div>{{ customTaleStore.customTale.content }}</div>
  </div>

  <div>
      <p>친구들의 동화 보기</p>
      <ul>
    <li v-for="customTale in getRandomCustomTales" :key="customTale.customTaleId">
      <router-link :to="`/customtale/${customTale.customTaleId}`">
        <img
          :src="customTale.thumbnail"
          class="img-fluid"
          style="height: 300px"
          alt="커스텀이미지"
        />
        {{ customTale.title }}
      </router-link>
    </li>
  </ul>

  
  </div>
</template>

<script setup>
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter()

const customTaleStore = useCustomTaleStore();
const getRandomCustomTales = ref([]);

onMounted(async () => {
// 비동기로 데이터를 먼저 로드
await Promise.all([
  customTaleStore.getCustomTalesList(true),
  customTaleStore.getCustomTale(route.params.customTaleId)
]);
console.log(customTaleStore.customTalesList);
// 로드된 데이터를 기반으로 랜덤 아이템 선택
const randomCustomTales = shuffleArray(customTaleStore.customTalesList).slice(0, 4);
getRandomCustomTales.value = randomCustomTales;
console.log(randomCustomTales);
});

function shuffleArray(array) {
const shuffledArray = array.slice();
for (let i = shuffledArray.length - 1; i > 0; i--) {
  const j = Math.floor(Math.random() * (i + 1));
  [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
}
return shuffledArray;
}
</script>

<style>

</style>
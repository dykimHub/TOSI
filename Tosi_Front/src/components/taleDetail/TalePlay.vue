<template>
  <div v-if="taleDetailStore.pages">
    <div>
      <div class="page">
        <img :src="paginatedList[0].left" />
        <h1 class="pre-wrap">{{ paginatedList[0].right }}</h1>
      </div>
      <button @click="prevPage">이전</button>
      <button @click="nextPage">다음</button>
    </div>
  </div>
  <div v-else>is Loading...</div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useTaleDetailStore } from "@/stores/taleDetailStore";
import { useRouter } from "vue-router";

const taleDetailStore = useTaleDetailStore();
const router = useRouter();

console.log(taleDetailStore.pages);

const props = defineProps({
  speaker: String,
});

// 현재 페이지
const currentPage = ref(1);
// 페이지당 아이템은 1개
const itemsPerPage = ref(1);

const paginatedList = computed(() => {
  // 페이지 네이션 시작 인덱스는 (현재 페이지 - 1) * 아이템
  const start = (currentPage.value - 1) * itemsPerPage.value;
  // 페이지네이션 끝 인덱스
  const end = start + itemsPerPage.value;
  return taleDetailStore.pages.slice(start, end);
});

const nextPage = () => {
  // 최대 페이지 = 전체 페이지 수 / 아이템
  const maxPage = Math.ceil(taleDetailStore.pages.length / itemsPerPage.value);
  // 현재 페이지가 최대 페이지보다 작으면 현재 페이지++
  if (currentPage.value < maxPage) currentPage.value++;
  else router.push({ name: "taleEnd", params: { taleId: taleDetailStore.taleId } });
};

// 이전 페이지
const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};
</script>

<style scoped>
button {
  border: 1px solid burlywood;
}

.page {
  display: flex;
}

img {
  width: 700px;
  height: 700px;
}

.pre-wrap {
  white-space: pre-wrap;
}
</style>

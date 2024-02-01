<template>
  <h2>동화 목록</h2>
  <hr />
  <div class="selecSort">
    <label>정렬 기준</label>
    <select v-model="sortOption">
      <option value="title">이름순</option>
      <option value="likeCnt">인기순</option>
      <option value="random">랜덤</option>
    </select>
  </div>
  <div class="taleContainer">
    <ul v-for="tale in Talestore.taleList" :key="tale.taleId">
      <div class="oneTale">
        <RouterLink :to="`/tales/${tale.taleId}`">{{ tale.title }}</RouterLink>
        <br />
        재생 시간: {{ tale.time }}
      </div>
    </ul>
  </div>
</template>

<script setup>
import { useTaleStore } from '@/stores/taleStore';
import { onMounted, ref, watch } from 'vue';

const Talestore = useTaleStore();

const sortOption = ref('title');
const sortedTaleList = ref([]);

const sortTaleList = () => {
  switch (sortOption.value) {
    case 'title':
      sortedTaleList.value = Talestore.taleList.sort((a, b) => a.title.localeCompare(b.title));
      break;
    case 'likeCnt':
      sortedTaleList.value = Talestore.taleList.sort((a, b) => b.likeCnt - a.likeCnt);
      break;
    case 'random':
      sortedTaleList.value = Talestore.taleList.sort(() => Math.random() - 0.5);
      break;
    default:
      break;
  }
};

onMounted(() => {
  Talestore.getTaleList();
  watch(() => Talestore.taleList, sortTaleList, { immediate: true });
  watch(sortOption, sortTaleList);
});
</script>

<style scoped>
.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
}

.oneTale {
  padding: 3em;
  text-align: center;
  margin: 80px;
}

.selecSort {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
</style>

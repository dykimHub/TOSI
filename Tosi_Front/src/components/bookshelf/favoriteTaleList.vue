<template>
  <div class="talelistContainer">
    <div class = topOfTaleList>
    <h2>즐겨찾기 동화 목록</h2>
    <div class="selecSort">
        <label>정렬 기준</label>
        <select v-model="sortOptionC">
            <option value="title">이름순</option>
            <option value="likeCnt">인기순</option>
            <option value="random">랜덤</option>
        </select>
    </div>
    </div>
    <div class="taleContainer">
        <ul v-for="tale in favoriteStore.taleList" :key="tale.favoriteId">
            <div class="oneTale">
                <RouterLink :to="`/tale/${tale.taleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
                <br>
                <RouterLink :to="`/tale/${tale.taleId}`">{{ tale.title }}</RouterLink>
                <br>
                재생 시간: {{ tale.time }}
            </div>
            <button @click="deleteTale">삭제</button>
        </ul>
    </div>
  </div>
</template>

<script setup>
import { useFavoriteStore } from '@/stores/favoriteStore';
import { onMounted, ref, watch } from 'vue';

const favoriteStore = useFavoriteStore();

const sortOptionC = ref('title');
const sortedTaleList = ref([]);

const sortTaleList = () => {
  switch (sortOptionC.value) {
    case 'title':
      sortedTaleList.value = favoriteStore.myFavoriteTalesList.sort((a, b) => a.title.localeCompare(b.title));
      break;
    case 'likeCnt':
      sortedTaleList.value = favoriteStore.myFavoriteTalesList.sort((a, b) => b.likeCnt - a.likeCnt);
      break;
    case 'random':
      sortedTaleList.value = favoriteStore.myFavoriteTalesList.sort(() => Math.random() - 0.5);
      break;
    default:
      break;
  }
};

onMounted(() => {
  favoriteStore.getMyFavoriteTalesList();
  watch(() => favoriteStore.myFavoriteTalesList, sortTaleList, { immediate: true });
  watch(sortOptionC, sortTaleList);
});

const deleteTale = function (favoriteId) {
  favoriteStore.deleteMyFavoriteTale(favoriteId);
}
</script>

<style scoped>

.taleContainer {
    display: flex; 
    justify-content: center;
    flex-direction: row;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-around;
}

.talelistContainer{
  background-color:white;
  border-radius: 20px;
  margin: 35px;
  padding-top: 40px;
  opacity: 0.95;  
}

.thumbnail {
    width: 200px;
}

.oneTale {
    width: 13em;
    text-align: center;
    margin: 2em;
}

.selecSort {
    display: flex;
    flex-direction: column;
    margin: 1em;
}

.topOfTaleList{
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

a {
  text-decoration: none;
  color: black;
}
</style>

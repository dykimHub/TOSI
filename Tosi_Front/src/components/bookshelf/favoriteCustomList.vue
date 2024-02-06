<template>
    <div class="talelistContainer">
    <div class = topOfTaleList>
    <h2>내가 만든 동화 목록</h2>
    <div class="selecSort">
        <label>정렬 기준</label>
        <select v-model="sortOptionT">
            <option value="title">이름순</option>
            <!-- <option value="likeCnt">인기순</option> -->
            <option value="random">랜덤</option>
        </select>
    </div>
    </div>
    <div class="taleContainer">
        <ul v-for="tale in customTaleStore.taleList" :key="tale.taleId">
            <div class="oneTale">
                <RouterLink :to="`/tale/${tale.taleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
                <br>
                <RouterLink :to="`/tale/${tale.taleId}`">{{ tale.title }}</RouterLink>
                <br>
                재생 시간: {{ tale.time }}
            </div>
            <button @click="deleteCustomTale">삭제</button>
        </ul>
    </div>
    </div>
</template>

<script setup>
import { useCustomTaleStore } from '@/stores/customTaleStore';
import { onMounted, ref, watch } from 'vue';

const customTaleStore = useCustomTaleStore();

const sortOptionT = ref('title');
const sortedTaleList = ref([]);

const sortTaleList = () => {
  switch (sortOptionT.value) {
    case 'title':
      sortedTaleList.value = customTaleStore.myCustomTalesList.sort((a, b) => a.title.localeCompare(b.title));
      break;
    // case 'likeCnt':
    //   sortedTaleList.value = customTaleStore.myCustomTalesList.sort((a, b) => b.likeCnt - a.likeCnt);
    //   break;
    case 'random':
      sortedTaleList.value = customTaleStore.myCustomTalesList.sort(() => Math.random() - 0.5);
      break;
    default:
      break;
  }
};

onMounted(() => {
  customTaleStore.getMyCustomTalesList();
  watch(() => customTaleStore.myCustomTalesList, sortTaleList, { immediate: true });
  watch(sortOptionT, sortTaleList);
});
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

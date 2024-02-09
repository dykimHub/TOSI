<template>
  <div class="talelistContainer">
    <div class=topOfTaleList>
      <h2>내가 만든 동화 목록</h2>
      <div class="selecSort">
        <label>정렬 기준</label>
        <select v-model="sortOptionT">
          <option value="title">이름순</option>
          <option value="regDate">날짜순</option>
          <option value="random">랜덤</option>
        </select>
      </div>
      <button v-if="!deleteButton" @click="activateDeleteButton" class="deleteButton">삭제</button>
      <button v-if="deleteButton" @click="deactivateDeleteButton" class="deleteButton">삭제 취소</button>
    </div>
    <div class="taleContainer">
      <ul v-for="tale in customTaleStore.myCustomTalesList" :key="tale.customTaleId">
        <div class="oneTale">
          <RouterLink :to="`/customTale/${tale.taleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
          <br>
          <RouterLink :to="`/customTale/${tale.taleId}`">{{ tale.title }}</RouterLink>
          <br>
          재생 시간: {{ tale.time }}
        </div>
        <button v-if="deleteButton == true" @click="deleteTale(favorite.favoriteId)">×</button>
      </ul>
    </div>
  </div>
</template>
<script setup>
import { useCustomTaleStore } from '@/stores/customTaleStore';
import { onMounted, ref, watch } from 'vue';
const customTaleStore = useCustomTaleStore();

const deleteButton = ref(false);

const activateDeleteButton = () => {
  deleteButton.value = true;
}
const deactivateDeleteButton = () => {
  deleteButton.value = false;
}

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
      case 'regDate':
      sortedTaleList.value = customTaleStore.myCustomTalesList.sort(sortByDate);
      break;
    case 'random':
      sortedTaleList.value = customTaleStore.myCustomTalesList.sort(() => Math.random() - 0.5);
      break;
    default:
      break;
  }
};

const sortByDate = (a, b) => {
  const dateA = new Date(a.createdAt);
  const dateB = new Date(b.createdAt);
  return dateB - dateA; // 내림차순 정렬
};

const deleteCustomTale = function (customTaleId) {
  alert("나의 책장에서 삭제하시겠습니까?")
  customTaleStore.deleteCustomTale(customTaleId);
  // sortedTaleList.value.splice(customTaleId, customTaleId);
  // sortedTaleList.value = sortedTaleList.value.filter(tale => tale.customTaleId !== customTaleId);
  // window.location.reload()
}


onMounted(() => {
  customTaleStore.getMyCustomTalesList();
  watch(() => customTaleStore.myCustomTalesList, sortTaleList, { immediate: true });
  watch(sortOptionT, sortTaleList);
});

</script>
<style scoped>
ul {
  position: relative;
}

.deleteButton {
  background-color: #d6b0ec;
  border: none;
  color: white;
  font-size: 16px;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
}

.deleteButton:hover {
  background-color: #d6b0ec;
}

.taleContainer {
  position: relative;
}

.taleContainer button {
  position: absolute;
  top: 10px;
  right: 10px;
}

.topOfTaleList {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.taleContainer button {
    background-color: transparent; /* 배경색을 투명하게 설정 */
    border: none; /* 테두리 제거 */
    font-size: 20px; /* 텍스트 크기 설정 */
    cursor: pointer; /* 커서를 포인터로 변경하여 클릭 가능한 상태로 표시 */
    position: absolute; /* 버튼을 모달 안에서 절대 위치로 설정 */
    top: 10px; /* 위쪽 여백 설정 */
    right: 10px; /* 오른쪽 여백 설정 */
    color: rgb(0,0,0); /* 텍스트 색상 설정 */
}
.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
}

.talelistContainer {
  background-color: white;
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

.topOfTaleList {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

a {
  text-decoration: none;
  color: black;
}
</style>
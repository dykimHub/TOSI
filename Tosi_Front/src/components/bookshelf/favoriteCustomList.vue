<template>
  <div class="talelistContainer">
    <div class=topOfTaleList>
      <h2 class="title">{{ bookshelfName }}</h2>
      <div class="selecSort">
        <label>정렬 기준</label>&nbsp;&nbsp;
        <select v-model="sortOptionC">
          <option value="regDate">날짜순</option>
          <option value="title">이름순</option>
          <option value="random">랜덤</option>
        </select>
      </div>
      <button v-if="!deleteButton" @click="activateDeleteButton" class="modifyButton">편집</button>
      <button v-if="deleteButton" @click="deactivateDeleteButton" class="modifyButton">편집 완료</button>
    </div>
    <div class="taleContainer">
      <ul v-for="tale in currentPageBoardList" :key="tale.customTaleId">
        <div class="oneTale">
          <RouterLink :to="`/customTale/${tale.customTaleId}`"><img class="thumbnail" :src="tale.thumbnail" />
          </RouterLink>
          <br>
          <RouterLink :to="`/customTale/${tale.customTaleId}`">{{ tale.title }}</RouterLink>
          <br>
          재생 시간: {{ tale.time }}
        </div>
        <div v-if="deleteButton == true" class="opened-container">
          <label>
            <input type="radio" class="opened-input" placeholder=" " v-model="tale.opened" value="1"
              :checked="tale.opened == 1">
            공개
          </label>
          <label>
            <input type="radio" class="opened-input" placeholder=" " v-model="tale.opened" value="0"
              :checked="tale.opened == 0">
            비공개
          </label>
          <button @click="updateOpened(tale.customTaleId, tale.opened)" class="update-opened-button">수정</button>
        </div>
        <button v-if="deleteButton == true" @click="deleteCustomTale(tale.customTaleId)" class="delete-button">×</button>
      </ul>
    </div>
    <div>
      <nav aria-label="Page navigation" style="padding: 15px;">
        <ul class="pagination d-flex justify-content-center flex-wrap
       pagination-rounded-flat pagination-success ">
          <li class="page-item">
            <a class="page-link" :class="{ disabled: currentPage <= 1 }" href="#" @click.prevent="currentPage--">&lt;</a>
          </li>
          <li :class="{ active: currentPage === page }" class="page-item" v-for="page in pageCount" :key="page">
            <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
              page
            }}</a>
          </li>
          <li class="page-item">
            <a class="page-link" :class="{ disabled: currentPage >= pageCount }" href="#"
              @click.prevent="currentPage++">&gt;</a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>
<script setup>
import { useCustomTaleStore } from '@/stores/customTaleStore';
import { onMounted, ref, watch, computed } from 'vue';
import { useUserStore } from '@/stores/userStore';
const userStore = useUserStore();
const bookshelfName = ref("");

const customTaleStore = useCustomTaleStore();
const myCustomTalesList = ref([]);

const deleteButton = ref(false);
const activateDeleteButton = () => {
  deleteButton.value = true;
}
const deactivateDeleteButton = () => {
  deleteButton.value = false;
}

const sortOptionC = ref('regDate');
const sortedTaleList = ref([]);
const sortTaleList = () => {
  switch (sortOptionC.value) {
    case 'regDate':
      sortedTaleList.value = myCustomTalesList.value.sort((a, b) => a.customTaleId - b.customTaleId);
      break;
    case 'title':
      sortedTaleList.value = myCustomTalesList.value.sort((a, b) => a.title.localeCompare(b.title));
      break;
    case 'random':
      sortedTaleList.value = myCustomTalesList.value.sort(() => Math.random() - 0.5);
      break;
    default:
      break;
  }
};

const updateOpened = function (customTaleId, opened) {
  customTaleStore.updateCustomTale(customTaleId, opened);
}

const deleteCustomTale = async function (customTaleId) {
  alert("나의 책장에서 삭제하시겠습니까?")
  await customTaleStore.deleteCustomTale(customTaleId);
  myCustomTalesList.value = myCustomTalesList.value.filter(tale => tale.customTaleId !== customTaleId);
}

//page
const perPage = 9;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(customTaleStore.myCustomTalesList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};


const currentPageBoardList = computed(() => {
  return myCustomTalesList.value.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});


onMounted(async () => {
  await userStore.getUser();
  bookshelfName.value = userStore.userInfo.bookshelfName;

  await customTaleStore.getMyCustomTalesList();
  myCustomTalesList.value = customTaleStore.myCustomTalesList;
  watch(() => myCustomTalesList, sortTaleList, { immediate: true });
  watch(sortOptionC, sortTaleList);
});

</script>
<style scoped>
.title {
  text-decoration: none;
  display: inline-block;
  box-shadow: inset 0 -20px 0 rgba(196, 236, 176);
  font-size: 40px;
  margin: 30px 0px 0px 50px;
  margin-bottom: 40px;
  line-height: 1;
  text-align: left;
}

ul {
  position: relative;
}

.modifyButton {
  font-size: 16px;
  padding: 8px 16px;
  margin-left: 5px;
  margin-right: 49px;
  cursor: pointer;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  transition: all 0.3s ease;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

.modifyButton:hover {
  box-shadow: 7px 7px 5px 0px #0002, 4px 4px 5px 0px #0001;
}

.topOfTaleList {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-left: 50px;
  margin-right: 50px;
}

.delete-button {
  background-color: transparent;
  border: none;
  font-size: 20px;
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  color: rgb(0, 0, 0);
}

.update-opened-button {
  width: 50px;
  height: 40px;
  border: 2px solid #d0d0d0;
  border-radius: 10px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: 3px 3px 5px 0px #0002;
}

.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-start;
  margin-left: 5vw;
}

.talelistContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: white;
  border-radius: 50px;
  margin: 35px;
  padding-top: 40px;
  opacity: 0.95;
  border: 5px solid #cee8e8;
  width: 80vw;
}

.thumbnail {
  width: 200px;
}

.oneTale {
  width: 13em;
  text-align: center;
  margin: 1em;
  margin-right: 55px;
}

.opened-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.selecSort {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 1em;
  margin-left: auto;
}

a {
  text-decoration: none;
  color: black;
}

.pagination,
.jsgrid .jsgrid-pager {
  display: flex;
  padding-left: 0;
  list-style: none;
  border-radius: 0.25rem
}

.page-link {
  color: black
}

.pagination.pagination-rounded-flat .page-item {
  margin: 0 .30rem
}

.pagination-success .page-item.active .page-link {
  background: #d8eef2;
}

.pagination.pagination-rounded-flat .page-item .page-link {
  border: none;
  border-radius: 50px;
}
</style>
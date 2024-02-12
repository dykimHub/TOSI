<template>
  <div class="talelistContainer">
    <div class=topOfTaleList>
      <h2 class="title">{{ bookshelfName }}</h2>
      <div class="selecSort">
        <label>정렬 기준</label>&nbsp;&nbsp;
        <select v-model="sortOptionC">
          <option value="title">이름순</option>
          <option value="regDate">날짜순</option>
          <option value="random">랜덤</option>
        </select>
      </div>
      <button v-if="!deleteButton" @click="activateDeleteButton" class="deleteButton">삭제</button>
      <button v-if="deleteButton" @click="deactivateDeleteButton" class="deleteButton">삭제 취소</button>
    </div>
    <div class="taleContainer">
      <ul v-for="tale in currentPageBoardList" :key="tale.customTaleId">
        <div class="oneTale">
          <RouterLink :to="`/customTale/${tale.customTaleId}`"><img class="thumbnail" :src="tale.thumbnail" /></RouterLink>
          <br>
          <RouterLink :to="`/customTale/${tale.customTaleId}`">{{ tale.title }}</RouterLink>
          <br>
          재생 시간: {{ tale.time }}
        </div>
        <button v-if="deleteButton == true" @click="deleteCustomTale(tale.customTaleId)">×</button>
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

const sortOptionC = ref('title');
const sortedTaleList = ref([]);
const sortTaleList = () => {
  switch (sortOptionC.value) {
    case 'title':
      sortedTaleList.value = myCustomTalesList.value.sort((a, b) => a.title.localeCompare(b.title));
      break;
    // case 'likeCnt':
    //   sortedTaleList.value = myCustomTalesList.sort((a, b) => b.likeCnt - a.likeCnt);
    //   break;
      case 'regDate':
      sortedTaleList.value = myCustomTalesList.value.sort(sortByDate);
      break;
    case 'random':
      sortedTaleList.value = myCustomTalesList.value.sort(() => Math.random() - 0.5);
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

const deleteCustomTale = async function (customTaleId) {
  alert("나의 책장에서 삭제하시겠습니까?")
  await customTaleStore.deleteCustomTale(customTaleId);
  // myCustomTalesList.value = customTaleStore.myCustomTalesList;
  // sortedTaleList.value.splice(customTaleId, customTaleId);
  myCustomTalesList.value = myCustomTalesList.value.filter(tale => tale.customTaleId !== customTaleId);
  // window.location.reload()
}
// const deleteCustomTale = function (customTaleId) {
//   alert("나의 책장에서 삭제하시겠습니까?")
//   axios.delete(`/customtale/${customTaleId}`,customTaleId, { withCredentials: true }).then((response) => {
//     });
//   // myCustomTalesList.value = customTaleStore.myCustomTalesList;
//   // sortedTaleList.value.splice(customTaleId, customTaleId);
//   sortedTaleList.value = sortedTaleList.value.filter(tale => tale.customTaleId !== customTaleId);
//   // window.location.reload()
// }

  // //나의책장 - 커스텀동화 삭제
  // const deleteCustomTale = async function (customTaleId) {
  //   await axios.delete(`/customtale/${customTaleId}`,customTaleId, { withCredentials: true }).then((response) => {
  //   });
  // };

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
    box-shadow: inset 0 -20px 0  #c4ecb0;
    font-size: 40px;
    margin: 30px 0px 0px 50px;
    margin-bottom: 40px;
    line-height: 1;
    text-align: left;
}

ul {
  position: relative;
}

.deleteButton {
  background-color:  #c4ecb0;
  border: none;
  color: white;
  font-size: 16px;
  padding: 8px 16px;
  margin-left: 5px;
  margin-right: 49px;
  cursor: pointer;
  border-radius: 4px;
}

.deleteButton:hover {
  background-color:  #c4ecb0;
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
/* .taleContainer {
  position: relative;
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
} */

.taleContainer {
  display: flex;
  justify-content: center;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-start;
  margin-left: 5vw;
}

.taleContainer button {
  position: absolute;
  top: 10px;
  right: 10px;
}

.talelistContainer {
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  justify-content: center;
  background-color: white;
  border-radius: 20px;
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
  margin: 2em;
  margin-right: 55px;
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
 

 .pagination-success .page-item.active .page-link
  {
     background: #d8eef2;
 }
 
 .pagination.pagination-rounded-flat .page-item .page-link{
    border: none;
    border-radius: 50px;
}
</style>
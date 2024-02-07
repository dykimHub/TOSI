<template>
<div class="talelistContainer">
    <div class = topOfTaleList>
      <h2>친구들의 동화 보기</h2>
    </div>
    <div class="taleContainer">
        <ul v-for="customTale in currentPageBoardList"
      :key="customTale.customTaleId">
            <div class="oneTale">
                <RouterLink :to="`/customtale/${customTale.customTaleId}`">
                  <img class="thumbnail" :src="customTale.thumbnail" /></RouterLink>
                <br>
                <RouterLink :to="`/customtale/${customTale.customTaleId}`">{{ customTale.title }}</RouterLink>
                <br>
                재생 시간: {{ customTale.time }}
            </div>
        </ul>
    </div>

    <div>
      <nav aria-label="Page navigation" style="padding: 15px;">
      <ul class="pagination d-flex justify-content-center flex-wrap pagination-rounded-flat pagination-success ">
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
import { useCustomTaleStore } from "@/stores/customTaleStore";
import { onMounted, computed, ref } from "vue";
const customTaleStore = useCustomTaleStore();

//page
const perPage = 9;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(customTaleStore.customTalesList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageBoardList = computed(() => {
  return customTaleStore.customTalesList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
}); //page



onMounted(() => {
  customTaleStore.getCustomTalesList(true);
});
</script>

<style>
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
  text-decoration: none !important;
  color: black !important;
}

.flex {
     -webkit-box-flex: 1;
     -ms-flex: 1 1 auto;
     flex: 1 1 auto
 }


 /* page */
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
